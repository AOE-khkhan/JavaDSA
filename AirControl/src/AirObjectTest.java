import student.TestCase;

/**
 * Test the AirObject base class.
 * 
 * @author  Bimal Gaudel
 * 
 * @version Apr 10, 2020
 */
public class AirObjectTest extends TestCase {
    /** AirObject used for testing. */
    private AirObject airObject;

    /** Prism object used to create the AirObject. */
    private Prism prism;

    /** Sets up the tests that follow. */
    public void setUp() {
        int[] prismSpec = new int[] {0, 0, 0, 10, 15, 20};
        prism = new Prism(prismSpec);
        airObject = new AirObject("Apollo", prismSpec);
    }

    /** Get code coverage. */
    public void testGetCodeCoverage() {
        assertEquals(airObject.getName(), "Apollo");
        //
        assertEquals(airObject.getXorig(), prism.getOrigX());
        assertEquals(airObject.getYorig(), prism.getOrigY());
        assertEquals(airObject.getZorig(), prism.getOrigZ());
        //
        assertEquals(airObject.getXwidth(), prism.getWidthX());
        assertEquals(airObject.getYwidth(), prism.getWidthY());
        assertEquals(airObject.getZwidth(), prism.getWidthZ());
    }

    /** Test the method compareTo. */
    public void testCompareTo() {
        AirObject newAirObject =
                new AirObject("Black Hawk", new int[] {0, 30, 30, 10, 10, 12});
        assertEquals(airObject.compareTo(newAirObject), -1);
    }
}
