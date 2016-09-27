package ai;

import static map.ctrl.NomadMethods.isNomadBurning;
import static map.ctrl.NomadMethods.isNomadCrossingBomb;
import static map.ctrl.NomadMethods.isNomadCrossingMapLimit;
import static map.ctrl.NomadMethods.isNomadCrossingObstacle;
import static sprite.ctrl.NomadMethods.isNomadCrossingEnemy;
import static sprite.nomad.abstracts.Enemy.status.STATUS_WALKING_BACK;
import static sprite.nomad.abstracts.Enemy.status.STATUS_WALKING_FRONT;
import static sprite.nomad.abstracts.Enemy.status.STATUS_WALKING_LEFT;
import static sprite.nomad.abstracts.Enemy.status.STATUS_WALKING_RIGHT;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import exceptions.CannotMoveNomadException;
import map.MapPoint;
import sprite.abstracts.Sprite;
import sprite.nomad.abstracts.Enemy;

public class EnemyAi {

    private static List<Enemy.status> moveList = new ArrayList<>(
            Arrays.asList(STATUS_WALKING_BACK, STATUS_WALKING_FRONT, STATUS_WALKING_LEFT, STATUS_WALKING_RIGHT));

    /**
     * @return a random move from the list of available moves.
     */
    private static Enemy.status getRandomDirection() {
        return moveList.get(new Random().nextInt(moveList.size()));
    }

    /**
     * Compute the next move.
     *
     * @param mapPointMatrix the map (represented by its matrix of MapPoint)
     * @param mapWidth       the map width
     * @param mapHeight      the map height
     * @param nomadList      the list of nomads
     * @param enemy          the enemy to move
     * @return the updated status
     */
    public static Enemy.status computeNextMove(MapPoint[][] mapPointMatrix, int mapWidth, int mapHeight,
            List<Sprite> nomadList, Enemy enemy) throws CannotMoveNomadException {
        if (!moveList.contains(enemy.getCurStatus())) {
            throw new RuntimeException("the provided curStatus is not valid.");
        }

        // create a set of checked status.
        Set<Enemy.status> checkedStatus = new HashSet<>();

        Enemy.status curCheckedStatus = enemy.getCurStatus();
        boolean resultFound = false;
        do {
            checkedStatus.add(curCheckedStatus); // add the current checked curStatus to the set of checked curStatus.
            switch (curCheckedStatus) {
                case STATUS_WALKING_BACK: {
                    if (!isNomadCrossingMapLimit(mapWidth, mapHeight, enemy.getXMap(), enemy.getYMap() - 1)) {
                        if (!isNomadCrossingObstacle(mapPointMatrix, enemy.getXMap(), enemy.getYMap() - 1) &&
                                !isNomadBurning(mapPointMatrix, enemy.getXMap(), enemy.getYMap() - 1) &&
                                !isNomadCrossingBomb(mapPointMatrix, enemy.getXMap(), enemy.getYMap() - 1, KeyEvent.VK_UP) &&
                                !isNomadCrossingEnemy(nomadList, enemy, enemy.getXMap(), enemy.getYMap() - 1)) {
                            resultFound = true;
                        }
                    }
                    break;
                }
                case STATUS_WALKING_FRONT: {
                    if (!isNomadCrossingMapLimit(mapWidth, mapHeight, enemy.getXMap(), enemy.getYMap() + 1)) {
                        if (!isNomadCrossingObstacle(mapPointMatrix, enemy.getXMap(), enemy.getYMap() + 1) &&
                                !isNomadBurning(mapPointMatrix, enemy.getXMap(), enemy.getYMap() + 1) &&
                                !isNomadCrossingBomb(mapPointMatrix, enemy.getXMap(), enemy.getYMap() + 1, KeyEvent.VK_DOWN) &&
                                !isNomadCrossingEnemy(nomadList, enemy, enemy.getXMap(), enemy.getYMap() + 1)) {
                            resultFound = true;
                        }
                    }
                    break;
                }
                case STATUS_WALKING_LEFT: {
                    if (!isNomadCrossingMapLimit(mapWidth, mapHeight, enemy.getXMap() - 1, enemy.getYMap())) {
                        if (!isNomadCrossingObstacle(mapPointMatrix, enemy.getXMap() - 1, enemy.getYMap()) &&
                                !isNomadBurning(mapPointMatrix, enemy.getXMap() - 1, enemy.getYMap()) &&
                                !isNomadCrossingBomb(mapPointMatrix, enemy.getXMap() - 1, enemy.getYMap(), KeyEvent.VK_LEFT) &&
                                !isNomadCrossingEnemy(nomadList, enemy, enemy.getXMap() - 1, enemy.getYMap())) {
                            resultFound = true;
                        }
                    }
                    break;
                }
                case STATUS_WALKING_RIGHT: {
                    if (!isNomadCrossingMapLimit(mapWidth, mapHeight, enemy.getXMap() + 1, enemy.getYMap())) {
                        if (!isNomadCrossingObstacle(mapPointMatrix, enemy.getXMap() + 1, enemy.getYMap()) &&
                                !isNomadBurning(mapPointMatrix, enemy.getXMap() + 1, enemy.getYMap()) &&
                                !isNomadCrossingBomb(mapPointMatrix, enemy.getXMap() + 1, enemy.getYMap(), KeyEvent.VK_RIGHT) &&
                                !isNomadCrossingEnemy(nomadList, enemy, enemy.getXMap() + 1, enemy.getYMap())) {
                            resultFound = true;
                        }
                    }
                    break;
                }
            }
            if (!resultFound) {
                if (checkedStatus.size() == moveList.size()) {

                    /**
                     * all the curStatus has been checked but no result found.
                     * this case happens when the abstracts is blocked by another one
                     * and it cannot move during this iteration ... just wait for the next one.
                     */
                    throw new CannotMoveNomadException("abstracts is not able to move during this iteration.");
                }
                curCheckedStatus = getRandomDirection();
            }
        } while (!resultFound); // until a move is possible.
        return curCheckedStatus;
    }
}