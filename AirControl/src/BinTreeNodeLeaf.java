/**
 * Leaf node for BinTree class.
 * 
 * @author  Bimal Gaudel
 * 
 * @version Apr 22, 2020
 *
 */
public class BinTreeNodeLeaf implements BinTreeNodeAbstract {

    /** List of records stored by this node. */
    private LinkedList<AirObject> records;

    /** Default constructor. */
    public BinTreeNodeLeaf() {
        records = new LinkedList<AirObject>();
    }

    /**
     * Check if the node is a leaf node.
     * 
     * @return True as it is a leaf node.
     */
    public boolean isLeaf() {
        return true;
    }

    /**
     * Check if the node is empty.
     * 
     * @return True if the node contains no records in it.
     */
    public boolean isEmpty() {
        return records.getCount() == 0;
    }

    /**
     * Check if the node is full when maxRecords are allowed in a node.
     * 
     * @param  maxRecords The maximum number of records allowed in the node.
     * 
     * @return            True if the number of records in the node is equal to
     *                    or greater than maxRecords.
     */
    public boolean isFull(int maxRecords) {
        return records.getCount() >= maxRecords;
    }
}
