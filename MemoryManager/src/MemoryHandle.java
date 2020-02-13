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
  private int size;

  /**
   * Construct MemoryHandle.
   * 
   * @param pos  Memory block position in the byte array.
   * @param size Size of the memory block.
   */
  MemoryHandle(int pos, int size) {
    this.pos = pos;
    this.size = size;
  }

  /** Getter method for the field pos. */
  int getPos() {
    return this.pos;
  }

  /** Getter method for the field size. */
  int getSize() {
    return this.size;
  }

  /**
   * Overriden method toString.
   * 
   * @return String representation of the handle.
   */
  @Override
  public String toString() {
    return "" + this.getSize() + ": " + this.getPos();
  }
}
