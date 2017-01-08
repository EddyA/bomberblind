package spriteList.ctrl;

import ai.EnemyAi;
import map.MapPoint;
import map.ctrl.NomadMethods;
import sprite.Sprite;
import sprite.nomad.Bomber;
import sprite.nomad.BreakingEnemy;
import sprite.nomad.WalkingEnemy;
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
import static spriteList.ctrl.AddingMethods.addBomb;
import static utils.Action.*;

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
    public static boolean processBomber(LinkedList<Sprite> list,
                                        LinkedList<Sprite> tmpList,
                                        MapPoint[][] mapPointMatrix,
                                        int mapWidth,
                                        int mapHeight,
                                        Bomber bomber,
                                        int pressedKey) {
        if (bomber.isFinished()) {
            bomber.init(); // re-init the bomber if finished.

        } else if (bomber.getCurAction() != ACTION_DYING) { // the bomber is not finished and not dead.

            // should the bomber die?
            if (!bomber.isInvincible() &&
                    (isNomadBurning(mapPointMatrix, bomber.getxMap(), bomber.getyMap()) ||
                            isNomadCrossingEnemy(list, bomber.getxMap(), bomber.getyMap(), bomber))) {
                bomber.setCurAction(ACTION_DYING);

            } else if (bomber.isTimeToAct()) { // it is time to act.
                switch (pressedKey) {
                    case 0: {
                        bomber.setCurAction(ACTION_WAITING);
                        break;
                    }
                    case KeyEvent.VK_UP: {
                        bomber.setCurAction(ACTION_WALKING);
                        bomber.setCurDirection(Direction.NORTH);
                        if (!NomadMethods.isNomadCrossingMapLimit(mapWidth, mapHeight,
                                bomber.getxMap(), bomber.getyMap() - 1)) {
                            if (!NomadMethods.isNomadCrossingObstacle(mapPointMatrix, bomber.getxMap(),
                                    bomber.getyMap() - 1) &&
                                    !NomadMethods.isNomadCrossingBomb(mapPointMatrix, bomber.getxMap(),
                                            bomber.getyMap() - 1, KeyEvent.VK_UP)) {
                                bomber.setyMap(bomber.getyMap() - 1);
                            } else {
                                shiftBomberIfPossible(mapPointMatrix, bomber, KeyEvent.VK_UP);
                            }
                        }
                        break;
                    }
                    case KeyEvent.VK_DOWN: {
                        bomber.setCurAction(ACTION_WALKING);
                        bomber.setCurDirection(Direction.SOUTH);
                        if (!NomadMethods.isNomadCrossingMapLimit(mapWidth, mapHeight,
                                bomber.getxMap(), bomber.getyMap() + 1)) {
                            if (!NomadMethods.isNomadCrossingObstacle(mapPointMatrix, bomber.getxMap(),
                                    bomber.getyMap() + 1) &&
                                    !NomadMethods.isNomadCrossingBomb(mapPointMatrix, bomber.getxMap(),
                                            bomber.getyMap() + 1, KeyEvent.VK_DOWN)) {
                                bomber.setyMap(bomber.getyMap() + 1);
                            } else {
                                shiftBomberIfPossible(mapPointMatrix, bomber, KeyEvent.VK_DOWN);
                            }
                        }
                        break;
                    }
                    case KeyEvent.VK_LEFT: {
                        bomber.setCurAction(ACTION_WALKING);
                        bomber.setCurDirection(Direction.WEST);
                        if (!NomadMethods.isNomadCrossingMapLimit(mapWidth, mapHeight,
                                bomber.getxMap() - 1, bomber.getyMap())) {
                            if (!NomadMethods.isNomadCrossingObstacle(mapPointMatrix, bomber.getxMap() - 1,
                                    bomber.getyMap()) &&
                                    !NomadMethods.isNomadCrossingBomb(mapPointMatrix, bomber.getxMap() - 1,
                                            bomber.getyMap(), KeyEvent.VK_LEFT)) {
                                bomber.setxMap(bomber.getxMap() - 1);
                            } else {
                                shiftBomberIfPossible(mapPointMatrix, bomber, KeyEvent.VK_LEFT);
                            }
                        }
                        break;
                    }
                    case KeyEvent.VK_RIGHT: {
                        bomber.setCurAction(ACTION_WALKING);
                        bomber.setCurDirection(Direction.EAST);
                        if (!NomadMethods.isNomadCrossingMapLimit(mapWidth, mapHeight,
                                bomber.getxMap() + 1, bomber.getyMap())) {
                            if (!NomadMethods.isNomadCrossingObstacle(mapPointMatrix, bomber.getxMap() + 1,
                                    bomber.getyMap()) &&
                                    !NomadMethods.isNomadCrossingBomb(mapPointMatrix, bomber.getxMap() + 1,
                                            bomber.getyMap(), KeyEvent.VK_RIGHT)) {
                                bomber.setxMap(bomber.getxMap() + 1);
                            } else {
                                shiftBomberIfPossible(mapPointMatrix, bomber, KeyEvent.VK_RIGHT);
                            }
                        }
                        break;
                    }
                    case KeyEvent.VK_B: {
                        addBomb(tmpList, mapPointMatrix, new Bomb(Tools.getCharRowIdx(bomber.getyMap()),
                                Tools.getCharColIdx(bomber.getxMap()), 3));
                        break;
                    }
                    case KeyEvent.VK_W: {
                        bomber.setCurAction(ACTION_WINING);
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
    public static void shiftBomberIfPossible(MapPoint[][] mapPointMatrix, Bomber bomber, int pressedKey) {
        int bbManRowIdx = bomber.getyMap() / IMAGE_SIZE;
        int bbManColIdx = bomber.getxMap() / IMAGE_SIZE;
        int bbManRowShift = bomber.getyMap() % IMAGE_SIZE;
        int bbManColShift = bomber.getxMap() % IMAGE_SIZE;

        switch (pressedKey) {
            case KeyEvent.VK_UP: {
                if (mapPointMatrix[bbManRowIdx - 1][bbManColIdx].isPathway() && // the upper case is a pathway
                        !mapPointMatrix[bbManRowIdx - 1][bbManColIdx].isBombing()) { // && !bombing.
                    if (bbManColShift < IMAGE_SIZE / 2) { // bomber on left side of its case.
                        bomber.setxMap(bomber.getxMap() + 1);
                    } else if (bbManColShift > IMAGE_SIZE / 2) { // bomber on right side of its case.
                        bomber.setxMap(bomber.getxMap() - 1);
                    }
                }
                break;
            }
            case KeyEvent.VK_DOWN: {
                if (mapPointMatrix[bbManRowIdx + 1][bbManColIdx].isPathway() && // the lower case is a pathway
                        !mapPointMatrix[bbManRowIdx + 1][bbManColIdx].isBombing()) { // && !bombing.
                    if (bbManColShift < IMAGE_SIZE / 2) { // bomber on left side of its case.
                        bomber.setxMap(bomber.getxMap() + 1);
                    } else if (bbManColShift > IMAGE_SIZE / 2) { // bomber on right side of its case.
                        bomber.setxMap(bomber.getxMap() - 1);
                    }
                }
                break;
            }
            case KeyEvent.VK_LEFT: {
                if (mapPointMatrix[bbManRowIdx][bbManColIdx - 1].isPathway() && // the left case is a pathway
                        !mapPointMatrix[bbManRowIdx][bbManColIdx - 1].isBombing()) { // && !bombing.
                    if (bbManRowShift < IMAGE_SIZE / 2) { // bomber on upper side of its case.
                        bomber.setyMap(bomber.getyMap() + 1);
                    }
                }
                if (mapPointMatrix[bbManRowIdx - 1][bbManColIdx - 1].isPathway() && // the upper/left case is a
                        // pathway
                        !mapPointMatrix[bbManRowIdx - 1][bbManColIdx - 1].isBombing()) { // && !bombing.
                    if (bbManRowShift < IMAGE_SIZE / 2) { // bomber on upper side of its case.
                        bomber.setyMap(bomber.getyMap() - 1);
                    }
                }
                break;
            }
            case KeyEvent.VK_RIGHT: {
                if (mapPointMatrix[bbManRowIdx][bbManColIdx + 1].isPathway() && // the right case is a pathway
                        !mapPointMatrix[bbManRowIdx][bbManColIdx + 1].isBombing()) { // && !bombing.
                    if (bbManRowShift < IMAGE_SIZE / 2) { // bomber on upper side of its case.
                        bomber.setyMap(bomber.getyMap() + 1);
                    }
                }
                if (mapPointMatrix[bbManRowIdx - 1][bbManColIdx + 1].isPathway() && // the upper/right case is a
                        // pathway
                        !mapPointMatrix[bbManRowIdx - 1][bbManColIdx + 1].isBombing()) { // && !bombing.
                    if (bbManRowShift < IMAGE_SIZE / 2) { // bomber on upper side of its case.
                        bomber.setyMap(bomber.getyMap() - 1);
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
     * @param walkingEnemy   the enemy
     * @return true if the enemy should be removed from the list, false otherwise.
     */
    public static boolean processWalkingEnemy(LinkedList<Sprite> list,
                                              MapPoint[][] mapPointMatrix,
                                              int mapWidth,
                                              int mapHeight,
                                              WalkingEnemy walkingEnemy) {
        boolean shouldBeRemoved = false;
        if (walkingEnemy.isFinished()) {
            shouldBeRemoved = true;
        } else if (walkingEnemy.getCurAction() != ACTION_DYING) { // the bomber is not finished and not dead.

            // should the enemy die?
            if (isNomadBurning(mapPointMatrix, walkingEnemy.getxMap(), walkingEnemy.getyMap())) {
                walkingEnemy.setCurAction(ACTION_DYING);

            } else if (walkingEnemy.isTimeToAct()) { // it is time to act.
                moveEnemyIfPossible(list, mapPointMatrix, mapWidth, mapHeight, walkingEnemy);
            }
        }
        return shouldBeRemoved;
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
     * @param breakingEnemy   the enemy
     * @return true if the enemy should be removed from the list, false otherwise.
     */
    public static boolean processBreakingEnemy(LinkedList<Sprite> list,
                                              MapPoint[][] mapPointMatrix,
                                              int mapWidth,
                                              int mapHeight,
                                              BreakingEnemy breakingEnemy) {
        boolean shouldBeRemoved = false;
        if (breakingEnemy.isFinished()) {
            shouldBeRemoved = true;
        } else if (breakingEnemy.getCurAction() != ACTION_DYING) { // the bomber is not finished and not dead.

            // should the enemy die?
            if (isNomadBurning(mapPointMatrix, breakingEnemy.getxMap(), breakingEnemy.getyMap())) {
                breakingEnemy.setCurAction(ACTION_DYING);

            } else if (breakingEnemy.isTimeToAct()) { // it is time to act.
                moveEnemyIfPossible(list, mapPointMatrix, mapWidth, mapHeight, breakingEnemy);
            }
        }
        return shouldBeRemoved;
    }

    /**
     * Move the enemy of a pixel if possible, favoring the current direction and changing of it if needed.
     * If the enemy cannot move (i.e. blocked off), do nothing.
     *
     * @param list           the list of sprites
     * @param mapPointMatrix mapPointMatrix the map (represented by its matrix of MapPoint)
     * @param mapWidth       the map width
     * @param mapHeight      the map height
     * @param walkingEnemy   the enemy
     */
    public static void moveEnemyIfPossible(LinkedList<Sprite> list,
                                           MapPoint[][] mapPointMatrix,
                                           int mapWidth,
                                           int mapHeight,
                                           WalkingEnemy walkingEnemy) {
        // compute the next direction.
        Direction newDirection = EnemyAi.computeNextDirection(
                mapPointMatrix,
                mapWidth,
                mapHeight,
                list,
                walkingEnemy);

        // assign the new coordinates and move if possible.
        if (newDirection != null) {
            walkingEnemy.setCurAction(ACTION_WALKING);
            walkingEnemy.setCurDirection(newDirection);
            switch (newDirection) {
                case NORTH: {
                    walkingEnemy.setyMap(walkingEnemy.getyMap() - 1);
                    break;
                }
                case SOUTH: {
                    walkingEnemy.setyMap(walkingEnemy.getyMap() + 1);
                    break;
                }
                case WEST: {
                    walkingEnemy.setxMap(walkingEnemy.getxMap() - 1);
                    break;
                }
                case EAST: {
                    walkingEnemy.setxMap(walkingEnemy.getxMap() + 1);
                    break;
                }
            }
        }
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
    public static boolean processBomb(LinkedList<Sprite> tmpList,
                                      MapPoint[][] mapPointMatrix,
                                      int mapWidth,
                                      int mapHeight,
                                      Bomb bomb) {
        boolean shouldBeRemoved = false;
        if (bomb.isFinished()) {
            AddingMethods.addFlames(tmpList, mapPointMatrix, mapWidth, mapHeight, bomb.getRowIdx(), bomb.getColIdx(),
                    bomb.getFlameSize()); // create flames.
            mapPointMatrix[bomb.getRowIdx()][bomb.getColIdx()].setBombing(false);
            shouldBeRemoved = true;

        } else if (mapPointMatrix[bomb.getRowIdx()][bomb.getColIdx()].isBurning()) {  // is it on a burning case?
            bomb.setStatus(TimedSettled.Status.STATUS_ENDED);
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