/**
 * Box object in three dimension.
 * 
 * @author  Bimal Gaudel
 * 
 * @version Apr 10, 2020
 */
public class Box {
    /**
     * x, y and z co-ordinates of the origin.
     * orig[0] = x co-ord
     * orig[1] = y co-ord
     * orig[2] = z co-ord
     */
    private int[] orig;


    /**
     * x, y and z widths of the box.
     * widths[0] = x width
     * widths[1] = y width
     * widths[2] = z width
     */
    private int[] widths;


    /**
     * Construct a Box object out of an array of ints.
     * 
     * @param spec
     *             [0] x co-ordinate of the origin.
     *             [1] y co-ordinate of the origin.
     *             [2] z co-ordinate of the origin.
     *             [3] X width of the box.
     *             [4] Y width of the box.
     *             [5] Z width of the box.
     */
    public Box(int[] spec) {
        //@formatter:off
        orig = new int[] {spec[DIM_X],
                          spec[DIM_Y],
                          spec[DIM_Z]};

        int offset = NUM_DIMS;
        widths = new int[] {spec[offset + DIM_X],
                            spec[offset + DIM_Y],
                            spec[offset + DIM_Z]};
        //@formatter:on
    }

    /**
     * Construct Box object out of origin specs and the widths specs.
     * 
     * @param orig
     *               [0] x co-ordinate of the origin
     *               [1] y co-ordinate of the origin
     *               [2] z co-ordinate of the origin
     * @param widths
     *               [0] x width of the box
     *               [1] y width of the box
     *               [2] z width of the box
     */
    protected Box(int[] orig, int[] widths) {
        this.orig = orig;
        this.widths = widths;
    }


    /**
     * Get the origin co-ordinates of the box.
     * 
     * @return Array of ints for x, y and z co-ordinates respectively.
     */
    public int[] getOrig() {
        return orig;
    }


    /**
     * Get the box widths.
     * 
     * @return Array of ints for x, y and z widths respectively.
     */
    public int[] getWidths() {
        return widths;
    }


    /**
     * Get a string representation of box object.
     * 
     * @return Box specs in a string.
     */
    @Override
    public String toString() {
        //@formatter:off
        return String.format("%d %d %d %d %d %d",
                getOrig()[DIM_X], getOrig()[DIM_Y], getOrig()[DIM_Z],
                getWidths()[DIM_X], getWidths()[DIM_Y], getWidths()[DIM_Z]);
        //@formatter:on
    }


    /**
     * Check if this box contains a point.
     * 
     * @param  point A 3d point: array of integers for x, y and z co-ordinates
     *               resrespectively.
     * 
     * @return       True if the box contains the point.
     */
    public boolean hasPoint(int[] point) {
        //@formatter:off
        return (point[DIM_X] >= getOrig()[DIM_X])
                && (point[DIM_X] < getOrig()[DIM_X] + getWidths()[DIM_X]) &&
                // x co-ordinate is contained
                (point[DIM_Y] >= getOrig()[DIM_Y])
                && (point[DIM_Y] < getOrig()[DIM_Y] + getWidths()[DIM_Y]) &&
                // y co-ordinate is contained
                (point[DIM_Z] >= getOrig()[DIM_Z])
                && (point[DIM_Z] < getOrig()[DIM_Z] + getWidths()[DIM_Z]);
                // z co-ordinate is contained
        //@formatter:on
    }


    /**
     * Check if a Box intersects with this box.
     * 
     * @param  box Box object to be checked.
     * 
     * @return     True if box intersects this object.
     */
    public boolean intersects(Box box) {
        //@formatter:off
        return !((getOrig()[DIM_X] >= box.getOrig()[DIM_X]
                                        + box.getWidths()[DIM_X]) ||
                 (getOrig()[DIM_Y] >= box.getOrig()[DIM_Y]
                                        + box.getWidths()[DIM_Y]) ||
                 (getOrig()[DIM_Z] >= box.getOrig()[DIM_Z]
                                        + box.getWidths()[DIM_Z]) ||
                 (box.getOrig()[DIM_X] >= getOrig()[DIM_X]
                                              + getWidths()[DIM_X]) ||
                 (box.getOrig()[DIM_Y] >= getOrig()[DIM_Y]
                                              + getWidths()[DIM_Y]) ||
                 (box.getOrig()[DIM_Z] >= getOrig()[DIM_Z]
                                              + getWidths()[DIM_Z]));
        //@formatter:on
    }


    /**
     * Get the box made of intersection of this box with another. @note The
     * other box must intersect with this one.
     * 
     * @param  box Another box that this object intersects.
     * 
     * @return     A box made out of the intersection of this and the other.
     */
    public Box getIntersection(Box box) {
        // The origin of the intersection starts as later as possible.
        // So, the x co-ordinate of the intersection is the greater
        // of the x co-ordinates of this object and the box. And, so on.
        int[] isecOrig = new int[NUM_DIMS];
        for (int ii = 0; ii < NUM_DIMS; ++ii) {
            isecOrig[ii] = Math.max(getOrig()[ii], box.getOrig()[ii]);
        }

        // The widths of the intersection end as soon as possible.
        // So, the width starts at the intersection's origin and
        // ends whichever of the corresponding dimension's widths
        // of this object and the box end.
        //
        int[] isecWidths = new int[NUM_DIMS];
        for (int ii = 0; ii < NUM_DIMS; ++ii) {

            isecWidths[ii] = Math.min((getOrig()[ii] + getWidths()[ii]),
                    (box.getOrig()[ii] + box.getWidths()[ii]));

            isecWidths[ii] -= isecOrig[ii];
        }
        return new Box(isecOrig, isecWidths);
    }


    /**
     * Position in the array of size three for origin co-ordinate and width
     * along x-axis
     */
    public static final int DIM_X = 0;


    /**
     * Position in the array of size three for origin co-ordinate and width
     * along y-axis
     */
    public static final int DIM_Y = 1;


    /**
     * Position in the array of size three for origin co-ordinate and width
     * along z-axis
     */
    public static final int DIM_Z = 2;


    /** Total number of dimensions. */
    public static final int NUM_DIMS = 3;
}
