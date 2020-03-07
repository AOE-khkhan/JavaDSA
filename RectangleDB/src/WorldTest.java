import student.TestCase;

/**
 * Test the World class.
 * 
 * @author  Bimal Gaudel
 * 
 * @version Feb 2020
 */
public class WorldTest extends TestCase {
    /** A world object used for testing. */
    private World myWorld;

    /** The root level canvas is obtained from the world object. */
    private SquareCanvas rootCanvas;

    /** Sets up the tests that follow. */
    public void setUp() {
        myWorld = new World();
        rootCanvas = myWorld.getCanvas();
    }

    /** Test the construction of the World object. */
    public void testConstruction() {
        assertNotNull(rootCanvas);
        assertEquals(rootCanvas.getX(), 0);
        assertEquals(rootCanvas.getY(), 0);
        assertEquals(rootCanvas.getSize(), 1024);
    }

    /** Test the setQuadTree method. */
    public void testSetQuadTree() {
        // create a record to insert
        RectangleRecord toInsert = new RectangleRecord("Rec_A", 0, 0, 100, 100);

        // refer to myWorld's quadtree for insertion
        QuadTree workingTree = myWorld.getQuadTree();

        // insert the rectangle by calling insert method on the workingTree
        workingTree = workingTree.insertRecord(toInsert, rootCanvas);

        // verify that myWorld's tree doesn't yet have the rectangle that was
        // just inserted, because we haven't modified its tree yet
        assertFalse(myWorld.getQuadTree().hasRecord(toInsert));

        // now we set the world's quadtree to be workingTree
        // and verify that the rectangle is actually inserted this time
        myWorld.setQuadTree(workingTree);
        assertTrue(myWorld.getQuadTree().hasRecord(toInsert));
    }
}
