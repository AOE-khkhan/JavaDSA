/**
 * An abstract node for BinTree object.
 * 
 * @author  Bimal Gaudel
 * 
 * @version Apr 22, 2020
 *
 */
public interface BinTreeNode {


    /**
     * Check if the node is a leaf node.
     * 
     * @return True if the node is a leaf node.
     */
    public boolean isLeaf();


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
    public BinTreeNode insert(AirObject airObject, BinBox box, int nodeLevel);


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
     * @return           The node after the deletion.
     */
    public BinTreeNode delete(AirObject airObject, BinBox box, int nodeLevel);
}
