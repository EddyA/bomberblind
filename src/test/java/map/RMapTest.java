package map;

import org.assertj.core.api.WithAssertions;
import org.junit.Test;
import org.mockito.Mockito;

public class RMapTest implements WithAssertions {

    // use small values to avoid heap overflow using Mockito.
    private final int MAP_WIDTH = 4;
    private final int MAP_HEIGHT = 3;
    private final int SCREEN_WIDTH = 2;
    private final int SCREEN_HEIGHT = 1;

    @Test
    public void rMapShouldSetMembersWithTheAppropriateValues() throws Exception {

        // mock the RMapSetting class.
        RMapSetting rMapSetting = Mockito.mock(RMapSetting.class);
        Mockito.when(rMapSetting.getMapHeight()).thenReturn(MAP_HEIGHT);
        Mockito.when(rMapSetting.getMapWidth()).thenReturn(MAP_WIDTH);

        // create the RMap and set the RMapSetting with mocked instance.
        RMap rMap = new RMap(SCREEN_WIDTH, SCREEN_HEIGHT);
        rMap.rMapSetting = rMapSetting;

        assertThat(rMap.getMapWidth()).isEqualTo(MAP_WIDTH);
        assertThat(rMap.getMapHeight()).isEqualTo(MAP_HEIGHT);
        assertThat(rMap.getScreenWidth()).isEqualTo(SCREEN_WIDTH);
        assertThat(rMap.getScreenHeight()).isEqualTo(SCREEN_HEIGHT);
        for (int rowIdx = 0; rowIdx < rMap.getMapHeight(); rowIdx++) {
            for (int colIdx = 0; colIdx < rMap.getMapWidth(); colIdx++) {
                assertThat(rMap.getRMapPointMatrix()[rowIdx][colIdx]).isNotNull();
            }
        }
    }
}