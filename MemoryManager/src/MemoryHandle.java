//------------------------------------------------------------------------------
/**
 * A class that stores a memory block start position and the size. This object
 * acts as the handle when MemoryManager and Record comunicate with each
 * other. @see MemoryManager.java @see Record.java
 * 
 * @author Bimal Gaudel
 * @version 2020-02-13
 */
public class MemoryHandle implements Comparable<MemoryHandle> {
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

  MemoryHandle(MemoryHandle other) {
    this(other.getPos(), other.getBlockSize(), other.getDataSize());
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

  /**
   * Check if two MemoryHandle objects refer to the same memory block. It is
   * only relevant when both are referring to the same MemoryManager.
   * 
   * @param obj Object of MemoryHandle type being compared against.
   * @return true if both refer to the same memory block, false otherwise.
   */
  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }

    if (!(obj instanceof MemoryHandle)) {
      return false;
    }

    MemoryHandle other = (MemoryHandle) obj;

    return ((this.getPos() == other.getPos())
        && (this.getBlockSize() == other.getBlockSize())
        && (this.getDataSize() == other.getDataSize()));
  }

  /**
   * Compare MemoryHandle based on where their positions start. This makes the
   * MemoryHandle class a Comparable so that we can use with the LinkedList
   * class.
   * 
   * @param anotherHandle Another MemoryHandle being compared.
   * @return -1 if this is smaller than that, 0 if they are equal, 1 otherwise.
   */
  @Override
  public int compareTo(MemoryHandle anotherHandle) {
    if (this.equals(anotherHandle)) {
      return 0;
    }
    if (this.getPos() < anotherHandle.getPos()) {
      return -1;
    }
    return +1;
  }
}
