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

    /** Prism spec used for airObject. */
    private int[] prismSpec;

    /** Sets up the tests that follow. */
    public void setUp() {
        prismSpec = new int[] {0, 0, 0, 10, 15, 20};
        airObject = new AirObject("Apollo", prismSpec);
    }

    /** Get code coverage for Balloon. */
    public void testBalloon() {
        Balloon ball = new Balloon("Ball", prismSpec, "hot_air", 10);
        System.out.println(ball);
        assertEquals(ball.toString(), "Balloon Ball "
                + (new Prism(prismSpec)).toString() + " hot_air 10");
    }

    /** Get code coverage for Airplane. */
    public void testAirplane() {
        Airplane plane = new Airplane("Boeing_777", prismSpec, "Yeti", 135, 8);
        System.out.println(plane);
        assertEquals(plane.toString(), "Airplane Boeing_777 "
                + (new Prism(prismSpec)).toString() + " Yeti 135 8");
    }

    /** Get code coverage for Rocket. */
    public void testRocket() {
        Rocket rocket = new Rocket("Apollo", prismSpec, 90, 20);
        System.out.println(rocket);
        assertEquals(rocket.toString(), "Rocket Apollo "
                + (new Prism(prismSpec)).toString() + " 90 20.00");
    }

    /** Get code coverage for Drone. */
    public void testDrone() {
        Drone drone = new Drone("Sentinel", prismSpec, "HackerX", 2);
        System.out.println(drone);
        assertEquals(drone.toString(), "Drone Sentinel "
                + (new Prism(prismSpec)).toString() + " HackerX 2");
    }

    /** Get code coverage for Bird. */
    public void testBird() {
        Bird crow = new Bird("Jay", prismSpec, "crow", 5);
        System.out.println(crow);
        assertEquals(crow.toString(),
                "Bird Jay " + (new Prism(prismSpec)).toString() + " crow 5");
    }

    /** Get code coverage. */
    public void testGetCodeCoverage() {
        Prism prism = new Prism(prismSpec);
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
