import student.TestCase;

/**
 * Test the HashTable class.
 * 
 * @author  Bimal Gaudel
 * 
 * @version Mar 25, 2020
 */
public class HashTableTest extends TestCase {

    /**
     * A HashTable object used for test methods below.
     */
    private HashTable table;

    /**
     * Sets up the tests that follow.
     */
    public void setUp() {
        table = new HashTable(10);
    }

    /**
     * Test the size getter method.
     */
    public void testGetSize() {
        assertEquals(table.getSize(), 10);
        assertEquals(table.getCountActive(), 0);
    }

    /**
     * Test the insertion method of the HashTable.
     */
    public void testInsert() {
        boolean success;
        // successful insertion
        success = table.insert(1, "Poochie");
        assertTrue(success);

        // successful insertion
        success = table.insert(2, "Pie");
        assertTrue(success);
        assertEquals(table.getCountActive(), 2);

        // duplicate insertion
        success = table.insert(2, "Pie");
        assertFalse(success);
        //
        assertEquals(table.getCountActive(), 2);
    }

    /** Test the deletion method of HashTable. */
    public void testDelete() {
        Object success;
        table.insert(1L, "Poochie");
        table.insert(2L, "Pie");
        assertEquals(table.getCountActive(), 2);

        // successful deletion
        success = table.delete(1L);
        assertNotNull(success);
        assertEquals(table.getCountActive(), 1);
        // deleting non-existent key
        success = table.delete(1L);
        assertNull(success);
        assertEquals(table.getCountActive(), 1);

        // successful deletion
        success = table.delete(2L);
        assertNotNull(success);
        assertEquals(table.getCountActive(), 0);
        // deleting non-existent key
        success = table.delete(2L);
        assertNull(success);
        assertEquals(table.getCountActive(), 0);
    }

    /**
     * Test the record getter method.
     */
    public void testGet() {
        table.insert(1, "Charlie");
        table.insert(2, "Max");
        table.insert(3, "Buddy");

        Object found = table.get(1);
        assertNotNull(found);
        assertEquals((String) found, "Charlie");

        found = table.get(4);
        assertNull(found);
    }

    /** Test the toString method of the HashTable. */
    public void testToString() {
        table.insert(1234123456789l, "Death Note");
        table.insert(21l, "Can you handle?");

        String expectedString =
                "21: 2\n" + "1234123456789: 6\n" + "Total records: 2";
        assertFuzzyEquals(expectedString, table.toString());
    }
}
