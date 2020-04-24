import student.TestCase;

/**
 * Test the class SkipList.
 * 
 * @author  Bimal Gaudel
 * 
 * @version Apr 17, 2020
 */
public class SkipListTest extends TestCase {
    private SkipList<Integer> list;

    /** Sets up the tests that follow. */
    public void setUp() {
        list = new SkipList<Integer>();
    }

    /** Test the insert method. */
    public void testInsert() {
        list.insert(1, "A");
        list.insert(2, "B");
        assertEquals(list.length(), 2);
    }

    /** Test the find method. */
    public void testFind() {
        list.insert(1, "A");
        list.insert(2, "B");
        assertEquals((String) list.find(1), "A");
        assertEquals((String) list.find(2), "B");
    }

    /** Test the delete method. */
    public void testDelete() {
        System.out.println("\nTesting delete method\n-----------");
        list.insert(1, "A");
        list.insert(2, "B");
        list.insert(6, "F");
        list.insert(7, "G");
        list.insert(8, "H");
        list.insert(3, "C");
        list.insert(4, "D");
        list.insert(5, "E");
        System.out.println(list);

        list.delete(2);
        System.out.println(list);
        list.delete(6);
        System.out.println(list);

        assertTrue(systemOut().getHistory()
                .endsWith("6 skiplist nodes printed\n"));
    }

    /** Test the rangePrint method. */
    public void testRangePrint() {
        list.insert(1, "A");
        list.insert(2, "B");
        list.insert(6, "F");
        list.insert(7, "G");
        list.insert(8, "H");
        list.insert(3, "C");
        list.insert(4, "D");
        list.insert(5, "E");
        list.rangePrint(6, 8);

        String expectedOutput = "F\nG\nH\n";
        assertFuzzyEquals(systemOut().getHistory(), expectedOutput);
    }
}
