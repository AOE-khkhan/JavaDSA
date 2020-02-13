// ------------------------------------------------------------------------------

import student.TestCase;

/**
 * Test the MemoryHandle class.
 * 
 * @author Bimal Gaudel
 * @version 2020-02-13
 */
public class MemoryHandleTest extends TestCase {
  /** A MemoryHandle object used for testing. */
  private MemoryHandle aHandle;

  /** Sets up the following tests. */
  public void setUp() {
    this.aHandle = new MemoryHandle(1024, 64);
  }

  /** Test the position getter. */
  public void testGetPos() {
    assertEquals(1024, aHandle.getPos());
  }

  /** Test the size getter. */
  public void testGetSize() {
    assertEquals(64, aHandle.getSize());
  }

  /** Test toString method. */
  public void testToString() {
    assertEquals(aHandle.toString(), "64: 1024");
  }
}