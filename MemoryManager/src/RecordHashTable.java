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
   * The MemoryManager object for this hashtable.
   */
  private MemoryManager memoryManager;

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
   * @param tableSize           The size of the hash table.
   * @param poolSizeInTwosPower The memorypool size given in the powers of two.
   */
  public RecordHashTable(int tableSize, int poolSizeInTwosPower) {
    this.size = tableSize;
    // initialize the table data
    this.tableData = new TableEntry[this.getSize()];
    for (int i = 0; i < this.getSize(); ++i) {
      this.tableData[i] = new TableEntry();
    }
    this.memoryManager = new MemoryManager(poolSizeInTwosPower);
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
   * Get the poolsize of the memoryManager.
   * 
   * @return Number of bytes that the memoryManager handles right now.
   */
  public int getMemoryPoolSize() {
    return memoryManager.getPoolSize();
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
   * Add a Record entry to the hash table. If the Record's name already exists
   * in the database no changes will be made.
   * 
   * @param recordName String name handle of the Record.
   * @return true if addition is successful, false otherwise.
   */
  public boolean addRecord(String recordName) {

    int validSlot = this.getSlotForInsertion(recordName);

    if (validSlot == -1) {
      return false;
    }

    if (this.tableData[validSlot].isDeleted()) {
      MemoryHandle recordHandle = memoryManager
          .storeBytes(recordName.getBytes());
      this.tableData[validSlot] = new TableEntry(new Record(recordHandle));
      ++this.countActive;
      --this.countDeleted;
    }
    else {
      if ((this.countActive + this.countDeleted) >= this.getSize() / 2) {
        this.doubleTableSize();
        validSlot = this.getSlotForInsertion(recordName);
      }
      MemoryHandle recordHandle = memoryManager
          .storeBytes(recordName.getBytes());
      this.tableData[validSlot] = new TableEntry(new Record(recordHandle));
      ++this.countActive;
    }

    return true;
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

    Record freedRecord = tableData[recordPos].getRecord();
    this.memoryManager.freeBlock(freedRecord.getNameHandle());
    for (freedRecord.moveToFirstHandle(); (!(freedRecord
        .yieldHandle() == null)); freedRecord.curseToNextHandle()) {
      this.memoryManager.freeBlock(freedRecord.yieldHandle());
    }
    this.tableData[recordPos].markDeleted();
    ++this.countDeleted;
    --this.countActive;
    return true;
  }

  /**
   * Delete a key-val pair from a Record's database, if the pair exists.
   * 
   * @param record Non-null Record object that must exist on the hashtable.
   * @param key    String key name of the key-value pair.
   * @return true if any deletion occurs, false otherwise.
   */
  public boolean deleteRecordKeyVal(Record record, String key) {
    for (record.moveToFirstHandle(); (!(record.yieldHandle() == null)); record
        .curseToNextHandle()) {
      if (getStrFromMemory(record.yieldHandle()).startsWith(key + "<SEP>")) {
        this.memoryManager.freeBlock(record.yieldHandle());
        record.removeHandle(record.yieldHandle());
        return true;
      }
    }
    return false;
  }

  /**
   * Add a key-val pair to a Record's database, deleting any pre-existing entry
   * with the same name.
   * 
   * @param record Non-null Record object that must exist on the hashtable.
   * @param key    String key name being updated or added.
   * @param val    String value being replaced or added.
   */
  public void addRecordKeyVal(Record record, String key, String val) {
    deleteRecordKeyVal(record, key);
    MemoryHandle keyvalHandle = memoryManager
        .storeBytes((key + "<SEP>" + val).getBytes());
    record.addHandle(keyvalHandle);
  }

  /**
   * Print the hashtable. Prints each Record's name and the position on the
   * table.
   */
  public void print() {
    for (int i = 0; i < this.getSize(); ++i) {
      if (this.tableData[i].isActive()) {
        System.out.println("|"
            + getStrFromMemory(tableData[i].getRecordNameHandle()) + "| " + i);
      }
    }
    System.out.println("Total records: " + this.getCount());
  }

  /**
   * Prints the block sizes and their starting positions in the memory bytes
   * pool.
   */
  public void printMemoryPool() {
    System.out.println(memoryManager);
  }

  /**
   * Print a Record with its key-val pairs by reading them from the memory
   * manager.
   * 
   * @param record A non-null Record object to print contents of.
   */
  public void printRecord(Record record) {
    System.out.print(getStrFromMemory(record.getNameHandle()));
    record.moveToFirstHandle();
    MemoryHandle handle = record.yieldHandle();
    while (handle != null) {
      System.out.print("<SEP>");
      System.out.print(getStrFromMemory(handle));
      record.curseToNextHandle();
      handle = record.yieldHandle();
    }
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
    return this.tableData[recordPos].getRecord();
  }

  /**
   * Add an active Record object to the hashtable. Useful when doubling the
   * table size.
   */
  private void addActiveRecord(Record record) {
    int position = getSlotForInsertion(
        getStrFromMemory(record.getNameHandle()));
    tableData[position] = new TableEntry(record);
    ++this.countActive;
  }

  /**
   * Return a slot index in the hashtable for insertion.
   *
   * Candidate positions will be computed using hashing and probing as needed. A
   * free slot or a deleted slot (given that no duplicate exists after the
   * deleted slot) are valid slot indices. Whichever comes first is reurned.
   * 
   * @param key Record name String used for hashing and lookup.
   * @return A valid slot index eligible for insertion, -1 if insertion cannot
   *         be performed.
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
      if (curEntry.isFree()) {
        if (tombstoneSlot != -1) {
          return tombstoneSlot;
        }
        else {
          return runningSlot;
        }
      }

      if ((tombstoneSlot == -1) && curEntry.isDeleted()) {
        tombstoneSlot = runningSlot;
      }

      if (curEntry.isActive()
          && getStrFromMemory(curEntry.getRecordNameHandle()).equals(key)) {
        return -1;
      }

      runningSlot = RecordHashTable.getProbedSlot(homeSlot, ++probeLevel,
          this.getSize());

      ++sanityCounter;

    } while (sanityCounter < this.getSize());
    return -1;
  }

  /**
   * Double the size of the hashtable. Rehashes existing active entries.
   * 
   */
  private void doubleTableSize() {
    RecordHashTable newHashTable = new RecordHashTable(this.getSize() * 2,
        MemoryManager.getLog2(memoryManager.getPoolSize()));
    for (int i = 0; i < this.getSize(); ++i) {
      if (tableData[i].isActive()) {
        Record oldRecord = tableData[i].getRecord();
        newHashTable.addActiveRecord(oldRecord);
      }
    }
    this.tableData = newHashTable.tableData;
    this.size = newHashTable.size;
    this.countActive = newHashTable.countActive;
  }

  /**
   * Return a slot index from the hashtable that has a key being looked for.
   *
   * Candidate positions will be computed using hashing and probing as needed.
   * Only active slots will be considered and the key will be matched against
   * such a slot's key. If a free slot is encountered, then it will be implied
   * that no key is present in the table and -1 will be returned.
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

      if (getStrFromMemory(curEntry.getRecordNameHandle()).equals(key)) {
        return runningSlot;
      }
      runningSlot = RecordHashTable.getProbedSlot(homeSlot, ++probeLevel,
          this.getSize());
      ++sanityCounter;
    } while (sanityCounter < this.getSize());
    return -1;
  }

  /**
   * Obtain the bytes from the MemoryManager as String and return.
   * 
   * @return String key-value pair.
   */
  private String getStrFromMemory(MemoryHandle handle) {
    byte[] data = new byte[handle.getDataSize()];
    this.memoryManager.getBytes(data, handle);
    return new String(data);
  }

  /**
   * Compute the hash function. Uses the "sfold" method from the OpenDSA module
   * on hash functions.
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
   * @param tableSize  The size of the table on which the position is to be
   *                   found.
   */
  private static int getProbedSlot(int homeSlot, int probeLevel,
      int tableSize) {
    return (homeSlot + probeLevel * probeLevel) % tableSize;
  }

}
