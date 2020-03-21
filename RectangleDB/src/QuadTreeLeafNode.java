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
     * Getter of the rectangle record list.
     * 
     * @return Rectangle record list of the leaf node.
     */
    public final LinkedList<RectangleRecord> getRecords() {
        return recordList;
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

            //@formatter:off
            for (recordList.moveToHead();
                (!recordList.atEnd());
                recordList.curseToNext()) {

                splitTree = splitTree.insertRecord(recordList.yieldNode(),
                                                                    canvas);
                //@formatter:on
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
     * @param  canvas The canvas where the deletion should occur.
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

        //@formatter:off
        for (recordList.moveToHead();
                (!recordList.atEnd());
                recordList.curseToNext()) {
        //@formatter:on
            if (recordList.yieldNode().equals(record)) {
                return true;
            }
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
     * Check if current node is a leaf node.
     * 
     * @return True for it is a leaf node.
     */
    @Override
    public boolean isLeaf() {
        return true;
    }

    /**
     * Print rectangles that intersect with the query @c rectangle.
     * 
     * @param  rectangle A rectangle object with which intersections of existing
     *                   rectangles will be checked.
     *
     * @param  canvas    A SquareCanvas object to guide the visiting of nodes.
     * 
     * @return           Number of nodes visited.
     */
    @Override
    public int searchRegion(Rectangle rectangle, SquareCanvas canvas) {
        // iter through the existing rectangles and print them out if they
        // intersect with the param rectangle
        // @formatter:off
        for (recordList.moveToHead();
             (!recordList.atEnd());
             recordList.curseToNext()) {
        // @formatter:on

            RectangleRecord record = recordList.yieldNode();

            if (record.getRectangle().intersects(rectangle)) {
                Rectangle isecRec =
                        record.getRectangle().getIntersection(rectangle);
                // avoid duplicate printing
                if (canvas.hasPoint(isecRec.getX(), isecRec.getY())) {
                    System.out.println("Rectangle found: " + record.toString());
                }
            }
        }
        return 1; // visited this leaf node
    }

    /**
     * List the overlapping rectangles in the tree.
     * 
     * @param  canvas SquareCanvas object is the quadrant information of the
     *                current that guides when to print to avoid duplicacy.
     * 
     * @return        Number of nodes visited.
     */
    @Override
    public int listIntersections(SquareCanvas canvas) {
        int outerLoopCount = 0;
        //@formatter:off
        for (recordList.moveToHead();
            (!recordList.atEnd());
            recordList.curseToNext()) {
            for (int innerLoopCount =
                    outerLoopCount + 1;
                    innerLoopCount < recordList.getCount();
                    ++innerLoopCount) {
            // formatter:on
                // a pair of rectangles to check intersection for.
                // this rectangle record is received by cursing the linked list
                RectangleRecord rec1 = recordList.yieldNode();
                // this rectangle record is received by index number
                RectangleRecord rec2 = recordList.yieldIndex(innerLoopCount);
                // check if the two rectangles intersect
                if (rec1.getRectangle().intersects(rec2.getRectangle())) {
                    Rectangle isecRec = rec1.getRectangle()
                                        .getIntersection(rec2.getRectangle());
                    // so they intersect, but is it the right place to print
                    // them as a intersecting pair?
                    if (canvas.hasPoint(isecRec.getX(), isecRec.getY())) {
                        System.out.println(rec1 + " and " + rec2);
                    }
                }
            }
            ++outerLoopCount;
        }
        //
        return 1; // visited this leaf node
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
        System.out.println(String.format("Node at %s:", canvas.toString()));
            //@formatter:off
        for (recordList.moveToHead();
            (!recordList.atEnd());
            recordList.curseToNext()) {
            //@formatter:on
            System.out.println(recordList.yieldNode());
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
        //@formatter:off
        for (recordList.moveToHead();
                (!recordList.atEnd());
                recordList.curseToNext()) {
        //@formatter:on
            // record rectangle pointed by current node of the list
            Rectangle inListRectangle = recordList.yieldNode().getRectangle();

            // found a set of rectangles which do not overlap with each other
            if (!intersection.intersects(inListRectangle)) {
                return true;
            }

            intersection = intersection.getIntersection(inListRectangle);
            //
        }
        // At this point all the rectangles in the record list overlap with each
        // other. No need to split up the leaf node.
        return false;
    }
}
