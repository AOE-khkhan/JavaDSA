import student.TestCase;

/**
 * Tests the Communicator class
 * 
 * @author Bimal Gaudel
 * @version 2020-02-08
 */
public class CommunicatorTest extends TestCase {
  /**
   * A communicator object used in the tests.
   */
  private Communicator aCommunicator;

  /**
   * Sets up the tests that follow.
   */
  public void setUp() {
    RecordHashTable aHashTable = new RecordHashTable(3, 4);
    this.aCommunicator = new Communicator(aHashTable);
    assertNotNull(this.aCommunicator);
  }

  /**
   * Test the printHashTable method.
   */
  public void testPrintHashTable() {
    this.aCommunicator.printHashTable();
    String output = systemOut().getHistory();
    assertFuzzyEquals(output, "Total records: 0");
  }

  /**
   * Test the addRecordToHashTable method.
   */
  public void testAddRecordToHashTable() {
    this.aCommunicator.addRecordToHashTable("Poochie");
    this.aCommunicator.addRecordToHashTable("Poochie");
    this.aCommunicator.addRecordToHashTable("Polly");
    String message = systemOut().getHistory();
    assertFuzzyEquals(message,
        "|Poochie| has been added to the Name database.\n"
            + "|Poochie| duplicates a record already in the Name database.\n"
            + "|Polly| has been added to the Name database.\n"
            + "Name hash table size doubled to 6 slots.");
  }

  /**
   * Test the deleteRecordFromHashTable method.
   */
  public void testDeleteRecordFromHashTable() {
    this.aCommunicator.deleteRecordFromHashTable("Poochie");
    this.aCommunicator.addRecordToHashTable("Poochie");
    this.aCommunicator.deleteRecordFromHashTable("Poochie");

    String expectedMessage = "|Poochie| not deleted because it does not "
        + "exist in the Name database.\n"
        + "|Poochie| has been added to the Name database.\n"
        + "|Poochie| has been deleted from the Name database.";
    assertFuzzyEquals(expectedMessage, systemOut().getHistory());
  }

  /**
   * Test the updateAddRecordKeyVal method.
   */
  public void testUpdateAddRecordKeyVal() {
    this.aCommunicator.updateAddRecordKeyVal("Death Note", "Genre", "Anime");
    this.aCommunicator.addRecordToHashTable("Death Note");
    this.aCommunicator.updateAddRecordKeyVal("Death Note", "Genre", "Anime");

    String message = systemOut().getHistory();
    assertFuzzyEquals(message,
        "|Death Note| not updated because it does not exist "
            + "in the Name database.\n"
            + "|Death Note| has been added to the Name database.\n"
            + "Memory pool expanded to be 32 bytes.\n"
            + "Updated Record: |Death Note<SEP>Genre<SEP>Anime|");
  }

  /**
   * Test the updateDeleteRecordKeyVal method.
   */
  public void testUpdateDeleteRecordKeyVal() {
    this.aCommunicator.updateDeleteRecordKeyVal("Death Note", "Genre");
    this.aCommunicator.addRecordToHashTable("Death Note");
    this.aCommunicator.updateDeleteRecordKeyVal("Death Note", "Genre");
    this.aCommunicator.updateAddRecordKeyVal("Death Note", "Genre", "Anime");
    this.aCommunicator.updateDeleteRecordKeyVal("Death Note", "Genre");

    String expectedMessage = "|Death Note| not updated because it does not "
        + "exist in the Name database.\n"
        + "|Death Note| has been added to the Name database.\n"
        + "|Death Note| not updated because the field |Genre|"
        + " does not exist\n"
        + "Memory pool expanded to be 32 bytes.\n"
        + "Updated record: |Death Note<SEP>Genre<SEP>Anime|\n"
        + "Updated record: |Death Note|";
    assertFuzzyEquals(expectedMessage, systemOut().getHistory());
  }

}
