package uk.ac.cf.spring.nhs.Common.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

public class BitmaskUtilityUnitTests {

    /**
     * Tests the setBit method of the BitmaskUtility class with valid positions.
     *
     * @throws none
     * @return none
     */
    @Test
    public void testSetBit_ValidPosition() {
        int bitmask = 0;
        int updatedBitmask = BitmaskUtility.setBit(bitmask, 1);
        assertThat(updatedBitmask).isEqualTo(1);

        updatedBitmask = BitmaskUtility.setBit(bitmask, 31);
        assertThat(updatedBitmask).isEqualTo(1 << 30); // 1073741824
    }

    /**
     * Tests the setBit method of the BitmaskUtility class with invalid positions.
     *
     * @throws none
     * @return none
     */
    @Test
    public void testSetBit_InvalidPosition() {
        assertThatThrownBy(() -> BitmaskUtility.setBit(0, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Day position must be between 1 and 31");

        assertThatThrownBy(() -> BitmaskUtility.setBit(0, 32))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Day position must be between 1 and 31");
    }

    /**
     * Tests if a bit is set in the bitmask.
     *
     * @throws none
     * @return none
     */
    @Test
    public void testIsBitSet_BitIsSet() {
        int bitmask = BitmaskUtility.setBit(0, 5);
        assertThat(BitmaskUtility.isBitSet(bitmask, 5)).isTrue();
    }

    /**
     * Tests if a bit is not set in the bitmask.
     *
     * @throws none
     * @return none
     */
    @Test
    public void testIsBitSet_BitIsNotSet() {
        int bitmask = BitmaskUtility.setBit(0, 5);
        assertThat(BitmaskUtility.isBitSet(bitmask, 4)).isFalse();
    }

    /**
     * Tests the isBitSet method of the BitmaskUtility class with invalid positions.
     *
     * @throws none
     * @return none
     */
    @Test
    public void testIsBitSet_InvalidPosition() {
        assertThatThrownBy(() -> BitmaskUtility.isBitSet(0, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Day position must be between 1 and 31");

        assertThatThrownBy(() -> BitmaskUtility.isBitSet(0, 32))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Day position must be between 1 and 31");
    }

    /**
     * Tests the clearBit method of the BitmaskUtility class with a valid position.
     *
     * @throws none
     * @return none
     */
    @Test
    public void testClearBit_ValidPosition() {
        int bitmask = BitmaskUtility.setBit(0, 5);
        int updatedBitmask = BitmaskUtility.clearBit(bitmask, 5);
        assertThat(updatedBitmask).isEqualTo(0);
    }

    /**
     * Tests the clearBit method of the BitmaskUtility class with invalid positions.
     *
     * @throws none
     * @return none
     */
    @Test
    public void testClearBit_InvalidPosition() {
        assertThatThrownBy(() -> BitmaskUtility.clearBit(0, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Day position must be between 1 and 31");

        assertThatThrownBy(() -> BitmaskUtility.clearBit(0, 32))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Day position must be between 1 and 31");
    }

    /**
     * Tests the resetBitmask method of the BitmaskUtility class.
     *
     * @throws none
     * @return none
     */
    @Test
    public void testResetBitmask() {
        int resetBitmask = BitmaskUtility.resetBitmask();
        assertThat(resetBitmask).isEqualTo(0);
    }

    /**
     * Tests setting multiple bits in a bitmask using the BitmaskUtility class.
     *
     * @throws none
     * @return none
     */
    @Test
    public void testSetMultipleBits() {
        int bitmask = 0;
        bitmask = BitmaskUtility.setBit(bitmask, 1);
        bitmask = BitmaskUtility.setBit(bitmask, 15);
        bitmask = BitmaskUtility.setBit(bitmask, 31);

        assertThat(BitmaskUtility.isBitSet(bitmask, 1)).isTrue();
        assertThat(BitmaskUtility.isBitSet(bitmask, 15)).isTrue();
        assertThat(BitmaskUtility.isBitSet(bitmask, 31)).isTrue();
        assertThat(BitmaskUtility.isBitSet(bitmask, 2)).isFalse();
    }

    /**
     * Tests clearing multiple bits in a bitmask using the BitmaskUtility class.
     *
     * @throws none
     * @return none
     */
    @Test
    public void testClearMultipleBits() {
        int bitmask = BitmaskUtility.setBit(0, 1);
        bitmask = BitmaskUtility.setBit(bitmask, 15);
        bitmask = BitmaskUtility.setBit(bitmask, 31);

        bitmask = BitmaskUtility.clearBit(bitmask, 1);
        bitmask = BitmaskUtility.clearBit(bitmask, 15);

        assertThat(BitmaskUtility.isBitSet(bitmask, 1)).isFalse();
        assertThat(BitmaskUtility.isBitSet(bitmask, 15)).isFalse();
        assertThat(BitmaskUtility.isBitSet(bitmask, 31)).isTrue();
    }
}
