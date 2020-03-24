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
                "<buffer-size> param must be a positive power of two. Got: |20|";
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

    /** Test when an unrecognized commands are passed. */
    public void testInvalidCommand() {
        String[] params = new String[] {"10", "16", "10", "src/input.txt"};
        DNAData.main(params);

        String expectedOutput = "Unrecognized input: |laugh|\n";
        expectedOutput += "Invalid target |nothing| for printing!\n";
        assertFuzzyEquals(systemOut().getHistory(), expectedOutput);
    }
}
