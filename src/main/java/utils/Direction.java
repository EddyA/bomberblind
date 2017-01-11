package utils;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;

public enum Direction {

    NORTH, SOUTH, WEST, EAST; // available directions.
    private final static Random random = new Random(); // init once the random object.

    /**
     * Create a list of direction.
     */
    private final static List<Direction> directionList = new ArrayList<>(Arrays.asList(NORTH, SOUTH, WEST, EAST));

    /**
     * @return a random direction.
     */
    public static Direction getRandomDirection() {
        return directionList.get(random.nextInt(directionList.size()));
    }

    /**
     * @return a random direction if possible, null otherwise (because all are excluded).
     */
    public static Direction getRandomDirectionWithExclusion(Set<Direction> excludedDirections) {
        if (excludedDirections.size() == directionList.size()) { // all directions are excluded.
            return null;
        }
        Direction rDirection;
        do {
            rDirection = directionList.get(random.nextInt(directionList.size()));
        } while (excludedDirections.contains(rDirection));
        return rDirection;
    }

    /**
     * Convert a KeyEvent to a Direction.
     *
     * @param keyEvent the keyEvent
     * @return the relative direction if possible, null otherwise.
     */
    public static Direction convertKeyEventToDirection(Integer keyEvent) {
        switch (keyEvent) {
            case KeyEvent.VK_UP:
                return NORTH;
            case KeyEvent.VK_DOWN:
                return SOUTH;
            case KeyEvent.VK_LEFT:
                return WEST;
            case KeyEvent.VK_RIGHT:
                return EAST;
        }
        throw new RuntimeException("Cannot convert KeyEvent '" + keyEvent + "' to Direction.");
    }
}
