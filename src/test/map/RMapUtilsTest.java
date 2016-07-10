package map;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RMapUtilsTest {

    @Test
    public void getTopRowIdxIfOrdIsShouldReturnTheAppropriateValue() {
        Assertions.assertThat(RMapUtils.getTopRowIdxIfOrdIs(20)).isEqualTo(0); // standard case.
        assertThat(RMapUtils.getTopRowIdxIfOrdIs(44)).isEqualTo(0); // limit before moving to another case.
        assertThat(RMapUtils.getTopRowIdxIfOrdIs(45)).isEqualTo(1); // moving to another case.
    }

    @Test
    public void getBottomRowIdxIfOrdIsShouldReturnTheAppropriateValue() {
        assertThat(RMapUtils.getBottomRowIdxIfOrdIs(20)).isEqualTo(0); // standard case.
        assertThat(RMapUtils.getBottomRowIdxIfOrdIs(29)).isEqualTo(0); // limit before moving to another case.
        assertThat(RMapUtils.getBottomRowIdxIfOrdIs(30)).isEqualTo(1); // moving to another case.

    }

    @Test
    public void getMostLeftRowIdxIfOrdIsShouldReturnTheAppropriateValue() {
        assertThat(RMapUtils.getMostLeftColIdxIfAbsIs(30)).isEqualTo(0); // standard case.
        assertThat(RMapUtils.getMostLeftColIdxIfAbsIs(44)).isEqualTo(0); // limit before moving to another case.
        assertThat(RMapUtils.getMostLeftColIdxIfAbsIs(45)).isEqualTo(1); // moving to another case.
    }

    @Test
    public void getMostRightRowIdxIfOrdIsShouldReturnTheAppropriateValue() {
        assertThat(RMapUtils.getMostRightColIdxIfAbsIs(30)).isEqualTo(1); // standard case.
        assertThat(RMapUtils.getMostRightColIdxIfAbsIs(45)).isEqualTo(1); // limit before moving to another case.
        assertThat(RMapUtils.getMostRightColIdxIfAbsIs(46)).isEqualTo(2); // moving to another case.
    }
}