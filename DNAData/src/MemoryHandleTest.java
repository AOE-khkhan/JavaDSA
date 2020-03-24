import student.TestCase;

/**
 * Test the MemoryHandle class.
 * 
 * @author  Bimal Gaudel
 * 
 * @version Mar 24, 2020
 */
public class MemoryHandleTest extends TestCase {
    /** A MemoryHandle object used for testing. */
    private MemoryHandle aHandle;

    /** Sets up the following tests. */
    public void setUp() {
        aHandle = new MemoryHandle(1024, 64, 64);
    }

    /** Test the position getter. */
    public void testGetPos() {
        assertEquals(1024, aHandle.getPos());
    }

    /** Test the size getters. */
    public void testSizeGetters() {
        MemoryHandle handle = new MemoryHandle(1024, 64, 55);
        assertEquals(64, handle.getBlockSize());
        assertEquals(55, handle.getDataSize());
    }

    /** Test equals method. */
    public void testEquals() {
        MemoryHandle handle1 = new MemoryHandle(0, 2, 2);
        MemoryHandle handle2 = new MemoryHandle(0, 2, 2);
        assertEquals(handle1, handle2);
        assertEquals(handle1, handle1);
    }

    /** Test toString method. */
    public void testToString() {
        assertEquals(aHandle.toString(), "64: 1024 64");
        aHandle = new MemoryHandle(1024, 64, 33);
        assertEquals(aHandle.toString(), "64: 1024 33");
    }

    /** Test the compareTo method. */
    public void testCompareTo() {
        MemoryHandle handle1 = new MemoryHandle(0, 2, 2);
        MemoryHandle handle2 = new MemoryHandle(0, 2, 2);
        MemoryHandle handle3 = new MemoryHandle(8, 4, 4);
        assertEquals(handle1.compareTo(handle2), 0);
        assertEquals(handle1.compareTo(handle3), -1);
        assertEquals(handle3.compareTo(handle2), 1);
    }
}
