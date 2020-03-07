/**
 * The leaf node of a QuadTree.
 *
 * A leaf node stores pointers to the rectangle data. @see QuadTree.java to
 * read about the decomposition rule.
 *
 * @author  Bimal Gaudel
 * 
 * @version Feb 2020
 */
public class QuadTreeLeafNode extends QuadTree {
    /** The RectangleRecord objects this leaf node points. */
    private LinkedList<RectangleRecord> recordList;

    /**
     * Construct an empty leaf node. So that the default constructor of
     * QuadTreeFlyweightNode is defined too
     */
    QuadTreeLeafNode() {
        recordList = null;
    }

    /**
     * Construct a leaf node from a record.
     * 
     * @param record A RectangleRecord object to initialize this leaf node with.
     */
    QuadTreeLeafNode(RectangleRecord record) {
        recordList = new LinkedList<RectangleRecord>();
        recordList.append(record);
    }

    /**
     * Insert a RectangleRecord in the leaf node.
     * 
     * @param  record A RectangleRecord object to be inserted.
     * @param  canvas The canvas where the insertion should occur.
     * 
     * @return        The quadtree root after insertion.
     */
    @Override
    public QuadTree insertRecord(RectangleRecord record, SquareCanvas canvas) {
        // implements the decomposition rule
        // the decomposition rule says there might be as many rectangles in a
        // leaf if all of them overlap at least at one point
        // however if there are 3 or more rectangles and at least one of them
        // does not overlap with all of them, then split the node

        // start by just appending the record the record list
        recordList.append(record);
        // if less than three records, nothing needs to be done
        if (recordList.getCount() < 3) {
            return this;
        }
        // at this point there are at least three rectangle records
        // in the list.
        // now we check if the splitting should be carried out
        //
        if (shouldSplit()) {
            QuadTree splitTree = new QuadTreeInternalNode();
            // iterate through the existing records and insert them into the new
            recordList.moveToHead();
            while (!recordList.atEnd()) {

                //@formatter:off
                splitTree = splitTree.insertRecord(recordList.yieldNode(),
                                                                    canvas);
                //@formatter:on

                recordList.curseToNext();
            }
            return splitTree;
        }
        // else no splitting required as all of the
        // rectangles overlap with each other
        return this;
    }

    /**
     * Remove an existing RectangleRecord in the tree.
     * 
     * @param  record RectangleRecord object to be removed that.
     * @param  canvas The canvas where the deltion should occur.
     * 
     * @return        The quadtree root after insertion.
     */
    @Override
    public QuadTree removeRecord(RectangleRecord record, SquareCanvas canvas) {
        recordList.remove(record);

        if (isEmpty()) {
            return QuadTreeInternalNode.FLYWEIGHT;
        }

        return this;
    }

    /**
     * Check if a record exists in the tree.
     * 
     * @param  record A RectangleRecord object being tested for duplicacy.
     * 
     * @return        True if the record exists in the tree.
     */
    @Override
    public boolean hasRecord(RectangleRecord record) {
        recordList.moveToHead();
        while (!recordList.atEnd()) {
            if (recordList.yieldNode().equals(record)) {
                return true;
            }
            recordList.curseToNext();
        }
        return false;
    }

    /**
     * Check if the leaf node is empty. A leaf node is empty when the records
     * list is empty.
     * 
     * @return True if no records exist in the record list, ie. the leaf node is
     *         empty.
     */
    @Override
    public boolean isEmpty() {
        return recordList.isEmpty();
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
        recordList.moveToHead();
        System.out.println(String.format("Node at %s:", canvas.toString()));
        while (!recordList.atEnd()) {
            System.out.println(recordList.yieldNode().toString());
            recordList.curseToNext();
        }
        return 1; // no. of nodes visited (counts only this node)
    }

    /**
     * Check if the leaf node should split according to the decomposition rule.
     * 
     * @return True if the decomposition rule is met.
     */
    private boolean shouldSplit() {
        // iterating through the rectangle records present
        // and seeing if each of them intersects with each of themselves
        //
        // move to the head node and set it as the intersection to begin with
        recordList.moveToHead();
        Rectangle intersection = recordList.yieldNode().getRectangle();
        //
        // starting from the second node, update the intersection
        // as soon as a non-intersection is encountered, return true
        // to indicate that splitting should happen
        while (!recordList.atEnd()) {
            //@formatter:off
            // System.out.println("intersection now: "
            //         + intersection.getX() + ", "
            //         + intersection.getY() + ", "
            //         + intersection.getWidth() + ", "
            //         + intersection.getHeight());
            //@formatter:on
            // record rectangle pointed by current node of the list
            Rectangle inListRectangle = recordList.yieldNode().getRectangle();

            // found a set of rectangles which do not overlap with each other
            if (!intersection.intersects(inListRectangle)) {
                return true;
            }

            intersection = intersection.getIntersection(inListRectangle);
            //
            recordList.curseToNext();
        }
        // At this point all the rectangles in the record list overlap with each
        // other. No need to split up the leaf node.
        return false;
    }
}
