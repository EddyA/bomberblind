package map.Ctrl;

import exceptions.OutOfRMapBoundsException;
import map.RMapPoint;

import java.awt.event.KeyEvent;

import static images.ImagesLoader.IMAGE_SIZE;

public class CharacterMethods {

    /**
     * Return the top rowIdx of a character given its map ordinate.
     */
    protected static int getTopRowIdxIfOrdIs(int yChar) {
        return (yChar - IMAGE_SIZE / 2) < 0 ? -1 : (yChar - IMAGE_SIZE / 2) / IMAGE_SIZE;
    }

    /**
     * Return the bottom rowIdx of a character given its map ordinate.
     */
    protected static int getBottomRowIdxIfOrdIs(int yChar) {
        return yChar / IMAGE_SIZE;
    }

    /**
     * Return the most left colIdx of a character given its map abscissa.
     */
    protected static int getMostLeftColIdxIfAbsIs(int xChar) {
        return (xChar - IMAGE_SIZE / 2) < 0 ? -1 : (xChar - IMAGE_SIZE / 2) / IMAGE_SIZE;
    }

    /**
     * Return the most right colIdx of a character given its map abscissa.
     */
    protected static int getMostRightColIdxIfAbsIs(int xChar) {
        return (xChar + IMAGE_SIZE / 2 - 1) / IMAGE_SIZE;
    }

    /**
     * Is the character crossing a map limit?
     *
     * @param mapWidth  the map width
     * @param mapHeight the map height
     * @param xChar     the character abscissa
     * @param yChar     the character ordinate
     * @return true if the character is crossing a map limit, false otherwise
     */
    public static boolean isCharacterCrossingMapLimit(int mapWidth, int mapHeight, int xChar, int yChar) {
        int topRowIdx = getTopRowIdxIfOrdIs(yChar);
        int bottomRowIdx = getBottomRowIdxIfOrdIs(yChar);
        int mostLeftColIdx = getMostLeftColIdxIfAbsIs(xChar);
        int mostRightColIdx = getMostRightColIdxIfAbsIs(xChar);

        boolean isCrossing = false;
        if (topRowIdx < 0 || bottomRowIdx >= mapHeight ||
                mostLeftColIdx < 0 || mostRightColIdx >= mapWidth) {
            isCrossing = true;
        }
        return isCrossing;
    }

    /**
     * Is the character crossing an obstacle?
     *
     * @param rMapPointMatrix the map (represented by its matrix of RMapPoint)
     * @param mapWidth        the map width
     * @param mapHeight       the map height
     * @param xChar           the character abscissa
     * @param yChar           the character ordinate
     * @return true if the character is crossing an obstacle, false otherwise
     * @throws OutOfRMapBoundsException if the character is crossing map limits
     */
    public static boolean isCharacterCrossingObstacle(RMapPoint[][] rMapPointMatrix, int mapWidth, int mapHeight,
                                                      int xChar, int yChar) throws OutOfRMapBoundsException {

        // ToDo: Do not check map limit and add an impLSpec as in PatternMethods class.
        if (isCharacterCrossingMapLimit(mapWidth, mapHeight, xChar, yChar)) {
            throw new OutOfRMapBoundsException(
                    "rMap out of bounds with the following coordinates: xChar=" + xChar + ", yChar=" + yChar);
        }
        int topRowIdx = getTopRowIdxIfOrdIs(yChar);
        int bottomRowIdx = getBottomRowIdxIfOrdIs(yChar);
        int mostLeftColIdx = getMostLeftColIdxIfAbsIs(xChar);
        int mostRightColIdx = getMostRightColIdxIfAbsIs(xChar);

        boolean isCrossing = false;
        if (!rMapPointMatrix[topRowIdx][mostLeftColIdx].isPathway() ||
                !rMapPointMatrix[topRowIdx][mostRightColIdx].isPathway() ||
                !rMapPointMatrix[bottomRowIdx][mostLeftColIdx].isPathway() ||
                !rMapPointMatrix[bottomRowIdx][mostRightColIdx].isPathway()) {
            isCrossing = true;
        }
        return isCrossing;
    }

