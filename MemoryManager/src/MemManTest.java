import student.TestCase;

/**
 * @author Bimal Gaudel
 * @version 0.1
 */
public class MemManTest extends TestCase {
    /**
     * Sets up the tests that follow. In general, used for initialization
     */
    public void setUp() {
        // Nothing Here
    }

    /**
     * Get code coverage of the class declaration.
     */
    public void testRInit() {
        MemMan manager = new MemMan();
        assertNotNull(manager);
        MemMan.main(null);
    }
}
