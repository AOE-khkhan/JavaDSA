//------------------------------------------------------------------------------

import student.TestCase;

/**
 * Test the LinkedList class.
 * 
 * @author Bimal Gaudel
 * @version 2020-02-09
 */
public class LinkedListTest extends TestCase {
  /** A LinkedList<String> object used in the tests. */
  private LinkedList<String> strList;

  /** Sets up the tests that follow. */
  public void setUp() {
    strList = new LinkedList<String>();
  }

  /** Test the constructor. */
  public void testConstructor() {
    assertNotNull(strList);
  }

  /** Test the method append. */
  public void testAppend() {

    strList.append("Name");
    assertEquals(strList.toString(), "Name");

    strList.append("Class");
    assertEquals(strList.toString(), "Name<SEP>Class");
  }

  /** Test the method remove. */
  public void testRemove() {
    strList.append("Name");
    strList.append("Class");
    assertEquals(strList.toString(), "Name<SEP>Class");
    strList.remove("Name");
    assertEquals(strList.toString(), "Class");

    boolean removed = strList.remove("Name");
    assertEquals(removed, false);

    strList.remove("Class");
    assertEquals(strList.toString(), "");
  }

  /** Test the method is empty. */
  public void testIsEmpty() {
    strList.append("Address");
    assertEquals(strList.isEmpty(), false);
    strList.remove("Address");
    assertEquals(strList.isEmpty(), true);
    assertEquals(strList.toString(), "");
  }

  /** Test the method hasData */
  public void testHasData() {
    strList.append("Address");
    assertEquals(strList.hasData("Address"), true);
    assertEquals(strList.hasData("Class"), false);
  }

  /** Test the method insert. */
  public void testInsert() {
    strList.insert("Apple");
    assertEquals(strList.toString(), "Apple");
    strList.insert("Cat");
    assertEquals(strList.toString(), "Apple<SEP>Cat");
    strList.insert("Ball");
    assertEquals(strList.toString(), "Apple<SEP>Ball<SEP>Cat");
  }

  /** Test the method popFront. */
  public void testPopFront() {
    strList.append("Apple");
    strList.append("Ball");
    strList.append("Cat");
    assertEquals(strList.popFront(), "Apple");
    assertEquals(strList.popFront(), "Ball");
    assertEquals(strList.popFront(), "Cat");
    assertEquals(strList.isEmpty(), true);
  }

  /** Test the methods moveToFront, curseToNext and yieldNode. */
  public void testCursoryMethods() {
    strList.append("Aardvark");
    strList.append("Bat");
    strList.append("Cat");
    strList.append("Dog");
    strList.append("Elephant");

    strList.moveToFront();
    assertEquals(strList.yieldNode(), "Aardvark");

    strList.curseToNext();
    assertEquals(strList.yieldNode(), "Bat");

    strList.curseToNext();
    assertEquals(strList.yieldNode(), "Cat");

    strList.curseToNext();
    assertEquals(strList.yieldNode(), "Dog");

    strList.curseToNext();
    assertEquals(strList.yieldNode(), "Elephant");

    strList.curseToNext();
    assertNull(strList.yieldNode());

    strList.curseToNext();
    assertNull(strList.yieldNode());
  }
}
