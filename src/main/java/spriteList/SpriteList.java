package spriteList;

import ai.EnemyAi;
import exceptions.CannotMoveNomadException;
import map.abstracts.Map;
import sprite.abstracts.Sprite;
import sprite.nomad.CloakedSkeleton;
import sprite.nomad.MecaAngel;
import sprite.nomad.Mummy;
import sprite.nomad.abstracts.Bomber;
import sprite.nomad.abstracts.Enemy;
import sprite.settled.Bomb;
import sprite.settled.ConclusionFlame;
import sprite.settled.Flame;

import java.awt.*;
import java.util.LinkedList;
import java.util.ListIterator;

import static map.ctrl.NomadMethods.isNomadBurning;
import static sprite.ctrl.NomadMethods.isNomadCrossingEnemy;

public class SpriteList extends LinkedList<Sprite> {

    // create a temporary list to manage addings and avoid concurent accesses.
    private LinkedList<Sprite> tmpList = new LinkedList<>();

    private Map map;
    private int screenWidth; // widht of the screen (expressed in pixel).
    private int screenHeight; // height of the screen (expressed in pixel).

    public SpriteList(Map map, int screenWidth, int screenHeight) {
        this.map = map;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    // ------ //
    // BOMBER //
    // ------ //

    /**
     * Add the main bomber to the list.
     *
     * @param bomber the bomber to add
     */
    public void addBomber(Bomber bomber) {
        this.add(bomber);
    }

    // ------- //
    // ENEMIES //
    // ------- //

    /**
     * Add a mummy to the list.
     *
     * @param xMap the abscissa of the mummy
     * @param yMap the ordinate of the mummy
     */
    public void addMummy(int xMap, int yMap) {
        this.add(new Mummy(xMap, yMap));
    }

    /**
     * Add a cloaked skeleton to the list.
     *
     * @param xMap the abscissa of the cloaked skeleton
     * @param yMap the ordinate of the cloaked skeleton
     */
    public void addCloakedSkeleton(int xMap, int yMap) {
        this.add(new CloakedSkeleton(xMap, yMap));
    }

    /**
     * Add a meca angel to the list.
     *
     * @param xMap the abscissa of the meca angel
     * @param yMap the ordinate of the meca angel
     */
    public void addMecaAngel(int xMap, int yMap) {
        this.add(new MecaAngel(xMap, yMap));
    }

    // ------- //
    // SETTLED //
    // ------- //

    /**
     * Add a bomb to the list.
     *
     * @param rowIdx    the map row index of the bomb
     * @param colIdx    the map column index of the bomb
     * @param flameSize the flame size of the bomb
     */
    public void addBomb(int rowIdx, int colIdx, int flameSize) {
        addBomb(this, rowIdx, colIdx, flameSize);
    }

    /**
     * Add a bomb to a list.
     * The bomb is adding if:
     * - no bomb is already in place.
     *
     * @param list      the list into which putting the bomb
     * @param rowIdx    the map row index of the bomb
     * @param colIdx    the map column index of the bomb
     * @param flameSize the flame size of the bomb
     */
    private void addBomb(LinkedList<Sprite> list, int rowIdx, int colIdx, int flameSize) {
        if (map.getMapPointMatrix()[rowIdx][colIdx].isPathway() &&
                !map.getMapPointMatrix()[rowIdx][colIdx].isBombing() &&
                !map.getMapPointMatrix()[rowIdx][colIdx].isBurning()) {
            map.getMapPointMatrix()[rowIdx][colIdx].setBombing(true);
            list.add(new Bomb(rowIdx, colIdx, flameSize));
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
    private void addFlames(LinkedList<Sprite> list, int centralRowIdx, int centralColIdx, int flameSize) {

        // place left flames.
        for (int i = 1, j = centralColIdx - 1; i <= flameSize && j >= 0; i++, j--) { // from center to left.
            if (!addFlame(list, centralRowIdx, centralColIdx - i)) {
                break; // break loop.
            }
        }

        // place right flames.
        for (int i = 1, j = centralColIdx + 1; i <= flameSize && j < map.getMapWidth(); i++, j++) { // from center to
            // right.
            if (!addFlame(list, centralRowIdx, centralColIdx + i)) {
                break; // break loop.
            }
        }

        // in order to display flames in a good order, we must parse that axis before burning cases.
        int rowIdx = 0;
        for (int i = 1, j = centralRowIdx - 1; i <= flameSize && j >= 0; i++, j--) { // from center to upper.
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

        for (int i = 1, j = centralRowIdx + 1; i <= flameSize && j < map.getMapHeight(); i++, j++) { // from center to
            // lower.
            if (!addFlame(list, centralRowIdx + i, centralColIdx)) {
                break; // break loop.
            }
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
        if (map.getMapPointMatrix()[rowIdx][colIdx].isPathway()) {
            map.getMapPointMatrix()[rowIdx][colIdx].addFlame();
            map.getMapPointMatrix()[rowIdx][colIdx].setImageAsBurned();
            list.add(new Flame(rowIdx, colIdx));
            return true; // the next case can burn.
        } else if (map.getMapPointMatrix()[rowIdx][colIdx].isMutable() ||
                map.getMapPointMatrix()[rowIdx][colIdx].isBombing()) {
            map.getMapPointMatrix()[rowIdx][colIdx].setPathway(true);
            map.getMapPointMatrix()[rowIdx][colIdx].setMutable(false);
            map.getMapPointMatrix()[rowIdx][colIdx].addFlame();
            map.getMapPointMatrix()[rowIdx][colIdx].setImageAsBurned();
            list.add(new Flame(rowIdx, colIdx));
            return false; // the next case should not burn.
        } else {
            return false; // the next case should not burn.
        }
    }

    /**
     * Add a conclusion flame to a list.
     *
     * @param list   the list into which putting the flame
     * @param rowIdx the map row index of the flame
     * @param colIdx the map column index of the flame
     */
    private void addConclusionFlame(LinkedList<Sprite> list, int rowIdx, int colIdx) {
        if (map.getMapPointMatrix()[rowIdx][colIdx].isBurning()) {
            list.add(new ConclusionFlame(rowIdx, colIdx));
        }
    }

    // ------- //
    // CONTROL //
    // ------- //

    /**
     * Update curStatus of nomads.
     */
    public synchronized void updateStatusAndClean() {
        for (ListIterator<Sprite> iterator = this.listIterator(); iterator.hasNext(); ) {
            Sprite sprite = iterator.next();

            if (sprite.getClass().getSuperclass().getSimpleName().equals("Bomber")) { // it is a bomber.
                Bomber bomber = (Bomber) sprite; // cast to bomber.

                // is it finished?
                if (bomber.isFinished()) {
                    bomber.init(); // bombers are never removed from the list, they are just re-initialized.
                } else if (bomber.getCurStatus() != Bomber.status.STATUS_DYING) { // not finished and not dead.

                    // should the bomber die?
                    if (!bomber.isInvincible() &&
                            (isNomadBurning(map.getMapPointMatrix(), bomber.getXMap(), bomber.getYMap()) ||
                                    isNomadCrossingEnemy(this, bomber, bomber.getXMap(), bomber.getYMap()))) {
                        bomber.setCurStatus(Bomber.status.STATUS_DYING);
                    }
                }
            } else if (sprite.getClass().getSuperclass().getSimpleName().equals("Enemy")) { // it is an enemy.
                Enemy enemy = (Enemy) sprite; // cast to enemy.

                // is it finished?
                if (enemy.isFinished()) {
                    iterator.remove(); // remove it from the list.
                } else if (enemy.getCurStatus() != Enemy.status.STATUS_DYING) { // not finished and not dead.

                    // should the enemy die?
                    if (isNomadBurning(map.getMapPointMatrix(), enemy.getXMap(), enemy.getYMap())) {
                        enemy.setCurStatus(Enemy.status.STATUS_DYING);

                    } else if (enemy.isTimeToMove()) { // not dead -> should the enemy move?
                        try {
                            Enemy.status newStatus = EnemyAi.computeNextMove(map.getMapPointMatrix(), map.getMapWidth(),
                                    map.getMapHeight(), this, enemy); // try to compute the nex move.

                            enemy.setCurStatus(newStatus); // if the previous function has not thrown an exception.
                            switch (enemy.getCurStatus()) {
                                case STATUS_WALKING_BACK: {
                                    sprite.setYMap(sprite.getYMap() - 1);
                                    break;
                                }
                                case STATUS_WALKING_FRONT: {
                                    sprite.setYMap(sprite.getYMap() + 1);
                                    break;
                                }
                                case STATUS_WALKING_LEFT: {
                                    sprite.setXMap(sprite.getXMap() - 1);
                                    break;
                                }
                                case STATUS_WALKING_RIGHT: {
                                    sprite.setXMap(sprite.getXMap() + 1);
                                    break;
                                }
                            }
                        } catch (CannotMoveNomadException e) {
                            // nothing to do, just wait for the next iteration.
                        }
                    }
                }
            } else if (sprite.getClass().getSimpleName().equals("Bomb")) { // it is a bomb.
                Bomb bomb = (Bomb) sprite; // cast to enemy.

                // is it finished?
                if (bomb.isFinished() ||
                        // OR is it on a burning case?
                        map.getMapPointMatrix()[bomb.getRowIdx()][bomb.getColIdx()].isBurning()) {
                    // create flames.
                    addFlames(tmpList, bomb.getRowIdx(), bomb.getColIdx(), bomb.getFlameSize());
                    map.getMapPointMatrix()[bomb.getRowIdx()][bomb.getColIdx()].setBombing(false);
                    iterator.remove(); // remove it from the list.
                }
            } else if (sprite.getClass().getSimpleName().equals("Flame")) { // it is a flame.
                Flame flame = (Flame) sprite; // cast to enemy.

                // is it finished?
                if (flame.isFinished()) {
                    // create conclusion flames.
                    addConclusionFlame(tmpList, flame.getRowIdx(), flame.getColIdx());
                    map.getMapPointMatrix()[flame.getRowIdx()][flame.getColIdx()].removeFlame();
                    iterator.remove(); // remove it from the list.
                }
            } else { // for all the other sprites.
                // is the current abstracts finished?
                if (sprite.isFinished()) {
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
        for (Sprite sprite : this) {
            sprite.updateImage();
            if ((sprite.getCurImage() != null) && // happens when the bomber is invincible.
                    !sprite.isFinished()) {
                if ((sprite.getYMap() >= yMap)
                        && (sprite.getYMap() <= yMap + sprite.getCurImage().getWidth(null) + screenHeight)
                        && (sprite.getXMap() >= xMap)
                        && (sprite.getXMap() <= xMap + sprite.getCurImage().getHeight(null) / 2 + screenWidth)) {
                    sprite.paintBuffer(g, sprite.getXMap() - xMap, sprite.getYMap() - yMap);
                }
            }
        }
    }
}
