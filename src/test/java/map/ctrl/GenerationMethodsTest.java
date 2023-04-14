package map.ctrl;

import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import exceptions.CannotCreateMapElementException;
import exceptions.CannotPlaceBonusOnMapException;
import images.ImagesLoader;
import map.MapPattern;
import map.MapPoint;
import static map.ctrl.GenerationMethods.randomlyPlaceBonus;
import static map.ctrl.GenerationMethods.randomlyPlaceComplexElements;
import static map.ctrl.GenerationMethods.randomlyPlaceEntranceAndExit;
import static map.ctrl.GenerationMethods.randomlyPlaceSingleElements;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.fail;
import utils.Tuple2;

class GenerationMethodsTest {

    private final int MAP_WIDTH = 20;
    private final int MAP_HEIGHT = 10;

    @BeforeEach
    void fillImagesMatrix() throws IOException {
        ImagesLoader.fillImagesMatrix();
    }

    @Test
    void randomlyPlaceCastlesWithDifferentHMarginShouldHaveExpectedBehavior() {
        int patternWidth = 3;
        MapPattern mapPattern = new MapPattern(new Image[patternWidth * 2], patternWidth, 2, false, false, false, false,
            "castle");

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
                randomlyPlaceEntranceAndExit(mapPointMatrix, MAP_WIDTH, MAP_HEIGHT, hMargin, 0, 0, 0,
                        new Tuple2<>(mapPattern, mapPattern), 0, 0);
            } catch (CannotCreateMapElementException e) {
                if (hMargin < MAP_WIDTH / 2 - patternWidth) {
                    fail(); // should not throw an exception with the current value of hMargin.
                } else {
                    assertThat(e.getMessage()).isEqualTo("not able to generate random colIdx when placing the entrance/exit: "
                            + "the map width is too small or the horizontal margin too high according to the entrance/exit "
                            + "width.");
                }
            }
        }
    }

    @Test
    void randomlyPlaceCastlesWithDifferentVMarginShouldHaveExpectedBehavior() {
        int northEdgeHeight = 1;
        int southEdgeHeight = 1;
        int patternHeight = 2;
        MapPattern mapPattern = new MapPattern(new Image[3 * patternHeight], 3, patternHeight, false, false, false,
            false, "castle");

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
                randomlyPlaceEntranceAndExit(mapPointMatrix, MAP_WIDTH, MAP_HEIGHT, 0, vMargin, northEdgeHeight, southEdgeHeight,
                        new Tuple2<>(mapPattern, mapPattern), 0, 0);
            } catch (CannotCreateMapElementException e) {
                if (vMargin <= (MAP_HEIGHT - patternHeight - (northEdgeHeight + southEdgeHeight)) / 2) {
                    fail(); // should not throw an exception with the current value of vMargin.
                } else {
                    assertThat(e.getMessage()).isEqualTo("not able to generate random rowIdx when placing the entrance/exit: "
                            + "the map height is too small or the vertical margin is too high according to the entrance/exit "
                            + "height and the north/south edge heights.");
                }
            }
        }
    }

    @Test
    void randomlyPlaceComplexElementsWithTooHighSizeShouldThrowExpectedException() {
        MapPoint[][] mapPointMatrix = new MapPoint[MAP_HEIGHT][MAP_WIDTH];
        for (int rowIdx = 0; rowIdx < MAP_HEIGHT; rowIdx++) {
            for (int colIdx = 0; colIdx < MAP_WIDTH; colIdx++) {
                mapPointMatrix[rowIdx][colIdx] = new MapPoint(rowIdx, colIdx);
            }
        }
        ArrayList<Tuple2<MapPattern, Integer>> complexEltPatterns = new ArrayList<>() {{
            add(new Tuple2<>(
                new MapPattern(new Image[6], MAP_WIDTH + 1, MAP_HEIGHT + 1, false, false, false, false, "pattern1"),
                1));
        }};
        assertThatThrownBy(
            () -> randomlyPlaceComplexElements(mapPointMatrix, MAP_WIDTH, MAP_HEIGHT, 0, 0, complexEltPatterns, 1))
            .isInstanceOf(CannotCreateMapElementException.class)
            .hasMessage("not able to place a complex element based on pattern 'pattern1': the map size can be too "
                + "small or the margins too high according to the relative pattern size and the north/south "
                + "edge heights.");
    }

    @Test
    void randomlyPlaceComplexElementsWithNoMoreSpaceOnMapShouldThrowExpectedException() {
        MapPoint[][] mapPointMatrix = new MapPoint[MAP_HEIGHT][MAP_WIDTH];
        for (int rowIdx = 0; rowIdx < MAP_HEIGHT; rowIdx++) {
            for (int colIdx = 0; colIdx < MAP_WIDTH; colIdx++) {
                mapPointMatrix[rowIdx][colIdx] = new MapPoint(rowIdx, colIdx);
            }
        }

        int maxNbTry = 10;
        int nbPattern1ToPlace = 2;
        ArrayList<Tuple2<MapPattern, Integer>> complexEltPatterns = new ArrayList<>() {{
            // the 2 complex elements to place has the size of the map :)!
            add(new Tuple2<>(
                new MapPattern(new Image[MAP_WIDTH * MAP_HEIGHT], MAP_WIDTH, MAP_HEIGHT, false, false, false, false,
                    "pattern1"),
                nbPattern1ToPlace));
        }};
        assertThatThrownBy(() -> randomlyPlaceComplexElements(mapPointMatrix, MAP_WIDTH, MAP_HEIGHT, 0, 0, complexEltPatterns, maxNbTry)).
                isInstanceOf(CannotCreateMapElementException.class)
                .hasMessage("not able to place a complex element based on pattern 'pattern1', despite a certain "
                        + "number of tries (10): no more room on the map to place it.");
    }

    @Test
    void randomlyPlaceSingleElementsShouldFillAvailableCasesWithSingleImmutableObstacles()
        throws CannotCreateMapElementException {
        MapPoint[][] mapPointMatrix = new MapPoint[MAP_HEIGHT][MAP_WIDTH];
        for (int rowIdx = 0; rowIdx < MAP_HEIGHT; rowIdx++) {
            for (int colIdx = 0; colIdx < MAP_WIDTH; colIdx++) {
                mapPointMatrix[rowIdx][colIdx] = new MapPoint(rowIdx, colIdx);
            }
        }
        randomlyPlaceSingleElements(mapPointMatrix, MAP_WIDTH, MAP_HEIGHT, 100, 0, 0, 0);
        for (int rowIdx = 0; rowIdx < MAP_HEIGHT; rowIdx++) {
            for (int colIdx = 0; colIdx < MAP_WIDTH; colIdx++) {
                assertThat(mapPointMatrix[rowIdx][colIdx].isAvailable()).isFalse();
                assertThat(mapPointMatrix[rowIdx][colIdx].isMutable()).isFalse();
                assertThat(mapPointMatrix[rowIdx][colIdx].isPathway()).isFalse();
            }
        }
    }

    @Test
    void randomlyPlaceSingleElementsShouldFillAvailableCasesWithSingleMutableObstacles()
        throws CannotCreateMapElementException {
        MapPoint[][] mapPointMatrix = new MapPoint[MAP_HEIGHT][MAP_WIDTH];
        for (int rowIdx = 0; rowIdx < MAP_HEIGHT; rowIdx++) {
            for (int colIdx = 0; colIdx < MAP_WIDTH; colIdx++) {
                mapPointMatrix[rowIdx][colIdx] = new MapPoint(rowIdx, colIdx);
            }
        }
        randomlyPlaceSingleElements(mapPointMatrix, MAP_WIDTH, MAP_HEIGHT, 0, 100, 0, 0);
        for (int rowIdx = 0; rowIdx < MAP_HEIGHT; rowIdx++) {
            for (int colIdx = 0; colIdx < MAP_WIDTH; colIdx++) {
                assertThat(mapPointMatrix[rowIdx][colIdx].isAvailable()).isFalse();
                assertThat(mapPointMatrix[rowIdx][colIdx].isMutable()).isTrue();
                assertThat(mapPointMatrix[rowIdx][colIdx].isPathway()).isFalse();
            }
        }
    }

    @Test
    void randomlyPlaceSingleElementsShouldFillAvailableCasesWithVirginSinglePathways()
        throws CannotCreateMapElementException {
        MapPoint[][] mapPointMatrix = new MapPoint[MAP_HEIGHT][MAP_WIDTH];
        for (int rowIdx = 0; rowIdx < MAP_HEIGHT; rowIdx++) {
            for (int colIdx = 0; colIdx < MAP_WIDTH; colIdx++) {
                mapPointMatrix[rowIdx][colIdx] = new MapPoint(rowIdx, colIdx);
            }
        }
        randomlyPlaceSingleElements(mapPointMatrix, MAP_WIDTH, MAP_HEIGHT, 0, 0, 0, 0);
        for (int rowIdx = 0; rowIdx < MAP_HEIGHT; rowIdx++) {
            for (int colIdx = 0; colIdx < MAP_WIDTH; colIdx++) {
                assertThat(mapPointMatrix[rowIdx][colIdx].isAvailable()).isFalse();
                assertThat(mapPointMatrix[rowIdx][colIdx].isMutable()).isFalse();
                assertThat(mapPointMatrix[rowIdx][colIdx].isPathway()).isTrue();
                assertThat(mapPointMatrix[rowIdx][colIdx].getImage()).isEqualTo(ImagesLoader.getVirginSinglePathway());
                assertThat(mapPointMatrix[rowIdx][colIdx].getImages()).isNull();
            }
        }
    }

    @Test
    void randomlyPlaceSingleElementsShouldFillAvailableCasesWithDecoratedSinglePathways()
        throws CannotCreateMapElementException {
        MapPoint[][] mapPointMatrix = new MapPoint[MAP_HEIGHT][MAP_WIDTH];
        for (int rowIdx = 0; rowIdx < MAP_HEIGHT; rowIdx++) {
            for (int colIdx = 0; colIdx < MAP_WIDTH; colIdx++) {
                mapPointMatrix[rowIdx][colIdx] = new MapPoint(rowIdx, colIdx);
            }
        }
        randomlyPlaceSingleElements(mapPointMatrix, MAP_WIDTH, MAP_HEIGHT, 0, 0, 100, 0);
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
    void randomlyPlaceSingleElementsShouldFillAvailableCasesWithDynamicSinglePathways()
        throws CannotCreateMapElementException {
        MapPoint[][] mapPointMatrix = new MapPoint[MAP_HEIGHT][MAP_WIDTH];
        for (int rowIdx = 0; rowIdx < MAP_HEIGHT; rowIdx++) {
            for (int colIdx = 0; colIdx < MAP_WIDTH; colIdx++) {
                mapPointMatrix[rowIdx][colIdx] = new MapPoint(rowIdx, colIdx);
            }
        }
        randomlyPlaceSingleElements(mapPointMatrix, MAP_WIDTH, MAP_HEIGHT, 0, 0, 100, 100);
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

    @Test
    void randomlyPlaceBonusShouldFillMutableCasesOnlyWithTheExpectedNumberOfBonus()
        throws CannotPlaceBonusOnMapException {
        MapPoint[][] mapPointMatrix = new MapPoint[MAP_HEIGHT][MAP_WIDTH];
        int nbMutableCase = 0;
        for (int rowIdx = 0; rowIdx < MAP_HEIGHT; rowIdx++) {
            for (int colIdx = 0; colIdx < MAP_WIDTH; colIdx++) {
                mapPointMatrix[rowIdx][colIdx] = new MapPoint(rowIdx, colIdx);
                if (nbMutableCase < 100) {
                    mapPointMatrix[rowIdx][colIdx].setMutable(true);
                    nbMutableCase++;
                } else {
                    mapPointMatrix[rowIdx][colIdx].setMutable(false);
                }
            }
        }
        randomlyPlaceBonus(mapPointMatrix, MAP_WIDTH, MAP_HEIGHT, 10, 20, 30, 40);

        int nbBonusBomb = 0;
        int nbBonusFlame = 0;
        int nbBonusHeart = 0;
        int nbBonusRoller = 0;
        for (int rowIdx = 0; rowIdx < MAP_HEIGHT; rowIdx++) {
            for (int colIdx = 0; colIdx < MAP_WIDTH; colIdx++) {
                if (mapPointMatrix[rowIdx][colIdx].getAttachedBonus() != null) {
                    assertThat(mapPointMatrix[rowIdx][colIdx].isMutable()).isTrue();
                    switch (mapPointMatrix[rowIdx][colIdx].getAttachedBonus()) {
                        case TYPE_BONUS_BOMB -> nbBonusBomb++;
                        case TYPE_BONUS_FLAME -> nbBonusFlame++;
                        case TYPE_BONUS_HEART -> nbBonusHeart++;
                        case TYPE_BONUS_ROLLER -> nbBonusRoller++;

                    }
                }
            }
        }
        assertThat(nbBonusBomb).isEqualTo(10);
        assertThat(nbBonusFlame).isEqualTo(20);
        assertThat(nbBonusHeart).isEqualTo(30);
        assertThat(nbBonusRoller).isEqualTo(40);
    }

    @Test
    void randomlyPlaceBonusWithTooManyBonusToPlaceShouldThrowTheExpectedExcpetion() {
        MapPoint[][] mapPointMatrix = new MapPoint[MAP_HEIGHT][MAP_WIDTH]; // 200 cases.
        for (int rowIdx = 0; rowIdx < MAP_HEIGHT; rowIdx++) {
            for (int colIdx = 0; colIdx < MAP_WIDTH; colIdx++) {
                mapPointMatrix[rowIdx][colIdx] = new MapPoint(rowIdx, colIdx);
                mapPointMatrix[rowIdx][colIdx].setMutable(true);
            }
        }
        assertThatThrownBy(
            () -> randomlyPlaceBonus(mapPointMatrix, MAP_WIDTH, MAP_HEIGHT, 200, 1, 0, 0)). // 200 + 1 bonus.
            isInstanceOf(CannotPlaceBonusOnMapException.class)
            .hasMessage("not able to place bonus 'bonus_flame' on map, no more available mutable.");
    }
}
