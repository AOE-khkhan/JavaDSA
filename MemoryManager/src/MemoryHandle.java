//------------------------------------------------------------------------------
/**
 * A class that stores a memory block start position and the size. This object
 * acts as the handle when MemoryManager and Record comunicate with each
 * other. @see MemoryManager.java @see Record.java
 * 
 * @author Bimal Gaudel
 * @version 2020-02-13
 */
public class MemoryHandle {
  /** The position in the bytes array where the block starts. */
  private int pos;

  /** The size of the block. */
  private int blockSize;

  /** The size of the data associated with this handle. */
  private int dataSize;

  /**
   * Construct MemoryHandle.
   * 
   * @param pos       Memory block position in the byte array.
   * @param blockSize Size of the memory block.
   * @param dataSize  Size of the data actually stored: never greater than
   *                  blockSize.
   */
  MemoryHandle(int pos, int blockSize, int dataSize) {
    this.pos = pos;
    this.blockSize = blockSize;
    this.dataSize = dataSize;
  }

  /**
   * Construct MemoryHandle.
   * 
   * @param pos  Memory block position in the byte array.
   * @param size Size of the memory block and the data.
   */
  MemoryHandle(int pos, int size) {
    this(pos, size, size);
  }

  /** Getter method for the field pos. */
  public int getPos() {
    return this.pos;
  }

  /** Getter method for the field blockSize. */
  int getBlockSize() {
    return this.blockSize;
  }

  public int getDataSize() {
    return this.dataSize;
  }

  /**
   * Overridden method toString.
   * 
   * @return String representation of the handle.
   */
  @Override
  public String toString() {
    return "" + this.getBlockSize() + ": " + this.getPos() + " "
        + this.getDataSize();
  }
}
