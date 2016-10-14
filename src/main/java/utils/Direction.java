package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public enum Direction {

    NORTH, SOUTH, WEST, EAST; // available directions.

    /**
     * Create a list of direction.
     */
    private static List<Direction> directionList = new ArrayList<>(Arrays.asList(NORTH, SOUTH, WEST, EAST));

    /**
     * @return the number of directions.
     */
    public static int getNbDirections() {
        return directionList.size();
    }

    /**
     * @return a random direction.
     */
    public static Direction getRandomDirection() {
        return directionList.get(new Random().nextInt(directionList.size()));
    }
}
