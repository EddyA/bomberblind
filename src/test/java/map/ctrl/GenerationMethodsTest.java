package map.ctrl;

import exceptions.CannotCreateMapElementException;
import images.ImagesLoader;
import map.MapPoint;
import map.MapPattern;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import utils.Tuple2;

import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static map.ctrl.GenerationMethods.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class GenerationMethodsTest {

    private final int MAP_WIDTH = 20;
    private final int MAP_HEIGHT = 10;

    @Before
    public void fillImagesMatrix() throws IOException {
        ImagesLoader.fillImagesMatrix();
    }

    @Test
    public void randomlyPlaceCastlesWithDifferentHMarginShouldHaveTheExpectedBehavior() throws Exception {
        int patternWidth = 3;
        MapPattern mapPattern = new MapPattern(new Image[patternWidth * 2], patternWidth, 2, false, false, "castle");

        // for each hMargin values from 0 to MAP_WIDTH.
        for (int hMargin = 0; hMargin <= MAP_WIDTH; hMargin++) {

            // re-create a virgin matrix of MapPoint.
            MapPoint[][] mapPointMatrix = new MapPoint[MAP_HEIGHT][MAP_WIDTH];
            for (int rowIdx = 0; rowIdx < MAP_HEIGHT; rowIdx++) {
                for (int colIdx = 0; colIdx < MAP_WIDTH; colIdx++) {
                    mapPointMatrix[rowIdx][colIdx] = new MapPoint(rowIdx, colIdx);
                }
            }

            try {
                // call the function with the current hMargin value.
                randomlyPlaceCastles(mapPointMatrix, MAP_WIDTH, MAP_HEIGHT, hMargin, 0, 0, 0,
                        new Tuple2<>(mapPattern, mapPattern), 0);
            } catch (CannotCreateMapElementException e) {
                if (hMargin < MAP_WIDTH / 2 - patternWidth) {
                    Assert.fail(); // should not throw an exception with the current value of hMargin.
                } else {
                    assertThat(e.getMessage()).isEqualTo("not able to generate random colIdx when placing castles: "
                            + "the map width is too small or the horizontal margin too high according to the castles "
                            + "width.");
                }
            }
        }
    }

    @Test
    public void randomlyPlaceCastlesWithDifferentVMarginShouldHaveTheExpectedBehavior() throws Exception {
        int northEdgeHeight = 1;
        int southEdgeHeight = 1;
        int patternHeight = 2;
        MapPattern mapPattern = new MapPattern(new Image[3 * patternHeight], 3, patternHeight, false, false, "castle");

        // for each vMargin values from 0 to MAP_HEIGHT.
        for (int vMargin = 0; vMargin <= MAP_HEIGHT; vMargin++) {

            // re-create a virgin matrix of MapPoint.
            MapPoint[][] mapPointMatrix = new MapPoint[MAP_HEIGHT][MAP_WIDTH];
            for (int rowIdx = 0; rowIdx < MAP_HEIGHT; rowIdx++) {
                for (int colIdx = 0; colIdx < MAP_WIDTH; colIdx++) {
                    mapPointMatrix[rowIdx][colIdx] = new MapPoint(rowIdx, colIdx);
                }
            }

            try {
                // call the function with the current vMargin value.
                randomlyPlaceCastles(mapPointMatrix, MAP_WIDTH, MAP_HEIGHT, 0, vMargin, northEdgeHeight, southEdgeHeight,
                        new Tuple2<>(mapPattern, mapPattern), 0);
            } catch (CannotCreateMapElementException e) {
                if (vMargin <= (MAP_HEIGHT - patternHeight - (northEdgeHeight + southEdgeHeight)) / 2) {
                    Assert.fail(); // should not throw an exception with the current value of vMargin.
                } else {
                    assertThat(e.getMessage()).isEqualTo("not able to generate random rowIdx when placing castles: "
                            + "the map height is too small or the vertical margin is too high according to the castle "
                            + "height and the north/south edge heights.");
                }
            }
        }
    }

    @Test
    public void randomlyPlaceComplexElementsWithTooHighSizeShouldThrowTheAppropriateException() throws Exception {
        MapPoint[][] mapPointMatrix = new MapPoint[MAP_HEIGHT][MAP_WIDTH];
        for (int rowIdx = 0; rowIdx < MAP_HEIGHT; rowIdx++) {
            for (int colIdx = 0; colIdx < MAP_WIDTH; colIdx++) {
                mapPointMatrix[rowIdx][colIdx] = new MapPoint(rowIdx, colIdx);
            }
        }
        Map<MapPattern, Integer> myMap = new HashMap<>();
        myMap.put(new MapPattern(new Image[6], MAP_WIDTH + 1, MAP_HEIGHT + 1, false, false, "pattern1"), 1);
        assertThatThrownBy(() -> randomlyPlaceComplexElements(mapPointMatrix, MAP_WIDTH, MAP_HEIGHT, 0, 0, myMap, 1))
                .isInstanceOf(CannotCreateMapElementException.class)
                .hasMessage("not able to place elements based on pattern 'pattern1': the map size can be too small "
                        + "or the margins too high according to the relative pattern size and the north/south edge "
                        + "heights.");
    }

    @Test
    public void randomlyPlaceComplexElementsShouldTryPlacingElementWithAMaxNumberOfTry() throws Exception {
        MapPoint[][] mapPointMatrix = new MapPoint[MAP_HEIGHT][MAP_WIDTH];
        for (int rowIdx = 0; rowIdx < MAP_HEIGHT; rowIdx++) {
            for (int colIdx = 0; colIdx < MAP_WIDTH; colIdx++) {
                mapPointMatrix[rowIdx][colIdx] = new MapPoint(rowIdx, colIdx);
            }
        }

        int maxNbTry = 10;
        int nbPattern1ToPlace = 4;
        int nbPattern2ToPlace = 5;
        Map<MapPattern, Integer> myMap = new HashMap<>();
        myMap.put(new MapPattern(new Image[MAP_WIDTH*MAP_HEIGHT], MAP_WIDTH, MAP_HEIGHT, false, false, "pattern1"),
                nbPattern1ToPlace);
        myMap.put(new MapPattern(new Image[MAP_WIDTH*MAP_HEIGHT], MAP_WIDTH, MAP_HEIGHT, false, false, "pattern2"),
                nbPattern2ToPlace);
        randomlyPlaceComplexElements(mapPointMatrix, MAP_WIDTH, MAP_HEIGHT, 0, 0, myMap, maxNbTry);

        // here, the map is too small to place all the elements, only the 1st has been placed.
        assertThat(GenerationMethods.totalNbTry).isEqualTo((nbPattern1ToPlace - 1) * maxNbTry +
                nbPattern2ToPlace * maxNbTry);
    }

    @Test
    public void randomlyPlaceSingleElementsShouldFillAvailableCasesWithSingleMutables() throws Exception {
        MapPoint[][] mapPointMatrix = new MapPoint[MAP_HEIGHT][MAP_WIDTH];
        for (int rowIdx = 0; rowIdx < MAP_HEIGHT; rowIdx++) {
            for (int colIdx = 0; colIdx < MAP_WIDTH; colIdx++) {
                mapPointMatrix[rowIdx][colIdx] = new MapPoint(rowIdx, colIdx);
            }
        }
        randomlyPlaceSingleElements(mapPointMatrix, MAP_WIDTH, MAP_HEIGHT, 100, 0, 0);
        for (int rowIdx = 0; rowIdx < MAP_HEIGHT; rowIdx++) {
            for (int colIdx = 0; colIdx < MAP_WIDTH; colIdx++) {
                assertThat(mapPointMatrix[rowIdx][colIdx].isAvailable()).isFalse();
                assertThat(mapPointMatrix[rowIdx][colIdx].isMutable()).isTrue();
                assertThat(mapPointMatrix[rowIdx][colIdx].isPathway()).isFalse();
            }
        }
    }

    @Test
    public void randomlyPlaceSingleElementsShouldFillAvailableCasesWithSingleObstacles() throws Exception {
        MapPoint[][] mapPointMatrix = new MapPoint[MAP_HEIGHT][MAP_WIDTH];
        for (int rowIdx = 0; rowIdx < MAP_HEIGHT; rowIdx++) {
            for (int colIdx = 0; colIdx < MAP_WIDTH; colIdx++) {
                mapPointMatrix[rowIdx][colIdx] = new MapPoint(rowIdx, colIdx);
            }
        }
        randomlyPlaceSingleElements(mapPointMatrix, MAP_WIDTH, MAP_HEIGHT, 0, 100, 0);
        for (int rowIdx = 0; rowIdx < MAP_HEIGHT; rowIdx++) {
            for (int colIdx = 0; colIdx < MAP_WIDTH; colIdx++) {
                assertThat(mapPointMatrix[rowIdx][colIdx].isAvailable()).isFalse();
                assertThat(mapPointMatrix[rowIdx][colIdx].isMutable()).isFalse();
                assertThat(mapPointMatrix[rowIdx][colIdx].isPathway()).isFalse();
            }
        }
    }

    @Test
    public void randomlyPlaceSingleElementsShouldFillAvailableCasesWithSingleStaticPathways() throws Exception {
        MapPoint[][] mapPointMatrix = new MapPoint[MAP_HEIGHT][MAP_WIDTH];
        for (int rowIdx = 0; rowIdx < MAP_HEIGHT; rowIdx++) {
            for (int colIdx = 0; colIdx < MAP_WIDTH; colIdx++) {
                mapPointMatrix[rowIdx][colIdx] = new MapPoint(rowIdx, colIdx);
            }
        }
        randomlyPlaceSingleElements(mapPointMatrix, MAP_WIDTH, MAP_HEIGHT, 0, 0, 0);
        for (int rowIdx = 0; rowIdx < MAP_HEIGHT; rowIdx++) {
            for (int colIdx = 0; colIdx < MAP_WIDTH; colIdx++) {
                assertThat(mapPointMatrix[rowIdx][colIdx].isAvailable()).isFalse();
                assertThat(mapPointMatrix[rowIdx][colIdx].isMutable()).isFalse();
                assertThat(mapPointMatrix[rowIdx][colIdx].isPathway()).isTrue();
                assertThat(mapPointMatrix[rowIdx][colIdx].getImage()).isNotNull();
                assertThat(mapPointMatrix[rowIdx][colIdx].getImages()).isNull();
            }
        }
    }

    @Test
    public void randomlyPlaceSingleElementsShouldFillAvailableCasesWithSingleDynamicPathways() throws Exception {
        MapPoint[][] mapPointMatrix = new MapPoint[MAP_HEIGHT][MAP_WIDTH];
        for (int rowIdx = 0; rowIdx < MAP_HEIGHT; rowIdx++) {
            for (int colIdx = 0; colIdx < MAP_WIDTH; colIdx++) {
                mapPointMatrix[rowIdx][colIdx] = new MapPoint(rowIdx, colIdx);
            }
        }
        randomlyPlaceSingleElements(mapPointMatrix, MAP_WIDTH, MAP_HEIGHT, 0, 0, 100);
        for (int rowIdx = 0; rowIdx < MAP_HEIGHT; rowIdx++) {
            for (int colIdx = 0; colIdx < MAP_WIDTH; colIdx++) {
                assertThat(mapPointMatrix[rowIdx][colIdx].isAvailable()).isFalse();
                assertThat(mapPointMatrix[rowIdx][colIdx].isMutable()).isFalse();
                assertThat(mapPointMatrix[rowIdx][colIdx].isPathway()).isTrue();
                assertThat(mapPointMatrix[rowIdx][colIdx].getImage()).isNull();
                assertThat(mapPointMatrix[rowIdx][colIdx].getImages()).isNotNull();
            }
        }
    }
}