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
        assertThat(curDirection.equals(DIRECTION_NORTH) ||
                curDirection.equals(DIRECTION_SOUTH) ||
                curDirection.equals(DIRECTION_EAST) ||
                curDirection.equals(DIRECTION_WEST)).isTrue();

        // add north direction to the list of checked directions.
        checkedDirections.add(DIRECTION_NORTH);

        // north direction is not allowed.
        curDirection = Direction.getRandomDirectionWithExclusion(checkedDirections);
        assertThat(curDirection).isNotNull();
        assertThat(curDirection.equals(DIRECTION_SOUTH) || curDirection.equals(DIRECTION_EAST) ||
                curDirection.equals(DIRECTION_WEST)).isTrue();

        // add south direction to the list of checked directions.
        checkedDirections.add(DIRECTION_SOUTH);

        // north/south directions are not allowed.
        curDirection = Direction.getRandomDirectionWithExclusion(checkedDirections);
        assertThat(curDirection).isNotNull();
        assertThat(curDirection.equals(DIRECTION_EAST) || curDirection.equals(DIRECTION_WEST)).isTrue();

        // add east direction to the list of checked directions.
        checkedDirections.add(DIRECTION_EAST);

        // north/south/east directions are not allowed.
        curDirection = Direction.getRandomDirectionWithExclusion(checkedDirections);
        assertThat(curDirection).isNotNull();
        assertThat(curDirection.equals(DIRECTION_WEST)).isTrue();

        // add west direction to the list of checked directions.
        checkedDirections.add(DIRECTION_WEST);

        // north/south/east/west directions are not allowed.
        curDirection = Direction.getRandomDirectionWithExclusion(checkedDirections);
        assertThat(curDirection).isNull();
    }

    @Test
    public void convertKeyEventToDirectionWithABadKeyShouldThrownAnException() throws Exception {
        assertThatThrownBy(() -> Direction.convertKeyEventToDirection(KeyEvent.VK_B))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("cannot convert KeyEvent '66' to Direction.");
    }

    @Test
    public void convertKeyEventToDirectionShouldReturnTheExpectedDirection() throws Exception {
        assertThat(Direction.convertKeyEventToDirection(KeyEvent.VK_UP)).isEqualTo(DIRECTION_NORTH);
        assertThat(Direction.convertKeyEventToDirection(KeyEvent.VK_DOWN)).isEqualTo(DIRECTION_SOUTH);
        assertThat(Direction.convertKeyEventToDirection(KeyEvent.VK_LEFT)).isEqualTo(DIRECTION_WEST);
        assertThat(Direction.convertKeyEventToDirection(KeyEvent.VK_RIGHT)).isEqualTo(DIRECTION_EAST);
    }

    @Test
    public void getlabelShouldReturnTheAppropriateLabel() throws Exception {
        assertThat(getlabel(DIRECTION_NORTH).orElse("no_name")).isEqualTo("north");
        assertThat(getlabel(DIRECTION_SOUTH).orElse("no_name")).isEqualTo("south");
        assertThat(getlabel(DIRECTION_WEST).orElse("no_name")).isEqualTo("west");
        assertThat(getlabel(DIRECTION_EAST).orElse("no_name")).isEqualTo("east");
    }
}