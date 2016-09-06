package map.zelda;

import org.assertj.core.api.WithAssertions;
import org.junit.Test;
import org.mockito.Mockito;

public class ZeldaMapTest implements WithAssertions {

    // use small values to avoid heap overflow using Mockito.
    private final int MAP_WIDTH = 4;
    private final int MAP_HEIGHT = 3;
    private final int SCREEN_WIDTH = 2;
    private final int SCREEN_HEIGHT = 1;

    @Test
    public void rMapShouldSetMembersWithTheExpectedValues() throws Exception {

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