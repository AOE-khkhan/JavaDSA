/**
 * Airplane type AirObject.
 * 
 * @author  Bimal Gaudel
 * 
 * @version Apr 10, 2020
 */
public class Airplane extends AirObject {
    /** Name of the airplane carrier. */
    private String carrier;

    /** Flight number of the airplane. */
    private int numFlight;

    /** Number of engines in the airplane. */
    private int numEngines;

    /**
     * Construct an Airplane object.
     * 
     * @param name       Name of the object.
     * @param prismSpec  Specifications for the prism that contains the object.
     * @param carrier    Name for the airplane carrier.
     * @param numFlight  Flight number.
     * @param numEngines Number of engines in the airplane.
     */
    //@formatter:off
    public Airplane(String name, int[] prismSpec,
            String carrier, int numFlight, int numEngines) {
    //@formatter:on
        super(name, prismSpec);
        this.carrier = carrier;
        this.numFlight = numFlight;
        this.numEngines = numEngines;
    }

    /**
     * Get a string representation of the object.
     * 
     * @return Specs of the object as a string.
     */
    @Override
    public String toString() {
        return String.format("Airplane %s %s %s %d %d", getName(),
                prismString(), carrier, numFlight, numEngines);
    }
}
