/**
 * The memory manager class.
 * 
 * @author Bimal Gaudel
 * @version 2020-02-09
 */
public class MemoryManager {
  /**
   * The total number of bytes available to the MemoryManager object.
   */
  private int poolSize;

  /**
   * The bare bytes this object currently manages.
   */
  private byte[] byteArray;

  /**
   * An array of log2(poolSize) BlockInfo objects to keep a record of free
   * positions associated with each block.
   */
  private BlockInfo[] blocks;

  /**
   * Construct the MemoryManager object.
   * 
   * @param size 2^size bytes will be allocated for the memory manager.
   */
  public MemoryManager(int size) {
    // 2^(size) bytes are allocated
    this.poolSize = 1 << size;
    this.byteArray = new byte[this.poolSize];
    this.blocks = new BlockInfo[size + 1];
    for (int i = 0; i <= size; ++i) {
      this.blocks[i] = new BlockInfo();
    }

    this.addPos(this.poolSize, 0);
  }

  /**
   * Store information and return a handle. The handle should be used for future
   * retrieval or freeing up the occupied space.
   */
  public MemoryHandle storeBytes(byte[] bytes) {
    int size = bytes.length;
    int expectedBlockPos = MemoryManager.getLog2(size);

    // get rid of under estimation
    int storedSize = 1 << expectedBlockPos;
    expectedBlockPos = size > storedSize ? expectedBlockPos + 1
        : expectedBlockPos;
    storedSize = 1 << expectedBlockPos;

    int blockPos = expectedBlockPos;

    while (blockPos < this.blocks.length) {
      if (!this.blocks[blockPos].isEmpty()) {
        break;
      }
      ++blockPos;
    }

    if (blockPos >= this.blocks.length) {
      this.doublePoolSize();
      return this.storeBytes(bytes);
    }

    int insertionPos = this.blocks[blockPos].getPos();

    if (blockPos > expectedBlockPos) {
      this.splitBlock(insertionPos, 1 << blockPos, storedSize);
    }

    for (int i = 0; i < bytes.length; ++i) {
      this.byteArray[insertionPos + i] = bytes[i];
    }

    this.blocks[expectedBlockPos].deletePos(insertionPos);
    return new MemoryHandle(insertionPos, storedSize, bytes.length);
  }

  /**
   * Retain the bytes from the memory. Handle is used to get reading positions
   * and bytes will be overrwritten with the read data.
   *
   * @param bytes  A reference to an array of bytes that will be overrwritten
   *               with the retained data.
   * @param handle A MemoryHandle object that tells where to start and stop
   *               reading.
   */
  public void getBytes(byte[] bytes, MemoryHandle handle) {
    // copy the number of bytes that is equal to the smaller of bytes length
    // and stored bytes length
    for (int i = 0; ((i < bytes.length) && (i < handle.getDataSize())); ++i) {
      bytes[i] = this.byteArray[handle.getPos() + i];
    }
  }

  /**
   * Free a memory block associated with a memory handle.
   * 
   * @param handle A MemoryHandle object.
   */
  public void freeBlock(MemoryHandle handle) {
    // simply mark the block as free by storing
    // the position of the handle's database into
    // the block size's position database
    int blockArrayPos = MemoryManager.getLog2(handle.getBlockSize());

    // first attempt mergin buddies
    // if it succeeds, then nothing needs to be done
    boolean merged = this.mergeBuddy(handle.getBlockSize(), handle.getPos());

    if (!merged) {
      this.blocks[blockArrayPos].insertPos(handle.getPos());
    }
  }

  /**
   * Getter method for the field poolSize.
   * 
   * @return Total number of bytes being handled.
   */
  public int getPoolSize() {
    return this.poolSize;
  }

  /**
   * String representation of the memory manager. It shows currently free blocks
   * and their starting positions.
   */
  @Override
  public String toString() {
    String result = "";
    for (int i = 0; i < this.blocks.length; ++i) {
      String posStrings = this.blocks[i].toString();
      if (!posStrings.isEmpty()) {
        int size = 1 << i;
        result += size + ": " + posStrings + "\n";
      }
    }
    if (result.isEmpty()) {
      return "No free blocks are available.";
    }
    return result.trim();
  }

  /**
   * Add a position of a memory block.
   * 
   * @param blockSize Size of the memory block, which is always a power of two.
   * @param pos       Position in the bytes array where the block starts.
   */
  private void addPos(int blockSize, int pos) {
    int blockInfoPos = MemoryManager.getLog2(blockSize);
    this.blocks[blockInfoPos].insertPos(pos);
  }

  /**
   * Remove a position of a memory block.
   * 
   * @param blockSize Size of the memory block, which is always a power of two.
   * @param pos       Position in the bytes array where the block starts.
   * @return true if position removed, false otherwise.
   */
  private boolean removePos(int blockSize, int pos) {
    int blockInfoPos = MemoryManager.getLog2(blockSize);
    boolean removed = this.blocks[blockInfoPos].deletePos(pos);
    return removed;
  }

