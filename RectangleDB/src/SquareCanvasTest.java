import student.TestCase;

/**
 * Test the SquareCanvas class.
 * 
 * @author  Bimal Gaudel
 * 
 * @version Feb 2020
 */
public class SquareCanvasTest extends TestCase {
    /** A canvas object used for testing. */
    private SquareCanvas canvas;

    /** Sets up the tests that follow. */
    public void setUp() {
        canvas = new SquareCanvas(0, 0, 1024);
    }

    /** Test the getter methods. */
    public void testGetters() {
        assertEquals(canvas.getX(), 0);
        assertEquals(canvas.getY(), 0);
        assertEquals(canvas.getSize(), 1024);
    }

    /** Test the getQuadrant method. */
    public void testGetQuadrant() {
        assertEquals(canvas.getQuadrant(0), new SquareCanvas(0, 0, 512));
        assertEquals(canvas.getQuadrant(1), new SquareCanvas(512, 0, 512));
        assertEquals(canvas.getQuadrant(2), new SquareCanvas(0, 512, 512));
        assertEquals(canvas.getQuadrant(3), new SquareCanvas(512, 512, 512));
        assertNull(canvas.getQuadrant(SquareCanvas.NUM_QUADRANTS));

        SquareCanvas quadrantSE = canvas.getQuadrant(3);
        assertEquals(quadrantSE.getQuadrant(3),
                new SquareCanvas(768, 768, 256));
    }

    /** Test intersectsRectangle method. */
    public void testIntersectsRectangle() {
        Rectangle rect = new Rectangle(0, 0, 10, 10);

        // (0, 0, 1024) canvas must hold rect
        assertTrue(canvas.intersectsRectangle(rect));

        // rect falls on the NW quadrant
        assertTrue(canvas.getQuadrant(0).intersectsRectangle(rect));
        // but not on the NE quadrant
        assertFalse(canvas.getQuadrant(1).intersectsRectangle(rect));
        // or on the SW quadrant
        assertFalse(canvas.getQuadrant(2).intersectsRectangle(rect));
        // or on the SE quadrant
        assertFalse(canvas.getQuadrant(3).intersectsRectangle(rect));

        rect = new Rectangle(510, 510, 4, 4);

        // rect falls on all four quadrants
        assertTrue(canvas.getQuadrant(0).intersectsRectangle(rect));
        assertTrue(canvas.getQuadrant(1).intersectsRectangle(rect));
        assertTrue(canvas.getQuadrant(2).intersectsRectangle(rect));
        assertTrue(canvas.getQuadrant(3).intersectsRectangle(rect));

        rect = new Rectangle(260, 260, 550, 550);
        assertTrue(canvas.getQuadrant(0).intersectsRectangle(rect));
        assertTrue(canvas.getQuadrant(1).intersectsRectangle(rect));
        assertTrue(canvas.getQuadrant(2).intersectsRectangle(rect));
        assertTrue(canvas.getQuadrant(3).intersectsRectangle(rect));

        SquareCanvas canv = canvas.getQuadrant(0).getQuadrant(3);
        assertTrue(canv.intersectsRectangle(rect));

        canv = canvas.getQuadrant(1).getQuadrant(2);
        assertTrue(canv.intersectsRectangle(rect));
        canv = canvas.getQuadrant(1).getQuadrant(3);
        assertTrue(canv.intersectsRectangle(rect));

        canv = canvas.getQuadrant(2).getQuadrant(1);
        assertTrue(canv.intersectsRectangle(rect));
        canv = canvas.getQuadrant(2).getQuadrant(3);
        assertTrue(canv.intersectsRectangle(rect));

        canv = canvas.getQuadrant(3).getQuadrant(0);
        assertTrue(canv.intersectsRectangle(rect));
        canv = canvas.getQuadrant(3).getQuadrant(1);
        assertTrue(canv.intersectsRectangle(rect));
        canv = canvas.getQuadrant(3).getQuadrant(2);
        assertTrue(canv.intersectsRectangle(rect));

        canv = canvas.getQuadrant(0).getQuadrant(0);
        assertFalse(canv.intersectsRectangle(rect));
        canv = canvas.getQuadrant(0).getQuadrant(1);
        assertFalse(canv.intersectsRectangle(rect));
        canv = canvas.getQuadrant(0).getQuadrant(2);
        assertFalse(canv.intersectsRectangle(rect));

        canv = canvas.getQuadrant(1).getQuadrant(0);
        assertFalse(canv.intersectsRectangle(rect));
        canv = canvas.getQuadrant(1).getQuadrant(1);
        assertFalse(canv.intersectsRectangle(rect));

        canv = canvas.getQuadrant(2).getQuadrant(0);
        assertFalse(canv.intersectsRectangle(rect));
        canv = canvas.getQuadrant(2).getQuadrant(2);
        assertFalse(canv.intersectsRectangle(rect));


        // these rectangles might be queried for region search
        rect = new Rectangle(-10, 1000, 1200, 1200);
        assertTrue(canvas.intersectsRectangle(rect));
        assertFalse(canvas.getQuadrant(0).intersectsRectangle(rect));
        assertFalse(canvas.getQuadrant(1).intersectsRectangle(rect));
        assertTrue(canvas.getQuadrant(2).intersectsRectangle(rect));
        assertTrue(canvas.getQuadrant(3).intersectsRectangle(rect));

        // testing more edge cases
        rect = new Rectangle(0, 0, 512, 512);
        assertFalse(canvas.getQuadrant(1).intersectsRectangle(rect));
        assertTrue(canvas.getQuadrant(0).intersectsRectangle(rect));
        rect = new Rectangle(512, 0, 512, 512);
        assertFalse(canvas.getQuadrant(0).intersectsRectangle(rect));
        assertTrue(canvas.getQuadrant(1).intersectsRectangle(rect));
    }
}
