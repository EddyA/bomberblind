package spriteList.ctrl;

import static map.ctrl.NomadMethods.isNomadBurning;
import static sprite.ctrl.NomadMethods.isNomadCrossingEnemy;
import static sprite.nomad.Enemy.Action.ACTION_WALKING;

import java.util.LinkedList;

import ai.EnemyAi;
import exceptions.CannotMoveEnemyException;
import map.MapPoint;
import sprite.Sprite;
import sprite.nomad.Bomber;
import sprite.nomad.Enemy;
import sprite.settled.Bomb;
import sprite.settled.Flame;
import sprite.settled.FlameEnd;
import sprite.settled.TimedSettled;
import utils.Direction;

/**
 * Define a collection of methods to process sprites.
 * These methods can:
 * - update the sprite's action (e.g. change the enemy direction),
 * - check if the sprite should be remove from the list of sprites (e.g. the enemy is dead),
 * - add other sprites according to the sprite's action (e.g. add flames if a bomb is exploding).
 */
public class ActionMethods {

    /**
     * - Re-init the bomber (if the bomber is dead and the sprite ended),
     * - OR kill the bomber (if the bomber is on a burning case or is crossing an enemy),
     * - OR do nothing.
     *
     * @param list           the list of sprites
     * @param mapPointMatrix mapPointMatrix the map (represented by its matrix of MapPoint)
     * @param bomber         the bomber
     * @return true if the bomber should be removed from the list, false otherwise.
     */
    public static boolean processBomber(LinkedList<Sprite> list, MapPoint[][] mapPointMatrix, Bomber bomber) {
        if (bomber.getCurAction() != Bomber.Action.ACTION_DYING) { // not ended and not dead.

            // should the bomber die?
            if (!bomber.isInvincible() &&
                    (isNomadBurning(mapPointMatrix, bomber.getXMap(), bomber.getYMap()) ||
                            isNomadCrossingEnemy(list, bomber.getXMap(), bomber.getYMap(), bomber))) {
                bomber.setCurAction(Bomber.Action.ACTION_DYING);
            }
        }
        if (bomber.isFinished()) {
            bomber.init();
        }
        return false;
    }

    /**
     * - Notice that the bomber must be removed from the list (if the enemy is dead and the sprite ended),
     * - OR kill the enemy (if the enemy is on a burning case),
     * - OR compute the next direction.
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
        if (enemy.getCurAction() != Enemy.Action.ACTION_DYING) { // not ended and not dying.

            // should the enemy die?
            if (isNomadBurning(mapPointMatrix, enemy.getXMap(), enemy.getYMap())) {
                enemy.setCurAction(Enemy.Action.ACTION_DYING);

            } else if (enemy.isTimeToMove()) { // not dead -> should the enemy move?
                try {
                    // compute the next direction.
                    Direction newDirection = EnemyAi.computeNextDirection(mapPointMatrix, mapWidth, mapHeight, list,
                            enemy);

                    // assign the new direction.
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
                } catch (CannotMoveEnemyException e) {
                    // nothing to do, just wait for the next iteration.
                }
            }
        }
        if (enemy.isFinished()) {
            shouldBeRemoved = true;
        }
        return shouldBeRemoved;
    }

    /**
     * - Notice that the bomb must be removed from the list (if the sprite is ended),
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
        if (mapPointMatrix[bomb.getRowIdx()][bomb.getColIdx()].isBurning()) {  // is it on a burning case?
            bomb.setCurStatus(TimedSettled.Status.STATUS_ENDED);
        }
        if (bomb.isFinished()) {
            AddingMethods.addFlames(tmpList, mapPointMatrix, mapWidth, mapHeight, bomb.getRowIdx(), bomb.getColIdx(),
                    bomb.getFlameSize()); // create flames.
            mapPointMatrix[bomb.getRowIdx()][bomb.getColIdx()].setBombing(false);
            shouldBeRemoved = true;

        }
        return shouldBeRemoved;
    }

    /**
     * - Notice that the flame must be removed from the list (if the sprite is ended),
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
     * - Notice that the flame end must be removed from the list (if the sprite is ended),
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
