// -------------------------------------------------------------------------
/**
 * RecordKeyVal represents a key and a value pair that belong to a Record.
 *
 * Every RecordKeyVal object also has a reference to the next RecordKeyVal
 * object.
 *
 * @see Record.java
 *
 * @author Bimal Gaudel
 * @version 2020-02-03
 */

class RecordKeyVal {
  /**
   * Key of the record.
   */
  private String key;
  /**
   * Value of the record.
   */
  private String val;
  /**
   * The linked RecordKeyVal object.
   */
  private RecordKeyVal nextKeyVal;

  /**
   * Construct RecordKeyVal from a key and a value.
   * 
   * @param k String key.
   * @param v string value.
   */
  public RecordKeyVal(String k, String v) {
    this.key = k;
    this.val = v;
    this.nextKeyVal = null;
  }

  /**
   * Get the key of the object.
   * 
   * @return String key.
   */
  public String getKey() {
    return this.key;
  }

  /**
   * Get the value of the object.
   * 
   * @return String value.
   */
  public String getVal() {
    return this.val;
  }

  /**
   * Get reference to the linked RecordKeyVal object.
   * 
   * @return RecordKeyVal reference.
   */
  public RecordKeyVal getNextKeyVal() {
    return this.nextKeyVal;
  }

  /**
   * Set the linked RecordKeyVal object.
   * 
   * @param kv RecordKeyVal reference.
   */
  public void setNextKeyVal(RecordKeyVal kv) {
    this.nextKeyVal = kv;
  }

  /**
   * Search for the key-value by key in the linked list.
   * 
   * @param k key to search
   * @return RecordKeyVal if key found, null otherwise.
   */
  public RecordKeyVal findKeyVal(String k) {
    if (this.getKey().equals(k)) {
      return this;
    }
    if (this.getNextKeyVal() == null) {
      return null;
    }
    return this.getNextKeyVal().findKeyVal(k);
  }

  /**
   * Delete a key-value entry if key exists in the linked RecordKeyVal.
   * 
   * @param k key to be looked for
   * @return true if any deletion occurs, false otherwise
   */
  public boolean deleteKeyVal(String k) {
    RecordKeyVal curRecordKV = this;

    while (curRecordKV.getNextKeyVal() != null) {
      if (curRecordKV.getNextKeyVal().getKey().equals(k)) {
        curRecordKV.setNextKeyVal(curRecordKV.getNextKeyVal().getNextKeyVal());
        return true;
      }
      curRecordKV = curRecordKV.getNextKeyVal();
    }
    return false;
  }

  /**
   * Append a RecordKeyVal object to the end of the linked list.
   * 
   * @note Duplicate entries are not checked from here.
   * @param kv RecordKeyVal to be appended.
   */
  public void appendKeyVal(RecordKeyVal kv) {
    RecordKeyVal curRecordKV = this;

    while (curRecordKV.getNextKeyVal() != null) {
      curRecordKV = curRecordKV.getNextKeyVal();
    }
    curRecordKV.setNextKeyVal(kv);
  }

  /**
   * String representation of the key-value object.
   * 
   * @return Returns the String representation of the object.
   */
  @Override
  public String toString() {
    return this.getKey().toString() + "<SEP>" + this.getVal().toString();
  }
}
