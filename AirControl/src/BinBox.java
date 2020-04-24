/**
 * A box that is capable of splitting into smaller boxes. All dimensions of
 * BinBox are a power of two.
 * 
 * @author  Bimal Gaudel
 * 
 * @version Apr 24, 2020
 */
public class BinBox extends Box {
    /**
     * Construct BinBox. The origin of the BinBox is the point (0,0,0).
     * 
     * @param width The width of the BinBox per dimension. Width must be a power
     *              of two.
     */
    public BinBox(int width) {
        super(new int[] {0, 0, 0, width, width, width});
    }

    /**
     * Construct BinBox object out of origin specs and the widths specs.
     * 
     * @param orig
     *               [0] x co-ordinate of the origin
     *               [1] y co-ordinate of the origin
     *               [2] z co-ordinate of the origin
     * @param widths
     *               [0] x width of the box
     *               [1] y width of the box
     *               [2] z width of the box
     */
    protected BinBox(int[] orig, int[] width) {
        super(orig, width);
    }

    /**
     * Split the box in half accross an axis.
     * 
     * @param  axis      The Box.dimX, dimY or dimZ.
     * @param  whichHalf The halved box to get.
     *                   0 for the first half
     *                   1 for the second half.
     * 
     * @return           The half of the BinBox obtained by splitting accross @c
     *                   axis and
     *                   chosen by @c whichHalf.
     */
    public BinBox split(short axis, int whichHalf) {
        // split accross the axis
        int[] splitWidths = new int[Box.numDims];
        for (int ii = 0; ii < Box.numDims; ++ii) {
            splitWidths[ii] =
                    (ii == axis) ? getWidths()[ii] / 2 : getWidths()[ii];
        }

        // decide the origin based on whichHalf
        int[] splitOrig = new int[Box.numDims];
        for (int ii = 0; ii < Box.numDims; ++ii) {
            splitOrig[ii] = getOrig()[ii];
            splitOrig[ii] += (ii == axis) ? whichHalf * splitWidths[ii] : 0;
        }

        return new BinBox(splitOrig, splitWidths);
    }
}
