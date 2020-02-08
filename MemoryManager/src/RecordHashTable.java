// -------------------------------------------------------------------------
/**
 * RecordHashTable represents the hashtable of Record objects.
 *
 * @author Bimal Gaudel
 * @version 2020-02-04
 */
public class RecordHashTable {
  /**
   * The number of slots present at the current state of the table
   */
  private int size;

  /**
   * The number of active records the table holds.
   */
  private int countActive;

  /**
   * The number of deleted records the table holds.
   */
  private int countDeleted;

  /**
   * The TableEntry array field.
   */
  private TableEntry[] tableData;

  /**
   * Construct RecordHashTable by giving table size.
   * 
   * @param tableSize The size of the hash table.
   */
  public RecordHashTable(int tableSize) {
    this.size = tableSize;
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
   * Get the number of the active entries.
   * 
   * @return the int count of the non-free entries in the hashtable
   */
  public int getCount() {
    return this.countActive;
  }

  /**
   * Add a Record entry to the hash table. If the Record's name already exists in
   * the database no changes will be made.
   * 
   * @param record a Record object
   * @return true if addition is successful, false otherwise.
   */
  public boolean addRecord(Record record) {

    int validSlot = this.getSlotForInsertion(record.getName());

    if (validSlot == -1) {
      return false;
    }

    if (this.tableData[validSlot].isDeleted()) {
      this.tableData[validSlot] = new TableEntry(record);
      ++this.countActive;
      --this.countDeleted;
    } else {
      if ((this.countActive + this.countDeleted >= this.getSize() / 2)) {
        this.doubleTableSize();
        validSlot = this.getSlotForInsertion(record.getName());
      }
      this.tableData[validSlot] = new TableEntry(record);
      ++this.countActive;
    }
    return true;
  }

  /**
   * Get a reference to the Record if the Record exists.
   * 
   * @param key The name field of the Record to be returned.
   * @return Record object reference if found, null otherwise.
   */
  public Record getRecord(String key) {
    int recordPos = this.getSlotForLookUp(key);
    if (recordPos == -1) {
      return null;
    }
    Record found = this.tableData[recordPos].getRecord();
    return found;
  }

  /**
   * Delete a record from the table.
   * 
   * @param key A string that is the name of the record to be deleted.
   * @return true if any deletion occurs, false otherwise.
   */
  public boolean deleteRecord(String key) {
    int recordPos = this.getSlotForLookUp(key);

    if (recordPos == -1) {
      return false;
    }

    this.tableData[recordPos].markDeleted();
    ++this.countDeleted;
    --this.countActive;
    return true;
  }

  /**
   * Print the hashtable. Prints each Record's name and the position on the table.
   */
  public void print() {
    for (int i = 0; i < this.getSize(); ++i) {
      if (this.tableData[i].isActive()) {
        System.out.println("|" + this.tableData[i].getRecordName() + "| " + i);
      }
    }
    System.out.println("Total records: " + this.getCount());
  }

  /**
   * Double the size of the hashtable. Rehashes existing active entries.
   * 
   */
  private void doubleTableSize() {
    RecordHashTable newHashTable = new RecordHashTable(this.getSize() * 2);
    for (int i = 0; i < this.getSize(); ++i) {
      if (this.tableData[i].isActive()) {
        newHashTable.addRecord(this.tableData[i].getRecord());
      }
    }
    this.tableData = newHashTable.tableData;
    this.size = newHashTable.size;
    this.countActive = newHashTable.countActive;
  }

  /**
   * Return a slot index in the hashtable for insertion.
   *
   * Candidate positions will be computed using hashing and probing as needed. A
   * free slot or a deleted slot (given that no duplicate exists after the deleted
   * slot) are valid slot indices. Whichever comes first is reurned.
   * 
   * @param key Record name String used for hashing and lookup.
   * @return A valid slot index eligible for insertion, -1 if insertion cannot be
   *         performed.
   */
  private int getSlotForInsertion(String key) {
    int homeSlot = RecordHashTable.getHomeSlot(key, this.getSize());
    int runningSlot = homeSlot;
    int tombstoneSlot = -1;
    int probeLevel = 0;
    int sanityCounter = 0;

    TableEntry curEntry = null;

    do {
      curEntry = this.tableData[runningSlot];
      if (curEntry.isFree() && (tombstoneSlot == -1)) {
        return runningSlot;
      }
      if (curEntry.isActive() && curEntry.getRecordName().equals(key)) {
        return -1;
      }

      if ((tombstoneSlot == -1) && curEntry.isDeleted() && curEntry.getRecordName().equals(key)) {
        tombstoneSlot = runningSlot;
      }

      runningSlot = RecordHashTable.getProbedSlot(homeSlot, ++probeLevel, this.getSize());

      ++sanityCounter;

    } while (sanityCounter < this.getSize());
    return tombstoneSlot;
  }

  /**
   * Return a slot index from the hashtable that has a key being looked for.
   *
   * Candidate positions will be computed using hashing and probing as needed.
   * Only active slots will be considered and the key will be matched against such
   * a slot's key. If a free slot is encountered, then it will be implied that no
   * key is present in the table and -1 will be returned.
   * 
   * @param key Record name string used for hashing and lookup.
   * @return The slot index if the key exists, -1 otherwise.
   */
  private int getSlotForLookUp(String key) {
    int homeSlot = RecordHashTable.getHomeSlot(key, this.getSize());
    int runningSlot = homeSlot;
    int probeLevel = 0;
    int sanityCounter = 0;

    TableEntry curEntry = null;
    do {
      curEntry = this.tableData[runningSlot];
      if (curEntry.isFree()) {
        break;
      }

      if (curEntry.getRecordName().equals(key)) {
        return runningSlot;
      }
      runningSlot = RecordHashTable.getProbedSlot(homeSlot, ++probeLevel, this.getSize());
      ++sanityCounter;
    } while (sanityCounter < this.getSize());
    return -1;
  }

  /**
   * Compute the hash function. Uses the "sfold" method from the OpenDSA module on
   * hash functions.
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
  private static int getProbedSlot(int homeSlot, int probeLevel, int tableSize) {
    return (homeSlot + probeLevel * probeLevel) % tableSize;
  }

}
