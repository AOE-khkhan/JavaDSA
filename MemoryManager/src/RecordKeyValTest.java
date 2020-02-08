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

  public void testGetNextKeyVal(){
    aKVpair.setNextKeyVal(new RecordKeyVal("Popularity", "Super Hit"));
    assertEquals(aKVpair.getNextKeyVal().getKey(), "Popularity");
    assertEquals(aKVpair.getNextKeyVal().getVal(), "Super Hit");
  }

  public void testSetNextKeyValLinked() {
    RecordKeyVal newKVpair = new RecordKeyVal("Year", "1995");
    newKVpair.setNextKeyVal(new RecordKeyVal("Country", "Japan"));
    aKVpair.setNextKeyVal(newKVpair);
    
    assertEquals(aKVpair.getNextKeyVal().getKey(), "Year");
    assertEquals(aKVpair.getNextKeyVal().getVal(), "1995");
    
    assertEquals(aKVpair.getNextKeyVal().getNextKeyVal().getKey(), "Country");
    assertEquals(aKVpair.getNextKeyVal().getNextKeyVal().getVal(), "Japan");
  }

  public void testDeleteKeyVal() {
    RecordKeyVal newKVpair = new RecordKeyVal("Year", "1995");
    newKVpair.setNextKeyVal(new RecordKeyVal("Country", "Japan"));
    aKVpair.setNextKeyVal(newKVpair);

    aKVpair.deleteKeyVal("Year");
    assertEquals(aKVpair.getNextKeyVal().getKey(), "Country");
    assertEquals(aKVpair.getNextKeyVal().getVal(), "Japan");
  }

  public void testAppendKeyVal() {
    aKVpair.appendKeyVal(new RecordKeyVal("Year", "1995"));
    aKVpair.appendKeyVal(new RecordKeyVal("Country", "Japan"));
    
    assertEquals(aKVpair.getNextKeyVal().getKey(), "Year");
    assertEquals(aKVpair.getNextKeyVal().getVal(), "1995");
    
    assertEquals(aKVpair.getNextKeyVal().getNextKeyVal().getKey(), "Country");
    assertEquals(aKVpair.getNextKeyVal().getNextKeyVal().getVal(), "Japan");
  }

  public void testFindKeyVal() {
    RecordKeyVal found = aKVpair.findKeyVal("Genre");
    assertEquals(found.getVal(), "Anime");
    found = aKVpair.findKeyVal("Country");
    assertEquals(found, null);
  }
  
  public void testToString() {
	  System.out.println(aKVpair);
	  String output = systemOut().getHistory();
	  
	  assertFuzzyEquals(output, "Genre<SEP>Anime");
	  
  }
}
