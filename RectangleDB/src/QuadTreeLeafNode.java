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

    /** Construct a leaf node. */
    QuadTreeLeafNode() {
        recordList = new LinkedList<RectangleRecord>();
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
    public QuadTree insertRectangle(RectangleRecord record,
            SquareCanvas canvas) {
        // implements the decomposition rule
        // the decomposition rule says there might be as many rectangles in a
        // leaf if all of them overlap at least at one point
        // however if there are more than 3 rectangles and at least one of them
        // does not overlap with all of them, then split the node
        if (recordList.getCount() < 3) {
            recordList.append(record);
            return this;
        }
        // at this point there are at least three rectangle records
        // in the list. now we check if the rectangle to be inserted has no
        // intersection with at least one of the existing rectangles
        // if that turns out to be true, then we split up this leaf node
        if (shouldSplit(record.getRectangle())) {
            QuadTree splitTree = new QuadTreeInternalNode();
            // iterate through the existing records and insert them into the new
            recordList.moveToHead();
            while (!recordList.atEnd()) {
                splitTree.insertRectangle(recordList.yieldNode(), canvas);
                recordList.curseToNext();
            }
            // don't forget to insert the record passed as the param
            splitTree.insertRectangle(record, canvas);
            return splitTree;
        }
        // if we are here, it means the rectangle to be inserted intersects with
        // all of the existing rectangles, no need to split, just append the
        // record and return
        recordList.append(record);
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
    public QuadTree removeRectangle(RectangleRecord record,
            SquareCanvas canvas) {
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
        System.out.println(String.format("Node at %d, %d, %d:", canvas.getX(),
                canvas.getY(), canvas.getSize()));
        while (!recordList.atEnd()) {
            System.out.println(recordList.yieldNode().toString());
            recordList.curseToNext();
        }
        return 1;
    }

    /**
     * Check if a rectangle doesn't intersect any of the rectangles present in a
     * record list.
     * 
     * @param  toCheck The rectangle to be checked for intersection with.
     * 
     * @return         True if the decomposition rule is met.
     */
    private boolean shouldSplit(Rectangle toCheck) {
        // iterating through the rectangle records present
        // and seeing if all of them intersect with each other
        //
        // move to the head node and set it as the intersection to begin with
        recordList.moveToHead();
        Rectangle intersection = recordList.yieldNode().getRectangle();
        //
        // starting from the second node, update the intersection
        // if not all of them intersect, return false
        recordList.curseToNext();
        while (!recordList.atEnd()) {
            // record rectangle pointed by current node of the list
            Rectangle inListRectangle = recordList.yieldNode().getRectangle();

            // found a set of rectangles which do not overlap with each other
            if (!intersection.intersectsRectangle(inListRectangle)) {
                return true;
            }

            intersection = intersection.getIntersection(inListRectangle);
            //
            recordList.curseToNext();
        }
        // At this point all the rectangles in the record list overlap with each
        // other. Now we decide by checking if the param rectangle also overlaps
        // with them all. If it does, there's no point in splitting up, else we
        // should split.
        return !intersection.intersectsRectangle(toCheck);
    }
}
