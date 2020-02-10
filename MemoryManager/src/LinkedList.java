//------------------------------------------------------------------------------

/**
 * A generic linked list data structure.
 * 
 * @author Bimal Gaudel
 * @version 2020-02-09
 */
public class LinkedList<E> {
  /** The head node of the list. */
  private Node headNode;

  /** The tail node of the list. */
  private Node tailNode;

  /** Empty parameter LinkedList constructor. */
  LinkedList() {
    tailNode = new Node();
    headNode = new Node();
    headNode.next = tailNode;
  }

  /** Construct list with a datum. */
  LinkedList(E dt) {
    this();
    this.append(dt);
  }

  /**
   * Append data to the list.
   * 
   * @param dt data to be appended.
   */
  public void append(E dt) {
    tailNode.data = dt;
    tailNode.next = new Node();
    tailNode = tailNode.next;
  }

  /**
   * Remove the first occurence of data if exists.
   * 
   * @return true if removed, false otherwise.
   */
  public boolean remove(E dt) {
    Node currNode = headNode;
    while (currNode.next != null) {
      if (currNode.next.data == dt) {
        currNode.next = currNode.next.next;
        return true;
      }
    }
    return false;
  }

  /**
   * Get a string representation of the current list.
   *
   * @return String representation of the object.
   */
  @Override
  public String toString() {
    String result = "";
    Node currNode = headNode;

    while (currNode.next.data != null) {
      currNode = currNode.next;
      result += "<SEP>" + currNode.data.toString();
    }

    if (result.isBlank()) {
      return result;
    }
    else {
      return result.substring(5);
    }
  }

  /** A node data structure. */
  private class Node {
    /** Data stored by this Node. */
    public E data;
    /** Reference to the linked Node. */
    public Node next;

    /** Construct from null. */
    Node() {
      this.data = null;
      this.next = null;
    }
  } // class Node
}
// class LinkedList
