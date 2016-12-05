package spriteList.ctrl;

import ai.EnemyAi;
import exceptions.CannotMoveEnemyException;
import map.MapPoint;
import sprite.abstracts.Sprite;
import sprite.nomad.abstracts.Bomber;
import sprite.nomad.abstracts.Enemy;
import sprite.settled.Bomb;
import sprite.settled.ConclusionFlame;
import sprite.settled.Flame;
import sprite.settled.abstracts.TimedSettled;
import utils.Direction;

import java.util.LinkedList;

import static map.ctrl.NomadMethods.isNomadBurning;
import static sprite.ctrl.NomadMethods.isNomadCrossingEnemy;
import static sprite.nomad.abstracts.Enemy.Action.ACTION_WALKING;

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
        if (bomber.isFinished()) {
            bomber.init();
        } else if (bomber.getCurAction() != Bomber.Action.ACTION_DYING) { // not ended and not dead.

            // should the bomber die?
            if (!bomber.isInvincible() &&
                    (isNomadBurning(mapPointMatrix, bomber.getXMap(), bomber.getYMap()) ||
                            isNomadCrossingEnemy(list, bomber.getXMap(), bomber.getYMap(), bomber.getUid()))) {
                bomber.setCurAction(Bomber.Action.ACTION_DYING);
            }
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
        if (enemy.isFinished()) {
            shouldBeRemoved = true;
        } else if (enemy.getCurAction() != Enemy.Action.ACTION_DYING) { // not ended and not dying.

            // should the enemy die?
            if (isNomadBurning(mapPointMatrix, enemy.getXMap(), enemy.getYMap())) {
                enemy.setCurAction(Enemy.Action.ACTION_DYING);

            } else if (enemy.isTimeToMove()) { // not dead -> should the enemy move?
                try {
                    // compute the next direction.
                    Direction newDirection = EnemyAi.computeNextDirection(mapPointMatrix, mapWidth, mapHeight, list,
                            enemy.getCurDirection(), enemy.getXMap(), enemy.getYMap(), enemy.getUid());

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
     * - Notice that the flame must be removed from the list (if the sprite is ended),
     * -- AND add a conclusion flame.
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

            // create conclusion flames.
            AddingMethods.addConclusionFlame(tmpList, mapPointMatrix, flame.getRowIdx(), flame.getColIdx());
            mapPointMatrix[flame.getRowIdx()][flame.getColIdx()].removeFlame();
            shouldBeRemoved = true;
        }
        return shouldBeRemoved;
    }

    /**
     * - Notice that the conclusion flame must be removed from the list (if the sprite is ended),
     * - OR do nothing.
     *
     * @param conclusionFlame the conclusion flame
     * @return true if the conclusion flame should be removed from the list, false otherwise.
     */
    public static boolean processConclusionFlame(ConclusionFlame conclusionFlame) {
        boolean shouldBeRemoved = false;
        if (conclusionFlame.isFinished()) {
            shouldBeRemoved = true;
        }
        return shouldBeRemoved;
    }
}
