package ai;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.experimental.UtilityClass;
import map.MapPoint;
import static map.ctrl.NomadMethods.isNomadBurning;
import static map.ctrl.NomadMethods.isNomadCrossingBomb;
import static map.ctrl.NomadMethods.isNomadCrossingMapLimit;
import static map.ctrl.NomadMethods.isNomadCrossingObstacle;
import sprite.Sprite;
import static sprite.ctrl.NomadMethods.isNomadCrossingEnemy;
import sprite.nomad.Nomad;
import utils.Direction;
import static utils.Direction.DIRECTION_EAST;
import static utils.Direction.DIRECTION_NORTH;
import static utils.Direction.DIRECTION_SOUTH;
import static utils.Direction.DIRECTION_WEST;

@UtilityClass
public class NomadAi {

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
    public static Direction computeNextDirection(MapPoint[][] mapPointMatrix,
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
                case DIRECTION_NORTH -> {
                    if (!isNomadCrossingMapLimit(mapWidth, mapHeight, nomad.getXMap(), nomad.getYMap() - 1) &&
                        !isNomadCrossingObstacle(mapPointMatrix, nomad.getXMap(), nomad.getYMap() - 1) &&
                        !isNomadBurning(mapPointMatrix, nomad.getXMap(), nomad.getYMap() - 1) &&
                        !isNomadCrossingBomb(mapPointMatrix, nomad.getXMap(), nomad.getYMap() - 1, DIRECTION_NORTH) &&
                        !isNomadCrossingEnemy(spriteList, nomad.getXMap(), nomad.getYMap() - 1, nomad)) {
                        resultFound = true;
                    }
                }
                case DIRECTION_SOUTH -> {
                    if (!isNomadCrossingMapLimit(mapWidth, mapHeight, nomad.getXMap(), nomad.getYMap() + 1) &&
                        !isNomadCrossingObstacle(mapPointMatrix, nomad.getXMap(), nomad.getYMap() + 1) &&
                        !isNomadBurning(mapPointMatrix, nomad.getXMap(), nomad.getYMap() + 1) &&
                        !isNomadCrossingBomb(mapPointMatrix, nomad.getXMap(), nomad.getYMap() + 1, DIRECTION_SOUTH) &&
                        !isNomadCrossingEnemy(spriteList, nomad.getXMap(), nomad.getYMap() + 1, nomad)) {
                        resultFound = true;
                    }
                }
                case DIRECTION_WEST -> {
                    if (!isNomadCrossingMapLimit(mapWidth, mapHeight, nomad.getXMap() - 1, nomad.getYMap()) &&
                        !isNomadCrossingObstacle(mapPointMatrix, nomad.getXMap() - 1, nomad.getYMap()) &&
                        !isNomadBurning(mapPointMatrix, nomad.getXMap() - 1, nomad.getYMap()) &&
                        !isNomadCrossingBomb(mapPointMatrix, nomad.getXMap() - 1, nomad.getYMap(), DIRECTION_WEST) &&
                        !isNomadCrossingEnemy(spriteList, nomad.getXMap() - 1, nomad.getYMap(), nomad)) {
                        resultFound = true;
                    }
                }
                case DIRECTION_EAST -> {
                    if (!isNomadCrossingMapLimit(mapWidth, mapHeight, nomad.getXMap() + 1, nomad.getYMap()) &&
                        !isNomadCrossingObstacle(mapPointMatrix, nomad.getXMap() + 1, nomad.getYMap()) &&
                        !isNomadBurning(mapPointMatrix, nomad.getXMap() + 1, nomad.getYMap()) &&
                        !isNomadCrossingBomb(mapPointMatrix, nomad.getXMap() + 1, nomad.getYMap(), DIRECTION_EAST) &&
                        !isNomadCrossingEnemy(spriteList, nomad.getXMap() + 1, nomad.getYMap(), nomad)) {
                        resultFound = true;
                    }
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
