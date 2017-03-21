package map.ctrl;

import static images.ImagesLoader.IMAGE_SIZE;
import static map.ctrl.NomadMethods.isNomadBlockedOffByMutableObstacle;
import static utils.Direction.DIRECTION_EAST;
import static utils.Direction.DIRECTION_NORTH;
import static utils.Direction.DIRECTION_SOUTH;
import static utils.Direction.DIRECTION_WEST;
import static utils.Direction.convertKeyEventToDirection;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.WithAssertions;
import org.junit.Test;

import map.MapPoint;
import utils.Direction;

public class NomadMethodsTest implements WithAssertions {

    private final int MAP_WIDTH = 20;
    private final int MAP_HEIGHT = 10;

    @Test
    public void isNomadCrossingMapLimitShouldReturnExpectedValue() throws Exception {
        
        /*
        compute the nomad limits according to the map dimensions.
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
                    assertThat(NomadMethods.isNomadCrossingMapLimit(MAP_WIDTH, MAP_HEIGHT, xChar, yChar)).isTrue(); // crossing.
                } else {
                    assertThat(NomadMethods.isNomadCrossingMapLimit(MAP_WIDTH, MAP_HEIGHT, xChar, yChar)).isFalse(); // not
                    // crossing.
                }
            }
        }
    }

    @Test
    public void isNomadCrossingObstacleShouldReturnExpectedValue() throws Exception {
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
        compute the nomad limits according to the obstacle position.
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

                        // assert that the nomad is crossing an obstacle.
                        assertThat(NomadMethods.isNomadCrossingObstacle(mapPointMatrix, xChar, yChar)).isTrue();
                    } else {

                        // assert that the nomad is not crossing an obstacle.
                        assertThat(NomadMethods.isNomadCrossingObstacle(mapPointMatrix, xChar, yChar)).isFalse();
                    }
                } catch (Exception e) {

                    // assert that an exception has been thrown because the nomad is crossing the map limits.
                    assertThat(NomadMethods.isNomadCrossingMapLimit(MAP_WIDTH, MAP_HEIGHT, xChar, yChar)).isTrue();
                }
            }
        }
    }

    @Test
    public void isNomadCrossingMutableShouldReturnExpectedValue() throws Exception {
        MapPoint[][] mapPointMatrix = new MapPoint[MAP_HEIGHT][MAP_WIDTH];
        for (int rowIdx = 0; rowIdx < MAP_HEIGHT; rowIdx++) {
            for (int colIdx = 0; colIdx < MAP_WIDTH; colIdx++) {
                mapPointMatrix[rowIdx][colIdx] = new MapPoint(rowIdx, colIdx);
                mapPointMatrix[rowIdx][colIdx].setPathway(true);
            }
        }
        int mutableRowIdx = 1;
        int mutableColIdx = 2;
        mapPointMatrix[mutableRowIdx][mutableColIdx].setMutable(true);

        /*
        compute the nomad limits according to the mutable case position.
        ex: Mutable(1, 2) -> x=60px, y=30px.
        - yChar > 29px &&
        - yChar < 75px &&
        - xChar > 45px &&
        - xChar < 105px should fail
        */

        int topMutableLimit = mutableRowIdx * IMAGE_SIZE - 1; // 29px.
        int bottomMutableLimit = (mutableRowIdx + 1) * IMAGE_SIZE + IMAGE_SIZE / 2; // 75px.
        int leftMutableLimit = mutableColIdx * IMAGE_SIZE - IMAGE_SIZE / 2; // 45px.
        int rightMutableLimit = (mutableColIdx + 1) * IMAGE_SIZE + IMAGE_SIZE / 2; // 105px.

        // test.
        for (int xChar = 0; xChar < MAP_WIDTH * IMAGE_SIZE; xChar++) {
            for (int yChar = 0; yChar < MAP_HEIGHT * IMAGE_SIZE; yChar++) {
                try {
                    if ((xChar > leftMutableLimit) && (xChar < rightMutableLimit) &&
                            (yChar > topMutableLimit) && (yChar < bottomMutableLimit)) {

                        // assert that the nomad is crossing a mutable.
                        assertThat(NomadMethods.isNomadCrossingMutableObstacle(mapPointMatrix, xChar, yChar)).isTrue();
                    } else {

                        // assert that the nomad is not crossing a mutable.
                        assertThat(NomadMethods.isNomadCrossingMutableObstacle(mapPointMatrix, xChar, yChar)).isFalse();
                    }
                } catch (Exception e) {

                    // assert that an exception has been thrown because the nomad is crossing the map limits.
                    assertThat(NomadMethods.isNomadCrossingMapLimit(MAP_WIDTH, MAP_HEIGHT, xChar, yChar)).isTrue();
                }
            }
        }
    }

