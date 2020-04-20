/**
 * Air traffic control general object type interface.
 * All tracked objects must have a bounding prism and a name.
 * 
 * @author  Bimal Gaudel
 * 
 * @version Apr 10, 2020
 * 
 */
public class AirObject implements Comparable<AirObject> {
    /** Name for this AirObject */
    private String name;

    /** Prism that confines this AirObject. */
    private Prism prism;

    /**
     * Constructor for base AirObject
     * 
     * @param name  The object's name.
     * @param prism Prism for the object.
     *
     */
    public AirObject(String name, Prism prism) {
        this.name = name;
        this.prism = prism;
    }

    /**
     * Getter for x origin
     * 
     * @return x origin
     */
    public int getXorig() {
        return prism.getOrigX();
    }


    /**
     * Getter for x width
     * 
     * @return x width
     */
    public int getXwidth() {
        return prism.getWidthX();
    }


    /**
     * Getter for y origin
     * 
     * @return y origin
     */
    public int getYorig() {
        return prism.getOrigY();
    }


    /**
     * Getter for y width
     * 
     * @return y width
     */
    public int getYwidth() {
        return prism.getWidthY();
    }


    /**
     * Getter for z origin
     * 
     * @return z origin
     */
    public int getZorig() {
        return prism.getOrigZ();
    }


    /**
     * Getter for z width
     * 
     * @return z width
     */

    public int getZwidth() {
        return prism.getWidthZ();
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
     * Getter for the prism object.
     * 
     * @return Prism object of air object.
     */
    public Prism getPrism() {
        return prism;
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
     * Get the string representation of the prism.
     * 
     * @return Specs of prism as string.
     */
    protected String prismString() {
        return prism.toString();
    }
} // class AirObject
