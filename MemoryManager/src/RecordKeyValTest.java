import student.TestCase;

// -------------------------------------------------------------------------
/**
 * Test the RecordKeyVal class.
 */
public class RecordKeyValTest extends TestCase {
  private RecordKeyVal aKVpair;

  /**
   * Sets up the tests that follow.
   */
  public void setUp() {
    aKVpair = new RecordKeyVal("Genre", "Anime");
  }

  public void testDefaultConstructor() {
    assertNotNull(aKVpair);
    assertNull(aKVpair.getNextKeyVal());
  }

  public void testGetKey() {
    assertEquals(aKVpair.getKey(), "Genre");
  }

  public void testGetValue() {
    assertEquals(aKVpair.getVal(), "Anime");
  }

  public void testSetNextKeyVal() {
    aKVpair.setNextKeyVal(new RecordKeyVal("Popularity", "Super Hit"));
    RecordKeyVal nextkv = aKVpair.getNextKeyVal();
    assertEquals(nextkv.getKey(), "Popularity");
    assertEquals(nextkv.getVal(), "Super Hit");
  }

  public void testSetNextKeyValLinked() {
    RecordKeyVal newKVpair = new RecordKeyVal("Year", "1995");
    newKVpair.setNextKeyVal(new RecordKeyVal("Country", "Japan"));
    aKVpair.setNextKeyVal(newKVpair);

    printLinkedRecordKeyVal(aKVpair);

    String output = systemOut().getHistory();
    assertFuzzyEquals("Genre Anime\nYear 1995\nCountry Japan\n", output);
    System.out.println("--------------------------------------");
  }

  public void testDeleteKeyVal() {
    RecordKeyVal newKVpair = new RecordKeyVal("Year", "1995");
    newKVpair.setNextKeyVal(new RecordKeyVal("Country", "Japan"));
    aKVpair.setNextKeyVal(newKVpair);

    aKVpair.deleteKeyVal("Year");

    printLinkedRecordKeyVal(aKVpair);

    String output = systemOut().getHistory();
    assertFuzzyEquals("Genre Anime\nCountry Japan\n", output);

    System.out.println("--------------------------------------");
  }

  public void testAppendKeyVal() {
    aKVpair.appendKeyVal(new RecordKeyVal("Year", "1995"));

    printLinkedRecordKeyVal(aKVpair);
    String output = systemOut().getHistory();

    assert (output.endsWith("1995\n"));
    System.out.println("--------------------------------------");
  }

  public void testFindKeyVal() {
    RecordKeyVal found = aKVpair.findKeyVal("Genre");
    assertEquals(found.getVal(), "Anime");
    found = aKVpair.findKeyVal("Country");
    assertEquals(found, null);
  }

  /*
   * TOOD Redundant function. Clean up.
   */
  private void printLinkedRecordKeyVal(RecordKeyVal kv) {
    System.out.println(kv.getKey() + " " + kv.getVal());
    if (!(kv.getNextKeyVal() == null))
      printLinkedRecordKeyVal(kv.getNextKeyVal());
  }

}
