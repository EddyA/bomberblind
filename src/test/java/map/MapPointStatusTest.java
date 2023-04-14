package map;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;

class MapPointStatusTest implements WithAssertions {

    @Test
    void constructorShouldSetAllBitsToSet() {
        MapPointStatus mapPointStatus = new MapPointStatus();

        // check.
        assertThat(mapPointStatus.isAvailable()).isFalse();
        assertThat(mapPointStatus.isPathway()).isFalse();
        assertThat(mapPointStatus.isMutable()).isFalse();
        assertThat(mapPointStatus.isEntrance()).isFalse();
        assertThat(mapPointStatus.isExit()).isFalse();
    }

    @Test
    void setAvailableShouldSetTheExpectedBit() {
        MapPointStatus mapPointStatus = new MapPointStatus();
        mapPointStatus.setAvailable(true);

        // check.
        assertThat(mapPointStatus.isAvailable()).isTrue();
        assertThat(mapPointStatus.isPathway()).isFalse();
        assertThat(mapPointStatus.isMutable()).isFalse();
        assertThat(mapPointStatus.isEntrance()).isFalse();
        assertThat(mapPointStatus.isExit()).isFalse();
    }

    @Test
    void setPathwayShouldSetTheExpectedBit() {
        MapPointStatus mapPointStatus = new MapPointStatus();
        mapPointStatus.setPathway(true);

        // check.
        assertThat(mapPointStatus.isAvailable()).isFalse();
        assertThat(mapPointStatus.isPathway()).isTrue();
        assertThat(mapPointStatus.isMutable()).isFalse();
        assertThat(mapPointStatus.isEntrance()).isFalse();
        assertThat(mapPointStatus.isExit()).isFalse();
    }

    @Test
    void setMutableShouldSetTheExpectedBit() {
        MapPointStatus mapPointStatus = new MapPointStatus();
        mapPointStatus.setMutable(true);

        // check.
        assertThat(mapPointStatus.isAvailable()).isFalse();
        assertThat(mapPointStatus.isPathway()).isFalse();
        assertThat(mapPointStatus.isMutable()).isTrue();
        assertThat(mapPointStatus.isEntrance()).isFalse();
        assertThat(mapPointStatus.isExit()).isFalse();
    }

    @Test
    void setEntranceShouldSetTheExpectedBit() {
        MapPointStatus mapPointStatus = new MapPointStatus();
        mapPointStatus.setEntrance(true);

        // check.
        assertThat(mapPointStatus.isAvailable()).isFalse();
        assertThat(mapPointStatus.isPathway()).isFalse();
        assertThat(mapPointStatus.isMutable()).isFalse();
        assertThat(mapPointStatus.isEntrance()).isTrue();
        assertThat(mapPointStatus.isExit()).isFalse();
    }

    @Test
    void setExitShouldSetTheExpectedBit() {
        MapPointStatus mapPointStatus = new MapPointStatus();
        mapPointStatus.setExit(true);

        // check.
        assertThat(mapPointStatus.isAvailable()).isFalse();
        assertThat(mapPointStatus.isPathway()).isFalse();
        assertThat(mapPointStatus.isMutable()).isFalse();
        assertThat(mapPointStatus.isEntrance()).isFalse();
        assertThat(mapPointStatus.isExit()).isTrue();
    }
}
