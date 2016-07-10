package map;

import static images.ImagesLoader.IMAGE_SIZE;

public class RMapUtils {

    /**
     * Return the top rowIdx of a character given its map ordinate.
     */
    protected static int getTopRowIdxIfOrdIs(int yCharacter) {
        return (yCharacter - IMAGE_SIZE / 2) / IMAGE_SIZE;
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
        return (xCharacter - IMAGE_SIZE / 2) / IMAGE_SIZE;
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
        boolean isCrossing = false;
        if (xCharacter - (IMAGE_SIZE / 2) < 0 ||
                xCharacter + (IMAGE_SIZE / 2) > rMap.mapWidth * IMAGE_SIZE) {
            isCrossing = true;
        }
        if (yCharacter - (IMAGE_SIZE / 2) < 0 ||
                yCharacter > rMap.mapHeight * IMAGE_SIZE) {
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
        boolean isCrossing = false;
        if (!rMap.myMap[getTopRowIdxIfOrdIs(yCharacter)][getMostLeftColIdxIfAbsIs(xCharacter)].isPathway() ||
                !rMap.myMap[getTopRowIdxIfOrdIs(yCharacter)][getMostRightColIdxIfAbsIs(xCharacter)].isPathway() ||
                !rMap.myMap[getBottomRowIdxIfOrdIs(yCharacter)][getMostLeftColIdxIfAbsIs(xCharacter)].isPathway() ||
                !rMap.myMap[getBottomRowIdxIfOrdIs(yCharacter)][getMostRightColIdxIfAbsIs(xCharacter)].isPathway()) {
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
        boolean isDying = false;
        if (!isInvincible &&
                (rMap.myMap[getTopRowIdxIfOrdIs(yCharacter)][getMostLeftColIdxIfAbsIs(xCharacter)].isBurning() ||
                rMap.myMap[getTopRowIdxIfOrdIs(yCharacter)][getMostRightColIdxIfAbsIs(xCharacter)].isBurning() ||
                rMap.myMap[getBottomRowIdxIfOrdIs(yCharacter)][getMostLeftColIdxIfAbsIs(xCharacter)].isBurning() ||
                rMap.myMap[getBottomRowIdxIfOrdIs(yCharacter)][getMostRightColIdxIfAbsIs(xCharacter)].isBurning())) {
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
        boolean isCrossing = false;
        /*if ((rMap.myMap[getTopRowIdxIfOrdIs(yCharacter)][getMostLeftColIdxIfAbsIs(xCharacter)].isBombing() &&
                getTopRowIdxIfOrdIs(yCharacter) != getTopRowIdxIfOrdIs(bbMan.getYMap()) &&
                getMostLeftColIdxIfAbsIs(xCharacter) != getMostLeftColIdxIfAbsIs(bbMan.getXMap())
        ) || (rMap.myMap[getTopRowIdxIfOrdIs(yCharacter)][getMostRightColIdxIfAbsIs(xCharacter)].isBombing() &&
                getTopRowIdxIfOrdIs(yCharacter) != getTopRowIdxIfOrdIs(bbMan.getYMap()) &&
                getMostRightColIdxIfAbsIs(xCharacter) != getMostRightColIdxIfAbsIs(bbMan.getXMap())
        ) || (rMap.myMap[getBottomRowIdxIfOrdIs(yCharacter)][getMostLeftColIdxIfAbsIs(xCharacter)].isBombing() &&
                getBottomRowIdxIfOrdIs(yCharacter) != getBottomRowIdxIfOrdIs(bbMan.getYMap()) &&
                getMostLeftColIdxIfAbsIs(xCharacter) != getMostLeftColIdxIfAbsIs(bbMan.getXMap())
        ) || (rMap.myMap[getBottomRowIdxIfOrdIs(yCharacter)][getMostRightColIdxIfAbsIs(xCharacter)].isBombing() &&
                getBottomRowIdxIfOrdIs(yCharacter) != getBottomRowIdxIfOrdIs(bbMan.getYMap()) &&
                getMostRightColIdxIfAbsIs(xCharacter) != getMostRightColIdxIfAbsIs(bbMan.getXMap()))) {
            isCrossing = true;
        }*/
        return isCrossing;
    }
}
