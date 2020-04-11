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

    /** Prism for the airObject */
    private Prism prism;

    /** Sets up the tests that follow. */
    public void setUp() {
        prism = new Prism(new int[] {0, 0, 0, 10, 15, 20});
        airObject = new AirObject("Apollo", prism);
    }

    /** Get code coverage for Balloon. */
    public void testBalloon() {
        Balloon ball = new Balloon("Ball", prism, "hot_air", 10);
        System.out.println(ball);
        assertEquals(ball.toString(),
                "Balloon Ball " + prism.toString() + " hot_air 10");
    }

    /** Get code coverage for Airplane. */
    public void testAirplane() {
        Airplane plane = new Airplane("Boeing_777", prism, "Yeti", 135, 8);
        System.out.println(plane);
        assertEquals(plane.toString(),
                "Airplane Boeing_777 " + prism.toString() + " Yeti 135 8");
    }

    /** Get code coverage for Rocket. */
    public void testRocket() {
        Rocket rocket = new Rocket("Apollo", prism, 90, 20);
        System.out.println(rocket);
        assertEquals(rocket.toString(),
                "Rocket Apollo " + prism.toString() + " 90 20.00");
    }

    /** Get code coverage for Drone. */
    public void testDrone() {
        Drone drone = new Drone("Sentinel", prism, "HackerX", 2);
        System.out.println(drone);
        assertEquals(drone.toString(),
                "Drone Sentinel " + prism.toString() + " HackerX 2");
    }

    /** Get code coverage for Bird. */
    public void testBird() {
        Bird crow = new Bird("Jay", prism, "crow", 5);
        System.out.println(crow);
        assertEquals(crow.toString(),
                "Bird Jay " + prism.toString() + " crow 5");
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
        try {
            airObject.toString();
        }
        catch (Exception e) {
            assertFuzzyEquals(e.getMessage(),
                    "toString() method not implemented"
                            + " for this derived class!");
        }
    }

    /** Test the method compareTo. */
    public void testCompareTo() {
        AirObject newAirObject = new AirObject("Black Hawk",
                new Prism(new int[] {0, 30, 30, 10, 10, 12}));
        assertEquals(airObject.compareTo(newAirObject), -1);
    }
}
