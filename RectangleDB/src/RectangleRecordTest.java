import student.TestCase;

/**
 * Test the RectangleRecord class.
 * 
 * @author  Bimal Gaudel
 * 
 * @version Feb 2020
 */
public class RectangleRecordTest extends TestCase {
    /** Test the getter methods. */
    public void testGetters() {
        RectangleRecord aRecord = new RectangleRecord("Rec_A");
        assertEquals(aRecord.getName(), "Rec_A");
        assertNull(aRecord.getRectangle());

        Rectangle rect1 = new Rectangle(0, 0, 10, 20);
        aRecord.setRectangle(rect1);

        assertEquals(aRecord.getRectangle(), rect1);

        Rectangle rect2 = new Rectangle(0, 0, 10, 20);
        assertEquals(aRecord.getRectangle(), rect2);

    }

    /** Test the equals method. */
    public void testEquality() {
        RectangleRecord rec1 =
                new RectangleRecord("Rec_x", new Rectangle(0, 0, 10, 10));

        RectangleRecord rec2 =
                new RectangleRecord("Rec_x", new Rectangle(0, 0, 10, 10));

        assertEquals(rec1, rec1);
        assertEquals(rec1, rec2);
        rec2 = new RectangleRecord("Rec_x", new Rectangle(0, 0, 9, 10));
        assertFalse(rec1.equals(rec2));

        rec2 = new RectangleRecord("Rec_y", new Rectangle(0, 0, 10, 10));

        assertFalse(rec1.equals(rec2));

        // testing equality with unrelated object
        Object obj = "unrelated_object";
        assertFalse(rec1.equals(obj));

    }
}
