/**
 * World object is the platform for a RectangleDB actions.
 * 
 * @author  Bimal Gaudel
 * 
 * @version February 2020
 */
public class World {
    /**
     * The maximum value for an X or Y coordinate we can handle. Together with
     * the
     * MIN_COORDINATE_VALUE it determines the spatial size of the rectangles we
     * can work with.
     */
    public static final int MAX_COORDINATE_VALUE = 1023;

    /**
     * The minimum value for an X or Y coordinate we can handle. Together with
     * the
     * MAX_COORDINATE_VALUE it determines the spatial size of the rectangles we
     * can work with.
     */
    public static final int MIN_COORDINATE_VALUE = 0;
}
