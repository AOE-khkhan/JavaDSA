// -------------------------------------------------------------------------
/**
 * RecordHashTable represents the hashtable of Record objects.
 *
 * @author Bimal Gaudel
 * @version 2020-02-04
 */
public class RecordHashTable {
  // the minimum size of a table
  public static final int MIN_SIZE = 10;

  // the number of slots present at the current state of the table
  private int size;

  // the number of records the table holds.
  private int count;

  // the table data
  private TableEntry[] tableData;

  public RecordHashTable() {
    this(RecordHashTable.MIN_SIZE);
  }

  public RecordHashTable(int tableSize) {
    this.size = tableSize < RecordHashTable.MIN_SIZE ? RecordHashTable.MIN_SIZE : tableSize;
    // initialize the table data
    this.tableData = new TableEntry[this.getSize()];
    for (int i = 0; i < this.getSize(); ++i)
      this.tableData[i] = new TableEntry();
  }

  public int getSize() {
    return this.size;
  }

  public int getCount() {
    return this.count;
  }

  private void incrementCount() {
    ++this.count;
  }

  private void decrementCount() {
    --this.count;
  }

  public boolean addEntry(Record record) throws IllegalStateException {
    // check if the size limit permits any addition
    // if not, extend the table size by doubling it
    // find a slot for the record by hashing
    // if record exists in the slot and not marked deleted: return
    // if slot active: do quadratic probing and find slot again
    // add entry, increment counts return

    if (this.getSize() / 2 == this.getCount()) {
      this.doubleTableSize();
    }

    int homeSlot = RecordHashTable.getHomeSlot(record.getName(), this.getSize());
    int validSlot = homeSlot;
    int probeLevel = 0;

    int sanityCounter = 0;
    boolean foundDuplicate = false;
    while ((sanityCounter < this.getSize()) && (!this.tableData[validSlot].isFree()) && (!foundDuplicate)) {
      if (this.tableData[validSlot].getRecord().getName() == record.getName()) {
        foundDuplicate = true;
        break;
      }
      validSlot = RecordHashTable.getProbedSlot(homeSlot, ++probeLevel, this.getSize());
    }

    if (sanityCounter >= this.getSize())
      throw new IllegalStateException("Can't hash!");

    if (foundDuplicate)
      return false;

    this.tableData[validSlot] = new TableEntry(record);
    this.incrementCount();
    return true;
  }

  private void doubleTableSize() {
    int newSize = 2 * this.getSize();

    // initialize new table data
    TableEntry[] newTableData = new TableEntry[newSize];
    for (int i = 0; i < newSize; ++i) {
      newTableData[i] = new TableEntry();
    }

    int countAdds = 0;
    for (int i = 0; i < this.getSize(); ++i) {
      if (this.tableData[i].isActive()) {
        int homeSlot = RecordHashTable.getHomeSlot(this.tableData[i].getRecord().getName(), newSize);
        int validSlot = homeSlot;
        int probeLevel = 0;
        while (!newTableData[i].isFree()) {
          validSlot = RecordHashTable.getProbedSlot(homeSlot, ++probeLevel, newSize);
        }
        newTableData[validSlot] = this.tableData[i];
        ++countAdds;
      }
    }
    this.tableData = newTableData;
    this.size = newSize;
    this.count = countAdds;
  }

  /**
   * Compute the hash function. Uses the "sfold" method from the OpenDSA module on
   * hash functions
   *
   * @param string The string to be hashed
   * @return The home slot for string
   */
  private static int getHomeSlot(String string, int tableSize) {
    int intLength = string.length() / 4;
    long sum = 0;
    for (int j = 0; j < intLength; j++) {
      char[] c = string.substring(j * 4, (j * 4) + 4).toCharArray();
      long mult = 1;
      for (int k = 0; k < c.length; k++) {
        sum += c[k] * mult;
        mult *= 256;
      }
    }

    char[] c = string.substring(intLength * 4).toCharArray();
    long mult = 1;
    for (int k = 0; k < c.length; k++) {
      sum += c[k] * mult;
      mult *= 256;
    }

    return (int) (Math.abs(sum) % tableSize);
  }

  /**
   * Compute a probed home slot using quadratic probing.
   *
   * @param homeSlot   The home slot position without probing.
   * @param probeLevel Number of times position finding is attempted.
   * @param tableSize  The size of the table on which the position is to be found.
   */
  private static int getProbedSlot(int homeSlot, int probeLevel, int tableSize) throws IllegalArgumentException {
    if (homeSlot > tableSize)
      throw new IllegalArgumentException("The homeSlot index should be smaller than the tableSize!");
    return (homeSlot + probeLevel * probeLevel) % tableSize;
  }

}
