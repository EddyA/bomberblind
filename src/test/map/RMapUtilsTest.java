package map;

import org.junit.Test;

import static images.ImagesLoader.IMAGE_SIZE;
import static org.assertj.core.api.Assertions.assertThat;

public class RMapUtilsTest {

    private final int MAP_WIDTH = 20;
    private final int MAP_HEIGHT = 10;

    @Test
    public void getTopRowIdxIfOrdIsShouldReturnTheAppropriateValue() {
        assertThat(RMapUtils.getTopRowIdxIfOrdIs(10)).isEqualTo(-1); // negative  case.
        assertThat(RMapUtils.getTopRowIdxIfOrdIs(14)).isEqualTo(-1); // limit before negative  case.
        assertThat(RMapUtils.getTopRowIdxIfOrdIs(20)).isEqualTo(0); // standard case.
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
        assertThat(RMapUtils.getMostLeftColIdxIfAbsIs(10)).isEqualTo(-1); // negative  case.
        assertThat(RMapUtils.getMostLeftColIdxIfAbsIs(14)).isEqualTo(-1); // limit before negative  case.
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

        // set map.
        RMap rMap = new RMap(MAP_WIDTH, MAP_HEIGHT, 0, 0);

        // compute character map limits according to the character gabarit.
        // ex: RMap(20, 10) = W=600px * H=300px.
        // - yChar < 15px ||
        // - yChar > 299px ||
        // - xChar < 15px ||
        // - xChar > 685px should fail

        int topMapLimit = IMAGE_SIZE / 2; // 15px.
        int bottomMapLimit = MAP_HEIGHT * IMAGE_SIZE - 1; // 299px.
        int leftMapLimit = IMAGE_SIZE / 2; // 15px.
        int rightMapLimit = MAP_WIDTH * IMAGE_SIZE - IMAGE_SIZE / 2; // 685px.

        // tests.
        for (int xChar = 0; xChar < MAP_WIDTH * IMAGE_SIZE; xChar++) {
            for (int yChar = 0; yChar < MAP_HEIGHT * IMAGE_SIZE; yChar++) {
                if ((xChar < leftMapLimit) || (xChar > rightMapLimit) ||
                        (yChar < topMapLimit) || (yChar > bottomMapLimit)) {
                    assertThat(RMapUtils.isCharacterCrossingMapLimit(rMap, xChar, yChar)).isTrue(); // crossing.
                } else {
                    assertThat(RMapUtils.isCharacterCrossingMapLimit(rMap, xChar, yChar)).isFalse(); // not crossing.
                }
            }
        }
    }

    @Test
    public void isCharacterCrossingObstacleShouldReturnTheAppropriateValue() throws Exception {

        // set the map.
        RMap rMap = new RMap(MAP_WIDTH, MAP_HEIGHT, 0, 0);
        for (int rowIdx = 0; rowIdx < MAP_HEIGHT; rowIdx++) {
            for (int colIdx = 0; colIdx < MAP_WIDTH; colIdx++) {
                rMap.myMap[rowIdx][colIdx].setPathway(true);
            }
        }
        int obsRowIdx = 1;
        int obsColIdx = 2;
        rMap.myMap[obsRowIdx][obsColIdx].setPathway(false);

        // compute character map limits according to the character gabarit.
        // ex: Obs(1, 2) -> x=60px, y=30px.
        // - yChar > 29px &&
        // - yChar < 75px &&
        // - xChar > 45px &&
        // - xChar < 105px should fail

        int topObsLimit = obsRowIdx * IMAGE_SIZE - 1; // 30px.
        int bottomObsLimit = (obsRowIdx + 1) * IMAGE_SIZE + IMAGE_SIZE / 2; // 60px.
        int leftObsLimit = obsColIdx * IMAGE_SIZE - IMAGE_SIZE / 2; // 45px.
        int rightObsLimit = (obsColIdx + 1) * IMAGE_SIZE + IMAGE_SIZE / 2; // 105px.

        // test.
        for (int xChar = 0; xChar < MAP_WIDTH * IMAGE_SIZE; xChar++) {
            for (int yChar = 0; yChar < MAP_HEIGHT * IMAGE_SIZE; yChar++) {
                if (!RMapUtils.isCharacterCrossingMapLimit(rMap, xChar, yChar)) // should be called to only get obstacle.
                    if ((xChar > leftObsLimit) && (xChar < rightObsLimit) &&
                            (yChar > topObsLimit) && (yChar < bottomObsLimit)) {
                        assertThat(RMapUtils.isCharacterCrossingObstacle(rMap, xChar, yChar)).isTrue(); // crossing.
                    } else {
                        assertThat(RMapUtils.isCharacterCrossingObstacle(rMap, xChar, yChar)).isFalse(); // not crossing.
                    }
            }
        }
    }
}