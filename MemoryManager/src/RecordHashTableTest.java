import student.TestCase;

// -------------------------------------------------------------------------
/**
 * Test the RecordHashTable class.
 */
public class RecordHashTableTest extends TestCase {
  private RecordHashTable sampleTable;

  public void setUp() {
    sampleTable = new RecordHashTable(10);
  }

  public void testConstructors() {
    RecordHashTable aTable = new RecordHashTable();
    assertNotNull(aTable);
    assertEquals(aTable.getSize(), RecordHashTable.MIN_SIZE);
    aTable = new RecordHashTable(-40);
    assertEquals(aTable.getSize(), RecordHashTable.MIN_SIZE);
  }

  public void testGetSize() {
    assertEquals(sampleTable.getSize(), 10);
  }

  public void testAddRecord() {
    sampleTable.addRecord(new Record("Poochie"));
    sampleTable.addRecord(new Record("Pie"));
    sampleTable.deleteRecord("Pie");
    sampleTable.addRecord(new Record("Pie"));
    assertEquals(sampleTable.getCount(), 2);
  }

  public void testGetRecord() {
    sampleTable.addRecord(new Record("Charlie"));
    sampleTable.addRecord(new Record("Max"));
    sampleTable.addRecord(new Record("Buddy"));

    Record found = sampleTable.getRecord("Charlie");
    assertNotNull(found);
    assertEquals(found.getName(), "Charlie");
    found.addRecordKeyVal(new RecordKeyVal("Class", "Animal"));
    assertNotNull(found.findRecordKeyVal("Class"));
    found = sampleTable.getRecord("Toby");
    assertNull(found);
  }

  public void testDoubleTableSize() {
    RecordHashTable aTable = new RecordHashTable(3);
    aTable.addRecord(new Record("Charlie"));
    assertEquals(aTable.getSize(), 3);
    assertEquals(aTable.getCount(), 1);
    aTable.deleteRecord("Charlie");
    assertEquals(aTable.getSize(), 3);
    assertEquals(aTable.getCount(), 0);
    aTable.addRecord(new Record("Charlie"));
    assertEquals(aTable.getSize(), 3);
    assertEquals(aTable.getCount(), 1);
    aTable.addRecord(new Record("Poochie"));
    assertEquals(aTable.getSize(), 6);
    // assertEquals(aTable.getCount(), 2);
  }

  public void testPrintHashTable() {
    sampleTable.addRecord(new Record("Death Note"));
    sampleTable.addRecord(new Record("Can you handle?"));
    sampleTable.printHashTable();
    String output = systemOut().getHistory();
    assertFuzzyEquals(output, "|Can you handle?| 0\n|Death Note| 4\nTotal records: 2");
  }

  public void testDeleteRecord() {
    sampleTable.addRecord(new Record("Bailey"));
    sampleTable.addRecord(new Record("Frankie"));
    boolean deleted = sampleTable.deleteRecord("Bailey");
    assertEquals(deleted, true);
    deleted = sampleTable.deleteRecord("Maggie");
    assertEquals(deleted, false);
  }
}
