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
        // get code coverage
        airObject = new Bird("thesaurus",
                new Box(new int[] {20, 20, 20, 50, 50, 50}), "Dinosaur", 1);
        tree.insert(airObject);
        tree.delete(airObject);
    }


    /** Test the delete method. */
    public void testDelete() {
        String expectedOutput = "\nTesting BinTree delete\n-----------\n";
        System.out.print(expectedOutput);

        // let's add an airplane object
        AirObject air1 = new Airplane("Air1",
                new Box(new int[] {0, 0, 0, 512, 512, 512}), "USAir", 717, 4);
        tree.insert(air1);

        // adding a balloon
        AirObject balloon = new Balloon("B1",
                new Box(new int[] {512, 0, 0, 512, 512, 512}), "hot_air", 15);
        tree.insert(balloon);

        // adding another airplane
        AirObject air2 = new Airplane("Air2",
                new Box(new int[] {0, 512, 0, 512, 512, 512}), "Delta", 17, 2);
        tree.insert(air2);

        // adding a bird
        AirObject bird = new Bird("pterodactyl",
                new Box(new int[] {0, 0, 512, 512, 512, 512}), "Dinosaur", 1);
        tree.insert(bird);

        // adding a rocket
        AirObject rocket = new Rocket("Enterprise",
                new Box(new int[] {256, 256, 256, 512, 512, 512}), 5000, 99.29);
        tree.insert(rocket);

        // lets delete objects
        tree.delete(rocket);
        tree.dumpTree();
        expectedOutput += "I\n" + "Leaf with 3 objects:\n"
                + "  (Airplane Air1 0 0 0 512 512 512 USAir 717 4)\n"
                + "  (Airplane Air2 0 512 0 512 512 512 Delta 17 2)\n"
                + "  (Bird pterodactyl 0 0 512 512 512 512 Dinosaur 1)\n"
                + "  Leaf with 1 objects:\n"
                + "  (Balloon B1 512 0 0 512 512 512 hot_air 15)\n";

        assertFuzzyEquals(systemOut().getHistory(), expectedOutput);

        // get code coverage
        tree.delete(air1);
        tree.delete(balloon);
        tree.delete(air2);
        tree.delete(bird);

        tree.insert(rocket);

        // let's add an airplane object
        air1 = new Airplane("Air1", new Box(new int[] {0, 0, 0, 256, 256, 256}),
                "USAir", 717, 4);
        tree.insert(air1);

        // adding another airplane
        air2 = new Airplane("Air2",
                new Box(new int[] {0, 1024 - 256, 0, 256, 256, 256}), "Delta",
                17, 2);
        tree.insert(air2);

        // adding a bird
        bird = new Bird("pterodactyl",
                new Box(new int[] {0, 0, 1024 - 256, 256, 256, 256}),
                "Dinosaur", 1);
        tree.insert(bird);

        // adding a balloon
        balloon = new Balloon("B1",
                new Box(new int[] {1024 - 256, 0, 0, 256, 256, 256}), "hot_air",
                15);
        tree.insert(balloon);

        tree.delete(rocket);
        tree.delete(air1);
        tree.delete(balloon);
        tree.delete(air2);
        tree.delete(bird);


        // System.out.println("Getting code coverage for deletion..");
        tree.dumpTree();
        expectedOutput += "E\n";
        assertFuzzyEquals(systemOut().getHistory(), expectedOutput);
    }


    /** Test printCollisions method. */
    public void testPrintCollisions() {
        String expectedOutput =
                "\nTesting BinTree printCollisions\n---------------\n";
        System.out.print(expectedOutput);

        // let's add an airplane object
        AirObject air1 = new Airplane("Air1",
                new Box(new int[] {0, 0, 0, 512, 512, 512}), "USAir", 717, 4);
        tree.insert(air1);

        // adding a balloon
        AirObject balloon = new Balloon("B1",
                new Box(new int[] {512, 0, 0, 512, 512, 512}), "hot_air", 15);
        tree.insert(balloon);

        // adding another airplane
        AirObject air2 = new Airplane("Air2",
                new Box(new int[] {0, 512, 0, 512, 512, 512}), "Delta", 17, 2);
        tree.insert(air2);

        // adding a bird
        AirObject bird = new Bird("pterodactyl",
                new Box(new int[] {0, 0, 512, 512, 512, 512}), "Dinosaur", 1);
        tree.insert(bird);

        // adding a rocket
        AirObject rocket = new Rocket("Enterprise",
                new Box(new int[] {256, 256, 256, 512, 512, 512}), 5000, 99.29);
        tree.insert(rocket);

        tree.printCollisions();

        // every other AirObject added so far only intersects with the rocket
        expectedOutput += "(Airplane Air1 0 0 0 512 512 512 USAir 717 4) and "
                + "(Rocket Enterprise 256 256 256 512 512 512 5000 99.29)\n"
                + "(Rocket Enterprise 256 256 256 512 512 512 5000 99.29) and "
                + "(Bird pterodactyl 0 0 512 512 512 512 Dinosaur 1)\n"
                + "(Airplane Air2 0 512 0 512 512 512 Delta 17 2) and "
                + "(Rocket Enterprise 256 256 256 512 512 512 5000 99.29)\n"
                + "(Balloon B1 512 0 0 512 512 512 hot_air 15) and "
                + "(Rocket Enterprise 256 256 256 512 512 512 5000 99.29)\n";

        // let's remove the rocket
        tree.delete(rocket);

        // there shouldn't exist any collisions
        tree.printCollisions();

        // let's delete everything else but the bird
        tree.delete(air1);
        tree.delete(air2);
        tree.delete(balloon);

        // no output expected
        tree.printCollisions();

        // adding more birds that collide with pterodactyl
        bird = new Bird("prolly", new Box(new int[] {0, 0, 512, 512, 512, 512}),
                "Parrot", 1);
        tree.insert(bird);

        bird = new Bird("molly", new Box(new int[] {0, 0, 512, 512, 512, 512}),
                "Pigeon", 1);
        tree.insert(bird);

        bird = new Bird("tota", new Box(new int[] {0, 0, 512, 512, 512, 512}),
                "Vulture", 1);
        tree.insert(bird);

        tree.printCollisions();

        // everyone collides with everyone else
        expectedOutput += "(Bird molly 0 0 512 512 512 512 Pigeon 1) and "
                + "(Bird prolly 0 0 512 512 512 512 Parrot 1)\n"
                + "(Bird molly 0 0 512 512 512 512 Pigeon 1) and "
                + "(Bird pterodactyl 0 0 512 512 512 512 Dinosaur 1)\n"
                + "(Bird molly 0 0 512 512 512 512 Pigeon 1) and "
                + "(Bird tota 0 0 512 512 512 512 Vulture 1)\n"
                + "(Bird prolly 0 0 512 512 512 512 Parrot 1) and "
                + "(Bird pterodactyl 0 0 512 512 512 512 Dinosaur 1)\n"
                + "(Bird prolly 0 0 512 512 512 512 Parrot 1) and "
                + "(Bird tota 0 0 512 512 512 512 Vulture 1)\n"
                + "(Bird pterodactyl 0 0 512 512 512 512 Dinosaur 1) and "
                + "(Bird tota 0 0 512 512 512 512 Vulture 1)\n";

        assertFuzzyEquals(systemOut().getHistory(), expectedOutput);
    }


    /** Test the intersections method. */
    public void testIntersections() {
        String expectedOutput =
                "\nTesting BinTree intersections\n---------------\n";
        System.out.print(expectedOutput);

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
        tree.intersections(new Box(new int[] {0, 0, 0, 100, 100, 100}));

        expectedOutput += "Airplane Air1 0 10 1 20 2 30 USAir 717 4\n"
                + "Balloon B1 10 11 11 21 12 31 hot_air 15\n";

        assertFuzzyEquals(systemOut().getHistory(), expectedOutput);
    }

}
