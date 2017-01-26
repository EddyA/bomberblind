package spriteList.ctrl;

import java.io.IOException;

import org.assertj.core.api.WithAssertions;
import org.junit.Before;
import org.junit.Test;

import images.ImagesLoader;
import map.MapPoint;
import sprite.nomad.BlueBomber;
import utils.Direction;

public class SpriteActionMethodsTest implements WithAssertions {

    private final int MAP_WIDTH = 20;
    private final int MAP_HEIGHT = 10;

    @Before
    public void fillImagesMatrix() throws IOException {
        ImagesLoader.fillImagesMatrix();
    }

    @Test
    public void shiftBomberIfPossibleWithPressedKeyIsUpShouldWordAsExpected() {
        MapPoint[][] mapPointMatrix = new MapPoint[MAP_HEIGHT][MAP_WIDTH];
        for (int rowIdx = 0; rowIdx < MAP_HEIGHT; rowIdx++) {
            for (int colIdx = 0; colIdx < MAP_WIDTH; colIdx++) {
                mapPointMatrix[rowIdx][colIdx] = new MapPoint(rowIdx, colIdx);
                mapPointMatrix[rowIdx][colIdx].setPathway(true);
            }
        }
        mapPointMatrix[1][1].setPathway(false);
        mapPointMatrix[1][3].setPathway(false);

        /*
         * set map as:
         *    0 1 2 3 4 5
         * 0 | | | | | | |
         * 1 | |M| |M| | |
         * 2 | | | | | | |
         *
         * with:
         * - M: mutable
         *
         * test: yChar = 75px && xMap = [46; 105]
         * if xChar >= 60px && xChar < 75px, the bomber should shift to the right,
         * else if xChar > 75 px && xChar < 90px, the bomber should shift to the left,
         * else do nothing.
         */

        // test.
        BlueBomber blueBomber = new BlueBomber(46, 75);
        for (int xChar = 46; xChar < 105; xChar++) {
            blueBomber.setxMap(xChar);
            int xMapBeforeShifting = blueBomber.getxMap();
            ActionMethods.shiftBomberIfPossible(mapPointMatrix, blueBomber, Direction.NORTH);
            if (xChar >= 60 && xChar < 75) {
                assertThat(blueBomber.getxMap()).isEqualTo(xMapBeforeShifting + 1); // shift to the right.
            } else if (xChar > 75 && xChar < 90) {
                assertThat(blueBomber.getxMap()).isEqualTo(xMapBeforeShifting - 1); // shift to the left.
            } else {
                assertThat(blueBomber.getxMap()).isEqualTo(xMapBeforeShifting); // do nothing.
            }
        }
    }

    @Test
    public void shiftBomberIfPossibleWithPressedKeyIsDownShouldWordAsExpected() {
        MapPoint[][] mapPointMatrix = new MapPoint[MAP_HEIGHT][MAP_WIDTH];
        for (int rowIdx = 0; rowIdx < MAP_HEIGHT; rowIdx++) {
            for (int colIdx = 0; colIdx < MAP_WIDTH; colIdx++) {
                mapPointMatrix[rowIdx][colIdx] = new MapPoint(rowIdx, colIdx);
                mapPointMatrix[rowIdx][colIdx].setPathway(true);
            }
        }
        mapPointMatrix[1][1].setPathway(false);
        mapPointMatrix[1][3].setPathway(false);

        /*
         * set map as:
         *    0 1 2 3 4 5
         * 0 | | | | | | |
         * 1 | |M| |M| | |
         * 2 | | | | | | |
         *
         * with:
         * - M: mutable
         *
         * test: yChar = 29px && xMap = [46; 105]
         * if xChar >= 60px && xChar < 75px, the bomber should shift to the right,
         * else if xChar > 75 px && xChar < 90px, the bomber should shift to the left,
         * else do nothing.
         */

        // test.
        BlueBomber blueBomber = new BlueBomber(46, 29);
        for (int xChar = 46; xChar < 105; xChar++) {
            blueBomber.setxMap(xChar);
            int xMapBeforeShifting = blueBomber.getxMap();
            ActionMethods.shiftBomberIfPossible(mapPointMatrix, blueBomber, Direction.SOUTH);
            if (xChar >= 60 && xChar < 75) {
                assertThat(blueBomber.getxMap()).isEqualTo(xMapBeforeShifting + 1); // shift to the right.
            } else if (xChar > 75 && xChar < 90) {
                assertThat(blueBomber.getxMap()).isEqualTo(xMapBeforeShifting - 1); // shift to the left.
            } else {
                assertThat(blueBomber.getxMap()).isEqualTo(xMapBeforeShifting); // do nothing.
            }
        }
    }

