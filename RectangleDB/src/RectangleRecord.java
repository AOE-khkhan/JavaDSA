/**
 * RectangleRecord is the data structure that is stored by the QuadTree.
 *
 * A RectangleRecord is made of a name for the record and a pointer to the
 * Rectangle object. @see Rectangle.java
 * 
 * @author  Bimal Gaudel
 * 
 * @version Feb 2020.
 */

public class RectangleRecord {
    /** Name of the record. */
    private String name;

    /** Reference to the Rectangle object. */
    private Rectangle rectangle;

    /**
     * Construct RectangleRecord.
     * 
     * @param name Name of the record.
     */
    RectangleRecord(String name) {
        this.name = name;
    }

    /**
     * Construct RectangleRecord.
     * 
     * @param name      Name of the record.
     * @param rectangle A Rectangle object.
     */
    RectangleRecord(String name, Rectangle rectangle) {
        this.name = name;
        this.rectangle = rectangle;
    }

    /**
     * Construct RectangleRecord.
     * 
     * @param name   Name of the record.
     * @param coordX X coordinate of the origin of the rectangle.
     * @param coordY Y coordinate of the origin of the rectangle.
     * @param width  Width of the of the rectangle.
     * @param height Height of the rectangle.
     */
    RectangleRecord(String name, int coordX, int coordY, int width,
            int height) {
        this(name, new Rectangle(coordX, coordY, width, height));
    }

    /**
     * Getter of the name data.
     * 
     * @return Name of the record.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter of the Rectangle object reference.
     * 
     * @return Reference to the Rectangle object.
     */
    public Rectangle getRectangle() {
        return rectangle;
    }

    /**
     * Setter of the Rectangle object reference.
     * 
     * @param rectangle Reference to a Rectangle object.
     */
    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    /**
     * Implement equals method.
     * 
     * @return True if two records are exactly the same.
     */
    @Override
    public boolean equals(Object obj) {
        // compared to itself
        if (obj == this) {
            return true;
        }

        // compared to invalid object
        if (!(obj instanceof RectangleRecord)) {
            return false;
        }

        // cast the object to this type
        RectangleRecord other = (RectangleRecord) obj;

        return (this.getName().equals(other.getName()))
                && (this.getRectangle().equals(other.getRectangle()));
    }


    /**
     * Get the String representation of a RectangleRecord object.
     * 
     * @return A string made of record name and specs separated by commas.
     */
    @Override
    public String toString() {
        return String.format("(%s, %d, %d, %d, %d)", getName(),
                rectangle.getX(), rectangle.getY(), rectangle.getWidth(),
                rectangle.getHeight());
    }
}
