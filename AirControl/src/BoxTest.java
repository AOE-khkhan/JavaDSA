import student.TestCase;

/**
 * Test the Box class.
 * 
 * @author  Bimal Gaudel.
 * 
 * @version Apr 10, 2020
 */
public class BoxTest extends TestCase {
    /** A Box object used for testing. */
    private Box box;

    /** Sets up the tests that follow. */
    public void setUp() {
        box = new Box(new int[] {1, 2, 3, 4, 6, 8});
    }

    /** Test the getters. **/
    public void testGetters() {
        assertEquals(box.getOrig()[Box.dimX], 1);
        assertEquals(box.getOrig()[Box.dimY], 2);
        assertEquals(box.getOrig()[Box.dimZ], 3);
        //
        assertEquals(box.getWidths()[Box.dimX], 4);
        assertEquals(box.getWidths()[Box.dimY], 6);
        assertEquals(box.getWidths()[Box.dimZ], 8);
    }
}
