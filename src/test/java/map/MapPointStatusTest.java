package map;

import org.assertj.core.api.WithAssertions;
import org.junit.Test;

public class MapPointStatusTest implements WithAssertions {

    @Test
    public void constructorShoudSetAllBitsToSet() throws Exception {
        MapPointStatus mapPointStatus = new MapPointStatus();

        // check.
        assertThat(mapPointStatus.isAvailable()).isFalse();
        assertThat(mapPointStatus.isPathway()).isFalse();
        assertThat(mapPointStatus.isMutable()).isFalse();
        assertThat(mapPointStatus.isEntrance()).isFalse();
        assertThat(mapPointStatus.isExit()).isFalse();
    }

    @Test
    public void setAvailableShoudSetTheExpectedBit() throws Exception {
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
    public void setPathwayShoudSetTheExpectedBit() throws Exception {
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
    public void setMutableShoudSetTheExpectedBit() throws Exception {
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
    public void setEntranceShoudSetTheExpectedBit() throws Exception {
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
    public void setExitShoudSetTheExpectedBit() throws Exception {
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