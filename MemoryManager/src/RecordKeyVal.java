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
  private String key;
  private String val;
  private RecordKeyVal nextKeyVal;

  public RecordKeyVal(String k, String v) {
    this.key = k;
    this.val = v;
    this.nextKeyVal = null;
  }

  public String getKey() {
    return this.key;
  }

  public String getVal() {
    return this.val;
  }

  public RecordKeyVal getNextKeyVal() {
    return this.nextKeyVal;
  }

  public void setNextKeyVal(RecordKeyVal kv) {
    this.nextKeyVal = kv;
  }

  /**
   * Search for the key-value by key in the linked list.
   * 
   * @param key key to search
   * @return RecordKeyVal if key found, null otherwise.
   */
  public RecordKeyVal findKeyVal(String key) {
    if (this.getKey() == key)
      return this;
    if (this.getNextKeyVal() == null)
      return null;
    return this.getNextKeyVal().findKeyVal(key);
  }

  /**
   * Delete a key-value entry if key exists in the linked RecordKeyVal.
   * 
   * @param key key to be looked for
   * @return true if any deletion occurs, false otherwise
   */
  public boolean deleteKeyVal(String key) {
    if (this.getNextKeyVal() == null)
      return false;
    if (this.getNextKeyVal().getKey() == key) {
      this.setNextKeyVal(this.getNextKeyVal().getNextKeyVal());
      return true;
    } else {
      return this.getNextKeyVal().deleteKeyVal(key);
    }
  }

  /**
   * Append a RecordKeyVal object to the end of the linked list.
   * 
   * @note Duplicate entries are not checked from here.
   * @param kv RecordKeyVal to be appended.
   */
  public void appendKeyVal(RecordKeyVal kv) {
    if (this.getNextKeyVal() == null)
      this.setNextKeyVal(kv);
    else
      getNextKeyVal().appendKeyVal(kv);
  }

  /**
   * Print the key-value entries from all RecordKeyVal's.
   *
   * Intended for testing.
   */
  public void printKeyVal() {
    System.out.println(this.getKey() + " " + this.getVal());
    if (this.getNextKeyVal() == null)
      return;
    this.getNextKeyVal().printKeyVal();
  }
}
