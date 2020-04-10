/**
 * Drone type AirObject.
 * 
 * @author  Bimal Gaudel
 * 
 * @version Apr 10, 2020
 */
public class Drone extends AirObject {
    /** Brand name of the drone. */
    private String brand;

    /** Number of engines in the drone. */
    private int numEngines;

    /**
     * Construct Drone object.
     * 
     * @param name       Name of the object.
     * @param prismSpec  Specifications for the prism that contains the object.
     * @param brand      Brand name of the drone.
     * @param numEngines Number of engines in the drone.
     */
    //@formatter:off
    public Drone(String name, int[] prismSpec,
            String brand, int numEngines) {
    //@formatter:on
        super(name, prismSpec);
        this.brand = brand;
        this.numEngines = numEngines;
    }

    /**
     * Get a string representation of the object.
     * 
     * @return Specs of the object as a string.
     */
    @Override
    public String toString() {
        return String.format("Drone %s %s %s %d", getName(), prismString(),
                brand, numEngines);
    }
}
