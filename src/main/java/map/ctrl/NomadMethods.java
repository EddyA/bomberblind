package map.ctrl;

import static images.ImagesLoader.IMAGE_SIZE;

import java.awt.event.KeyEvent;

import map.MapPoint;

public class NomadMethods {

    /**
     * Return the top rowIdx of a nomad given its map ordinate.
     */
    protected static int getTopRowIdxIfOrdIs(int yChar) {
        return (yChar - IMAGE_SIZE / 2) < 0 ? -1 : (yChar - IMAGE_SIZE / 2) / IMAGE_SIZE;
    }

    /**
     * Return the bottom rowIdx of a nomad given its map ordinate.
     */
    protected static int getBottomRowIdxIfOrdIs(int yChar) {
        return yChar / IMAGE_SIZE;
    }

    /**
     * Return the most left colIdx of a nomad given its map abscissa.
     */
    protected static int getMostLeftColIdxIfAbsIs(int xChar) {
        return (xChar - IMAGE_SIZE / 2) < 0 ? -1 : (xChar - IMAGE_SIZE / 2) / IMAGE_SIZE;
    }

    /**
     * Return the most right colIdx of a nomad given its map abscissa.
     */
    protected static int getMostRightColIdxIfAbsIs(int xChar) {
        return (xChar + IMAGE_SIZE / 2 - 1) / IMAGE_SIZE;
    }

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
     * Is the nomad crossing an obstacle?
     *
     * @param mapPointMatrix the map (represented by its matrix of MapPoint)
     * @param xChar the nomad abscissa
     * @param yChar the nomad ordinate
     * @return true if the nomad is crossing an obstacle, false otherwise
     * @implSpec isNomadCrossingMapLimit() must be called before this function!
     */
    public static boolean isNomadCrossingObstacle(MapPoint[][] mapPointMatrix, int xChar, int yChar) {
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
     * Is the nomad burning?
     * i.e. is there a burning case adjoining the nomad?
     *
     * @param mapPointMatrix the map (represented by its matrix of MapPoint)
     * @param xChar the nomad abscissa
     * @param yChar the nomad ordinate
     * @return true if the nomad is burning, false otherwise
     * @implSpec isNomadCrossingMapLimit() must be called before this function!
     */
    public static boolean isNomadBurning(MapPoint[][] mapPointMatrix, int xChar, int yChar) {
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
     * Is the nomad crossing a bomb?
     * note: we can't just use the isNomadCrossingObstacle() algorithm as the nomad can already be
     * on a bombing case - when it just put a bomb - and this particular case cannot be processed the same way.
     *
     * @param mapPointMatrix the map (represented by its matrix of MapPoint)
     * @param xChar the nomad abscissa
     * @param yChar the nomad ordinate
     * @param keyEvent the current pressed key
     * @return true if the nomad is crossing a bomb, false otherwise
     * @implSpec isNomadCrossingMapLimit() must be called before this function!
     */
    public static boolean isNomadCrossingBomb(MapPoint[][] mapPointMatrix, int xChar, int yChar, int keyEvent) {
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