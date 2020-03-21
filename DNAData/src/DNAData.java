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
     */
    public static void main(String[] args) {
        if (args.length != 4) {
            System.out.println("Bad argument count! Usage: \n"
                    + "DNAData <number-of-buffers> <buffer-size> "
                    + "<initial-hash-size> <command-file>");
            return;
        }
    }
}
