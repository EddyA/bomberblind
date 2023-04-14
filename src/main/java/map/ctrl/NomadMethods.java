package map.ctrl;

import lombok.experimental.UtilityClass;
import map.MapPoint;
import utils.Direction;
import utils.Tools;
import static utils.Tools.getCharBottomRowIdx;
import static utils.Tools.getCharLeftColIdx;
import static utils.Tools.getCharRightColIdx;
import static utils.Tools.getCharTopRowIdx;

@UtilityClass
public class NomadMethods {

    /**
     * Is the nomad crossing a map limit?
     *
     * @param mapWidth  the map width
     * @param mapHeight the map height
     * @param xChar     the nomad abscissa
     * @param yChar     the nomad ordinate
     * @return true if the nomad is crossing a map limit, false otherwise
     */
    public static boolean isNomadCrossingMapLimit(int mapWidth, int mapHeight, int xChar, int yChar) {
        int topRowIdx = getCharTopRowIdx(yChar);
        int bottomRowIdx = getCharBottomRowIdx(yChar);
        int mostLeftColIdx = getCharLeftColIdx(xChar);
        int mostRightColIdx = getCharRightColIdx(xChar);

        return topRowIdx < 0 || bottomRowIdx >= mapHeight || mostLeftColIdx < 0 || mostRightColIdx >= mapWidth;
    }

    /**
     * Is the nomad crossing an obstacle (i.e. not a pathway)?
     *
     * @param mapPointMatrix the map (represented by its matrix of MapPoint)
     * @param xChar          the nomad abscissa
     * @param yChar          the nomad ordinate
     * @return true if the nomad is crossing an obstacle, false otherwise
     * @implSpec isNomadCrossingMapLimit() must be called before this function!
     */
    public static boolean isNomadCrossingObstacle(MapPoint[][] mapPointMatrix, int xChar, int yChar) {
        int topRowIdx = getCharTopRowIdx(yChar);
        int bottomRowIdx = getCharBottomRowIdx(yChar);
        int mostLeftColIdx = getCharLeftColIdx(xChar);
        int mostRightColIdx = getCharRightColIdx(xChar);

        return !mapPointMatrix[topRowIdx][mostLeftColIdx].isPathway() ||
            !mapPointMatrix[topRowIdx][mostRightColIdx].isPathway() ||
            !mapPointMatrix[bottomRowIdx][mostLeftColIdx].isPathway() ||
            !mapPointMatrix[bottomRowIdx][mostRightColIdx].isPathway();
    }

    /**
     * Is the nomad crossing a mutable obstacle?
     *
     * @param mapPointMatrix the map (represented by its matrix of MapPoint)
     * @param xChar          the nomad abscissa
     * @param yChar          the nomad ordinate
     * @return true if the nomad is crossing a mutable obstacle, false otherwise
     * @implSpec isNomadCrossingMapLimit() must be called before this function!
     */
    public static boolean isNomadCrossingMutableObstacle(MapPoint[][] mapPointMatrix, int xChar, int yChar) {
        int topRowIdx = getCharTopRowIdx(yChar);
        int bottomRowIdx = getCharBottomRowIdx(yChar);
        int mostLeftColIdx = getCharLeftColIdx(xChar);
        int mostRightColIdx = getCharRightColIdx(xChar);

        return mapPointMatrix[topRowIdx][mostLeftColIdx].isMutable() ||
            mapPointMatrix[topRowIdx][mostRightColIdx].isMutable() ||
            mapPointMatrix[bottomRowIdx][mostLeftColIdx].isMutable() ||
            mapPointMatrix[bottomRowIdx][mostRightColIdx].isMutable();
    }

    /**
     * Is the nomad is blocked off by a mutable obstacle (according to its direction).
     *
     * @param mapPointMatrix mapPointMatrix the map (represented by its matrix of MapPoint)
     * @param mapWidth       the map width
     * @param mapHeight      the map height
     * @param xChar          the nomad abscissa
     * @param yChar          the nomad ordinate
     * @param direction      the nomad direction
     * @return the blocking MapPoint if the nomad is blocked off, null otherwise.
     */
    public static MapPoint isNomadBlockedOffByMutableObstacle(MapPoint[][] mapPointMatrix,
                                                              int mapWidth,
                                                              int mapHeight,
                                                              int xChar,
                                                              int yChar,
                                                              Direction direction) {
        MapPoint blockingMutable = null;
        switch (direction) {
            case DIRECTION_NORTH -> {
                if (!isNomadCrossingMapLimit(mapWidth, mapHeight, xChar, yChar - 1) &&
                    isNomadCrossingMutableObstacle(mapPointMatrix, xChar, yChar - 1)) {
                    blockingMutable = mapPointMatrix[getCharTopRowIdx(yChar - 1)][Tools.getCharColIdx(xChar)];
                }
            }
            case DIRECTION_SOUTH -> {
                if (!isNomadCrossingMapLimit(mapWidth, mapHeight, xChar, yChar + 1) &&
                    isNomadCrossingMutableObstacle(mapPointMatrix, xChar, yChar + 1)) {
                    blockingMutable = mapPointMatrix[getCharBottomRowIdx(yChar + 1)][Tools.getCharColIdx(xChar)];

                }
            }
            case DIRECTION_WEST -> {
                if (!isNomadCrossingMapLimit(mapWidth, mapHeight, xChar - 1, yChar) &&
                    isNomadCrossingMutableObstacle(mapPointMatrix, xChar - 1, yChar)) {
                    blockingMutable = mapPointMatrix[Tools.getCharRowIdx(yChar)][getCharLeftColIdx(xChar - 1)];
                }
            }
            case DIRECTION_EAST -> {
                if (!isNomadCrossingMapLimit(mapWidth, mapHeight, xChar + 1, yChar) &&
                    isNomadCrossingMutableObstacle(mapPointMatrix, xChar + 1, yChar)) {
                    blockingMutable = mapPointMatrix[Tools.getCharRowIdx(yChar)][getCharRightColIdx(xChar + 1)];
                }
            }
        }
        return blockingMutable;
    }

