package map.ctrl;

import exceptions.CannotCreateMapElementException;
import exceptions.CannotPlaceBonusOnMapException;
import map.MapPattern;
import map.MapPoint;
import sprite.settled.BonusType;
import utils.Tuple2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static map.ctrl.PatternMethods.*;
import static map.ctrl.SingleMethods.*;

public class GenerationMethods {

    /**
     * Randomly place the entrance and exit elements on map.
     * The entrance/exit perimeters are secured with single pathways.
     *
     * @param mapPointMatrix            the map (represented by its matrix of MapPoint)
     * @param mapWidth                  the map width
     * @param mapHeight                 the map height
     * @param hMargin                   the vertical margin (to put the entrance/exit at x cases from the left/right sides of the map)
     * @param vMargin                   the horizontal margin (to put the entrance/exit at a minimum of y cases from the top/bottom of the map)
     * @param northEdgeHeight           the height of the north edge
     * @param southEdgeHeight           the height of the south edge
     * @param patterns                  the tuple of entrance/exit patterns to place (2 elements)
     * @param perDecoratedSinglePathway the percentage of decorated elements to place among single pathway
     * @param perDynamicSinglePathway   the percentage of dynamic elements to place among decorated single pathway
     * @return the tuple of entrance/exit start points (i.e. corner north/west of the elements) (2 elements)
     * @throws CannotCreateMapElementException as soon as a pattern cannot be placed
     */
    public static Tuple2<MapPoint, MapPoint> randomlyPlaceEntranceAndExit(MapPoint[][] mapPointMatrix,
                                                                          int mapWidth,
                                                                          int mapHeight,
                                                                          int hMargin,
                                                                          int vMargin,
                                                                          int northEdgeHeight,
                                                                          int southEdgeHeight,
                                                                          Tuple2<MapPattern, MapPattern> patterns,
                                                                          int perDecoratedSinglePathway,
                                                                          int perDynamicSinglePathway)
            throws CannotCreateMapElementException {

        // check map properties (mapWidth and hMargin).
        if (mapWidth / 2 - patterns.getFirst().getWidth() - hMargin <= 0) {
            throw new CannotCreateMapElementException("not able to generate random colIdx when placing the entrance/exit: "
                    + "the map width is too small or the horizontal margin too high according to the entrance/exit width.");
        }
        try {
            // entrance.
            int ySpEntrance = generateRandomRowIdx(mapHeight, northEdgeHeight, southEdgeHeight,
                    patterns.getFirst().getHeight(), vMargin);
            placePatternOnMapAndSecurePerimeter(mapPointMatrix,
                    mapWidth,
                    mapHeight,
                    patterns.getFirst(),
                    ySpEntrance,
                    hMargin,
                    perDecoratedSinglePathway,
                    perDynamicSinglePathway);

            // exit.
            int xSpExit = mapWidth - hMargin - patterns.getSecond().getWidth();
            int ySpExit = generateRandomRowIdx(mapHeight, northEdgeHeight, southEdgeHeight,
                    patterns.getSecond().getHeight(), vMargin);
            placePatternOnMapAndSecurePerimeter(mapPointMatrix,
                    mapWidth,
                    mapHeight,
                    patterns.getSecond(),
                    ySpExit,
                    xSpExit,
                    perDecoratedSinglePathway,
                    perDynamicSinglePathway);

            return new Tuple2<>(mapPointMatrix[ySpEntrance][hMargin], mapPointMatrix[ySpExit][xSpExit]);
        } catch (IllegalArgumentException e) {
            throw new CannotCreateMapElementException("not able to generate random rowIdx when placing the entrance/exit: "
                    + "the map height is too small or the vertical margin is too high according to the entrance/exit height "
                    + "and the north/south edge heights.");
        }
    }

    /**
     * Randomly place complex elements.
     * If an element cannot be placed after a certain number of try, it is ignoring.
     *
     * @param mapPointMatrix  the map (represented by its matrix of MapPoint)
     * @param mapWidth        the map width
     * @param mapHeight       the map height
     * @param northEdgeHeight the height of the north edge
     * @param southEdgeHeight the height of the south edge
     * @param patterns        the map of patterns with the relative number of occurences to place
     * @param maxNbTry        the max nummber of try to place each complex element on map
     *                        (give up if the number of try id reached)
     * @throws CannotCreateMapElementException if an element cannot be placed because of the map configuration
     *                                         (e.g. the map size can be too small or the margins too high according
     *                                         to the relative pattern size and the north/south edge heights).
     */
    public static void randomlyPlaceComplexElements(MapPoint[][] mapPointMatrix,
                                                    int mapWidth,
                                                    int mapHeight,
                                                    int northEdgeHeight,
                                                    int southEdgeHeight,
                                                    ArrayList<Tuple2<MapPattern, Integer>> patterns,
                                                    int maxNbTry)
            throws CannotCreateMapElementException {
        for (Tuple2<MapPattern, Integer> pattern : patterns) {
            try {
                for (int eltIdx = 0; eltIdx < pattern.getSecond(); eltIdx++) {
                    int nbTry = 0;
                    while (true) {
                        int xSpElt = generateRandomColIdx(mapWidth, 0, 0, pattern.getFirst().getWidth(), 0);
                        int ySpElt = generateRandomRowIdx(mapHeight, northEdgeHeight, southEdgeHeight,
                                pattern.getFirst().getHeight(), 0);
                        if (!placePatternOnMap(mapPointMatrix, mapWidth, mapHeight, pattern.getFirst(), ySpElt, xSpElt)) {
                            if (nbTry < maxNbTry) {
                                nbTry++;
                            } else {
                                throw new CannotCreateMapElementException("not able to place a complex element based on pattern '" +
                                        pattern.getFirst().getName() + "', despite a certain number of tries (" +
                                        String.valueOf(maxNbTry) + "): no more room on the map to place it.");
                            }
                        } else {
                            break;
                        }
                    }
                }
            } catch (IllegalArgumentException e) {
                throw new CannotCreateMapElementException("not able to place a complex element based on pattern '"
                        + pattern.getFirst().getName() + "': the map size can be too small or the margins " +
                        "too high according to the relative pattern size and the north/south edge heights.");
            }
        }
    }

