// -------------------------------------------------------------------------
/**
 * RecordHashTable represents the hashtable of Record objects.
 *
 * @author Bimal Gaudel
 * @version 2020-02-04
 */
public class RecordHashTable {
  /**
   * the minimum size of a table
   */
  public static final int MIN_SIZE = 1;

  /**
   * the number of slots present at the current state of the table
   */
  private int size;

  /**
   * the number of active records the table holds.
   */
  private int count;

  /**
   * the number of deleted records the table holds.
   */
  private int countDeleted;

  /**
   * the table data array
   */
  private TableEntry[] tableData;

  /**
   * Empty parameter constructor.
   */
  public RecordHashTable() {
    this(RecordHashTable.MIN_SIZE);
  }

  /**
   * Construct RecordHashTable by giving table size. Minimum size is always
   * maintained.
   * 
   * @param tableSize The size of the hash table.
   */
  public RecordHashTable(int tableSize) {
    this.size = tableSize < RecordHashTable.MIN_SIZE ? RecordHashTable.MIN_SIZE : tableSize;
    // initialize the table data
    this.tableData = new TableEntry[this.getSize()];
    for (int i = 0; i < this.getSize(); ++i) {
      this.tableData[i] = new TableEntry();
    }
  }

  /**
   * Get the size of the hashtable.
   * 
   * @return the int size of the hashtable
   */
  public int getSize() {
    return this.size;
  }

  /**
   * Get the number of entries the hash table currently holds. Both the active and
   * deleted entries are counted for the reasons how the closed hashtable is
   * implemented.
   * 
   * @return the int count of the non-free entries in the hashtable
   */
  public int getCount() {
    return this.count;
  }

  /**
   * Add a Record entry to the hash table. The Record's name already exists in the
   * database no changes will be made.
   * 
   * @param record Record object
   * @throws IllegalStateException
   * @return true if addition is successful, false otherwise.
   */
  public boolean addEntry(Record record) throws IllegalStateException {
    if (this.getSize() / 2 <= this.getCount() + this.countDeleted) {
      this.doubleTableSize();
    }

    int validSlot = this.getRecordSlot(record.getName());

    if (validSlot == -1) {
      throw new IllegalStateException("Hashtable is in illegal state. Can't hash!");
    }

    if (this.tableData[validSlot].isFree()) {
      this.tableData[validSlot] = new TableEntry(record);
      ++this.count;
      return true;
    }
    return false;
  }

  /**
   * Delete a record from the table.
   * 
   * @param key A string that is the name of the record to be deleted.
   * @return true if any deletion occurs, false otherwise.
   */
  public boolean deleteRecord(String key) {
    int recordPos = this.getRecordSlot(key);

    if ((recordPos != -1) && (this.tableData[recordPos].isActive())) {
      this.tableData[recordPos].markDeleted();
      ++this.countDeleted;
      --this.count;
      return true;
    }
    return false;
  }

  /**
   * Print the hashtable. Prints each Record's name and the position on the table.
   */
  public void printHashTable() {
    for (int i = 0; i < this.getSize(); ++i) {
      if (this.tableData[i].isActive()) {
        System.out.println("|" + this.tableData[i].getRecordName() + "| " + i);
      }
    }
    System.out.println("Total records: " + this.getCount());
  }

  /**
   * Double the size of the hashtable. Rehashes existing non-deleted entries.
   * 
   */
  private void doubleTableSize() {
    RecordHashTable newHashTable = new RecordHashTable(this.getSize() * 2);
    for (int i = 0; i < this.getSize(); ++i) {
      if (this.tableData[i].isActive()) {
        newHashTable.addEntry(this.tableData[i].getRecord());
      }
    }
    this.tableData = newHashTable.tableData;
    this.size = newHashTable.size;
    this.count = newHashTable.count;
  }

  /**
   * Get the slot index of a Record by its name. An active slot with key as the
   * Record name or a free slot computed by hashing (and probing if needed) are
   * valid slots. This function returns the first index of such kinds. If no valid
   * slot could be found it returns -1 and that implies an unexpected state of the
   * table.
   * 
   * @param key The String name of the Record.
   * @return The index of the valid slot for a Record.
   */
  private int getRecordSlot(String key) {
    int homeSlot = RecordHashTable.getHomeSlot(key, this.getSize());
    int validSlot = homeSlot;
    int probeLevel = 0;
    int sanityCounter = 0;

    do {
      TableEntry curEntry = this.tableData[validSlot];

      if (curEntry.isFree() || (curEntry.isActive() && curEntry.getRecordName().equals(key))) {
        return validSlot;
      }
      validSlot = RecordHashTable.getProbedSlot(homeSlot, ++probeLevel, this.getSize());

    } while (sanityCounter < this.getSize());

    return -1;

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
