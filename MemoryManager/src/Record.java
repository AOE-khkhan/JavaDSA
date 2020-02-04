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
  // the identifier used for this Record object
  private String name;

  // reference to the first RecordKeyVal object
  // which will be null by default
  private RecordKeyVal headRecord;

  public Record(String name) {
    this.name = name;
    headRecord = null;
  }

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
    if (this.getHeadRecord().getKey() == key) {
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

  public void printRecord() {
    if (this.getHeadRecord() == null)
      return;
    this.getHeadRecord().printKeyVal();
  }

  private RecordKeyVal getHeadRecord() {
    return this.headRecord;
  }

  private void setHeadRecord(RecordKeyVal kv) {
    this.headRecord = kv;
  }

}
