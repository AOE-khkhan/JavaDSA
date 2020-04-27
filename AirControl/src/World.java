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


    /** BinTree object used for the AirControl project. */
    private BinTree binTree;


    /** Construct World. */
    public World() {
        skipList = new SkipList<String>();
        binTree = new BinTree(SIZE_ONE_DIM);
    }


    /** Print the skip list. */
    public void printSkiplist() {
        System.out.println(skipList);
    }


    /** Print the bintree. */
    public void printBintree() {
        System.out.println("Bintree dump:");
        int nodesPrinted = binTree.dumpTree();
        System.out.println(nodesPrinted + " bintree nodes printed");
    }


    /** Print air objects in the database that collide. */
    public void printCollisions() {
        System.out.println("The following collisions exist in the database:");
        binTree.printCollisions();
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
     * Print the air objects that intersect the given box.
     * 
     * @param box A box object.
     */
    public void printIntersection(Box box) {
        // assert valid box
        AirObject dummy = new Balloon("", box, "", 0);
        if (boxWithInvalidDimension(dummy)) {
            printBadBoxInvalidDims(box);
            return;
        }

        System.out.println("The following objects intersect (" + box + "):");
        int nodesVisited = binTree.printIntersections(box);
        System.out.println(nodesVisited + " nodes were visited in the bintree");
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
            // delete from the skip list
            skipList.delete(name);
            // delete from the bintree
            binTree.delete(found);
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
        if (World.boxWithInvalidDimension(airObject)) {
            printBadBoxInvalidDims(airObject.getBox());
            return;
        }
        else if (boxExcludesWorld(airObject)
                || boxExtendsBeyondWorld(airObject)) {
            printBadBoxOutofWorld(airObject.getBox());
            return;
        }
        else if (skipList.find(airObject.getName()) != null) {
            System.out.println("Duplicate object names not permitted: |"
                    + airObject.getName() + "|");
            return;
        }

        skipList.insert(airObject.getName(), airObject);
        binTree.insert(airObject);
        System.out.println(
                airObject.getName() + " has been added to the database");
    }


    /**
     * Check if an AirObject's box has non-positive sizes for x, y, or z
     * lengths.
     * 
     * @return True if x, y or z length is non-positive.
     */
    private static boolean boxWithInvalidDimension(AirObject airObject) {
        return (airObject.getXwidth() <= 0) || (airObject.getYwidth() <= 0)
                || (airObject.getZwidth() <= 0);
    }


    /**
     * Check if an AirObject doesn't overlap with the world at all.
     * 
     * @return True if the world's box excludes the world.
     */
    private static boolean boxExcludesWorld(AirObject airObject) {

        if ((airObject.getXorig() >= World.SIZE_ONE_DIM)
                || (airObject.getYorig() >= World.SIZE_ONE_DIM)
                || (airObject.getZorig() >= World.SIZE_ONE_DIM)) {
            return true;
        }

        return (airObject.getXorig() + airObject.getXwidth() <= 0)
                || (airObject.getYorig() + airObject.getYwidth() <= 0)
                || (airObject.getZorig() + airObject.getZwidth() <= 0);
    }


    /**
     * Check if a box extends beyond the world.
     * 
     * @param  airObject The air object to be checked.
     * 
     * @return           True if box extends beyond the world.
     */
    private static boolean boxExtendsBeyondWorld(AirObject airObject) {
        if ((airObject.getXorig() < 0) || (airObject.getYorig() < 0)
                || (airObject.getZorig() < 0)) {
            return true;
        }
        return (airObject.getXorig()
                + airObject.getXwidth() > World.SIZE_ONE_DIM)
                || (airObject.getYorig()
                        + airObject.getYwidth() > World.SIZE_ONE_DIM)
                || (airObject.getZorig()
                        + airObject.getZwidth() > World.SIZE_ONE_DIM);
    }


    /**
     * Print message for a bad box whose dimensions extend beyond the world.
     * 
     * @param box A bad box.
     */
    private static void printBadBoxOutofWorld(Box box) {
        System.out.println("Bad box (" + box + "). "
                + "All boxes must be entirely within the world box.");
    }


    /**
     * Print message for a bad box that has non-positive widths.
     * 
     * @param box A bad box.
     */
    private static void printBadBoxInvalidDims(Box box) {
        System.out.println(
                "Bad box (" + box + "). " + "All widths must be positive.");
    }
}