  /**
   * Split a memory block into halves until desired size is achieved. This will
   * simply record new block positions in the free blocks database and remove
   * the starting blocks's position.
   * 
   * @param blockPos   Position of the memory block to be split up.
   * @param blockSize  Size of the memory block that is being split up.
   * @param targetSize Memory block size desired to be obtained by splitting up.
   */
  private void splitBlock(int blockPos, int blockSize, int targetSize) {
    this.removePos(blockSize, blockPos);
    while (blockSize != targetSize) {
      blockSize /= 2;
      this.addPos(blockSize, blockPos + blockSize);
    }
    this.addPos(targetSize, blockPos);
  }

  /**
   * Get the buddy's position.
   * 
   * @param thisBuddyPos Position of the memory block for whom we are looking
   *                     the buddy
   * @param size         The size of the memory block (both buddies have the
   *                     same block sizes). Size is always a power of two.
   * @return Calculated int position of the buddy.
   */
  private int getBuddyPos(int thisBuddyPos, int size) {
    return thisBuddyPos ^ size;
  }

  /**
   * Merge buddy memory blocks if both are free.
   *
   * @param blockSize The size of the memory block where merger started.
   * @param pos       The position of the memory block that initiated the
   *                  merger.
   * @return true if any merger occurred, false otherwise.
   */
  private boolean mergeBuddy(int blockSize, int pos) {
    int initBlockSize = blockSize;
    while (blockSize < this.poolSize) {
      int buddyPos = this.getBuddyPos(pos, blockSize);
      boolean removed = this.removePos(blockSize, buddyPos);
      if (!removed) {
        break;
      }
      this.removePos(blockSize, pos);
      pos = pos < buddyPos ? pos : buddyPos;
      blockSize *= 2;
    }
    if (initBlockSize != blockSize) {
      this.addPos(blockSize, pos);
      return true;
    }
    return false;
  }

  /** Double the capacity of the memory poolsize. */
  private void doublePoolSize() {
    // initialize a new MemoryManager object with double capacity
    MemoryManager biggerMemoryManager = new MemoryManager(
        MemoryManager.getLog2(this.poolSize) + 1);
    biggerMemoryManager.removePos(this.poolSize * 2, 0);
    // copy all the bytes
    for (int i = 0; i < this.byteArray.length; ++i) {
      biggerMemoryManager.byteArray[i] = this.byteArray[i];
    }
    // copy all the block informations
    for (int i = 0; i < this.blocks.length; ++i) {
      biggerMemoryManager.blocks[i] = this.blocks[i];
    }
    // finally mark the extra space as free
    biggerMemoryManager.addPos(this.poolSize, this.poolSize);

    // okay now time to obtain everything from the biggerMemoryManager
    // to this memory manager
    this.poolSize = biggerMemoryManager.poolSize;
    this.byteArray = biggerMemoryManager.byteArray;
    this.blocks = biggerMemoryManager.blocks;
    this.mergeBuddy(this.poolSize / 2, this.poolSize / 2);
  }

  /**
   * Get the log based two. E.g. getLog2(4) = 2, getLog2(31) = 4, getLog2(32) =
   * 5
   * 
   * @param num The positive number to calculate log2 of.
   * @return The number which is to be raised to the power of two.
   */
  public static int getLog2(int num) {
    int result = -1;
    while (num != 0) {
      num = num >> 1;
      ++result;
    }
    return result;
  }

  /**
   * A class that holds the information on a block of given size. It is a
   * wrapper class around the generic LinkedList class.
   */
  private class BlockInfo {
    /**
     * Various positions (if any) where blocks start in the byteArray.
     */
    private LinkedList<Integer> blockPos;

    /** Construct a BlockInfo. */
    BlockInfo() {
      this.blockPos = new LinkedList<Integer>();
    }

    /**
     * Insert a new position index for the current block. The insertion will
     * maintain a lexicographic ordering of the positions.
     */
    public void insertPos(int pos) {
      this.blockPos.insert(pos);
    }

    /**
     * Delete a position entry for the block.
     * 
     * @param Position to be deleted.
     * @return true if position deleted, false otherwise.
     */
    public boolean deletePos(int pos) {
      boolean deleted = this.blockPos.remove(pos);
      return deleted;
    }

    /**
     * Get a free position from the positions record for the current blocksize.
     * It will also remove the position from the database.
     * 
     * @return The first occurring position.
     */
    public int getPos() {
      int pos = this.blockPos.popFront();
      return pos;
    }

    /**
     * Check if this block has any free positions.
     *
     * @return true if no positions in record, false otherwise.
     */
    public boolean isEmpty() {
      boolean empty = this.blockPos.isEmpty();
      return empty;
    }

    /**
     * String representation of a BlockInfo object.
     */
    @Override
    public String toString() {
      return this.blockPos.toString().replaceAll("<SEP>", " ");
    }
  }
}
