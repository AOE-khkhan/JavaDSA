import student.TestCase;

/**
 * Test the BinTree class.
 * 
 * @author  Bimal Gaudel
 * 
 * @version Apr 24, 2020
 */
public class BinTreeTest extends TestCase {


    /** A BinTree object used for testing. */
    private BinTree tree;


    /** Sets up the tests that follow. */
    public void setUp() {
        /** The dimension of the world in one direction for the tree. */
        int worldSize = 1024;
        tree = new BinTree(worldSize);
    }


    /** Test the insert method. */
    public void testSimpleInsert() {
        String expectedOutput =
                "\nTesting BinTree simple insert\n-----------\n";
        System.out.print(expectedOutput);

        tree.dumpTree();
        // the single empty node got printed
        expectedOutput += "E\n";

        // let's add an airplane object
        AirObject airObject = new Airplane("Air1",
                new Box(new int[] {0, 10, 1, 20, 2, 30}), "USAir", 717, 4);
        tree.insert(airObject);

        // adding a balloon
        airObject = new Balloon("B1",
                new Box(new int[] {10, 11, 11, 21, 12, 31}), "hot_air", 15);
        tree.insert(airObject);

        // adding another airplane
        airObject = new Airplane("Air2",
                new Box(new int[] {100, 1010, 101, 924, 2, 900}), "Delta", 17,
                2);
        tree.insert(airObject);

        // adding a bird
        airObject = new Bird("pterodactyl",
                new Box(new int[] {0, 100, 20, 10, 50, 50}), "Dinosaur", 1);
        tree.insert(airObject);

        //
        tree.dumpTree();
        //@formatter:off
        expectedOutput += "I\n"
                        + "  I\n"
                        + "    Leaf with 3 objects:\n"
                        + "    (Airplane Air1 0 10 1 20 2 30 USAir 717 4)\n"
                        + "    (Balloon B1 10 11 11 21 12 31 hot_air 15)\n"
                        + "    (Bird pterodactyl "
                                + "0 100 20 10 50 50 Dinosaur 1)\n"
                        + "    Leaf with 1 objects:\n"
                        + "    (Airplane Air2 "
                                + "100 1010 101 924 2 900 Delta 17 2)\n"
                        + "  Leaf with 1 objects:\n"
                        + "  (Airplane Air2 "
                                + "100 1010 101 924 2 900 Delta 17 2)\n";
        //@formatter:on
        assertFuzzyEquals(systemOut().getHistory(), expectedOutput);
    }


    /**
     * Test when insertion doesn't trigger splitting because objects have common
     * intersection.
     */
    public void testOverlappedInsert() {
        String expectedOutput =
                "\nTesting BinTree overlapped insert\n--------------\n";
        System.out.print(expectedOutput);
        AirObject airObject = new Bird("tota",
                new Box(new int[] {10, 10, 10, 5, 5, 5}), "Parrot", 1);
        tree.insert(airObject);

        airObject = new Bird("maina", new Box(new int[] {10, 10, 10, 5, 5, 5}),
                "Parrot", 1);
        tree.insert(airObject);

        airObject = new Bird("saina", new Box(new int[] {10, 10, 10, 5, 5, 5}),
                "Parrot", 1);
        tree.insert(airObject);

        airObject = new Bird("prolly", new Box(new int[] {10, 10, 10, 5, 5, 5}),
                "Parrot", 1);
        tree.insert(airObject);

        tree.dumpTree();
        //@formatter:off
        expectedOutput += "Leaf with 4 objects:\n"
                        + "(Bird maina 10 10 10 5 5 5 Parrot 1)\n"
                        + "(Bird prolly 10 10 10 5 5 5 Parrot 1)\n"
                        + "(Bird saina 10 10 10 5 5 5 Parrot 1)\n"
                        + "(Bird tota 10 10 10 5 5 5 Parrot 1)\n";
        //@formatter:on
        assertFuzzyEquals(systemOut().getHistory(), expectedOutput);
    }


    /** Test the delete method. */
    public void testDelete() {
        String expectedOutput = "\nTesting BinTree delete\n-----------\n";
        System.out.print(expectedOutput);

        // let's add an airplane object
        AirObject air1 = new Airplane("Air1",
                new Box(new int[] {0, 10, 1, 20, 2, 30}), "USAir", 717, 4);
        tree.insert(air1);

        // adding a balloon
        AirObject b1 = new Balloon("B1",
                new Box(new int[] {10, 11, 11, 21, 12, 31}), "hot_air", 15);
        tree.insert(b1);

        // adding another airplane
        AirObject air2 = new Airplane("Air2",
                new Box(new int[] {100, 1010, 101, 924, 2, 900}), "Delta", 17,
                2);
        tree.insert(air2);

        // adding a bird
        AirObject pterodactyl = new Bird("pterodactyl",
                new Box(new int[] {0, 100, 20, 10, 50, 50}), "Dinosaur", 1);
        tree.insert(pterodactyl);

        //
        tree.dumpTree();
        //@formatter:off
        expectedOutput += "I\n"
                        + "  I\n"
                        + "    Leaf with 3 objects:\n"
                        + "    (Airplane Air1 0 10 1 20 2 30 USAir 717 4)\n"
                        + "    (Balloon B1 10 11 11 21 12 31 hot_air 15)\n"
                        + "    (Bird pterodactyl "
                                + "0 100 20 10 50 50 Dinosaur 1)\n"
                        + "    Leaf with 1 objects:\n"
                        + "    (Airplane Air2 "
                                + "100 1010 101 924 2 900 Delta 17 2)\n"
                        + "  Leaf with 1 objects:\n"
                        + "  (Airplane Air2 "
                                + "100 1010 101 924 2 900 Delta 17 2)\n";
        //@formatter:on

        // lets delete objects
        tree.delete(air1);
        tree.delete(air2);
        tree.delete(b1);
        tree.delete(pterodactyl);

        tree.dumpTree();
        expectedOutput += "E\n";
        assertFuzzyEquals(systemOut().getHistory(), expectedOutput);
    }

}
