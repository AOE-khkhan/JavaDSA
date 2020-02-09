import student.TestCase;

// -------------------------------------------------------------------------
/**
 * Test the Record class.
 * 
 * @author Bimal Gaudel
 * @version 0.1
 */
public class RecordTest extends TestCase {
  private Record aRecord;

  /**
   * Sets up the tests that follow.
   */
  public void setUp() {
    aRecord = new Record("Death Note");
  }

  /**
   * Test the name getter.
   */
  public void testGetName() {
    assertEquals(aRecord.getName(), "Death Note");
  }

  /**
   * Test the method to add a RecordKeyVal object.
   */
  public void testAddRecordKeyVal() {
    boolean added = aRecord.addRecordKeyVal(new RecordKeyVal("Genre", "Anime"));
    assertEquals(added, true);
    added = aRecord.addRecordKeyVal(new RecordKeyVal("Genre", "Anime"));
    assertEquals(added, false);
    added = aRecord.addRecordKeyVal(new RecordKeyVal("Country", "Japan"));
    assertEquals(added, true);
  }

  /** Test the method to delete a RecordKeyVal. */
  public void testDeleteRecordKeyVal() {
    aRecord.deleteRecordKeyVal("Genre");
    assertEquals(aRecord.findRecordKeyVal("Genre"), null);
    aRecord.addRecordKeyVal(new RecordKeyVal("Year", "2003"));
  }

  /** Test the method to find a RecordKeyVal. */
  public void testFindRecordKeyVal() {
    aRecord.addRecordKeyVal(new RecordKeyVal("Genre", "Anime"));

    RecordKeyVal found = aRecord.findRecordKeyVal("Genre");
    assertEquals(found.getVal(), "Anime");

    aRecord.deleteRecordKeyVal("Genre");

    found = aRecord.findRecordKeyVal("Genre");
    assertNull(found);

    aRecord.addRecordKeyVal(new RecordKeyVal("Country", "Japan"));
    found = aRecord.findRecordKeyVal("Country");
    assertNotNull(found);
  }

  /** Test the overridden method toString. */
  public void testToString() {
    assertFuzzyEquals(aRecord.toString(), "Death Note");
    aRecord.addRecordKeyVal(new RecordKeyVal("Genre", "Anime"));
    aRecord.addRecordKeyVal(new RecordKeyVal("Country", "Japan"));
    System.out.println(aRecord.toString());
    assertFuzzyEquals(aRecord.toString(),
        "Death Note<SEP>Genre<SEP>Anime<SEP>Country<SEP>Japan");
  }

}
