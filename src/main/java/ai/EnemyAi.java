package ai;

import exceptions.CannotMoveNomadException;
import map.MapPoint;
import sprites.nomad.Enemy;
import sprites.nomad.Nomad;

import java.awt.event.KeyEvent;
import java.util.*;

import static map.ctrl.NomadMethods.*;
import static sprites.nomad.Enemy.status.*;
import static sprites.nomad.NomadCtrl.isNomadCrossingEnemy;

public class EnemyAi {

    private static List<Enemy.status> moveList = new ArrayList<>(
            Arrays.asList(STATUS_WALK_BACK, STATUS_WALK_FRONT, STATUS_WALK_LEFT, STATUS_WALK_RIGHT));

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
                                               List<Nomad> nomadList, Enemy enemy) throws CannotMoveNomadException {
        if (!moveList.contains(enemy.getStatus())) {
            throw new RuntimeException("the provided status is not valid.");
        }

        // create a set of checked status.
        Set<Enemy.status> checkedStatus = new HashSet<>();

        Enemy.status curCheckedStatus = enemy.getStatus();
        boolean resultFound = false;
        do {
            checkedStatus.add(curCheckedStatus); // add the current checked status to the set of checked status.
            switch (curCheckedStatus) {
                case STATUS_WALK_BACK: {
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
                case STATUS_WALK_FRONT: {
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
                case STATUS_WALK_LEFT: {
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
                case STATUS_WALK_RIGHT: {
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
                     * all the status has been checked but no result found.
                     * this case happens when the nomad is blocked by another one
                     * and it cannot move during this iteration ... just wait for the next one.
                     */
                    throw new CannotMoveNomadException("nomad is not able to move during this iteration.");
                }
                curCheckedStatus = getRandomDirection();
            }
        } while (!resultFound); // until a move is possible.
        return curCheckedStatus;
    }
}