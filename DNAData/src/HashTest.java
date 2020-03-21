import student.TestCase;

// -------------------------------------------------------------------------
/**
 * Test the hash function (you should throw this away for your project)
 *
 * @author CS5040 staff
 * @version March 2020
 */
public class HashTest extends TestCase {
    /**
     * Sets up the tests that follow.
     */
    public void setUp() {
        // Nothing Here
    }


    /**
     * Test the hash function
     */
    public void testSimpleInsert() throws Exception {
        int tSize = 10;
        System.out.println("Do simple hash insert");
        Hash ht = new Hash(tSize);
        long key1 = 9876543210L;
        assertTrue(ht.h(1, tSize) == 2);
        assertTrue(ht.h(2, tSize) == 8);
        assertTrue(ht.h(key1, tSize) == 8);
    }
}
