import student.TestCase;

import java.io.File;
import java.io.RandomAccessFile;

/**
 * Test the class BufferPool.
 * 
 * @author  Bimal Gaudel
 * 
 * @version Apr 03, 2020
 */
public class BufferPoolTest extends TestCase {

    /** Random access file used for disk I/O. */
    private RandomAccessFile raFile;

    /** File used for I/O. */
    private File ioFile;

    /** A buffer pool used for testing. */
    private BufferPool pool;

    /** Sets up the tests that follow. */
    public void setUp() {
        try {
            ioFile = new File(".diskIO.raw");
            ioFile.delete();
            raFile = new RandomAccessFile(ioFile, "rw");
            //
            raFile.setLength(16);
            pool = new BufferPool(5, 16, raFile);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /** Test the method insertBytes. */
    public void testInsertBytes() {
        String expectedOutput = "\nTesting insertBytes\n-------------\n";
        System.out.print(expectedOutput);

        doubleRAfileSize(); // new size 32 bytes
        doubleRAfileSize(); // new size 64 bytes
        pool.insertBytes(new byte[45], new MemoryHandle(0, 64, 45));
        // file is now full

        System.out.println(pool);
        //@formatter:off
        expectedOutput += "2 dirty\n"
                        + "1 dirty\n"
                        + "0 dirty\n"
                        + "-1 clean\n"
                        + "-1 clean\n"
                        + "Cache Hits: 0\n"
                        + "Disk Reads: 0\n"
                        + "Disk Writes: 0\n";
        //@formatter:on

        doubleRAfileSize(); // new size 128 bytes
        pool.insertBytes(new byte[45], new MemoryHandle(64, 64, 45));

        System.out.println(pool);
        //@formatter:off
        expectedOutput += "6 dirty\n"
                        + "5 dirty\n"
                        + "4 dirty\n"
                        + "2 dirty\n"
                        + "1 dirty\n"
                        + "Cache Hits: 0\n"
                        + "Disk Reads: 0\n"
                        + "Disk Writes: 1\n";
        //@formatter:on

        pool.insertBytes(new byte[8], new MemoryHandle(48, 8, 8));

        System.out.println(pool);
        //@formatter:off
        expectedOutput += "3 dirty\n"
                        + "6 dirty\n"
                        + "5 dirty\n"
                        + "4 dirty\n"
                        + "2 dirty\n"
                        + "Cache Hits: 0\n"
                        + "Disk Reads: 1\n"
                        + "Disk Writes: 2\n";
        //@formatter:on

        assertFuzzyEquals(systemOut().getHistory(), expectedOutput);

        deleteIOfile();
        closeRAfile();
    }

    /** Test the method getBytes. */
    public void testGetBytes() {
        String expectedOutput = "\nTesting getBytes\n-------------\n";
        System.out.print(expectedOutput);
        doubleRAfileSize();
        doubleRAfileSize();
        byte[] data = "abracadabra".getBytes();
        MemoryHandle handle = new MemoryHandle(0, 16, data.length);
        pool.insertBytes(data, handle);

        byte[] retrievedData = new byte[data.length];
        pool.getBytes(retrievedData, handle);
        assertEquals(new String(retrievedData), "abracadabra");

        deleteIOfile();
        closeRAfile();
    }

    /** Get code coverage. */
    public void testGetCoverage() {
        String expectedOutput = "\nGetting code coverage\n-------------\n";
        System.out.print(expectedOutput);
        doubleRAfileSize();
        doubleRAfileSize();
        MemoryHandle handle = new MemoryHandle(0, 64, 64);
        pool.insertBytes(new byte[64], handle);
        byte[] storage = new byte[64];
        pool.getBytes(storage, handle);
        assertNotNull(pool);
    }

    /** Double the size of the disk file used for I/O. */
    private void doubleRAfileSize() {
        try {
            raFile.setLength(raFile.length() * 2);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /** Close the random access file. */
    private void closeRAfile() {
        try {
            raFile.close();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /** Close the File object accompanying the random access file. */
    private void deleteIOfile() {
        try {
            ioFile.delete();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
