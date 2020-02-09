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
  private RecordKeyVal headRecordKeyVal;

  /**
   * Construct a Record object from its name.
   * 
   * @param n name of the record
   */
  public Record(String n) {
    this.name = n;
    headRecordKeyVal = null;
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
   * @return true if no deletion occurred, false if an existing RecordKeyVal was
   *         first deleted and a new RecordKeyVal was appended.
   */
  public boolean addRecordKeyVal(RecordKeyVal kv) {
    if (this.getHeadRecordKV() == null) {
      this.setHeadRecordKV(kv);
      return true;
    }
    else {
      boolean del_dup = this.deleteRecordKeyVal(kv.getKey());
      if (this.getHeadRecordKV() == null) {
        this.setHeadRecordKV(kv);
      }
      else {
        this.getHeadRecordKV().appendKeyVal(kv);
      }
      return (!del_dup);
    }
  }

  /**
   * Delete a key-value entry if key exists.
   * 
   * @param key key to be looked for
   * @return true if any deletion occurs, false otherwise.
   */
  public boolean deleteRecordKeyVal(String key) {
    if (this.getHeadRecordKV() == null)
      return false;
    if (this.getHeadRecordKV().getKey().equals(key)) {
      this.setHeadRecordKV(this.getHeadRecordKV().getNextKeyVal());
      return true;
    }
    else {
      boolean deleted = this.getHeadRecordKV().deleteKeyVal(key);
      return deleted;
    }
  }

  /**
   * Find a key value entry in the current record.
   *
   * @param key the key to search.
   * @return RecordKeyVal if found, null otherwise.
   */
  public RecordKeyVal findRecordKeyVal(String key) {
    if (this.getHeadRecordKV() == null)
      return null;
    RecordKeyVal found = this.getHeadRecordKV().findKeyVal(key);
    return found;
  }

  /**
   * Override the toString method.
   * 
   * @return String representation of the Record object.
   */
  @Override
  public String toString() {
    String result = this.getName();
    RecordKeyVal curRecordKV = this.getHeadRecordKV();
    while (curRecordKV != null) {
      result += "<SEP>" + curRecordKV.toString();
      curRecordKV = curRecordKV.getNextKeyVal();
    }
    return result;
  }

  /**
   * Get the filed headRecordKeyVal.
   * 
   * @return RecordKeyVal linked list.
   */
  private RecordKeyVal getHeadRecordKV() {
    return this.headRecordKeyVal;
  }

  /**
   * Set the RecordKeyVal data member of this object.
   * 
   * @param kv RecordKeyVal linked list.
   */
  private void setHeadRecordKV(RecordKeyVal kv) {
    this.headRecordKeyVal = kv;
  }

}
