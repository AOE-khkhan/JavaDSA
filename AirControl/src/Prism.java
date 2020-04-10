/**
 * Prism object in three dimension.
 * 
 * @author  Bimal Gaudel
 * 
 * @version Apr 10, 2020
 */
public class Prism {
    /** X co-ordinate of the origin of the prism. */
    private int origX;

    /** Y co-ordinate of the origin of the prism. */
    private int origY;

    /** Z co-ordinate of the origin of the prism. */
    private int origZ;

    /** X width of the prism. */
    private int widthX;

    /** Y width of the prism. */
    private int widthY;

    /** Z width of the prism. */
    private int widthZ;


    /**
     * Construct a Prism object out of an array of ints.
     * 
     * @param spec
     *             [0] x co-ordinate of the origin.
     *             [1] y co-ordinate of the origin.
     *             [2] z co-ordinate of the origin.
     *             [3] X width of the prism.
     *             [4] Y width of the prism.
     *             [5] Z width of the prism.
     */
    public Prism(int[] spec) {
        origX = spec[0];
        origY = spec[1];
        origZ = spec[2];
        //
        widthX = spec[3];
        widthY = spec[4];
        widthZ = spec[5];
    }


    /**
     * Get the x co-ordinate of the origin.
     * 
     * @return X co-ordinate of the origin.
     */
    public int getOrigX() {
        return origX;
    }


    /**
     * Get the y co-ordinate of the origin.
     * 
     * @return Y co-ordinate of the origin.
     */
    public int getOrigY() {
        return origY;
    }


    /**
     * Get the z co-ordinate of the origin.
     * 
     * @return Z co-ordinate of the origin.
     */
    public int getOrigZ() {
        return origZ;
    }


    /**
     * Get the x width of the prism.
     * 
     * @return X width of the prism.
     */
    public int getWidthX() {
        return widthX;
    }


    /**
     * Get the y width of the prism.
     * 
     * @return Y width of the prism.
     */
    public int getWidthY() {
        return widthY;
    }


    /**
     * Get the z width of the prism.
     * 
     * @return Z width of the prism.
     */
    public int getWidthZ() {
        return widthZ;
    }

    /**
     * Get a string representation of prism object.
     * 
     * @return Prism specs in a string.
     */
    @Override
    public String toString() {
        return String.format("%d %d %d %d %d %d", getOrigX(), getOrigY(),
                getOrigZ(), getWidthX(), getWidthY(), getWidthZ());
    }
}
