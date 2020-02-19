/**
 * Project 2: Rectangle Data Base Manager Intermediate Data Structure and
 * Algorithm, CS 5040 Spring 2020, Virginia Tech Student: Bimal Gaudel
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
import java.io.IOException;
import java.util.Scanner;

/**
 * Rectangle data base manager's main method lives in this class. Programming
 * Project 2 COMP 5040 Spring 2020, Virginia Tech.
 * 
 * 
 * @author Bimal Gaudel
 * 
 * @version Feb 2020
 */
public class RectangleDB {
  /**
   * @param args [0] The file which contains commands to be executed.
   * 
   * @throws IOException
   */
  public static void main(String[] args) {

    if (args.length != 1) {
      System.out.println("Bad argument count. Usage: "
                         + "RectangleDB <command-file>");
      return;
    }

    // scanner object to parse the command file
    Scanner sc;

    try {
      sc = new Scanner(new File(args[0]));
    }
    catch (Exception e) {
      // error opening file.
      System.out.println("Error opening file!");
      System.out.println(e.getMessage());
      return;
    }

    /** The world object for this session is now initialized. */
    World world = new World();

    /** A handler object to channel commands to the World object. */
    RequestHandler requestHandler = new RequestHandler(world);

    while (sc.hasNext()) {
      String cmd = sc.next();

      if (cmd.equals("dump")) {
        requestHandler.dump();
      }
      else if (cmd.equals("intersections")) {
        requestHandler.intersections();
      }
      else if (cmd.equals("regionsearch")) {
        requestHandler.regionsearch(sc.nextLine());
      }
      else if (cmd.equals("remove")) {
        requestHandler.remove(sc.next(), sc.nextLine());
      }
      else if (cmd.equals("insert")) {
        requestHandler.insert(sc.next(), sc.nextLine());
      }
      else {
        System.out.println("Unrecognized input: |" + cmd + "|");
      }
    }

    // done with the scanner object
    sc.close();
  }
}
