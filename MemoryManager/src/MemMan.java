
/**
 * Project 1: Memory Manager
 * Intermediate Data Structure and Algorithm, CS 5040
 * Spring 2020, Virginia Tech
 */

import java.io.File;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * The class containing the main method.
 *
 * @author Bimal Gaudel
 * @version 0.1
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

public class MemMan {
  /**
   * The main method of the MemMan program.
   * 
   * @param args Command line parameters
   */
  public static void main(String[] args) {
    // This is the main file for the program.

    // the initial memory size
    int initMemSizeInTwosPower = Integer.parseInt(args[0]);

    // the initial hashtable size
    int initHashTblSize = Integer.parseInt(args[1]);

    // the command file
    File commandFile = new File(args[2]);

    // ready to honor any commands
    try {
      // scans the command file
      Scanner sc = new Scanner(commandFile);
      // our memory manager
      MemoryManager memoryManager = new MemoryManager(initMemSizeInTwosPower);
      // our hashtable
      RecordHashTable hashTable = new RecordHashTable(initHashTblSize);

      Communicator memManCommunicator = new Communicator(hashTable,
          memoryManager);
      while (sc.hasNext()) {
        String commandLine = sc.nextLine().trim().replaceAll("\\s+", " ");

        if (commandLine.equals("print hashtable")) {
          memManCommunicator.printHashTable();

        }
        else if (commandLine.equals("print blocks")) {
          memManCommunicator.printMemoryPool();
        }
        else if (commandLine.startsWith("add")) {
          String recordName = commandLine.substring(4);
          memManCommunicator.addRecordToHashTable(recordName);

        }
        else if (commandLine.startsWith("delete")) {
          String recordName = commandLine.substring(7);
          memManCommunicator.deleteRecordFromHashTable(recordName);

        }
        else if (commandLine.startsWith("update add")) {
          String pattern = "^update add(.*?)<SEP>(.*?)<SEP>(.*)$";
          Pattern r = Pattern.compile(pattern);
          Matcher m = r.matcher(commandLine);
          m.find();
          String name = m.group(1).trim();
          String fieldName = m.group(2).trim();
          String fieldValue = m.group(3).trim();

          memManCommunicator.updateAddRecordKeyVal(name, fieldName, fieldValue);

        }
        else if (commandLine.startsWith("update delete")) {
          String pattern = "^update delete(.*?)<SEP>(.*?)$";
          Pattern r = Pattern.compile(pattern);
          Matcher m = r.matcher(commandLine);
          m.find();
          String nameString = m.group(1).trim();
          String fieldNameString = m.group(2).trim();

          memManCommunicator.updateDeleteRecordKeyVal(nameString,
              fieldNameString);
        }
      }
      // sc has been read thoroughly
      sc.close();
    } catch (Exception e) {
      // can't read commands, then quit
      return;
    }
  } // main
} // class MemMan
