package map.ctrl;

import exceptions.CannotCreateMapElementException;
import images.ImagesLoader;
import map.MapPattern;
import map.MapPoint;
import org.assertj.core.api.WithAssertions;
import org.junit.Test;

import java.awt.*;
import java.io.IOException;

import static map.ctrl.PatternMethods.*;

public class PatternMethodsTest implements WithAssertions {

    private final int MAP_WIDTH = 20;
    private final int MAP_HEIGHT = 10;

    @Test
    public void placeNorthEdgeOnMapShouldThrowExpectedException() throws Exception {
        MapPoint[][] mapPointMatrix = new MapPoint[MAP_HEIGHT][MAP_WIDTH];
        for (int rowIdx = 0; rowIdx < MAP_HEIGHT; rowIdx++) {
            for (int colIdx = 0; colIdx < MAP_WIDTH; colIdx++) {
                mapPointMatrix[rowIdx][colIdx] = new MapPoint(rowIdx, colIdx);
            }
        }
        MapPattern mapPattern = new MapPattern(new Image[15], 3, 2, false, false, "northEdge");
        assertThatThrownBy(() -> placeNorthEdgeOnMap(mapPointMatrix, MAP_WIDTH, MAP_HEIGHT, mapPattern))
                .isInstanceOf(CannotCreateMapElementException.class)
                .hasMessage("not able to create the north edge: mapWidth(=20) % patternWidth(=3) != 0).");
    }

    @Test
    public void placeSouthEdgeOnMapShouldThrowExpectedException() throws Exception {
        MapPoint[][] mapPointMatrix = new MapPoint[MAP_HEIGHT][MAP_WIDTH];
        for (int rowIdx = 0; rowIdx < MAP_HEIGHT; rowIdx++) {
            for (int colIdx = 0; colIdx < MAP_WIDTH; colIdx++) {
                mapPointMatrix[rowIdx][colIdx] = new MapPoint(rowIdx, colIdx);
            }
        }
        MapPattern mapPattern = new MapPattern(new Image[15], 3, 2, false, false, "southEdge");
        assertThatThrownBy(() -> placeSouthEdgeOnMap(mapPointMatrix, MAP_WIDTH, MAP_HEIGHT, mapPattern))
                .isInstanceOf(CannotCreateMapElementException.class)
                .hasMessage("not able to create the south edge: mapWidth(=20) % patternWidth(=3) != 0).");
    }

    @Test
    public void placePatternOnMapAndSecurePerimeterShouldThrowExpectedException() throws Exception {
        MapPoint[][] mapPointMatrix = new MapPoint[MAP_HEIGHT][MAP_WIDTH];
        for (int rowIdx = 0; rowIdx < MAP_HEIGHT; rowIdx++) {
            for (int colIdx = 0; colIdx < MAP_WIDTH; colIdx++) {
                mapPointMatrix[rowIdx][colIdx] = new MapPoint(rowIdx, colIdx);
            }
        }
        MapPattern mapPattern = new MapPattern(new Image[15], 3, 2, false, false, "castle");
        assertThatThrownBy(() -> placePatternOnMapAndSecurePerimeter(mapPointMatrix, MAP_WIDTH, MAP_HEIGHT, mapPattern,
                MAP_HEIGHT - mapPattern.getHeight() + 1, 0, 0, 0))
                .isInstanceOf(CannotCreateMapElementException.class)
                .hasMessage("not able to create an element at rowIdx=9, colIdx=0.");
    }

    @Test
    public void isPatternCrossingMapLimitShouldReturnExpectedValue() {
        MapPattern mapPattern = new MapPattern(new Image[6], 2, 3, true, true, "myPattern");

        // tests.
        for (int colIdx = -1; colIdx <= MAP_WIDTH + 1; colIdx++) {
            for (int rowIdx = -1; rowIdx <= MAP_HEIGHT + 1; rowIdx++) {
                if (colIdx < 0 || colIdx + mapPattern.getWidth() > MAP_WIDTH ||
                        rowIdx < 0 || rowIdx + mapPattern.getHeight() > MAP_HEIGHT) {
                    assertThat(PatternMethods.isPatternCrossingMapLimit(MAP_WIDTH, MAP_HEIGHT, mapPattern, rowIdx,
                            colIdx)).isTrue();
                } else {
                    assertThat(PatternMethods.isPatternCrossingMapLimit(MAP_WIDTH, MAP_HEIGHT, mapPattern, rowIdx,
                            colIdx)).isFalse();
                }
            }
        }
    }

    @Test
    public void isPatternCrossingNotAvailableCaseShouldReturnExpectedValue() {
        MapPoint[][] mapPointMatrix = new MapPoint[MAP_HEIGHT][MAP_WIDTH];
        for (int rowIdx = 0; rowIdx < MAP_HEIGHT; rowIdx++) {
            for (int colIdx = 0; colIdx < MAP_WIDTH; colIdx++) {
                mapPointMatrix[rowIdx][colIdx] = new MapPoint(rowIdx, colIdx);
            }
        }
        int notAvCaseRowIdx = 4;
        int notAvCaseColIdx = 5;
        mapPointMatrix[notAvCaseRowIdx][notAvCaseColIdx].setAvailable(false);
        MapPattern mapPattern = new MapPattern(new Image[6], 2, 3, true, true, "myPattern");

        // tests.
        for (int colIdx = 0; colIdx < MAP_WIDTH - mapPattern.getWidth(); colIdx++) {
            for (int rowIdx = 0; rowIdx < MAP_HEIGHT - mapPattern.getHeight(); rowIdx++) {
                if (colIdx > notAvCaseColIdx - mapPattern.getWidth() && colIdx <= notAvCaseColIdx &&
                        rowIdx > notAvCaseRowIdx - mapPattern.getHeight() && rowIdx <= notAvCaseRowIdx) {
                    assertThat(PatternMethods.isPatternCrossingNotAvailableCase(mapPointMatrix, mapPattern, rowIdx,
                            colIdx)).isTrue();
                } else {
                    assertThat(PatternMethods.isPatternCrossingNotAvailableCase(mapPointMatrix, mapPattern, rowIdx,
                            colIdx)).isFalse();
                }
            }
        }
    }

