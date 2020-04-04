import student.TestCase;

/**
 * Test the HelperFunctions class. Mostly intended to get code coverage.
 * 
 * @author  Bimal Gaudel
 * 
 * @version Apr 03, 2020
 */
public class HelperFunctionsTest extends TestCase {
    /** Test the function to compute log2. */
    public void testGetLog2() {
        assertEquals(HelperFunctions.getLog2(16), 4);
        assertEquals(HelperFunctions.getLog2(15), 3);
    }

    /** Test the function to comptue power of two. */
    public void testGetPower2() {
        assertEquals(HelperFunctions.getPower2(0), 1);
        assertEquals(HelperFunctions.getPower2(10), 1024);
    }

    /** Test the function to check if a number is power of two. */
    public void testIsPowerOf2() {
        assertTrue(HelperFunctions.isPowerOf2(2));
        assertTrue(HelperFunctions.isPowerOf2(512));
        assertFalse(HelperFunctions.isPowerOf2(66));
    }

    /** Get code coverage. */
    public void testGetCoverage() {
        HelperFunctions instance = new HelperFunctions();
        assertNotNull(instance);
    }
}
