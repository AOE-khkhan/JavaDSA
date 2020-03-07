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
                children[i] = children[i].insertRecord(record, childCanvas);
            }
        }
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
        for (int i = 0; i < children.length; ++i) {
            SquareCanvas childCanvas = canvas.getQuadrant(i);
            if (childCanvas.intersects(record.getRectangle())) {
                children[i] = children[i].removeRecord(record, childCanvas);
            }
        }

        if (isEmpty()) {
            return FLYWEIGHT;
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
        for (int i = 0; i < children.length; ++i) {
            if (children[i].hasRecord(record)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if the internal node is empty. An internal node is empty when all
     * four of its children nodes point to null pointer or a flyweight as in our
     * case.
     * 
     * @return True if the internal node is empty.
     */
    @Override
    public boolean isEmpty() {
        for (QuadTree child : children) {
            if (child != FLYWEIGHT) {
                return false;
            }
        }
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
     * A flyweight object used for representing leaf nodes with no rectangle
     * data in them.
     */
    public static final QuadTreeFlyweightNode FLYWEIGHT =
            new QuadTreeFlyweightNode();
}
