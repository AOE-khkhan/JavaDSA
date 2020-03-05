/**
 * World object is the platform for a RectangleDB actions.
 * 
 * @author  Bimal Gaudel
 * 
 * @version February 2020
 */
public class World {
    /**
     * The maximum value for an X or Y coordinate we can handle. Together with
     * the
     * MIN_COORDINATE_VALUE it determines the spatial size of the rectangles we
     * can work with.
     */
    public static final int MAX_COORDINATE_VALUE = 1023;

    /**
     * The minimum value for an X or Y coordinate we can handle. Together with
     * the
     * MAX_COORDINATE_VALUE it determines the spatial size of the rectangles we
     * can work with.
     */
    public static final int MIN_COORDINATE_VALUE = 0;

    /** The root canvas for the quadtree. */
    private final SquareCanvas rootCanvas;

    /** QuadTree object of this world. */
    private QuadTree qtree;

    /** Construct a world object. */
    World() {
        qtree = QuadTreeInternalNode.FLYWEIGHT;
        // The canvas is of 1024 size as specified by the project requirement.
        rootCanvas = new SquareCanvas(MIN_COORDINATE_VALUE,
                MIN_COORDINATE_VALUE, MAX_COORDINATE_VALUE + 1);
    }

    /**
     * Getter for the quadtree.
     * 
     * @return The QuadTree object managed by this world.
     */
    public QuadTree getQuadTree() {
        return qtree;
    }

    /**
     * Allow modified quadtree to be stored by this world.
     * 
     * @param tree A QuadTree that is a result of insertion or removal commands
     *             or equivalents.
     */
    public void setQuadTree(QuadTree tree) {
        qtree = tree;
    }

    /**
     * Getter of the rootCanvas.
     * 
     * @return The SquareCanvas object of the root node of the quadtree.
     */
    public SquareCanvas getCanvas() {
        return rootCanvas;
    }
}
