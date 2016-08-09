package map.Ctrl;

import exceptions.CannotCreateMapElementException;
import images.ImagesLoader;
import map.RMapPattern;
import map.RMapPoint;
import org.assertj.core.api.WithAssertions;
import org.junit.Test;

import java.awt.*;
import java.io.IOException;

import static map.Ctrl.PatternMethods.*;

public class PatternMethodsTest implements WithAssertions {

    private final int MAP_WIDTH = 20;
    private final int MAP_HEIGHT = 10;

    @Test
    public void placeNorthEdgeOnMapShouldThrowTheAppropriateException() throws Exception {
        RMapPoint[][] rMapPointMatrix = new RMapPoint[MAP_HEIGHT][MAP_WIDTH];
        for (int rowIdx = 0; rowIdx < MAP_HEIGHT; rowIdx++) {
            for (int colIdx = 0; colIdx < MAP_WIDTH; colIdx++) {
                rMapPointMatrix[rowIdx][colIdx] = new RMapPoint(rowIdx, colIdx);
            }
        }
        RMapPattern rMapPattern = new RMapPattern(new Image[15], 3, 2, false, false, "northEdge");
        assertThatThrownBy(() -> placeNorthEdgeOnMap(rMapPointMatrix, MAP_WIDTH, MAP_HEIGHT, rMapPattern))
                .isInstanceOf(CannotCreateMapElementException.class)
                .hasMessage("not able to create the north edge: mapWidth(=20) % patternWidth(=3) != 0).");
    }

    @Test
    public void placeSouthEdgeOnMapShouldThrowTheAppropriateException() throws Exception {
        RMapPoint[][] rMapPointMatrix = new RMapPoint[MAP_HEIGHT][MAP_WIDTH];
        for (int rowIdx = 0; rowIdx < MAP_HEIGHT; rowIdx++) {
            for (int colIdx = 0; colIdx < MAP_WIDTH; colIdx++) {
                rMapPointMatrix[rowIdx][colIdx] = new RMapPoint(rowIdx, colIdx);
            }
        }
        RMapPattern rMapPattern = new RMapPattern(new Image[15], 3, 2, false, false, "southEdge");
        assertThatThrownBy(() -> placeSouthEdgeOnMap(rMapPointMatrix, MAP_WIDTH, MAP_HEIGHT, rMapPattern))
                .isInstanceOf(CannotCreateMapElementException.class)
                .hasMessage("not able to create the south edge: mapWidth(=20) % patternWidth(=3) != 0).");
    }

    @Test
    public void placeCastleOnMapShouldThrowTheAppropriateException() throws Exception {
        RMapPoint[][] rMapPointMatrix = new RMapPoint[MAP_HEIGHT][MAP_WIDTH];
        for (int rowIdx = 0; rowIdx < MAP_HEIGHT; rowIdx++) {
            for (int colIdx = 0; colIdx < MAP_WIDTH; colIdx++) {
                rMapPointMatrix[rowIdx][colIdx] = new RMapPoint(rowIdx, colIdx);
            }
        }
        RMapPattern rMapPattern = new RMapPattern(new Image[15], 3, 2, false, false, "castle");
        assertThatThrownBy(() -> placeCastleOnMap(rMapPointMatrix, MAP_WIDTH, MAP_HEIGHT, rMapPattern,
                MAP_HEIGHT - rMapPattern.getHeight() + 1, 0, 0))
                .isInstanceOf(CannotCreateMapElementException.class)
                .hasMessage("not able to create a castle at rowIdx=9, colIdx=0.");
    }

    @Test
    public void isPatternCrossingMapLimitShouldReturnTheAppropriateValue() {
        RMapPattern rMapPattern = new RMapPattern(new Image[6], 2, 3, true, true, "myPattern");

        // tests.
        for (int colIdx = -1; colIdx <= MAP_WIDTH + 1; colIdx++) {
            for (int rowIdx = -1; rowIdx <= MAP_HEIGHT + 1; rowIdx++) {
                if (colIdx < 0 || colIdx + rMapPattern.getWidth() > MAP_WIDTH ||
                        rowIdx < 0 || rowIdx + rMapPattern.getHeight() > MAP_HEIGHT) {
                    assertThat(PatternMethods.isPatternCrossingMapLimit(MAP_WIDTH, MAP_HEIGHT, rMapPattern, rowIdx,
                            colIdx)).isTrue();
                } else {
                    assertThat(PatternMethods.isPatternCrossingMapLimit(MAP_WIDTH, MAP_HEIGHT, rMapPattern, rowIdx,
                            colIdx)).isFalse();
                }
            }
        }
    }

    @Test
    public void isPatternCrossingNotAvailableCaseShouldReturnTheAppropriateValue() {
        RMapPoint[][] rMapPointMatrix = new RMapPoint[MAP_HEIGHT][MAP_WIDTH];
        for (int rowIdx = 0; rowIdx < MAP_HEIGHT; rowIdx++) {
            for (int colIdx = 0; colIdx < MAP_WIDTH; colIdx++) {
                rMapPointMatrix[rowIdx][colIdx] = new RMapPoint(rowIdx, colIdx);
            }
        }
        int notAvCaseRowIdx = 4;
        int notAvCaseColIdx = 5;
        rMapPointMatrix[notAvCaseRowIdx][notAvCaseColIdx].setAvailable(false);
        RMapPattern rMapPattern = new RMapPattern(new Image[6], 2, 3, true, true, "myPattern");

        // tests.
        for (int colIdx = 0; colIdx < MAP_WIDTH - rMapPattern.getWidth(); colIdx++) {
            for (int rowIdx = 0; rowIdx < MAP_HEIGHT - rMapPattern.getHeight(); rowIdx++) {
                if (colIdx > notAvCaseColIdx - rMapPattern.getWidth() && colIdx <= notAvCaseColIdx &&
                        rowIdx > notAvCaseRowIdx - rMapPattern.getHeight() && rowIdx <= notAvCaseRowIdx) {
                    assertThat(PatternMethods.isPatternCrossingNotAvailableCase(rMapPointMatrix, rMapPattern, rowIdx,
                            colIdx)).isTrue();
                } else {
                    assertThat(PatternMethods.isPatternCrossingNotAvailableCase(rMapPointMatrix, rMapPattern, rowIdx,
                            colIdx)).isFalse();
                }
            }
        }
    }

