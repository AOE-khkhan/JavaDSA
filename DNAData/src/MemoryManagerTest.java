import student.TestCase;

/**
 * Test the MemoryManager class.
 * 
 * @author  Bimal Gaudel
 * 
 * @version Mar 24, 2020
 */
public class MemoryManagerTest extends TestCase {
    /** An instance of MemoryManger used for testing. */
    private MemoryManager manager;

    /** Sets up the tests that follow. */
    public void setUp() {
        manager = new MemoryManager(32);
    }

    /** Test the constructor of the MemoryManager class. */
    public void testConstructor() {
        assertNotNull(this.manager);
    }

    /** Test the storeBytes method. */
    public void testRecordBytes() {
        // initially there was a single 32 sized blocks starting at 0
        assertFuzzyEquals(manager.toString(), "32: 0");

        // some data we will store
        byte[] data = new byte[2];

        // add data and receive the handle
        MemoryHandle handle = manager.storeBytes(data);

        // the memory block has now split up into multiple chunks
        assertFuzzyEquals(manager.toString(),
                "2: 2\n" + "4: 4\n" + "8: 8\n" + "16: 16");

        // the handle should start at zero and have a size of two
        assertEquals(handle, new MemoryHandle(0, 2, 2));

        // let's add another size two data
        handle = manager.storeBytes(data);
        // notice how the first available size two block is now used up
        assertFuzzyEquals(manager.toString(), "4: 4\n" + "8: 8\n" + "16: 16");
        // the handle now starts at pos 2 as expected
        assertEquals(handle, new MemoryHandle(2, 2, 2));

        // let's add another size two data
        handle = manager.storeBytes(data);
        // notice how the size 4 block is now split up
        assertFuzzyEquals(manager.toString(), "2: 6\n" + "8: 8\n" + "16: 16");
        // the handle now starts at pos 4 as expected
        assertEquals(handle, new MemoryHandle(4, 2, 2));

        // let's add another data but size 33 this time
        // this should expand the whole pool size twice
        // because as of right now the largest free block
        // is of size 16, we double once and get another
        // free block of size 32, but that is not enough
        // to store a data of size 33, so we double in
        // pool size again, and get a free block of size
        // 64, then that size 64 block is used for storage
        handle = manager.storeBytes(new byte[33]);
        assertFuzzyEquals(manager.toString(),
                "2: 6\n" + "8: 8\n" + "16: 16\n" + "32: 32");
        // assertFuzzyEquals(handle.toString(), "64: 64 33");
    }

    /** Test the getBytes method. */
    public void testGetBytes() {
        String course = "COMP 5040";
        byte[] data = course.getBytes();
        MemoryHandle handle = manager.storeBytes(data);
        byte[] retainedData = new byte[data.length];
        manager.getBytes(retainedData, handle);
        String retainedString = new String(retainedData);
        assertEquals(course, retainedString);

        course = "abracadabra";
        data = course.getBytes();
        handle = manager.storeBytes(data);
        retainedData = new byte[data.length];
        manager.getBytes(retainedData, handle);
        retainedString = new String(retainedData);
        assertEquals(course, retainedString);

        course = "aaaaaaaaabbbbbbbbbbbbbbbbbm"
                + "mmmmmmmmmmmmmmmmmnnnnnnnnnnnnnnn"
                + "nooooooooooooooopppppppppppqqqqq" + "qqqqqqllllllllll"
                + "zzzzzzzzzzzzzzzzzzzzzzzzz";
        data = course.getBytes();
        handle = manager.storeBytes(data);
        retainedData = new byte[data.length];
        manager.getBytes(retainedData, handle);
        retainedString = new String(retainedData);
        assertEquals(course, retainedString);

        // get code coverage
        data = "hello".getBytes();
        handle = manager.storeBytes(data);
        retainedData = new byte[data.length];
        MemoryHandle newHandle = new MemoryHandle(handle.getPos(),
                handle.getBlockSize(), handle.getDataSize() - 1);
        manager.getBytes(retainedData, newHandle);
        retainedString = new String(retainedData);
        assertFuzzyEquals(retainedString, "hell");
        //
    }

