package map.ctrl;

import exceptions.OutOfMapBoundsException;
import map.MapPoint;
import org.assertj.core.api.WithAssertions;
import org.junit.Test;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import static images.ImagesLoader.IMAGE_SIZE;

public class NomadMethodsTest implements WithAssertions {

    private final int MAP_WIDTH = 20;
    private final int MAP_HEIGHT = 10;

    @Test
    public void getTopRowIdxIfOrdIsShouldReturnTheAppropriateValue() throws Exception {
        assertThat(CharacterMethods.getTopRowIdxIfOrdIs(10)).isEqualTo(-1); // negative  case.
        assertThat(CharacterMethods.getTopRowIdxIfOrdIs(14)).isEqualTo(-1); // limit before negative  case.
        assertThat(CharacterMethods.getTopRowIdxIfOrdIs(20)).isEqualTo(0); // standard case.
        assertThat(CharacterMethods.getTopRowIdxIfOrdIs(44)).isEqualTo(0); // limit before moving to another case.
        assertThat(CharacterMethods.getTopRowIdxIfOrdIs(45)).isEqualTo(1); // moving to another case.
    }

    @Test
    public void getBottomRowIdxIfOrdIsShouldReturnTheAppropriateValue() throws Exception {
        assertThat(CharacterMethods.getBottomRowIdxIfOrdIs(20)).isEqualTo(0); // standard case.
        assertThat(CharacterMethods.getBottomRowIdxIfOrdIs(29)).isEqualTo(0); // limit before moving to another case.
        assertThat(CharacterMethods.getBottomRowIdxIfOrdIs(30)).isEqualTo(1); // moving to another case.

    }

    @Test
    public void getMostLeftRowIdxIfOrdIsShouldReturnTheAppropriateValue() throws Exception {
        assertThat(CharacterMethods.getMostLeftColIdxIfAbsIs(10)).isEqualTo(-1); // negative  case.
        assertThat(CharacterMethods.getMostLeftColIdxIfAbsIs(14)).isEqualTo(-1); // limit before negative  case.
        assertThat(CharacterMethods.getMostLeftColIdxIfAbsIs(30)).isEqualTo(0); // standard case.
        assertThat(CharacterMethods.getMostLeftColIdxIfAbsIs(44)).isEqualTo(0); // limit before moving to another case.
        assertThat(CharacterMethods.getMostLeftColIdxIfAbsIs(45)).isEqualTo(1); // moving to another case.
    }

    @Test
    public void getMostRightRowIdxIfOrdIsShouldReturnTheAppropriateValue() throws Exception {
        assertThat(CharacterMethods.getMostRightColIdxIfAbsIs(30)).isEqualTo(1); // standard case.
        assertThat(CharacterMethods.getMostRightColIdxIfAbsIs(45)).isEqualTo(1); // limit before moving to another case.
        assertThat(CharacterMethods.getMostRightColIdxIfAbsIs(46)).isEqualTo(2); // moving to another case.
    }

    @Test
    public void isCharacterCrossingMapLimitShouldReturnTheAppropriateValue() throws Exception {
        
        /*
        compute the character limits according to the map dimensions.
        ex: ZeldaMap(20, 10) = W=600px * H=300px.
        - yChar < 15px ||
        - yChar > 299px ||
        - xChar < 15px ||
        - xChar > 685px should fail
        */

        int topMapLimit = IMAGE_SIZE / 2; // 15px.
        int bottomMapLimit = MAP_HEIGHT * IMAGE_SIZE - 1; // 299px.
        int leftMapLimit = IMAGE_SIZE / 2; // 15px.
        int rightMapLimit = MAP_WIDTH * IMAGE_SIZE - IMAGE_SIZE / 2; // 685px.

        // tests.
        for (int xChar = 0; xChar < MAP_WIDTH * IMAGE_SIZE; xChar++) {
            for (int yChar = 0; yChar < MAP_HEIGHT * IMAGE_SIZE; yChar++) {
                if ((xChar < leftMapLimit) || (xChar > rightMapLimit) ||
                        (yChar < topMapLimit) || (yChar > bottomMapLimit)) {
                    assertThat(CharacterMethods.isCharacterCrossingMapLimit(MAP_WIDTH, MAP_HEIGHT, xChar, yChar)).isTrue(); // crossing.
                } else {
                    assertThat(CharacterMethods.isCharacterCrossingMapLimit(MAP_WIDTH, MAP_HEIGHT, xChar, yChar)).isFalse(); // not crossing.
                }
            }
        }
    }

