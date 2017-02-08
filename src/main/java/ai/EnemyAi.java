package ai;

import map.MapPoint;
import sprite.Sprite;
import sprite.nomad.Nomad;
import utils.Direction;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static map.ctrl.NomadMethods.*;
import static sprite.ctrl.NomadMethods.isNomadCrossingEnemy;
import static utils.Direction.*;

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
                case DIRECTION_NORTH: {
                    if (!isNomadCrossingMapLimit(mapWidth, mapHeight, nomad.getxMap(), nomad.getyMap() - 1) &&
                            !isNomadCrossingObstacle(mapPointMatrix, nomad.getxMap(), nomad.getyMap() - 1) &&
                            !isNomadBurning(mapPointMatrix, nomad.getxMap(), nomad.getyMap() - 1) &&
                            !isNomadCrossingBomb(mapPointMatrix, nomad.getxMap(), nomad.getyMap() - 1, DIRECTION_NORTH) &&
                            !isNomadCrossingEnemy(spriteList, nomad.getxMap(), nomad.getyMap() - 1, nomad)) {
                        resultFound = true;
                    }
                    break;
                }
                case DIRECTION_SOUTH: {
                    if (!isNomadCrossingMapLimit(mapWidth, mapHeight, nomad.getxMap(), nomad.getyMap() + 1) &&
                            !isNomadCrossingObstacle(mapPointMatrix, nomad.getxMap(), nomad.getyMap() + 1) &&
                            !isNomadBurning(mapPointMatrix, nomad.getxMap(), nomad.getyMap() + 1) &&
                            !isNomadCrossingBomb(mapPointMatrix, nomad.getxMap(), nomad.getyMap() + 1, DIRECTION_SOUTH) &&
                            !isNomadCrossingEnemy(spriteList, nomad.getxMap(), nomad.getyMap() + 1, nomad)) {
                        resultFound = true;
                    }
                    break;
                }
                case DIRECTION_WEST: {
                    if (!isNomadCrossingMapLimit(mapWidth, mapHeight, nomad.getxMap() - 1, nomad.getyMap()) &&
                            !isNomadCrossingObstacle(mapPointMatrix, nomad.getxMap() - 1, nomad.getyMap()) &&
                            !isNomadBurning(mapPointMatrix, nomad.getxMap() - 1, nomad.getyMap()) &&
                            !isNomadCrossingBomb(mapPointMatrix, nomad.getxMap() - 1, nomad.getyMap(), DIRECTION_WEST) &&
                            !isNomadCrossingEnemy(spriteList, nomad.getxMap() - 1, nomad.getyMap(), nomad)) {
                        resultFound = true;
                    }
                    break;
                }
                case DIRECTION_EAST: {
                    if (!isNomadCrossingMapLimit(mapWidth, mapHeight, nomad.getxMap() + 1, nomad.getyMap()) &&
                            !isNomadCrossingObstacle(mapPointMatrix, nomad.getxMap() + 1, nomad.getyMap()) &&
                            !isNomadBurning(mapPointMatrix, nomad.getxMap() + 1, nomad.getyMap()) &&
                            !isNomadCrossingBomb(mapPointMatrix, nomad.getxMap() + 1, nomad.getyMap(), DIRECTION_EAST) &&
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
}
