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
        children = new BinTreeNode[] {BinTreeNodeFlyweight.getInstance(),
                BinTreeNodeFlyweight.getInstance()};
    }


    /**
     * Getter for the children array.
     * 
     * @return The array of children node.
     */
    protected BinTreeNode[] getChildren() {
        return children;
    }


    /**
     * Check if a node is a leaf node.
     * 
     * @return False as it is an internal node.
     */
    @Override
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
    @Override
    public BinTreeNode insert(AirObject airObject, BinBox box, int nodeLevel) {
        // The descriminator dimension across which the split should occur
        // for current node.
        int descriDim = nodeLevel % Box.NUM_DIMS;

        for (int ii = 0; ii < children.length; ++ii) {
            // if each children node's bin box
            // intersects the air object's box,
            // insert the object and update the child
            BinBox childBinBox = box.split(descriDim, ii);
            if (airObject.getBox().intersects(childBinBox)) {
                children[ii] = children[ii].insert(airObject, childBinBox,
                        nodeLevel + 1);
            }
        }

        return this;
    }


    /**
     * Delete an AirObject from the tree.
     * 
     * @param  airObject The AirObject to be deleted.
     * @param  box       The Box information passed to current node to help
     *                   locate the AirObject.
     * @param  nodeLevel The level of the current node in the tree. The root is
     *                   at the level zero and the level increases down the
     *                   tree.
     * 
     * @return           The node after deletion.
     */
    @Override
    public BinTreeNode delete(AirObject airObject, BinBox box, int nodeLevel) {
        // delete object from children
        for (int descriDim = nodeLevel % Box.NUM_DIMS, ii =
                0; ii < getChildren().length; ++ii) {
            BinBox childBin = box.split(descriDim, ii);
            if (childBin.intersects(airObject.getBox())) {
                // child's box intersects the object's box
                // delete object from the child and update
                children[ii] =
                        children[ii].delete(airObject, childBin, nodeLevel + 1);
            }
        }

        // After deletion lets check if all of the children have
        // turned to be leaf nodes or not. If at least one of the
        // children is not a leaf node then we can't merge.
        for (BinTreeNode child : children) {
            if (!child.isLeaf()) {
                // found a child that's not leaf
                return this;
            }
        }

        //
        // At this point all the children are leaf nodes. We now check if the
        // decomposition rule is violated or not.
        //
        // Decomposition rule for this project says there can be at most three
        // records in a leaf node without a common intersection among all of
        // their boxes. However there can be arbitrary number of records in a
        // node if all the record's boxes have a common intersection.
        //
        //
        //@formatter:off
        // We will collect all the unique record entries from the children.
        // - If there are no more than three records, we must merge.
        // - If there are more than three records but all of them share
        //   a common intersection, we must merge.
        //@formatter:on

        LinkedList<AirObject> uniqueRecords = new LinkedList<AirObject>();
        for (BinTreeNode child : children) {
            // get the child's records
            LinkedList<AirObject> childRecords =
                    ((BinTreeNodeLeaf) child).getRecords();

            // @formatter:off
            for (childRecords.moveToHead();
                    !childRecords.atEnd();
                    childRecords.curseToNext()) {
            //@formatter:on

                AirObject currObject = childRecords.yieldCurrNode();
                if (!uniqueRecords.exists(currObject)) {
                    // insert if it's unique
                    uniqueRecords.insert(currObject);
                }

            } // for all records in a child
        } // for all children

        if (uniqueRecords.getCount() <= BinTreeNodeLeaf.MAX_RECORDS) {
            // Too few records in multiple leaf nodes, thus decomposition
            // rule is violated. Merge and return.
            return getMergedNode(uniqueRecords, box, nodeLevel);
        }

        // More than max records are in multiple leaf nodes. If they all share a
        // common intersection, the decomposition rule is violated and the nodes
        // should be merged.

        // Set the initial intersection to be the first object's box, then curse
        // through the end of the list computing intersection between previous
        // intersection and the current record's box.
        uniqueRecords.moveToHead();
        Box isecBox = uniqueRecords.yieldCurrNode().getBox();
        for (; !uniqueRecords.atEnd(); uniqueRecords.curseToNext()) {
            Box currBox = uniqueRecords.yieldCurrNode().getBox();
            if (!currBox.intersects(isecBox)) {
                // Found an object whose box doesn't intersect with all the
                // records' boxes that came before it. Can't be merged.
                return this;
            }
            // update the intersection
            isecBox = isecBox.getIntersection(currBox);
        }

        // If control reaches this point, all the records do have a common
        // intersection. Must be merged.
        return getMergedNode(uniqueRecords, box, nodeLevel);
    } // method delete record


    /**
     * Insert records to a new BinTreeNode.
     * 
     * @param records   LinkedList of AirObjects'.
     * @param box       The BinBox for the root of the new node.
     * @param nodeLevel The node level for the root of the new node.
     */
    private BinTreeNode getMergedNode(LinkedList<AirObject> records, BinBox box,
            int nodeLevel) {
        BinTreeNode merged = BinTreeNodeFlyweight.getInstance();
        for (records.moveToHead(); !records.atEnd(); records.curseToNext()) {
            merged = merged.insert(records.yieldCurrNode(), box, nodeLevel);
        }
        return merged;
    }
}
