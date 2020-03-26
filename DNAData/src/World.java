import java.util.Scanner;
import java.nio.ByteBuffer;

/**
 * World object is the platform for DNAData project actions.
 * 
 * @author  Bimal Gaudel
 * 
 * @version Mar 21, 2020
 */
public class World {
    // /** Number of buffers in the buffer pool. */
    // private int numBuffers;

    // /** Size of a buffer in bytes that is a power of two. */
    // private int buffSize;

    /** The memory manager for this world. */
    private MemoryManager memManager;

    /** The hash table for this world. */
    private HashTable hashTable;

    /**
     * Construct a World object.
     * 
     * @param numBuffers Number of buffers in the buffer pool.
     * @param buffSize   Size of a buffer in bytes that is a power of two.
     * @param hashSlots  Number of slots in the hash table.
     */
    World(int numBuffers, int buffSize, int hashSlots) {
        // this.numBuffers = numBuffers;
        // this.buffSize = buffSize;

        // buffSize serves as the initial size of the memory pool as per the
        // spec document of the project
        memManager = new MemoryManager(buffSize);

        // initialize the hash table
        hashTable = new HashTable(hashSlots);
    }

    /**
     * Add a record to the database if no record with the id value is
     * already there.
     * 
     * @param recordSpec A string that has id, type and name of the record to be
     *                   inserted.
     */
    public void addRecord(String recordSpec) {
        // a DNA record is made of three parts: long key, int type, and string
        // sequence of DNA code
        Scanner sc = new Scanner(recordSpec);

        // parse the key of the record
        long key = sc.nextLong();

        // handle duplicate additions
        if (hashTable.get(key) != null) {
            System.out.println(
                    key + " duplicates a record already in the key database.");
            // close the scanner since we are about to return
            sc.close();
            return;
        }

        // parse the type of the record
        int type = sc.nextInt();

        // parse the sequence of DNA code
        String dnaCode = sc.next();
        // done with the scanner
        sc.close();

        // serializing the record to store in the memory pool
        byte[] recordData = serializeDNARecord(key, type, dnaCode);

        // store recordData in the memory pool and obtain a MemoryHandle
        // also need to track if the memory pool is expanded in the process
        int poolSizeBefore = memManager.getPoolSize();
        MemoryHandle recordHandle = memManager.storeBytes(recordData);
        int poolSizeAfter = memManager.getPoolSize();
        if (poolSizeAfter > poolSizeBefore) {
            System.out.println(
                    "Memory pool expanded to be " + poolSizeAfter + " bytes.");
        }

        if (hashTable.isHalfFull()) {
            hashTable.doubleTableSize();
            System.out.println("Hash table size doubled to "
                    + hashTable.getSize() + " slots.");
        }

        // store the key and the handle in the hash table
        hashTable.insert(key, recordHandle);
        System.out.println(key + " has been added to the database.");
    }

    /**
     * Delete a record from the database if it exists.
     *
     * @param recordId The id of the record to be deleted.
     */
    public void deleteRecord(String recordId) {
        // parse the key from the recordId
        Scanner sc = new Scanner(recordId);
        long key = sc.nextLong();
        sc.close();

        // delete the object from the database and get a reference to it
        Object deleted = hashTable.delete(key);

        if (deleted == null) {
            System.out.println(key + " not deleted because it does not"
                    + " exist in the database.");
        }
        else {
            // need to deserialize the record from the memory manager
            MemoryHandle handle = (MemoryHandle) deleted;
            System.out.println(
                    String.format("Record %s deleted from the database.",
                            deserializeMemoryHandle(handle)));
            // free the memory block in the memory pool
            memManager.freeBlock(handle);
        }
    }

    /**
     * Print the three fields id, type, and name associated with a record.
     * 
     * @param recordId The id of the record to be printed.
     */
    public void printRecord(String recordId) {
        // read in the key from the recordId
        Scanner sc = new Scanner(recordId);
        long key = sc.nextLong();
        sc.close();

        Object found = hashTable.get(key);
        if (found == null) {
            System.out.println(key + " does not exist in the database.");
        }
        else {
            DNARecord foundRecord =
                    deserializeMemoryHandle((MemoryHandle) found);
            System.out.println("Record found: " + foundRecord);
        }
    }

