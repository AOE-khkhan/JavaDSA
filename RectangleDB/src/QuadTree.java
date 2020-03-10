/**
 * A QuadTree abstract class.
 *
 * Internal and the leaf nodes derive from this class.
 * 
 * A QuadTree is a full tree that branches four way. The key property of the
 * Quadtree is its decomposition rule. The decomposition rule for this project
 * is:
 * A leaf node will split into four when
 * (a) There are three or more rectangles in the node, such that not all
 * rectangles in the node overlap a single point.
 *
 * (b) Four sibling leaf nodes will merge together to form a single leaf
 * node whenever deleting a rectangle results in a set of rectangles among
 * the four leaves that does not violate the decomposition rule.
 *
 * It is possible for a single deletion to cause a cascading series of node
 * merges.
 *
 * Note that two rectangles that are adjacent to each other do not overlap.
 * For example, rectangles (5, 5, 5, 5) and (5, 10, 5, 5) do not overlap.
 *
 * @author  Bimal Gaudel
 * 
 * @version Feb 2020
 */
abstract class QuadTree {
    /**
     * Insert a RectangleRecord in the tree.
     * 
     * @param  record A RectangleRecord object to be inserted.
     * @param  canvas The canvas where the insertion should occur.
     * 
     * @return        The quadtree root after insertion.
     */
    public abstract QuadTree insertRecord(RectangleRecord record,
            SquareCanvas canvas);

    /**
     * Remove an existing RectangleRecord in the tree.
     * 
     * @param  record RectangleRecord object to be removed that.
     * @param  canvas The canvas where the deltion should occur.
     * 
     * @return        The quadtree root after insertion.
     */
    public abstract QuadTree removeRecord(RectangleRecord record,
            SquareCanvas canvas);

    /**
     * Check if a record exists in the tree.
     * 
     * @param  record A RectangleRecord object being tested for duplicacy.
     * 
     * @return        True if the record exists in the tree.
     */
    public abstract boolean hasRecord(RectangleRecord record);

    /**
     * Check if the tree is empty.
     * 
     * @return True if the tree is empty.
     */
    public abstract boolean isEmpty();

    /**
     * Check if current node is a leaf node.
     * 
     * @return True if the tree node is a leaf.
     */
    public abstract boolean isLeaf();

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
    public abstract int searchRegion(Rectangle rectangle, SquareCanvas canvas);

    /**
     * List the overlapping rectangles in the tree.
     * 
     * @param  canvas SquareCanvas object is the quadrant information of the
     *                current that guides when to print to avoid duplicacy.
     * 
     * @return        Number of nodes visited.
     */
    public abstract int listIntersections(SquareCanvas canvas);

    /**
     * Print the rectangles present in the tree by pre-order traversal.
     * 
     * @param  canvas A SquareCanvas object to designate which canvas does the
     *                printed rectangle belongs to.
     * 
     * @return        The number of nodes visited.
     */
    public abstract int dump(SquareCanvas canvas);
}
