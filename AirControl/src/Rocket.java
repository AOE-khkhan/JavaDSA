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
     * @param prism      Prism of the object.
     * @param ascentRate Positive integer for object's rate of ascence.
     * @param trajectory Trajectory of the rocket object.
     *
     */
    public Rocket(String name, Prism prism, int ascentRate, double trajectory) {
        super(name, prism);
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
