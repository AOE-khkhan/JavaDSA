import java.util.Random;

/**
 * Skiplist data structure.
 *
 * @param   <K> Type of the record key stored in the list.
 * 
 * @author      Bimal Gaudel
 * 
 * @version     Apr 17, 2020
 */
public class SkipList<K extends Comparable<K>> {

    /** The head node of the list. */
    private SkipNode<K> head;

    /** The highest level for any node currently in the list. */
    private int level;

    /** Number of nodes in the list. */
    private int size;

    /** Hold the Random class object. */
    static private Random rand = new Random();

    /**
     * Getter of the list size.
     * 
     * @return Length of the list.
     */
    public int length() {
        return size;
    }

    /**
     * Pick a random level using geometric distribution.
     * 
     * @return Radom integer using geometric distribution.
     */
    static private int randomLevel() {
        int lev;
        for (lev = 0; Math.abs(rand.nextInt()) % 2 == 0; ++lev) {
            // do nothing
        }
        return lev;
    }

    /** Construct SkipList. */
    public SkipList() {
        head = new SkipNode<K>(null, null, 0);
        level = -1;
        size = 0;
    }

    /**
     * Find the record by key if it exists.
     *
     * @param  key Key of the record.
     * 
     * @return     First record with matching key, null otherwise.
     */
    Object find(K key) {
        SkipNode<K> currNode = head; // dummy header node
        for (int i = level; i >= 0; --i) {
            while ((currNode.getForward()[i] != null)
                    && (currNode.getForward()[i].getKey().compareTo(key) < 0)) {
                currNode = currNode.getForward()[i]; // Go one last step
            }
        }
        // move to actual record if it exists
        currNode = currNode.getForward()[0];
        if ((currNode != null) && (currNode.getKey().compareTo(key) == 0)) {
            // got it
            return currNode.getValue();
        }
        return null;
    }

    /**
     * Insert a key, value pair into the list.
     * 
     * @param key   Key of the record.
     * @param value Value of the record.
     */
    public void insert(K key, Object value) {
        int newLevel = randomLevel(); // new node's level
        if (newLevel > level) { // if new node is deeper
            adjustHead(newLevel); // adjust the header
        }
        // track end of level
        SkipNode<K>[] update = new SkipNode[level + 1];
        SkipNode<K> currNode = head; // start at header node
        for (int i = level; i >= 0; --i) {
            // find insert position
            while ((currNode.getForward()[i] != null)
                    && (currNode.getForward()[i].getKey().compareTo(key) < 0)) {
                currNode = currNode.getForward()[i];
            }
            update[i] = currNode; // track end at level i
        }
        currNode = new SkipNode<K>(key, value, newLevel);
        for (int i = 0; i <= newLevel; ++i) { // splice into list
            currNode.getForward()[i] = update[i].getForward()[i];
            update[i].getForward()[i] = currNode;
        }
        ++size;
    }

    /**
     * Delete a record with the given key.
     * 
     * @param key Key of the object to be deleted.
     * 
     */
    public void delete(K key) {
        // track end of level
        SkipNode<K>[] update = new SkipNode[level + 1];
        SkipNode<K> currNode = head; // start at header node
        for (int i = level; i >= 0; --i) {
            // find insert position
            while ((currNode.getForward()[i] != null)
                    && (currNode.getForward()[i].getKey().compareTo(key) < 0)) {
                currNode = currNode.getForward()[i];
            }
            update[i] = currNode; // track end at level i
        }
        currNode = update[0].getForward()[0];
        int currNodeLevel = currNode.getForward().length - 1;
        for (int i = 0; i <= level; ++i) { // splice into list
            if (i <= currNodeLevel) {
                update[i].getForward()[i] = currNode.getForward()[i];
            }
        }
        --size;
    }

    /**
     * Print the records from the list in alphabetical order.
     * 
     * @param start Records with keys greater than or equal to start
     *              will be printed.
     * @param end   Records with keys smaller than or equal to end
     *              will be printed.
     */
    public void rangePrint(K start, K end) {
        // figure out where to start printing
        SkipNode<K> currNode = head; // dummy header node
        for (int i = level; i >= 0; --i) {
            while ((currNode.getForward()[i] != null)
                    && (currNode.getForward()[i].getKey()
                            .compareTo(start) < 0)) {
                currNode = currNode.getForward()[i]; // Go one last step
            }
        }
        // move to actual printable record if it exists
        currNode = currNode.getForward()[0];
        while ((currNode != null) && (currNode.getKey().compareTo(end) <= 0)) {
            System.out.println(currNode.getValue());
            currNode = currNode.getForward()[0];
        }
    }

    /**
     * Adjust the head node of the list for a new level.
     * 
     * @param newLevel New highest level of skip list pointers in the list.
     */
    private void adjustHead(int newLevel) {
        SkipNode<K> temp = head;
        head = new SkipNode<K>(null, null, newLevel);
        for (int i = 0; i <= level; ++i) {
            head.getForward()[i] = temp.getForward()[i];
        }
        level = newLevel;
    }

    /**
     * Dump the skip list.
     * 
     * @return String representation of the list.
     */
    @Override
    public String toString() {
        String result = "SkipList dump:\n";

        int numNodesPrinted = -1; // dis-count head node printing
        SkipNode<K> currNode = head;

        while (currNode != null) {
            result += "Node has depth " + (currNode.getForward().length - 1)
                    + ", Value (";
            result += (currNode.getValue() != null)
                    ? currNode.getValue().toString()
                    : "null";
            result += ")\n";
            ++numNodesPrinted;
            currNode = currNode.getForward()[0];
        }
        result += numNodesPrinted + " skiplist nodes printed";

        return result;
    }

    /**
     * A node in the SkipList class.
     * 
     * @param   <KeyType> The type of key in the records stored.
     *
     * @author            Bimal Gaudel
     * 
     * @version           Apr 17, 2020
     */
    private class SkipNode<KeyType extends Comparable<KeyType>> {
        /** Key of the record. */
        private KeyType key;

        /** Value of the record. */
        private Object value;

        /** Skip list pointers from level zero upwards. */
        private SkipNode<KeyType>[] forward;

        /**
         * Getter of the key field.
         * 
         * @return Key of the record.
         */
        public KeyType getKey() {
            return key;
        }

        /**
         * Getter of the value field.
         * 
         * @return Value of the record.
         */
        public Object getValue() {
            return value;
        }

        /**
         * Getter of forward pointers array.
         * 
         * @return Reference to the forward pointers array.
         */
        public SkipNode<KeyType>[] getForward() {
            return forward;
        }

        /**
         * Construct SkipNode.
         * 
         * @param key   Key of the record.
         * @param value Value of the record.
         * @param level The highest level in the node for the SkipList pointers.
         */
        public SkipNode(KeyType key, Object value, int level) {
            this.key = key;
            this.value = value;
            forward = new SkipNode[level + 1];
            for (int i = 0; i < level; ++i) {
                forward[i] = null;
            }
        }

    }
}
