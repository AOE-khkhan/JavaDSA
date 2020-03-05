import student.TestCase;

import java.io.File;
import java.util.Scanner;


/**
 * Test the RectangleDB class. This is intended to get code coverage only.
 * 
 * @author  Bimal Gaudel
 * 
 * @version Feb 2020
 */
public class RectangleDBTest extends TestCase {

    /** Test for the case when no file name is passed as the argument. */
    public void testEmptyParams() {
        String[] params = new String[0];
        RectangleDB.main(params);

        assertFuzzyEquals(systemOut().getHistory(),
                "Bad argument count. Usage: " + "RectangleDB <command-file>");
    }

    /** Test for the case when a file name is passed but it doesn't exist. */
    public void testBadFileName() {
        String[] params = new String[1];
        params[0] = "non-existent-file.txt";
        RectangleDB.main(params);

        String expectedOutput = "";
        try {
            new Scanner(new File(params[0])).close();
        }
        catch (Exception e) {
            expectedOutput = e.getMessage();
        }

        assertFuzzyEquals(systemOut().getHistory(), expectedOutput);
    }

    /** Get code coverage. */
    public void testMain() {

        String[] params = new String[1];
        //
        // test-input.txt is an existing file with some valid commands
        params[0] = "src/test-input.txt";

        RectangleDB.main(params);

        String expectedOutput = "Rectangle inserted: (Rec_A, 0, 0, 100, 100)\n"
                + "Rectangle removed: (Rec_A, 0, 0, 100, 100)\n"
                + "Rectangles intersecting rectangle (-5, -5, 400, 400):\n"
                + "Rectangle intersections in the database:\n"
                + "QuadTree dump:\n"
                + "Node at 0, 0, 1024: Empty\n"
                + "1. quadtree nodes visited\n"
                + "Unrecognized input: |dump1|";

        assertFuzzyEquals(systemOut().getHistory(), expectedOutput);
    }
}
