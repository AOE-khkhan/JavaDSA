/**
 * A class that stores a memory block start position and the size. This object
 * acts as the handle while communicating with a MemoryManager.
 * 
 * @see     MemoryManager.java
 * 
 * @author  Bimal Gaudel
 * 
 * @version Mar 24, 2020
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
     * @param blockSize Number of bytes in the block. Always a power of two.
     * @param dataSize  Number of bytes used for the data. Never greater than
     *                  blockSize.
     */
    MemoryHandle(int pos, int blockSize, int dataSize) {
        this.pos = pos;
        this.blockSize = blockSize;
        this.dataSize = dataSize;
    }

    /**
     * Getter method for the field pos.
     * 
     * @return Position value stored by this object.
     */
    public int getPos() {
        return pos;
    }

    /**
     * Getter method for the field blockSize.
     * 
     * @return Block size value stored by this object.
     */
    int getBlockSize() {
        return blockSize;
    }

    /**
     * Getter method for the field dataSize.
     * 
     * @return Data size value stored by this object.
     */
    public int getDataSize() {
        return dataSize;
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
     * Check if two MemoryHandle objects refer to the same memory block.
     * 
     * @param  obj Object of MemoryHandle type being compared against.
     * 
     * @return     true if both refer to the same memory block, false otherwise.
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
     * @param  anotherHandle Another MemoryHandle being compared.
     * 
     * @return               -1 if this is smaller than that, 0 if equal, 1
     *                       otherwise.
     */
    @Override
    public int compareTo(MemoryHandle anotherHandle) {
        if (this.equals(anotherHandle)) {
            return 0;
        }
        if (this.getPos() < anotherHandle.getPos()) {
            return -1;
        }
        return 1;
    }
}
