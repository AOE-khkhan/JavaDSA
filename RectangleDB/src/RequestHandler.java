
import java.util.Scanner;

/**
 * Handle received commands and arguments for RectangleDB program.
 * 
 * @author Bimal Gaudel
 * 
 * @version February 2020
 */
public class RequestHandler {
  /**
   * The World object we are working on. Every action is passed through the
   * World object to various data-structures.
   */
  private World world;

  /**
   * Construct a handling session for a World.
   * 
   * @param world The World object through which the commands will be chanelled.
   */
  RequestHandler(World world) {
    this.world = world;
  }

  /**
   * Insert a rectangle in the database.
   *
   * A rectangle to be inserted is specified by its name, x and y cooordinate of
   * the starting point, and the width and height that are horizontal and
   * vertical lengths of the rectangle respectively.
   *
   * Duplicate rectangles will not be inserted. A duplicate rectangle is an
   * rectangle that has the same name, origin and the size of one of the
   * pre-existing cooordinates. Note that two unique rectangles can have the
   * same name, or the same spatial specifications but not both.
   * 
   * 
   * @param rectName A String name for the rectangle. eg. "Rec_A"
   * @param args Arguments String that will be parsed to get the starting
   *        cooordinates of the rectangle together with the width and the
   *        height. eg. "110 100 50 70" are x, y, width and height respectively.
   *
   */
  public void insert(String rectName, String args) {
    try {
      int[]  specs        = scanSpecs(args);
      String recSpecPrint = specsToString(rectName, specs);

      if (rejectRectangle(specs)) {
        System.out.println("Rectangle rejected: " + recSpecPrint);
        return;
      }
      // TODO
      // if duplicate println("Duplicate rectangle rejected: " + recSpecPrint);
      System.out.println("Rectangle inserted: " + recSpecPrint);
    }
    catch (Exception e) {
      // not a good specification for a rectangle found
      System.out.println("Bad arguments! Use <rect_name> "
                         + " <x> <y> <width> <height>");
    }
  }

  /**
   * Remove a rectangle from the database.
   * 
   * @param rectName A String name for the rectangle. eg. "Rec_A"
   * @param args Arguments String that will be parsed to get the starting
   *        cooordinates of the rectangle together with the width and the
   *        height. eg. "110 100 50 70" are x, y, width and height respectively.
   */
  public void remove(String rectName, String args) {
    try {
      int[]  specs        = scanSpecs(args);
      String recSpecPrint = specsToString(rectName, specs);

      if (rejectRectangle(specs)) {
        System.out.println("Rectangle rejected: " + recSpecPrint);
        return;
      }
      // TODO
      // if rectangle not found
      // println("Rectangle not in database: " + recSpecPrint)
      System.out.println("Rectangle removed: " + recSpecPrint);
    }
    catch (Exception e) {
      // not a good specification for a rectangle found
      System.out.println("Bad arguments! Use <rect_name> "
                         + " <x> <y> <width> <height>");
    }
  }

  /** Print out the database. */
  public void dump() {
    System.out.println("QuadTree dump:");
    // TODO
  }

  /**
   * Report all rectangles currently in the database that intersect the query
   * rectangle specified by the parameter passed.
   * 
   * @param args Arguments String that will be parsed to get the starting
   *        cooordinates of the rectangle together with the width and the
   *        height. eg. "110 100 50 70" are x, y, width and height respectively.
   */
  public void regionsearch(String args) {
    int[] specs = RequestHandler.scanSpecs(args);
    // rectangles with illegal size specs should be rejected
    String recSpecPrint = specsToString(specs);
    if ((specs[WIDTH] < 0) || (specs[HEIGHT] < 0)) {
      System.out.println("Rectangle rejected: " + recSpecPrint);
      return;
    }
    System.out.println(String.format("Rectangles intersecting rectangle %s:",
                                     recSpecPrint));
    // TODO
  }

  /** Report all pairs of rectangles within the database that intersect. */
  public void intersections() {
    System.out.println("Rectangle intersections in the database:");
    // TODO
  }

  /**
   * Reject a rectangle when attempted for insertion or deletion if its size
   * specification exceeds the size of the maximum allowed rectangle.
   * 
   * @param specs An array of four integers that are x, y cooordinates, the
   *        width and the height of the rectangle respectively.
   * 
   * @return True if the rectangle specification is invalid and should be
   *         rejected.
   */
  private boolean rejectRectangle(int[] specs) {
    // negative or too large values are no good
    for (int i : specs) {
      if ((i < World.MIN_COORDINATE_VALUE)
          || (i > World.MAX_COORDINATE_VALUE)) {
        return true;
      }
    }
    // x + width should not exceed max cooordinate
    if ((specs[X_COORD] + specs[WIDTH] > World.MAX_COORDINATE_VALUE)
        // y + height should not exceed max cooordinate
        || (specs[Y_COORD] + specs[HEIGHT] > World.MAX_COORDINATE_VALUE)) {
      return true;
    }

    return false;
  }

  /**
   * Given a rectangle specification, scans for the x and y cooordinates for the
   * starting point, the width and the height of the rectangle in that order.
   *
   * @param args A String that is a specification for a rectangle. It is assumed
   *        to be four numbers separated by space.
   * 
   * @return An array of ints that are the specifications of the rectangle in
   *         the order of x and y cooordinates, width and the height.
   */
  private static int[] scanSpecs(String args) {

    int[]   specs = new int[SPECS_NUM];
    Scanner sc    = new Scanner(args);
    for (int i = 0; i < SPECS_NUM; ++i) {
      specs[i] = sc.nextInt();
    }

    sc.close();
    return specs;
  }

  /**
   * Format rectangle for printing.
   * 
   * @param specs Array of ints for x coordinate, y coordinate, width and height
   *        of the rectangle respectively.
   * 
   * @return String for printing.
   */
  private static String specsToString(int[] specs) {
    return String.format("(%d, %d, %d, %d)",
                         specs[X_COORD],
                         specs[Y_COORD],
                         specs[WIDTH],
                         specs[HEIGHT]);
  }

  /**
   * Format rectangle for printing.
   * 
   * @param rectName String name of the rectangle.
   * @param specs Array of ints for x coordinate, y coordinate, width and height
   *        of the rectangle respectively.
   * 
   * @return String for printing.
   */
  private static String specsToString(String rectName, int[] specs) {
    return String.format("(%s, %d, %d, %d, %d)",
                         rectName,
                         specs[X_COORD],
                         specs[Y_COORD],
                         specs[WIDTH],
                         specs[HEIGHT]);
  }

  /**
   * Specification position in an array of integers for a rectangle.
   *
   * X_COORD: x coordinate where the rectangle starts
   *
   * Y_COORD: y coordinate where the rectangle starts
   *
   * WIDTH: Width of the rectangle
   *
   * HEIGHT: Height of the rectangle SPECS_NUM: total number of specifications
   *
   * SPECS_NUM: Total number of the specification entries.
   */
  private static final short X_COORD = 0, Y_COORD = 1,
      WIDTH = 2, HEIGHT = 3, SPECS_NUM = 4;
}
