package uk.ac.cf.spring.nhs.Common.util;

public class BitmaskUtility {

    // Maximum number of days in a month
    public static final int MAX_DAYS_IN_MONTH = 31;

    /**
     * Sets the bit at the specified position (1-based index).
     * 
     * @param bitmask  The current bitmask.
     * @param position The position of the bit to set (1-based index).
     * @return The updated bitmask with the bit set.
     * @throws IllegalArgumentException if the position is not between 1 and 31.
     */
    public static int setBit(int bitmask, int position) {
        if (position < 1 || position > MAX_DAYS_IN_MONTH) {
            throw new IllegalArgumentException("Day position must be between 1 and 31");
        }
        return bitmask | (1 << (position - 1));
    }

    /**
     * Checks if the bit at the specified position (1-based index) is set.
     * 
     * @param bitmask  The bitmask.
     * @param position The position of the bit to check (1-based index).
     * @return true if the bit is set, false otherwise.
     * @throws IllegalArgumentException if the position is not between 1 and 31.
     */
    public static boolean isBitSet(int bitmask, int position) {
        if (position < 1 || position > MAX_DAYS_IN_MONTH) {
            throw new IllegalArgumentException("Day position must be between 1 and 31");
        }
        return (bitmask & (1 << (position - 1))) != 0;
    }

    /**
     * Clears the bit at the specified position (1-based index).
     * 
     * @param bitmask  The current bitmask.
     * @param position The position of the bit to clear (1-based index).
     * @return The updated bitmask with the bit cleared.
     * @throws IllegalArgumentException if the position is not between 1 and 31.
     */
    public static int clearBit(int bitmask, int position) {
        if (position < 1 || position > MAX_DAYS_IN_MONTH) {
            throw new IllegalArgumentException("Day position must be between 1 and 31");
        }
        return bitmask & ~(1 << (position - 1));
    }

    /**
     * Resets the bitmask for a new month, clearing all bits.
     * 
     * @return The reset bitmask (all bits set to 0).
     */
    public static int resetBitmask() {
        return 0; // All bits reset
    }

}
