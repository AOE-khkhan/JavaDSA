//------------------------------------------------------------------------------

import student.TestCase;

/**
 * Test the LinkedList class.
 * 
 * @author Bimal Gaudel
 * @version 2020-02-09
 */
public class LinkedListTest extends TestCase {
  /** A LinkedList<Integer> object used in the tests. */
  private LinkedList<Integer> intList;

  /** Sets up the tests that follow. */
  public void setUp() {
    intList = new LinkedList<Integer>();
  }

  /** Test the constructor. */
  public void testConstructor() {
    assertNotNull(intList);
  }

  /** Test toString public methods. */
  public void testMethods() {
    LinkedList<String> strList = new LinkedList<String>();

    strList.append("Name");
    assertEquals(strList.toString(), "Name");

    strList.append("Class");
    assertEquals(strList.toString(), "Name<SEP>Class");

    strList.remove("Name");
    assertEquals(strList.toString(), "Class");
    
    boolean removed = strList.remove("Name");
    assertEquals(removed, false);

    strList.remove("Class");
    assertEquals(strList.toString(), "");

    strList.append("Address");
    assertEquals(strList.isEmpty(), false);
    assertEquals(strList.hasData("Address"), true);
    assertEquals(strList.hasData("Class"), false);
    strList.remove("Address");
    assertEquals(strList.hasData("Address"), false);
    assertEquals(strList.isEmpty(), true);
    assertEquals(strList.toString(), "");

    strList.insert("Apple");
    assertEquals(strList.toString(), "Apple");
    strList.insert("Cat");
    assertEquals(strList.toString(), "Apple<SEP>Cat");
    strList.insert("Ball");
    assertEquals(strList.toString(), "Apple<SEP>Ball<SEP>Cat");
  }
}
