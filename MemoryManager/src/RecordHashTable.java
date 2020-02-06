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
   * the number of records the table holds.
   */
  private int count;

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

  /** Get the size of the hash table. */
  public int getSize() {
    return this.size;
  }

  /** Get the number of entries the hash table currently holds. */
  public int getCount() {
    return this.count;
  }

  /**
   * Add a Record entry to the hash table.
   * 
   * @param record Record object
   * @throws IllegalStateException
   */
  public boolean addEntry(Record record) throws IllegalStateException {
    if (this.getSize() / 2 <= this.getCount()) {
      this.doubleTableSize();
    }
    int homeSlot = RecordHashTable.getHomeSlot(record.getName(), this.getSize());
    int validSlot = homeSlot;
    int probeLevel = 0;

    int sanityCounter = 0;
    boolean foundDuplicate = false;
    while ((sanityCounter < this.getSize()) && (!this.tableData[validSlot].isFree()) && (!foundDuplicate)) {
      if (this.tableData[validSlot].getRecordName().equals(record.getName())) {
        foundDuplicate = true;
        break;
      }
      validSlot = RecordHashTable.getProbedSlot(homeSlot, ++probeLevel, this.getSize());
    }
    if (sanityCounter == this.getSize()) {
      throw new IllegalStateException("Can't hash!");
    }
    if (foundDuplicate) {
      return false;
    }
    this.tableData[validSlot] = new TableEntry(record);
    this.incrementCount();
    return true;
  }

  /**
   * Delete a record from the table.
   * 
   * @param key A string that is the name of the record to be deleted.
   * @return true if any deletion occurs, false otherwise.
   */
  public boolean deleteRecord(String key) {
    // int recordPos = this.getRecordSlot(key);

    // if ((recordPos != -1) && (this.tableData[recordPos].isActive())){
    //   this.tableData[recordPos].markDeleted();
    //   this.decrementCount();
    //   return true;
    // }
    // return false;

    // if (recordPos > -1) {
    //   this.tableData[recordPos].markDeleted();
    //   this.decrementCount();
    //   return true;
    // }
    // return false;
    for (int i = 0; i < this.getSize(); ++i) {
      if (this.tableData[i].isActive() && this.tableData[i].getRecordName().equals(key)){
        this.tableData[i].markDeleted();
        this.decrementCount();
        return true;
      }
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
   * @throws IllegalStateException
   */
  private void doubleTableSize() throws IllegalStateException {

    int newSize = 2 * this.getSize();
    TableEntry[] newTableData = new TableEntry[newSize];

    for (int i = 0; i < newSize; ++i) {
      newTableData[i] = new TableEntry();
    }
    int countAdds = 0;
    for (int i = 0; i < this.getSize(); ++i) {
      if (this.tableData[i].isActive()) {
        int homeSlot = RecordHashTable.getHomeSlot(this.tableData[i].getRecordName(), newSize);
        int validSlot = homeSlot;
        int probeLevel = 0;
        int sanityCounter = 0;
        while ((!newTableData[validSlot].isFree()) && (sanityCounter < newSize)) {
          validSlot = RecordHashTable.getProbedSlot(homeSlot, ++probeLevel, newSize);
          ++sanityCounter;
        }
        if (sanityCounter == newSize) {
          throw new IllegalStateException("Can't hash!");
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
   * Increment the entries count.
   */
  private void incrementCount() {
    ++this.count;
  }

  /**
   * Decrement the entries count.
   */
  private void decrementCount() {
    --this.count;
  }

  /**
   * Get the slot index of a Record by its name.
   * 
   * @param key The String name of the Record.
   * @return The index of the valid slot for a Record.
   */
  private int getRecordSlot(String key) {
    int homeSlot = RecordHashTable.getHomeSlot(key, this.getSize());
    int validSlot = homeSlot;
    int probeLevel = 0;
    int sanityCounter = 0;

    // while (
    //     (sanityCounter < this.getSize()) &&
    //     ((!this.tableData[validSlot].isFree()) ||
    //       (!((this.tableData[validSlot].isActive()) &&
    //       this.tableData[validSlot].getRecordName().equals(key))))) {
    //   validSlot = RecordHashTable.getProbedSlot(homeSlot, ++probeLevel, this.getSize());
    //   ++sanityCounter;
    // }

    // if (sanityCounter != this.getSize()){
    //   return validSlot;
    // } else {
    //   return -1;
    // }

    while ((sanityCounter < this.getSize()) &&
    ((!this.tableData[validSlot].isFree()) ||
    (!(this.tableData[validSlot].isActive() &&
    this.tableData[validSlot].getRecordName().equals(key))))) {

    validSlot = RecordHashTable.getProbedSlot(homeSlot, ++probeLevel,
    this.getSize());
    ++sanityCounter;
    }

    if ((!this.tableData[validSlot].isFree()) && (sanityCounter <
    this.getSize())) {
    return validSlot;
    }
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
