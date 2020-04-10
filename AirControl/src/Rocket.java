/**
 * Rocket type AirObject.
 * 
 * @author  Bimal Gaudel
 * 
 * @version Apr 10, 2020
 */
public class Rocket extends AirObject {
    /** Positive integer for object's rate of ascence. */
    private int ascentRate;

    /** Trajectory of the rocket object. */
    private double trajectory;

    /**
     * Construct Rocket object.
     * 
     * @param name       Name of the object.
     * @param prismSpec  Specifications for the prism that contains the object.
     * @param ascentRate Positive integer for object's rate of ascence.
     * @param trajectory Trajectory of the rocket object.
     *
     */
    //@formatter:off
    public Rocket(String name, int[] prismSpec,
            int ascentRate, double trajectory) {
    //@formatter:on
        super(name, prismSpec);
        this.ascentRate = ascentRate;
        this.trajectory = trajectory;
    }

    /**
     * Get a string representation of the object.
     * 
     * @return Specs of the object as a string.
     */
    @Override
    public String toString() {
        return String.format("Rocket %s %s %d %.2f", getName(), prismString(),
                ascentRate, trajectory);
    }
}
