/**
 * A communicator class that responds to commands received to manage the memory
 * and the hashtable.
 *
 * @author Bimal Gaudel
 * @version 2020-02-08
 */
class Communicator {
  /** The hashtable we are working with. */
  private RecordHashTable theHashTable;

  /** The memory manager we are working with. */
  private MemoryManager theMemoryManager;

  /**
   * Initialize the communicator.
   *
   * @param ht Reference to a RecordHashTable object.
   */
  public Communicator(RecordHashTable ht, MemoryManager mm) {
    this.theHashTable = ht;
    this.theMemoryManager = mm;
  }

  /**
   * Prints theHashTable.
   */
  public void printHashTable() {
    this.theHashTable.print();
  }

  /**
   * Prints the block sizes and their starting positions in the memory bytes
   * pool.
   */
  public void printMemoryPool() {
    System.out.print(theMemoryManager);
  }

  /**
   * Add a Record name to the hashtable database.
   * 
   * @param recordName A String name that identifies a Record.
   */
  public void addRecordToHashTable(String recordName) {
    int tableSize = this.theHashTable.getSize();
    boolean added = this.theHashTable.addRecord(new Record(recordName));

    if (added) {
      System.out
          .println("|" + recordName + "| has been added to the Name database.");

      if (this.theHashTable.getSize() > tableSize) {
        System.out.println("Name hash table size doubled to "
            + this.theHashTable.getSize() + " slots.");
      }

    }
    else {
      System.out.println("|" + recordName
          + "| duplicates a record already in the Name database.");
    }

  }

  /**
   * Deletes a Record from the hashtable database.
   *
   * @param recordName String identifier of the Record to delete.
   */
  public void deleteRecordFromHashTable(String recordName) {
    boolean deleted = this.theHashTable.deleteRecord(recordName);
    if (deleted) {
      System.out.println(
          "|" + recordName + "| has been deleted from the Name database.");
    }
    else {
      System.out.println("|" + recordName
          + "| not deleted because it does not exist in the Name database.");
    }
  }

  /**
   * Add a key-value entry to a Record object.
   *
   * If Record doesn't exist, nothing will be changed. If the key already
   * exists, the existing key-value pair will be deleted and the new key-value
   * will be appended to the database.
   *
   * @param recordName String identifier of the Record.
   * @param keyName    String key of the key-val entry being updated.
   * @param value      String value of the associated key.
   */
  public void updateAddRecordKeyVal(String recordName, String keyName,
      String value) {

    Record theRecord = this.theHashTable.getRecord(recordName);

    if (theRecord == null) {
      System.out.println("|" + recordName
          + "| not updated because it does not exist in the Name database.");
      return;
    }

    theRecord.moveToFirstHandle();
    MemoryHandle runningHandle = theRecord.yieldHandle();
    while (runningHandle != null) {
      String thisHandlesKV = this.retainKeyValFromMemory(runningHandle);
      if (thisHandlesKV.startsWith(keyName + "<SEP>")) {
        theRecord.removeHandle(runningHandle);
        break;
      }
      theRecord.curseToNextHandle();
      runningHandle = theRecord.yieldHandle();
    }

    int poolSize = this.theMemoryManager.getPoolSize();
    String serializedKeyVal = keyName + "<SEP>" + value;
    runningHandle = theMemoryManager.storeBytes(serializedKeyVal.getBytes());
    theRecord.addHandle(runningHandle);
    System.out.print("Updated Record: |");
    this.printRecord(theRecord);
    System.out.println("|");
    if (poolSize < this.theMemoryManager.getPoolSize()) {
      System.out.println("Memory pool expanded to "
          + this.theMemoryManager.getPoolSize() + " bytes.");
    }
  }

  /**
   * Delete a key-value entry from a Record object.
   *
   * If the Record doesn't exist or if the key doesn't exist in the Record,
   * nothing will be changed.
   *
   * @param recordName String identifier of the Record.
   * @param keyName    String key of the key-val entry being deleted.
   */
  public void updateDeleteRecordKeyVal(String recordName, String keyName) {
    Record theRecord = this.theHashTable.getRecord(recordName);

    if (theRecord == null) {
      System.out.println("|" + recordName
          + "| not updated because it does not exist in the Name database.");
      return;
    }

    boolean deleted = false;
    theRecord.moveToFirstHandle();
    MemoryHandle handle = theRecord.yieldHandle();
    while (handle != null) {
      String thisHandlesKV = this.retainKeyValFromMemory(handle);
      if (thisHandlesKV.startsWith(keyName + "<SEP>")) {
        theRecord.removeHandle(handle);
        deleted = true;
        break;
      }
      theRecord.curseToNextHandle();
      handle = theRecord.yieldHandle();
    }

    if (deleted) {
      System.out.print("Updated Record: |");
      this.printRecord(theRecord);
      System.out.println("|");
    }
    else {
      System.out.println("|" + recordName + "| not updated because the field |"
          + keyName + "| does not exist");
    }
  }

  /**
   * Given a MemoryHandle, obtain the bytes as string from the MemoryManager and
   * return.
   * 
   * @return String key-value pair.
   */
  private String retainKeyValFromMemory(MemoryHandle handle) {
    byte[] data = new byte[handle.getDataSize()];
    this.theMemoryManager.getBytes(data, handle);
    return new String(data);
  }

  /**
   * Print a Record with its key-val pairs by reading them from the memory
   * manager.
   * 
   * @param record A Record object to print contents of.
   */
  private void printRecord(Record record) {
    System.out.print(record.getName());
    record.moveToFirstHandle();
    MemoryHandle handle = record.yieldHandle();
    while (handle != null) {
      System.out.print("<SEP>");
      System.out.print(this.retainKeyValFromMemory(handle));
      record.curseToNextHandle();
      handle = record.yieldHandle();
    }
  }

}
