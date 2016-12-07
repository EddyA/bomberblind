package ai;

import exceptions.CannotMoveEnemyException;
import map.MapPoint;
import sprite.abstracts.Sprite;
import sprite.nomad.abstracts.Enemy;
import utils.Direction;

import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static map.ctrl.NomadMethods.*;
import static sprite.ctrl.NomadMethods.isNomadCrossingEnemy;

public class EnemyAi {

    /**
     * Compute the next direction of an enemy.
     *
     * @param mapPointMatrix the map (represented by its matrix of MapPoint)
     * @param mapWidth       the map width
     * @param mapHeight      the map height
     * @param spriteList     the list of nomads
     * @param enemy          the enemy
     * @return the updated action
     */
    public static Direction computeNextDirection(MapPoint[][] mapPointMatrix, int mapWidth, int mapHeight,
                                                 List<Sprite> spriteList, Enemy enemy)
            throws CannotMoveEnemyException {

        // create a set of checked action.
        Set<Direction> checkedDirections = new HashSet<>();

        // if a (current) direction is set, firstly try to continue on that way, randomly get one otherwise.
        Direction curCheckedDirection = enemy.getCurDirection() != null ?
                enemy.getCurDirection() : Direction.getRandomDirection();

        boolean resultFound = false;
        do {
            checkedDirections.add(curCheckedDirection); // add the current direction to the set of checked direction.
            switch (curCheckedDirection) {
                case NORTH: {
                    if (!isNomadCrossingMapLimit(mapWidth, mapHeight, enemy.getXMap(), enemy.getYMap() - 1)) {
                        if (!isNomadCrossingObstacle(mapPointMatrix, enemy.getXMap(), enemy.getYMap() - 1) &&
                                !isNomadBurning(mapPointMatrix, enemy.getXMap(), enemy.getYMap() - 1) &&
                                !isNomadCrossingBomb(mapPointMatrix, enemy.getXMap(), enemy.getYMap() - 1, KeyEvent.VK_UP) &&
                                !isNomadCrossingEnemy(spriteList, enemy.getXMap(), enemy.getYMap() - 1, enemy)) {
                            resultFound = true;
                        }
                    }
                    break;
                }
                case SOUTH: {
                    if (!isNomadCrossingMapLimit(mapWidth, mapHeight, enemy.getXMap(), enemy.getYMap() + 1)) {
                        if (!isNomadCrossingObstacle(mapPointMatrix, enemy.getXMap(), enemy.getYMap() + 1) &&
                                !isNomadBurning(mapPointMatrix, enemy.getXMap(), enemy.getYMap() + 1) &&
                                !isNomadCrossingBomb(mapPointMatrix, enemy.getXMap(), enemy.getYMap() + 1, KeyEvent.VK_DOWN) &&
                                !isNomadCrossingEnemy(spriteList, enemy.getXMap(), enemy.getYMap() + 1, enemy)) {
                            resultFound = true;
                        }
                    }
                    break;
                }
                case WEST: {
                    if (!isNomadCrossingMapLimit(mapWidth, mapHeight, enemy.getXMap() - 1, enemy.getYMap())) {
                        if (!isNomadCrossingObstacle(mapPointMatrix, enemy.getXMap() - 1, enemy.getYMap()) &&
                                !isNomadBurning(mapPointMatrix, enemy.getXMap() - 1, enemy.getYMap()) &&
                                !isNomadCrossingBomb(mapPointMatrix, enemy.getXMap() - 1, enemy.getYMap(), KeyEvent.VK_LEFT) &&
                                !isNomadCrossingEnemy(spriteList, enemy.getXMap() - 1, enemy.getYMap(), enemy)) {
                            resultFound = true;
                        }
                    }
                    break;
                }
                case EAST: {
                    if (!isNomadCrossingMapLimit(mapWidth, mapHeight, enemy.getXMap() + 1, enemy.getYMap())) {
                        if (!isNomadCrossingObstacle(mapPointMatrix, enemy.getXMap() + 1, enemy.getYMap()) &&
                                !isNomadBurning(mapPointMatrix, enemy.getXMap() + 1, enemy.getYMap()) &&
                                !isNomadCrossingBomb(mapPointMatrix, enemy.getXMap() + 1, enemy.getYMap(), KeyEvent.VK_RIGHT) &&
                                !isNomadCrossingEnemy(spriteList, enemy.getXMap() + 1, enemy.getYMap(), enemy)) {
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
