package uk.ac.cf.spring.nhs.Common.util;

public class BitmaskUtility {

    /**
     * Sets a specific bit to 1 in the given integer value.
     *
     * @param value     the integer value to modify
     * @param position  the position of the bit to set (1-indexed)
     * @return          the modified integer value with the bit set
     */
    public static int setBit(int value, int position) {
        return value | (1 << position - 1);
    }

    /**
     * Checks if a specific bit is set to 1 in the given integer value.
     *
     * @param value     the integer value to check
     * @param position  the position of the bit to check (1-indexed)
     * @return          true if the bit is set, false otherwise
     */
    public static boolean isBitSet(int value, int position) {
        return (value & (1 << position - 1)) != 0;
    }

}
