import student.TestCase;

/**
 * @author  Bimal Gaudel
 * 
 * @version Apr 11, 2020
 */
public class AirControlTest extends TestCase {
    /** Get code coverage for AirControl class. */
    public void testGetCodeCoverage() {
        AirControl recstore = new AirControl();
        assertNotNull(recstore);
        AirControl.main(new String[] {"invalid-file.txt"});
        AirControl.main(new String[] {"src/input.txt"});
    }
}