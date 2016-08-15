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
    public void rMapShouldSetMembersWithTheAppropriateValues() throws Exception {

        // mock the ZeldaMapSetting class.
        ZeldaMapSetting zeldaMapSetting = Mockito.mock(ZeldaMapSetting.class);
        Mockito.when(zeldaMapSetting.getMapHeight()).thenReturn(MAP_HEIGHT);
        Mockito.when(zeldaMapSetting.getMapWidth()).thenReturn(MAP_WIDTH);

        // create the ZeldaMap and set the ZeldaMapSetting with mocked instance.
        ZeldaMap zeldaMap = new ZeldaMap(SCREEN_WIDTH, SCREEN_HEIGHT);
        zeldaMap.zeldaMapSetting = zeldaMapSetting;

        assertThat(zeldaMap.getMapWidth()).isEqualTo(MAP_WIDTH);
        assertThat(zeldaMap.getMapHeight()).isEqualTo(MAP_HEIGHT);
        assertThat(zeldaMap.screenWidth).isEqualTo(SCREEN_WIDTH);
        assertThat(zeldaMap.screenHeight).isEqualTo(SCREEN_HEIGHT);
        for (int rowIdx = 0; rowIdx < zeldaMap.getMapHeight(); rowIdx++) {
            for (int colIdx = 0; colIdx < zeldaMap.getMapWidth(); colIdx++) {
                assertThat(zeldaMap.getMapPointMatrix()[rowIdx][colIdx]).isNotNull();
            }
        }
    }
}