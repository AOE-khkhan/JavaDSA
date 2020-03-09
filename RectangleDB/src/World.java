/**
 * World object is the platform for a RectangleDB actions.
 * 
 * @author  Bimal Gaudel
 * 
 * @version February 2020
 */
public class World {
    /**
     * The size of the world in one dimension. Our world is two dimensional with
     * total size being SIZE_ONE_DIM * SIZE_ONE_DIM
     * */
    public static final int SIZE_ONE_DIM = 1024;

    /** The root canvas for the quadtree. */
    private final SquareCanvas rootCanvas;

    /** QuadTree object of this world. */
    private QuadTree qtree;

    /** Construct a world object. */
    World() {
        qtree = QuadTreeInternalNode.FLYWEIGHT;
        // The canvas is of 1024 size as specified by the project requirement.
        rootCanvas = new SquareCanvas(0, 0, SIZE_ONE_DIM);
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
