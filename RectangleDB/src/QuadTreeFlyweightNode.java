/**
 * A flyweight object used for representing leaf nodes with no rectangle
 * data in them.
 * 
 * @author  Bimal Gaudel
 * 
 * @version Feb 2020
 */
public class QuadTreeFlyweightNode extends QuadTreeLeafNode {
    /**
     * Insert a RectangleRecord by creating a leaf node.
     * 
     * @param  record A RectangleRecord object to be inserted.
     * @param  canvas The canvas where the insertion should occur.
     * 
     * @return        A QuadTreeLeafNode pointer.
     */
    @Override
    public QuadTree insertRecord(RectangleRecord record, SquareCanvas canvas) {
        return new QuadTreeLeafNode(record);
    }

    /**
     * Remove an existing RectangleRecord in the tree.
     * 
     * @param  record RectangleRecord object to be removed that.
     * @param  canvas The canvas where the deletion should occur.
     * 
     * @return        The quadtree root after insertion.
     */
    @Override
    public QuadTree removeRecord(RectangleRecord record, SquareCanvas canvas) {
        // because no data exists in a flyweight
        // just return itself
        return this;
    }

    /**
     * Check if a record exists in the tree.
     * 
     * @param  record A RectangleRecord object being tested for duplicacy.
     * 
     * @return        False. Flyweight never has a record.
     */
    @Override
    public boolean hasRecord(RectangleRecord record) {
        // flyweight never stores any data
        return false;
    }

    /**
     * Check if the node is empty.
     * 
     * @return True as a flyweight is always empty.
     */
    @Override
    public boolean isEmpty() {
        // flyweight is always empty
        return true;
    }

    /**
     * Print the rectangles present in the tree by pre-order traversal.
     * 
     * @param  canvas A SquareCanvas object to designate which canvas does the
     *                printed rectangle belongs to.
     * 
     * @return        The number of nodes visited.
     */
    @Override
    public int dump(SquareCanvas canvas) {
        System.out
                .println(String.format("Node at %s: Empty", canvas.toString()));
        return 1;
    }
}
