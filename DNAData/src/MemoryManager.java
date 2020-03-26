/**
 * The memory manager class. This class manages an array of bytes using the
 * buddy method.
 * 
 * @author  Bimal Gaudel
 * 
 * @version Mar 24, 2020
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
     * An array of log2(poolSize) many BlockInfo objects to keep a record of
     * free positions associated with each block.
     */
    private BlockInfo[] blocks;

    /**
     * Construct the MemoryManager object.
     * 
     * @param poolSize Total number of bytes to start managing.
     */
    public MemoryManager(int poolSize) {
        this.poolSize = poolSize;
        byteArray = new byte[this.poolSize];

        blocks = new BlockInfo[getLog2(this.poolSize) + 1];

        for (int i = 0; i < blocks.length; ++i) {
            blocks[i] = new BlockInfo();
        }

        // initialize the whole block at the position 0
        addPos(this.poolSize, 0);
    }

    /**
     * Store information and return a handle. The handle should be used for
     * future retrieval or freeing up the occupied space.
     *
     * @param  bytes An array of byte to be stored in the memory pool.
     * 
     * @return       A MemoryHandle object.
     */
    public MemoryHandle storeBytes(byte[] bytes) {
        int dataSize = bytes.length;
        // find the position in the BlockInfo[] blocks array
        // that corresponds to the memory block of size
        // that is required to store the received bytes
        int minBlockPos = MemoryManager.getLog2(dataSize);

        // get rid of under estimation
        int usedBlockSize = 1 << minBlockPos;
        minBlockPos = dataSize > usedBlockSize ? minBlockPos + 1 : minBlockPos;
        usedBlockSize = 1 << minBlockPos;

        int usedBlockPos = minBlockPos;
        while (usedBlockPos < blocks.length) {
            if (!blocks[usedBlockPos].isEmpty()) {
                break;
            }
            ++usedBlockPos;
        }

        if (usedBlockPos >= blocks.length) {
            // no memory block of appropriate size exists
            // double the pool size and call storeBytes recursively
            doublePoolSize();
            return storeBytes(bytes);
        }

        // gets the first free position to be used
        int insertionPos = blocks[usedBlockPos].getPos();

        // splitting is required if the block was different from first
        // calculated
        if (usedBlockPos > minBlockPos) {
            splitBlock(insertionPos, 1 << usedBlockPos, usedBlockSize);
        }

        // inserting the data
        for (int i = 0; i < bytes.length; ++i) {
            byteArray[insertionPos + i] = bytes[i];
        }

        // removing the just used memory block from the free-block database
        blocks[minBlockPos].deletePos(insertionPos);
        return new MemoryHandle(insertionPos, usedBlockSize, bytes.length);
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
        for (int i = 0; ((i < bytes.length)
                && (i < handle.getDataSize())); ++i) {
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
        boolean merged =
                this.mergeBuddy(handle.getBlockSize(), handle.getPos());

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
     * String representation of the memory manager. It shows currently free
     * blocks and their starting positions.
     * 
     * @return String representation of the memory manager, showing free blocks.
     */
    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < blocks.length; ++i) {
            String posStrings = blocks[i].toString();
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
     * @param blockSize Size of the memory block, which is always a power of
     *                  two.
     * @param pos       Position in the bytes array where the block starts.
     */
    private void addPos(int blockSize, int pos) {
        int blockInfoPos = MemoryManager.getLog2(blockSize);
        this.blocks[blockInfoPos].insertPos(pos);
    }

    /**
     * Remove a position of a memory block.
     * 
     * @param  blockSize Size of the memory block, which is always a power of
     *                   two.
     * @param  pos       Position in the bytes array where the block starts.
     * 
     * @return           True if position removed, false otherwise.
     */
    private boolean removePos(int blockSize, int pos) {
        int blockInfoPos = MemoryManager.getLog2(blockSize);
        return blocks[blockInfoPos].deletePos(pos);
    }

    /**
     * Split a memory block into halves until desired size is achieved. This
     * will simply record new block positions in the free blocks database and
     * remove the starting blocks's position.
     * 
     * @param blockPos   Position of the memory block to be split up.
     *
     * @param blockSize  Size of the memory block that is being split up.
     *
     * @param targetSize Memory block size desired to be obtained by splitting
     *                   up.
     */
    private void splitBlock(int blockPos, int blockSize, int targetSize) {
        removePos(blockSize, blockPos);
        while (blockSize != targetSize) {
            blockSize /= 2;
            addPos(blockSize, blockPos + blockSize);
        }
        addPos(targetSize, blockPos);
    }

    /**
     * Get the buddy's position.
     * 
     * @param  thisBuddyPos Position of the memory block for whom we are looking
     *                      the buddy
     * @param  size         The size of the memory block (both buddies have the
     *                      same block sizes). Size is always a power of two.
     * 
     * @return              Calculated int position of the buddy.
     */
    private int getBuddyPos(int thisBuddyPos, int size) {
        return thisBuddyPos ^ size;
    }

    /**
     * Merge buddy memory blocks if both are free.
     *
     * @param  blockSize The size of the memory block which started the merger.
     *
     * @param  pos       The position of the memory block in the bytes array.
     * 
     * @return           True if any merger occurred, false otherwise.
     */
    private boolean mergeBuddy(int blockSize, int pos) {
        int initBlockSize = blockSize;
        while (blockSize < poolSize) {
            int buddyPos = getBuddyPos(pos, blockSize);
            boolean removed = removePos(blockSize, buddyPos);
            if (!removed) {
                break;
            }
            removePos(blockSize, pos);
            pos = pos < buddyPos ? pos : buddyPos;
            blockSize *= 2;
        }
        if (initBlockSize != blockSize) {
            addPos(blockSize, pos);
            return true;
        }
        return false;
    }

    /** Double the capacity of the memory poolsize. */
    private void doublePoolSize() {
        // initialize a new MemoryManager object with double capacity
        MemoryManager biggerMemoryManager = new MemoryManager(2 * poolSize);
        biggerMemoryManager.removePos(poolSize * 2, 0);
        // copy all the bytes
        for (int i = 0; i < byteArray.length; ++i) {
            biggerMemoryManager.byteArray[i] = byteArray[i];
        }
        // copy all the block informations
        for (int i = 0; i < blocks.length; ++i) {
            biggerMemoryManager.blocks[i] = blocks[i];
        }
        // finally mark the extra space as free
        biggerMemoryManager.addPos(poolSize, poolSize);

        // okay now time to obtain everything from the biggerMemoryManager
        // to this memory manager
        this.poolSize = biggerMemoryManager.poolSize;
        this.byteArray = biggerMemoryManager.byteArray;
        this.blocks = biggerMemoryManager.blocks;
        this.mergeBuddy(poolSize / 2, poolSize / 2);
    }

    /**
     * Get the log based two.
     * E.g.
     * getLog2(4) = 2
     * getLog2(31) = 4
     * getLog2(32) = 5
     * 
     * @param  num The positive number to calculate log2 of.
     * 
     * @return     The number which is to be raised to the power of two.
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
     * 
     * @author  Bimal Gaudel
     * 
     * @version Feb 2020
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
         * @param  pos Position to be deleted.
         * 
         * @return     True if position deleted, false otherwise.
         */
        public boolean deletePos(int pos) {
            return this.blockPos.remove(pos);
        }

        /**
         * Get a free position from the positions record for the current
         * block size. It will also remove the position from the database.
         * 
         * @return The first occurring position.
         */
        public int getPos() {
            return this.blockPos.popFront();
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
            String result = "";
            for (blockPos.moveToHead(); !blockPos.atEnd(); blockPos
                    .curseToNext()) {
                result += " " + blockPos.yieldNode().toString();
            }
            return result;
        }
    }
}
