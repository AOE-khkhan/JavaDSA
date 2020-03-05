import student.TestCase;

/**
 * Test the QuadTree and the derived classes.
 *
 * @author  Bimal Gaudel
 * 
 * @version Feb 2020
 */
public class QuadTreeTest extends TestCase {
    /** A QuadTree used for testing. */
    private QuadTree qtree;

    /** The canvas is of 1024 size as specified by the project requirement. */
    private SquareCanvas rootCanvas;

    /** Sets up the tests that follow. */
    public void setUp() {
        qtree = QuadTreeInternalNode.FLYWEIGHT;
        rootCanvas = new SquareCanvas(0, 0, 1024);
    }

    /** Test insertion of a rectangle. */
    public void testInsert() {
        // inserting example rectangles given on the project description
        qtree = qtree.insertRectangle(
                new RectangleRecord("A", 200, 200, 400, 300), rootCanvas);

        qtree = qtree.insertRectangle(
                new RectangleRecord("B", 250, 250, 500, 500), rootCanvas);

        qtree = qtree.insertRectangle(
                new RectangleRecord("C", 600, 600, 400, 400), rootCanvas);

        qtree = qtree.insertRectangle(
                new RectangleRecord("D", 650, 650, 300, 300), rootCanvas);

        qtree.dump(rootCanvas);
        //@formatter:off
        String expectedOutput =
                  "Node at 0, 0, 512:\n"
                + "(A, 200, 200, 400, 300)\n"
                + "(B, 250, 250, 500, 500)\n"
                //
                + "Node at 512, 0, 512:\n"
                + "(A, 200, 200, 400, 300)\n"
                + "(B, 250, 250, 500, 500)\n"
                //
                + "Node at 0, 512, 512:\n"
                + "(B, 250, 250, 500, 500)\n"
                //
                + "Node at 512, 512, 512:\n"
                + "(B, 250, 250, 500, 500)\n"
                + "(C, 600, 600, 400, 400)\n"
                + "(D, 650, 650, 300, 300)";
        //@formatter:on
        assertFuzzyEquals(systemOut().getHistory(), expectedOutput);
    }

    /** Test hasRecord method. */
    public void testHasRecord() {
        qtree = qtree.insertRectangle(
                new RectangleRecord("Rec_A", 0, 0, 10, 10), rootCanvas);
        qtree = qtree.insertRectangle(
                new RectangleRecord("A", 200, 200, 400, 300), rootCanvas);
        qtree = qtree.insertRectangle(
                new RectangleRecord("B", 250, 250, 500, 500), rootCanvas);
        qtree = qtree.insertRectangle(
                new RectangleRecord("C", 600, 600, 400, 400), rootCanvas);
        qtree = qtree.insertRectangle(
                new RectangleRecord("D", 650, 650, 300, 300), rootCanvas);
        assertTrue(qtree.hasRecord(new RectangleRecord("Rec_A", 0, 0, 10, 10)));
        assertFalse(qtree.hasRecord(new RectangleRecord("Rec", 0, 0, 10, 10)));

    }

    /** Test the removeRectangle method. */
    public void testRemove() {
        // inserting example rectangles given on the project description
        qtree = qtree.insertRectangle(
                new RectangleRecord("A", 200, 200, 400, 300), rootCanvas);
        qtree = qtree.insertRectangle(
                new RectangleRecord("B", 250, 250, 500, 500), rootCanvas);
        qtree = qtree.insertRectangle(
                new RectangleRecord("C", 600, 600, 400, 400), rootCanvas);
        qtree = qtree.insertRectangle(
                new RectangleRecord("D", 650, 650, 300, 300), rootCanvas);

        // removing all the inserted rectangles one at a time
        qtree = qtree.removeRectangle(
                new RectangleRecord("A", 200, 200, 400, 300), rootCanvas);
        qtree = qtree.removeRectangle(
                new RectangleRecord("B", 250, 250, 500, 500), rootCanvas);
        qtree = qtree.removeRectangle(
                new RectangleRecord("C", 600, 600, 400, 400), rootCanvas);
        qtree = qtree.removeRectangle(
                new RectangleRecord("D", 650, 650, 300, 300), rootCanvas);

        // printing the quadtree dump
        qtree.dump(rootCanvas);

        assertFuzzyEquals(systemOut().getHistory(),
                "Node at 0, 0, 1024: Empty");

        assertEquals(qtree, QuadTreeInternalNode.FLYWEIGHT);
    }

    /** Get code coverage for the unexcuted functions and statements. */
    public void testGetCodeCoverage() {
        QuadTree flyNode = new QuadTreeFlyweightNode();
        //
        RectangleRecord toRemove = new RectangleRecord("Rec", 0, 0, 10, 10);
        assertEquals(flyNode.removeRectangle(toRemove, rootCanvas), flyNode);

        assertTrue(flyNode.isEmpty());
    }

    // Test dump of the quadtree.
}
