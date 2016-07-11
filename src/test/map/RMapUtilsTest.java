package map;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import static images.ImagesLoader.IMAGE_SIZE;
import static org.assertj.core.api.Assertions.assertThat;

public class RMapUtilsTest {

    private final int MAP_WIDTH = 20;
    private final int MAP_HEIGHT = 10;

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

    @Test
    public void isCharacterCrossingMapLimitShouldReturnTheAppropriateValue() throws Exception {
        RMap rMap = new RMap(MAP_WIDTH, MAP_HEIGHT, 0, 0);

        // define map limits.
        int topLimit = IMAGE_SIZE / 2;
        int bottomLimit = MAP_HEIGHT * IMAGE_SIZE;
        int leftLimit = IMAGE_SIZE / 2;
        int rightLimit = MAP_WIDTH * IMAGE_SIZE - IMAGE_SIZE / 2;

        // top/left corner.
        assertThat(RMapUtils.isCharacterCrossingMapLimit(rMap, leftLimit, topLimit)).isFalse();
        assertThat(RMapUtils.isCharacterCrossingMapLimit(rMap, leftLimit - 1, topLimit)).isTrue();
        assertThat(RMapUtils.isCharacterCrossingMapLimit(rMap, leftLimit, topLimit - 1)).isTrue();

        // top/right corner.
        assertThat(RMapUtils.isCharacterCrossingMapLimit(rMap, rightLimit, topLimit)).isFalse();
        assertThat(RMapUtils.isCharacterCrossingMapLimit(rMap, rightLimit + 1, topLimit)).isTrue();
        assertThat(RMapUtils.isCharacterCrossingMapLimit(rMap, rightLimit, topLimit - 1)).isTrue();

        // bottom/right corner.
        assertThat(RMapUtils.isCharacterCrossingMapLimit(rMap, rightLimit, bottomLimit)).isFalse();
        assertThat(RMapUtils.isCharacterCrossingMapLimit(rMap, rightLimit + 1, bottomLimit)).isTrue();
        assertThat(RMapUtils.isCharacterCrossingMapLimit(rMap, rightLimit, bottomLimit + 1)).isTrue();

        // bottom/left corner.
        assertThat(RMapUtils.isCharacterCrossingMapLimit(rMap, leftLimit, bottomLimit)).isFalse();
        assertThat(RMapUtils.isCharacterCrossingMapLimit(rMap, leftLimit - 1, bottomLimit)).isTrue();
        assertThat(RMapUtils.isCharacterCrossingMapLimit(rMap, leftLimit, bottomLimit + 1)).isTrue();
    }

}