    @Test
    public void placePatternOnMapShouldReturnExpectedValue() {
        MapPoint[][] mapPointMatrix = new MapPoint[MAP_HEIGHT][MAP_WIDTH];
        for (int rowIdx = 0; rowIdx < MAP_HEIGHT; rowIdx++) {
            for (int colIdx = 0; colIdx < MAP_WIDTH; colIdx++) {
                mapPointMatrix[rowIdx][colIdx] = new MapPoint(rowIdx, colIdx);
            }
        }
        int startRowIdx = 1;
        int startColIdx = 6;
        MapPattern mapPattern = new MapPattern(new Image[6], 2, 3, true, true, "myPattern");

        // test.
        assertThat(PatternMethods.placePatternOnMap(mapPointMatrix, MAP_WIDTH, MAP_HEIGHT, mapPattern, startRowIdx,
                startColIdx)).isTrue();
        for (int colIdx = 0; colIdx < MAP_WIDTH - mapPattern.getWidth(); colIdx++) {
            for (int rowIdx = 0; rowIdx < MAP_HEIGHT - mapPattern.getHeight(); rowIdx++) {
                if (colIdx >= startColIdx && colIdx < startColIdx + mapPattern.getWidth() &&
                        rowIdx >= startRowIdx && rowIdx < startRowIdx + mapPattern.getHeight()) {
                    assertThat(mapPointMatrix[rowIdx][colIdx].isAvailable()).isFalse();
                } else {
                    assertThat(mapPointMatrix[rowIdx][colIdx].isAvailable()).isTrue();
                }
            }
        }
    }

    @Test
    public void securePerimeterShouldReturnExpectedValue() throws IOException {
        ImagesLoader.fillImagesMatrix(); // fill images to avoid a nullPointerException when putting a static mutable.

        MapPoint[][] mapPointMatrix = new MapPoint[MAP_HEIGHT][MAP_WIDTH];
        for (int rowIdx = 0; rowIdx < MAP_HEIGHT; rowIdx++) {
            for (int colIdx = 0; colIdx < MAP_WIDTH; colIdx++) {
                mapPointMatrix[rowIdx][colIdx] = new MapPoint(rowIdx, colIdx);
            }
        }
        int startRowIdx = 0;
        int startColIdx = 6;
        MapPattern mapPattern = new MapPattern(new Image[6], 2, 3, true, true, "myPattern");
        int notAvCaseRowIdx = 0;
        int notAvCaseColIdx = 5;
        mapPointMatrix[notAvCaseRowIdx][notAvCaseColIdx].setAvailable(false);

        // secure perimeter.
        PatternMethods.securePerimeter(mapPointMatrix, MAP_WIDTH, MAP_HEIGHT, mapPattern, startRowIdx, startColIdx, 0, 0);

        // test.
        for (int colIdx = 0; colIdx < MAP_WIDTH - mapPattern.getWidth(); colIdx++) {
            for (int rowIdx = 0; rowIdx < MAP_HEIGHT - mapPattern.getHeight(); rowIdx++) {
                if (colIdx >= startColIdx - 1 && colIdx <= startColIdx + mapPattern.getWidth() &&
                        rowIdx >= startRowIdx && rowIdx <= startRowIdx + mapPattern.getHeight()) {
                    assertThat(mapPointMatrix[rowIdx][colIdx].isAvailable()).isFalse();
                } else {
                    assertThat(mapPointMatrix[rowIdx][colIdx].isAvailable()).isTrue();
                }
            }
        }
    }

    @Test
    public void generateRandomRowIdxShouldReturnExpectedValue() {
        assertThat(generateRandomRowIdx(10, 1, 1, 4, 2)).isEqualTo(3);
        for (int i = 0; i < 100000; i++) {
            assertThat(generateRandomRowIdx(100, 1, 1, 4, 2)).isBetween(3, 92);
        }
    }

    @Test
    public void generateRandomRowIdxShouldThrowExpectedException() {
        assertThatThrownBy(() -> generateRandomRowIdx(8, 1, 1, 4, 2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("bound must be positive");
    }

    @Test
    public void generateRandomColIdxShouldReturnExpectedValue() {
        assertThat(generateRandomColIdx(10, 1, 1, 4, 2)).isEqualTo(3);
        for (int i = 0; i < 100000; i++) {
            assertThat(generateRandomColIdx(100, 1, 1, 4, 2)).isBetween(3, 92);
        }
    }

    @Test
    public void generateRandomColIdxShouldThrowExpectedException() {
        assertThatThrownBy(() -> generateRandomColIdx(8, 1, 1, 4, 2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("bound must be positive");
    }
}