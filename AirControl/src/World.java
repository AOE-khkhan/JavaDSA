/**
 * World object is the platform for AirControl project actions.
 * 
 * @author  Bimal Gaudel
 * 
 * @version Apr 11, 2020
 */
public class World {
    /**
     * Length of the world in one of the three dimensions that are all equal.
     */
    private static final int SIZE_ONE_DIM = 1024;

    /** SkipList object used for the AirControl project. */
    private SkipList<String> skipList;

    /** Construct World. */
    public World() {
        skipList = new SkipList<String>();
    }

    /** Print the skip list. */
    public void printSkiplist() {
        System.out.println(skipList);
    }

    /** Print the bintree. */
    public void printBintree() {
        System.out.println("printBintree will be implemented later..");
    }

    /** Print air objects in the database that collide. */
    public void printCollisions() {
        System.out.println("printCollisions will be implemented later..");
    }

    /**
     * Print the records from the skip list from start to end. Printing is done
     * in alphabetical order. It is an error if end comes before start.
     * 
     * @param start AirObject with names greater than or equal to start will be
     *              printed.
     * @param end   AirObject with names smaller than or equal to start will be
     *              printed.
     */
    public void printRange(String start, String end) {
        if (start.compareTo(end) >= 0) {
            System.out.println("Error in rangeprint parameters: |" + start
                    + "| is not less than |" + end + "|");
            return;
        }
        System.out.println("Found these records in the range |" + start
                + "| to |" + end + "|");
        skipList.rangePrint(start, end);
    }

    /**
     * Print the air objects that intersect the given prism.
     * 
     * @param prism A prism object.
     */
    public void printIntersection(Prism prism) {
        System.out.println("printIntersection will be implemented later..");
    }

    /**
     * Print an object if it exists in the database.
     * 
     * @param name Name of the object to be printed.
     */
    public void printObject(String name) {
        AirObject found = (AirObject) skipList.find(name);
        if (found == null) {
            System.out.println("|" + name + "| does not exist in the database");
        }
        else {
            System.out.println("Found: " + found);
        }
    }

    /**
     * Delete an object if it exists in the database.
     * 
     * @param name Name of the object to be deleted.
     */
    public void deleteObject(String name) {
        AirObject found = (AirObject) skipList.find(name);
        if (found == null) {
            System.out.println("Object |" + name + "| not in the database");
        }
        else {
            skipList.delete(name);
            System.out.println("Deleted |" + name + "| from the database");
        }

    }

    /**
     * Add an AirObject to the database. The object won't be added if there's
     * already an air object in the database with the same name.
     * 
     * @param airObject An AirObject to be added to the database.
     */
    public void addObject(AirObject airObject) {
        Prism prism = airObject.getPrism();
        if (World.prismWithInvalidDimension(prism)) {
            System.out.println(
                    "Bad box (" + prism + "). All widths must be positive.");
            return;
        }
        else if (prismExcludesWorld(prism) || prismExtendsBeyondWorld(prism)) {
            System.out.println("Bad box (" + prism + "). "
                    + "All boxes must be entirely within the world box.");
            return;
        }
        else if (skipList.find(airObject.getName()) != null) {
            System.out.println("Duplicate object names not permitted: |"
                    + airObject.getName() + "|");
            return;
        }

        skipList.insert(airObject.getName(), airObject);
        System.out.println(
                airObject.getName() + " has been added to the database");
    }

    /**
     * Check if a prism has non-positive sizes for x, y, or z lengths.
     * 
     * @param  prism The prism object being checked.
     * 
     * @return       True if x, y or z length is non-positive.
     */
    private static boolean prismWithInvalidDimension(Prism prism) {
        return (prism.getWidthX() <= 0) || (prism.getWidthY() <= 0)
                || (prism.getWidthZ() <= 0);
    }

    /**
     * Check if a prism doesn't overlap with the world at all.
     * 
     * @param  prism The prism object being checked.
     *
     * @return       True if prism excludes the world.
     */
    private static boolean prismExcludesWorld(Prism prism) {

        if ((prism.getOrigX() >= World.SIZE_ONE_DIM)
                || (prism.getOrigY() >= World.SIZE_ONE_DIM)
                || (prism.getOrigZ() >= World.SIZE_ONE_DIM)) {
            return true;
        }

        return (prism.getOrigX() + prism.getWidthX() <= 0)
                || (prism.getOrigY() + prism.getWidthY() <= 0)
                || (prism.getOrigZ() + prism.getWidthZ() <= 0);

    }

    /**
     * Check if a prism extends beyond the world.
     * 
     * @param  prism The prism object to be checked.
     * 
     * @return       True if prism extends beyond the world.
     */
    private static boolean prismExtendsBeyondWorld(Prism prism) {
        if ((prism.getOrigX() < 0) || (prism.getOrigY() < 0)
                || (prism.getOrigZ() < 0)) {
            return true;
        }
        return (prism.getOrigX() + prism.getWidthX() > World.SIZE_ONE_DIM)
                || (prism.getOrigY() + prism.getWidthY() > World.SIZE_ONE_DIM)
                || (prism.getOrigZ() + prism.getWidthZ() > World.SIZE_ONE_DIM);
    }
}
