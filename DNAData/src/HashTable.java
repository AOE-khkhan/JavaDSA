/**
 * A hash table class to store a key-value record where key is of long integer
 * type and the value can be any Object. Key is hashed and found a position in
 * the table to store the key-value pair.
 *
 * @author  Bimal Gaudel
 * 
 * @version Mar 24, 2020
 *
 */
public class HashTable {
    /** The initialized size of the table. */
    private int initSize;

    /**
     * The number of active records the table holds.
     */
    private int countActive;

    /**
     * The database handled by this hash table.
     */
    private TableEntry[] tableData;

    /**
     * Construct a hash table.
     * 
     * @param tableSize The number of entries initialized.
     */
    HashTable(int tableSize) {
        initSize = tableSize;
        countActive = 0;
        tableData = new TableEntry[tableSize];
    }

    /**
     * Get the size of the table.
     * 
     * @return Total number of entries present in the table.
     */
    public int getSize() {
        return initSize;
    }

    /**
     * Get the number of active entries.
     * 
     * @return Number of active entries.
     */
    public int getCountActive() {
        return countActive;
    }

    /**
     * Store value in the table along with key. Key is used for hashing.
     * 
     * @param  key   The key of the record to be inserted.
     * @param  value Object to be stored associated with the key.
     * 
     * @return       True if no duplicate pre-exist in the table.
     */
    public boolean insert(long key, Object value) {
        int slot = findSlotForInsertion(key);
        if (slot == -1) {
            // duplicate exists
            // insertion cannot be performed
            return false;
        }

        // store the record
        tableData[slot] = new TableEntry(key, value);
        ++countActive;
        return true;
    }

    /**
     * Mark an entry in the table as tombstone.
     * 
     * @param  key The key of the entry to be marked as tombstone.
     * 
     * @return     True if key exists in the database.
     */
    public Object delete(long key) {
        int slot = findSlot(key);
        if (slot == -1) {
            // key doesn't exist
            return null;
        }

        // mark the deleted entry as a tombstone
        tableData[slot].markTombstone();
        --countActive;
        return tableData[slot].getValue();
    }


    /**
     * Get the value associated with a key.
     * 
     * @param  key The key of the entry to look for.
     * 
     * @return     The value corresponding to key if key exists, null otherwise.
     */
    public Object get(long key) {
        int slot = findSlot(key);
        if (slot == -1) {
            // key doesn't exist in the database
            return null;
        }
        // key exists
        return tableData[slot].getValue();
    }

    /**
     * Check if the table has at least half of the entries marked as active.
     * 
     * @return True if floor(size/2) entries are active.
     */
    public boolean isHalfFull() {
        return (initSize / 2) == countActive;
    }

    /**
     * Double the size of the hash table. This will replace the table database
     * with a new one. All currently active entries will be moved to the new
     * database by rehashing.
     */
    public void doubleTableSize() {
        HashTable biggerTable = new HashTable(2 * initSize);

        for (TableEntry entry : tableData) {
            // iter through all entries
            // skip processing empty or tombstone slots
            if ((entry == null) || (entry.isTombstone())) {
                continue;
            }

            biggerTable.insert(entry.getKey(), entry.getValue());
        }

        // update this table's database
        this.initSize = biggerTable.initSize;
        this.tableData = biggerTable.tableData;
    }

