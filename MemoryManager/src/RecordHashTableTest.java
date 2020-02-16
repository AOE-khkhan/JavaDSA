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
    sampleTable = new RecordHashTable(10, 5);
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
    sampleTable.addRecord("Poochie");
    sampleTable.addRecord("Pie");
    sampleTable.deleteRecord("Pie");
    sampleTable.addRecord("Pie");
    assertEquals(sampleTable.getCount(), 2);
  }

  /**
   * Test hash collisions.
   */
  public void testHashCollision() {
    // for the strings "aaaazzzz", "bbbbyyyy", "ccccxxxx",
    // "ddddwwww", "eeeevvvv" and so on the home slot
    // is computed to be 1 for a hash table of size 10.
    // So, if our hashtable algorithm works as expected
    // aaaazzzz -> 1
    // bbbbyyyy -> 2 // because it collided once
    // ccccxxxx -> 5 // because it collided twice
    // delete bbbbyyyy
    // ddddwwww -> 2 // tombstone is reclaimed
    //
    sampleTable.addRecord("aaaazzzz");
    sampleTable.addRecord("bbbbyyyy");
    sampleTable.addRecord("ccccxxxx");
    sampleTable.deleteRecord("bbbbyyyy");
    sampleTable.addRecord("ddddwwww");
    sampleTable.print();
    String expectedOutput = "|aaaazzzz| 1\n" + "|ddddwwww| 2\n"
    // + "|bbbbyyyy| 2\n"
        + "|ccccxxxx| 5\n" + "Total records: 3";
    assertFuzzyEquals(expectedOutput, systemOut().getHistory());
  }

  /**
   * Test the Record getter method.
   */
  public void testGetRecord() {
    sampleTable.addRecord("Charlie");
    sampleTable.addRecord("Max");
    sampleTable.addRecord("Buddy");

    Record found = sampleTable.getRecord("Charlie");
    assertNotNull(found);
    found = sampleTable.getRecord("Toby");
    assertNull(found);
  }

  /**
   * Test if the hashtable doubles in size as expected.
   */
  public void testDoubleTableSize() {
    RecordHashTable aTable = new RecordHashTable(3, 5);
    aTable.addRecord("Charlie");
    assertEquals(aTable.getSize(), 3);
    assertEquals(aTable.getCount(), 1);
    aTable.deleteRecord("Charlie");
    assertEquals(aTable.getSize(), 3);
    assertEquals(aTable.getCount(), 0);
    aTable.addRecord("Charlie");
    assertEquals(aTable.getSize(), 3);
    assertEquals(aTable.getCount(), 1);
    aTable.addRecord("Poochie");
    assertEquals(aTable.getSize(), 6);
    // assertEquals(aTable.getCount(), 2);
  }

  /**
   * Test the hashtable print method.
   */
  public void testPrint() {
    sampleTable.addRecord("Death Note");
    sampleTable.addRecord("Can you handle?");
    sampleTable.print();
    String output = systemOut().getHistory();
    assertFuzzyEquals(output,
        "|Can you handle?| 0\n" + "|Death Note| 4\nTotal records: 2");
  }

  /**
   * Test the method to delete a Record from the table.
   */
  public void testDeleteRecord() {
    sampleTable.addRecord("Bailey");
    sampleTable.addRecord("Frankie");
    boolean deleted = sampleTable.deleteRecord("Bailey");
    assertEquals(deleted, true);
    deleted = sampleTable.deleteRecord("Maggie");
    assertEquals(deleted, false);
  }
}
