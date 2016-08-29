package ai;

import exceptions.CannotMoveNomadException;
import map.MapPoint;
import map.ctrl.NomadMethods;
import sprites.nomad.NomadCtrl;
import sprites.nomad.abstracts.Enemy;
import sprites.nomad.abstracts.Nomad;

import java.awt.event.KeyEvent;
import java.util.*;

import static sprites.nomad.abstracts.Enemy.status.*;

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
                    if (!NomadMethods.isNomadCrossingMapLimit(mapWidth, mapHeight, enemy.getXMap(), enemy.getYMap() - 1)) {
                        if (!NomadMethods.isNomadCrossingObstacle(mapPointMatrix, enemy.getXMap(), enemy.getYMap() - 1) &&
                                !NomadMethods.isNomadBurning(mapPointMatrix, enemy.getXMap(), enemy.getYMap() - 1) &&
                                !NomadMethods.isNomadCrossingBomb(mapPointMatrix, enemy.getXMap(), enemy.getYMap() - 1, KeyEvent.VK_UP) &&
                                !NomadCtrl.isNomadCrossingAnotherNomad(nomadList, enemy, enemy.getXMap(), enemy.getYMap() - 1)) {
                            resultFound = true;
                        }
                    }
                    break;
                }
                case STATUS_WALK_FRONT: {
                    if (!NomadMethods.isNomadCrossingMapLimit(mapWidth, mapHeight, enemy.getXMap(), enemy.getYMap() + 1)) {
                        if (!NomadMethods.isNomadCrossingObstacle(mapPointMatrix, enemy.getXMap(), enemy.getYMap() + 1) &&
                                !NomadMethods.isNomadBurning(mapPointMatrix, enemy.getXMap(), enemy.getYMap() + 1) &&
                                !NomadMethods.isNomadCrossingBomb(mapPointMatrix, enemy.getXMap(), enemy.getYMap() + 1, KeyEvent.VK_DOWN) &&
                                !NomadCtrl.isNomadCrossingAnotherNomad(nomadList, enemy, enemy.getXMap(), enemy.getYMap() + 1)) {
                            resultFound = true;
                        }
                    }
                    break;
                }
                case STATUS_WALK_LEFT: {
                    if (!NomadMethods.isNomadCrossingMapLimit(mapWidth, mapHeight, enemy.getXMap() - 1, enemy.getYMap())) {
                        if (!NomadMethods.isNomadCrossingObstacle(mapPointMatrix, enemy.getXMap() - 1, enemy.getYMap()) &&
                                !NomadMethods.isNomadBurning(mapPointMatrix, enemy.getXMap() - 1, enemy.getYMap()) &&
                                !NomadMethods.isNomadCrossingBomb(mapPointMatrix, enemy.getXMap() - 1, enemy.getYMap(), KeyEvent.VK_LEFT) &&
                                !NomadCtrl.isNomadCrossingAnotherNomad(nomadList, enemy, enemy.getXMap() - 1, enemy.getYMap())) {
                            resultFound = true;
                        }
                    }
                    break;
                }
                case STATUS_WALK_RIGHT: {
                    if (!NomadMethods.isNomadCrossingMapLimit(mapWidth, mapHeight, enemy.getXMap() + 1, enemy.getYMap())) {
                        if (!NomadMethods.isNomadCrossingObstacle(mapPointMatrix, enemy.getXMap() + 1, enemy.getYMap()) &&
                                !NomadMethods.isNomadBurning(mapPointMatrix, enemy.getXMap() + 1, enemy.getYMap()) &&
                                !NomadMethods.isNomadCrossingBomb(mapPointMatrix, enemy.getXMap() + 1, enemy.getYMap(), KeyEvent.VK_RIGHT) &&
                                !NomadCtrl.isNomadCrossingAnotherNomad(nomadList, enemy, enemy.getXMap() + 1, enemy.getYMap())) {
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