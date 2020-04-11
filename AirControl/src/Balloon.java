/**
 * Baloon type AirObject.
 * 
 * @author  Bimal Gaudel
 * 
 * @version Apr 10, 2020
 */
public class Balloon extends AirObject {
    /** The type of the balloon. */
    private String type;

    /** Positive integer for object's rate of ascence. */
    private int ascentRate;

    /**
     * Construct Balloon object.
     * 
     * @param name       Name of the object.
     * @param prism      Prism for the object.
     * @param type       The type of the balloon.
     * @param ascentRate Positive integer for object's rate of ascence.
     */
    public Balloon(String name, Prism prism, String type, int ascentRate) {
        super(name, prism);
        this.type = type;
        this.ascentRate = ascentRate;
    }

    /**
     * Get a string representation of the object.
     * 
     * @return Specs of the object as a string.
     */
    @Override
    public String toString() {
        return String.format("Balloon %s %s %s %d", getName(), prismString(),
                type, ascentRate);
    }
}