    /** Print the hashtable of the current world. */
    public void printHashtable() {
        System.out.println(hashTable);
    }

    /** Print the free blocks in the hashtable of the current world. */
    public void printBlocks() {
        System.out.println(memManager);
    }

    /**
     * Print the block ids of the buffers. Listing is from most recently to
     * least recently used order. Also, print whether a buffer is clean or
     * dirty.
     */
    public void printBuffers() {
        System.out.println("To do");
    }


    /**
     * Serialize the DNA record to convert it into a form that can be stored in
     * the memory pool by the memory manager.
     * 
     * @return Byte array of the serialized data.
     */
    private byte[] serializeDNARecord(long key, int type, String dnaCode) {

        int arraySize = Long.BYTES + Integer.BYTES + dnaCode.length();

        ByteBuffer buffer = ByteBuffer.wrap(new byte[arraySize]);

        // serialize key
        buffer.putLong(key);

        // serialize type
        buffer.putInt(type);

        // serialize DNA code
        buffer.put(dnaCode.getBytes());

        return buffer.array();
    }

    /**
     * Deserialize the DNA record that is in the form of byte array.
     * 
     * @return DNARecord object.
     */
    private DNARecord deserializeBytes(byte[] recordBytes) {
        ByteBuffer buffer = ByteBuffer.wrap(recordBytes);

        // deserialize the key
        long key = buffer.getLong(0);

        // deserialize the value
        int type = buffer.getInt(Long.BYTES);

        // deserialize the String
        int offset = Long.BYTES + Integer.BYTES;
        int stringLength = buffer.capacity() - offset;
        //
        byte[] stringBytes = new byte[stringLength];
        for (int i = 0; i < stringLength; ++i) {
            stringBytes[i] = buffer.get(offset + i);
        }

        // create a DNARecord and return it
        return new DNARecord(key, type, new String(stringBytes));

    }

    /**
     * Retain a DNARecord object using a MemoryHandle.
     * 
     * @return DNARecord object read from the memory pool.
     */
    private DNARecord deserializeMemoryHandle(MemoryHandle handle) {
        // allocate a byte array to read in bytes from the memory manager
        byte[] recordBytes = new byte[handle.getDataSize()];
        // read in the bytes
        memManager.getBytes(recordBytes, handle);
        return deserializeBytes(recordBytes);
    }

    /**
     * A helper class to represent a DNA record. Useful when deserializing data
     * from memory buffer.
     * A DNA data record for this project consists of three fields. A long
     * integer type key used as an id, an Integer type type of the record and a
     * String that is a series of DNA code.
     * 
     * @author  Bimal Gaudel
     * 
     * @version Mar 25, 2020
     */
    private class DNARecord {
        /** The key of the record. */
        private long key;

        /** The type of the record. */
        private int type;

        /** The DNA code sequence. */
        private String dnaCode;

        /** Construct the DNARecord object. */
        DNARecord(long key, int type, String dnaCode) {
            this.key = key;
            this.type = type;
            this.dnaCode = dnaCode;
        }

        /**
         * Getter of the key field.
         * 
         * @return The key of the record: a long integer type.
         */
        public long getKey() {
            return key;
        }

        /**
         * Getter of the type field.
         * 
         * @return Type of the record: an integer.
         */
        public int getType() {
            return type;
        }

        /**
         * Getter of the DNA code field.
         * 
         * @return String of letters that make up the DNA code sequence.
         */
        public String getCode() {
            return dnaCode;
        }

        /**
         * Get a string representation of the record.
         * 
         * @return A string representation of the record.
         */
        @Override
        public String toString() {
            return getKey() + ", " + getType() + ", |" + getCode() + "|";
        }
    }
}
