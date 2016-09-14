package ai;

import exceptions.CannotMoveNomadException;
import map.MapPoint;
import sprites.nomad.abstracts.Enemy;
import sprites.nomad.abstracts.Nomad;

import java.awt.event.KeyEvent;
import java.util.*;

import static map.ctrl.NomadMethods.*;
import static sprites.nomad.abstracts.Enemy.status.*;
import static sprites.NomadCtrl.isNomadCrossingEnemy;

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
     * @return the updated curStatus
     */
    public static Enemy.status computeNextMove(MapPoint[][] mapPointMatrix, int mapWidth, int mapHeight,
                                               List<Nomad> nomadList, Enemy enemy) throws CannotMoveNomadException {
        if (!moveList.contains(enemy.getCurStatus())) {
            throw new RuntimeException("the provided curStatus is not valid.");
        }

        // create a set of checked curStatus.
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