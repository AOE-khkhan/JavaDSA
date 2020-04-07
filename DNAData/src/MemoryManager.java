/**
 * A memory manager that makes use of disk I/O.
 * 
 * @author  Bimal Gaudel
 * 
 * @version Mar 24, 2020
 */
public class MemoryManager {
    /**
     * The total number of bytes intially available to the MemoryManager object.
     */
    private int poolSize;

    /** The buffer pool object used for disk I/O. */
    private BufferPool bufferPool;

    /**
     * An array of log2(poolSize) many BlockInfo objects to keep a record of
     * free positions associated with each block.
     */
    private BlockInfo[] blocks;

    /**
     * Construct the MemoryManager object.
     * 
     * @param poolSize   Total number of bytes to start managing.
     * @param bufferPool The buffer pool object used for disk I/O.
     */
    public MemoryManager(int poolSize, BufferPool bufferPool) {
        this.poolSize = poolSize;
        this.bufferPool = bufferPool;

        blocks = new BlockInfo[HelperFunctions.getLog2(this.poolSize) + 1];

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
        // find an entry in the BlockInfo[] array
        // that corresponds to the memory block of size
        // that is required to store the received bytes
        int minBlockPos = HelperFunctions.getLog2(dataSize);

        // get rid of under estimation
        int usedBlockSize = HelperFunctions.getPower2(minBlockPos);
        minBlockPos = dataSize > usedBlockSize ? minBlockPos + 1 : minBlockPos;
        usedBlockSize = HelperFunctions.getPower2(minBlockPos);

        // usedBlockPos is the position in the BlockInfo array, the block
        // corresponding to which is used to either store the data in case it is
        // at least one free entry, or split until we get a free block of
        // minimum size in the database
        int usedBlockPos = minBlockPos;
        //
        // we want to store bytes in the computed minBlockPos
        // however we may not have a free chunk of memory
        // of that particular size
        while (usedBlockPos < blocks.length) {
            if (!blocks[usedBlockPos].isEmpty()) {
                break;
            }
            ++usedBlockPos;
        }

        if (usedBlockPos >= blocks.length) {
            // even the largest memory block is too small for our data
            // double the pool size and call storeBytes recursively
            doublePoolSize();
            return storeBytes(bytes);
        }

        // gets the first free position to be used
        int insertionPos = blocks[usedBlockPos].getPos();

        // splitting is required if the block was different from first
        // calculated
        if (usedBlockPos > minBlockPos) {
            splitBlock(insertionPos, HelperFunctions.getPower2(usedBlockPos),
                    usedBlockSize);
        }

        // creating the memory handle to return
        MemoryHandle handle =
                new MemoryHandle(insertionPos, usedBlockSize, dataSize);

        // inserting the data
        bufferPool.insertBytes(bytes, handle);
        // removing the just used memory block from the free-block database
        blocks[minBlockPos].deletePos(insertionPos);

        return handle;
    }

    /**
     * Retain the bytes from the memory. Handle is used to get reading positions
     * and bytes will be overwritten with the read data.
     *
     * @param bytes  A reference to an array of bytes that will be overrwritten
     *               with the retained data.
     * @param handle A MemoryHandle object that tells where to start and stop
     *               reading.
     */
    public void getBytes(byte[] bytes, MemoryHandle handle) {
        bufferPool.getBytes(bytes, handle);
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
        int blockInfoPos = HelperFunctions.getLog2(handle.getBlockSize());

        // first attempt merging buddies
        // if it succeeds, then nothing needs to be done
        boolean merged = mergeBuddy(handle.getBlockSize(), handle.getPos());

        if (!merged) {
            blocks[blockInfoPos].insertPos(handle.getPos());
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
                int size = HelperFunctions.getPower2(i);
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
        int blockInfoPos = HelperFunctions.getLog2(blockSize);
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
        int blockInfoPos = HelperFunctions.getLog2(blockSize);
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
        try {
            // turns out setLength is not needed as we can write beyond the file
            // size limit
            // bufferPool.getDiskIOFile().setLength(2 * poolSize);
            poolSize *= 2;
            BlockInfo[] temp = this.blocks;

            this.blocks = new BlockInfo[temp.length + 1];
            for (int i = 0; i < temp.length; ++i) {
                blocks[i] = temp[i];
            }
            // finally mark the extra space as free
            this.blocks[temp.length] = new BlockInfo();
            addPos(poolSize / 2, poolSize / 2);
            mergeBuddy(poolSize / 2, poolSize / 2);

            System.out.println(
                    "Memory pool expanded to be " + poolSize + " bytes.");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
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
