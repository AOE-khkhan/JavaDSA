/**
 * A Buffer object is an element of the collection of the buffers managed by
 * the BufferPool. Note that Buffer implements Comparable<Buffer> so that it
 * can be used with the LinkedList class.
 * 
 * @author  Bimal Gaudel
 * 
 * @version Mar 30, 2020
 */
public class Buffer implements Comparable<Buffer> {
    /** The blockId of the buffer. */
    private int blockId;

    /** The byte array managed by this buffer. */
    private byte[] dataBytes;

    /** The dirty bit of the buffer. */
    private boolean dirty;

    /**
     * Construct a buffer.
     * 
     * @param blockId   The block id.
     * @param blockSize The number of bytes this buffer has.
     */
    Buffer(int blockId, int blockSize) {
        this.blockId = blockId;
        dataBytes = new byte[blockSize];
        dirty = false;
    }

    /**
     * Getter for the block id.
     * 
     * @return The block id of this buffer
     */
    public int getBlockId() {
        return blockId;
    }

    /**
     * Setter for the block id.
     * 
     * @param id The id value to be set.
     */
    public void setBlockId(int id) {
        blockId = id;
    }

    /** Set the dirty bit on. */
    public void markDirty() {
        dirty = true;
    }

    /** Set the dirty bit off. */
    public void markClean() {
        dirty = false;
    }

    /**
     * Check if the dirty bit is set or not.
     *
     * @return True if the dirty bit is set.
     */
    public boolean isDirty() {
        return dirty;
    }

    /**
     * Return reference to the dataBytes array. Note use with care.
     * 
     * @return Reference to the dataBytes.
     */
    public byte[] getDataBytes() {
        return dataBytes;
    }

    /**
     * Copy bytes to the buffered storage.
     * 
     * @param  data         Byte array with data to be written to the buffer.
     * @param  size         Number of bytes to be written.
     * @param  dataOffset   Position in data of the first byte to be
     *                      written.
     * @param  bufferOffset Position in the buffer where to start inserting.
     * 
     * @return              The number of bytes inserted.
     */
    public int insert(byte[] data, int size, int dataOffset, int bufferOffset) {
        int i = 0;
        for (; (i < size) && (i + bufferOffset < dataBytes.length); ++i) {
            dataBytes[bufferOffset + i] = data[dataOffset + i];
        }
        // after writing bytes, always mark this buffer to be dirty
        markDirty();

        // total number of bytes inserted
        return i;
    }

    /**
     * Copy bytes from the buffered storage.
     * 
     * @param  data         Byte array that will be overwritten with the data
     *                      from buffer.
     * @param  size         Number of bytes to be copied.
     * @param  dataOffset   Position in data where to start writing
     * @param  bufferOffset Position in the buffer where to start copying.
     * 
     * @return              The number of bytes inserted.
     */
    public int getBytes(byte[] data, int size, int dataOffset,
            int bufferOffset) {
        int i = 0;
        for (; (i < size) && (i + bufferOffset < dataBytes.length); ++i) {
            data[dataOffset + i] = dataBytes[bufferOffset + i];
        }

        // total number of bytes copied
        return i;
    }

    /**
     * Compare two buffers based on their ids. This makes the Buffer class a
     * Comparable so that we can use with the LinkedList class.
     * 
     * @param  that Another Buffer object.
     * 
     * @return      -1 if this buffer's block id is smaller than the others,
     *              0 if equal, 1 otherwise.
     */
    @Override
    public int compareTo(Buffer that) {
        if (getBlockId() == that.getBlockId()) {
            return 0;
        }
        if (getBlockId() < that.getBlockId()) {
            return -1;
        }
        return 1;
    }

    /**
     * Get a string representation of the buffer.
     * 
     * @return String with block id and dirty/clean tag.
     */
    @Override
    public String toString() {
        return String.format("%d %s", getBlockId(),
                isDirty() ? "dirty" : "clean");
    }
} // class Buffer
