import java.io.RandomAccessFile;

/**
 * A class to handle a collection of file I/O buffers.
 * 
 * @author  Bimal Gaudel
 * 
 * @version Mar 30, 2020
 */
public class BufferPool {
    /** The number of bytes present in each of the buffers in the pool. */
    private int sizeBuffer;

    /** The collection of buffers. */
    private LinkedList<Buffer> pool;

    /** The file for disk I/O. */
    private RandomAccessFile diskIOFile;

    /** Number of times buffers were accessed for read/write. */
    private int cacheHits;

    /** Number of times disk reads performed. */
    private int diskReads;

    /** Number of times disk writes performed. */
    private int diskWrites;

    /**
     * Construct a BufferPool object.
     * 
     * @param numBuffers Number of buffers in the pool.
     * @param sizeBuffer Number of bytes per buffer.
     * @param diskIOFile File for disk I/O.
     */
    BufferPool(int numBuffers, int sizeBuffer, RandomAccessFile diskIOFile) {
        this.sizeBuffer = sizeBuffer;
        this.diskIOFile = diskIOFile;
        cacheHits = 0;
        diskReads = 0;
        diskWrites = 0;
        // initialize the buffers
        pool = new LinkedList<Buffer>();
        for (int i = 0; i < numBuffers; ++i) {
            // initially Buffer is initialized with the block id -1
            pool.append(new Buffer(-1, sizeBuffer));
        }
    }

    /**
     * Get a reference to the disk I/O file.
     * 
     * @return Pointer to the random access file used by this buffer pool.
     */
    public RandomAccessFile getDiskIOFile() {
        return diskIOFile;
    }

    /**
     * Store bytes from space to appropriate buffer(s). Use handle for guidance.
     * 
     * @param space  Byte array to be stored.
     * @param handle MemoryHandle object to guide store data.
     */
    public void insertBytes(byte[] space, MemoryHandle handle) {
        int blockId = findBlockId(handle);
        int bufferOffset = handle.getPos() % sizeBuffer;
        int remainingBytes = handle.getDataSize();
        //
        Buffer currBuffer = null;
        pool.moveToHead();
        do {
            for (; !pool.atEnd(); pool.curseToNext()) {
                currBuffer = pool.yieldNode();
                if ((currBuffer.getBlockId() == blockId)
                        || (currBuffer.getBlockId() == -1)) {
                    break;
                }
            }

            if (pool.atEnd()) {
                // no suitable block found
                // need to overwrite least recently used block
                pool.moveToTail();
                currBuffer = pool.yieldNode();
                if (currBuffer.isDirty()) {
                    writeBufferToDisk(currBuffer);
                }
            }
            else {
                // either a block with proper id was found
                // or an empty block was encountered
                if (currBuffer.getBlockId() != -1) {
                    ++cacheHits;
                }
            }
            int spaceOffset = handle.getDataSize() - remainingBytes;
            int bytesInserted = currBuffer.insert(space, remainingBytes,
                    spaceOffset, bufferOffset);
            currBuffer.setBlockId(blockId);
            //
            pool.bubbleNodeToFront();
            pool.moveToHead();
            pool.curseToNext();
            //

            remainingBytes -= bytesInserted;
            bufferOffset = 0;
            ++blockId;
        } while (remainingBytes > 0);
    }

    /**
     * Retrieve bytes from the buffer(s) or from the disk file as appropriate.
     * 
     * @param space  Byte array that will be overwritten by retrieved data.
     * @param handle MemoryHandle object to guide retrieve data.
     */
    public void getBytes(byte[] space, MemoryHandle handle) {
        int blockId = findBlockId(handle);
        int bufferOffset = handle.getPos() % sizeBuffer;
        int remainingBytes = handle.getDataSize();
        //
        Buffer currBuffer = null;
        pool.moveToHead();
        do {
            for (; !pool.atEnd(); pool.curseToNext()) {
                currBuffer = pool.yieldNode();
                if (currBuffer.getBlockId() == blockId) {
                    break;
                }
            }

            if (pool.atEnd()) {
                // no suitable block found
                // need to overwrite least recently used block
                pool.moveToTail();
                currBuffer = pool.yieldNode();
                if (currBuffer.isDirty()) {
                    writeBufferToDisk(currBuffer);
                }
                writeBufferFromDisk(blockId, currBuffer);
            }
            else {
                // else
                // block with proper id was found
                ++cacheHits;
            }
            int spaceOffset = handle.getDataSize() - remainingBytes;
            int bytesRetrieved = currBuffer.getBytes(space, remainingBytes,
                    spaceOffset, bufferOffset);
            //
            pool.bubbleNodeToFront();
            pool.moveToHead();
            pool.curseToNext();
            //

            remainingBytes -= bytesRetrieved;
            bufferOffset = 0;
            ++blockId;
        } while (remainingBytes > 0);
    }

    /**
     * Get a string with info on buffers.
     * 
     * @return String representation of the buffer pool.
     */
    @Override
    public String toString() {
        String result = "";
        for (pool.moveToHead(); !pool.atEnd(); pool.curseToNext()) {
            result += pool.yieldNode().toString() + "\n";
        }

        result += String.format(
                "Cache hits: %d\n" + "Disk reads: %d\n" + "Disk writes: %d",
                cacheHits, diskReads, diskWrites);
        return result;
    }

    /**
     * Write buffer from disk.
     * 
     * @param blockId      The block id of the data block.
     * @param bufferInPool The buffer that will be overwritten.
     */
    private void writeBufferFromDisk(int blockId, Buffer bufferInPool) {
        try {
            diskIOFile.seek(blockId * sizeBuffer);
            diskIOFile.read(bufferInPool.getDataBytes(), 0, sizeBuffer);
            bufferInPool.setBlockId(blockId);
            ++diskReads;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Write buffer to disk.
     * 
     * @param bufferInPool The buffer to be written to the file.
     */
    private void writeBufferToDisk(Buffer bufferInPool) {
        try {
            diskIOFile.seek(bufferInPool.getBlockId() * sizeBuffer);
            diskIOFile.write(bufferInPool.getDataBytes());
            bufferInPool.markClean();
            ++diskWrites;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Find the block id to which a data block belongs.
     * 
     * @param  handle The memory handle for the data being queried about.
     * 
     * @return        The block id of the buffer which has the starting position
     *                for data as read in the memory handle.
     */
    private int findBlockId(MemoryHandle handle) {
        return handle.getPos() / sizeBuffer;
    }
} // class BufferPool
