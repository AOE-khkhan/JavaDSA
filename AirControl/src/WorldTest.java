import student.TestCase;

/**
 * Test World class.
 * 
 * @author  Bimal Gaudel
 * 
 * @version Apr 19, 2020
 */
public class WorldTest extends TestCase {
    /** World object used for testing. */
    private World world;

    /** Sets up the tests that follow. */
    public void setUp() {
        world = new World();
    }

    /** Test printSkipList. */
    public void testPrintSkipList() {
        System.out.println("\nTesting print skip list\n--------------");

        world.addObject(new Bird("crit1", new Box(new int[] {0, 0, 0, 5, 5, 5}),
                "crow", 5));
        world.printSkiplist();
        assertTrue(systemOut().getHistory()
                .endsWith("1 skiplist nodes printed\n"));
    }

    /** Test handling of invalid box specs: part 1. */
    public void testInvalidBox1() {
        String expectedOutput =
                "\nTesting inavlid box handling: part 1\n-------------\n";
        System.out.print(expectedOutput);

        // negative widths
        Box box = new Box(new int[] {0, 0, 0, -5, 5, 5});
        Bird bird = new Bird("crit1", box, "crow", 5);
        world.addObject(bird);
        expectedOutput += "Bad box (" + box.toString() + ") "
                + "All widths must be positive.\n";

        box = new Box(new int[] {0, 0, 0, 5, -5, 5});
        bird = new Bird("crit1", box, "crow", 5);
        world.addObject(bird);
        expectedOutput += "Bad box (" + box.toString() + ") "
                + "All widths must be positive.\n";

        box = new Box(new int[] {0, 0, 0, 5, 5, -5});
        bird = new Bird("crit1", box, "crow", 5);
        world.addObject(bird);
        expectedOutput += "Bad box (" + box.toString() + ") "
                + "All widths must be positive.\n";

        box = new Box(new int[] {0, 0, 0, 5, 5, -5});
        bird = new Bird("crit1", box, "crow", 5);
        world.addObject(bird);
        expectedOutput += "Bad box (" + box.toString() + ") "
                + "All widths must be positive.\n";

        // box excludes world
        box = new Box(new int[] {1024, 0, 0, 5, 5, 5});
        bird = new Bird("crit1", box, "crow", 5);
        world.addObject(bird);
        expectedOutput += "Bad box (" + box.toString() + ") "
                + "All boxes must be entirely within the world box.\n";

        box = new Box(new int[] {0, 1024, 0, 5, 5, 5});
        bird = new Bird("crit1", box, "crow", 5);
        world.addObject(bird);
        expectedOutput += "Bad box (" + box.toString() + ") "
                + "All boxes must be entirely within the world box.\n";

        box = new Box(new int[] {0, 0, 1024, 5, 5, 5});
        bird = new Bird("crit1", box, "crow", 5);
        world.addObject(bird);
        expectedOutput += "Bad box (" + box.toString() + ") "
                + "All boxes must be entirely within the world box.\n";

        assertFuzzyEquals(systemOut().getHistory(), expectedOutput);
    }

    /** Test handling of invalid box specs: part 2. */
    public void testInvalidBox2() {
        String expectedOutput =
                "\nTesting inavlid box handling: part 2\n-------------\n";
        System.out.print(expectedOutput);

        Box box = new Box(new int[] {-50, 0, 0, 5, 5, 5});
        Bird bird = new Bird("crit1", box, "crow", 5);
        world.addObject(bird);
        expectedOutput += "Bad box (" + box.toString() + ") "
                + "All boxes must be entirely within the world box.\n";

        box = new Box(new int[] {0, -50, 0, 5, 5, 5});
        bird = new Bird("crit1", box, "crow", 5);
        world.addObject(bird);
        expectedOutput += "Bad box (" + box.toString() + ") "
                + "All boxes must be entirely within the world box.\n";

        box = new Box(new int[] {0, 0, -50, 5, 5, 5});
        bird = new Bird("crit1", box, "crow", 5);
        world.addObject(bird);
        expectedOutput += "Bad box (" + box.toString() + ") "
                + "All boxes must be entirely within the world box.\n";


        // box extends beyond world
        box = new Box(new int[] {-1, 0, 0, 5, 5, 5});
        bird = new Bird("crit1", box, "crow", 5);
        world.addObject(bird);
        expectedOutput += "Bad box (" + box.toString() + ") "
                + "All boxes must be entirely within the world box.\n";

        box = new Box(new int[] {0, -1, 0, 5, 5, 5});
        bird = new Bird("crit1", box, "crow", 5);
        world.addObject(bird);
        expectedOutput += "Bad box (" + box.toString() + ") "
                + "All boxes must be entirely within the world box.\n";

        box = new Box(new int[] {0, 0, -1, 5, 5, 5});
        bird = new Bird("crit1", box, "crow", 5);
        world.addObject(bird);
        expectedOutput += "Bad box (" + box.toString() + ") "
                + "All boxes must be entirely within the world box.\n";

        box = new Box(new int[] {1020, 1020, 1020, 5, 2, 2});
        bird = new Bird("crit1", box, "crow", 5);
        world.addObject(bird);
        expectedOutput += "Bad box (" + box.toString() + ") "
                + "All boxes must be entirely within the world box.\n";

        box = new Box(new int[] {1020, 1020, 1020, 2, 5, 2});
        bird = new Bird("crit1", box, "crow", 5);
        world.addObject(bird);
        expectedOutput += "Bad box (" + box.toString() + ") "
                + "All boxes must be entirely within the world box.\n";

        box = new Box(new int[] {1020, 1020, 1020, 2, 2, 5});
        bird = new Bird("crit1", box, "crow", 5);
        world.addObject(bird);
        expectedOutput += "Bad box (" + box.toString() + ") "
                + "All boxes must be entirely within the world box.\n";
        assertFuzzyEquals(systemOut().getHistory(), expectedOutput);

    }

    /** Test handling of invalid box specs: part 3. */
    public void testInvalidBox3() {
        String expectedOutput =
                "\nTesting inavlid box handling: part 3\n-------------\n";
        System.out.print(expectedOutput);
        Box box = new Box(new int[] {50, 0, 0, -5, 5, 5});

        world.printIntersection(box);
        expectedOutput += "Bad box (" + box.toString() + ") "
                + "All widths must be positive.\n";

        assertFuzzyEquals(systemOut().getHistory(), expectedOutput);

    }
}