    /** Test the freeBlock method. */
    public void testFreeBlock() {
        // initially there was a single 32 sized blocks starting at 0
        assertFuzzyEquals(manager.toString(), "32: 0");

        // add data and receive the handle
        MemoryHandle handle = manager.storeBytes(new byte[2]);

        // the memory block has now split up into multiple chunks
        assertFuzzyEquals(manager.toString(),
                "2: 2\n" + "4: 4\n" + "8: 8\n" + "16: 16\n");

        // let's now free up the storage of handle
        manager.freeBlock(handle);
        // now the blocks should be restored
        assertFuzzyEquals(manager.toString(), "32: 0");

        // adding more data
        manager.storeBytes(new byte[2]);
        // the memory block has now split up into multiple chunks
        assertFuzzyEquals(manager.toString(),
                "2: 2\n" + "4: 4\n" + "8: 8\n" + "16: 16\n");

        // adding size 4 data
        handle = manager.storeBytes(new byte[4]);
        //
        assertFuzzyEquals(manager.toString(), "2: 2\n" + "8: 8\n" + "16: 16\n");

        // freeing up the space associated to handle
        manager.freeBlock(handle);
        // memory block should be restored in the state
        // it was right after adding the size 2 data
        assertFuzzyEquals(manager.toString(),
                "2: 2\n" + "4: 4\n" + "8: 8\n" + "16: 16\n");
    }

    /** Test the method getPoolSize. */
    public void testGetPoolSize() {
        assertEquals(32, manager.getPoolSize());
    }

    /** Test whether the pool size gets expanded as expected. */
    public void testPoolSizeExpansion() {
        int initPoolSize = manager.getPoolSize();

        // started with free 32 byte block, and storing a 32 byte data
        // no expansion should be observed
        MemoryHandle handle = manager.storeBytes(new byte[32]);
        assertEquals(initPoolSize, manager.getPoolSize());

        // freeing up the stored block
        // no expansion should be observed
        manager.freeBlock(handle);
        assertEquals(initPoolSize, manager.getPoolSize());

        // freed-up space should be reclaimed
        // no expansion should be observed
        handle = manager.storeBytes(new byte[32]);
        assertEquals(initPoolSize, manager.getPoolSize());

        manager.freeBlock(handle);
        manager.storeBytes(new byte[33]);
        // pool size should now be doubled
        assertEquals(initPoolSize * 2, manager.getPoolSize());

        manager = new MemoryManager(32);
        manager.storeBytes("Death Note".getBytes()); // 10 bytes
        manager.storeBytes("Genre<SEP>Anime".getBytes()); // 15 bytes
        manager.storeBytes("Can You Handle?".getBytes()); // 15 bytes
        assertEquals(manager.getPoolSize(), 64);
    }

    /** Test toString method when the memory pool is full. */
    public void testToStringWhenFull() {
        manager.storeBytes(new byte[32]);
        assertFuzzyEquals(manager.toString(), "No free blocks are available.");
    }

    /** Verify the memory manager works as expected. */
    public void testComplexActions() {
        assertFuzzyEquals(manager.toString(), "32: 0\n");

        MemoryHandle handle20 = manager.storeBytes(new byte[2]);
        assertFuzzyEquals(manager.toString(),
                "2: 2\n" + "4: 4\n" + "8: 8\n" + "16: 16\n");

        MemoryHandle handle88 = manager.storeBytes(new byte[8]);
        assertFuzzyEquals(manager.toString(), "2: 2\n" + "4: 4\n" + "16: 16\n");

        MemoryHandle handle816 = manager.storeBytes(new byte[8]);
        assertFuzzyEquals(manager.toString(), "2: 2\n" + "4: 4\n" + "8: 24\n");

        MemoryHandle handle824 = manager.storeBytes(new byte[8]);
        assertFuzzyEquals(manager.toString(), "2: 2\n" + "4: 4\n");

        manager.freeBlock(handle88);
        assertFuzzyEquals(manager.toString(), "2: 2\n" + "4: 4\n" + "8: 8\n");

        manager.freeBlock(handle816);
        assertFuzzyEquals(manager.toString(),
                "2: 2\n" + "4: 4\n" + "8: 8 16\n");

        manager.freeBlock(handle824);
        assertFuzzyEquals(manager.toString(),
                "2: 2\n" + "4: 4\n" + "8: 8\n" + "16: 16\n");

        manager.freeBlock(handle20);
        assertFuzzyEquals(manager.toString(), "32: 0\n");
    }
}
