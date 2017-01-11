package ai;

import static map.ctrl.NomadMethods.isNomadBurning;
import static map.ctrl.NomadMethods.isNomadCrossingBomb;
import static map.ctrl.NomadMethods.isNomadCrossingMapLimit;
import static map.ctrl.NomadMethods.isNomadCrossingMutable;
import static map.ctrl.NomadMethods.isNomadCrossingObstacle;
import static sprite.ctrl.NomadMethods.isNomadCrossingEnemy;
import static utils.Action.ACTION_BREAKING;
import static utils.Tools.getCharBottomRowIdx;
import static utils.Tools.getCharLeftColIdx;
import static utils.Tools.getCharRightColIdx;
import static utils.Tools.getCharTopRowIdx;

import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import map.MapPoint;
import sprite.Sprite;
import sprite.nomad.BreakingEnemy;
import sprite.nomad.Nomad;
import utils.Direction;
import utils.Tools;

public class EnemyAi {

    /**
     * Compute the next direction of an nomad.
     *
     * @param mapPointMatrix the map (represented by its matrix of MapPoint)
     * @param mapWidth       the map width
     * @param mapHeight      the map height
     * @param spriteList     the list of nomads
     * @param nomad          the nomad
     * @return the computed direction if possible, null otherwise (when the nomad is blocked off)
     */
    public static Direction computeNextDirection(
            MapPoint[][] mapPointMatrix,
            int mapWidth,
            int mapHeight,
            List<Sprite> spriteList,
            Nomad nomad) {

        // create a set of checked directions.
        Set<Direction> checkedDirections = new HashSet<>();

        // if a (current) direction is set, firstly try to continue on that way, randomly get one otherwise.
        Direction curCheckedDirection = nomad.getCurDirection() != null ?
                nomad.getCurDirection() : Direction.getRandomDirectionWithExclusion(checkedDirections);

        // loop while a direction is not found and random directions are still provided.
        boolean resultFound = false;
        while (!resultFound && curCheckedDirection != null) {
            checkedDirections.add(curCheckedDirection); // add the current direction to the set of checked direction.
            switch (curCheckedDirection) {
                case NORTH: {
                    if (!isNomadCrossingMapLimit(mapWidth, mapHeight, nomad.getxMap(), nomad.getyMap() - 1) &&
                            !isNomadCrossingObstacle(mapPointMatrix, nomad.getxMap(), nomad.getyMap() - 1) &&
                            !isNomadBurning(mapPointMatrix, nomad.getxMap(), nomad.getyMap() - 1) &&
                            !isNomadCrossingBomb(mapPointMatrix, nomad.getxMap(), nomad.getyMap() - 1, KeyEvent.VK_UP) &&
                            !isNomadCrossingEnemy(spriteList, nomad.getxMap(), nomad.getyMap() - 1, nomad)) {
                        resultFound = true;
                    }
                    break;
                }
                case SOUTH: {
                    if (!isNomadCrossingMapLimit(mapWidth, mapHeight, nomad.getxMap(), nomad.getyMap() + 1) &&
                            !isNomadCrossingObstacle(mapPointMatrix, nomad.getxMap(), nomad.getyMap() + 1) &&
                            !isNomadBurning(mapPointMatrix, nomad.getxMap(), nomad.getyMap() + 1) &&
                            !isNomadCrossingBomb(mapPointMatrix, nomad.getxMap(), nomad.getyMap() + 1, KeyEvent.VK_DOWN) &&
                            !isNomadCrossingEnemy(spriteList, nomad.getxMap(), nomad.getyMap() + 1, nomad)) {
                        resultFound = true;
                    }
                    break;
                }
                case WEST: {
                    if (!isNomadCrossingMapLimit(mapWidth, mapHeight, nomad.getxMap() - 1, nomad.getyMap()) &&
                            !isNomadCrossingObstacle(mapPointMatrix, nomad.getxMap() - 1, nomad.getyMap()) &&
                            !isNomadBurning(mapPointMatrix, nomad.getxMap() - 1, nomad.getyMap()) &&
                            !isNomadCrossingBomb(mapPointMatrix, nomad.getxMap() - 1, nomad.getyMap(), KeyEvent.VK_LEFT) &&
                            !isNomadCrossingEnemy(spriteList, nomad.getxMap() - 1, nomad.getyMap(), nomad)) {
                        resultFound = true;
                    }
                    break;
                }
                case EAST: {
                    if (!isNomadCrossingMapLimit(mapWidth, mapHeight, nomad.getxMap() + 1, nomad.getyMap()) &&
                            !isNomadCrossingObstacle(mapPointMatrix, nomad.getxMap() + 1, nomad.getyMap()) &&
                            !isNomadBurning(mapPointMatrix, nomad.getxMap() + 1, nomad.getyMap()) &&
                            !isNomadCrossingBomb(mapPointMatrix, nomad.getxMap() + 1, nomad.getyMap(), KeyEvent.VK_RIGHT) &&
                            !isNomadCrossingEnemy(spriteList, nomad.getxMap() + 1, nomad.getyMap(), nomad)) {
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

    /**
     * Is there a mutable blocking an enemy.
     * 
     * @param mapPointMatrix mapPointMatrix the map (represented by its matrix of MapPoint)
     * @param mapWidth the map width
     * @param mapHeight the map height
     * @param breakingEnemy the enemy
     * @return the MapPoint to break if there is any, false otherwise.
     */
    public static MapPoint isThereMutableBlockingEnemy(MapPoint[][] mapPointMatrix,
            int mapWidth,
            int mapHeight,
            BreakingEnemy breakingEnemy) {

        MapPoint curCheckedMapPoint = null;
        switch (breakingEnemy.getCurDirection()) {
        case NORTH: {
            if (!isNomadCrossingMapLimit(mapWidth, mapHeight, breakingEnemy.getxMap(), breakingEnemy.getyMap() - 1) &&
                    isNomadCrossingMutable(mapPointMatrix, breakingEnemy.getxMap(), breakingEnemy.getyMap() - 1)) {
                curCheckedMapPoint = mapPointMatrix[getCharTopRowIdx(breakingEnemy.getyMap() - 1)][Tools
                        .getCharColIdx(breakingEnemy.getxMap())];
            }
            break;
        }
        case SOUTH: {
            if (!isNomadCrossingMapLimit(mapWidth, mapHeight, breakingEnemy.getxMap(), breakingEnemy.getyMap() + 1) &&
                    isNomadCrossingMutable(mapPointMatrix, breakingEnemy.getxMap(), breakingEnemy.getyMap() + 1)) {
                curCheckedMapPoint = mapPointMatrix[getCharBottomRowIdx(breakingEnemy.getyMap() + 1)][Tools
                        .getCharColIdx(breakingEnemy.getxMap())];

            }
            break;
        }
        case WEST: {
            if (!isNomadCrossingMapLimit(mapWidth, mapHeight, breakingEnemy.getxMap() - 1, breakingEnemy.getyMap()) &&
                    isNomadCrossingMutable(mapPointMatrix, breakingEnemy.getxMap() - 1, breakingEnemy.getyMap())) {
                curCheckedMapPoint = mapPointMatrix[Tools.getCharRowIdx(breakingEnemy.getyMap())][getCharLeftColIdx(
                        breakingEnemy.getxMap() - 1)];
            }
            break;
        }
        case EAST: {
            if (!isNomadCrossingMapLimit(mapWidth, mapHeight, breakingEnemy.getxMap() + 1, breakingEnemy.getyMap()) &&
                    isNomadCrossingMutable(mapPointMatrix, breakingEnemy.getxMap() + 1, breakingEnemy.getyMap())) {
                curCheckedMapPoint = mapPointMatrix[Tools.getCharRowIdx(breakingEnemy.getyMap())][getCharRightColIdx(
                        breakingEnemy.getxMap() + 1)];
            }
            break;
        }
        }
        if (curCheckedMapPoint != null) {
            // if a mutable must be broken.
            breakingEnemy.setCurAction(ACTION_BREAKING);
        }
        return curCheckedMapPoint;
    }
}
