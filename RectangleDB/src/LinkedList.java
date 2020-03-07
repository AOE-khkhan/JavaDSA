/**
 * A linked-list data structure for holding RectangleRecord objects.
 * 
 * @param   <E> The type of object stored in the list.
 * 
 * @author      Bimal Gaudel
 * 
 * @version     Feb 2020
 */
public class LinkedList<E> {
    /** The head node of the list. */
    private Node headerNode;

    /** The tail node of the list. */
    private Node trailerNode;

    /** The current node used for iterating. */
    private Node currNode;

    /** The number of items in the list. */
    private int count;

    /** Empty parameter LinkedList constructor. */
    LinkedList() {
        headerNode = new Node();
        trailerNode = new Node();
        currNode = trailerNode;
        headerNode.setNext(trailerNode);
        count = 0;
    }

    /**
     * Check if the list is empty.
     * 
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty() {
        return (headerNode.getNext() == trailerNode);
    }

    /**
     * Append data to the list.
     * 
     * @param data data to be appended.
     */
    public void append(E data) {
        moveToEnd();
        currNode.setData(data);
        trailerNode = new Node();
        currNode.setNext(trailerNode);
        ++count;
    }

    /**
     * Remove the first occurrence of data if exists.
     * 
     * @param  data Data to be removed from the list.
     * 
     * @return      True if removed, false otherwise.
     */
    public boolean remove(E data) {
        //@formatter:off
        for (moveToFront();
                (currNode.getNext() != trailerNode);
                curseToNext()) {
            if (currNode.getNext().getData().equals(data)) {
                currNode.setNext(currNode.getNext().getNext());
                --count;
                return true;
            }
        }
        return false;
        //@formatter:on
    }

    /**
     * Get the number of items in the list.
     * 
     * @return Number of items in the list.
     */
    public int getCount() {
        return count;
    }

    /**
     * Move the cursor node towards the end. Moving stops once the cursor hits
     * the
     * trailerNode.
     */
    public void curseToNext() {
        if (currNode != trailerNode) {
            currNode = currNode.getNext();
        }
    }

    /**
     * Get the data from the Node pointed by the currNode.
     *
     * @return Data in the current node pointed to by currNode.
     */
    public E yieldNode() {
        return currNode.getData();
    }

    /**
     * Check if the list has a datum.
     * 
     * @param  data The datum being checked for.
     * 
     * @return      True if data exists in the list.
     */
    public boolean exists(E data) {
        moveToHead();
        while (!atEnd()) {
            if (currNode.getData().equals(data)) {
                return true;
            }
            curseToNext();
        }
        return false;
    }

    /**
     * Yield the data stored at a given position when counted from the head.
     *
     * The counting is zero based. @note It is not an efficient method and
     * should be avoided whenever possible.
     * 
     * @param  index The node index to be yielded. The head node's index is
     *               zero.
     * 
     * @return       Data stored by the node at index position. Null if invalid
     *               position is passed.
     */
    public E yieldIndex(int index) {
        // check invalid index
        if ((index >= getCount()) || (index < 0)) {
            return null;
        }
        // we don't want to mutate the current node
        // that was set on the list before this method
        // was called
        Node currentNodeBeforeProcessing = currNode;
        moveToHead();
        while (index > 0) {
            --index;
            curseToNext();
        }
        E data = yieldNode();
        // unmutate the current node
        currNode = currentNodeBeforeProcessing;
        return data;
    }

    /**
     * Check if the current node is at the end of the list.
     * 
     * @return True if cursor is at the end of the list.
     */
    public boolean atEnd() {
        return currNode == trailerNode;
    }

    /** Move the cursor to the head node. */
    public void moveToHead() {
        moveToFront();
        curseToNext();
    }

    /** Move the cursor node to the header node of the list. */
    private void moveToFront() {
        currNode = headerNode;
    }

    /** Move the cursor node to the trailer node of the list. */
    private void moveToEnd() {
        currNode = trailerNode;
    }


    /** The node data structure used in the LinkedList<E> class. */
    private class Node {
        /** Data stored by this Node. */
        private E data;
        /** Reference to the linked Node. */
        private Node next;

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
