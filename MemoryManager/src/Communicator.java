/* **
 * A communicator class that responds to commands received to manage the memory
 * and the hashtable.
 *
 * @author Bimal Gaudel
 * @version 2020-02-08
 */
class Communicator {
  /**
   * The hashtable we are working on.
   */
  private RecordHashTable theHashTable;

  /**
   * Initialize the communicator.
   *
   * @param ht Reference to a RecordHashTable object.
   */
  public Communicator(RecordHashTable ht) {
    this.theHashTable = ht;
  }

  /**
   * Prints theHashTable.
   */
  public void printHashTable() {
    this.theHashTable.print();
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

    if (theRecord != null) {
      theRecord.addRecordKeyVal(new RecordKeyVal(keyName, value));
      System.out.println("Updated Record: |" + theRecord.toString() + "|");
    }
    else {
      System.out.println("|" + recordName
          + "| not updated because it does not exist in the Name database.");
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

    if (theRecord != null) {
      boolean deleted = theRecord.deleteRecordKeyVal(keyName);
      if (deleted) {
        System.out.println("Updated Record: |" + theRecord.toString() + "|");
      }
      else {
        System.out
            .println("|" + recordName + "| not updated because the field |"
                + keyName + "| does not exist");
      }
    }
    else {
      System.out.println("|" + recordName
          + "| not updated because it does not exist in the Name database.");
    }

  }
}
