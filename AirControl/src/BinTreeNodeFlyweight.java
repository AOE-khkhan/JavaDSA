/**
 * Flyweight node to represent empty nodes for the BinTree class.
 * 
 * 
 * @author  Bimal Gaudel
 * 
 * @version Apr 22, 2020
 */
public final class BinTreeNodeFlyweight extends BinTreeNodeLeaf {

    /** The singleton flyweight instance. */
    private static BinTreeNodeFlyweight SINGLETON_FLYWEIGHT =
            new BinTreeNodeFlyweight();

    /** Default constructor made private for flyweight singleton. */
    private BinTreeNodeFlyweight() {}

    /**
     * Get reference to the singleton flyweight object.
     * 
     * @return The singleton flyweight instance.
     */
    public static BinTreeNodeFlyweight getInstance() {
        return SINGLETON_FLYWEIGHT;
    }
}
