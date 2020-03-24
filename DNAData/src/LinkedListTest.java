import student.TestCase;

/**
 * Test the LinkedList class.
 * 
 * @author  Bimal Gaudel
 * 
 * @version Mar 24, 2020
 */
public class LinkedListTest extends TestCase {
    /** A LinkedList<String> object used in the tests. */
    private LinkedList<String> strList;

    /** Sets up the tests that follow. */
    public void setUp() {
        strList = new LinkedList<String>();
    }

    /** Test the constructor. */
    public void testConstructor() {
        assertNotNull(strList);
    }

    /** Test the method append. */
    public void testAppend() {

        strList.append("Name");
        strList.append("Class");
        strList.moveToHead();
        assertEquals(strList.yieldNode(), "Name");
        strList.curseToNext();
        assertEquals(strList.yieldNode(), "Class");
    }

    /** Test the remove method. */
    public void testRemove() {
        strList.append("Name");
        strList.append("Class");

        boolean removed = strList.remove("Name");
        assertEquals(removed, true);

        removed = strList.remove("Name");
        assertEquals(removed, false);

        removed = strList.remove("Class");
        assertEquals(removed, true);
    }

    /** Test the method is empty. */
    public void testIsEmpty() {
        strList.append("Address");
        assertEquals(strList.isEmpty(), false);
        strList.remove("Address");
        assertEquals(strList.isEmpty(), true);
    }

    /** Test the exists method. */
    public void testExists() {
        strList.append("Address");
        assertEquals(strList.exists("Address"), true);
        assertEquals(strList.exists("Class"), false);
    }

    /** Test getCount method. */
    public void testGetCount() {
        assertEquals(strList.getCount(), 0);
        strList.append("Name");
        assertEquals(strList.getCount(), 1);
        strList.append("Class");
        strList.append("Address");
        assertEquals(strList.getCount(), 3);
        strList.remove("Name");
        assertEquals(strList.getCount(), 2);
        strList.remove("Address");
        assertEquals(strList.getCount(), 1);
        strList.remove("Class");
        assertEquals(strList.getCount(), 0);
    }

    /** Test yieldIndex method. */
    public void testYieldIndex() {
        strList.append("Name");
        strList.append("Address");
        strList.append("Year");

        assertTrue(strList.yieldIndex(0).equals("Name"));
        assertTrue(strList.yieldIndex(1).equals("Address"));
        assertTrue(strList.yieldIndex(2).equals("Year"));

        assertNull(strList.yieldIndex(-1));
        assertNull(strList.yieldIndex(3));

        // validate current node state is not mutated
        strList.moveToHead();
        strList.curseToNext(); // set curr node to "Address"
        strList.yieldIndex(2);
        assertTrue(strList.yieldNode().equals("Address"));

        strList = new LinkedList<String>();
        assertNull(strList.yieldIndex(0));
        assertNull(strList.yieldIndex(-1));
        assertNull(strList.yieldIndex(1));
    }

    /** Test the insert method. */
    public void testInsert() {
        strList.insert("Name");
        strList.moveToHead();
        assertEquals(strList.yieldNode(), "Name");

        assertEquals(strList.getCount(), 1);

        strList.insert("Naame");
        strList.moveToHead();
        assertEquals(strList.yieldNode(), "Naame");

        assertEquals(strList.getCount(), 2);

        strList.insert("Class");
        strList.insert("Day Job");
        strList.insert("Address");
        assertEquals(strList.getCount(), 5);
        strList.moveToHead();
        assertEquals(strList.yieldNode(), "Address");
    }

    /** Test the popFront method. */
    public void testPopFront() {
        strList.append("Name");
        strList.append("Class");
        strList.append("Address");
        assertEquals(strList.popFront(), "Name");
        assertEquals(strList.popFront(), "Class");
        assertEquals(strList.popFront(), "Address");
        assertNull(strList.popFront());
    }


    /** Get code coverage for unexecuted statemetns. */
    public void testGetCodeCoverage() {
        LinkedList<String> myList = new LinkedList<String>();
        myList.append("data");
        myList.moveToHead();
        myList.curseToNext();
        myList.curseToNext();
        myList.curseToNext();
        assertTrue(myList.atEnd());
    }
}
