import student.TestCase;

// -------------------------------------------------------------------------
/**
 * Test the Record class.
 */
public class RecordTest extends TestCase {
  private Record aRecord;

  /**
   * Sets up the tests that follow.
   */
  public void setUp() {
    aRecord = new Record("Death Note");
  }

  public void testGetName() {
    assertEquals(aRecord.getName(), "Death Note");
  }

  public void testAddRecordKeyVal() {
    boolean added = aRecord.addRecordKeyVal(new RecordKeyVal("Genre", "Anime"));
    assertEquals(added, true);
    added = aRecord.addRecordKeyVal(new RecordKeyVal("Genre", "Anime"));
    assertEquals(added, false);
    added = aRecord.addRecordKeyVal(new RecordKeyVal("Country", "Japan"));
    assertEquals(added, true);
  }

  public void testDeleteRecordKeyVal() {
    aRecord.deleteRecordKeyVal("Genre");
    assertEquals(aRecord.findRecordKeyVal("Genre"), null);
    aRecord.addRecordKeyVal(new RecordKeyVal("Year", "2003"));
  }

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

  public void testToString() {
    assertFuzzyEquals(aRecord.toString(), "Death Note");
    aRecord.addRecordKeyVal(new RecordKeyVal("Genre", "Anime"));
    aRecord.addRecordKeyVal(new RecordKeyVal("Country", "Japan"));
    System.out.println(aRecord.toString());
    assertFuzzyEquals(aRecord.toString(), "Death Note<SEP>Genre<SEP>Anime<SEP>Country<SEP>Japan");
  }

}
