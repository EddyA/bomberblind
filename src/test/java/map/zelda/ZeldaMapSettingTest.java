package map.zelda;

import org.assertj.core.api.WithAssertions;
import org.junit.Test;

public class ZeldaMapSettingTest implements WithAssertions {

    @SuppressWarnings("FieldCanBeLocal")
    final private String TEST_MAP_PROPERTIES_FILE = "/test.zelda.map.properties";

    @Test
    public void rMapSettingShouldLoadExpectedValues() throws Exception {
        ZeldaMapProperties zeldaMapProperties = new ZeldaMapProperties(TEST_MAP_PROPERTIES_FILE);
        zeldaMapProperties.loadProperties();
        zeldaMapProperties.checkProperties();

        ZeldaMapSetting zeldaMapSetting = new ZeldaMapSetting(zeldaMapProperties);
        assertThat(zeldaMapSetting.getMapWidth()).isEqualTo(1);
        assertThat(zeldaMapSetting.getMapHeight()).isEqualTo(2);
        assertThat(zeldaMapSetting.getVerticalMargin()).isEqualTo(3);
        assertThat(zeldaMapSetting.getHorizontalMargin()).isEqualTo(4);
        assertThat(zeldaMapSetting.getNbWood1()).isEqualTo(5);
        assertThat(zeldaMapSetting.getNbWood2()).isEqualTo(6);
        assertThat(zeldaMapSetting.getNbTree1()).isEqualTo(7);
        assertThat(zeldaMapSetting.getNbTree2()).isEqualTo(8);
        assertThat(zeldaMapSetting.getNbPuddle1()).isEqualTo(9);
        assertThat(zeldaMapSetting.getNbPuddle2()).isEqualTo(10);
        assertThat(zeldaMapSetting.getPerSingleMutable()).isEqualTo(11);
        assertThat(zeldaMapSetting.getPerSingleObstacle()).isEqualTo(12);
        assertThat(zeldaMapSetting.getPerSingleDynPathway()).isEqualTo(13);
        assertThat(zeldaMapSetting.getNbBonusBomb()).isEqualTo(14);
        assertThat(zeldaMapSetting.getNbBonusFlame()).isEqualTo(15);
        assertThat(zeldaMapSetting.getNbBonusHeart()).isEqualTo(16);
        assertThat(zeldaMapSetting.getNbBonusRoller()).isEqualTo(17);
    }
}