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
        assertEquals(box.getOrig()[Box.DIM_X], 1);
        assertEquals(box.getOrig()[Box.DIM_Y], 2);
        assertEquals(box.getOrig()[Box.DIM_Z], 3);
        //
        assertEquals(box.getWidths()[Box.DIM_X], 4);
        assertEquals(box.getWidths()[Box.DIM_Y], 6);
        assertEquals(box.getWidths()[Box.DIM_Z], 8);
    }


    /** Test has point method. */
    public void testHasPoint() {
        Box box1 = new Box(new int[] {0, 0, 0, 256, 256, 256});

        assertTrue(box1.hasPoint(new int[] {0, 0, 0}));
        assertTrue(box1.hasPoint(new int[] {100, 100, 100}));
        assertTrue(box1.hasPoint(new int[] {100, 0, 0}));
        assertTrue(box1.hasPoint(new int[] {0, 100, 0}));
        assertTrue(box1.hasPoint(new int[] {0, 0, 100}));

        assertFalse(box1.hasPoint(new int[] {256, 256, 256}));
        assertFalse(box1.hasPoint(new int[] {256, 0, 0}));
        assertFalse(box1.hasPoint(new int[] {0, 256, 0}));
        assertFalse(box1.hasPoint(new int[] {0, 0, 256}));

        assertFalse(box1.hasPoint(new int[] {-1,  0,  0}));
        assertFalse(box1.hasPoint(new int[] { 0, -1,  0}));
        assertFalse(box1.hasPoint(new int[] { 0,  0, -1}));

        assertFalse(box1.hasPoint(new int[] {300, 0, 0}));
        assertFalse(box1.hasPoint(new int[] {0, 300, 0}));
        assertFalse(box1.hasPoint(new int[] {0, 0, 300}));
    }


    /** Test intersects method. */
    public void testIntersects() {
        Box box1 = new Box(new int[] {0, 0, 0, 256, 256, 256});
        Box box2 = new Box(new int[] {0, 0, 0, 256, 256, 256});

        assertTrue(box1.intersects(box1));
        assertTrue(box1.intersects(box2));

        box2 = new Box(new int[] {64, 64, 64, 128, 128, 128});
        assertTrue(box1.intersects(box2));

        box2 = new Box(new int[] {256, 256, 256, 256, 256, 256});
        assertFalse(box1.intersects(box2));

        box2 = new Box(new int[] {512, 0, 0, 256, 256, 256});
        assertFalse(box1.intersects(box2));

        box2 = new Box(new int[] {0, 512, 0, 256, 256, 256});
        assertFalse(box1.intersects(box2));

        box2 = new Box(new int[] {0, 0, 512, 256, 256, 256});
        assertFalse(box1.intersects(box2));
    }


    /** Test getIntersection method. */
    public void testGetIntersection() {
        Box box1 = new Box(new int[] {0, 0, 0, 512, 512, 512});
        Box box2 = new Box(new int[] {128, 128, 128, 640, 640, 640});
        Box isec = box1.getIntersection(box2);
        //@formatter:off
        String expected = String.format("%d, %d, %d, %d, %d, %d",
                                            128, 128, 128,
                                                512 - 128,
                                                512 - 128,
                                                512 - 128);
        //@formatter:on
        assertFuzzyEquals(isec.toString(), expected);
    }


    /** Test the BinBox class. */
    public void testBinBox() {
        final int firstHalf = 0;
        final int secondHalf = 1;
        BinBox binBox = new BinBox(1024);
        assertFuzzyEquals(binBox.toString(), "0, 0, 0, 1024, 1024, 1024");

        // split accross x axis and choose the first half
        binBox = binBox.split(Box.DIM_X, firstHalf);
        assertFuzzyEquals(binBox.toString(), "0, 0, 0, 512, 1024, 1024");

        // split accross x axis and choose the second half
        binBox = binBox.split(Box.DIM_X, secondHalf);
        assertFuzzyEquals(binBox.toString(), "256, 0, 0, 256, 1024, 1024");

        // split accross z axis and choose the second half
        binBox = binBox.split(Box.DIM_Z, secondHalf);
        assertFuzzyEquals(binBox.toString(), "256, 0, 512, 256, 1024, 512");
    }
}