    /**
     * Get string representation of the hash table.
     * 
     * @return Keys and their slot positions in separate lines followed by the
     *         total number of active records present.
     */
    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < tableData.length; ++i) {
            TableEntry entry = tableData[i];
            if ((entry == null) || (entry.isTombstone())) {
                // not occupied slots or tombstone slots are skipped
                continue;
            }
            result += entry.getKey() + ": " + i + "\n";
        }
        result += "Total records: " + getCountActive();
        return result;
    }


    /**
     * Compute the hash function. This works for long int values, using a
     * mid-square style computation.
     *
     * @param  key The int key to be hashed.
     * 
     * @return     The home slot for string
     */
    private int getHomeSlot(long key) {

        key = ((key >> 32) | (key & 0xffffffff));
        if (key < 256) {
            key = key << 16;
        }
        key = key * key;
        key = (key >> 15) & 0xffffffff;
        key = Math.abs(key);
        return (int) (Math.abs(key) % getSize());
    }

    /**
     * Compute a probed home slot using quadratic probing.
     *
     * @param  homeSlot   The home slot position without probing.
     * @param  probeLevel Number of times position finding is attempted before.
     * 
     * @return            The next slot found by probing.
     */
    private int getProbedSlot(int homeSlot, int probeLevel) {
        return (homeSlot + probeLevel * probeLevel) % getSize();
    }

    /**
     * Return a slot index in the hashtable for insertion.
     *
     * Candidate positions will be computed using hashing and probing as needed.
     * A free slot or a deleted slot (given that no duplicate exists after the
     * deleted slot) are valid slot indices. Whichever comes first is returned.
     *
     * @param  key A key used for hashing and lookup.
     * 
     * @return     A valid slot index eligible for insertion, -1 if insertion
     *             cannot be performed.
     */
    private int findSlotForInsertion(long key) {
        int homeSlot = getHomeSlot(key);
        int runningSlot = homeSlot;
        int tombstoneSlot = -1;
        int probeLevel = 0;
        TableEntry curEntry = null;

        // @note It is assumed that the state of the hash table allows this
        // loop to break
        for (;;) {
            curEntry = tableData[runningSlot];

            if (curEntry == null) {
                // found a free slot
                // if tombstoneSlot is also found to be available
                // we prefer tombstones to be occupied
                if (tombstoneSlot != -1) {
                    return tombstoneSlot;
                }
                return runningSlot;
            }

            // found a non-empty slot
            //
            // is it a tombstone? if yes update our tombstone slot
            if ((tombstoneSlot == -1) && curEntry.isTombstone()) {
                tombstoneSlot = runningSlot;
                // we don't return from here because we still need to make sure
                // that no duplicate entries are present beyond the tombstone
            }

            if ((!curEntry.isTombstone()) && curEntry.getKey() == key) {
                // found a duplicate that's not a tombstone
                // insertion not possible
                return -1;
            }

            // update the running slot
            runningSlot = getProbedSlot(homeSlot, ++probeLevel);
        } // for
    }

    /**
     * Return a slot index from the hashtable that has a key being looked for.
     *
     * Candidate positions will be computed using hashing and probing as needed.
     * Only active slots will be considered and the key will be matched against
     * such a slot's key. If a free slot is encountered, then it will be implied
     * that no key is present in the table and -1 will be returned.
     * 
     * @param  key A key used for hashing and lookup.
     * 
     * @return     The slot index if the key exists, -1 otherwise.
     */
    private int findSlot(long key) {
        int homeSlot = getHomeSlot(key);
        int runningSlot = homeSlot;
        int probeLevel = 0;
        TableEntry curEntry = null;

        // @note It is assumed that the state of the hash table allows this
        // loop to break
        for (;;) {
            curEntry = tableData[runningSlot];
            if (curEntry == null) {
                // encountered a free slot
                return -1;
            }

            // only active slots are considered
            if ((!curEntry.isTombstone()) && (curEntry.getKey() == key)) {
                // found the slot with the key
                return runningSlot;
            }

            // update the runningSlot
            runningSlot = getProbedSlot(homeSlot, ++probeLevel);
        } // for
    }

    /**
     * A helper class to represent an entry in the hash table. Allows for
     * effective tagging of entries for active, deleted or free.
     * 
     * @author  Bimal Gaudel
     * 
     * @version Mar 24, 2020
     */
    private class TableEntry {
        /** The key of the entry. */
        private long key;

        /** The value in the key-val pair stored by this entry. */
        private Object recordValue;

        /** Whether this is a deleted entry or not. */
        private boolean tombstone;

        /** Construct a hash table entry. */
        TableEntry(long key, Object recordValue) {
            this.key = key;
            this.recordValue = recordValue;
            this.tombstone = false;
        }

        /**
         * Getter for the key.
         * 
         * @return The key of the entry.
         */
        public long getKey() {
            return key;
        }

        /**
         * Getter for the record value.
         * 
         * @return The value stored by the entry.
         */
        public Object getValue() {
            return recordValue;
        }

        /**
         * Check if entry is a tombstone.
         * 
         * @return True if entry is marked deleted.
         */
        public boolean isTombstone() {
            return tombstone;
        }

        /** Mark this entry as a tombstone. */
        public void markTombstone() {
            tombstone = true;
        }
    }
}
