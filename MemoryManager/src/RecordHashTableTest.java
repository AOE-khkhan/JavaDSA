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

  public void testGetSize() {
    assertEquals(sampleTable.getSize(), 20);
  }

  public void testAddEntry() {
    sampleTable.addEntry(new Record("Poochie"));
    sampleTable.addEntry(new Record("Pie"));
    assertEquals(sampleTable.getCount(), 2);
  }

  public void testDoubleTableSize() {
    sampleTable.addEntry(new Record("Poochie"));
    sampleTable.addEntry(new Record("Pie"));
    sampleTable.addEntry(new Record("Charlie"));
    sampleTable.addEntry(new Record("Max"));
    sampleTable.addEntry(new Record("Buddy"));
    sampleTable.addEntry(new Record("Archie"));
    sampleTable.addEntry(new Record("Teddy"));
    sampleTable.addEntry(new Record("Toby"));
    sampleTable.addEntry(new Record("Bailey"));
    sampleTable.addEntry(new Record("Frankie"));
    // System.out.println("The size of the table is " + sampleTable.getSize());
    // System.out.println("The count of the table is " + sampleTable.getCount());
    // 10 records added so far
    // the table size should be 20, confirm it
    assertEquals(sampleTable.getSize(), 20);
    // now adding one more should double the table size
    sampleTable.addEntry(new Record("Ollie"));
    assertEquals(sampleTable.getSize(), 40);
    assertEquals(sampleTable.getCount(), 11);
    // 11 records added so far
    sampleTable.addEntry(new Record("Jack"));
    sampleTable.addEntry(new Record("Bella"));
    sampleTable.addEntry(new Record("Ruby"));
    sampleTable.addEntry(new Record("Coco"));
    sampleTable.addEntry(new Record("Molly"));
    sampleTable.addEntry(new Record("Bonnie"));
    sampleTable.addEntry(new Record("Rosie"));
    sampleTable.addEntry(new Record("Daisy"));
    sampleTable.addEntry(new Record("Lucy"));
    // 20 records added so far
    // the table size should be still 40
    assertEquals(sampleTable.getSize(), 40);
    assertEquals(sampleTable.getCount(), 20);
    // now adding one more should double the table size
    sampleTable.addEntry(new Record("Luna"));
    assertEquals(sampleTable.getSize(), 80);
    sampleTable.addEntry(new Record("Maggie"));
    assertEquals(sampleTable.getCount(), 22);
  }

  public void testPrintHashTable() {
    sampleTable.addEntry(new Record("Bailey"));
    sampleTable.addEntry(new Record("Frankie"));
    sampleTable.printHashTable();
    String output = systemOut().getHistory();
    assertFuzzyEquals(output, "|Bailey| 3\n|Frankie| 9\nTotal records: 2");
  }

  public void testDeleteRecord() {
    sampleTable.addEntry(new Record("Bailey"));
    sampleTable.addEntry(new Record("Frankie"));
    boolean deleted = sampleTable.deleteRecord("Bailey");
    assertEquals(deleted, true);
    // boolean notDeleted = sampleTable.deleteRecord("Maggie");
    // assertEquals(notDeleted, false);
    sampleTable.printHashTable();

    String output = systemOut().getHistory();
    assertFuzzyEquals(output, "|Frankie| 9\nTotal records: 1");
  }
}
