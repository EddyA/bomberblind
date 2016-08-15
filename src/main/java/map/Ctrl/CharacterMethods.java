package map.ctrl;

import exceptions.OutOfMapBoundsException;
import map.MapPoint;

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
     * @param mapPointMatrix the map (represented by its matrix of MapPoint)
     * @param mapWidth        the map width
     * @param mapHeight       the map height
     * @param xChar           the character abscissa
     * @param yChar           the character ordinate
     * @return true if the character is crossing an obstacle, false otherwise
     * @throws OutOfMapBoundsException if the character is crossing map limits
     */
    public static boolean isCharacterCrossingObstacle(MapPoint[][] mapPointMatrix, int mapWidth, int mapHeight,
                                                      int xChar, int yChar) throws OutOfMapBoundsException {

        // ToDo: Do not check map limit and add an impLSpec as in PatternMethods class.
        if (isCharacterCrossingMapLimit(mapWidth, mapHeight, xChar, yChar)) {
            throw new OutOfMapBoundsException(
                    "map out of bounds with the following coordinates: xChar=" + xChar + ", yChar=" + yChar);
        }
        int topRowIdx = getTopRowIdxIfOrdIs(yChar);
        int bottomRowIdx = getBottomRowIdxIfOrdIs(yChar);
        int mostLeftColIdx = getMostLeftColIdxIfAbsIs(xChar);
        int mostRightColIdx = getMostRightColIdxIfAbsIs(xChar);

        boolean isCrossing = false;
        if (!mapPointMatrix[topRowIdx][mostLeftColIdx].isPathway() ||
                !mapPointMatrix[topRowIdx][mostRightColIdx].isPathway() ||
                !mapPointMatrix[bottomRowIdx][mostLeftColIdx].isPathway() ||
                !mapPointMatrix[bottomRowIdx][mostRightColIdx].isPathway()) {
            isCrossing = true;
        }
        return isCrossing;
    }

    /**
     * Is the character burning?
     * i.e. is there a burning case adjoining the character?
     *
     * @param mapPointMatrix the map (represented by its matrix of MapPoint)
     * @param mapWidth        the map width
     * @param mapHeight       the map height
     * @param xChar           the character abscissa
     * @param yChar           the character ordinate
     * @return true if the character is burning, false otherwise
     * @throws OutOfMapBoundsException if the character is crossing map limits
     */
    public static boolean isCharacterBurning(MapPoint[][] mapPointMatrix, int mapWidth, int mapHeight,
                                             int xChar, int yChar) throws OutOfMapBoundsException {
        if (isCharacterCrossingMapLimit(mapWidth, mapHeight, xChar, yChar)) {
            throw new OutOfMapBoundsException(
                    "map out of bounds with the following coordinates: xChar=" + xChar + ", yChar=" + yChar);
        }
        int topRowIdx = getTopRowIdxIfOrdIs(yChar);
        int bottomRowIdx = getBottomRowIdxIfOrdIs(yChar);
        int mostLeftColIdx = getMostLeftColIdxIfAbsIs(xChar);
        int mostRightColIdx = getMostRightColIdxIfAbsIs(xChar);

        boolean isBurning = false;
        if (mapPointMatrix[topRowIdx][mostLeftColIdx].isBurning() ||
                mapPointMatrix[topRowIdx][mostRightColIdx].isBurning() ||
                mapPointMatrix[bottomRowIdx][mostLeftColIdx].isBurning() ||
                mapPointMatrix[bottomRowIdx][mostRightColIdx].isBurning()) {
            isBurning = true;
        }
        return isBurning;
    }

    /**
     * Is the character crossing a bomb?
     * note: we can't just use the isCharacterCrossingObstacle() algorithm as the character can already be
     * on a bombing case - when it just put a bomb - and this particular case cannot be processed the same way.
     *
     * @param mapPointMatrix the map (represented by its matrix of MapPoint)
     * @param mapWidth        the map width
     * @param mapHeight       the map height
     * @param xChar           the character abscissa
     * @param yChar           the character ordinate
     * @param keyEvent        the current pressed key
     * @return true if the character is crossing a bomb, false otherwise
     * @throws OutOfMapBoundsException if the character is crossing map limits
     */
    public static boolean isCharacterCrossingBomb(MapPoint[][] mapPointMatrix, int mapWidth, int mapHeight,
                                                  int xChar, int yChar, int keyEvent)
            throws OutOfMapBoundsException {
        if (isCharacterCrossingMapLimit(mapWidth, mapHeight, xChar, yChar)) {
            throw new OutOfMapBoundsException(
                    "map out of bounds with the following coordinates: xChar=" + xChar + ", yChar=" + yChar);
        }
        int topRowIdx = getTopRowIdxIfOrdIs(yChar);
        int bottomRowIdx = getBottomRowIdxIfOrdIs(yChar);
        int mostLeftColIdx = getMostLeftColIdxIfAbsIs(xChar);
        int mostRightColIdx = getMostRightColIdxIfAbsIs(xChar);

        boolean isCrossing = false;
        switch (keyEvent) {
            case KeyEvent.VK_UP: {
                if ((mapPointMatrix[topRowIdx][mostLeftColIdx].isBombing() ||
                        mapPointMatrix[topRowIdx][mostRightColIdx].isBombing()) &&
                        topRowIdx != getTopRowIdxIfOrdIs(yChar + 1)) {
                    isCrossing = true;
                }
                break;
            }
            case KeyEvent.VK_DOWN: {
                if ((mapPointMatrix[bottomRowIdx][mostLeftColIdx].isBombing() ||
                        mapPointMatrix[bottomRowIdx][mostRightColIdx].isBombing()) &&
                        bottomRowIdx != getBottomRowIdxIfOrdIs(yChar - 1)) {
                    isCrossing = true;
                }
                break;
            }
            case KeyEvent.VK_LEFT: {
                if ((mapPointMatrix[topRowIdx][mostLeftColIdx].isBombing() ||
                        mapPointMatrix[bottomRowIdx][mostLeftColIdx].isBombing()) &&
                        mostLeftColIdx != getMostLeftColIdxIfAbsIs(xChar + 1)) {
                    isCrossing = true;
                }
                break;
            }
            case KeyEvent.VK_RIGHT: {
                if ((mapPointMatrix[topRowIdx][mostRightColIdx].isBombing() ||
                        mapPointMatrix[bottomRowIdx][mostRightColIdx].isBombing()) &&
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