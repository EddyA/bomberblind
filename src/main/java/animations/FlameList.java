package animations;

import images.ImagesLoader;
import map.RMap;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class groups (all) flames of the detonated bombs.
 * It impacts the rMap by updating rMapPoint status.
 * note: the methods of this class are synchronized to avoid concurrent access when adding,
 * removing and display elements.
 */
public class FlameList extends ArrayList<Flame> {
    private RMap rMap;
    private int screenWidth; // widht of the screen (expressed in pixel).
    private int screenHeight; // height of the screen (expressed in pixel).

    public FlameList(RMap rMap, int screenWidth, int screenHeight) {
        this.rMap = rMap;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    /**
     * Add a set of flames based on a central position and a size.
     *
     * @param centralRowIdx abscisa of the central position
     * @param centralColIdx ordinate of the central position
     * @param flameSize     size of flames
     */
    public synchronized void add(int centralRowIdx, int centralColIdx, int flameSize) {

        // place left flames.
        for (int i = 1, j = centralColIdx - 1;
             i <= flameSize && j >= 0;
             i++, j--) { // from center to left.
            if (!burnCase(centralRowIdx, centralColIdx - i)) {
                break; // break loop.
            }
        }

        // place right flames.
        for (int i = 1, j = centralColIdx + 1;
             i <= flameSize && j < rMap.mapWidth;
             i++, j++) { // from center to right.
            if (!burnCase(centralRowIdx, centralColIdx + i)) {
                break; // break loop.
            }
        }

        // in order to display flames in a good order, we must parse that axis before burning cases.
        int rowIdx = 0;
        for (int i = 1, j = centralRowIdx - 1;
             i <= flameSize && j >= 0;
             i++, j--) { // from center to upper.
            if (rMap.myMap[centralRowIdx - i][centralColIdx].isPathway()) {
                // as a pathway, this case must burn -> check the following one.
                rowIdx++;
                continue;
            } else if (rMap.myMap[centralRowIdx - i][centralColIdx].isMutable()) {
                // as a mutable, this case must burn -> stop here.
                rowIdx++;
                break;
            } else {
                break; // as an obstacle, stop here.
            }
        }
        for (int i = rowIdx; i > 0; i--) { // from upper to center.
            burnCase(centralRowIdx - i, centralColIdx);
        }
        burnCase(centralRowIdx, centralColIdx); // central case.

        for (int i = 1, j = centralRowIdx + 1;
             i <= flameSize && j < rMap.mapHeight;
             i++, j++) { // from center to lower.
            if (!burnCase(centralRowIdx + i, centralColIdx)) {
                break; // break loop.
            }
        }
    }

    /**
     * Burn a case.
     *
     * @param rowIdx the abscissa of the case
     * @param colIdx the ordinate of the case
     * @return true if the flame can be propagated, false if not
     */
    private synchronized boolean burnCase(int rowIdx, int colIdx) {
        if (rMap.myMap[rowIdx][colIdx].isPathway()) {
            rMap.myMap[rowIdx][colIdx].setBurning(true);
            rMap.myMap[rowIdx][colIdx].setImageAsBurned();
            this.add(new Flame(rowIdx, colIdx));
            return true; // the next case can burn.
        } else if (rMap.myMap[rowIdx][colIdx].isMutable()) {
            rMap.myMap[rowIdx][colIdx].setPathway(true);
            rMap.myMap[rowIdx][colIdx].setMutable(false);
            rMap.myMap[rowIdx][colIdx].setBurning(true);
            rMap.myMap[rowIdx][colIdx].setImageAsBurned();
            this.add(new Flame(rowIdx, colIdx));
            return false; // the next case should not burn.
        } else {
            return false; // the next case should not burn.
        }
    }

    /**
     * Clean dead flames from the list.
     */
    public synchronized void clean() {
        for (Iterator<Flame> iterator = this.iterator(); iterator.hasNext(); ) {
            Flame flame = iterator.next();
            if (flame.isDead()) {
                rMap.myMap[flame.getRowIdx()][flame.getColIdx()].setBurning(false);
                iterator.remove();
            }
        }
    }

    /**
     * Paint a fragment of the list from a point (expressed in pixel).
     * note 1: the start point is expressed in pixel - and not in RMapPoint.
     * note 2: the map is sized mapWidth*ImagesLoader.IMAGE_SIZE x mapHeight*ImagesLoader.IMAGE_SIZE.
     *
     * @param g          the graphics context
     * @param startPoint the start point (abscissa and ordinate of a RMapPoint).
     */
    public synchronized void paintBuffer(Graphics g, Point startPoint) {
        int xMap = (int) startPoint.getX();
        int yMap = (int) startPoint.getY();

        // get the starting RMapPoint concerned.
        int startColIdx = xMap / ImagesLoader.IMAGE_SIZE;
        int startRowIdx = yMap / ImagesLoader.IMAGE_SIZE;

        // paint the flames.
        for (Flame flame : this) {
            if ((flame.getRowIdx() >= startRowIdx &&
                    flame.getRowIdx() < startRowIdx + (screenHeight / ImagesLoader.IMAGE_SIZE) + 1) &&
                    (flame.getColIdx() >= startColIdx &&
                            flame.getColIdx() < startColIdx + (screenWidth / ImagesLoader.IMAGE_SIZE) + 1)) {
                flame.paintBuffer(g,
                        (flame.getColIdx() - startColIdx) * ImagesLoader.IMAGE_SIZE - xMap % ImagesLoader.IMAGE_SIZE,
                        (flame.getRowIdx() - startRowIdx) * ImagesLoader.IMAGE_SIZE - yMap % ImagesLoader.IMAGE_SIZE);
            }
        }
    }
}
