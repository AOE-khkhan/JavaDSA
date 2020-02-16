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

  /** The current node used for iterating. */
  private Node currNode;

  /** Empty parameter LinkedList constructor. */
  LinkedList() {
    tailNode = new Node();
    headNode = new Node();
    currNode = headNode;
    headNode.setNext(tailNode);
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
    return (this.headNode.getNext().getData() == null);
  }

  /**
   * Check if the list has a datum.
   * 
   * @param dt The datum being checked for.
   */
  public boolean hasData(E dt) {
    for (moveToFront(); (!(currNode.getData() == null)); curseToNext()) {
      if (currNode.getData().equals(dt)) {
        return true;
      }
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
    Node runningNode = headNode;
    while (runningNode.getNext() != tailNode) {
      if (runningNode.getNext().getData().compareTo(dt) >= 0) {
        Node prevNext = runningNode.getNext();
        runningNode.setNext(new Node());
        runningNode.getNext().setData(dt);
        runningNode.getNext().setNext(prevNext);
        return;
      }
      runningNode = runningNode.getNext();
    }
    this.append(dt);
  }

  /**
   * Append data to the list.
   * 
   * @param dt data to be appended.
   */
  public void append(E dt) {
    tailNode.setData(dt);
    tailNode.setNext(new Node());
    tailNode = tailNode.getNext();
  }

  /**
   * Remove the first occurrence of data if exists.
   * 
   * @return true if removed, false otherwise.
   */
  public boolean remove(E dt) {
    currNode = this.headNode;
    while (currNode.getNext() != tailNode) {
      if (currNode.getNext().getData().equals(dt)) {
        currNode.setNext(currNode.getNext().getNext());
        return true;
      }
      currNode = currNode.getNext();
    }
    return false;
  }

  /** Pop the datum at the beginning of the list. */
  public E popFront() {
    E data = this.headNode.getNext().getData();
    this.remove(data);
    return data;
  }

  /** Move the cursor node to the head of the list. */
  public void moveToFront() {
    this.currNode = this.headNode.getNext();
  }

  /**
   * Move the cursor node towards the end. Moving stops once the cursor hits the
   * tailNode.
   */
  public void curseToNext() {
    if (currNode == tailNode) {
      return;
    }
    this.currNode = currNode.getNext();
  }

  /**
   * Get the data from the Node pointed by the currNode.
   *
   * @return Data in the current node pointed to by currNode.
   */
  public E yieldNode() {
    return this.currNode.getData();
  }

  /**
   * Get a string representation of the current list.
   *
   * @return String representation of the object.
   */
  @Override
  public String toString() {
    if (isEmpty()) {
      return "";
    }
    moveToFront();
    String result = yieldNode().toString();
    curseToNext();
    while (!(yieldNode() == null)) {
      result += "<SEP>" + yieldNode().toString();
      curseToNext();
    }
    return result;
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

    /** Getter for data. */
    public E getData() {
      return this.data;
    }

    /** Getter for next. */
    public Node getNext() {
      return this.next;
    }

    /** Setter for data. */
    public void setData(E data) {
      this.data = data;
    }

    /** Setter for next. */
    public void setNext(Node next) {
      this.next = next;
    }
  } // class Node
}
// class LinkedList
