import student.TestCase;

// -------------------------------------------------------------------------
/**
 * Test the RecordHashTable class.
 */
public class RecordHashTableTest extends TestCase {
  private RecordHashTable sampleTable;

  public void setUp() { 
    sampleTable = new RecordHashTable(20);
  }

  public void testConstructors() {
    RecordHashTable aTable = new RecordHashTable();
    assertNotNull(aTable);
    assertEquals(aTable.getSize(), RecordHashTable.MIN_SIZE);
    aTable = new RecordHashTable(-40);
    assertEquals(aTable.getSize(), RecordHashTable.MIN_SIZE);
  }

  public void testGetSize(){
    assertEquals(sampleTable.getSize(), 20);
  }

  public void testAddEntry(){
    sampleTable.addEntry(new Record("Poochie"));
    sampleTable.addEntry(new Record("Pie"));
    assertEquals(sampleTable.getCount(), 2);
  }
}
