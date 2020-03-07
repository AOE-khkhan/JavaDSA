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

    /** Test project description examples insertion. */
    public void testProjectExamplesInsert() {
        // inserting example rectangles given on the project description
        qtree = qtree.insertRecord(new RectangleRecord("A", 200, 200, 400, 300),
                rootCanvas);

        qtree = qtree.insertRecord(new RectangleRecord("B", 250, 250, 500, 500),
                rootCanvas);

        qtree = qtree.insertRecord(new RectangleRecord("C", 600, 600, 400, 400),
                rootCanvas);

        qtree = qtree.insertRecord(new RectangleRecord("D", 650, 650, 300, 300),
                rootCanvas);

        qtree.dump(rootCanvas);
       //@formatter:off
        String expectedOutput = "Node at 0, 0, 1024 internal:\n"
               + "Node at 0, 0, 512:\n"
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
               + "(D, 650, 650, 300, 300)\n";
       //@formatter:on
        assertFuzzyEquals(systemOut().getHistory(), expectedOutput);
    }

    /** Test complex insertion. */
    public void testComplexInsertion() {
        //@formatter:off
        String header = "---------------------------\n" 
                        + "Testing complex insertions";
        //@formatter:on
        System.out.println(header);
        String expectedOutput = header + "\n";

        // lets insert two non-overlapping rectangles
        RectangleRecord recordA = new RectangleRecord("A", 0, 0, 400, 400);
        RectangleRecord recordB = new RectangleRecord("B", 512, 0, 400, 400);
        qtree = qtree.insertRecord(recordA, rootCanvas);
        qtree = qtree.insertRecord(recordB, rootCanvas);
        //
        // so far no splitting should have occured
        //
        // lets add one more rectangle that doesn't intersect either of the
        // rectangles inserted so far
        RectangleRecord recordC = new RectangleRecord("C", 0, 512, 400, 400);
        qtree = qtree.insertRecord(recordC, rootCanvas);
        // 
        // now there should be splitting
        //
        // lets add another rectangle that intersects all of the pre-existing
        // rectangles
        RectangleRecord recordABC =
                new RectangleRecord("ABC", 256, 256, 512, 512);
        qtree = qtree.insertRecord(recordABC, rootCanvas);
        // There shouldn't have been any splitting.
        // Only the recordABC rectangle should have been added
        //
        // now adding a rectangle that intersects recordA
        RectangleRecord recordA1 = new RectangleRecord("A1", 256, 0, 256, 256);
        qtree = qtree.insertRecord(recordA1, rootCanvas);
        //
        // now there should splitting in the first quadrant, because
        // on the first quadrant there are three rectangles recordA, recordABC
        // and recordA1, and not all of them have a common intersection
        //
        // now adding another rectangle that intersects with the two rectangles
        // present in the quadrant (256, 256, 256) namely recordA and recordABC
        RectangleRecord recordA2 =
                new RectangleRecord("A2", 260, 260, 200, 200);
        qtree = qtree.insertRecord(recordA2, rootCanvas);
        // no splitting should occur even though the quadrant (256, 256, 256)
        // now has three records, because all three overlap together
        qtree.dump(rootCanvas);
        //@formatter:off
        expectedOutput += "Node at 0, 0, 1024 internal:\n"
                       //
                       + "Node at 0, 0, 512 internal:\n"
                       //
                       + "Node at 0, 0, 256:\n"
                       + recordA.toString() + "\n"
                       + "Node at 256, 0, 256:\n"
                       + recordA.toString() + "\n"
                       + recordA1.toString() + "\n"
                       + "Node at 0, 256, 256:\n"
                       + recordA.toString() + "\n"
                       + "Node at 256, 256, 256:\n"
                       + recordA.toString() + "\n"
                       + recordABC.toString() + "\n"
                       + recordA2.toString() + "\n"
                       //
                       + "Node at 512, 0, 512:\n"
                       + recordB.toString() + "\n"
                       + recordABC.toString() + "\n"
                       + "Node at 0, 512, 512:\n"
                       + recordC.toString() + "\n"
                       + recordABC.toString() + "\n"
                       + "Node at 512, 512, 512:\n"
                       + recordABC.toString() + "\n";
        //@formatter:on

        String footer = "---------------------------";
        System.out.println(footer);
        expectedOutput += footer + "\n";
        assertFuzzyEquals(systemOut().getHistory(), expectedOutput);
    }

    /** Test hasRecord method. */
    public void testHasRecord() {
        qtree = qtree.insertRecord(new RectangleRecord("Rec_A", 0, 0, 10, 10),
                rootCanvas);
        qtree = qtree.insertRecord(new RectangleRecord("A", 200, 200, 400, 300),
                rootCanvas);
        qtree = qtree.insertRecord(new RectangleRecord("B", 250, 250, 500, 500),
                rootCanvas);
        qtree = qtree.insertRecord(new RectangleRecord("C", 600, 600, 400, 400),
                rootCanvas);
        qtree = qtree.insertRecord(new RectangleRecord("D", 650, 650, 300, 300),
                rootCanvas);
        assertTrue(qtree.hasRecord(new RectangleRecord("Rec_A", 0, 0, 10, 10)));
        assertFalse(qtree.hasRecord(new RectangleRecord("Rec", 0, 0, 10, 10)));

    }

    /** Test the removeRectangle method. */
    public void testRemove() {
        // inserting example rectangles given on the project description
        qtree = qtree.insertRecord(new RectangleRecord("A", 200, 200, 400, 300),
                rootCanvas);
        qtree = qtree.insertRecord(new RectangleRecord("B", 250, 250, 500, 500),
                rootCanvas);
        qtree = qtree.insertRecord(new RectangleRecord("C", 600, 600, 400, 400),
                rootCanvas);
        qtree = qtree.insertRecord(new RectangleRecord("D", 650, 650, 300, 300),
                rootCanvas);

        // removing all the inserted rectangles one at a time
        qtree = qtree.removeRecord(new RectangleRecord("A", 200, 200, 400, 300),
                rootCanvas);
        qtree = qtree.removeRecord(new RectangleRecord("B", 250, 250, 500, 500),
                rootCanvas);
        qtree = qtree.removeRecord(new RectangleRecord("C", 600, 600, 400, 400),
                rootCanvas);
        qtree = qtree.removeRecord(new RectangleRecord("D", 650, 650, 300, 300),
                rootCanvas);

        assertEquals(qtree, QuadTreeInternalNode.FLYWEIGHT);
    }

    /** Get code coverage for the unexcuted functions and statements. */
    public void testGetCodeCoverage() {
        QuadTree flyNode = new QuadTreeFlyweightNode();
        //
        RectangleRecord toRemove = new RectangleRecord("Rec", 0, 0, 10, 10);
        assertEquals(flyNode.removeRecord(toRemove, rootCanvas), flyNode);

        assertTrue(flyNode.isEmpty());
    }

    // Test dump of the quadtree.
}
