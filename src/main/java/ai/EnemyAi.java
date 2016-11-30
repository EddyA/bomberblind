package ai;

import static map.ctrl.NomadMethods.isNomadBurning;
import static map.ctrl.NomadMethods.isNomadCrossingBomb;
import static map.ctrl.NomadMethods.isNomadCrossingMapLimit;
import static map.ctrl.NomadMethods.isNomadCrossingObstacle;
import static sprite.ctrl.NomadMethods.isNomadCrossingEnemy;

import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import exceptions.CannotMoveEnemyException;
import map.MapPoint;
import sprite.abstracts.Sprite;
import utils.Direction;

public class EnemyAi {

    /**
     * Compute the next direction of an enemy.
     *
     * @param mapPointMatrix the map (represented by its matrix of MapPoint)
     * @param mapWidth the map width
     * @param mapHeight the map height
     * @param spriteList the list of nomads
     * @param curDirection the enemy's direction
     * @param xMap the enemy's abscissa
     * @param yMap the enemy's ordinate
     * @param uid the enemy's uid
     * @return the updated action
     */
    public static Direction computeNextDirection(MapPoint[][] mapPointMatrix, int mapWidth, int mapHeight,
            List<Sprite> spriteList, Direction curDirection, int xMap, int yMap, int uid)
            throws CannotMoveEnemyException {

        // create a set of checked action.
        Set<Direction> checkedDirections = new HashSet<>();

        // if a (current) direction is set, firstly try to continue on that way, randomly get one otherwise.
        Direction curCheckedDirection = curDirection != null ? curDirection : Direction.getRandomDirection();

        boolean resultFound = false;
        do {
            checkedDirections.add(curCheckedDirection); // add the current direction to the set of checked direction.
            switch (curCheckedDirection) {
            case NORTH: {
                if (!isNomadCrossingMapLimit(mapWidth, mapHeight, xMap, yMap - 1)) {
                    if (!isNomadCrossingObstacle(mapPointMatrix, xMap, yMap - 1) &&
                            !isNomadBurning(mapPointMatrix, xMap, yMap - 1) &&
                            !isNomadCrossingBomb(mapPointMatrix, xMap, yMap - 1, KeyEvent.VK_UP) &&
                            !isNomadCrossingEnemy(spriteList, xMap, yMap - 1, uid)) {
                        resultFound = true;
                    }
                }
                break;
            }
            case SOUTH: {
                if (!isNomadCrossingMapLimit(mapWidth, mapHeight, xMap, yMap + 1)) {
                    if (!isNomadCrossingObstacle(mapPointMatrix, xMap, yMap + 1) &&
                            !isNomadBurning(mapPointMatrix, xMap, yMap + 1) &&
                            !isNomadCrossingBomb(mapPointMatrix, xMap, yMap + 1, KeyEvent.VK_DOWN) &&
                            !isNomadCrossingEnemy(spriteList, xMap, yMap + 1, uid)) {
                        resultFound = true;
                    }
                }
                break;
            }
            case WEST: {
                if (!isNomadCrossingMapLimit(mapWidth, mapHeight, xMap - 1, yMap)) {
                    if (!isNomadCrossingObstacle(mapPointMatrix, xMap - 1, yMap) &&
                            !isNomadBurning(mapPointMatrix, xMap - 1, yMap) &&
                            !isNomadCrossingBomb(mapPointMatrix, xMap - 1, yMap, KeyEvent.VK_LEFT) &&
                            !isNomadCrossingEnemy(spriteList, xMap - 1, yMap, uid)) {
                        resultFound = true;
                    }
                }
                break;
            }
            case EAST: {
                if (!isNomadCrossingMapLimit(mapWidth, mapHeight, xMap + 1, yMap)) {
                    if (!isNomadCrossingObstacle(mapPointMatrix, xMap + 1, yMap) &&
                            !isNomadBurning(mapPointMatrix, xMap + 1, yMap) &&
                            !isNomadCrossingBomb(mapPointMatrix, xMap + 1, yMap, KeyEvent.VK_RIGHT) &&
                            !isNomadCrossingEnemy(spriteList, xMap + 1, yMap, uid)) {
                        resultFound = true;
                    }
                }
                break;
            }
            }
            if (!resultFound) {
                if (checkedDirections.size() == Direction.getNbDirections()) {

                    /*
                      all the 'curCheckedDirection' has been checked but no result found.
                      this case happens when the abstracts is blocked by another one
                      and it cannot move during this iteration ... just wait for the next one.
                     */
                    throw new CannotMoveEnemyException("abstracts is not able to move during this iteration.");
                }
                curCheckedDirection = Direction.getRandomDirection();
            }
        } while (!resultFound); // until a move is possible.
        return curCheckedDirection;
    }
}
