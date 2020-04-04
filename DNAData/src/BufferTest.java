import student.TestCase;

/**
 * Testing Buffer class. Mostly intended to get code coverage.
 * 
 * @author  Bimal Gaudel
 * 
 * @version Apr 03, 2020
 */
public class BufferTest extends TestCase {
    /** Get code coverage for the compareTo method. */
    public void testComparator() {
        Buffer buf1 = new Buffer(0, 16);
        Buffer buf2 = new Buffer(1, 16);
        Buffer buf3 = new Buffer(1, 16);

        assertEquals(buf1.compareTo(buf2), -1);
        assertEquals(buf2.compareTo(buf1), 1);
        assertEquals(buf2.compareTo(buf3), 0);
    }

    /** Get code coverage. */
    public void testGetCoverage() {
        Buffer buf1 = new Buffer(0, 16);
        assertEquals(buf1.getBlockId(), 0);

        buf1.setBlockId(1);
        assertEquals(buf1.getBlockId(), 1);

        buf1.markDirty();
        buf1.markClean();
        assertFalse(buf1.isDirty());

        assertEquals(buf1.getDataBytes().length, 16);

        buf1.insert(new byte[4], 4, 0, 0);

        byte[] storage = new byte[4];
        buf1.getBytes(storage, 4, 0, 0);

        assertFuzzyEquals(buf1.toString(), "1: dirty");
    }
}
