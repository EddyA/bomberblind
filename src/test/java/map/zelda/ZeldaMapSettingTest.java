package map.zelda;

import java.io.IOException;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;
import exceptions.InvalidConfigurationException;
import exceptions.InvalidPropertiesException;

class ZeldaMapSettingTest implements WithAssertions {

    @SuppressWarnings("FieldCanBeLocal")
    final private String TEST_MAP_PROPERTIES_FILE = "/test.zelda.map.properties";

    @Test
    void rMapSettingShouldLoadExpectedValues()
        throws IOException, InvalidConfigurationException, InvalidPropertiesException {
        ZeldaMapProperties zeldaMapProperties = new ZeldaMapProperties(TEST_MAP_PROPERTIES_FILE);
        zeldaMapProperties.loadProperties();
        zeldaMapProperties.checkProperties();

        ZeldaMapSetting zeldaMapSetting = new ZeldaMapSetting(zeldaMapProperties);
        assertThat(zeldaMapSetting.getMapWidth()).isEqualTo(1);
        assertThat(zeldaMapSetting.getMapHeight()).isEqualTo(2);
        assertThat(zeldaMapSetting.getVerticalMargin()).isEqualTo(3);
        assertThat(zeldaMapSetting.getHorizontalMargin()).isEqualTo(4);
        assertThat(zeldaMapSetting.getNbOrchard()).isEqualTo(5);
        assertThat(zeldaMapSetting.getNbTrough()).isEqualTo(6);
        assertThat(zeldaMapSetting.getNbGreenTree()).isEqualTo(7);
        assertThat(zeldaMapSetting.getNbRedTree()).isEqualTo(8);
        assertThat(zeldaMapSetting.getNbYellowTree()).isEqualTo(9);
        assertThat(zeldaMapSetting.getNbPathway()).isEqualTo(10);
        assertThat(zeldaMapSetting.getNbStatue()).isEqualTo(11);
        assertThat(zeldaMapSetting.getPerSingleImmutableObstacle()).isEqualTo(12);
        assertThat(zeldaMapSetting.getPerSingleMutableObstacle()).isEqualTo(13);
        assertThat(zeldaMapSetting.getPerDecoratedSinglePathway()).isEqualTo(14);
        assertThat(zeldaMapSetting.getPerDynamicSinglePathway()).isEqualTo(15);
        assertThat(zeldaMapSetting.getNbBonusBomb()).isEqualTo(16);
        assertThat(zeldaMapSetting.getNbBonusFlame()).isEqualTo(17);
        assertThat(zeldaMapSetting.getNbBonusHeart()).isEqualTo(18);
        assertThat(zeldaMapSetting.getNbBonusRoller()).isEqualTo(19);
    }
}
