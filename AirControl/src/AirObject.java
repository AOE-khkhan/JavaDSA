/**
 * Air traffic control general object type interface.
 * All tracked objects must have a bounding box and a name.
 * 
 * @author  Bimal Gaudel
 * 
 * @version Apr 10, 2020
 * 
 */
public class AirObject implements Comparable<AirObject> {
    /** Name for this AirObject */
    private String name;

    /** Box that confines this AirObject. */
    private Box box;

    /**
     * Constructor for base AirObject
     * 
     * @param name  The object's name.
     * @param box Box for the object.
     *
     */
    public AirObject(String name, Box box) {
        this.name = name;
        this.box = box;
    }

    /**
     * Getter for x origin
     * 
     * @return x origin
     */
    public int getXorig() {
        return box.getOrig()[Box.dimX];
    }


    /**
     * Getter for x width
     * 
     * @return x width
     */
    public int getXwidth() {
        return box.getWidths()[Box.dimX];
    }


    /**
     * Getter for y origin
     * 
     * @return y origin
     */
    public int getYorig() {
        return box.getOrig()[Box.dimY];
    }


    /**
     * Getter for y width
     * 
     * @return y width
     */
    public int getYwidth() {
        return box.getWidths()[Box.dimY];
    }


    /**
     * Getter for z origin
     * 
     * @return z origin
     */
    public int getZorig() {
        return box.getOrig()[Box.dimZ];
    }


    /**
     * Getter for z width
     * 
     * @return z width
     */
    public int getZwidth() {
        return box.getWidths()[Box.dimZ];
    }


    /**
     * Getter for name
     * 
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for the box object.
     * 
     * @return Box object of air object.
     */
    public Box getBox() {
        return box;
    }


    /**
     * Compare against a (name) String.
     *
     * @param  it The String to compare to
     * 
     * @return    Standard values for compareTo
     */
    @Override
    public int compareTo(AirObject it) {
        return name.compareTo(it.getName());
    }

    /**
     * Get a string representation of the AirObject. Derived classes should
     * override it.
     * 
     * @return String representation of the air object.
     */
    @Override
    public String toString() {
        throw new java.lang.RuntimeException(
                "toString() method not implemented for this derived class!");
    }


    /**
     * Get the string representation of the box.
     * 
     * @return Specs of box as string.
     */
    protected String boxString() {
        return box.toString();
    }
} // class AirObject
