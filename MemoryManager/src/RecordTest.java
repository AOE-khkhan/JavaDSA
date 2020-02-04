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
    aRecord.addRecordKeyVal(new RecordKeyVal("Genre", "Anime"));
    aRecord.addRecordKeyVal(new RecordKeyVal("Country", "Japan"));
    aRecord.printRecord();

    String output = systemOut().getHistory();
    assertFuzzyEquals(output, "Genre Anime\nCountry Japan");
  }

  public void testDeleteRecordKeyVal(){
    aRecord.deleteRecordKeyVal("Genre");
    assertEquals(aRecord.findRecordKeyVal("Genre"), null);
    aRecord.addRecordKeyVal(new RecordKeyVal("Year", "2003"));
    aRecord.printRecord();
    String output = systemOut().getHistory();
    assertFuzzyEquals(output, "Year 2003");
  }

  public void testFindRecordKeyVal(){
    aRecord.addRecordKeyVal(new RecordKeyVal("Genre", "Anime"));
    RecordKeyVal found = aRecord.findRecordKeyVal("Genre");
    assertEquals(found.getVal(), "Anime");
    aRecord.deleteRecordKeyVal("Genre");
    found = aRecord.findRecordKeyVal("Genre");
    assertEquals(found, null);
  }

//  public void test

  // public void test
}

