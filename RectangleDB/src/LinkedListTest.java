// ---------------------------------------------------------------------------

import student.TestCase;

/**
 * Test the LinkedList class.
 * 
 * @author  Bimal Gaudel
 * 
 * @version Feb 2020
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

    /** Test the method exists. */
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

    /** Get code coverage for unexecuted statemetns. */
    public void getCodeCoverage() {
        LinkedList<String> myList = new LinkedList<String>();
        myList.append("data");
        myList.moveToHead();
        myList.curseToNext();
        myList.curseToNext();
        myList.curseToNext();
        assertTrue(myList.atEnd());
    }
}
