import java.io.File;
import java.util.Scanner;

import student.TestCase;

/**
 * Test the DNAData class.
 * 
 * @author  Bimal Gaudel
 * 
 * @version Mar 19, 2020
 */
public class DNADataTest extends TestCase {
    /** Test when bad arguments are passed. */
    public void testBadArguments() {
        String[] params = new String[0];

        DNAData.main(params);
        String expectedOutput = "Bad argument count! Usage: \n"
                + "DNAData <number-of-buffers> <buffer-size> "
                + "<initial-hash-size> <command-file>";
        assertFuzzyEquals(expectedOutput, systemOut().getHistory());
    }

    /** Test when the buffer size is not a positive power of two. */
    public void testBadBufferSize() {
        // 20 is not a power of two
        String[] params = new String[] {"10", "20", "10", "input.txt"};
        DNAData.main(params);
        String expectedOutput =
                "<buffer-size> param must be a positive power of two."
                        + " Got: |20|\n";

        // -1 is not a power of two
        params = new String[] {"10", "-1", "10", "input.txt"};
        DNAData.main(params);
        expectedOutput += "<buffer-size> param must be "
                + "a positive power of two." + " Got: |-1|\n";

        assertFuzzyEquals(expectedOutput, systemOut().getHistory());
    }

    /** Test when the command file provided doesn't exist. */
    public void testInvalidCommandFile() {
        String[] params = new String[] {"10", "16", "10", "invalid.txt"};

        String expectedOutput = "";
        try {
            new Scanner(new File("invalid.txt")).close();
        }
        catch (Exception e) {
            expectedOutput = e.getMessage();
        }

        DNAData.main(params);
        assertFuzzyEquals(systemOut().getHistory(), expectedOutput);

    }

    /** Get the code coverage for unexecuted code. */
    public void testGetCodeCoverage() {
        //
        DNAData aSession = new DNAData();
        assertNotNull(aSession);

        String[] params = new String[] {"5", "16", "5", "src/input.txt"};
        DNAData.main(params);

        // 'laugh'
        String expectedOutput = "Unrecognized input: |laugh|\n";

        // 'print nothing'
        expectedOutput += "Invalid target |nothing| for printing!\n";

        // 'print hashtable'
        expectedOutput += "Total records: 0\n";

        // 'print blocks'
        expectedOutput += "16: 0\n";

        // 'print buffers'
        //@formatter:off
        expectedOutput += "-1 clean\n"
                        + "-1 clean\n"
                        + "-1 clean\n"
                        + "-1 clean\n"
                        + "-1 clean\n"
                        + "Cache Hits: 0\n"
                        + "Disk Reads: 0\n"
                        + "Disk Writes: 0\n";
        //@formatter:on

        // 'print record 1234123456789'
        expectedOutput += "1234123456789 not printed because "
                + "it does not exist in the database.\n";

        // 'delete record 1234123456789'
        expectedOutput += "1234123456789 not deleted because "
                + "it does not exist in the database.\n";
        // command received is
        // 'add 1234123456789 17 AGCDAAAAAGCDAAGGGGGCCCCCDDDDDDDDD'

        expectedOutput += "Memory pool expanded to be 32 bytes.\n"
                + "Memory pool expanded to be 64 bytes.\n"
                + "1234123456789 has been added to the database.\n";

        // 'print record 1234123456789'
        expectedOutput += "Record found: "
                + "1234123456789, 17, |AGCDAAAAAGCDAAGGGGGCCCCCDDDDDDDDD|\n";

        // 'print hashtable'
        expectedOutput += "1234123456789: 1\n" + "Total records: 1\n";

        // 'print blocks'
        expectedOutput += "No free blocks are available.\n";

        // 'print buffers'
        //@formatter:off
        expectedOutput += "2 dirty\n"
                        + "1 dirty\n"
                        + "0 dirty\n"
                        + "-1 clean\n"
                        + "-1 clean\n"
                        + "Cache Hits: 3\n"
                        + "Disk Reads: 0\n"
                        + "Disk Writes: 0\n";
        //@formatter:on

        // 'delete 1234123456789'
        expectedOutput += "Record "
                + "1234123456789, 17, |AGCDAAAAAGCDAAGGGGGCCCCCDDDDDDDDD|"
                + " has been deleted from the database.\n";

        // 'add 1234123456789 17 AGCD'
        expectedOutput += "1234123456789 has been added to the database.\n";

        // 'print blocks'
        expectedOutput += "16: 16\n 32: 32\n";

        // 'print buffers'
        //@formatter:off
        expectedOutput += "0 dirty\n"
                        + "2 dirty\n"
                        + "1 dirty\n"
                        + "-1 clean\n"
                        + "-1 clean\n"
                        + "Cache Hits: 7\n"
                        + "Disk Reads: 0\n"
                        + "Disk Writes: 0\n";
        //@formatter:on

        // 'add 1 1 A'
        expectedOutput += "1 has been added to the database.\n"
                + "Hash table size doubled to 10 slots.\n";

        // 'add 2 2 B'
        expectedOutput += "2 has been added to the database.\n";

        assertFuzzyEquals(systemOut().getHistory(), expectedOutput);
    }
}
