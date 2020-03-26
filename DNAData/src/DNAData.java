/**
 * Programming Assignment #3: DNAData. CS 5040 Spring 2020, Virginia Tech
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
 * The class with main function for the DNAData project.
 * 
 * @author  Bimal Gaudel
 * 
 * @version Mar 19, 2020
 */
public class DNAData {
    /**
     * @param args [0] Number of buffers.
     *             [1] Buffer size.
     *             [2] Initial hash size.
     *             [3] The file with commands to be executed.
     * 
     */
    public static void main(String[] args) {
        if (args.length != 4) {
            System.out.println("Bad argument count! Usage: \n"
                    + "DNAData <number-of-buffers> <buffer-size> "
                    + "<initial-hash-size> <command-file>");
            return;
        }

        // now we have exactly four arguments passed
        // let's make sure that they are in order
        int numBuffs;
        int buffSize;
        int numHashSlots;
        try {
            numBuffs = Integer.parseInt(args[0]);
            buffSize = Integer.parseInt(args[1]);
            numHashSlots = Integer.parseInt(args[2]);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        if (!(isTwosPower(buffSize))) {
            System.out.println(
                    "<buffer-size> param must be a positive power of two. Got: |"
                            + buffSize + "|");
            return;
        }

        // now the arguments are in order lets check if the commands file is in
        // order too
        //
        // scanner object to parse the command file
        Scanner sc;

        try {
            sc = new Scanner(new File(args[3]));
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        // Now we are ready to execute the commands from the command file

        // The world object for this session is now initialized.
        World world = new World(numBuffs, buffSize, numHashSlots);

        while (sc.hasNext()) {
            String cmd = sc.next();

            if (cmd.equals("add")) {
                world.addRecord(sc.nextLine());
            }
            else if (cmd.equals("delete")) {
                world.deleteRecord(sc.nextLine());
            }
            else if (cmd.equals("print")) {
                String printTarget = sc.next();
                if (printTarget.equals("hashtable")) {
                    world.printHashtable();
                }
                else if (printTarget.equals("blocks")) {
                    world.printBlocks();
                }
                else if (printTarget.equals("buffers")) {
                    world.printBuffers();
                }
                else if (printTarget.equals("record")) {
                    world.printRecord(sc.nextLine());
                }
                else {
                    System.out.println("Invalid target |" + printTarget
                            + "| for printing!");
                }
            }
            else {
                System.out.println("Unrecognized input: |" + cmd + "|");
            }
        }

        // done with the scanner object
        sc.close();
    }

    /**
     * Check if a number is a positive power of two.
     * 
     * @param  num An integer.
     * 
     * @return     True if num is a positive power of two.
     */
    private static boolean isTwosPower(int num) {
        if (num <= 0) {
            return false;
        }
        while (num != 1) {
            if (((num / 2) * 2) == num) {
                num = num / 2;
            }
            else {
                return false;
            }
        }
        return true;
    }
}
