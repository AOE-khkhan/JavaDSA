import student.TestCase;

/**
 * Test the MemoryManager class.
 * 
 * @author Bimal Gaudel
 * @version 2020-02-09
 */
public class MemoryManagerTest extends TestCase {
  /** An instance of MemoryManger used for testing. */
  private MemoryManager aMemManager;

  /** Sets up the tests that follow. */
  public void setUp() {
    this.aMemManager = new MemoryManager(5);
  }

  /** Test the constructor of the MemoryManager class. */
  public void testConstructor() {
    assertNotNull(this.aMemManager);
  }

  /** Test private methods. */
  public void testMethods() {
    assertFuzzyEquals("32: 0", aMemManager.toString());
    aMemManager.splitBlock(0, 32, 1);
    String expectedOutput = "1: 0 1\n"
                            + "2: 2\n"
                            + "4: 4\n"
                            + "8: 8\n"
                            + "16: 16";
    assertFuzzyEquals(expectedOutput, aMemManager.toString());
    aMemManager.splitBlock(4, 4, 1);
    expectedOutput = "1: 0 1 4 5\n"
                            + "2: 2 6\n"
                            + "8: 8\n"
                            + "16: 16";
    aMemManager.doublePoolSize();
    assertFuzzyEquals(expectedOutput + "\n32: 32", aMemManager.toString());
  }
}
