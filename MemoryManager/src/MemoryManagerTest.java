import student.TestCase;

/**
 * Test the MemoryManager class.
 * 
 * @author Bimal Gaudel
 * @version 2020-02-09
 */
public class MemoryManagerTest extends TestCase {
  /** An instance of MemoryManger used for testing. */
  private MemoryManager aMemManager;

  /** Sets up the tests that follow. */
  public void setUp() {
    this.aMemManager = new MemoryManager(5);
  }

  /** Test the constructor of the MemoryManager class. */
  public void testConstructor() {
    assertNotNull(this.aMemManager);
  }

  /** Test the storeBytes method. */
  public void testRecordBytes() {
    // intially there was a single 32 sized blocks starting at 0
    assertFuzzyEquals(aMemManager.toString(), "32: 0");

    // some data we will store
    byte[] data = new byte[2];

    // add data and receive the handle
    MemoryHandle handle = aMemManager.storeBytes(data);

    // the memory block has now split up into multiple chunks
    assertFuzzyEquals(aMemManager.toString(),
        "2: 2\n" + "4: 4\n" + "8: 8\n" + "16: 16\n");

    // the handle should start at zero and have a size of two
    assertFuzzyEquals(handle.toString(), "2: 0 2");

    // let's add another size two data
    handle = aMemManager.storeBytes(data);
    // notice how the first available size two block is now used up
    assertFuzzyEquals(aMemManager.toString(), "4: 4\n" + "8: 8\n" + "16: 16\n");
    // the handle now starts at pos 2 as expected
    assertFuzzyEquals(handle.toString(), "2: 2 2");

    // let's add another size two data
    handle = aMemManager.storeBytes(data);
    // notice how the size 4 block is now split up
    assertFuzzyEquals(aMemManager.toString(), "2: 6\n" + "8: 8\n" + "16: 16\n");
    // the handle now starts at pos 4 as expected
    assertFuzzyEquals(handle.toString(), "2: 4 2");

    // let's add another data but size 33 this time
    // this should expand the whole poolsize twice
    // because as of right now the largest free block
    // is of size 16, we double once and get another
    // free block of size 32, but that is not enough
    // to store a data of size 33, so we double in
    // poolsize again, and get a free block of size
    // 64, then that size 64 block is used for storage
    handle = aMemManager.storeBytes(new byte[33]);
    assertFuzzyEquals(aMemManager.toString(),
        "2: 6\n" + "8: 8\n" + "16: 16\n" + "32: 32\n");
    assertFuzzyEquals(handle.toString(), "64: 64 33");

  }

  /** Test the getBytes method. */
  public void testGetBytes() {
    String course = "COMP 5040";
    byte[] data = course.getBytes();
    MemoryHandle handle = aMemManager.storeBytes(data);
    byte[] retainedData = new byte[data.length];
    aMemManager.getBytes(retainedData, handle);
    String retainedString = new String(retainedData);
    assertEquals(course, retainedString);

    course = "abracadabra";
    data = course.getBytes();
    handle = aMemManager.storeBytes(data);
    retainedData = new byte[data.length];
    aMemManager.getBytes(retainedData, handle);
    retainedString = new String(retainedData);
    assertEquals(course, retainedString);

    course = "aaaaaaaaabbbbbbbbbbbbbbbbbm" + "mmmmmmmmmmmmmmmmmnnnnnnnnnnnnnnn"
        + "nooooooooooooooopppppppppppqqqqq" + "qqqqqqllllllllll"
        + "zzzzzzzzzzzzzzzzzzzzzzzzz";
    data = course.getBytes();
    handle = aMemManager.storeBytes(data);
    retainedData = new byte[data.length];
    aMemManager.getBytes(retainedData, handle);
    retainedString = new String(retainedData);
    assertEquals(course, retainedString);
  }

  /** Free the freeBlock method. */
  public void testFreeBlock() {
    // intially there was a single 32 sized blocks starting at 0
    assertFuzzyEquals(aMemManager.toString(), "32: 0");

    // some data we will store
    byte[] data = new byte[2];

    // add data and receive the handle
    MemoryHandle handle = aMemManager.storeBytes(data);

    // the memory block has now split up into multiple chunks
    assertFuzzyEquals(aMemManager.toString(),
        "2: 2\n" + "4: 4\n" + "8: 8\n" + "16: 16\n");

    // let's now free up the storage of handle
    aMemManager.freeBlock(handle);
    // now the blocks should be restored
    assertFuzzyEquals(aMemManager.toString(), "32: 0");

    // adding more data
    aMemManager.storeBytes(data);
    // the memory block has now split up into multiple chunks
    assertFuzzyEquals(aMemManager.toString(),
        "2: 2\n" + "4: 4\n" + "8: 8\n" + "16: 16\n");

    // adding size 4 data
    handle = aMemManager.storeBytes(new byte[4]);
    //
    assertFuzzyEquals(aMemManager.toString(), "2: 2\n" + "8: 8\n" + "16: 16\n");

    // freeing up the space associated to handle
    aMemManager.freeBlock(handle);
    // memory block should be restored in the state
    // it was right after adding the size 2 data
    assertFuzzyEquals(aMemManager.toString(),
        "2: 2\n" + "4: 4\n" + "8: 8\n" + "16: 16\n");
  }

  /** Test the method getPoolSize. */
  public void testGetPoolSize() {
    assertEquals(32, aMemManager.getPoolSize());
  }
}