    @Test
    public void isCharacterCrossingObstacleShouldReturnTheAppropriateValue() throws Exception {
        MapPoint[][] mapPointMatrix = new MapPoint[MAP_HEIGHT][MAP_WIDTH];
        for (int rowIdx = 0; rowIdx < MAP_HEIGHT; rowIdx++) {
            for (int colIdx = 0; colIdx < MAP_WIDTH; colIdx++) {
                mapPointMatrix[rowIdx][colIdx] = new MapPoint(rowIdx, colIdx);
                mapPointMatrix[rowIdx][colIdx].setPathway(true);
            }
        }
        int obsRowIdx = 1;
        int obsColIdx = 2;
        mapPointMatrix[obsRowIdx][obsColIdx].setPathway(false);

        /*
        compute the character limits according to the obstacle position.
        ex: Obs(1, 2) -> x=60px, y=30px.
        - yChar > 29px &&
        - yChar < 75px &&
        - xChar > 45px &&
        - xChar < 105px should fail
        */

        int topObsLimit = obsRowIdx * IMAGE_SIZE - 1; // 29px.
        int bottomObsLimit = (obsRowIdx + 1) * IMAGE_SIZE + IMAGE_SIZE / 2; // 75px.
        int leftObsLimit = obsColIdx * IMAGE_SIZE - IMAGE_SIZE / 2; // 45px.
        int rightObsLimit = (obsColIdx + 1) * IMAGE_SIZE + IMAGE_SIZE / 2; // 105px.

        // test.
        for (int xChar = 0; xChar < MAP_WIDTH * IMAGE_SIZE; xChar++) {
            for (int yChar = 0; yChar < MAP_HEIGHT * IMAGE_SIZE; yChar++) {
                try {
                    if ((xChar > leftObsLimit) && (xChar < rightObsLimit) &&
                            (yChar > topObsLimit) && (yChar < bottomObsLimit)) {

                        // assert that the character is crossing an obstacle.
                        assertThat(CharacterMethods.isCharacterCrossingObstacle(mapPointMatrix, MAP_WIDTH, MAP_HEIGHT, xChar, yChar)).isTrue();
                    } else {

                        // assert that the character is not crossing an obstacle.
                        assertThat(CharacterMethods.isCharacterCrossingObstacle(mapPointMatrix, MAP_WIDTH, MAP_HEIGHT, xChar, yChar)).isFalse();
                    }
                } catch (OutOfMapBoundsException e) {

                    // assert that an exception has been thrown because the character is crossing the map limits.
                    assertThat(CharacterMethods.isCharacterCrossingMapLimit(MAP_WIDTH, MAP_HEIGHT, xChar, yChar)).isTrue();
                }
            }
        }
    }

    @Test
    public void isCharacterBurningShouldReturnTheAppropriateValue() throws Exception {
        MapPoint[][] mapPointMatrix = new MapPoint[MAP_HEIGHT][MAP_WIDTH];
        for (int rowIdx = 0; rowIdx < MAP_HEIGHT; rowIdx++) {
            for (int colIdx = 0; colIdx < MAP_WIDTH; colIdx++) {
                mapPointMatrix[rowIdx][colIdx] = new MapPoint(rowIdx, colIdx);
                mapPointMatrix[rowIdx][colIdx].setPathway(true);
            }
        }
        int flameRowIdx = 1;
        int flameColIdx = 2;
        mapPointMatrix[flameRowIdx][flameColIdx].addFlame();

        /*
        compute the character limits according to the flame position.
        ex: Flame(1, 2) -> x=60px, y=30px.
        - yChar > 29px &&
        - yChar < 75px &&
        - xChar > 45px &&
        - xChar < 105px should fail
        */

        int topFlameLimit = flameRowIdx * IMAGE_SIZE - 1; // 29px.
        int bottomFlameLimit = (flameRowIdx + 1) * IMAGE_SIZE + IMAGE_SIZE / 2; // 75px.
        int leftFlameLimit = flameColIdx * IMAGE_SIZE - IMAGE_SIZE / 2; // 45px.
        int rightFlameLimit = (flameColIdx + 1) * IMAGE_SIZE + IMAGE_SIZE / 2; // 105px.

        // test.
        for (int xChar = 0; xChar < MAP_WIDTH * IMAGE_SIZE; xChar++) {
            for (int yChar = 0; yChar < MAP_HEIGHT * IMAGE_SIZE; yChar++) {
                try {
                    if ((xChar > leftFlameLimit) && (xChar < rightFlameLimit) &&
                            (yChar > topFlameLimit) && (yChar < bottomFlameLimit)) {

                        // assert that the character is crossing a flame.
                        assertThat(CharacterMethods.isCharacterBurning(mapPointMatrix, MAP_WIDTH, MAP_HEIGHT, xChar, yChar)).isTrue();
                    } else {

                        // assert that the character is not crossing a flame.
                        assertThat(CharacterMethods.isCharacterBurning(mapPointMatrix, MAP_WIDTH, MAP_HEIGHT, xChar, yChar)).isFalse();
                    }
                } catch (OutOfMapBoundsException e) {

                    // assert that an exception has been thrown because the character is crossing the map limits.
                    assertThat(CharacterMethods.isCharacterCrossingMapLimit(MAP_WIDTH, MAP_HEIGHT, xChar, yChar)).isTrue();
                }
            }
        }
    }

