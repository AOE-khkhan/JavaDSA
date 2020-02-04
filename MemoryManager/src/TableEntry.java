// -------------------------------------------------------------------------
/**
 * Represents a table entry in the HashTableRecord object.
 *
 * @author Bimal Gaudel
 * @version 2020-02-04
 *
 */
public class TableEntry {
  // The record this table entry points to
  private Record record;
  // Is the entry active, deleted or free?
  private SlotInfo info;

  public TableEntry() {
    this(null);
  }

  public TableEntry(Record record) {
    this.record = record;
    this.info = (this.getRecord() == null) ? SlotInfo.FREE : SlotInfo.ACTIVE;
  }

  public Record getRecord() {
    return this.record;
  }

  public boolean isActive() {
    return this.info == SlotInfo.ACTIVE;
  }

  public boolean isDeleted() {
    return this.info == SlotInfo.DELETED;
  }

  public boolean isFree() {
    return this.info == SlotInfo.FREE;
  }

  public void markDeleted() {
    this.info = SlotInfo.DELETED;
  }

  /**
   * Tells if the entry is active, deleted or free.
   */
  public static enum SlotInfo {
    ACTIVE, FREE, DELETED
  }

}
