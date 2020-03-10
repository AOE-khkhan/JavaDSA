/**
 * The internal node of a QuadTree.
 *
 * An internal node has exactly four children. @see QuadTree.java to read about
 * the decomposition rule.
 *
 * @author  Bimal Gaudel
 * 
 * @version Feb 2020
 */
public class QuadTreeInternalNode extends QuadTree {
    private QuadTree[] children;

    /** Default constructor. */
    QuadTreeInternalNode() {
        // initialize the four children array
        children = new QuadTree[SquareCanvas.NUM_QUADRANTS];
        // all the children are empty leaf nodes
        // for that a flyweight object is used
        for (int pos = 0; pos < children.length; ++pos) {
            children[pos] = QuadTreeInternalNode.FLYWEIGHT;
        }
    }

    /**
     * Insert a RectangleRecord in the intermediate node.
     * 
     * @param  record A RectangleRecord object to be inserted.
     * @param  canvas The canvas where the insertion should occur.
     * 
     * @return        The quadtree root after insertion.
     */
    @Override
    public QuadTree insertRecord(RectangleRecord record, SquareCanvas canvas) {

        Rectangle recordRectangle = record.getRectangle();
        for (int i = 0; i < children.length; ++i) {

            SquareCanvas childCanvas = canvas.getQuadrant(i);

            if (childCanvas.intersects(recordRectangle)) {
                // inserting
                children[i] = children[i].insertRecord(record, childCanvas);
            }
        }
        return this;
    }

    /**
     * Remove an existing RectangleRecord in the tree. Removing might trigger
     * merger of nodes.
     * 
     * @param  record RectangleRecord object to be removed that.
     * @param  canvas The canvas where the deltion should occur.
     * 
     * @return        The quadtree root after insertion.
     */
    @Override
    public QuadTree removeRecord(RectangleRecord record, SquareCanvas canvas) {
        for (int i = 0; i < children.length; ++i) {
            SquareCanvas childCanvas = canvas.getQuadrant(i);
            if (childCanvas.intersects(record.getRectangle())) {
                children[i] = children[i].removeRecord(record, childCanvas);
            }
        }

        // loop through children and check if at least one of them is non-leaf
        // then no merger required
        for (QuadTree child : children) {
            if (!child.isLeaf()) {
                return this;
            }
        }

        // all of the children are leaf nodes
        // now decide and merge if we should
        LinkedList<RectangleRecord> recordsToMerge = shouldMerge();
        if (recordsToMerge == null) {
            // no merger required
            return this;
        }
        // now creating a new quadtree with the records to be merged
        QuadTree merged = FLYWEIGHT;
        //@formatter:off
        for (recordsToMerge.moveToHead();
                (!recordsToMerge.atEnd());
                recordsToMerge.curseToNext()) {
        //@formatter:on
            merged = merged.insertRecord(recordsToMerge.yieldNode(), canvas);
        }
        return merged;
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
        for (int i = 0; i < children.length; ++i) {
            if (children[i].hasRecord(record)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if the internal node is empty.
     * 
     * @return False as an internal node can't be empty.
     */
    @Override
    public boolean isEmpty() {
        return false;
    }

    /**
     * Check if current node is a leaf node.
     * 
     * @return False for it is an internal node.
     */
    @Override
    public boolean isLeaf() {
        return false;
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
        int numVisited = 1; // visited this internal node
        for (int i = 0; i < children.length; ++i) {
            SquareCanvas childCanvas = canvas.getQuadrant(i);
            if (rectangle.intersects(childCanvas)) {
                numVisited += children[i].searchRegion(rectangle, childCanvas);
            }
        }
        return numVisited; // this node plus any child nodes visit count
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
        int visitCount = 1; // visited this node
        for (int i = 0; i < children.length; ++i) {
            SquareCanvas childCanvas = canvas.getQuadrant(i);
            visitCount += children[i].listIntersections(childCanvas);
        }
        return visitCount;
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
        System.out.println(
                String.format("Node at %s internal:", canvas.toString()));
        int visitCount = 1; // visited this node
        for (int i = 0; i < children.length; ++i) {
            visitCount += children[i].dump(canvas.getQuadrant(i));
        }
        return visitCount;
    }

    /**
     * Decide whether the children of this internal nodes should be merged.
     * Decision is based on the decomposition rule given in the project
     * description.
     * 
     * @return Null if no merger is rquired, linked list of unique rectangle
     *         records if the decomposition rule is violated and merger should
     *         occur.
     */
    private LinkedList<RectangleRecord> shouldMerge() {

        // if the rectangle records are in more than one node the decomposition
        // rule is violated if there are more than two rectangles and not alll
        // of them have an intersection

        // find the unique records in the non-empty leaves
        LinkedList<RectangleRecord> uniqueRecords =
                new LinkedList<RectangleRecord>();
        for (QuadTree child : children) {
            if (child.isEmpty()) {
                continue;
            }
            QuadTreeLeafNode leaf = (QuadTreeLeafNode) child;
            LinkedList<RectangleRecord> records = leaf.getRecords();
            //@formatter:off
            for (records.moveToHead();
                    (!records.atEnd());
                    records.curseToNext()) {
            //@formatter:on
                RectangleRecord rec = records.yieldNode();
                if (!uniqueRecords.exists(rec)) {
                    uniqueRecords.append(rec);
                }
            }
        }
        // if there are less than three records merger is must
        if (uniqueRecords.getCount() < 3) {
            return uniqueRecords;
        }

        // now checking if all of rectangles (more than three) have a common
        // intersection
        //
        // set the common intersection to be the first rectangle
        Rectangle isec = uniqueRecords.yieldNode().getRectangle();
        uniqueRecords.moveToHead();
        //@formatter:off
        for (uniqueRecords.moveToHead();
                (!uniqueRecords.atEnd());
                uniqueRecords.curseToNext()) {
        //@formatter:on
            Rectangle inListRectangle =
                    uniqueRecords.yieldNode().getRectangle();
            if (!isec.intersects(inListRectangle)) {
                // found at least one non-intersecting rectangle
                // should not be merged
                return null;
            }
            isec = isec.getIntersection(inListRectangle);
        }
        // if we are here, it means all of the rectangles have a common
        // intersection so should be merged
        return uniqueRecords;
    }

    /**
     * A flyweight object used for representing leaf nodes with no rectangle
     * data in them.
     */
    public static final QuadTreeFlyweightNode FLYWEIGHT =
            new QuadTreeFlyweightNode();
}
