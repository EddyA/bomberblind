package map.zelda;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ZeldaMapTest implements WithAssertions {

    // use small values to avoid heap overflow using Mockito.
    @SuppressWarnings("FieldCanBeLocal")
    private final int MAP_WIDTH = 4;
    @SuppressWarnings("FieldCanBeLocal")
    private final int MAP_HEIGHT = 3;
    @SuppressWarnings("FieldCanBeLocal")
    private final int SCREEN_WIDTH = 2;
    @SuppressWarnings("FieldCanBeLocal")
    private final int SCREEN_HEIGHT = 1;

    @Test
    void rMapShouldSetMembersWithTheExpectedValues() {

        // mock the ZeldaMapSetting class.
        ZeldaMapSetting zeldaMapSetting = Mockito.mock(ZeldaMapSetting.class);
        Mockito.when(zeldaMapSetting.getMapHeight()).thenReturn(MAP_HEIGHT);
        Mockito.when(zeldaMapSetting.getMapWidth()).thenReturn(MAP_WIDTH);

        // create the ZeldaMap and set the ZeldaMapSetting with mocked instance.
        ZeldaMap map = new ZeldaMap(zeldaMapSetting, SCREEN_WIDTH, SCREEN_HEIGHT);

        assertThat(map.getMapWidth()).isEqualTo(MAP_WIDTH);
        assertThat(map.getMapHeight()).isEqualTo(MAP_HEIGHT);
        assertThat(map.getScreenWidth()).isEqualTo(SCREEN_WIDTH);
        assertThat(map.getScreenHeight()).isEqualTo(SCREEN_HEIGHT);
        for (int rowIdx = 0; rowIdx < map.getMapHeight(); rowIdx++) {
            for (int colIdx = 0; colIdx < map.getMapWidth(); colIdx++) {
                assertThat(map.getMapPointMatrix()[rowIdx][colIdx]).isNotNull();
            }
        }
    }
}
