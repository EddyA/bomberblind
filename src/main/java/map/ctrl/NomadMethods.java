package map.ctrl;

import map.MapPoint;

import java.awt.event.KeyEvent;

import static utils.Tools.*;

public class NomadMethods {

    /**
     * Is the abstracts crossing a map limit?
     *
     * @param mapWidth  the map width
     * @param mapHeight the map height
     * @param xChar     the abstracts abscissa
     * @param yChar     the abstracts ordinate
     * @return true if the abstracts is crossing a map limit, false otherwise
     */
    public static boolean isNomadCrossingMapLimit(int mapWidth, int mapHeight, int xChar, int yChar) {
        int topRowIdx = getCharTopRowIdx(yChar);
        int bottomRowIdx = getCharBottomRowIdx(yChar);
        int mostLeftColIdx = getCharLeftColIdx(xChar);
        int mostRightColIdx = getCharRightColIdx(xChar);

        boolean isCrossing = false;
        if (topRowIdx < 0 || bottomRowIdx >= mapHeight ||
                mostLeftColIdx < 0 || mostRightColIdx >= mapWidth) {
            isCrossing = true;
        }
        return isCrossing;
    }

    /**
     * Is the abstracts crossing an obstacle?
     *
     * @param mapPointMatrix the map (represented by its matrix of MapPoint)
     * @param xChar          the abstracts abscissa
     * @param yChar          the abstracts ordinate
     * @return true if the abstracts is crossing an obstacle, false otherwise
     * @implSpec isNomadCrossingMapLimit() must be called before this function!
     */
    public static boolean isNomadCrossingObstacle(MapPoint[][] mapPointMatrix, int xChar, int yChar) {
        int topRowIdx = getCharTopRowIdx(yChar);
        int bottomRowIdx = getCharBottomRowIdx(yChar);
        int mostLeftColIdx = getCharLeftColIdx(xChar);
        int mostRightColIdx = getCharRightColIdx(xChar);

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
     * Is the abstracts burning?
     * i.e. is there a burning case adjoining the abstracts?
     *
     * @param mapPointMatrix the map (represented by its matrix of MapPoint)
     * @param xChar          the abstracts abscissa
     * @param yChar          the abstracts ordinate
     * @return true if the abstracts is burning, false otherwise
     * @implSpec isNomadCrossingMapLimit() must be called before this function!
     */
    public static boolean isNomadBurning(MapPoint[][] mapPointMatrix, int xChar, int yChar) {
        int topRowIdx = getCharTopRowIdx(yChar);
        int bottomRowIdx = getCharBottomRowIdx(yChar);
        int mostLeftColIdx = getCharLeftColIdx(xChar);
        int mostRightColIdx = getCharRightColIdx(xChar);

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
     * Is the abstracts crossing a bomb?
     * note: we can't just use the isNomadCrossingObstacle() algorithm as the abstracts can already be
     * on a bombing case - when it just put a bomb - and this particular case cannot be processed the same way.
     *
     * @param mapPointMatrix the map (represented by its matrix of MapPoint)
     * @param xChar          the abstracts abscissa
     * @param yChar          the abstracts ordinate
     * @param keyEvent       the current pressed key
     * @return true if the abstracts is crossing a bomb, false otherwise
     * @implSpec isNomadCrossingMapLimit() must be called before this function!
     */
    public static boolean isNomadCrossingBomb(MapPoint[][] mapPointMatrix, int xChar, int yChar, int keyEvent) {
        int topRowIdx = getCharTopRowIdx(yChar);
        int bottomRowIdx = getCharBottomRowIdx(yChar);
        int mostLeftColIdx = getCharLeftColIdx(xChar);
        int mostRightColIdx = getCharRightColIdx(xChar);

        boolean isCrossing = false;
        switch (keyEvent) {
            case KeyEvent.VK_UP: {
                if ((mapPointMatrix[topRowIdx][mostLeftColIdx].isBombing() ||
                        mapPointMatrix[topRowIdx][mostRightColIdx].isBombing()) &&
                        topRowIdx != getCharTopRowIdx(yChar + 1)) {
                    isCrossing = true;
                }
                break;
            }
            case KeyEvent.VK_DOWN: {
                if ((mapPointMatrix[bottomRowIdx][mostLeftColIdx].isBombing() ||
                        mapPointMatrix[bottomRowIdx][mostRightColIdx].isBombing()) &&
                        bottomRowIdx != getCharBottomRowIdx(yChar - 1)) {
                    isCrossing = true;
                }
                break;
            }
            case KeyEvent.VK_LEFT: {
                if ((mapPointMatrix[topRowIdx][mostLeftColIdx].isBombing() ||
                        mapPointMatrix[bottomRowIdx][mostLeftColIdx].isBombing()) &&
                        mostLeftColIdx != getCharLeftColIdx(xChar + 1)) {
                    isCrossing = true;
                }
                break;
            }
            case KeyEvent.VK_RIGHT: {
                if ((mapPointMatrix[topRowIdx][mostRightColIdx].isBombing() ||
                        mapPointMatrix[bottomRowIdx][mostRightColIdx].isBombing()) &&
                        mostRightColIdx != getCharRightColIdx(xChar - 1)) {
                    isCrossing = true;
                }
                break;
            }
            default: // bad key, nothing to do.
        }
        return isCrossing;
    }
}