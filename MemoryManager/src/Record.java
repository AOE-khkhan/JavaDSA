// -------------------------------------------------------------------------
/**
 * Record represents a record object specific to the MemMan project.
 *
 * A Record can have none or a number of key-value pairs associated with it.
 * These key-values can be updated or deleted.
 * 
 * @author Bimal Gaudel
 * @version 2020-02-03
 */
public class Record {
  /**
   * The identifier used for this Record object.
   */
  private String name;

  /**
   * Reference to the first RecordKeyVal object. Null by default.
   */
  private RecordKeyVal headRecord;

  /** Construct a Record object from its name. */
  public Record(String name) {
    this.name = name;
    headRecord = null;
  }

  /**
   * Get the name of this Record object.
   * 
   * @return String that is the name of this record.
   */
  public String getName() {
    return this.name;
  }

  /**
   * Add a RecordKeyVal entry.
   * 
   * @param kv the RecordKeyVal object to be added.
   * @return true if no deletion occured, false if an existing RecordKeyVal was
   *         first deleted and a new RecordKeyVal was appended.
   */
  public boolean addRecordKeyVal(RecordKeyVal kv) {
    if (this.getHeadRecord() == null) {
      this.setHeadRecord(kv);
      return true;
    } else {
      boolean del_dup = this.deleteRecordKeyVal(kv.getKey());
      this.getHeadRecord().appendKeyVal(kv);
      return del_dup;
    }
  }

  /**
   * Delete a key-value entry if key exists in the headRecord data member.
   * 
   * @param key key to be looked for
   * @return true if any deletion occurs, false otherwise.
   */
  public boolean deleteRecordKeyVal(String key) {
    if (this.getHeadRecord() == null)
      return false;
    if (this.getHeadRecord().getKey().equals(key)) {
      this.setHeadRecord(this.getHeadRecord().getNextKeyVal());
      return true;
    } else {
      return this.getHeadRecord().deleteKeyVal(key);
    }
  }

  /**
   * Find a key value entry in the current record.
   *
   * @param key the key to search.
   * @return RecordKeyVal if found, null otherwise.
   */
  public RecordKeyVal findRecordKeyVal(String key) {
    if (this.getHeadRecord() == null)
      return null;
    return this.getHeadRecord().findKeyVal(key);
  }

  /**
   * Print the Record. Intended for testing purpose.
   */
  public void printRecord() {
    if (this.getHeadRecord() == null)
      return;
    this.getHeadRecord().printKeyVal();
  }

  /**
   * Get the head of the RecordKeyVal data structure. RecordKeyVal is a linked
   * list.
   * 
   * @return RecordKeyVal linked list.
   */
  private RecordKeyVal getHeadRecord() {
    return this.headRecord;
  }

  /**
   * Set the RecordKeyVal data member of this object.
   * 
   * @param kv RecordKeyVal linked list.
   */
  private void setHeadRecord(RecordKeyVal kv) {
    this.headRecord = kv;
  }

}
