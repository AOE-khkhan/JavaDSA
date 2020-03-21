import student.TestCase;

/**
 * Test the DNAData class.
 * 
 * @author  Bimal Gaudel
 * 
 * @version Mar 19, 2020
 */
public class DNADataTest extends TestCase {
    public void testBadArguments() {
        String[] params = new String[0];

        DNAData.main(params);
        String expectedOutput = "Bad argument count! Usage: \n"
                + "DNAData <number-of-buffers> <buffer-size> "
                + "<initial-hash-size> <command-file>";
        assertFuzzyEquals(expectedOutput, systemOut().getHistory());
    }
}
