/**
 * Flyweight node to represent empty nodes for the BinTree class.
 * 
 * 
 * @author  Bimal Gaudel
 * 
 * @version Apr 22, 2020
 */
public final class BinTreeNodeFlyweight extends BinTreeNodeLeaf {


    /** The singleton flyweight instance. */
    private static final BinTreeNodeFlyweight SINGLETON_FLYWEIGHT =
            new BinTreeNodeFlyweight();


    /** Default constructor made private for flyweight singleton. */
    private BinTreeNodeFlyweight() {
        // empty
    }


    /**
     * Insert an AirObject into the tree.
     * 
     * @param  airObject The AirObject to be inserted.
     * @param  box       The Box information passed to current node to help
     *                   decide where to insert the AirObject.
     * @param  nodeLevel The level of the current node in the tree. The root is
     *                   at the level zero and the level increases down the
     *                   tree.
     * 
     * @return           The node after the insertion.
     */
    @Override
    public BinTreeNode insert(AirObject airObject, BinBox box, int nodeLevel) {
        return new BinTreeNodeLeaf().insert(airObject, box, nodeLevel);
    }

    /**
     * Get reference to the singleton flyweight object.
     * 
     * @return The singleton flyweight instance.
     */
    public static BinTreeNodeFlyweight getInstance() {
        return SINGLETON_FLYWEIGHT;
    }
}
