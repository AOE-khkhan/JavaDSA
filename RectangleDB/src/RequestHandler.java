
import java.util.Scanner;

/**
 * Handle received commands and arguments for RectangleDB program.
 * 
 * @author  Bimal Gaudel
 * 
 * @version February 2020
 */
public class RequestHandler {
    /**
     * The World object we are working on. Every action is passed through the
     * World object to various data-structures.
     */
    private World world;

    /**
     * Construct a handling session for a World.
     * 
     * @param world The World object through which the commands will be
     *              chanelled.
     */
    RequestHandler(World world) {
        this.world = world;
    }

    /**
     * Insert a rectangle in the database.
     *
     * A rectangle to be inserted is specified by its name, x and y coordinates
     * of the starting point, and the width and height that are horizontal and
     * vertical lengths of the rectangle respectively.
     *
     * Duplicate rectangles will not be inserted. A duplicate rectangle is a
     * rectangle that has the same name, origin and the size as one of the
     * pre-existing coordinates. Note that two unique rectangles can have the
     * same name, or the same spatial specifications but not both.
     * 
     * 
     * @param rectName A String name for the rectangle. eg. "Rec_A"
     * @param args     Arguments String that will be parsed to get the starting
     *                 coordinates of the rectangle together with the width and
     *                 the height. eg. "110 100 50 70" are x, y, width and
     *                 height respectively.
     *
     */
    public void insert(String rectName, String args) {
        try {
            int[] specs = scanSpecs(args);
            // rectangle specs to print when we are done
            String recSpecPrint = specsToString(rectName, specs);

            if (rejectRectangle(specs)) {
                System.out.println("Rectangle rejected: " + recSpecPrint);
                return;
            }

            RectangleRecord toInsert =
                    new RectangleRecord(rectName, specs[X_COORD],
                            specs[Y_COORD], specs[WIDTH], specs[HEIGHT]);

            // context for insertion we need a quadtree and a canvas
            QuadTree tree = world.getQuadTree();
            SquareCanvas canvas = world.getCanvas();


            // if duplicate
            if (tree.hasRecord(toInsert)) {
                System.out.println(
                        "Duplicate rectangle rejected: " + recSpecPrint);
                return;
            }

            tree = tree.insertRecord(toInsert, canvas);
            world.setQuadTree(tree);
            System.out.println("Rectangle inserted: " + recSpecPrint);
        }
        catch (Exception e) {
            // not a good specification for a rectangle found
            System.out.println("Bad arguments! Use: insert <rect_name>"
                    + " <x> <y> <width> <height>");
        }
    }

    /**
     * Remove a rectangle from the database.
     * 
     * @param rectName A String name for the rectangle. eg. "Rec_A"
     * @param args     Arguments String that will be parsed to get the starting
     *                 coordinates of the rectangle together with the width and
     *                 the height. eg. "110 100 50 70" are x, y, width and
     *                 height respectively.
     */
    public void remove(String rectName, String args) {
        try {
            int[] specs = scanSpecs(args);
            // rectangle specs to print when we are done
            String recSpecPrint = specsToString(rectName, specs);

            if (rejectRectangle(specs)) {
                System.out.println("Rectangle rejected: " + recSpecPrint);
                return;
            }

            RectangleRecord toRemove =
                    new RectangleRecord(rectName, specs[X_COORD],
                            specs[Y_COORD], specs[WIDTH], specs[HEIGHT]);

            // context for deletion we need a quadtree and a canvas
            QuadTree tree = world.getQuadTree();
            SquareCanvas canvas = world.getCanvas();


            // if non-existent
            if (!tree.hasRecord(toRemove)) {
                System.out
                        .println("Rectangle not in database: " + recSpecPrint);
                return;
            }
            tree = tree.removeRecord(toRemove, canvas);
            world.setQuadTree(tree);
            System.out.println("Rectangle removed: " + recSpecPrint);
        }
        catch (Exception e) {
            // not a good specification for a rectangle found
            System.out.println("Bad arguments! Use: remove <rect_name>"
                    + " <x> <y> <width> <height>");
        }
    }

    /** Print out the database. */
    public void dump() {
        System.out.println("QuadTree dump:");

        int nodesVisited = world.getQuadTree().dump(world.getCanvas());

        System.out.println(nodesVisited + ". quadtree nodes printed");
    }