    /**
     * Randomly place single elements.
     *
     * @param mapPointMatrix             the map (represented by its matrix of MapPoint)
     * @param mapWidth                   the map width
     * @param mapHeight                  the map height
     * @param perSingleImmutableObstacle the percentage of single immutable obstacle to place among available cases
     * @param perSingleMutableObstacle   the percentage of single mutable obstacle to place among available cases
     * @param perDecoratedSinglePathway  the percentage of decorated elements to place among single pathway
     * @param perDynamicSinglePathway    the percentage of dynamic elements to place among decorated single pathway
     * @throws CannotCreateMapElementException as soon as an element cannot be placed
     */
    public static void randomlyPlaceSingleElements(MapPoint[][] mapPointMatrix,
                                                   int mapWidth,
                                                   int mapHeight,
                                                   int perSingleImmutableObstacle,
                                                   int perSingleMutableObstacle,
                                                   int perDecoratedSinglePathway,
                                                   int perDynamicSinglePathway)
            throws CannotCreateMapElementException {

        // create list of empty cases.
        List<MapPoint> emptyPtList = new ArrayList<>();
        for (int rowIdx = 0; rowIdx < mapHeight; rowIdx++) {
            for (int colIdx = 0; colIdx < mapWidth; colIdx++) {
                if (mapPointMatrix[rowIdx][colIdx].isAvailable()) {
                    emptyPtList.add(mapPointMatrix[rowIdx][colIdx]);
                }
            }
        }
        int nbEmptyPt = emptyPtList.size();

        // place single elements.
        Random R = new Random();
        for (int i = 0; i < nbEmptyPt; i++) {

            int randomPercent = Math.abs(R.nextInt(100)); // randomly choose a single element.
            int ptIdx = Math.abs(R.nextInt(emptyPtList.size())); // randomly choose an empty case.
            MapPoint mapPoint = emptyPtList.get(ptIdx);
            if (randomPercent < perSingleImmutableObstacle) {
                if (!placeSingleImmutableObstacleOnMap(mapPoint)) {
                    throw new CannotCreateMapElementException("not able to create a single immutable obstacle.");
                }
            } else if (randomPercent < perSingleImmutableObstacle + perSingleMutableObstacle) {
                if (!placeSingleMutableObstacleOnMap(mapPoint)) {
                    throw new CannotCreateMapElementException("not able to create a single mutable obstacle.");
                }
            } else {
                if (!placeSinglePathwayOnMap(mapPoint, perDecoratedSinglePathway, perDynamicSinglePathway)) {
                    throw new CannotCreateMapElementException("not able to create a single pathway.");
                }
            }
            emptyPtList.remove(ptIdx);
        }
    }

    /**
     * Randomly place bonus.
     *
     * @param mapPointMatrix the map (represented by its matrix of MapPoint)
     * @param mapWidth       the map width
     * @param mapHeight      the map height
     * @param nbBonusBomb    the number of bonus bomb
     * @param nbBonusFlame   the number of bonus flame
     * @param nbBonusHeart   the number of bonus heart
     * @param nbBonusRoller  the number of bonus roller
     */
    public static void randomlyPlaceBonus(MapPoint[][] mapPointMatrix,
                                          int mapWidth,
                                          int mapHeight,
                                          int nbBonusBomb,
                                          int nbBonusFlame,
                                          int nbBonusHeart,
                                          int nbBonusRoller) throws CannotPlaceBonusOnMapException {

        // create list of single mutable obstacle.
        List<MapPoint> mutableCases = new ArrayList<>();
        for (int rowIdx = 0; rowIdx < mapHeight; rowIdx++) {
            for (int colIdx = 0; colIdx < mapWidth; colIdx++) {
                if (mapPointMatrix[rowIdx][colIdx].isMutable()) {
                    mutableCases.add(mapPointMatrix[rowIdx][colIdx]);
                }
            }
        }

        // place bonus.
        Random R = new Random();
        for (int i = 0; i < nbBonusBomb + nbBonusFlame + nbBonusHeart + nbBonusRoller; i++) {

            // compute the bonus to place.
            BonusType bonusToPlace;
            if (i < nbBonusBomb) {
                bonusToPlace = BonusType.TYPE_BONUS_BOMB;
            } else if (i < nbBonusBomb + nbBonusFlame) {
                bonusToPlace = BonusType.TYPE_BONUS_FLAME;
            } else if (i < nbBonusBomb + nbBonusFlame + nbBonusHeart) {
                bonusToPlace = BonusType.TYPE_BONUS_HEART;
            } else {
                bonusToPlace = BonusType.TYPE_BONUS_ROLLER;
            }

            // place the bonus.
            if (mutableCases.size() <= 0) {
                throw new CannotPlaceBonusOnMapException("not able to place bonus '" +
                        BonusType.getlabel(bonusToPlace).orElse("no_name")
                        + "' on map, no more available mutable.");
            }
            int ptIdx = Math.abs(R.nextInt(mutableCases.size())); // randomly choose a single mutable obstacle.
            MapPoint mapPoint = mutableCases.get(ptIdx);
            mapPoint.setAttachedBonus(bonusToPlace);
            mutableCases.remove(ptIdx);
        }
    }
}