    /**
     * Is the nomad crossing an exit point.
     *
     * @param mapPointMatrix mapPointMatrix the map (represented by its matrix of MapPoint)
     * @param mapWidth       the map width
     * @param mapHeight      the map height
     * @param xChar          the nomad abscissa
     * @param yChar          the nomad ordinate
     * @return true if the nomad is crossing an exit point, false otherwise
     */
    public static boolean isNomadCrossingExit(MapPoint[][] mapPointMatrix,
                                              int mapWidth,
                                              int mapHeight,
                                              int xChar,
                                              int yChar) {
        int topRowIdx = getCharTopRowIdx(yChar);
        int bottomRowIdx = getCharBottomRowIdx(yChar);
        int mostLeftColIdx = getCharLeftColIdx(xChar);
        int mostRightColIdx = getCharRightColIdx(xChar);

        return topRowIdx >= 0 && mostLeftColIdx >= 0 && mapPointMatrix[topRowIdx][mostLeftColIdx].isExit() ||
            topRowIdx >= 0 && mostRightColIdx < mapWidth && mapPointMatrix[topRowIdx][mostRightColIdx].isExit() ||
            bottomRowIdx < mapHeight && mostLeftColIdx >= 0 && mapPointMatrix[bottomRowIdx][mostLeftColIdx].isExit() ||
            bottomRowIdx < mapHeight && mostRightColIdx < mapWidth
                && mapPointMatrix[bottomRowIdx][mostRightColIdx].isExit();
    }

    /**
     * Is the nomad burning?
     * i.e. is there a burning case adjoining the nomad?
     *
     * @param mapPointMatrix the map (represented by its matrix of MapPoint)
     * @param xChar          the nomad abscissa
     * @param yChar          the nomad ordinate
     * @return true if the nomad is burning, false otherwise
     * @implSpec isNomadCrossingMapLimit() must be called before this function!
     */
    public static boolean isNomadBurning(MapPoint[][] mapPointMatrix, int xChar, int yChar) {
        int topRowIdx = getCharTopRowIdx(yChar);
        int bottomRowIdx = getCharBottomRowIdx(yChar);
        int mostLeftColIdx = getCharLeftColIdx(xChar);
        int mostRightColIdx = getCharRightColIdx(xChar);

        return mapPointMatrix[topRowIdx][mostLeftColIdx].isBurning() ||
            mapPointMatrix[topRowIdx][mostRightColIdx].isBurning() ||
            mapPointMatrix[bottomRowIdx][mostLeftColIdx].isBurning() ||
            mapPointMatrix[bottomRowIdx][mostRightColIdx].isBurning();
    }

    /**
     * Is the nomad crossing a bomb?
     * note: we can't just use the isNomadCrossingObstacle() algorithm as the nomad can already be
     * on a bombing case - when it just put a bomb - and this particular case cannot be processed the same way.
     *
     * @param mapPointMatrix the map (represented by its matrix of MapPoint)
     * @param xChar          the nomad abscissa
     * @param yChar          the nomad ordinate
     * @param direction      the nomad direction
     * @return true if the nomad is crossing a bomb, false otherwise
     * @implSpec isNomadCrossingMapLimit() must be called before this function!
     */
    public static boolean isNomadCrossingBomb(MapPoint[][] mapPointMatrix, int xChar, int yChar, Direction direction) {
        int topRowIdx = getCharTopRowIdx(yChar);
        int bottomRowIdx = getCharBottomRowIdx(yChar);
        int mostLeftColIdx = getCharLeftColIdx(xChar);
        int mostRightColIdx = getCharRightColIdx(xChar);

        boolean isCrossing = false;
        switch (direction) {
            case DIRECTION_NORTH -> {
                if ((mapPointMatrix[topRowIdx][mostLeftColIdx].isBombing() ||
                    mapPointMatrix[topRowIdx][mostRightColIdx].isBombing()) &&
                    topRowIdx != getCharTopRowIdx(yChar + 1)) {
                    isCrossing = true;
                }
            }
            case DIRECTION_SOUTH -> {
                if ((mapPointMatrix[bottomRowIdx][mostLeftColIdx].isBombing() ||
                    mapPointMatrix[bottomRowIdx][mostRightColIdx].isBombing()) &&
                    bottomRowIdx != getCharBottomRowIdx(yChar - 1)) {
                    isCrossing = true;
                }
            }
            case DIRECTION_WEST -> {
                if ((mapPointMatrix[topRowIdx][mostLeftColIdx].isBombing() ||
                    mapPointMatrix[bottomRowIdx][mostLeftColIdx].isBombing()) &&
                    mostLeftColIdx != getCharLeftColIdx(xChar + 1)) {
                    isCrossing = true;
                }
            }
            case DIRECTION_EAST -> {
                if ((mapPointMatrix[topRowIdx][mostRightColIdx].isBombing() ||
                    mapPointMatrix[bottomRowIdx][mostRightColIdx].isBombing()) &&
                    mostRightColIdx != getCharRightColIdx(xChar - 1)) {
                    isCrossing = true;
                }
            }
        }
        return isCrossing;
    }
}
