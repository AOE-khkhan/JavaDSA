import student.TestCase;

/**
 * Test the MemMan class.
 * 
 * @author Bimal Gaudel
 * @version 0.1
 */
public class MemManTest extends TestCase {
  /**
   * Sets up the tests that follow. In general, used for initialization
   */
  public void setUp() {
    // Nothing Here
  }

  /**
   * Get code coverage of the class declaration.
   */
  public void testRInit() {
    MemMan manager = new MemMan();
    assertNotNull(manager);
  }

  /**
   * Test the main method
   */
  public void testMain() {
    String[] args = { "5", "10", "src/commands.txt" };
    MemMan.main(args);

    String expectedOutput = "Total records: 0\n" + "32: 0\n"
        + "|Death Note| not deleted because it does not exist in the "
        + "Name database.\n"
        + "|Another Note| not updated because it does not exist in the "
        + "Name database.\n"
        + "|Death Note| has been added to the Name database.\n"
        + "|Death Note| 4\n" + "Total records: 1\n"
        + "|Death Note| duplicates a record already in the Name database.\n"
        + "32: 0\n"
        + "|Death Note| not updated because the field |Genre| does not exist\n"
        + "Updated Record: |Death Note<SEP>Genre<SEP>Anime|\n" + "16: 16\n"
        + "|Can You Handle?| has been added to the Name database.\n"
        + "|Another Test| has been added to the Name database.\n"
        + "|Death Note| 4\n" + "|Can You Handle?| 6\n" + "|Another Test| 9\n"
        + "Total records: 3\n"
        + "|Death Note| has been deleted from the Name database.\n"
        + "|Death Note| has been added to the Name database.\n"
        + "|Another Test| has been deleted from the Name database.\n"
        + "16: 16\n" + "Updated Record: |Death Note<SEP>Genre<SEP>Animation|\n"
        + "Memory pool expanded to 128 bytes.\n"
        + "Updated Record: "
        + "|Death Note<SEP>Genre<SEP>Animation<SEP>Format<SEP>Series|\n"
        + "Updated Record: "
        + "|Death Note<SEP>Format<SEP>Series<SEP>Genre<SEP>Anime|\n"
        + "Updated Record: |Death Note<SEP>Format<SEP>Series|\n"
        + "|Fullmetal Alchemist| has been added to the Name database.\n"
        + "|Spirited Away| has been added to the Name database.\n"
        + "|death note| has been added to the Name database.\n"
        + "Name hash table size doubled to 20 slots.\n"
        + "|Castle in the Sky| has been added to the Name database.\n"
        + "|Fullmetal Alchemist| 3\n" + "|Death Note| 4\n"
        + "|Spirited Away| 5\n" + "|Castle in the Sky| 7\n" + "|death note| 8\n"
        + "|Can You Handle?| 16\n" + "Total records: 6\n";

    assertFuzzyEquals(systemOut().getHistory(), expectedOutput);
  }
}
