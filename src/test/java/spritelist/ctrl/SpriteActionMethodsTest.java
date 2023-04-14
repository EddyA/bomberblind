package spritelist.ctrl;

import java.io.IOException;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import images.ImagesLoader;
import map.MapPoint;
import sprite.nomad.BlueBomber;
import utils.Direction;

import static images.ImagesLoader.IMAGE_SIZE;

class SpriteActionMethodsTest implements WithAssertions {

    private final int MAP_WIDTH = 20;
    private final int MAP_HEIGHT = 10;

    @BeforeEach
    void fillImagesMatrix() throws IOException {
        ImagesLoader.fillImagesMatrix();
    }

    @Test
    void shiftBomberIfPossibleWithPressedKeyIsUpShouldWordAsExpected() {
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
         */

        // test.
        int xBomber = IMAGE_SIZE + IMAGE_SIZE / 2 + 1;
        int yBomber = IMAGE_SIZE * 2 + IMAGE_SIZE / 2;
        BlueBomber blueBomber = new BlueBomber(xBomber, yBomber);
        for (int xChar = xBomber; xChar < IMAGE_SIZE * 3 + IMAGE_SIZE / 2; xChar++) {
            blueBomber.setXMap(xChar);
            int xMapBeforeShifting = blueBomber.getXMap();
            ActionMethods.shiftBomberIfPossible(mapPointMatrix, blueBomber, Direction.DIRECTION_NORTH);
            if (xChar >= IMAGE_SIZE * 2 && xChar < IMAGE_SIZE * 2 + IMAGE_SIZE / 2) {
                assertThat(blueBomber.getXMap()).isEqualTo(xMapBeforeShifting + 1); // shift to the right.
            } else if (xChar > IMAGE_SIZE * 2 + IMAGE_SIZE / 2 && xChar < IMAGE_SIZE * 3) {
                assertThat(blueBomber.getXMap()).isEqualTo(xMapBeforeShifting - 1); // shift to the left.
            } else {
                assertThat(blueBomber.getXMap()).isEqualTo(xMapBeforeShifting); // do nothing.
            }
        }
    }

    @Test
    void shiftBomberIfPossibleWithPressedKeyIsDownShouldWordAsExpected() {
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
         */

        // test.
        int xBomber = IMAGE_SIZE + IMAGE_SIZE / 2 + 1;
        int yBomber = IMAGE_SIZE - 1;
        BlueBomber blueBomber = new BlueBomber(xBomber, yBomber);
        for (int xChar = xBomber; xChar < IMAGE_SIZE * 3 + IMAGE_SIZE / 2; xChar++) {
            blueBomber.setXMap(xChar);
            int xMapBeforeShifting = blueBomber.getXMap();
            ActionMethods.shiftBomberIfPossible(mapPointMatrix, blueBomber, Direction.DIRECTION_SOUTH);
            if (xChar >= IMAGE_SIZE * 2 && xChar < IMAGE_SIZE * 2 + IMAGE_SIZE / 2) {
                assertThat(blueBomber.getXMap()).isEqualTo(xMapBeforeShifting + 1); // shift to the right.
            } else if (xChar > IMAGE_SIZE * 2 + IMAGE_SIZE / 2 && xChar < IMAGE_SIZE * 3) {
                assertThat(blueBomber.getXMap()).isEqualTo(xMapBeforeShifting - 1); // shift to the left.
            } else {
                assertThat(blueBomber.getXMap()).isEqualTo(xMapBeforeShifting); // do nothing.
            }
        }
    }

    @Test
    void shiftBomberIfPossibleWithPressedKeyIsLeftShouldWordAsExpected() {
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
         */

        // test.
        int xBomber = IMAGE_SIZE * 2 + IMAGE_SIZE / 2;
        int yBomber = IMAGE_SIZE + IMAGE_SIZE / 2 + 1;
        BlueBomber blueBomber = new BlueBomber(xBomber, yBomber);
        for (int yChar = yBomber; yChar < IMAGE_SIZE * 4; yChar++) {
            blueBomber.setYMap(yChar);
            int yMapBeforeShifting = blueBomber.getYMap();
            ActionMethods.shiftBomberIfPossible(mapPointMatrix, blueBomber, Direction.DIRECTION_WEST);
            if (yChar >= IMAGE_SIZE * 2 && yChar < IMAGE_SIZE * 2 + IMAGE_SIZE / 2) {
                assertThat(blueBomber.getYMap()).isEqualTo(yMapBeforeShifting + 1); // shift to the right.
            } else if (yChar >= IMAGE_SIZE * 3 && yChar < IMAGE_SIZE * 3 + IMAGE_SIZE / 2) {
                assertThat(blueBomber.getYMap()).isEqualTo(yMapBeforeShifting - 1); // shift to the left.
            } else {
                assertThat(blueBomber.getYMap()).isEqualTo(yMapBeforeShifting); // do nothing.
            }
        }
    }

    @Test
    void shiftBomberIfPossibleWithPressedKeyIsRightShouldWordAsExpected() {
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
         */

        // test.
        int xBomber = IMAGE_SIZE - 1;
        int yBomber = IMAGE_SIZE + IMAGE_SIZE / 2 + 1;
        BlueBomber blueBomber = new BlueBomber(xBomber, yBomber);
        for (int yChar = yBomber; yChar < IMAGE_SIZE * 4; yChar++) {
            blueBomber.setYMap(yChar);
            int yMapBeforeShifting = blueBomber.getYMap();
            ActionMethods.shiftBomberIfPossible(mapPointMatrix, blueBomber, Direction.DIRECTION_EAST);
            if (yChar >= IMAGE_SIZE * 2 && yChar < IMAGE_SIZE * 2 + IMAGE_SIZE / 2) {
                assertThat(blueBomber.getYMap()).isEqualTo(yMapBeforeShifting + 1); // shift to the right.
            } else if (yChar >= IMAGE_SIZE * 3 && yChar < IMAGE_SIZE * 3 + IMAGE_SIZE / 2) {
                assertThat(blueBomber.getYMap()).isEqualTo(yMapBeforeShifting - 1); // shift to the left.
            } else {
                assertThat(blueBomber.getYMap()).isEqualTo(yMapBeforeShifting); // do nothing.
            }
        }
    }
}