    @Test
    public void shiftBomberIfPossibleWithPressedKeyIsLeftShouldWordAsExpected() {
        MapPoint[][] mapPointMatrix = new MapPoint[MAP_HEIGHT][MAP_WIDTH];
        for (int rowIdx = 0; rowIdx < MAP_HEIGHT; rowIdx++) {
            for (int colIdx = 0; colIdx < MAP_WIDTH; colIdx++) {
                mapPointMatrix[rowIdx][colIdx] = new MapPoint(rowIdx, colIdx);
                mapPointMatrix[rowIdx][colIdx].setPathway(true);
            }
        }
        mapPointMatrix[1][1].setPathway(false);
        mapPointMatrix[3][1].setPathway(false);

        /*
         * set map as:
         *    0 1 2 3
         * 0 | | | | |
         * 1 | |M| | |
         * 2 | | | | |
         * 3 | |M| | |
         * 4 | | | | |
         *
         * with:
         * - M: mutable
         *
         * test: xChar = 75px && yMap = [46; 120]
         * if yChar >= 60px && yChar < 75px, the bomber should shift to the shouth,
         * else if yChar >= 90 px && yChar < 105px, the bomber should shift to the north,
         * else do nothing.
         */

        // test.
        BlueBomber blueBomber = new BlueBomber(75, 46);
        for (int yChar = 46; yChar < 120; yChar++) {
            blueBomber.setyMap(yChar);
            int yMapBeforeShifting = blueBomber.getyMap();
            ActionMethods.shiftBomberIfPossible(mapPointMatrix, blueBomber, Direction.WEST);
            if (yChar >= 60 && yChar < 75) {
                assertThat(blueBomber.getyMap()).isEqualTo(yMapBeforeShifting + 1); // shift to the right.
            } else if (yChar >= 90 && yChar < 105) {
                assertThat(blueBomber.getyMap()).isEqualTo(yMapBeforeShifting - 1); // shift to the left.
            } else {
                assertThat(blueBomber.getyMap()).isEqualTo(yMapBeforeShifting); // do nothing.
            }
        }
    }

    @Test
    public void shiftBomberIfPossibleWithPressedKeyIsRightShouldWordAsExpected() {
        MapPoint[][] mapPointMatrix = new MapPoint[MAP_HEIGHT][MAP_WIDTH];
        for (int rowIdx = 0; rowIdx < MAP_HEIGHT; rowIdx++) {
            for (int colIdx = 0; colIdx < MAP_WIDTH; colIdx++) {
                mapPointMatrix[rowIdx][colIdx] = new MapPoint(rowIdx, colIdx);
                mapPointMatrix[rowIdx][colIdx].setPathway(true);
            }
        }
        mapPointMatrix[1][1].setPathway(false);
        mapPointMatrix[3][1].setPathway(false);

        /*
         * set map as:
         *    0 1 2 3
         * 0 | | | | |
         * 1 | |M| | |
         * 2 | | | | |
         * 3 | |M| | |
         * 4 | | | | |
         *
         * with:
         * - M: mutable
         *
         * test: xChar = 75px && yMap = [46; 120]
         * if yChar >= 60px && yChar < 75px, the bomber should shift to the shouth,
         * else if yChar >= 90 px && yChar < 105px, the bomber should shift to the north,
         * else do nothing.
         */

        // test.
        BlueBomber blueBomber = new BlueBomber(29, 46);
        for (int yChar = 46; yChar < 120; yChar++) {
            blueBomber.setyMap(yChar);
            int yMapBeforeShifting = blueBomber.getyMap();
            ActionMethods.shiftBomberIfPossible(mapPointMatrix, blueBomber, Direction.EAST);
            if (yChar >= 60 && yChar < 75) {
                assertThat(blueBomber.getyMap()).isEqualTo(yMapBeforeShifting + 1); // shift to the right.
            } else if (yChar >= 90 && yChar < 105) {
                assertThat(blueBomber.getyMap()).isEqualTo(yMapBeforeShifting - 1); // shift to the left.
            } else {
                assertThat(blueBomber.getyMap()).isEqualTo(yMapBeforeShifting); // do nothing.
            }
        }
    }
}