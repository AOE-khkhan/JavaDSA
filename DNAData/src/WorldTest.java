import student.TestCase;

/**
 * Test the World class.
 * 
 * @author  Bimal Gaudel
 * 
 * @version Mar 25, 2020
 */
public class WorldTest extends TestCase {
    /** A World object used for testing. */
    private World world;

    /** Sets up the tests that follow. */
    public void setUp() {
        // 5 buffers
        // each of 32 bytes memory pool
        // using a hashtable with 10 slots
        world = new World(5, 32, 10);
    }

    /** Test the addRecord method. */
    public void testAddRecord() {
        String expectedOutput = "\nTesting deleteRecord\n--------------\n";
        System.out.print(expectedOutput);

        world.addRecord("1234512345678 17 ATGC");
        expectedOutput += "1234512345678 has been added to the database.\n";

        world.addRecord("1221 11 UAG");
        expectedOutput += "1221 has been added to the database.\n";

        world.addRecord("1221 12 AUG");
        expectedOutput +=
                "1221 duplicates a record already in the key database.\n";

        assertFuzzyEquals(systemOut().getHistory(), expectedOutput);
    }

    /** Test the deleteRecord method. */
    public void testDeleteRecord() {
        String expectedOutput = "\nTesting addRecord\n--------------\n";
        System.out.print(expectedOutput);

        world.addRecord("1221 11 UAG");
        expectedOutput += "1221 has been added to the database.\n";

        world.deleteRecord("1221");
        expectedOutput += "Record 1221, 11, |UAG|"
                + " has been deleted from the database.\n";

        world.deleteRecord("1121");
        expectedOutput +=
                "1121 not deleted because it does not exist in the database.\n";
        assertFuzzyEquals(systemOut().getHistory(), expectedOutput);
    }

    /** Test the printRecord method. */
    public void testPrintRecord() {
        String expectedOutput = "\nTesting printRecord\n--------------\n";
        System.out.print(expectedOutput);

        world.addRecord("1221 11 UAG");
        expectedOutput += "1221 has been added to the database.\n";

        world.printRecord("1221");
        expectedOutput += "Record found: 1221, 11, |UAG|\n";

        world.printRecord("1121");
        expectedOutput += "1121 not printed because "
                + "it does not exist in the database.\n";

        assertFuzzyEquals(systemOut().getHistory(), expectedOutput);

    }

    /** Get code coverage for the unexecuted methods. */
    public void testGetCodeCoverage() {
        String expectedOutput = "\nGetting code coverage\n--------------\n";
        System.out.print(expectedOutput);

        world.printHashtable();
        expectedOutput += new HashTable(10).toString() + "\n";

        world.printBlocks();
        expectedOutput += new MemoryManager(32).toString() + "\n";

        world.printBuffers();
        expectedOutput += "To do\n";

        world.addRecord("1 1 ATG"); // 15 bytes
        expectedOutput += "1 has been added to the database.\n";

        world.addRecord("2 1 ATG"); // 15 bytes
        expectedOutput += "2 has been added to the database.\n";

        world.addRecord("3 1 ATG"); // 15 bytes
        expectedOutput += "Memory pool expanded to be 64 bytes.\n";
        expectedOutput += "3 has been added to the database.\n";

        world.addRecord("4 1 ATG"); // 15 bytes
        expectedOutput += "4 has been added to the database.\n";

        world.addRecord("5 1 ATG"); // 15 bytes
        expectedOutput += "Memory pool expanded to be 128 bytes.\n";
        expectedOutput += "5 has been added to the database.\n";

        world.addRecord("6 1 ATG"); // 15 bytes
        expectedOutput += "Hash table size doubled to 20 slots.\n";
        expectedOutput += "6 has been added to the database.\n";

        assertFuzzyEquals(systemOut().getHistory(), expectedOutput);
    }
}
