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
  }

  /**
   * Test the main method
   */
  public void testMain() {
    String[] args = { "0", "15", "src/commands.txt" };
    MemMan.main(args);
    // String output = systemOut().getHistory();
    // assertFuzzyEquals(output,
    //     "|Death Note| not deleted because it does not exist in the Name database.\n|Death Note| has been added to the Name database.");
  }
}
