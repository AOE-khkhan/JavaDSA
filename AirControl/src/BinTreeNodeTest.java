import student.TestCase;

/**
 * Test the BinTree node classes.
 * 
 * @author  Bimal Gaudel
 * 
 * @version Apr 23, 2020
 */
public class BinTreeNodeTest extends TestCase {
    /** An internal node. */
    private BinTreeNodeInternal internal;

    /** A leaf node. */
    private BinTreeNodeLeaf leaf;

    /** A flyweight node. */
    private BinTreeNodeFlyweight fly;

    /** Sets up the tests that follow. */
    public void setUp() {
        internal = new BinTreeNodeInternal();
        leaf = new BinTreeNodeLeaf();
        fly = BinTreeNodeFlyweight.getInstance();
    }

    /** Test the flyweight node. */
    public void testFlyWeight() {
        assertTrue(fly.isLeaf());
        assertTrue(fly.isEmpty());
        assertFalse(fly.isFull(3));
        assertEquals(fly, BinTreeNodeFlyweight.getInstance());
    }

    /** Test the leaf node. */
    public void testLeafNode() {
        assertTrue(leaf.isLeaf());
        // leaf is empty to start with
        assertTrue(leaf.isEmpty());
        assertFalse(leaf.isFull(3));
    }

    /** Test the internal node. */
    public void testInternalNode() {
        assertFalse(internal.isLeaf());
    }
}
