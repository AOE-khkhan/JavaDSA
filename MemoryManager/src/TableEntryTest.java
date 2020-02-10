import student.TestCase;

// -------------------------------------------------------------------------
/**
 * Test the TableEntry class.
 * 
 * @author Bimal Gaudel
 * @version 0.1
 */
public class TableEntryTest extends TestCase {
  /**
   * A TableEntry object used in the methods.
   */
  private TableEntry anEntry;

  /**
   * Sets up the tests that follow.
   */
  public void setUp() {
    anEntry = new TableEntry(new Record("Poochie"));
  }

  /**
   * Test the constructor methods of the TableEntry class.
   */
  public void testConstructors() {
    TableEntry sampleEntry = new TableEntry();
    assertNotNull(sampleEntry);
  }

  /**
   * Test the Record getter.
   */
  public void testGetRecord() {
    Record gotRecord = anEntry.getRecord();
    assertEquals(gotRecord.getName(), "Poochie");
  }

  /**
   * Test the method to check if the object is active.
   */
  public void testIsActive() {
    assertEquals(anEntry.isActive(), true);
  }

  /**
   * Test deletion of the TableEntry object.
   */
  public void testDeletion() {
    anEntry.markDeleted();
    assertEquals(anEntry.isDeleted(), true);
  }

  /**
   * Test the method to check if the object is free.
   */
  public void testIsFree() {
    TableEntry sampleEntry = new TableEntry();
    assertEquals(sampleEntry.isFree(), true);
  }

  /**
   * Test the method to get the name field of the Record object present in the
   * TableEntry object.
   */
  public void testGetRecordName() {
    assertEquals(anEntry.getRecordName(), anEntry.getRecord().getName());
  }
}
