/**
 * SquareCanvas represents the two-dimensional sheet where rectangle objects are
 * inserted by creating quadrants as required.
 * 
 * @author  Bimal Gaudel
 * 
 * @version Feb 2020
 */
public class SquareCanvas extends Rectangle {
    /**
     * Construct a SquareCanvas object.
     *
     * @param coordX X coordinate of the origin of the canvas.
     * @param coordY Y coordinate of the origin of the canvas.
     * @param size   Width (or height) of the square canvas.
     */
    SquareCanvas(int coordX, int coordY, int size) {
        super(coordX, coordY, size, size);
    }

    /**
     * Get the size of the canvas.
     * 
     * @return The width (or height) of the square canvas.
     */
    public int getSize() {
        return getWidth();
    }

    /**
     * Get a SquareCanvas object made out of a quadrant of this canvas.
     * 
     * @param  index The index of the quadrant to be returned. Valid arguments
     *               0, 1, ..., n : n less than SquareCanvas.NUM_QUADRANTS.
     * 
     * @return       A SquareCanvas corresponding to the index.
     */
    public SquareCanvas getQuadrant(int index) {
        // @formatter:off
        // |------------------------|
        // |           ..           |
        // |    NW     ..     NE    |
        // |           ..           |
        // |........................|
        // |........................|
        // |           ..           |
        // |    SW     ..     SE    |
        // |           ..           |
        // |------------------------|
        // @formatter:on
        int newCanvasSize = getSize() / 2;

        if (index == QUAD_NW) {
            return new SquareCanvas(getX(), getY(), newCanvasSize);
        }
        if (index == QUAD_NE) {
            return new SquareCanvas(getX() + newCanvasSize, getY(),
                    newCanvasSize);
        }
        if (index == QUAD_SW) {
            return new SquareCanvas(getX(), getY() + newCanvasSize,
                    newCanvasSize);
        }
        if (index == QUAD_SE) {
            return new SquareCanvas(getX() + newCanvasSize,
                    getY() + newCanvasSize, newCanvasSize);
        }
        return null;
    }

    /** Array indices for the four quadrants. */

    /** The north-west node index. */
    private static final short QUAD_NW = 0;

    /** The north-east node index. */
    private static final short QUAD_NE = 1;

    /** The south-west node index. */
    private static final short QUAD_SW = 2;

    /** The south-east node index. */
    private static final short QUAD_SE = 3;

    /** The total number of quadrants. */
    public static final short NUM_QUADRANTS = 4;
}
