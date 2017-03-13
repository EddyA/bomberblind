package map.ctrl;

import exceptions.CannotCreateMapElementException;
import exceptions.CannotPlaceBonusOnMapException;
import map.MapPattern;
import map.MapPoint;
import sprite.settled.BonusType;
import utils.Tuple2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static map.ctrl.PatternMethods.*;
import static map.ctrl.SingleMethods.*;

public class GenerationMethods {

    /**
     * Randomly place castles.
     *
     * @param mapPointMatrix      the map (represented by its matrix of MapPoint)
     * @param mapWidth             the map width
     * @param mapHeight            the map height
     * @param hMargin              the vertical margin (to put castles at x cases from the left/right sides of the map)
     * @param vMargin              the horizontal margin (to put castles a minimum of y cases from the top/bottom of the map)
     * @param northEdgeHeight      the height of the north edge
     * @param southEdgeHeight      the height of the south edge
     * @param patterns             the tuple of castle patterns to place (2 elements)
     * @param perDynamicPathwayElt the percentage of dynamic pathway elements to place arround the castles
     * @return the MapPoint of the two placed castles
     * @throws CannotCreateMapElementException as soon as a castle cannot be placed
     */
    public static Tuple2<MapPoint, MapPoint> randomlyPlaceCastles(MapPoint[][] mapPointMatrix,
                                                                  int mapWidth,
                                                                  int mapHeight,
                                                                  int hMargin,
                                                                  int vMargin,
                                                                  int northEdgeHeight,
                                                                  int southEdgeHeight,
                                                                  Tuple2<MapPattern, MapPattern> patterns,
                                                                  int perDynamicPathwayElt)
            throws CannotCreateMapElementException {

        // check zelda.map.properties values (mapWidth and hMargin).
        if (mapWidth / 2 - patterns.getFirst().getWidth() - hMargin <= 0) {
            throw new CannotCreateMapElementException("not able to generate random colIdx when placing castles: "
                    + "the map width is too small or the horizontal margin too high according to the castles width.");
        }
        try {
            // 1st castle.
            int ySpCastleT1 = generateRandomRowIdx(mapHeight, northEdgeHeight, southEdgeHeight,
                    patterns.getFirst().getHeight(), vMargin);
            placeCastleOnMap(mapPointMatrix, mapWidth, mapHeight, patterns.getFirst(), ySpCastleT1, hMargin,
                    perDynamicPathwayElt);

            // 2nd castle.
            int xSpCastleT2 = mapWidth - hMargin - patterns.getSecond().getWidth();
            int ySpCastleT2 = generateRandomRowIdx(mapHeight, northEdgeHeight, southEdgeHeight,
                    patterns.getSecond().getHeight(), vMargin);
            placeCastleOnMap(mapPointMatrix, mapWidth, mapHeight, patterns.getSecond(), ySpCastleT2, xSpCastleT2,
                    perDynamicPathwayElt);

            return new Tuple2<>(mapPointMatrix[ySpCastleT1][hMargin], mapPointMatrix[ySpCastleT2][xSpCastleT2]);
        } catch (IllegalArgumentException e) {
            throw new CannotCreateMapElementException("not able to generate random rowIdx when placing castles: "
                    + "the map height is too small or the vertical margin is too high according to the castle height "
                    + "and the north/south edge heights.");
        }
    }

    /**
     * Randomly place complex elements.
     * If an element cannot be placed after a certain number of try, it is ignoring.
     *
     * @param mapPointMatrix the map (represented by its matrix of MapPoint)
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
                                                    Map<MapPattern, Integer> patterns,
                                                    int maxNbTry)
            throws CannotCreateMapElementException {
        for (Map.Entry<MapPattern, Integer> eltConf : patterns.entrySet()) {
            try {
                for (int eltIdx = 0; eltIdx < eltConf.getValue(); eltIdx++) {
                    int nbTry = 0;
                    while (true) {
                        int xSpElt = generateRandomColIdx(mapWidth, 0, 0, eltConf.getKey().getWidth(), 0);
                        int ySpElt = generateRandomRowIdx(mapHeight, northEdgeHeight, southEdgeHeight,
                                eltConf.getKey().getHeight(), 0);
                        if (!placePatternOnMap(mapPointMatrix, mapWidth, mapHeight, eltConf.getKey(), ySpElt, xSpElt)) {
                            if (nbTry < maxNbTry) {
                                nbTry++;
                            } else {
                                throw new CannotCreateMapElementException("not able to place a complex element based on pattern '" +
                                        eltConf.getKey().getName() + "', despite a certain number of tries (" + String.valueOf(maxNbTry) +
                                        "): no more room on the map to place it.");
                            }
                        } else {
                            break;
                        }
                    }
                }
            } catch (IllegalArgumentException e) {
                throw new CannotCreateMapElementException("not able to place a complex element based on pattern '"
                        + eltConf.getKey().getName() + "': the map size can be too small or the margins " +
                        "too high according to the relative pattern size and the north/south edge heights.");
            }
        }
    }

    /**
     * Randomly place single elements.
     *
     * @param mapPointMatrix   the map (represented by its matrix of MapPoint)
     * @param mapWidth          the map width
     * @param mapHeight         the map height
     * @param perSingleMutable  the percentage of single mutable to place among available cases
     * @param perSingleObstacle the percentage of single obstacle to place among available cases
     * @param perDynPathwayElt  the percentage of dynamic pathway elements to place among available cases
     * @throws CannotCreateMapElementException as soon as a castle cannot be placed
     */
    public static void randomlyPlaceSingleElements(MapPoint[][] mapPointMatrix,
                                                   int mapWidth,
                                                   int mapHeight,
                                                   int perSingleMutable,
                                                   int perSingleObstacle,
                                                   int perDynPathwayElt)
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
            if (randomPercent < perSingleObstacle) {
                if (!placeSingleObstacleOnMap(mapPoint)) {
                    throw new CannotCreateMapElementException("not able to create a single obstacle.");
                }
            } else if (randomPercent < perSingleObstacle + perSingleMutable) {
                if (!placeSingleMutableOnMap(mapPoint)) {
                    throw new CannotCreateMapElementException("not able to create a single mutable.");
                }
            } else {
                if (!placeSinglePathwayOnMap(mapPoint, perDynPathwayElt)) {
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

        // create list of mutable cases.
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
            int ptIdx = Math.abs(R.nextInt(mutableCases.size())); // randomly choose a mutable case.
            MapPoint mapPoint = mutableCases.get(ptIdx);
            mapPoint.setAttachedBonus(bonusToPlace);
            mutableCases.remove(ptIdx);
        }
    }
}
