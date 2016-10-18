package spriteList.ctrl;

import static map.ctrl.NomadMethods.isNomadBurning;
import static sprite.ctrl.NomadMethods.isNomadCrossingEnemy;
import static sprite.nomad.abstracts.Enemy.Action.ACTION_WALKING;

import java.util.LinkedList;

import ai.EnemyAi;
import exceptions.CannotMoveNomadException;
import map.MapPoint;
import sprite.abstracts.Sprite;
import sprite.nomad.abstracts.Bomber;
import sprite.nomad.abstracts.Enemy;
import sprite.settled.Bomb;
import sprite.settled.ConclusionFlame;
import sprite.settled.Flame;
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
     * - Re-init the bomber (if the bomber is dead and the sprite finished),
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
        } else if (bomber.getCurAction() != Bomber.Action.STATUS_DYING) { // not finished and not dead.

            // should the bomber die?
            if (!bomber.isInvincible() &&
                    (isNomadBurning(mapPointMatrix, bomber.getXMap(), bomber.getYMap()) ||
                            isNomadCrossingEnemy(list, bomber.getXMap(), bomber.getYMap(), bomber.getUid()))) {
                bomber.setCurAction(Bomber.Action.STATUS_DYING);
            }
        }
        return false;
    }

    /**
     * - Check if the enemy must be removed from the list (if the enemy is dead and the sprite finished),
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
    public static boolean processEnemyA(LinkedList<Sprite> list, MapPoint[][] mapPointMatrix, int mapWidth,
                                        int mapHeight, Enemy enemy) {
        boolean shouldBeRemoved = false;
        if (enemy.isFinished()) {
            shouldBeRemoved = true;
        } else if (enemy.getCurAction() != Enemy.Action.ACTION_DYING) { // not finished and not dying.

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
                } catch (CannotMoveNomadException e) {
                    // nothing to do, just wait for the next iteration.
                }
            }
        }
        return shouldBeRemoved;
    }

    /**
     * - Check if the bomb must be removed from the list (if the sprite is finished),
     * - OR kill the bomb (if it is time or the bomb is on a burning case)
     * AND add flames.
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
        if (bomb.isFinished() || // is it finished?
                mapPointMatrix[bomb.getRowIdx()][bomb.getColIdx()].isBurning()) { // OR is it on a burning case?

            // create flames.
            AddingMethods.addFlames(tmpList, mapPointMatrix, mapWidth, mapHeight, bomb.getRowIdx(), bomb.getColIdx(),
                    bomb.getFlameSize());
            mapPointMatrix[bomb.getRowIdx()][bomb.getColIdx()].setBombing(false);
            shouldBeRemoved = true;
        }
        return shouldBeRemoved;
    }

    /**
     * - Check if the flame must be removed from the list (if the sprite is finished)
     * AND add a conclusion flame,
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
     * - Check if the conclusion flame must be removed from the list (if the sprite is finished),
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
