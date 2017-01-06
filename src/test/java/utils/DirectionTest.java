package utils;

import org.assertj.core.api.WithAssertions;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

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
        assertThat(curDirection.equals(Direction.NORTH) ||
                curDirection.equals(Direction.SOUTH) ||
                curDirection.equals(Direction.EAST) ||
                curDirection.equals(Direction.WEST)).isTrue();

        // add north direction to the list of checked directions.
        checkedDirections.add(Direction.NORTH);

        // north direction is not allowed.
        curDirection = Direction.getRandomDirectionWithExclusion(checkedDirections);
        assertThat(curDirection).isNotNull();
        assertThat(curDirection.equals(Direction.SOUTH) || curDirection.equals(Direction.EAST) ||
                curDirection.equals(Direction.WEST)).isTrue();

        // add south direction to the list of checked directions.
        checkedDirections.add(Direction.SOUTH);

        // north/south directions are not allowed.
        curDirection = Direction.getRandomDirectionWithExclusion(checkedDirections);
        assertThat(curDirection).isNotNull();
        assertThat(curDirection.equals(Direction.EAST) || curDirection.equals(Direction.WEST)).isTrue();

        // add east direction to the list of checked directions.
        checkedDirections.add(Direction.EAST);

        // north/south/east directions are not allowed.
        curDirection = Direction.getRandomDirectionWithExclusion(checkedDirections);
        assertThat(curDirection).isNotNull();
        assertThat(curDirection.equals(Direction.WEST)).isTrue();

        // add west direction to the list of checked directions.
        checkedDirections.add(Direction.WEST);

        // north/south/east/west directions are not allowed.
        curDirection = Direction.getRandomDirectionWithExclusion(checkedDirections);
        assertThat(curDirection).isNull();
    }
}