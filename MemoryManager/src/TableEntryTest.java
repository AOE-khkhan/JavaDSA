import student.TestCase;

// -------------------------------------------------------------------------
/**
 * Test the TableEntry class.
 */
public class TableEntryTest extends TestCase {
  private TableEntry anEntry;

  public void setUp() {
    anEntry = new TableEntry(new Record("Poochie"));
  }

  public void testConstructors() {
    TableEntry sampleEntry = new TableEntry();
    assertNotNull(sampleEntry);
  }

  public void testGetRecord() {
    assertEquals(anEntry.getRecord().getName(), "Poochie");
  }

  public void testIsActive() {
    assert (anEntry.isActive());
  }

  public void testDeletion() {
    anEntry.markDeleted();
    assert (anEntry.isDeleted());
  }

  public void testIsFree() {
    TableEntry sampleEntry = new TableEntry();
    assert (sampleEntry.isFree());
  }

  // public void
}