    /**
     * Report all rectangles currently in the database that intersect the query
     * rectangle specified by the parameters passed.
     * 
     * @param args Arguments String that will be parsed to get the starting
     *             coordinates of the rectangle together with the width and the
     *             height. eg. "110 100 50 70" are x, y, width and height
     *             respectively.
     */
    public void regionsearch(String args) {
        int[] specs = RequestHandler.scanSpecs(args);
        String recSpecPrint = specsToString(specs);

        int x = specs[X_COORD];
        int y = specs[Y_COORD];
        int width = specs[WIDTH];
        int height = specs[HEIGHT];

        Rectangle queryRectangle = new Rectangle(x, y, width, height);

        // rectangles with negative width or height spec should be rejected
        // on the other hand if the query rectangle doesn't overlap with the
        // world, it should be rejected too
        if ((width < 0) || (height < 0)
                || (!queryRectangle.intersects(world.getCanvas()))) {
            System.out.println("Search rectangle rejected: " + recSpecPrint);
            return;
        }

        //@formatter:off
        System.out.println( String.format(
                    "Rectangles intersecting rectangle %s:", recSpecPrint));
        int visitCount = world.getQuadTree().searchRegion(queryRectangle,
                                                        world.getCanvas());
        System.out.println(visitCount + ". quadtree nodes visited");
        //@formatter:on

    }

    /** Report all pairs of rectangles within the database that intersect. */
    public void intersections() {
        System.out.println("Rectangle intersections in the database:");
        int numVisitedNodes =
                world.getQuadTree().listIntersections(world.getCanvas());
        System.out.println(numVisitedNodes + ". quadtree nodes visited");
    }

    /**
     * Reject a rectangle when attempted for insertion or deletion. If its size
     * specification exceeds the size of the maximum allowed rectangle
     * originating from the requested point.
     * 
     * @param  specs An array of four integers that are x, y coordinates, the
     *               width and the height of the rectangle respectively.
     * 
     * @return       True if the rectangle specification is invalid and should
     *               be rejected.
     */
    private boolean rejectRectangle(int[] specs) {
        // negative or too large values are no good
        for (int i : specs) {
            if ((i < 0) || (i >= World.SIZE_ONE_DIM)) {
                return true;
            }
        }

        // x + width should not exceed max coordinate
        // y + height should not exceed max coordinate
        // @formatter:off
        return ((specs[X_COORD] + specs[WIDTH] > World.SIZE_ONE_DIM)
            || (specs[Y_COORD] + specs[HEIGHT] > World.SIZE_ONE_DIM));
        // @formatter:on
    }

    /**
     * Given a rectangle specification, scans for the x and y coordinates for
     * the starting point, the width and the height of the rectangle in that
     * order.
     *
     * @param  args A String that is a specification for a rectangle. It is
     *              assumed to be four numbers separated by space.
     * 
     * @return      An array of ints that are the specifications of the
     *              rectangle in the order of x and y coordinates, width and
     *              the height.
     */
    private static int[] scanSpecs(String args) {

        int[] specs = new int[SPECS_NUM];
        Scanner sc = new Scanner(args);
        for (int i = 0; i < SPECS_NUM; ++i) {
            specs[i] = sc.nextInt();
        }

        sc.close();
        return specs;
    }

    /**
     * Format rectangle specs for printing.
     * 
     * @param  specs Array of ints for x coordinate, y coordinate, width and
     *               height of the rectangle respectively.
     * 
     * @return       String for printing.
     */
    private static String specsToString(int[] specs) {
        // @formatter:off
        return String.format("(%d, %d, %d, %d)",
                specs[X_COORD], specs[Y_COORD],
                specs[WIDTH], specs[HEIGHT]);
        // @formatter:on
    }

    /**
     * Format rectangle specs for printing.
     * 
     * @param  rectName String name of the rectangle.
     * @param  specs    Array of ints for x coordinate, y coordinate, width and
     *                  height of the rectangle respectively.
     * 
     * @return          String for printing.
     */
    private static String specsToString(String rectName, int[] specs) {
        // @formatter:off
        return String.format("(%s, %d, %d, %d, %d)", rectName,
                                     specs[X_COORD], specs[Y_COORD],
                                       specs[WIDTH], specs[HEIGHT]);
    }

    /** Array indices for rectangle specs. */

    /** X_COORD: x coordinate where the rectangle starts */
    private static final short X_COORD   = 0;

    /** Y_COORD: y coordinate where the rectangle starts */
    private static final short Y_COORD   = 1;

    /** WIDTH: Width of the rectangle */
    private static final short WIDTH     = 2;

    /** HEIGHT: Height of the rectangle */
    private static final short HEIGHT    = 3;

    /** SPECS_NUM: Total number of the specification entries. */
    private static final short SPECS_NUM = 4;
}
