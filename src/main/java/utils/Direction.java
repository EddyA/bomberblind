package utils;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

public enum Direction {

    DIRECTION_NORTH, DIRECTION_SOUTH, DIRECTION_WEST, DIRECTION_EAST; // available directions.
    private static final Random random = new Random();

    /**
     * Create a list of direction.
     */
    private static final List<Direction> directionList =
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
        return switch (keyEvent) {
            case KeyEvent.VK_UP -> DIRECTION_NORTH;
            case KeyEvent.VK_DOWN -> DIRECTION_SOUTH;
            case KeyEvent.VK_LEFT -> DIRECTION_WEST;
            case KeyEvent.VK_RIGHT -> DIRECTION_EAST;
            default -> throw new RuntimeException("cannot convert KeyEvent '" + keyEvent + "' to Direction.");
        };
    }

    public static Optional<String> getLabel(Direction direction) {
        Optional<String> label = Optional.empty();
        switch (direction) {
            case DIRECTION_NORTH -> label = Optional.of("north");
            case DIRECTION_SOUTH -> label = Optional.of("south");
            case DIRECTION_WEST -> label = Optional.of("west");
            case DIRECTION_EAST -> label = Optional.of("east");
        }
        return label;
    }
}
