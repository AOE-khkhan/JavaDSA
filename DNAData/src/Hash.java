/**
 * Stub for hash table class
 *
 * @author CS5040staff
 * @version March 2020
 */

public class Hash {

    /**
     * Create a new Hash object.
     * 
     */
    public Hash(int size) {
        // Nothing here yet
    }


    /**
     * Compute the hash function. This works for long int values, using a
     * mid-square style computation.
     *
     * @param key
     *            The (long) key that we are hashing
     * @param m
     *            The size of the hash table
     * @return The home slot for that string
     */
    // You can make this private in your project.
    // This is public for distributing the hash function in a way
    // that will pass milestone 1 without change.
    public int h(long key, int m) {
        key = ((key >> 32) | (key & 0xffffffff));
        if (key < 256) {
            key = key << 16;
        }
        key = key * key;
        key = (key >> 15) & 0xffffffff;
        key = Math.abs(key);
        return (int)(Math.abs(key) % m);
    }
}
