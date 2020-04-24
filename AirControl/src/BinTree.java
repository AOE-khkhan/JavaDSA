/**
 * A BinTree class for storing AirObjects'.
 *
 * 
 * @author  Bimal Gaudel
 * 
 * @version Apr 22, 2020
 */
public class BinTree {
    /**
     * The dimension in one direction for the 3d space applicable to this
     * BinTree object.
     */
    private final int worldSize;

    /**
     * The minimum number of records allowed in a node to make it full.
     */
    private final int maxRecordsPerNode;

    /**
     * Keep a flyweight object initialized so that we won't be recreating it
     * when no instance of BinTreeNodeFlyweight class.
     */
    private final BinTreeNodeFlyweight fly = BinTreeNodeFlyweight.getInstance();

    /** The root node of the bin tree. */
    private BinTreeNodeAbstract root;

    /**
     * Construct a bin tree.
     * 
     * @param worldSize         The dimension in one direction for the 3d space
     *                          applicable to this BinTree object.
     * @param maxRecordsPerNode The minimum number of records allowed in a leaf
     *                          node to make it full.
     */
    BinTree(int worldSize, int maxRecordsPerNode) {
        this.worldSize = worldSize;
        this.maxRecordsPerNode = maxRecordsPerNode;
        root = fly;
    }
}