    @Test
    public void isNomadBlockedOffByMutableShouldReturnExpectedValue() throws Exception {
        MapPoint[][] mapPointMatrix = new MapPoint[MAP_HEIGHT][MAP_WIDTH];
        for (int rowIdx = 0; rowIdx < MAP_HEIGHT; rowIdx++) {
            for (int colIdx = 0; colIdx < MAP_WIDTH; colIdx++) {
                mapPointMatrix[rowIdx][colIdx] = new MapPoint(rowIdx, colIdx);
                mapPointMatrix[rowIdx][colIdx].setPathway(true);
            }
        }
        int mutableRowIdx = 1;
        int mutableColIdx = 2;
        mapPointMatrix[mutableRowIdx][mutableColIdx].setMutable(true);

        /*
         * compute the nomad limits according to the mutable case.
         * ex: Mutable(1, 2) -> x=60px, y=30px.
         * - yChar > 29px &&
         * - yChar < 75px &&
         * - xChar > 45px &&
         * - xChar < 105px should fail
         */

        int topMutableLimit = mutableRowIdx * IMAGE_SIZE - 1; // 29px.
        int bottomMutableLimit = (mutableRowIdx + 1) * IMAGE_SIZE + IMAGE_SIZE / 2; // 75px.
        int leftMutableLimit = mutableColIdx * IMAGE_SIZE - IMAGE_SIZE / 2; // 45px.
        int rightMutableLimit = (mutableColIdx + 1) * IMAGE_SIZE + IMAGE_SIZE / 2; // 105px.

        MapPoint blockingMutable;

        // going toward south.
        // - 2 pixels before crossing.
        blockingMutable = isNomadBlockedOffByMutableObstacle(mapPointMatrix, MAP_WIDTH, MAP_HEIGHT,
                mutableColIdx * IMAGE_SIZE, topMutableLimit - 1, DIRECTION_SOUTH);
        assertThat(blockingMutable).isNull();
        // - 1 pixel before crossing.
        blockingMutable = isNomadBlockedOffByMutableObstacle(mapPointMatrix, MAP_WIDTH, MAP_HEIGHT,
                mutableColIdx * IMAGE_SIZE, topMutableLimit, DIRECTION_SOUTH);
        assertThat(blockingMutable).isEqualTo(mapPointMatrix[mutableRowIdx][mutableColIdx]);

        // going toward north.
        // - 2 pixels before crossing.
        blockingMutable = isNomadBlockedOffByMutableObstacle(mapPointMatrix, MAP_WIDTH, MAP_HEIGHT,
                mutableColIdx * IMAGE_SIZE, bottomMutableLimit + 1, DIRECTION_NORTH);
        assertThat(blockingMutable).isNull();
        // - 1 pixel before crossing.
        blockingMutable = isNomadBlockedOffByMutableObstacle(mapPointMatrix, MAP_WIDTH, MAP_HEIGHT,
                mutableColIdx * IMAGE_SIZE, bottomMutableLimit, DIRECTION_NORTH);
        assertThat(blockingMutable).isEqualTo(mapPointMatrix[mutableRowIdx][mutableColIdx]);

        // going toward west.
        // - 2 pixels before crossing.
        blockingMutable = isNomadBlockedOffByMutableObstacle(mapPointMatrix, MAP_WIDTH, MAP_HEIGHT,
                rightMutableLimit + 1, mutableRowIdx * IMAGE_SIZE, DIRECTION_WEST);
        assertThat(blockingMutable).isNull();
        // - 1 pixel before crossing.
        blockingMutable = isNomadBlockedOffByMutableObstacle(mapPointMatrix, MAP_WIDTH, MAP_HEIGHT,
                rightMutableLimit, mutableRowIdx * IMAGE_SIZE, DIRECTION_WEST);
        assertThat(blockingMutable).isEqualTo(mapPointMatrix[mutableRowIdx][mutableColIdx]);

        // going toward east.
        // - 2 pixels before crossing.
        blockingMutable = isNomadBlockedOffByMutableObstacle(mapPointMatrix, MAP_WIDTH, MAP_HEIGHT,
                leftMutableLimit - 1, mutableRowIdx * IMAGE_SIZE, DIRECTION_EAST);
        assertThat(blockingMutable).isNull();
        // - 1 pixel before crossing.
        blockingMutable = isNomadBlockedOffByMutableObstacle(mapPointMatrix, MAP_WIDTH, MAP_HEIGHT,
                leftMutableLimit, mutableRowIdx * IMAGE_SIZE, DIRECTION_EAST);
        assertThat(blockingMutable).isEqualTo(mapPointMatrix[mutableRowIdx][mutableColIdx]);
    }