    @Test
    public void isCharacterCrossingBombShouldReturnTheAppropriateValue() throws Exception {
        MapPoint[][] mapPointMatrix = new MapPoint[MAP_HEIGHT][MAP_WIDTH];
        for (int rowIdx = 0; rowIdx < MAP_HEIGHT; rowIdx++) {
            for (int colIdx = 0; colIdx < MAP_WIDTH; colIdx++) {
                mapPointMatrix[rowIdx][colIdx] = new MapPoint(rowIdx, colIdx);
                mapPointMatrix[rowIdx][colIdx].setPathway(true);
            }
        }
        int bombRowIdx = 1;
        int bombColIdx = 2;
        mapPointMatrix[bombRowIdx][bombColIdx].setBombing(true);

        /*
        compute the character limits according to the bomb position.
        ex: Bomb(1, 2) -> x=60px, y=30px.
        - yChar == 30px && key is DOWN should fail
        - yChar == 74px && key is UP should fail
        - xChar == 46px && key is RIGHT should fail
        - xChar == 104px  key is LEFT should fail
        */

        int topBombLimit = bombRowIdx * IMAGE_SIZE - 1; // 29px.
        int bottomBombLimit = (bombRowIdx + 1) * IMAGE_SIZE + IMAGE_SIZE / 2; // 75px.
        int leftBombLimit = bombColIdx * IMAGE_SIZE - IMAGE_SIZE / 2; // 45px.
        int rightBombLimit = (bombColIdx + 1) * IMAGE_SIZE + IMAGE_SIZE / 2; // 105px.

        // create the list of KeyEvent.
        List<Integer> keyEventList = new ArrayList<>();
        keyEventList.add(KeyEvent.VK_UP);
        keyEventList.add(KeyEvent.VK_DOWN);
        keyEventList.add(KeyEvent.VK_LEFT);
        keyEventList.add(KeyEvent.VK_RIGHT);

        // test.
        for (Integer keyEvent : keyEventList) {
            for (int xChar = 0; xChar < MAP_WIDTH * IMAGE_SIZE; xChar++) {
                for (int yChar = 0; yChar < MAP_HEIGHT * IMAGE_SIZE; yChar++) {
                    try {
                        // assert that the character is crossing a bomb.
                        if (((xChar > leftBombLimit) && (xChar < rightBombLimit) &&
                                (yChar == topBombLimit + 1) && keyEvent == KeyEvent.VK_DOWN) ||
                                ((xChar > leftBombLimit) && (xChar < rightBombLimit) &&
                                        (yChar == bottomBombLimit - 1) && keyEvent == KeyEvent.VK_UP) ||
                                ((yChar > topBombLimit) && (yChar < bottomBombLimit) &&
                                        (xChar == leftBombLimit + 1) && keyEvent == KeyEvent.VK_RIGHT) ||
                                ((yChar > topBombLimit) && (yChar < bottomBombLimit) &&
                                        (xChar == rightBombLimit - 1) && keyEvent == KeyEvent.VK_LEFT)) {
                            assertThat(CharacterMethods.isCharacterCrossingBomb(mapPointMatrix, MAP_WIDTH, MAP_HEIGHT, xChar, yChar, keyEvent)).isTrue();
                        } else {

                            // assert that the character is not crossing a flame.
                            assertThat(CharacterMethods.isCharacterCrossingBomb(mapPointMatrix, MAP_WIDTH, MAP_HEIGHT, xChar, yChar, keyEvent)).isFalse();
                        }
                    } catch (OutOfMapBoundsException e) {

                        // assert that an exception has been thrown because the character is crossing the map limits.
                        assertThat(CharacterMethods.isCharacterCrossingMapLimit(MAP_WIDTH, MAP_HEIGHT, xChar, yChar)).isTrue();
                    }
                }
            }
        }
    }
}