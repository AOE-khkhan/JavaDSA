/**
 * Internal node for BinTree object.
 * 
 * @author  Bimal Gaudel
 * 
 * @version Apr 22, 2020
 *
 */
public class BinTreeNodeInternal implements BinTreeNode {
    /** The children nodes of this internal node. */
    private BinTreeNode[] children;

    /** Construct an internal node. */
    public BinTreeNodeInternal() {
        //@formatter:off
        children = new BinTreeNode[] {BinTreeNodeFlyweight.getInstance(),
                                      BinTreeNodeFlyweight.getInstance()};
        //@formatter:on
    }

    /**
     * Getter for the children array.
     * 
     * @return The array of children node.
     */
    public BinTreeNode[] getChildren() {
        return children;
    }

    /**
     * Check if a node is a leaf node.
     * 
     * @return False as it is an internal node.
     */
    public boolean isLeaf() {
        return false;
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
    public BinTreeNode insert(AirObject airObject, BinBox box, int nodeLevel) {
        // The descriminator dimension across which the split should occur
        // for current node.
        int descriDim = nodeLevel % Box.numDims;

        // indexes for first and second
        int first = 0;
        int second = 1;

        BinBox firstBin = box.split(descriDim, first);
        BinBox secondBin = box.split(descriDim, second);

        //@formatter:off
        if (airObject.getBox().intersects(firstBin)) {
            children[first] =
                    children[first].insert(airObject, firstBin, nodeLevel + 1);
        }

        if (airObject.getBox().intersects(secondBin)) {
            children[second] =
                children[second].insert(airObject, secondBin, nodeLevel + 1);
        }
        //@formatter:off
        return this;

    }
}
