package map;

import org.assertj.core.api.WithAssertions;
import org.junit.Test;
import utils.MapProperties;

public class RMapSettingTest implements WithAssertions {

    final private String TEST_MAP_PROPERTIES_FILE = "/test.map.properties";

    @Test
    public void rMapSettingShouldLoadExpectedValues() throws Exception {
        MapProperties mapProperties = new MapProperties(TEST_MAP_PROPERTIES_FILE);
        mapProperties.loadProperties();
        mapProperties.checkProperties();

        RMapSetting rMapSetting = new RMapSetting(mapProperties);
        assertThat(rMapSetting.getMapWidth()).isEqualTo(1);
        assertThat(rMapSetting.getMapHeight()).isEqualTo(2);
        assertThat(rMapSetting.getNbWood1()).isEqualTo(3);
        assertThat(rMapSetting.getNbWood2()).isEqualTo(4);
        assertThat(rMapSetting.getNbTree1()).isEqualTo(5);
        assertThat(rMapSetting.getNbTree2()).isEqualTo(6);
        assertThat(rMapSetting.getNbPuddle1()).isEqualTo(7);
        assertThat(rMapSetting.getNbPuddle2()).isEqualTo(8);
        assertThat(rMapSetting.getPerSingleMutable()).isEqualTo(9);
        assertThat(rMapSetting.getPerSingleObstacle()).isEqualTo(10);
        assertThat(rMapSetting.getPerSingleFlowerPathway()).isEqualTo(11);
    }
}