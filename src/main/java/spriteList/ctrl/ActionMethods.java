package spriteList.ctrl;

import ai.EnemyAi;
import map.MapPoint;
import map.ctrl.NomadMethods;
import sprite.Sprite;
import sprite.nomad.Bomber;
import sprite.nomad.Enemy;
import sprite.settled.Bomb;
import sprite.settled.Flame;
import sprite.settled.FlameEnd;
import sprite.settled.TimedSettled;
import utils.Direction;
import utils.Tools;

import java.awt.event.KeyEvent;
import java.util.LinkedList;

import static images.ImagesLoader.IMAGE_SIZE;
import static map.ctrl.NomadMethods.isNomadBurning;
import static sprite.ctrl.NomadMethods.isNomadCrossingEnemy;
import static sprite.nomad.Enemy.Action.ACTION_WALKING;
import static spriteList.ctrl.AddingMethods.addBomb;

/**
 * Define a collection of methods to process sprites.
 * These methods can:
 * - update the sprite's action (e.g. change the enemy direction),
 * - check if the sprite should be remove from the list of sprites (e.g. the enemy is dead),
 * - add other sprites according to the sprite's action (e.g. add flames if a bomb is exploding).
 */
public class ActionMethods {

    /**
     * - Re-init the bomber (if the bomber is finished - i.e. dead and the sprite ended),
     * - OR kill the bomber (if the bomber is on a burning case or is crossing an enemy),
     * - OR the bomber acts (if it is time to act),
     * - OR do nothing.
     *
     * @param list           the list of sprites
     * @param tmpList        the temporary list to add new sprites
     * @param mapPointMatrix mapPointMatrix the map (represented by its matrix of MapPoint)
     * @param mapWidth       the map width
     * @param mapHeight      the map height
     * @param bomber         the bomber
     * @param pressedKey     the pressed key
     * @return false (today, the bomber is never removed from the list of sprites).
     */
    public static boolean processBomber(LinkedList<Sprite> list, LinkedList<Sprite> tmpList,
                                        MapPoint[][] mapPointMatrix, int mapWidth, int mapHeight, Bomber bomber, int pressedKey) {
        if (bomber.isFinished()) {
            bomber.init(); // re-init the bomber if finished.

        } else if (bomber.getCurAction() != Bomber.Action.ACTION_DYING) { // the bomber is not finished and not dead.

            // should the bomber die?
            if (!bomber.isInvincible() &&
                    (isNomadBurning(mapPointMatrix, bomber.getXMap(), bomber.getYMap()) ||
                            isNomadCrossingEnemy(list, bomber.getXMap(), bomber.getYMap(), bomber))) {
                bomber.setCurAction(Bomber.Action.ACTION_DYING);

            } else if (bomber.isTimeToAct()) { // it is time to act.
                switch (pressedKey) {
                    case 0: {
                        bomber.setCurAction(Bomber.Action.ACTION_WAITING);
                        break;
                    }
                    case KeyEvent.VK_UP: {
                        bomber.setCurAction(Bomber.Action.ACTION_WALKING);
                        bomber.setCurDirection(Direction.NORTH);
                        if (!NomadMethods.isNomadCrossingMapLimit(mapWidth, mapHeight,
                                bomber.getXMap(), bomber.getYMap() - 1)) {
                            if (!NomadMethods.isNomadCrossingObstacle(mapPointMatrix, bomber.getXMap(),
                                    bomber.getYMap() - 1) &&
                                    !NomadMethods.isNomadCrossingBomb(mapPointMatrix, bomber.getXMap(),
                                            bomber.getYMap() - 1, KeyEvent.VK_UP)) {
                                bomber.setYMap(bomber.getYMap() - 1);
                            } else {
                                shiftBomberIfPossible(mapPointMatrix, bomber, KeyEvent.VK_UP);
                            }
                        }
                        break;
                    }
                    case KeyEvent.VK_DOWN: {
                        bomber.setCurAction(Bomber.Action.ACTION_WALKING);
                        bomber.setCurDirection(Direction.SOUTH);
                        if (!NomadMethods.isNomadCrossingMapLimit(mapWidth, mapHeight,
                                bomber.getXMap(), bomber.getYMap() + 1)) {
                            if (!NomadMethods.isNomadCrossingObstacle(mapPointMatrix, bomber.getXMap(),
                                    bomber.getYMap() + 1) &&
                                    !NomadMethods.isNomadCrossingBomb(mapPointMatrix, bomber.getXMap(),
                                            bomber.getYMap() + 1, KeyEvent.VK_DOWN)) {
                                bomber.setYMap(bomber.getYMap() + 1);
                            } else {
                                shiftBomberIfPossible(mapPointMatrix, bomber, KeyEvent.VK_DOWN);
                            }
                        }
                        break;
                    }
                    case KeyEvent.VK_LEFT: {
                        bomber.setCurAction(Bomber.Action.ACTION_WALKING);
                        bomber.setCurDirection(Direction.WEST);
                        if (!NomadMethods.isNomadCrossingMapLimit(mapWidth, mapHeight,
                                bomber.getXMap() - 1, bomber.getYMap())) {
                            if (!NomadMethods.isNomadCrossingObstacle(mapPointMatrix, bomber.getXMap() - 1,
                                    bomber.getYMap()) &&
                                    !NomadMethods.isNomadCrossingBomb(mapPointMatrix, bomber.getXMap() - 1,
                                            bomber.getYMap(), KeyEvent.VK_LEFT)) {
                                bomber.setXMap(bomber.getXMap() - 1);
                            } else {
                                shiftBomberIfPossible(mapPointMatrix, bomber, KeyEvent.VK_LEFT);
                            }
                        }
                        break;
                    }
                    case KeyEvent.VK_RIGHT: {
                        bomber.setCurAction(Bomber.Action.ACTION_WALKING);
                        bomber.setCurDirection(Direction.EAST);
                        if (!NomadMethods.isNomadCrossingMapLimit(mapWidth, mapHeight,
                                bomber.getXMap() + 1, bomber.getYMap())) {
                            if (!NomadMethods.isNomadCrossingObstacle(mapPointMatrix, bomber.getXMap() + 1,
                                    bomber.getYMap()) &&
                                    !NomadMethods.isNomadCrossingBomb(mapPointMatrix, bomber.getXMap() + 1,
                                            bomber.getYMap(), KeyEvent.VK_RIGHT)) {
                                bomber.setXMap(bomber.getXMap() + 1);
                            } else {
                                shiftBomberIfPossible(mapPointMatrix, bomber, KeyEvent.VK_RIGHT);
                            }
                        }
                        break;
                    }
                    case KeyEvent.VK_B: {
                        addBomb(tmpList, mapPointMatrix, new Bomb(Tools.getCharRowIdx(bomber.getYMap()),
                                Tools.getCharColIdx(bomber.getXMap()), 3));
                        break;
                    }
                    case KeyEvent.VK_W: {
                        bomber.setCurAction(Bomber.Action.ACTION_WINING);
                        break;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Shift the bomber of a pixel to help him finding the way (if possible).
     *
     * @param mapPointMatrix mapPointMatrix the map (represented by its matrix of MapPoint)
     * @param bomber         the bomber
     * @param pressedKey     the pressed key
     */
    private static void shiftBomberIfPossible(MapPoint[][] mapPointMatrix, Bomber bomber, int pressedKey) {
        int bbManRowIdx = bomber.getYMap() / IMAGE_SIZE;
        int bbManColIdx = bomber.getXMap() / IMAGE_SIZE;
        int bbManRowShift = bomber.getYMap() % IMAGE_SIZE;
        int bbManColShift = bomber.getXMap() % IMAGE_SIZE;

        switch (pressedKey) {
            case KeyEvent.VK_UP: {
                if (mapPointMatrix[bbManRowIdx - 1][bbManColIdx].isPathway() && // the upper case is a pathway
                        !mapPointMatrix[bbManRowIdx - 1][bbManColIdx].isBombing()) { // && !bombing.
                    if (bbManColShift < IMAGE_SIZE / 2) { // bomber on left side of its case.
                        bomber.setXMap(bomber.getXMap() + 1);
                    } else if (bbManColShift > IMAGE_SIZE / 2) { // bomber on right side of its case.
                        bomber.setXMap(bomber.getXMap() - 1);
                    }
                }
                break;
            }
            case KeyEvent.VK_DOWN: {
                if (mapPointMatrix[bbManRowIdx + 1][bbManColIdx].isPathway() && // the lower case is a pathway
                        !mapPointMatrix[bbManRowIdx + 1][bbManColIdx].isBombing()) { // && !bombing.
                    if (bbManColShift < IMAGE_SIZE / 2) { // bomber on left side of its case.
                        bomber.setXMap(bomber.getXMap() + 1);
                    } else if (bbManColShift > IMAGE_SIZE / 2) { // bomber on right side of its case.
                        bomber.setXMap(bomber.getXMap() - 1);
                    }
                }
                break;
            }
            case KeyEvent.VK_LEFT: {
                if (mapPointMatrix[bbManRowIdx][bbManColIdx - 1].isPathway() && // the left case is a pathway
                        !mapPointMatrix[bbManRowIdx][bbManColIdx - 1].isBombing()) { // && !bombing.
                    if (bbManRowShift < IMAGE_SIZE / 2) { // bomber on upper side of its case.
                        bomber.setYMap(bomber.getYMap() + 1);
                    }
                }
                if (mapPointMatrix[bbManRowIdx - 1][bbManColIdx - 1].isPathway() && // the upper/left case is a
                        // pathway
                        !mapPointMatrix[bbManRowIdx - 1][bbManColIdx - 1].isBombing()) { // && !bombing.
                    if (bbManRowShift < IMAGE_SIZE / 2) { // bomber on upper side of its case.
                        bomber.setYMap(bomber.getYMap() - 1);
                    }
                }
                break;
            }
            case KeyEvent.VK_RIGHT: {
                if (mapPointMatrix[bbManRowIdx][bbManColIdx + 1].isPathway() && // the right case is a pathway
                        !mapPointMatrix[bbManRowIdx][bbManColIdx + 1].isBombing()) { // && !bombing.
                    if (bbManRowShift < IMAGE_SIZE / 2) { // bomber on upper side of its case.
                        bomber.setYMap(bomber.getYMap() + 1);
                    }
                }
                if (mapPointMatrix[bbManRowIdx - 1][bbManColIdx + 1].isPathway() && // the upper/right case is a
                        // pathway
                        !mapPointMatrix[bbManRowIdx - 1][bbManColIdx + 1].isBombing()) { // && !bombing.
                    if (bbManRowShift < IMAGE_SIZE / 2) { // bomber on upper side of its case.
                        bomber.setYMap(bomber.getYMap() - 1);
                    }
                }
                break;
            }
        }
    }

    /**
     * - Notice that the enemy must be removed from the list (if the enemy is finished - i.e. dead and the sprite
     * ended),
     * - OR kill the enemy (if the enemy is on a burning case),
     * - OR compute the next direction (if it is tim to act),
     * - OR do nothing.
     *
     * @param list           the list of sprites
     * @param mapPointMatrix mapPointMatrix the map (represented by its matrix of MapPoint)
     * @param mapWidth       the map width
     * @param mapHeight      the map height
     * @param enemy          the enemy
     * @return true if the enemy should be removed from the list, false otherwise.
     */
    public static boolean processEnemy(LinkedList<Sprite> list, MapPoint[][] mapPointMatrix, int mapWidth,
                                       int mapHeight, Enemy enemy) {
        boolean shouldBeRemoved = false;
        if (enemy.isFinished()) {
            shouldBeRemoved = true;
        } else if (enemy.getCurAction() != Enemy.Action.ACTION_DYING) { // the bomber is not finished and not dead.

            // should the enemy die?
            if (isNomadBurning(mapPointMatrix, enemy.getXMap(), enemy.getYMap())) {
                enemy.setCurAction(Enemy.Action.ACTION_DYING);

            } else if (enemy.isTimeToAct()) { // it is time to act.

                // compute the next direction.
                Direction newDirection = EnemyAi.computeNextDirection(
                        mapPointMatrix,
                        mapWidth,
                        mapHeight,
                        list,
                        enemy);

                // assign the new coordinates and action if found.
                if (newDirection != null) {
                    enemy.setCurAction(ACTION_WALKING);
                    enemy.setCurDirection(newDirection);
                    switch (newDirection) {
                        case NORTH: {
                            enemy.setYMap(enemy.getYMap() - 1);
                            break;
                        }
                        case SOUTH: {
                            enemy.setYMap(enemy.getYMap() + 1);
                            break;
                        }
                        case WEST: {
                            enemy.setXMap(enemy.getXMap() - 1);
                            break;
                        }
                        case EAST: {
                            enemy.setXMap(enemy.getXMap() + 1);
                            break;
                        }
                    }
                }
            }
        }
        return shouldBeRemoved;
    }

    /**
     * - Notice that the bomb must be removed from the list (if the sprite is finished),
     * -- AND add flames,
     * -- AND remove the bombing status of the relative case.
     * - OR ended the bomb (if the bomb is on a burning case),
     * - OR do nothing.
     *
     * @param tmpList        the temporary list of sprites to add new elements
     * @param mapPointMatrix mapPointMatrix the map (represented by its matrix of MapPoint)
     * @param mapWidth       the map width
     * @param mapHeight      the map height
     * @param bomb           the bomb
     * @return true if the bomb should be removed from the list, false otherwise.
     */
    public static boolean processBomb(LinkedList<Sprite> tmpList, MapPoint[][] mapPointMatrix, int mapWidth,
                                      int mapHeight, Bomb bomb) {
        boolean shouldBeRemoved = false;
        if (bomb.isFinished()) {
            AddingMethods.addFlames(tmpList, mapPointMatrix, mapWidth, mapHeight, bomb.getRowIdx(), bomb.getColIdx(),
                    bomb.getFlameSize()); // create flames.
            mapPointMatrix[bomb.getRowIdx()][bomb.getColIdx()].setBombing(false);
            shouldBeRemoved = true;

        } else if (mapPointMatrix[bomb.getRowIdx()][bomb.getColIdx()].isBurning()) {  // is it on a burning case?
            bomb.setCurStatus(TimedSettled.Status.STATUS_ENDED);
        }
        return shouldBeRemoved;
    }

    /**
     * - Notice that the flame must be removed from the list (if the sprite is finished),
     * -- AND add a flame end.
     * - OR do nothing.
     *
     * @param tmpList        the temporary list of sprites to add new elements
     * @param mapPointMatrix mapPointMatrix the map (represented by its matrix of MapPoint)
     * @param flame          the flame
     * @return true if the flame should be removed from the list, false otherwise.
     */
    public static boolean processFlame(LinkedList<Sprite> tmpList, MapPoint[][] mapPointMatrix, Flame flame) {
        boolean shouldBeRemoved = false;
        if (flame.isFinished()) {

            // create flame ends.
            AddingMethods.addFlameEnd(tmpList, new FlameEnd(flame.getRowIdx(), flame.getColIdx()));
            mapPointMatrix[flame.getRowIdx()][flame.getColIdx()].removeFlame();
            shouldBeRemoved = true;
        }
        return shouldBeRemoved;
    }

    /**
     * - Notice that the flame end must be removed from the list (if the sprite is finished),
     * - OR do nothing.
     *
     * @param flameEnd the flame end
     * @return true if the flame end should be removed from the list, false otherwise.
     */
    public static boolean processFlameEnd(FlameEnd flameEnd) {
        boolean shouldBeRemoved = false;
        if (flameEnd.isFinished()) {
            shouldBeRemoved = true;
        }
        return shouldBeRemoved;
    }
}