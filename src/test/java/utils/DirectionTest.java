package utils;

import org.assertj.core.api.WithAssertions;
import org.junit.Test;

import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

import static utils.Direction.*;

public class DirectionTest implements WithAssertions {

    @Test
    public void getRandomDirectionShouldReturnADirection() throws Exception {
        assertThat(Direction.getRandomDirection()).isInstanceOf(Direction.class);
    }

    @Test
    public void getRandomDirectionWithExclusionShouldReturnTheExpectedDirection() throws Exception {
        Set<Direction> checkedDirections = new HashSet<>();

        // all direction are allowed.
        Direction curDirection = Direction.getRandomDirectionWithExclusion(checkedDirections);
        assertThat(curDirection).isNotNull();
        assertThat(curDirection.equals(NORTH) ||
                curDirection.equals(SOUTH) ||
                curDirection.equals(EAST) ||
                curDirection.equals(WEST)).isTrue();

        // add north direction to the list of checked directions.
        checkedDirections.add(NORTH);

        // north direction is not allowed.
        curDirection = Direction.getRandomDirectionWithExclusion(checkedDirections);
        assertThat(curDirection).isNotNull();
        assertThat(curDirection.equals(SOUTH) || curDirection.equals(EAST) ||
                curDirection.equals(WEST)).isTrue();

        // add south direction to the list of checked directions.
        checkedDirections.add(SOUTH);

        // north/south directions are not allowed.
        curDirection = Direction.getRandomDirectionWithExclusion(checkedDirections);
        assertThat(curDirection).isNotNull();
        assertThat(curDirection.equals(EAST) || curDirection.equals(WEST)).isTrue();

        // add east direction to the list of checked directions.
        checkedDirections.add(EAST);

        // north/south/east directions are not allowed.
        curDirection = Direction.getRandomDirectionWithExclusion(checkedDirections);
        assertThat(curDirection).isNotNull();
        assertThat(curDirection.equals(WEST)).isTrue();

        // add west direction to the list of checked directions.
        checkedDirections.add(WEST);

        // north/south/east/west directions are not allowed.
        curDirection = Direction.getRandomDirectionWithExclusion(checkedDirections);
        assertThat(curDirection).isNull();
    }

    @Test
    public void convertKeyEventToDirectionShouldReturnTheExpectedDirection() throws Exception {
        assertThat(Direction.convertKeyEventToDirection(KeyEvent.VK_UP)).isEqualTo(NORTH);
        assertThat(Direction.convertKeyEventToDirection(KeyEvent.VK_DOWN)).isEqualTo(SOUTH);
        assertThat(Direction.convertKeyEventToDirection(KeyEvent.VK_LEFT)).isEqualTo(WEST);
        assertThat(Direction.convertKeyEventToDirection(KeyEvent.VK_RIGHT)).isEqualTo(EAST);
    }
}