/**
 * Bird type AirObject.
 * 
 * @author  Bimal Gaudel
 * 
 * @version Apr 10, 2020
 */
public class Bird extends AirObject {
    /** Type of bird. */
    private String type;

    /** Number of birds. */
    private int number;

    /**
     * Construct Bird object.
     * 
     * @param name   Name of the object.
     * @param box  Box of the object.
     * @param type   Name of the bird's kind. eg. crow, vulture.
     * @param number Number of birds.
     */
    public Bird(String name, Box box, String type, int number) {
        super(name, box);
        this.type = type;
        this.number = number;
    }

    /**
     * Get a string representation of the object.
     * 
     * @return Specs of the object as a string.
     */
    @Override
    public String toString() {
        return String.format("Bird %s %s %s %d", getName(), boxString(), type,
                number);
    }
}