    @Test
    public void isNomadBurningShouldReturnExpectedValue() throws Exception {
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
        compute the nomad limits according to the flame position.
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

                        // assert that the nomad is crossing a flame.
                        assertThat(NomadMethods.isNomadBurning(mapPointMatrix, xChar, yChar)).isTrue();
                    } else {

                        // assert that the nomad is not crossing a flame.
                        assertThat(NomadMethods.isNomadBurning(mapPointMatrix, xChar, yChar)).isFalse();
                    }
                } catch (Exception e) {

                    // assert that an exception has been thrown because the nomad is crossing the map limits.
                    assertThat(NomadMethods.isNomadCrossingMapLimit(MAP_WIDTH, MAP_HEIGHT, xChar, yChar)).isTrue();
                }
            }
        }
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void isNomadCrossingBombShouldReturnExpectedValue() throws Exception {
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
        compute the nomad limits according to the bomb position.
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
            Direction direction = convertKeyEventToDirection(keyEvent);
            for (int xChar = 0; xChar < MAP_WIDTH * IMAGE_SIZE; xChar++) {
                for (int yChar = 0; yChar < MAP_HEIGHT * IMAGE_SIZE; yChar++) {
                    try {
                        // assert that the nomad is crossing a bomb.
                        if (((xChar > leftBombLimit) && (xChar < rightBombLimit) &&
                                (yChar == topBombLimit + 1) && direction == DIRECTION_SOUTH) ||
                                ((xChar > leftBombLimit) && (xChar < rightBombLimit) &&
                                        (yChar == bottomBombLimit - 1) && direction == DIRECTION_NORTH) ||
                                ((yChar > topBombLimit) && (yChar < bottomBombLimit) &&
                                        (xChar == leftBombLimit + 1) && direction == DIRECTION_EAST) ||
                                ((yChar > topBombLimit) && (yChar < bottomBombLimit) &&
                                        (xChar == rightBombLimit - 1) && direction == DIRECTION_WEST)) {
                            assertThat(NomadMethods.isNomadCrossingBomb(mapPointMatrix, xChar, yChar, direction))
                                    .isTrue();
                        } else {

                            // assert that the nomad is not crossing a flame.
                            assertThat(NomadMethods.isNomadCrossingBomb(mapPointMatrix, xChar, yChar, direction))
                                    .isFalse();
                        }
                    } catch (Exception e) {

                        // assert that an exception has been thrown because the nomad is crossing the map limits.
                        assertThat(NomadMethods.isNomadCrossingMapLimit(MAP_WIDTH, MAP_HEIGHT, xChar, yChar)).isTrue();
                    }
                }
            }
        }
    }
}