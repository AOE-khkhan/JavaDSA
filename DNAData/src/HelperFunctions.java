/**
 * A collection of small helper functions.
 * 
 * @author  Bimal Gaudel
 * 
 * @version Mar 30, 2020
 */
public class HelperFunctions {
    /**
     * Get the log based two.
     * E.g.
     * getLog2(4) = 2
     * getLog2(31) = 4
     * getLog2(32) = 5
     * 
     * @param  num The positive number to calculate log2 of.
     * 
     * @return     The number which is to be raised to the power of two.
     */
    public static int getLog2(int num) {
        int result = -1;
        while (num != 0) {
            num = num >> 1;
            ++result;
        }
        return result;
    }

    /**
     * Get the value when number 2 is raised to the power of num.
     * E.g.
     * getPower2(0) = 1
     * getPower2(2) = 4
     * getPower2(10) = 1024
     * 
     * @param  num The positive number 2 is raised to the power of.
     * 
     * @return     The number resulting from raising 2 to the power of num.
     */
    public static int getPower2(int num) {
        return 1 << num;
    }

    /**
     * Check if a number is a positive power of two.
     * 
     * @param  num An integer.
     * 
     * @return     True if num is a positive power of two.
     */
    public static boolean isPowerOf2(int num) {
        if (num < 1) {
            return false;
        }
        while (num != 1) {
            if (((num / 2) * 2) == num) {
                num = num / 2;
            }
            else {
                return false;
            }
        }
        return true;
    }
}