    /**
     * Is the character burning?
     * i.e. is there a burning case adjoining the character?
     *
     * @param rMapPointMatrix the map (represented by its matrix of RMapPoint)
     * @param mapWidth        the map width
     * @param mapHeight       the map height
     * @param xChar           the character abscissa
     * @param yChar           the character ordinate
     * @return true if the character is burning, false otherwise
     * @throws OutOfRMapBoundsException if the character is crossing map limits
     */
    public static boolean isCharacterBurning(RMapPoint[][] rMapPointMatrix, int mapWidth, int mapHeight,
                                             int xChar, int yChar) throws OutOfRMapBoundsException {
        if (isCharacterCrossingMapLimit(mapWidth, mapHeight, xChar, yChar)) {
            throw new OutOfRMapBoundsException(
                    "rMap out of bounds with the following coordinates: xChar=" + xChar + ", yChar=" + yChar);
        }
        int topRowIdx = getTopRowIdxIfOrdIs(yChar);
        int bottomRowIdx = getBottomRowIdxIfOrdIs(yChar);
        int mostLeftColIdx = getMostLeftColIdxIfAbsIs(xChar);
        int mostRightColIdx = getMostRightColIdxIfAbsIs(xChar);

        boolean isBurning = false;
        if (rMapPointMatrix[topRowIdx][mostLeftColIdx].isBurning() ||
                rMapPointMatrix[topRowIdx][mostRightColIdx].isBurning() ||
                rMapPointMatrix[bottomRowIdx][mostLeftColIdx].isBurning() ||
                rMapPointMatrix[bottomRowIdx][mostRightColIdx].isBurning()) {
            isBurning = true;
        }
        return isBurning;
    }

    /**
     * Is the character crossing a bomb?
     * note: we can't just use the isCharacterCrossingObstacle() algorithm as the character can already be
     * on a bombing case - when it just put a bomb - and this particular case cannot be processed the same way.
     *
     * @param rMapPointMatrix the map (represented by its matrix of RMapPoint)
     * @param mapWidth        the map width
     * @param mapHeight       the map height
     * @param xChar           the character abscissa
     * @param yChar           the character ordinate
     * @param keyEvent        the current pressed key
     * @return true if the character is crossing a bomb, false otherwise
     * @throws OutOfRMapBoundsException if the character is crossing map limits
     */
    public static boolean isCharacterCrossingBomb(RMapPoint[][] rMapPointMatrix, int mapWidth, int mapHeight,
                                                  int xChar, int yChar, int keyEvent)
            throws OutOfRMapBoundsException {
        if (isCharacterCrossingMapLimit(mapWidth, mapHeight, xChar, yChar)) {
            throw new OutOfRMapBoundsException(
                    "rMap out of bounds with the following coordinates: xChar=" + xChar + ", yChar=" + yChar);
        }
        int topRowIdx = getTopRowIdxIfOrdIs(yChar);
        int bottomRowIdx = getBottomRowIdxIfOrdIs(yChar);
        int mostLeftColIdx = getMostLeftColIdxIfAbsIs(xChar);
        int mostRightColIdx = getMostRightColIdxIfAbsIs(xChar);

        boolean isCrossing = false;
        switch (keyEvent) {
            case KeyEvent.VK_UP: {
                if ((rMapPointMatrix[topRowIdx][mostLeftColIdx].isBombing() ||
                        rMapPointMatrix[topRowIdx][mostRightColIdx].isBombing()) &&
                        topRowIdx != getTopRowIdxIfOrdIs(yChar + 1)) {
                    isCrossing = true;
                }
                break;
            }
            case KeyEvent.VK_DOWN: {
                if ((rMapPointMatrix[bottomRowIdx][mostLeftColIdx].isBombing() ||
                        rMapPointMatrix[bottomRowIdx][mostRightColIdx].isBombing()) &&
                        bottomRowIdx != getBottomRowIdxIfOrdIs(yChar - 1)) {
                    isCrossing = true;
                }
                break;
            }
            case KeyEvent.VK_LEFT: {
                if ((rMapPointMatrix[topRowIdx][mostLeftColIdx].isBombing() ||
                        rMapPointMatrix[bottomRowIdx][mostLeftColIdx].isBombing()) &&
                        mostLeftColIdx != getMostLeftColIdxIfAbsIs(xChar + 1)) {
                    isCrossing = true;
                }
                break;
            }
            case KeyEvent.VK_RIGHT: {
                if ((rMapPointMatrix[topRowIdx][mostRightColIdx].isBombing() ||
                        rMapPointMatrix[bottomRowIdx][mostRightColIdx].isBombing()) &&
                        mostRightColIdx != getMostRightColIdxIfAbsIs(xChar - 1)) {
                    isCrossing = true;
                }
                break;
            }
            default: // bad key, nothing to do.
        }
        return isCrossing;
    }
}