package ai;

import map.MapPoint;
import map.ctrl.NomadMethods;
import sprites.nomad.abstracts.Enemy;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

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
     * Compute direction according to the map.
     *
     * @param mapPointMatrix the map (represented by its matrix of MapPoint)
     * @param mapWidth the map width
     * @param mapHeight the map height
     * @param xChar the enemy abscissa
     * @param yChar the enemy ordinate
     * @param status the enemy
     * @return the updated status
     */
    public static Enemy.status updateStatus(MapPoint[][] mapPointMatrix, int mapWidth, int mapHeight, int xChar,
                                            int yChar, Enemy.status status) {
        if (!moveList.contains(status)) {
            throw new RuntimeException("the provided status is not valid.");
        }
        Enemy.status checkedStatus = status;
        boolean resultFound = false;
        do {
            switch (checkedStatus) {
                case STATUS_WALK_BACK: {
                    if (!NomadMethods.isNomadCrossingMapLimit(mapWidth, mapHeight, xChar, yChar - 1)) {
                        if (!NomadMethods.isNomadCrossingObstacle(mapPointMatrix, xChar, yChar - 1) &&
                                !NomadMethods.isNomadCrossingBomb(mapPointMatrix, xChar, yChar - 1, KeyEvent.VK_UP)) {
                            resultFound = true;
                        }
                    }
                    break;
                }
                case STATUS_WALK_FRONT: {
                    if (!NomadMethods.isNomadCrossingMapLimit(mapWidth, mapHeight, xChar, yChar + 1)) {
                        if (!NomadMethods.isNomadCrossingObstacle(mapPointMatrix, xChar, yChar + 1) &&
                                !NomadMethods.isNomadCrossingBomb(mapPointMatrix, xChar, yChar + 1, KeyEvent.VK_DOWN)) {
                            resultFound = true;
                        }
                    }
                    break;
                }
                case STATUS_WALK_LEFT: {
                    if (!NomadMethods.isNomadCrossingMapLimit(mapWidth, mapHeight, xChar - 1, yChar)) {
                        if (!NomadMethods.isNomadCrossingObstacle(mapPointMatrix, xChar - 1, yChar) &&
                                !NomadMethods.isNomadCrossingBomb(mapPointMatrix, xChar - 1, yChar, KeyEvent.VK_LEFT)) {
                            resultFound = true;
                        }
                    }
                    break;
                }
                case STATUS_WALK_RIGHT: {
                    if (!NomadMethods.isNomadCrossingMapLimit(mapWidth, mapHeight, xChar + 1, yChar)) {
                        if (!NomadMethods.isNomadCrossingObstacle(mapPointMatrix, xChar + 1, yChar) &&
                                !NomadMethods.isNomadCrossingBomb(mapPointMatrix, xChar + 1, yChar, KeyEvent.VK_RIGHT)) {
                            resultFound = true;
                        }
                    }
                    break;
                }
            }
            if (!resultFound) {
                checkedStatus = getRandomDirection();
            }
        } while (!resultFound); // until a move is possible.
        return checkedStatus;
    }
}