import student.TestCase;

// -------------------------------------------------------------------------
/**
 * Test the RecordHashTable class.
 * 
 * @author Bimal Gaudel
 * @version 0.1
 */
public class RecordHashTableTest extends TestCase {

  /**
   * A RecordHashTable object used for test methods below.
   */
  private RecordHashTable sampleTable;

  /**
   * Sets up the tests that follow.
   */
  public void setUp() {
    sampleTable = new RecordHashTable(10);
  }

  /**
   * Test the constructors of the RecordHashTable class.
   */
  public void testConstructors() {
    assertNotNull(sampleTable);
  }

  /**
   * Test the getSize getter method.
   */
  public void testGetSize() {
    assertEquals(sampleTable.getSize(), 10);
  }

  /**
   * Test the methods for adding Record objects to the RecordHashTable.
   */
  public void testAddRecord() {
    sampleTable.addRecord(new Record("Poochie"));
    sampleTable.addRecord(new Record("Pie"));
    sampleTable.deleteRecord("Pie");
    sampleTable.addRecord(new Record("Pie"));
    assertEquals(sampleTable.getCount(), 2);
  }

  /**
   * Test the Record getter method.
   */
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

  /**
   * Test if the hashtable doubles in size as expected.
   */
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

  /**
   * Test the hashtable print method.
   */
  public void testPrint() {
    sampleTable.addRecord(new Record("Death Note"));
    sampleTable.addRecord(new Record("Can you handle?"));
    sampleTable.print();
    String output = systemOut().getHistory();
    assertFuzzyEquals(output, "|Can you handle?| 0\n"
        + "|Death Note| 4\nTotal records: 2");
  }

  /**
   * Test the method to delete a Record from the table.
   */
  public void testDeleteRecord() {
    sampleTable.addRecord(new Record("Bailey"));
    sampleTable.addRecord(new Record("Frankie"));
    boolean deleted = sampleTable.deleteRecord("Bailey");
    assertEquals(deleted, true);
    deleted = sampleTable.deleteRecord("Maggie");
    assertEquals(deleted, false);
  }
}
