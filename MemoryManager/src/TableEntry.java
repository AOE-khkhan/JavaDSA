// -------------------------------------------------------------------------
/**
 * Represents a table entry in the HashTableRecord object.
 *
 * @author Bimal Gaudel
 * @version 2020-02-04
 *
 */
public class TableEntry {
  /**
   * The record this table entry points to.
   */
  private Record record;
  /**
   * Is the entry active, deleted or free?
   */
  private SlotInfo info;

  /** Empty parameter constructor. */
  public TableEntry() {
    this(null);
  }

  /**
   * Construct TableEntry with a Record parameter.
   * 
   * @param record A Record object.
   */
  public TableEntry(Record record) {
    this.record = record;
    this.info = (this.getRecord() == null) ? SlotInfo.FREE : SlotInfo.ACTIVE;
  }

  /**
   * Get the Record object this TableEntry holds.
   * 
   * @return Record object.
   */
  public Record getRecord() {
    return this.record;
  }

  /**
   * Get the name field of the Record object held by this TableEntry.
   * 
   * @return name field of the Record object held by this object.
   */
  public String getRecordName() {
    return this.getRecord().getName();
  }

  /**
   * Check if this entry is active. An active entry is neither deleted nor empty.
   * 
   * @return true if the entry is active.
   */
  public boolean isActive() {
    return this.info == SlotInfo.ACTIVE;
  }

  /**
   * Check if this entry is deleted. A deleted entry is neither active nor empty.
   * 
   * @return true if the entry is deleted.
   */
  public boolean isDeleted() {
    return this.info == SlotInfo.DELETED;
  }

  /**
   * Check if the entry is free. A free entry is neither active nor deleted.
   * 
   * @return true if the entry is free.
   */
  public boolean isFree() {
    return this.info == SlotInfo.FREE;
  }

  /**
   * Mark this entry as deleted. This doesn't make this entry a free entry though.
   */
  public void markDeleted() {
    this.info = SlotInfo.DELETED;
  }

  /**
   * Denote if the entry is active, deleted or free.
   */
  public static enum SlotInfo {
    ACTIVE, FREE, DELETED
  }

}
