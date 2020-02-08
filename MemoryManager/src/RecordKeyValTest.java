import student.TestCase;

// -------------------------------------------------------------------------
/**
 * Test the RecordKeyVal class.
 * 
 * @author Bimal Gaudel
 * @version 0.1
 */
public class RecordKeyValTest extends TestCase {
  /**
   * A RecordKeyVal object used for testing.
   */
  private RecordKeyVal aKVpair;

  /**
   * Sets up the tests that follow.
   */
  public void setUp() {
    aKVpair = new RecordKeyVal("Genre", "Anime");
  }

  /**
   * Test the constructor of the RecordKeyVal class.
   */
  public void testConstructor() {
    assertNotNull(aKVpair);
    assertNull(aKVpair.getNextKeyVal());
  }

  /** Test the getKey getter method. */
  public void testGetKey() {
    assertEquals(aKVpair.getKey(), "Genre");
  }

  /** Test the getValue getter method. */
  public void testGetValue() {
    assertEquals(aKVpair.getVal(), "Anime");
  }

  /** Test the setNextKeyVal setter method. */
  public void testSetNextKeyVal() {
    aKVpair.setNextKeyVal(new RecordKeyVal("Popularity", "Super Hit"));
    RecordKeyVal nextkv = aKVpair.getNextKeyVal();
    assertEquals(nextkv.getKey(), "Popularity");
    assertEquals(nextkv.getVal(), "Super Hit");
  }

  /** Test the getNextKeyVal getter method. */
  public void testGetNextKeyVal() {
    aKVpair.setNextKeyVal(new RecordKeyVal("Popularity", "Super Hit"));
    assertEquals(aKVpair.getNextKeyVal().getKey(), "Popularity");
    assertEquals(aKVpair.getNextKeyVal().getVal(), "Super Hit");
  }

  /**
   * Test the setNextKeyVal setter method when the argument itself is a linked
   * list instead of a single RecordKeyVal object.
   */
  public void testSetNextKeyValLinked() {
    RecordKeyVal newKVpair = new RecordKeyVal("Year", "1995");
    newKVpair.setNextKeyVal(new RecordKeyVal("Country", "Japan"));
    aKVpair.setNextKeyVal(newKVpair);

    assertEquals(aKVpair.getNextKeyVal().getKey(), "Year");
    assertEquals(aKVpair.getNextKeyVal().getVal(), "1995");

    assertEquals(aKVpair.getNextKeyVal().getNextKeyVal().getKey(), "Country");
    assertEquals(aKVpair.getNextKeyVal().getNextKeyVal().getVal(), "Japan");
  }

  /**
   * Test the method to delete a RecordKeyVal object if present in the linked
   * member.
   */
  public void testDeleteKeyVal() {
    RecordKeyVal newKVpair = new RecordKeyVal("Year", "1995");
    newKVpair.setNextKeyVal(new RecordKeyVal("Country", "Japan"));
    aKVpair.setNextKeyVal(newKVpair);

    aKVpair.deleteKeyVal("Year");
    assertEquals(aKVpair.getNextKeyVal().getKey(), "Country");
    assertEquals(aKVpair.getNextKeyVal().getVal(), "Japan");
  }

  /** Test the method that appends a RecordKeyVal object at the end the list. */
  public void testAppendKeyVal() {
    aKVpair.appendKeyVal(new RecordKeyVal("Year", "1995"));
    aKVpair.appendKeyVal(new RecordKeyVal("Country", "Japan"));

    assertEquals(aKVpair.getNextKeyVal().getKey(), "Year");
    assertEquals(aKVpair.getNextKeyVal().getVal(), "1995");

    assertEquals(aKVpair.getNextKeyVal().getNextKeyVal().getKey(), "Country");
    assertEquals(aKVpair.getNextKeyVal().getNextKeyVal().getVal(), "Japan");
  }

  /** Test the method to find a RecordKeyVal in the linked list member. */
  public void testFindKeyVal() {
    RecordKeyVal found = aKVpair.findKeyVal("Genre");
    assertEquals(found.getVal(), "Anime");
    found = aKVpair.findKeyVal("Country");
    assertEquals(found, null);
  }

  /** Test the overridden method toString. */
  public void testToString() {
    System.out.println(aKVpair);
    String output = systemOut().getHistory();

    assertFuzzyEquals(output, "Genre<SEP>Anime");

  }
}
