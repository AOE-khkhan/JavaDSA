/**
 * World object is the platform for AirControl project actions.
 * 
 * @author  Bimal Gaudel
 * 
 * @version Apr 11, 2020
 */
public class World {
    /** Print the skip list. */
    public void printSkiplist() {}

    /** Print the bintree. */
    public void printBintree() {}

    /** Print air objects in the database that collide. */
    public void printCollisions() {}

    /**
     * Print the records from the skip list from start to end. Printing is done
     * in alphabetical order. It is an error if end comes before start.
     * 
     * @param start AirObject with names greater than or equal to start will be
     *              printed.
     * @param end   AirObject with names smaller than or equal to start will be
     *              printed.
     */
    public void printRange(String start, String end) {}

    /**
     * Print the air objects that intersect the given prism.
     * 
     * @param prism A prism object.
     */
    public void printIntersection(Prism prism) {}

    /**
     * Print an object if it exists in the database.
     * 
     * @param name Name of the object to be printed.
     */
    public void printObject(String name) {}

    /**
     * Delete an object if it exists in the database.
     * 
     * @param name Name of the object to be deleted.
     */
    public void deleteObject(String name) {}

    /**
     * Add an AirObject to the database. The object won't be added if there's
     * already an air object in the database with the same name.
     * 
     * @param airObject An AirObject to be added to the database.
     */
    public void addObject(AirObject airObject) {
        System.out.println("Added: " + airObject);
    }
}
