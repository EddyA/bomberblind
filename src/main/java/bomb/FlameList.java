package bomb;

import images.ImagesLoader;
import map.RMap;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * The methods of this class are synchronized to avoid concurrent access when adding, removing and display elements.
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
     * @param flameTime     duration of flames
     */
    public synchronized void add(int centralRowIdx, int centralColIdx, int flameSize, int flameTime) {

        // place left flames.
        for (int i = 1; i <= flameSize; i++) { // from center to left.
            if (rMap.myMap[centralRowIdx][centralColIdx - i].isPathway() ||
                    rMap.myMap[centralRowIdx][centralColIdx - i].isMutable()) {
                this.add(new Flame(centralRowIdx, centralColIdx - i, flameTime));
            } else {
                break; // break loop.
            }
        }

        // place right flames.
        for (int i = 1; i <= flameSize; i++) { // from center to right.
            if (rMap.myMap[centralRowIdx][centralColIdx + i].isPathway() ||
                    rMap.myMap[centralRowIdx][centralColIdx + i].isMutable()) {
                this.add(new Flame(centralRowIdx, centralColIdx + i, flameTime));
            } else {
                break; // break loop.
            }
        }

        // 1st passage to find obstacles.
        int rowIdx = flameSize + 1;
        for (int i = 1; i <= flameSize; i++) { // from center to upper.
            if (!rMap.myMap[centralRowIdx - i][centralColIdx].isPathway() &&
                    !rMap.myMap[centralRowIdx - i][centralColIdx].isMutable()) {
                rowIdx = i;
            }
        }
        for (int i = rowIdx - 1; i > 0; i--) { // from upper to center.
            if (rMap.myMap[centralRowIdx - i][centralColIdx].isPathway() ||
                    rMap.myMap[centralRowIdx - i][centralColIdx].isMutable()) {
                this.add(new Flame(centralRowIdx - i, centralColIdx, flameTime));
            } else {
                break; // break loop.
            }
        }
        this.add(new Flame(centralRowIdx, centralColIdx, flameTime)); // central case.

        for (int i = 1; i <= flameSize; i++) { // from center to lower.
            if (rMap.myMap[centralRowIdx + i][centralColIdx].isPathway() ||
                    rMap.myMap[centralRowIdx + i][centralColIdx].isMutable()) {
                this.add(new Flame(centralRowIdx + i, centralColIdx, flameTime));
            } else {
                break; // break loop.
            }
        }
    }

    /**
     * Clean dead flames from the list.
     */
    public synchronized void clean() {
        for (Iterator<Flame> iterator = this.iterator(); iterator.hasNext(); ) {
            Flame flame = iterator.next();
            if (flame.isDead()) {
                iterator.remove();
            }
        }
    }

    /**
     * Paint a fragment of the list from a point (expressed in pixel).
     * note 1: the start point is expressed in pixel - and not in RMapPoint - to allow smooth move.
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
            if ((flame.getRowIdx() > startRowIdx &&
                    flame.getRowIdx() < startRowIdx + (screenHeight / ImagesLoader.IMAGE_SIZE) + 1) &&
                    (flame.getColIdx() > startColIdx &&
                            flame.getColIdx() < startColIdx + (screenWidth / ImagesLoader.IMAGE_SIZE) + 1)) {
                flame.paintBuffer(g,
                        (flame.getColIdx() - startColIdx) * ImagesLoader.IMAGE_SIZE - xMap % ImagesLoader.IMAGE_SIZE,
                        (flame.getRowIdx() - startRowIdx) * ImagesLoader.IMAGE_SIZE - yMap % ImagesLoader.IMAGE_SIZE);
            }
        }
    }
}
