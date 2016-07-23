package map;

import exceptions.OutOfRMapBoundsException;

import java.awt.event.KeyEvent;

import static images.ImagesLoader.IMAGE_SIZE;

public class RMapUtils {

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
     * @param rMap  the relative map
     * @param xChar the character abscissa
     * @param yChar the character ordinate
     * @return true if the character is crossing a map limit, false otherwise
     */
    public static boolean isCharacterCrossingMapLimit(RMap rMap, int xChar, int yChar) {
        int topRowIdx = getTopRowIdxIfOrdIs(yChar);
        int bottomRowIdx = getBottomRowIdxIfOrdIs(yChar);
        int mostLeftColIdx = getMostLeftColIdxIfAbsIs(xChar);
        int mostRightColIdx = getMostRightColIdxIfAbsIs(xChar);

        boolean isCrossing = false;
        if (topRowIdx < 0 || bottomRowIdx >= rMap.getMapHeight() ||
                mostLeftColIdx < 0 || mostRightColIdx >= rMap.getMapWidth()) {
            isCrossing = true;
        }
        return isCrossing;
    }

    /**
     * Is the character crossing an obstacle?
     *
     * @param rMap  the relative map
     * @param xChar the character abscissa
     * @param yChar the character ordinate
     * @return true if the character is crossing an obstacle, false otherwise
     * @throws OutOfRMapBoundsException if the character is crossing map limits
     */
    public static boolean isCharacterCrossingObstacle(RMap rMap, int xChar, int yChar)
            throws OutOfRMapBoundsException {
        if (isCharacterCrossingMapLimit(rMap, xChar, yChar)) {
            throw new OutOfRMapBoundsException(
                    "rMap out of bounds with the following coordinates: xChar=" + xChar + ", yChar=" + yChar);
        }
        int topRowIdx = getTopRowIdxIfOrdIs(yChar);
        int bottomRowIdx = getBottomRowIdxIfOrdIs(yChar);
        int mostLeftColIdx = getMostLeftColIdxIfAbsIs(xChar);
        int mostRightColIdx = getMostRightColIdxIfAbsIs(xChar);

        boolean isCrossing = false;
        if (!rMap.getRMapPoint(topRowIdx, mostLeftColIdx).isPathway() ||
                !rMap.getRMapPoint(topRowIdx, mostRightColIdx).isPathway() ||
                !rMap.getRMapPoint(bottomRowIdx, mostLeftColIdx).isPathway() ||
                !rMap.getRMapPoint(bottomRowIdx, mostRightColIdx).isPathway()) {
            isCrossing = true;
        }
        return isCrossing;
    }

    /**
     * Is the character burning?
     * i.e. is there a burning case adjoining the character?
     *
     * @param rMap  the relative map
     * @param xChar the character abscissa
     * @param yChar the character ordinate
     * @return true if the character is burning, false otherwise
     * @throws OutOfRMapBoundsException if the character is crossing map limits
     */
    public static boolean isCharacterBurning(RMap rMap, int xChar, int yChar)
            throws OutOfRMapBoundsException {
        if (isCharacterCrossingMapLimit(rMap, xChar, yChar)) {
            throw new OutOfRMapBoundsException(
                    "rMap out of bounds with the following coordinates: xChar=" + xChar + ", yChar=" + yChar);
        }
        int topRowIdx = getTopRowIdxIfOrdIs(yChar);
        int bottomRowIdx = getBottomRowIdxIfOrdIs(yChar);
        int mostLeftColIdx = getMostLeftColIdxIfAbsIs(xChar);
        int mostRightColIdx = getMostRightColIdxIfAbsIs(xChar);

        boolean isBurning = false;
        if (rMap.getRMapPoint(topRowIdx, mostLeftColIdx).isBurning() ||
                rMap.getRMapPoint(topRowIdx, mostRightColIdx).isBurning() ||
                rMap.getRMapPoint(bottomRowIdx, mostLeftColIdx).isBurning() ||
                rMap.getRMapPoint(bottomRowIdx, mostRightColIdx).isBurning()) {
            isBurning = true;
        }
        return isBurning;
    }

    /**
     * Is the character crossing a bomb?
     * note: we can't just use the isCharacterCrossingObstacle() algorithm as the character can already be
     * on a bombing case - when it just put a bomb - and this particular case cannot be processed the same way.
     *
     * @param rMap  the relative map
     * @param xChar the character abscissa
     * @param yChar the character ordinate
     * @return true if the character is crossing a bomb, false otherwise
     * @throws OutOfRMapBoundsException if the character is crossing map limits
     */
    public static boolean isCharacterCrossingBomb(RMap rMap, int xChar, int yChar, int keyEvent)
            throws OutOfRMapBoundsException {
        if (isCharacterCrossingMapLimit(rMap, xChar, yChar)) {
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
                if ((rMap.getRMapPoint(topRowIdx, mostLeftColIdx).isBombing() ||
                        rMap.getRMapPoint(topRowIdx, mostRightColIdx).isBombing()) &&
                        topRowIdx != getTopRowIdxIfOrdIs(yChar + 1)) {
                    isCrossing = true;
                }
                break;
            }
            case KeyEvent.VK_DOWN: {
                if ((rMap.getRMapPoint(bottomRowIdx, mostLeftColIdx).isBombing() ||
                        rMap.getRMapPoint(bottomRowIdx, mostRightColIdx).isBombing()) &&
                        bottomRowIdx != getBottomRowIdxIfOrdIs(yChar - 1)) {
                    isCrossing = true;
                }
                break;
            }
            case KeyEvent.VK_LEFT: {
                if ((rMap.getRMapPoint(topRowIdx, mostLeftColIdx).isBombing() ||
                        rMap.getRMapPoint(bottomRowIdx, mostLeftColIdx).isBombing()) &&
                        mostLeftColIdx != getMostLeftColIdxIfAbsIs(xChar + 1)) {
                    isCrossing = true;
                }
                break;
            }
            case KeyEvent.VK_RIGHT: {
                if ((rMap.getRMapPoint(topRowIdx, mostRightColIdx).isBombing() ||
                        rMap.getRMapPoint(bottomRowIdx, mostRightColIdx).isBombing()) &&
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
