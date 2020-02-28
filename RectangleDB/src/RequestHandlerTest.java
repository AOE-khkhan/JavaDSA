import student.TestCase;

/**
 * Test the RequestHandler class.
 * 
 * @author  Bimal Gaudel
 * 
 * @version February 2020
 */
public class RequestHandlerTest extends TestCase {
    /** A RequestHandler object used for testing. */
    private RequestHandler theHandler;

    /** Sets up the testing methods that follow. */
    public void setUp() {
        theHandler = new RequestHandler(new World());
    }

    /** Test the insert() method. */
    public void testInsert() {

        // good enough rectangle for insertion
        theHandler.insert("Rec_A", " 0 0 50 30");
        // rectangle with negative coordinate
        theHandler.insert("Rec_A", "-1 0 50 30");
        theHandler.insert("Rec_A", "0 -1 50 30");
        // rectangle with size bigger than world's
        theHandler.remove("Rec_A", "0 500 600 600");
        // rectangle with negative width
        theHandler.insert("Rec_A", "0 1 -50 30");
        theHandler.insert("Rec_A", "0 1 50 -30");
        // rectangle with insufficient specs
        theHandler.insert("Rec_A", "0 1 50 ");

        assertFuzzyEquals(systemOut().getHistory(),
                "Rectangle inserted: (Rec_A, 0, 0, 50, 30)\n"
                        + "Rectangle rejected: (Rec_A, -1, 0, 50, 30)\n"
                        + "Rectangle rejected: (Rec_A, 0, -1, 50, 30)\n"
                        + "Rectangle rejected: (Rec_A, 0, 500, 600, 600)\n"
                        + "Rectangle rejected: (Rec_A, 0, 1, -50, 30)\n"
                        + "Rectangle rejected: (Rec_A, 0, 1, 50, -30)\n"
                        + "Bad arguments! Use: insert <rect_name>"
                        + " <x> <y> <width> <height>");
    }

    /** Test the remove() method. */
    public void testRemove() {

        // good enough rectangle for removal (if it's present)
        theHandler.remove("Rec_A", " 0 0 50 30");
        // rectangle with negative coordinate
        theHandler.remove("Rec_A", "-1 0 50 30");
        // rectangle with size bigger than world's
        theHandler.remove("Rec_A", "0 1200 50 30");
        theHandler.remove("Rec_A", "500 500 600 600");
        // rectangle with negative width
        theHandler.remove("Rec_A", "0 1 -50 30");
        theHandler.remove("Rec_A", "0 1 50 -30");
        // rectangle with insufficient specs
        theHandler.remove("Rec_A", "0 1 50 ");

        assertFuzzyEquals(systemOut().getHistory(),
                "Rectangle removed: (Rec_A, 0, 0, 50, 30)\n"
                        + "Rectangle rejected: (Rec_A, -1, 0, 50, 30)\n"
                        + "Rectangle rejected: (Rec_A, 0, 1200, 50, 30)\n"
                        + "Rectangle rejected: (Rec_A, 500, 500, 600, 600)\n"
                        + "Rectangle rejected: (Rec_A, 0, 1, -50, 30)\n"
                        + "Rectangle rejected: (Rec_A, 0, 1, 50, -30)\n"
                        + "Bad arguments! Use: remove <rect_name>"
                        + " <x> <y> <width> <height>");
    }

    /** Test the dump() method. */
    public void testDump() {
        theHandler.dump();
        assertFuzzyEquals(systemOut().getHistory(), "QuadTree dump:");
    }

    /** Test the regionsearch() method. */
    public void testRegionsearch() {

        // rectangle with negative width
        theHandler.regionsearch("0 0 -10 60");
        // rectangle with negative height
        theHandler.regionsearch("0 0 10 -60");
        // rectangle with negative cooordinates but good enough for a
        // regionsearch query
        theHandler.regionsearch("-10 -10 100 100");
        // rectangle with size bigger than world's
        theHandler.regionsearch("700 900 1200 1600");

        assertFuzzyEquals(systemOut().getHistory(),
                "Rectangle rejected: (0, 0, -10, 60)\n"
                        + "Rectangle rejected: (0, 0, 10, -60)\n"
                        + "Rectangles intersecting rectangle "
                        + "(-10, -10, 100, 100):\n"
                        + "Rectangles intersecting rectangle "
                        + "(700, 900, 1200, 1600):");
    }

    /** Test the intersections() method. */
    public void testIntersections() {
        theHandler.intersections();
        assertFuzzyEquals(systemOut().getHistory(),
                "Rectangle intersections in the database:");
    }
}
