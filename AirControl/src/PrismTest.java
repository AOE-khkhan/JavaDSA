import student.TestCase;

/**
 * Test the Prism class.
 * 
 * @author  Bimal Gaudel.
 * 
 * @version Apr 10, 2020
 */
public class PrismTest extends TestCase {
    /** A Prism object used for testing. */
    private Prism prism;

    /** Sets up the tests that follow. */
    public void setUp() {
        prism = new Prism(new int[] {1, 2, 3, 4, 6, 8});
    }

    /** Test the getters. **/
    public void testGetters() {
        assertEquals(prism.getOrigX(), 1);
        assertEquals(prism.getOrigY(), 2);
        assertEquals(prism.getOrigZ(), 3);
        //
        assertEquals(prism.getWidthX(), 4);
        assertEquals(prism.getWidthY(), 6);
        assertEquals(prism.getWidthZ(), 8);
    }
}
