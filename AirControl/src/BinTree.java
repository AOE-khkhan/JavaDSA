import java.util.Scanner;

/**
 * A BinTree class for storing AirObjects'.
 *
 * 
 * @author  Bimal Gaudel
 * 
 * @version Apr 22, 2020
 */
public class BinTree {


    /**
     * Keep a flyweight object initialized so that we won't be recreating it
     * when no instance of BinTreeNodeFlyweight class exist.
     */
    private final BinTreeNodeFlyweight fly = BinTreeNodeFlyweight.getInstance();


    /** The root node of the bin tree. */
    private BinTreeNode root;


    /** The BinBox object for the root node. */
    private final BinBox rootBinBox;


    /**
     * Construct a bin tree.
     * 
     * @param worldSize The dimension in one direction for the 3d space
     *                  applicable to this BinTree object.
     */
    public BinTree(int worldSize) {
        rootBinBox = new BinBox(worldSize);
        root = fly;
    }


    /**
     * Insert an AirObject to the tree.
     * 
     * @param airObject The AirObject to be inserted.
     */
    public void insert(AirObject airObject) {
        root = root.insert(airObject, rootBinBox, 0);
    }


    /**
     * Delete an AirObject from the tree.
     * 
     * @param airObject The AirObject to be deleted.
     */
    public void delete(AirObject airObject) {
        root = root.delete(airObject, rootBinBox, 0);
    }


    /**
     * Print the BinTree.
     * 
     * @return Number of nodes printed.
     */
    public int dumpTree() {
        return dumpNode(root, "");
    }


    /** Print the intersections in the tree. */
    public void printCollisions() {
        printCollInNode(root, rootBinBox, 0);
    }

    /**
     * Print a node of the BinTree.
     * 
     * @param  node    The node to be printed.
     * @param  leftPad String to be printed before the content of the node.
     * 
     * @return         Number of nodes printed.
     */
    private static int dumpNode(BinTreeNode node, String leftPad) {
        if (node.isLeaf()) {
            // printing a leaf node
            BinTreeNodeLeaf leaf = (BinTreeNodeLeaf) node;
            if (leaf.isEmpty()) {
                // printing the flyweight
                System.out.println(leftPad + "E");
                return 1; // printed this node
            }
            else {
                LinkedList<AirObject> records =
                        ((BinTreeNodeLeaf) node).getRecords();

                System.out.println(leftPad + "Leaf with " + records.getCount()
                        + " objects:");

                for (records.moveToHead(); !records.atEnd(); records
                        .curseToNext()) {
                    System.out.println(
                            leftPad + "(" + records.yieldCurrNode() + ")");
                }

                return 1; // printed this node
            }
        }
        else {
            // printing an internal node
            System.out.println(leftPad + "I");

            int nodesPrinted = 1; // printed this internal node.
            BinTreeNodeInternal intrnl = (BinTreeNodeInternal) node;
            for (BinTreeNode child : intrnl.getChildren()) {
                nodesPrinted += dumpNode(child, leftPad + "  ");
            }

            return nodesPrinted;
        }
    }


    /**
     * Print unique intersections in a node.
     * 
     * @param node      The node to be printed intersections from.
     * @param box       A BinBox corresponding to the node.
     * @param nodeLevel The level of the node.
     */
    private static void printCollInNode(BinTreeNode node, BinBox box,
            int nodeLevel) {
        if (node.isLeaf()) {
            // Found a leaf node.
            // Iterate through its records and print pair of records with
            // intersecting boxes
            LinkedList<AirObject> records =
                    ((BinTreeNodeLeaf) node).getRecords();
            int ocounter = 0;
            //@formatter:off
            for (records.moveToHead();
                    !records.atEnd();
                    records.curseToNext()) {
                // the outer loop is cursed using the cursor of the linked list

                AirObject rec1 = records.yieldCurrNode();

                for (int icounter = ocounter + 1;
                        icounter < records.getCount();
                        ++icounter) {
            //@formatter:on
                    // the inner loop is cursed using indices


                    AirObject rec2 = records.yieldIndex(icounter);

                    Box isecBox = rec1.getBox().getIntersection(rec2.getBox());

                    if (box.hasPoint(isecBox.getOrig())) {
                        // We see if the origin of the intersection falls in the
                        // current node's BinBox, to avoid printing the same
                        // pair of intersecting objects from multiple nodes in
                        // the tree.
                        System.out.println("(" + rec1 + ") and (" + rec2 + ")");
                    }
                }
                ++ocounter;
            }
        }
        else {
            // children of the internal node
            BinTreeNode[] children = ((BinTreeNodeInternal) node).getChildren();

            // descriminator dimension
            int descriDim = nodeLevel % Box.NUM_DIMS;

            // for each children call printIsecsInNode
            for (int ii = 0; ii < children.length; ++ii) {
                printCollInNode(children[ii], box.split(descriDim, ii),
                        nodeLevel + 1);
            }
        }
    }
}
