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
}
    // this.splitBlock(0, this.poolSize, 1);
    // this.splitBlock(16, 16, 1);
    // System.out.println(this.toString());
    // System.out.println("doubling the size..");
    // this.doublePoolSize();
    // System.out.println(this.toString());
    // System.out.println("merging buddies..");
    // this.mergeBuddy(1, 16);
    // System.out.println(this.toString());
