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


    /**
     * The dimension in one direction for the 3d world space applicable to this
     * BinTree object.
     */
    private final int worldSize;


    /** The BinBox object for the root node. */
    private final BinBox worldBinBox;


    /**
     * Construct a bin tree.
     * 
     * @param worldSize The dimension in one direction for the 3d space
     *                  applicable to this BinTree object.
     */
    public BinTree(int worldSize) {
        this.worldSize = worldSize;
        worldBinBox = new BinBox(this.worldSize);
        root = fly;
    }


    /**
     * Insert an AirObject to the tree.
     * 
     * @param airObject The AirObject to be inserted.
     */
    public void insert(AirObject airObject) {
        root = root.insert(airObject, worldBinBox, 0);
    }


    /** Print the BinTree. */
    public void dumpTree() {
        int nodesPrinted = dumpNode(root, "");
        System.out.println(nodesPrinted + " bintree nodes printed");
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
                return 1;
            }
            else {
                Scanner sc = new Scanner(node.toString());
                while (sc.hasNext()) {
                    System.out.println(leftPad + sc.nextLine());
                }
                sc.close();
                return 1;
            }
        }
        else {
            // printing an internal node
            System.out.println(leftPad + "I");
            int nodesPrinted = 1; // just printed this internal node.
            BinTreeNodeInternal intrnl = (BinTreeNodeInternal) node;
            for (BinTreeNode child : intrnl.getChildren()) {
                nodesPrinted += dumpNode(child, leftPad + "  ");
            }
            return nodesPrinted;
        }
    }
}
