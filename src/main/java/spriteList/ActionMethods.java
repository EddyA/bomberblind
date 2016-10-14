package spriteList;

import static map.ctrl.NomadMethods.isNomadBurning;
import static sprite.ctrl.NomadMethods.isNomadCrossingEnemy;
import static sprite.nomad.abstracts.EnemyA.status.STATUS_WALKING_BACK;
import static sprite.nomad.abstracts.EnemyA.status.STATUS_WALKING_FRONT;
import static sprite.nomad.abstracts.EnemyA.status.STATUS_WALKING_LEFT;
import static sprite.nomad.abstracts.EnemyA.status.STATUS_WALKING_RIGHT;

import java.util.LinkedList;

import ai.EnemyAI;
import exceptions.CannotMoveNomadException;
import map.MapPoint;
import sprite.abstracts.Sprite;
import sprite.nomad.abstracts.Bomber;
import sprite.nomad.abstracts.EnemyA;
import sprite.settled.Bomb;
import sprite.settled.ConclusionFlame;
import sprite.settled.Flame;
import utils.Direction;

/**
 * Define a collection of methods to process sprites.
 * These methods can:
 * - update the sprite's status (e.g. change the enemy direction),
 * - notify if the sprite should be remove from the list of sprites (e.g. the enemy is dead),
 * - add other sprites according to the sprite's status (e.g. add flames if the bomb is exploding).
 */
public class ActionMethods {

    /**
     * - Re-init the bomber (if the bomber is dead and the dead sprite finished),
     * - OR kill the bomber (if it is on a burning case or crossing an enemy),
     * - OR do nothing.
     *
     * @param list the list of sprites
     * @param mapPointMatrix mapPointMatrix the map (represented by its matrix of MapPoint)
     * @param bomber the bomber
     * @return true if the bomber should be removed from the list, false otherwise.
     */
    public static boolean processBomber(LinkedList<Sprite> list, MapPoint[][] mapPointMatrix, Bomber bomber) {
        if (bomber.isFinished()) {
            bomber.init();
        } else if (bomber.getCurStatus() != Bomber.status.STATUS_DYING) { // not finished and not dead.

            // should the bomber die?
            if (!bomber.isInvincible() &&
                    (isNomadBurning(mapPointMatrix, bomber.getXMap(), bomber.getYMap()) ||
                            isNomadCrossingEnemy(list, bomber.getXMap(), bomber.getYMap(), bomber.getUid()))) {
                bomber.setCurStatus(Bomber.status.STATUS_DYING);
            }
        }
        return false;
    }

    /**
     * - Notify that the enemyA must be removed from the list (if the bomber is dead and the dead sprite finished),
     * - OR kill the enemyA (if it is on burning case),
     * - OR compute the next direction.
     *
     * @param list the list of sprites
     * @param mapPointMatrix mapPointMatrix the map (represented by its matrix of MapPoint)
     * @param mapWidth the map width
     * @param mapHeight the map height
     * @param enemy the enemy
     * @return true if the enemy should be removed from the list, false otherwise.
     */
    public static boolean processEnemyA(LinkedList<Sprite> list, MapPoint[][] mapPointMatrix, int mapWidth,
            int mapHeight, EnemyA enemy) {

        boolean shouldBeRemoved = false;
        if (enemy.isFinished()) {
            shouldBeRemoved = true;
        } else if (enemy.getCurStatus() != EnemyA.status.STATUS_DYING) { // not finished and not dying.

            // should the enemy die?
            if (isNomadBurning(mapPointMatrix, enemy.getXMap(), enemy.getYMap())) {
                enemy.setCurStatus(EnemyA.status.STATUS_DYING);

            } else if (enemy.isTimeToMove()) { // not dead -> should the enemy move?
                try {
                    Direction newDirection = EnemyAI.computeNextDirection(mapPointMatrix,
                            mapWidth, mapHeight, list, enemy.getCurDirection().orElse(null),
                            enemy.getXMap(), enemy.getYMap(), enemy.getUid()); // try to compute the next move.
                    switch (newDirection) {
                    case NORTH: {
                        enemy.setYMap(enemy.getYMap() - 1);
                        enemy.setCurStatus(STATUS_WALKING_BACK);
                        break;
                    }
                    case SOUTH: {
                        enemy.setYMap(enemy.getYMap() + 1);
                        enemy.setCurStatus(STATUS_WALKING_FRONT);
                        break;
                    }
                    case WEST: {
                        enemy.setXMap(enemy.getXMap() - 1);
                        enemy.setCurStatus(STATUS_WALKING_LEFT);
                        break;
                    }
                    case EAST: {
                        enemy.setXMap(enemy.getXMap() + 1);
                        enemy.setCurStatus(STATUS_WALKING_RIGHT);
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
     * - Notify that the bomb must be removed from the list (if the sprite is finished),
     * - OR kill the bomb (if it is time or it is on burning case)
     * AND add flames.
     * - OR do nothing.
     *
     * @param tmpList the temporary list of sprites to add new elements
     * @param mapPointMatrix mapPointMatrix the map (represented by its matrix of MapPoint)
     * @param mapWidth the map width
     * @param mapHeight the map height
     * @param bomb the bomb
     * @return true if the enemy should be removed from the list, false otherwise.
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
     * - Notify that the flame must be removed from the list (if the sprite is finished)
     * AND add a conclusion flame,
     * - OR do nothing.
     *
     * @param tmpList the temporary list of sprites to add new elements
     * @param mapPointMatrix mapPointMatrix the map (represented by its matrix of MapPoint)
     * @param flame the flame
     * @return true if the bomber should be removed from the list, false otherwise.
     */
    public static boolean processFlame(LinkedList<Sprite> tmpList, MapPoint[][] mapPointMatrix, Flame flame) {
        if (flame.isFinished()) {

            // create conclusion flames.
            AddingMethods.addConclusionFlame(tmpList, mapPointMatrix, flame.getRowIdx(), flame.getColIdx());
            mapPointMatrix[flame.getRowIdx()][flame.getColIdx()].removeFlame();
        }
        return false;
    }

    /**
     * - Notify that the conclusion flame must be removed from the list (if the sprite is finished),
     * - OR do nothing.
     *
     * @param conclusionFlame the conclusion flame
     * @return true if the bomber should be removed from the list, false otherwise.
     */
    public static boolean processConclusionFlame(ConclusionFlame conclusionFlame) {
        boolean shouldBeRemoved = false;
        if (conclusionFlame.isFinished()) {
            shouldBeRemoved = true;
        }
        return shouldBeRemoved;
    }

}
