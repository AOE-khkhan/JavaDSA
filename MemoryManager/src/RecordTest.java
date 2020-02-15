//------------------------------------------------------------------------------

import student.TestCase;

/**
 * Test the class Record.
 * 
 * @author Bimal Gaudel
 * @version 2020-02-14
 */
public class RecordTest extends TestCase {
  /** A Record object used for testing. */
  private Record aRecord;

  /** Sets up the tests that follow. */
  public void setUp() {
    aRecord = new Record("Death Note");
  }

  /** Test the construction. */
  public void testConstruction() {
    assertNotNull(aRecord);
    assertEquals(aRecord.isEmpty(), true);
  }

  /** Test the getName method. */
  public void testGetName() {
    assertEquals(aRecord.getName(), "Death Note");
  }

  /** Test isEmpty. */
  public void testIsEmpty() {
    assertEquals(aRecord.isEmpty(), true);
    aRecord.addHandle(new MemoryHandle(0, 2));
    assertEquals(aRecord.isEmpty(), false);
  }

  /** Test the methods moveToFirstHandle, curseToNextHandle and yieldHandle. */
  public void testCursuryMethods() {
    aRecord.addHandle(new MemoryHandle(0, 2));
    aRecord.addHandle(new MemoryHandle(2, 2));
    aRecord.addHandle(new MemoryHandle(4, 4));
    aRecord.addHandle(new MemoryHandle(8, 8));

    aRecord.moveToFirstHandle();
    assertEquals(aRecord.yieldHandle(), new MemoryHandle(0, 2));

    aRecord.curseToNextHandle();
    assertEquals(aRecord.yieldHandle(), new MemoryHandle(2, 2));

    aRecord.curseToNextHandle();
    assertEquals(aRecord.yieldHandle(), new MemoryHandle(4, 4));

    aRecord.curseToNextHandle();
    assertEquals(aRecord.yieldHandle(), new MemoryHandle(8, 8));

    aRecord.curseToNextHandle();
    assertNull(aRecord.yieldHandle());

    aRecord.curseToNextHandle();
    assertNull(aRecord.yieldHandle());
  }

  /** Test the method removeHandle. */
  public void testRemoveHandle() {
    aRecord.addHandle(new MemoryHandle(0, 2));
    aRecord.addHandle(new MemoryHandle(2, 2));
    aRecord.addHandle(new MemoryHandle(4, 4));
    aRecord.addHandle(new MemoryHandle(8, 8));

    aRecord.removeHandle(new MemoryHandle(0, 2));
    aRecord.removeHandle(new MemoryHandle(4, 4));

    aRecord.moveToFirstHandle();
    assertEquals(aRecord.yieldHandle(), new MemoryHandle(2, 2));
    aRecord.curseToNextHandle();
    assertEquals(aRecord.yieldHandle(), new MemoryHandle(8, 8));
  }
}
