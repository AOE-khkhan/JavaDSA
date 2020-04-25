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

    /** Box for the airObject */
    private Box box;

    /** Sets up the tests that follow. */
    public void setUp() {
        box = new Box(new int[] {0, 0, 0, 10, 15, 20});
        airObject = new AirObject("Apollo", box);
    }

    /** Get code coverage for Balloon. */
    public void testBalloon() {
        Balloon ball = new Balloon("Ball", box, "hot_air", 10);
        System.out.println(ball);
        assertEquals(ball.toString(),
                "Balloon Ball " + box.toString() + " hot_air 10");
    }

    /** Get code coverage for Airplane. */
    public void testAirplane() {
        Airplane plane = new Airplane("Boeing_777", box, "Yeti", 135, 8);
        System.out.println(plane);
        assertEquals(plane.toString(),
                "Airplane Boeing_777 " + box.toString() + " Yeti 135 8");
    }

    /** Get code coverage for Rocket. */
    public void testRocket() {
        Rocket rocket = new Rocket("Apollo", box, 90, 20);
        System.out.println(rocket);
        assertEquals(rocket.toString(),
                "Rocket Apollo " + box.toString() + " 90 20.00");
    }

    /** Get code coverage for Drone. */
    public void testDrone() {
        Drone drone = new Drone("Sentinel", box, "HackerX", 2);
        System.out.println(drone);
        assertEquals(drone.toString(),
                "Drone Sentinel " + box.toString() + " HackerX 2");
    }

    /** Get code coverage for Bird. */
    public void testBird() {
        Bird crow = new Bird("Jay", box, "crow", 5);
        System.out.println(crow);
        assertEquals(crow.toString(),
                "Bird Jay " + box.toString() + " crow 5");
    }

    /** Get code coverage. */
    public void testGetCodeCoverage() {
        assertEquals(airObject.getName(), "Apollo");
        //
        assertEquals(airObject.getXorig(), box.getOrig()[Box.DIM_X]);
        assertEquals(airObject.getYorig(), box.getOrig()[Box.DIM_Y]);
        assertEquals(airObject.getZorig(), box.getOrig()[Box.DIM_Z]);
        //
        assertEquals(airObject.getXwidth(), box.getWidths()[Box.DIM_X]);
        assertEquals(airObject.getYwidth(), box.getWidths()[Box.DIM_Y]);
        assertEquals(airObject.getZwidth(), box.getWidths()[Box.DIM_Z]);
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
                new Box(new int[] {0, 30, 30, 10, 10, 12}));
        assertEquals(airObject.compareTo(newAirObject), -1);
    }
}
