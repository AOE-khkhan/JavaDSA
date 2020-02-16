// -------------------------------------------------------------------------
/**
 * Record represents a record object specific to the MemMan project.
 *
 * A Record can have none or a number of key-value pairs associated with it.
 * These key-values can be updated or deleted. Note this class however doesn't
 * store the key-value pairs directly. Instead stores the MemoryHandle objects
 * that point to a MemoryManager's database locations where the key-value pairs
 * are stored in bytes form. Also, the Record's name handle is stored in a
 * MemoryManager.
 * 
 * @author Bimal Gaudel
 * @version 2020-02-03
 */
public class Record {
  /**
   * The identifier used for this Record object. A MemoryHandle object that
   * points to the name data.
   */
  private MemoryHandle nameHandle;

  /**
   * Reference to the first RecordKeyVal object. Null by default.
   */
  private LinkedList<MemoryHandle> handleList;

  /**
   * Construct a Record object from its name handle.
   * 
   * @param nameHandle MemoryHandle object with the reference to name data.
   */
  public Record(MemoryHandle nameHandle) {
    this.nameHandle = nameHandle;
    handleList = new LinkedList<MemoryHandle>();
  }

  /**
   * Get the name of this Record object.
   * 
   * @return MemoryHandle object with reference to the name data of this object.
   */
  public MemoryHandle getNameHandle() {
    return this.nameHandle;
  }

  /**
   * Test if the handleList is empty.
   * 
   * @return true if the list has no handle entries, false otherwise.
   */
  public boolean isEmpty() {
    return this.handleList.isEmpty();
  }

  /**
   * Add a MemoryHandle object for the current Record.
   * 
   * @param handle A MemoryHandle object.
   */
  public void addHandle(MemoryHandle handle) {
    this.handleList.append(handle);
  }

  /**
   * Remove a MemoryHandle entry.
   *
   * @param handle A MemoryHandle object.
   * @return true if removed, false otherwise.
   */
  public boolean removeHandle(MemoryHandle handle) {
    return this.handleList.remove(handle);
  }

  /** Move to the first handle in the handleList. */
  public void moveToFirstHandle() {
    this.handleList.moveToFront();
  }

  /** Move to the next handle in the handleList. */
  public void curseToNextHandle() {
    this.handleList.curseToNext();
  }

  /**
   * Get the MemoryHandle at current position in the handleList.
   * 
   * @return MemoryHandle object at the current position in the handleList.
   */
  public MemoryHandle yieldHandle() {
    return this.handleList.yieldNode();
  }
}
