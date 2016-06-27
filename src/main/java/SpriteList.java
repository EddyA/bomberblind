import images.ImagesLoader;
import map.RMap;
import sprites.Bomb;
import sprites.Flame;
import sprites.FlameEnd;
import sprites.Sprite;

import java.awt.*;
import java.util.LinkedList;
import java.util.ListIterator;

public class SpriteList extends LinkedList<Sprite> {

    // create a temporary list to manage addings and avoid concurent accesses.
    LinkedList<Sprite> tmpList = new LinkedList<>();

    private RMap rMap;
    private int screenWidth; // widht of the screen (expressed in pixel).
    private int screenHeight; // height of the screen (expressed in pixel).

    public SpriteList(RMap rMap, int screenWidth, int screenHeight) {
        this.rMap = rMap;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    /**
     * Add a bomb to a list.
     *
     * @param rowIdx    the map row index of the bomb
     * @param colIdx    the map column index of the bomb
     * @param flameSize size of flames of the bomb
     */
    public synchronized void addBomb(int rowIdx, int colIdx, int flameSize) {
        addBomb(this, rowIdx, colIdx, flameSize);
    }

    /**
     * Add a bomb to a list.
     *
     * @param list      the list into which putting the bomb
     * @param rowIdx    the map row index of the bomb
     * @param colIdx    the map column index of the bomb
     * @param flameSize size of flames of the bomb
     */
    private void addBomb(LinkedList<Sprite> list, int rowIdx, int colIdx, int flameSize) {
        if (!rMap.myMap[rowIdx][colIdx].isBombing()) {
            rMap.myMap[rowIdx][colIdx].setBombing(true);
            list.add(new Bomb(rowIdx, colIdx, flameSize));
        }
    }

    /**
     * Add a flame to a list.
     *
     * @param list   the list into which putting the flame
     * @param rowIdx the map row index of the flame
     * @param colIdx the map column index of the flame
     * @return true if the flame can be propagated, false it is stopped
     */
    private boolean addFlame(LinkedList<Sprite> list, int rowIdx, int colIdx) {
        if (rMap.myMap[rowIdx][colIdx].isPathway()) {
            rMap.myMap[rowIdx][colIdx].addFlame();
            rMap.myMap[rowIdx][colIdx].setImageAsBurned();
            list.add(new Flame(rowIdx, colIdx));
            return true; // the next case can burn.
        } else if (rMap.myMap[rowIdx][colIdx].isMutable()) {
            rMap.myMap[rowIdx][colIdx].setPathway(true);
            rMap.myMap[rowIdx][colIdx].setMutable(false);
            rMap.myMap[rowIdx][colIdx].addFlame();
            rMap.myMap[rowIdx][colIdx].setImageAsBurned();
            list.add(new Flame(rowIdx, colIdx));
            return false; // the next case should not burn.
        } else {
            return false; // the next case should not burn.
        }
    }

    /**
     * Add a set of flames to a depict a bomb explosion.
     *
     * @param list          the list into which putting the flames
     * @param centralRowIdx the map row index of the central flame
     * @param centralColIdx the map column index of the central flame
     * @param flameSize     size of flames
     */
    private void addFlames(LinkedList<Sprite> list, int centralRowIdx, int centralColIdx, int flameSize) {

        // place left flames.
        for (int i = 1, j = centralColIdx - 1;
             i <= flameSize && j >= 0;
             i++, j--) { // from center to left.
            if (!addFlame(list, centralRowIdx, centralColIdx - i)) {
                break; // break loop.
            }
        }

        // place right flames.
        for (int i = 1, j = centralColIdx + 1;
             i <= flameSize && j < rMap.mapWidth;
             i++, j++) { // from center to right.
            if (!addFlame(list, centralRowIdx, centralColIdx + i)) {
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
            addFlame(list, centralRowIdx - i, centralColIdx);
        }
        addFlame(list, centralRowIdx, centralColIdx); // central case.

        for (int i = 1, j = centralRowIdx + 1;
             i <= flameSize && j < rMap.mapHeight;
             i++, j++) { // from center to lower.
            if (!addFlame(list, centralRowIdx + i, centralColIdx)) {
                break; // break loop.
            }
        }
    }

    /**
     * Add a conclusion flame to a list.
     *
     * @param list   the list into which putting the flame
     * @param rowIdx the map row index of the flame
     * @param colIdx the map column index of the flame
     */
    private void addFlameEnd(LinkedList<Sprite> list, int rowIdx, int colIdx) {
        list.add(new FlameEnd(rowIdx, colIdx));
    }

    /**
     * Detonate imminent bombs, create flames and clean dead bombs from the list.
     */
    public synchronized void clean() {

        for (ListIterator<Sprite> iterator = this.listIterator(); iterator.hasNext(); ) {
            Sprite sprite = iterator.next();

            // if the current sprite is a bomb, check if it is on a burning case.
            if (sprite.getClass().getSimpleName().equals("Bomb")) { // it is a bomb.
                if (rMap.myMap[sprite.getRowIdx()][sprite.getColIdx()].isBurning()) {
                    addFlames(tmpList, sprite.getRowIdx(), sprite.getColIdx(), ((Bomb) sprite).getFlameSize());
                    iterator.remove(); // remove the sprite from the list.
                }
            }

            // if the sprite is finished, perfomed actions and remove it from the list.
            if (sprite.isFinished()) {
                if (sprite.getClass().getSimpleName().equals("Bomb")) { // it is a bomb.
                    addFlames(tmpList, sprite.getRowIdx(), sprite.getColIdx(), ((Bomb) sprite).getFlameSize());
                    rMap.myMap[sprite.getRowIdx()][sprite.getColIdx()].setBombing(false);
                }
                if (sprite.getClass().getSimpleName().equals("Flame")) { // it is a flame.
                    addFlameEnd(tmpList, sprite.getRowIdx(), sprite.getColIdx());
                    rMap.myMap[sprite.getRowIdx()][sprite.getColIdx()].removeFlame();
                }
                iterator.remove(); // remove the sprite from the list.
            }
        }
        this.addAll(tmpList); // add sprites of the temporary list to the main one.
        tmpList.clear(); // clear the temporary list.
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
        for (Sprite sprite : this) {
            if ((sprite.getRowIdx() >= startRowIdx &&
                    sprite.getRowIdx() < startRowIdx + (screenHeight / ImagesLoader.IMAGE_SIZE) + 1) &&
                    (sprite.getColIdx() >= startColIdx &&
                            sprite.getColIdx() < startColIdx + (screenWidth / ImagesLoader.IMAGE_SIZE) + 1)) {
                sprite.paintBuffer(g,
                        (sprite.getColIdx() - startColIdx) * ImagesLoader.IMAGE_SIZE - xMap % ImagesLoader.IMAGE_SIZE,
                        (sprite.getRowIdx() - startRowIdx) * ImagesLoader.IMAGE_SIZE - yMap % ImagesLoader.IMAGE_SIZE);
            }
        }
    }
}
