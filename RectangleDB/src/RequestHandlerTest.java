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

    /** Test the duplicate insertion of rectangles. */
    public void testDuplicateInsert() {
        // good enough rectangle for insertion
        theHandler.insert("Rec_A", " 0 0 50 30");
        // duplicate insertion
        theHandler.insert("Rec_A", " 0 0 50 30");

        //@formatter:off
        assertFuzzyEquals(systemOut().getHistory(),
                "Rectangle inserted: (Rec_A, 0, 0, 50, 30)\n"
              + "Duplicate rectangle rejected: (Rec_A, 0, 0, 50, 30)");
        //@formatter:on

    }

    /** Test the remove() method. */
    public void testRemove() {

        String expectedOutput;

        // insert a rectangle to remove it successfully
        theHandler.insert("Rec_A", " 0 0 50 30");
        expectedOutput = "Rectangle inserted: (Rec_A, 0, 0, 50, 30)\n";

        // good enough rectangle for removal (if it's present)
        theHandler.remove("Rec_A", " 0 0 50 30");
        expectedOutput += "Rectangle removed: (Rec_A, 0, 0, 50, 30)\n";

        // removing twice
        theHandler.remove("Rec_A", " 0 0 50 30");
        expectedOutput += "Rectangle not in database: (Rec_A, 0, 0, 50, 30)\n";

        // rectangle with negative coordinate
        theHandler.remove("Rec_A", "-1 0 50 30");
        expectedOutput += "Rectangle rejected: (Rec_A, -1, 0, 50, 30)\n";

        // rectangle with size bigger than world's
        theHandler.remove("Rec_A", "0 1200 50 30");
        expectedOutput += "Rectangle rejected: (Rec_A, 0, 1200, 50, 30)\n";
        //
        theHandler.remove("Rec_A", "500 500 600 600");
        expectedOutput += "Rectangle rejected: (Rec_A, 500, 500, 600, 600)\n";

        // rectangle with negative width
        theHandler.remove("Rec_A", "0 1 -50 30");
        expectedOutput += "Rectangle rejected: (Rec_A, 0, 1, -50, 30)\n";
        //
        theHandler.remove("Rec_A", "0 1 50 -30");
        expectedOutput += "Rectangle rejected: (Rec_A, 0, 1, 50, -30)\n";

        // rectangle with insufficient specs
        // @formatter:off
        theHandler.remove("Rec_A", "0 1 50 ");
        expectedOutput += "Bad arguments! Use: remove <rect_name>"
                       +  " <x> <y> <width> <height>\n";
        // @formatter:on

        assertFuzzyEquals(systemOut().getHistory(), expectedOutput);
    }

    /** Test the dump() method. */
    public void testDump() {
        // let's first insert a bunch of records
        String header = "\nTesting dump method\n------------------\n";
        System.out.print(header);
        String expectedOutput = header;
        theHandler.insert("Rec_A", "0 0 10 10");
        expectedOutput += "Rectangle inserted: " + "(Rec_A, 0, 0, 10, 10)\n";

        theHandler.insert("Rec_B", "0 0 20 20");
        expectedOutput += "Rectangle inserted: " + "(Rec_B, 0, 0, 20, 20)\n";

        theHandler.insert("Rec_C", "512 512 20 20");
        expectedOutput +=
                "Rectangle inserted: " + "(Rec_C, 512, 512, 20, 20)\n";

        // three rectangles inserted only two of which overlap
        // so they should've splitted
        theHandler.dump();

        //@formatter:off
        expectedOutput += "QuadTree dump:\n"
                        + "Node at 0, 0, 1024 internal:\n"
                        //
                        + "Node at 0, 0, 512:\n"
                        + "(Rec_A, 0, 0, 10, 10)\n"
                        + "(Rec_B, 0, 0, 20, 20)\n"
                        //
                        + "Node at 512, 0, 512: Empty\n"
                        //
                        + "Node at 0, 512, 512: Empty\n"
                        //
                        + "Node at 512, 512, 512:\n"
                        + "(Rec_C, 512, 512, 20, 20)\n";
        //@formatter:on

        expectedOutput += "5. quadtree nodes printed\n";
        assertFuzzyEquals(systemOut().getHistory(), expectedOutput);
    }

    /** Test the regionsearch() method. */
    public void testRegionsearch() {
        //@formatter:off
        String header = "\nTesting region search\n" +
                        "---------------------\n"; 
        //@formatter:on
        System.out.print(header);
        String expectedOutput = header;

        // inserting example rectangles given on the project description
        RectangleRecord recordA = new RectangleRecord("A", 200, 200, 400, 300);
        RectangleRecord recordB = new RectangleRecord("B", 250, 250, 500, 500);
        RectangleRecord recordC = new RectangleRecord("C", 600, 600, 400, 400);
        RectangleRecord recordD = new RectangleRecord("D", 650, 650, 300, 300);
        RectangleRecord[] toInsert =
                new RectangleRecord[] {recordA, recordB, recordC, recordD};
        for (RectangleRecord record : toInsert) {
            int x = record.getRectangle().getX();
            int y = record.getRectangle().getY();
            int width = record.getRectangle().getWidth();
            int height = record.getRectangle().getHeight();
            theHandler.insert(record.getName(),
                    String.format("%d %d %d %d", x, y, width, height));
            expectedOutput += "Rectangle inserted: " + record.toString() + "\n";
        }

        // rectangle with negative width
        theHandler.regionsearch("0 0 -10 60");
        expectedOutput += "Search rectangle rejected: (0, 0, -10, 60)\n";
        // rectangle with negative height
        theHandler.regionsearch("0 0 10 -60");
        expectedOutput += "Search rectangle rejected: (0, 0, 10, -60)\n";
        // proper rectangle that doesn't intersect the world canvas
        theHandler.regionsearch("-5 -5 1 1");
        theHandler.regionsearch("1024 1024 10 10");
        expectedOutput += "Search rectangle rejected: (-5, -5, 1, 1)\n";
        expectedOutput += "Search rectangle rejected: (1024 1024 10 10)\n";

        // rectangle with negative coordinates but good enough for a
        // regionsearch query
        theHandler.regionsearch("-10 -10 100 100");
        expectedOutput +=
                "Rectangles intersecting rectangle (-10, -10, 100, 100):\n";
        // there are no rectangles that intersect (-10, -10, 100, 100)
        // should only print num of nodes visited
        // the root internal node was visited
        // and the first quadrant of tha node was visited in accordance with the
        // query rectangle's dimension
        expectedOutput += "2. quadtree nodes visited.\n";

        // rectangle with size bigger than world's but
        // intersects nevertheless
        theHandler.regionsearch("700 900 1200 1600");
        // rectangle C and D should've been printed
        expectedOutput +=
                "Rectangles intersecting rectangle (700, 900, 1200, 1600):\n";
        expectedOutput +=
                String.format("Rectangle found: %s\n", recordC.toString());
        expectedOutput +=
                String.format("Rectangle found: %s\n", recordD.toString());
        // still two quadtree nodes were visited
        expectedOutput += "2. quadtree nodes visited.\n";

        assertFuzzyEquals(systemOut().getHistory(), expectedOutput);
    }

    /** Test the intersections() method. */
    public void testIntersections() {
        theHandler.intersections();
        assertFuzzyEquals(systemOut().getHistory(),
                "Rectangle intersections in the database:\n"
                + "1. quadtree nodes visited");
    }
}
