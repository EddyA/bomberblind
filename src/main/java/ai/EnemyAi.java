package ai;

import map.MapPoint;
import sprite.Sprite;
import sprite.nomad.Nomad;
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
     * @return the computed direction if possible, null otherwise (when the enemy is blocked off)
     */
    public static Direction computeNextDirection(
            MapPoint[][] mapPointMatrix,
            int mapWidth,
            int mapHeight,
            List<Sprite> spriteList,
            Nomad enemy) {

        // create a set of checked directions.
        Set<Direction> checkedDirections = new HashSet<>();

        // if a (current) direction is set, firstly try to continue on that way, randomly get one otherwise.
        Direction curCheckedDirection = enemy.getCurDirection() != null ?
                enemy.getCurDirection() : Direction.getRandomDirectionWithExclusion(checkedDirections);

        // loop while a direction is not found and random directions are still provided.
        boolean resultFound = false;
        while (!resultFound && curCheckedDirection != null) {
            checkedDirections.add(curCheckedDirection); // add the current direction to the set of checked direction.
            switch (curCheckedDirection) {
                case NORTH: {
                    if (!isNomadCrossingMapLimit(mapWidth, mapHeight, enemy.getxMap(), enemy.getyMap() - 1) &&
                            !isNomadCrossingObstacle(mapPointMatrix, enemy.getxMap(), enemy.getyMap() - 1) &&
                            !isNomadBurning(mapPointMatrix, enemy.getxMap(), enemy.getyMap() - 1) &&
                            !isNomadCrossingBomb(mapPointMatrix, enemy.getxMap(), enemy.getyMap() - 1, KeyEvent.VK_UP) &&
                            !isNomadCrossingEnemy(spriteList, enemy.getxMap(), enemy.getyMap() - 1, enemy)) {
                        resultFound = true;
                    }
                    break;
                }
                case SOUTH: {
                    if (!isNomadCrossingMapLimit(mapWidth, mapHeight, enemy.getxMap(), enemy.getyMap() + 1) &&
                            !isNomadCrossingObstacle(mapPointMatrix, enemy.getxMap(), enemy.getyMap() + 1) &&
                            !isNomadBurning(mapPointMatrix, enemy.getxMap(), enemy.getyMap() + 1) &&
                            !isNomadCrossingBomb(mapPointMatrix, enemy.getxMap(), enemy.getyMap() + 1, KeyEvent.VK_DOWN) &&
                            !isNomadCrossingEnemy(spriteList, enemy.getxMap(), enemy.getyMap() + 1, enemy)) {
                        resultFound = true;
                    }
                    break;
                }
                case WEST: {
                    if (!isNomadCrossingMapLimit(mapWidth, mapHeight, enemy.getxMap() - 1, enemy.getyMap()) &&
                            !isNomadCrossingObstacle(mapPointMatrix, enemy.getxMap() - 1, enemy.getyMap()) &&
                            !isNomadBurning(mapPointMatrix, enemy.getxMap() - 1, enemy.getyMap()) &&
                            !isNomadCrossingBomb(mapPointMatrix, enemy.getxMap() - 1, enemy.getyMap(), KeyEvent.VK_LEFT) &&
                            !isNomadCrossingEnemy(spriteList, enemy.getxMap() - 1, enemy.getyMap(), enemy)) {
                        resultFound = true;
                    }
                    break;
                }
                case EAST: {
                    if (!isNomadCrossingMapLimit(mapWidth, mapHeight, enemy.getxMap() + 1, enemy.getyMap()) &&
                            !isNomadCrossingObstacle(mapPointMatrix, enemy.getxMap() + 1, enemy.getyMap()) &&
                            !isNomadBurning(mapPointMatrix, enemy.getxMap() + 1, enemy.getyMap()) &&
                            !isNomadCrossingBomb(mapPointMatrix, enemy.getxMap() + 1, enemy.getyMap(), KeyEvent.VK_RIGHT) &&
                            !isNomadCrossingEnemy(spriteList, enemy.getxMap() + 1, enemy.getyMap(), enemy)) {
                        resultFound = true;
                    }
                    break;
                }
            }
            if (!resultFound) {
                // result not found, try another direction.
                curCheckedDirection = Direction.getRandomDirectionWithExclusion(checkedDirections);
            }
        }
        return curCheckedDirection;
    }
}
