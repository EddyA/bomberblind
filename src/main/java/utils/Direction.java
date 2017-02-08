package utils;

import java.awt.event.KeyEvent;
import java.util.*;

public enum Direction {

    DIRECTION_NORTH, DIRECTION_SOUTH, DIRECTION_WEST, DIRECTION_EAST; // available directions.
    private final static Random random = new Random(); // init once the random object.

    /**
     * Create a list of direction.
     */
    private final static List<Direction> directionList =
            new ArrayList<>(Arrays.asList(DIRECTION_NORTH, DIRECTION_SOUTH, DIRECTION_WEST, DIRECTION_EAST));

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
                return DIRECTION_NORTH;
            case KeyEvent.VK_DOWN:
                return DIRECTION_SOUTH;
            case KeyEvent.VK_LEFT:
                return DIRECTION_WEST;
            case KeyEvent.VK_RIGHT:
                return DIRECTION_EAST;
        }
        throw new RuntimeException("cannot convert KeyEvent '" + keyEvent + "' to Direction.");
    }

    public static Optional<String> getlabel(Direction direction) {
        Optional<String> label = Optional.empty();
        switch (direction) {
            case DIRECTION_NORTH: {
                label = Optional.of("north");
                break;
            }
            case DIRECTION_SOUTH: {
                label = Optional.of("south");
                break;
            }
            case DIRECTION_WEST: {
                label = Optional.of("west");
                break;
            }
            case DIRECTION_EAST: {
                label = Optional.of("east");
                break;
            }
        }
        return label;
    }
}
