package map;

import static images.ImagesLoader.IMAGE_SIZE;

public class RMapUtils {

    /**
     * Return the top rowIdx of a character given its map ordinate.
     */
    protected static int getTopRowIdxIfOrdIs(int yCharacter) {
        return (yCharacter - IMAGE_SIZE / 2) < 0 ? -1 : (yCharacter - IMAGE_SIZE / 2) / IMAGE_SIZE;
    }

    /**
     * Return the bottom rowIdx of a character given its map ordinate.
     */
    protected static int getBottomRowIdxIfOrdIs(int yCharacter) {
        return yCharacter / IMAGE_SIZE;
    }

    /**
     * Return the most left colIdx of a character given its map abscissa.
     */
    protected static int getMostLeftColIdxIfAbsIs(int xCharacter) {
        return (xCharacter - IMAGE_SIZE / 2) < 0 ? -1 : (xCharacter - IMAGE_SIZE / 2) / IMAGE_SIZE;
    }

    /**
     * Return the most right colIdx of a character given its map abscissa.
     */
    protected static int getMostRightColIdxIfAbsIs(int xCharacter) {
        return (xCharacter + IMAGE_SIZE / 2 - 1) / IMAGE_SIZE;
    }

    /**
     * Is the character crossing a map limit?
     *
     * @param rMap       the map
     * @param xCharacter the character abscissa
     * @param yCharacter the character ordinate
     * @return true if the character is crossing a map limit, false otherwise
     */
    public static boolean isCharacterCrossingMapLimit(RMap rMap, int xCharacter, int yCharacter) {
        int topRowIdx = getTopRowIdxIfOrdIs(yCharacter);
        int bottomRowIdx = getBottomRowIdxIfOrdIs(yCharacter);
        int mostLeftColIdx = getMostLeftColIdxIfAbsIs(xCharacter);
        int mostRightColIdx = getMostRightColIdxIfAbsIs(xCharacter);
        boolean isCrossing = false;

        if (topRowIdx < 0 || bottomRowIdx >= rMap.mapHeight ||
                mostLeftColIdx < 0 || mostRightColIdx >= rMap.mapWidth) {
            isCrossing = true;
        }
        return isCrossing;
    }

    /**
     * Is the character crossing an obstacle?
     *
     * @param rMap       the map
     * @param xCharacter the character abscissa
     * @param yCharacter the character ordinate
     * @return true if the character is crossing an obstacle, false otherwise
     */
    public static boolean isCharacterCrossingObstacle(RMap rMap, int xCharacter, int yCharacter) {
        int topRowIdx = getTopRowIdxIfOrdIs(yCharacter);
        int bottomRowIdx = getBottomRowIdxIfOrdIs(yCharacter);
        int mostLeftColIdx = getMostLeftColIdxIfAbsIs(xCharacter);
        int mostRightColIdx = getMostRightColIdxIfAbsIs(xCharacter);

        boolean isCrossing = false;
        if (topRowIdx < 0 ||
                bottomRowIdx > (rMap.mapHeight - 1) ||
                mostLeftColIdx < 0 ||
                mostRightColIdx > (rMap.mapWidth - 1) ||
                !rMap.myMap[topRowIdx][mostLeftColIdx].isPathway() ||
                !rMap.myMap[topRowIdx][mostRightColIdx].isPathway() ||
                !rMap.myMap[bottomRowIdx][mostLeftColIdx].isPathway() ||
                !rMap.myMap[bottomRowIdx][mostRightColIdx].isPathway()) {
            isCrossing = true;
        }
        return isCrossing;
    }

    /**
     * Should the character Die?
     *
     * @param rMap       the map
     * @param xCharacter the character abscissa
     * @param yCharacter the character ordinate
     * @return true if the character should die, false otherwise
     */
    public static boolean shouldCharacterDie(RMap rMap, boolean isInvincible, int xCharacter, int yCharacter) {
        int topRowIdx = getTopRowIdxIfOrdIs(yCharacter);
        int bottomRowIdx = getBottomRowIdxIfOrdIs(yCharacter);
        int mostLeftColIdx = getMostLeftColIdxIfAbsIs(xCharacter);
        int mostRightColIdx = getMostRightColIdxIfAbsIs(xCharacter);
        boolean isDying = false;

        if (!isInvincible &&
                (rMap.myMap[topRowIdx][mostLeftColIdx].isBurning() ||
                        rMap.myMap[topRowIdx][mostRightColIdx].isBurning() ||
                        rMap.myMap[bottomRowIdx][mostLeftColIdx].isBurning() ||
                        rMap.myMap[bottomRowIdx][mostRightColIdx].isBurning())) {
            isDying = true;
        }
        return isDying;
    }

    /**
     * Is the character crossing a bomb?
     * note: this function is mainly used to avoid being obstructed by the bomb the character just put.
     *
     * @param rMap       the map
     * @param xCharacter the character abscissa
     * @param yCharacter the character ordinate
     * @return true if the character is crossing a bomb, false otherwise
     */
    public static boolean isCharacterCrossingBomb(RMap rMap, int xCharacter, int yCharacter) {
        int topRowIdx = getTopRowIdxIfOrdIs(yCharacter);
        int bottomRowIdx = getBottomRowIdxIfOrdIs(yCharacter);
        int mostLeftColIdx = getMostLeftColIdxIfAbsIs(xCharacter);
        int mostRightColIdx = getMostRightColIdxIfAbsIs(xCharacter);

        boolean isCrossing = false;
        /*if ((rMap.myMap[topRowIdx][mostLeftColIdx].isBombing() &&
                topRowIdx != getTopRowIdxIfOrdIs(bbMan.getYMap()) &&
                mostLeftColIdx != getMostLeftColIdxIfAbsIs(bbMan.getXMap())
        ) || (rMap.myMap[topRowIdx][mostRightColIdx].isBombing() &&
                topRowIdx != getTopRowIdxIfOrdIs(bbMan.getYMap()) &&
                mostRightColIdx != getMostRightColIdxIfAbsIs(bbMan.getXMap())
        ) || (rMap.myMap[bottomRowIdx][mostLeftColIdx].isBombing() &&
                bottomRowIdx != getBottomRowIdxIfOrdIs(bbMan.getYMap()) &&
                mostLeftColIdx != getMostLeftColIdxIfAbsIs(bbMan.getXMap())
        ) || (rMap.myMap[bottomRowIdx][mostRightColIdx].isBombing() &&
                bottomRowIdx != getBottomRowIdxIfOrdIs(bbMan.getYMap()) &&
                mostRightColIdx != getMostRightColIdxIfAbsIs(bbMan.getXMap()))) {
            isCrossing = true;
        }*/
        return isCrossing;
    }
}
