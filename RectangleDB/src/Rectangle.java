/**
 * Rectangle objects managed by the RectangleDB project.
 *
 * A rectangle object the Integer specs that are four
 * numbers: x and y coordinates of the origin point of the rectangle, the width
 * and the height of the rectangle in that order.
 * 
 * @author  Bimal Gaudel
 * 
 * @version Feb 2020
 */
public class Rectangle implements Comparable<Rectangle> {
    /* The x coordinate of the origin. */
    private int coordX;

    /* The y coordinate of the origin. */
    private int coordY;

    /* The horizontal length of the rectangle. */
    private int width;

    /* The vertical length of the rectangle. */
    private int height;

    /**
     * Construct a rectangle object.
     *
     * @param x      The x coordinate of the origin.
     * @param y      The y coordinate of the origin.
     * @param width  The horizontal length of the rectangle.
     * @param height The vertical length of the rectangle.
     */
    Rectangle(int x, int y, int width, int height) {
        this.coordX = x;
        this.coordY = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Getter of the width data.
     * 
     * @return Width of the rectangle.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Getter of the height data.
     * 
     * @return Height of the rectangle.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Getter of the x coordinate of the origin.
     * 
     * @return X coordinate of the origin.
     */
    public int getX() {
        return coordX;
    }

    /**
     * Getter of the y coordinate of the origin.
     * 
     * @return Y coordinate of the origin.
     */
    public int getY() {
        return coordY;
    }

    /**
     * Check if a point falls into this rectangle.
     * 
     * @param  x X coordinate of the point.
     * @param  y Y coordinate of the point.
     * 
     * @return   True if the point lies on this canvas.
     */
    public boolean hasPoint(int x, int y) {
        // x coordinate is contained
        return (x >= getX()) && (x < getX() + getWidth())
        // y coordinate is contained
                && (y >= getY()) && (y < getY() + getHeight());
    }

    /**
     * Test if a rectangle interesects with this rectangle.
     * 
     * @param  rectangle Rectangle object.
     * 
     * @return           True if rectangle intersects this canvas.
     */
    public boolean intersects(Rectangle rectangle) {
        return !(getX() >= rectangle.getX() + rectangle.getWidth()
                || rectangle.getX() >= getX() + getWidth()
                || getY() >= rectangle.getY() + rectangle.getHeight()
                || rectangle.getY() >= getY() + getHeight());
    }

    /**
     * Get the rectangle made of the intersection of this rectangle with
     * another.
     *
     * @param  rectangle A rectangle that this rectangle intersects with.
     * 
     * @return           The rectangle made of intersection between this and the
     *                   other rectangle.
     * 
     * @note             We assume that @c rectangle intersects with this
     *                   rectangle.
     */
    public Rectangle getIntersection(Rectangle rectangle) {
        // The origin of the intersection starts as later on the canvas as
        // possible. The greater of the two x coordinates and the greater of the
        // two y coordinates make up the origin of the intersection rectangle.
        int isecX = Math.max(getX(), rectangle.getX());
        int isecY = Math.max(getY(), rectangle.getY());
        //
        // the size of the intersection is as small as possible
        //
        // the width starts from the intersection's origin and
        // ends wherever the shorter of the widths of the two rectangles ends
        int isecWidth = Math.min(getX() + getWidth(),
                rectangle.getX() + rectangle.getWidth()) - isecX;
        //
        // the height starts from the intersection's origin and
        // ends wherever the shorter of the heights of the two rectangles ends
        int isecHeight = Math.min(getY() + getHeight(),
                rectangle.getY() + rectangle.getHeight()) - isecY;

        return new Rectangle(isecX, isecY, isecWidth, isecHeight);
    }

    /**
     * Override equals method to compare to rectangles.
     * 
     * @return True if two rectangles have the same specs.
     */
    @Override
    public boolean equals(Object obj) {
        // compared to itself
        if (obj == this) {
            return true;
        }

        // compared to invalid object
        if (!(obj instanceof Rectangle)) {
            return false;
        }

        // cast the object to this type
        Rectangle other = (Rectangle) obj;

        // compare the specs
        return (getX() == other.getX()) && (getY() == other.getY())
                && (getWidth() == other.getWidth())
                && (getHeight() == other.getHeight());
    }

    /**
     * Implement the compareTo method of the Comparable interface.
     * 
     * @param  that A RectangleRecord object being compared to this object.
     * 
     * @return      0 if the origins of the Rectangle data match, -1 if the
     *              origin of this object comes before that of the param
     *              object's, 1 otherwise.
     */
    @Override
    public int compareTo(Rectangle that) {
        if (this.equals(that)) {
            return 0;
        }

        // case when this rectangle originates earlier on a rectangular canvas
        if (this.getX() < that.getX()) {
            return -1;
        }

        // case when this rectangle originates later on a rectangular canvas
        if (this.getX() > that.getX()) {
            return 1;
        }

        // at this point the x coordinates of both rectangles are the same
        // decide by comparing the y coordinates
        if (this.getY() < that.getY()) {
            return -1;
        }

        // at this point this must be greater than that
        return 1;
    }
}
