/**
 * Programming Assignment #4: AirControl. CS 5040 Spring 2020, Virginia Tech.
 */

// On my honor:
//
// - I have not used source code obtained from another student,
// or any other unauthorized source, either modified or
// unmodified.
//
// - All source code and documentation used in my program is
// either my original work, or was derived by me from the
// source code published in the textbook for this course.
//
// - I have not discussed coding details about this project with
// anyone other than my partner (in the case of a joint
// submission), instructor, ACM/UPE tutors or the TAs assigned
// to this course. I understand that I may discuss the concepts
// of this program with other students, and that another student
// may help me debug my program so long as neither of us writes
// anything during the discussion or modifies any computer file
// during the discussion. I have violated neither the spirit nor
// letter of this restriction.

import java.io.File;
import java.util.Scanner;

/**
 * The class containing the main method.
 *
 * @author  Bimal Gaudel
 * 
 * @version Apr 10, 2020
 */
public class AirControl {
    /**
     * Main method of the class.
     * 
     * @param args Command line parameters.
     *             [0] command file
     */
    public static void main(String[] args) {
        // scanner object to parse the command file
        Scanner sc;

        try {
            sc = new Scanner(new File(args[0]));
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        // ready to execute commands from the file
        // initialize a world object
        World world = new World();

        while (sc.hasNext()) {
            String cmd = sc.next();
            //
            if (cmd.equals("print")) {
                String target = sc.next();
                if (target.equals("skiplist")) {
                    world.printSkiplist();
                }
                else if (target.equals("bintree")) {
                    world.printBintree();
                }
                else { // "object"
                    String name = sc.next();
                    world.printObject(name);
                }
            }
            else if (cmd.equals("rangeprint")) {
                String start = sc.next();
                String end = sc.next();
                world.printRange(start, end);
            }
            else if (cmd.equals("collisions")) {
                world.printCollisions();
            }
            else if (cmd.equals("delete")) {
                String name = sc.next();
                world.deleteObject(name);
            }
            else if (cmd.equals("intersect")) {
                Prism prism = scanPrism(sc);
                world.printIntersection(prism);
            }
            else { // "add"
                String objectType = sc.next();
                if (objectType.equals("balloon")) {
                    world.addObject(scanBalloon(sc));
                }
                else if (objectType.equals("airplane")) {
                    world.addObject(scanAirplane(sc));
                }
                else if (objectType.equals("rocket")) {
                    world.addObject(scanRocket(sc));
                }
                else if (objectType.equals("drone")) {
                    world.addObject(scanDrone(sc));
                }
                else { // "bird"
                    world.addObject(scanBird(sc));
                }
            }
        }
        // done with the scanner object
        sc.close();
    }

    /**
     * Scan a prism specs from a string. @see Prism.java
     * 
     * @param  sc Scanner object.
     * 
     * @return    Prism object.
     */
    private static Prism scanPrism(Scanner sc) {
        int[] specs = new int[6];
        specs[0] = sc.nextInt();
        specs[1] = sc.nextInt();
        specs[2] = sc.nextInt();
        specs[3] = sc.nextInt();
        specs[4] = sc.nextInt();
        specs[5] = sc.nextInt();
        return new Prism(specs);
    }

    /**
     * Scan Balloon specs from a string.
     * 
     * @param  sc Scanner object.
     * 
     * @return    Balloon object.
     */
    private static Balloon scanBalloon(Scanner sc) {
        String name = sc.next();
        Prism prism = scanPrism(sc);
        String type = sc.next();
        int ascentRate = sc.nextInt();
        return new Balloon(name, prism, type, ascentRate);
    }

    /**
     * Scan Airplane specs from a string.
     * 
     * @param  sc Scanner object.
     * 
     * @return    Airplane object.
     */
    private static Airplane scanAirplane(Scanner sc) {
        String name = sc.next();
        Prism prism = scanPrism(sc);
        String carrier = sc.next();
        int numFlight = sc.nextInt();
        int numEngines = sc.nextInt();
        return new Airplane(name, prism, carrier, numFlight, numEngines);
    }

    /**
     * Scan Rocket specs from a string.
     * 
     * @param  sc Scanner object.
     * 
     * @return    Rocket object.
     */
    private static Rocket scanRocket(Scanner sc) {
        String name = sc.next();
        Prism prism = scanPrism(sc);
        int ascentRate = sc.nextInt();
        double trajectory = sc.nextDouble();
        return new Rocket(name, prism, ascentRate, trajectory);
    }

    /**
     * Scan Drone specs from a string.
     * 
     * @param  sc Scanner object.
     * 
     * @return    Drone object.
     */
    private static Drone scanDrone(Scanner sc) {
        String name = sc.next();
        Prism prism = scanPrism(sc);
        String brand = sc.next();
        int numEngines = sc.nextInt();
        return new Drone(name, prism, brand, numEngines);
    }

    /**
     * Scan Bird specs from a string.
     * 
     * @param  sc Scanner object.
     * 
     * @return    Bird object.
     */
    private static Bird scanBird(Scanner sc) {
        String name = sc.next();
        Prism prism = scanPrism(sc);
        String type = sc.next();
        int number = sc.nextInt();
        return new Bird(name, prism, type, number);
    }
}
