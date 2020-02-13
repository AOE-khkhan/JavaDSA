//------------------------------------------------------------------------------

/**
 * A generic linked list data structure.
 * 
 * @author Bimal Gaudel
 * @version 2020-02-09
 */
public class LinkedList<E extends Comparable<E>> {
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
   * Check if the list is empty.
   * 
   * @return true if empty, false otherwise.
   */
  public boolean isEmpty() {
    return (this.headNode.next.data == null);
  }

  /**
   * Check if the list has a datum.
   * 
   * @param dt The datum being checked for.
   */
  public boolean hasData(E dt) {
    Node currNode = headNode;
    while (currNode.next != null) {
      if (currNode.next.data == dt) {
        return true;
      }
      currNode = currNode.next;
    }
    return false;
  }

  /**
   * Insert data to the list at a position that comes before the first occuring
   * data that is greater than the data being inserted.
   * 
   * @param dt The datum being inserted.
   */
  public void insert(E dt) {
    if (this.isEmpty()) {
      this.append(dt);
      return;
    }
    Node currNode = headNode;
    while (currNode.next != tailNode) {
      if (currNode.next.data.compareTo(dt) >= 0) {
        Node prevNext = currNode.next;
        currNode.next = new Node();
        currNode.next.data = dt;
        currNode.next.next = prevNext;
        return;
      }
      currNode = currNode.next;
    }
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
    while (currNode.next != tailNode) {
      if (currNode.next.data == dt) {
        currNode.next = currNode.next.next;
        return true;
      }
      currNode = currNode.next;
    }
    return false;
  }

  /** Pop the datum at the beginning of the list. */
  public E popFront() {
    E data = this.headNode.next.data;
    this.remove(data);
    return data;
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