    @Test
    public void placePatternOnMapShouldReturnTheAppropriateValue() {
        RMapPoint[][] rMapPointMatrix = new RMapPoint[MAP_HEIGHT][MAP_WIDTH];
        for (int rowIdx = 0; rowIdx < MAP_HEIGHT; rowIdx++) {
            for (int colIdx = 0; colIdx < MAP_WIDTH; colIdx++) {
                rMapPointMatrix[rowIdx][colIdx] = new RMapPoint(rowIdx, colIdx);
            }
        }
        int startRowIdx = 1;
        int startColIdx = 6;
        RMapPattern rMapPattern = new RMapPattern(new Image[6], 2, 3, true, true, "myPattern");

        // test.
        assertThat(PatternMethods.placePatternOnMap(rMapPointMatrix, MAP_WIDTH, MAP_HEIGHT, rMapPattern, startRowIdx,
                startColIdx)).isTrue();
        for (int colIdx = 0; colIdx < MAP_WIDTH - rMapPattern.getWidth(); colIdx++) {
            for (int rowIdx = 0; rowIdx < MAP_HEIGHT - rMapPattern.getHeight(); rowIdx++) {
                if (colIdx >= startColIdx && colIdx < startColIdx + rMapPattern.getWidth() &&
                        rowIdx >= startRowIdx && rowIdx < startRowIdx + rMapPattern.getHeight()) {
                    assertThat(rMapPointMatrix[rowIdx][colIdx].isAvailable()).isFalse();
                } else {
                    assertThat(rMapPointMatrix[rowIdx][colIdx].isAvailable()).isTrue();
                }
            }
        }
    }

    @Test
    public void securePerimeterShouldReturnTheAppropriateValue() throws IOException {
        ImagesLoader.fillImagesMatrix(); // fill images to avoid a nullPointerException when putting a static mutable.

        RMapPoint[][] rMapPointMatrix = new RMapPoint[MAP_HEIGHT][MAP_WIDTH];
        for (int rowIdx = 0; rowIdx < MAP_HEIGHT; rowIdx++) {
            for (int colIdx = 0; colIdx < MAP_WIDTH; colIdx++) {
                rMapPointMatrix[rowIdx][colIdx] = new RMapPoint(rowIdx, colIdx);
            }
        }
        int startRowIdx = 0;
        int startColIdx = 6;
        RMapPattern rMapPattern = new RMapPattern(new Image[6], 2, 3, true, true, "myPattern");
        int notAvCaseRowIdx = 0;
        int notAvCaseColIdx = 5;
        rMapPointMatrix[notAvCaseRowIdx][notAvCaseColIdx].setAvailable(false);

        // secure perimeter.
        PatternMethods.securePerimeter(rMapPointMatrix, MAP_WIDTH, MAP_HEIGHT, rMapPattern, startRowIdx, startColIdx, 0);

        // test.
        for (int colIdx = 0; colIdx < MAP_WIDTH - rMapPattern.getWidth(); colIdx++) {
            for (int rowIdx = 0; rowIdx < MAP_HEIGHT - rMapPattern.getHeight(); rowIdx++) {
                if (colIdx >= startColIdx - 1 && colIdx <= startColIdx + rMapPattern.getWidth() &&
                        rowIdx >= startRowIdx && rowIdx <= startRowIdx + rMapPattern.getHeight()) {
                    assertThat(rMapPointMatrix[rowIdx][colIdx].isAvailable()).isFalse();
                } else {
                    assertThat(rMapPointMatrix[rowIdx][colIdx].isAvailable()).isTrue();
                }
            }
        }
    }

    @Test
    public void generateRandomRowIdxShouldReturnTheAppropriateValue() {
        assertThat(generateRandomRowIdx(10, 1, 1, 4, 2)).isEqualTo(3);
        for (int i = 0; i < 100000; i++) {
            assertThat(generateRandomRowIdx(100, 1, 1, 4, 2)).isBetween(3, 92);
        }
    }

    @Test
    public void generateRandomRowIdxShouldThrowTheAppropriateException() {
        assertThatThrownBy(() -> generateRandomRowIdx(8, 1, 1, 4, 2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("bound must be positive");
    }

    @Test
    public void generateRandomColIdxShouldReturnTheAppropriateValue() {
        assertThat(generateRandomColIdx(10, 1, 1, 4, 2)).isEqualTo(3);
        for (int i = 0; i < 100000; i++) {
            assertThat(generateRandomColIdx(100, 1, 1, 4, 2)).isBetween(3, 92);
        }
    }

    @Test
    public void generateRandomColIdxShouldThrowTheAppropriateException() {
        assertThatThrownBy(() -> generateRandomColIdx(8, 1, 1, 4, 2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("bound must be positive");
    }
}