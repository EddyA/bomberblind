package animations;

import images.ImagesLoader;
import map.RMap;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class groups (all) bombs.
 * It impacts the rMap by updating rMapPoint status.
 * note: the methods of this class are synchronized to avoid concurrent access when adding,
 * removing and display elements.
 */
public class BombList extends ArrayList<Bomb> {

    private RMap rMap;
    private FlameList flameList; // list of flames.
    private int screenWidth; // widht of the screen (expressed in pixel).
    private int screenHeight; // height of the screen (expressed in pixel).

    public BombList(RMap rMap, FlameList flameList, int screenWidth, int screenHeight) {
        this.rMap = rMap;
        this.flameList = flameList;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    /**
     * Add a bomb.
     *
     * @param rowIdx abscisa of the bomb
     * @param colIdx ordinate of the bomb
     * @param flameSize size of flames of the bomb
     */
    public synchronized void add(int rowIdx, int colIdx, int flameSize) {
        if (!rMap.myMap[rowIdx][colIdx].isBombing()) {
            rMap.myMap[rowIdx][colIdx].setBombing(true);
            this.add(new Bomb(rowIdx, colIdx, flameSize));
        }
    }

    /**
     * Detonate imminent bombs, create flames and clean dead bombs from the list.
     */
    public synchronized void clean() {
        for (Iterator<Bomb> iterator = this.iterator(); iterator.hasNext(); ) {
            Bomb bomb = iterator.next();
            if (bomb.isDead()) {
                flameList.add(bomb.getRowIdx(), bomb.getColIdx(), bomb.getFlameSize());
                rMap.myMap[bomb.getRowIdx()][bomb.getColIdx()].setBombing(false);
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

        // paint the bombs.
        for (Bomb bomb : this) {
            if ((bomb.getRowIdx() >= startRowIdx &&
                    bomb.getRowIdx() < startRowIdx + (screenHeight / ImagesLoader.IMAGE_SIZE) + 1) &&
                    (bomb.getColIdx() >= startColIdx &&
                            bomb.getColIdx() < startColIdx + (screenWidth / ImagesLoader.IMAGE_SIZE) + 1)) {
                bomb.paintBuffer(g,
                        (bomb.getColIdx() - startColIdx) * ImagesLoader.IMAGE_SIZE - xMap % ImagesLoader.IMAGE_SIZE,
                        (bomb.getRowIdx() - startRowIdx) * ImagesLoader.IMAGE_SIZE - yMap % ImagesLoader.IMAGE_SIZE);
            }
        }
    }
}