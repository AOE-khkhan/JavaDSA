import student.TestCase;

/**
 * Test the Rectangle class.
 * 
 * @author  Bimal Gaudel
 * 
 * @version Feb 2020
 */
public class RectangleTest extends TestCase {
    /** A Rectangle object used for testing. */
    private Rectangle rec;

    /** Sets up the tests that follow. */
    public void setUp() {
        rec = new Rectangle(0, 5, 200, 400);
    }

    /** Test the getter methods. */
    public void testGetters() {
        // test getX()
        assertEquals(rec.getX(), 0);

        // test getY()
        assertEquals(rec.getY(), 5);

        // test getWidth()
        assertEquals(rec.getWidth(), 200);

        // test getHeight()
        assertEquals(rec.getHeight(), 400);
    }

    /** Test the overridden method equals(). */
    public void testEquality() {
        Rectangle aRec = new Rectangle(0, 0, 10, 10);
        Rectangle aliasToaRec = aRec;

        // Comparison of the same objects.
        assertEquals(aRec.equals(aRec), true);
        assertEquals(aliasToaRec.equals(aRec), true);

        // Comparison of non-Rectangle type object.
        int anInt = 10;
        Object nonRecObj = (Object) anInt;
        assertEquals(aRec.equals(nonRecObj), false);

        // Comparison of Rectangle with different specs.
        Rectangle rec1 = new Rectangle(1, 0, 10, 10);
        Rectangle rec2 = new Rectangle(0, 1, 10, 10);
        Rectangle rec3 = new Rectangle(0, 0, 11, 10);
        Rectangle rec4 = new Rectangle(0, 0, 10, 11);
        //
        assertEquals(aRec.equals(rec1), false);
        assertEquals(aRec.equals(rec2), false);
        assertEquals(aRec.equals(rec3), false);
        assertEquals(aRec.equals(rec4), false);

        // Comparison of Rectangle with same specs.
        Rectangle rec5 = new Rectangle(0, 0, 10, 10);
        assertEquals(aRec.equals(rec5), true);

    }

    /** Test hasPoint method. */
    public void testHasPoint() {
        assertTrue(rec.hasPoint(0, 5));
        assertTrue(rec.hasPoint(1, 5));
        assertTrue(rec.hasPoint(0, 6));
        assertFalse(rec.hasPoint(0, 0));
        assertFalse(rec.hasPoint(0, 410));

        assertTrue(rec.hasPoint(199, 404));
        assertTrue(rec.hasPoint(0, 404));
        assertFalse(rec.hasPoint(200, 405));
        assertFalse(rec.hasPoint(200, 5));
        assertFalse(rec.hasPoint(201, 5));
    }

    /** Test the getIntersection method. */
    public void testGetIntersection() {
        Rectangle rec1 = new Rectangle(100, 200, 200, 400);
        assertTrue(rec.intersects(rec1));
        Rectangle insec = rec.getIntersection(rec1);
        assertTrue(insec.equals(new Rectangle(100, 200, 100, 205)));
    }
}
