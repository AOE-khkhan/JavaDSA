/**
 * Internal node for BinTree object.
 * 
 * @author  Bimal Gaudel
 * 
 * @version Apr 22, 2020
 *
 */
public class BinTreeNodeInternal implements BinTreeNode {
    /**
     * Check if a node is a leaf node.
     * 
     * @return False as it is an internal node.
     */
    public boolean isLeaf() {
        return false;
    }
}
