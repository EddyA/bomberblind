package map;

import org.junit.Test;
import org.mockito.Mockito;

import java.awt.*;

import static org.assertj.core.api.Assertions.assertThat;

public class PatternMethodsTest {

    private final int MAP_WIDTH = 20;
    private final int MAP_HEIGHT = 10;

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
                startColIdx));
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
    public void securePerimeterShouldReturnTheAppropriateValue() {
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
        try {
            PatternMethods.securePerimeter(rMapPointMatrix, MAP_WIDTH, MAP_HEIGHT, rMapPattern, startRowIdx,
                    startColIdx, 0);
        } catch (NullPointerException e) { // must throw an excpetion because of ImageLoader.

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

    }
}