import map.abstracts.Map;
import sprites.nomad.Nomad;
import sprites.settled.Bomb;
import sprites.settled.Flame;
import sprites.settled.FlameEnd;
import utils.Tools;

import java.awt.*;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * List of nomad items.
 */
public class SettledList extends LinkedList<Nomad> {

    // create a temporary list to manage addings and avoid concurent accesses.
    LinkedList<Nomad> tmpList = new LinkedList<>();

    private Map map;
    private int screenWidth; // widht of the screen (expressed in pixel).
    private int screenHeight; // height of the screen (expressed in pixel).

    public SettledList(Map map, int screenWidth, int screenHeight) {
        this.map = map;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    /**
     * Add a bomb to the list.
     *
     * @param rowIdx    the map row index of the bomb
     * @param colIdx    the map column index of the bomb
     * @param flameSize the flame size of the bomb
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
     * @param flameSize the flame size of the bomb
     */
    private void addBomb(LinkedList<Nomad> list, int rowIdx, int colIdx, int flameSize) {
        if (!map.getMapPointMatrix()[rowIdx][colIdx].isBombing()) {
            map.getMapPointMatrix()[rowIdx][colIdx].setBombing(true);
            list.add(new Bomb(Tools.getCaseCentreAbscissa(colIdx), Tools.getCaseBottomOrdinate(rowIdx), flameSize));
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
    private boolean addFlame(LinkedList<Nomad> list, int rowIdx, int colIdx) {
        if (map.getMapPointMatrix()[rowIdx][colIdx].isPathway()) {
            map.getMapPointMatrix()[rowIdx][colIdx].addFlame();
            map.getMapPointMatrix()[rowIdx][colIdx].setImageAsBurned();
            list.add(new Flame(Tools.getCaseCentreAbscissa(colIdx), Tools.getCaseBottomOrdinate(rowIdx)));
            return true; // the next case can burn.
        } else if (map.getMapPointMatrix()[rowIdx][colIdx].isMutable() ||
                map.getMapPointMatrix()[rowIdx][colIdx].isBombing()) {
            map.getMapPointMatrix()[rowIdx][colIdx].setPathway(true);
            map.getMapPointMatrix()[rowIdx][colIdx].setMutable(false);
            map.getMapPointMatrix()[rowIdx][colIdx].addFlame();
            map.getMapPointMatrix()[rowIdx][colIdx].setImageAsBurned();
            list.add(new Flame(Tools.getCaseCentreAbscissa(colIdx), Tools.getCaseBottomOrdinate(rowIdx)));
            return false; // the next case should not burn.
        } else {
            return false; // the next case should not burn.
        }
    }

    /**
     * Add a set of flames to represent a bomb explosion.
     *
     * @param list          the list into which putting the flames
     * @param centralRowIdx the map row index of the central flame
     * @param centralColIdx the map column index of the central flame
     * @param flameSize     the flame size
     */
    private void addFlames(LinkedList<Nomad> list, int centralRowIdx, int centralColIdx, int flameSize) {

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
             i <= flameSize && j < map.getMapWidth();
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
            if (map.getMapPointMatrix()[centralRowIdx - i][centralColIdx].isPathway()) {
                // as a pathway, this case must burn -> check the following one.
                rowIdx++;
            } else if (map.getMapPointMatrix()[centralRowIdx - i][centralColIdx].isMutable() ||
                    map.getMapPointMatrix()[centralRowIdx - i][centralColIdx].isBombing()) {
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
             i <= flameSize && j < map.getMapHeight();
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
    private void addFlameEnd(LinkedList<Nomad> list, int rowIdx, int colIdx) {
        list.add(new FlameEnd(Tools.getCaseCentreAbscissa(colIdx), Tools.getCaseBottomOrdinate(rowIdx)));
    }

    /**
     * 1. Detonate bombs, if
     * - it is on a burning case,
     * - it is finished.
     * 2. Create flames and clean dead nomad from the list.
     */
    public synchronized void updateStatusAndClean() {
        for (ListIterator<Nomad> iterator = this.listIterator(); iterator.hasNext(); ) {
            Nomad nomad = iterator.next();

            if (nomad.getClass().getSimpleName().equals("Bomb")) { // it is a bomb.

                // is it finished?
                if (nomad.isFinished() ||
                        // OR is it on a burning case?
                        map.getMapPointMatrix()[nomad.getRowIdx()][nomad.getColIdx()].isBurning()) {
                    // create flames.
                    addFlames(tmpList, nomad.getRowIdx(), nomad.getColIdx(), ((Bomb) nomad).getFlameSize());
                    map.getMapPointMatrix()[nomad.getRowIdx()][nomad.getColIdx()].setBombing(false);
                    iterator.remove(); // remove it from the list.
                }
            } else if (nomad.getClass().getSimpleName().equals("Flame")) { // it is a flame.
                // is it finished?
                if (nomad.isFinished()) {
                    // create conclusion flames.
                    addFlameEnd(tmpList, nomad.getRowIdx(), nomad.getColIdx());
                    map.getMapPointMatrix()[nomad.getRowIdx()][nomad.getColIdx()].removeFlame();
                    iterator.remove(); // remove it from the list.
                }
            } else { // for all the other sprites.
                // is the current nomad finished?
                if (nomad.isFinished()) {
                    iterator.remove(); // remove it from the list.
                }
            }
        }
        if (!tmpList.isEmpty()) {
            this.addAll(tmpList); // add sprites from the temporary list to the main one.
            tmpList.clear(); // clear the temporary list.
        }
    }

    /**
     * Paint the visible nomads on screen.
     *
     * @param g    the graphics context
     * @param xMap the map abscissa from which painting nomads
     * @param yMap the map ordinate from which painting nomads
     */
    public synchronized void paintBuffer(Graphics2D g, int xMap, int yMap) {

        // paint sprites.
        for (Nomad nomad : this) {
            nomad.updateImage();
            if (nomad.getCurImage() != null) {
                if ((nomad.getYMap() >= yMap && nomad.getYMap() <=
                        yMap + nomad.getCurImage().getWidth(null) + screenHeight) &&
                        (nomad.getXMap() >= xMap && nomad.getXMap() <=
                                xMap + nomad.getCurImage().getHeight(null) / 2 + screenWidth)) {
                    nomad.paintBuffer(g, nomad.getXMap() - xMap, nomad.getYMap() - yMap);
                }
            }
        }
    }
}