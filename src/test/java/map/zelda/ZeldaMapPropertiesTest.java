package map.zelda;

import java.io.IOException;
import exceptions.InvalidConfigurationException;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;

class ZeldaMapPropertiesTest implements WithAssertions {

    private final String TEST_MAP_PROPERTIES_FILE = "/test.zelda.map.properties";

    @Test
    void loadAndCheckPropertiesShouldLoadExpectedValues() throws IOException, InvalidConfigurationException {
        ZeldaMapProperties zeldaMapProperties = new ZeldaMapProperties(TEST_MAP_PROPERTIES_FILE);
        zeldaMapProperties.loadProperties();
        zeldaMapProperties.checkProperties();

        // check values.
        assertThat(zeldaMapProperties.getMapSizeWidth()).isEqualTo(1);
        assertThat(zeldaMapProperties.getMapSizeHeight()).isEqualTo(2);
        assertThat(zeldaMapProperties.getMapMarginVertical()).isEqualTo(3);
        assertThat(zeldaMapProperties.getMapMarginHorizontal()).isEqualTo(4);
        assertThat(zeldaMapProperties.getMapElementNbOrchard()).isEqualTo(5);
        assertThat(zeldaMapProperties.getMapElementNbTrough()).isEqualTo(6);
        assertThat(zeldaMapProperties.getMapElementNbGreenWood()).isEqualTo(7);
        assertThat(zeldaMapProperties.getMapElementNbRedTree()).isEqualTo(8);
        assertThat(zeldaMapProperties.getMapElementNbYellowTree()).isEqualTo(9);
        assertThat(zeldaMapProperties.getMapElementNbPathway()).isEqualTo(10);
        assertThat(zeldaMapProperties.getMapElementNbStatue()).isEqualTo(11);
        assertThat(zeldaMapProperties.getMapElementPerSingleImmutableObstacle()).isEqualTo(12);
        assertThat(zeldaMapProperties.getMapElementPerSingleMutableObstacle()).isEqualTo(13);
        assertThat(zeldaMapProperties.getMapElementPerDecoratedSinglePathway()).isEqualTo(14);
        assertThat(zeldaMapProperties.getMapElementPerDynamicSinglePathway()).isEqualTo(15);
        assertThat(zeldaMapProperties.getMapBonusNbBomb()).isEqualTo(16);
        assertThat(zeldaMapProperties.getMapBonusNbFlame()).isEqualTo(17);
        assertThat(zeldaMapProperties.getMapBonusNbHeart()).isEqualTo(18);
        assertThat(zeldaMapProperties.getMapBonusNbRoller()).isEqualTo(19);
    }

    @Test
    void valueOfOptionalPropertiesShouldBeZeroWhenNotSet() {
        ZeldaMapProperties zeldaMapProperties = new ZeldaMapProperties(null);

        // check values of optional properties.
        assertThat(zeldaMapProperties.getMapElementNbOrchard()).isEqualTo(0);
        assertThat(zeldaMapProperties.getMapElementNbTrough()).isEqualTo(0);
        assertThat(zeldaMapProperties.getMapElementNbGreenWood()).isEqualTo(0);
        assertThat(zeldaMapProperties.getMapElementNbRedTree()).isEqualTo(0);
        assertThat(zeldaMapProperties.getMapElementNbYellowTree()).isEqualTo(0);
        assertThat(zeldaMapProperties.getMapElementNbPathway()).isEqualTo(0);
        assertThat(zeldaMapProperties.getMapElementNbStatue()).isEqualTo(0);
        assertThat(zeldaMapProperties.getMapElementPerSingleImmutableObstacle()).isEqualTo(0);
        assertThat(zeldaMapProperties.getMapElementPerSingleMutableObstacle()).isEqualTo(0);
        assertThat(zeldaMapProperties.getMapElementPerDecoratedSinglePathway()).isEqualTo(0);
        assertThat(zeldaMapProperties.getMapElementPerDynamicSinglePathway()).isEqualTo(0);
        assertThat(zeldaMapProperties.getMapBonusNbBomb()).isEqualTo(0);
        assertThat(zeldaMapProperties.getMapBonusNbFlame()).isEqualTo(0);
        assertThat(zeldaMapProperties.getMapBonusNbHeart()).isEqualTo(0);
        assertThat(zeldaMapProperties.getMapBonusNbRoller()).isEqualTo(0);
    }

    @Test
    void loadPropertiesWithNullFileShouldThrowExpectedException() {
        ZeldaMapProperties zeldaMapProperties = new ZeldaMapProperties(null);
        assertThatThrownBy(zeldaMapProperties::loadProperties)
                .isInstanceOf(AssertionError.class)
                .hasMessage("map properties file not set.");
    }

    @Test
    void loadPropertiesWithEmptyFileShouldThrowExpectedException() {
        ZeldaMapProperties zeldaMapProperties = new ZeldaMapProperties("");
        assertThatThrownBy(zeldaMapProperties::loadProperties)
                .isInstanceOf(AssertionError.class)
                .hasMessage("map properties file not set.");
    }

    @Test
    void loadPropertiesWithBadPropertiesFilePathShouldThrowExpectedException() {
        ZeldaMapProperties zeldaMapProperties = new ZeldaMapProperties("badFilePath");
        assertThatThrownBy(zeldaMapProperties::loadProperties)
                .isInstanceOf(InvalidConfigurationException.class)
                .hasMessage("'badFilePath' file not found.");
    }

    @Test
    void checkPropertiesWithMissingPropertiesShouldThrowExpectedException()
        throws IOException, InvalidConfigurationException {
        ZeldaMapProperties zeldaMapProperties = new ZeldaMapProperties(TEST_MAP_PROPERTIES_FILE);
        zeldaMapProperties.loadProperties();

        // remove a mandatory field.
        zeldaMapProperties.getProperties().remove(ZeldaMapProperties.MAP_SIZE_HEIGHT);

        assertThatThrownBy(zeldaMapProperties::checkProperties)
                .isInstanceOf(InvalidConfigurationException.class)
                .hasMessage("'/test.zelda.map.properties' is not a valid properties file: "
                        + "some field are missing or not integer convertible.");
    }

    @Test
    void checkPropertiesWithNotIntegerPropertiesShouldThrowExpectedException()
        throws IOException, InvalidConfigurationException {
        ZeldaMapProperties zeldaMapProperties = new ZeldaMapProperties(TEST_MAP_PROPERTIES_FILE);
        zeldaMapProperties.loadProperties();

        // set a field with a bad value.
        zeldaMapProperties.getProperties().setProperty(ZeldaMapProperties.MAP_ELEMENT_NB_GREEN_TREE,
                "notAnIntegerConvertibleString");

        assertThatThrownBy(zeldaMapProperties::checkProperties)
                .isInstanceOf(InvalidConfigurationException.class)
                .hasMessage("'/test.zelda.map.properties' is not a valid properties file: "
                        + "some field are missing or not integer convertible.");
    }

    @Test
    void checkPropertiesWithTooHighPerValuePropertiesShouldThrowExpectedException()
        throws IOException, InvalidConfigurationException {
        ZeldaMapProperties zeldaMapProperties = new ZeldaMapProperties(TEST_MAP_PROPERTIES_FILE);
        zeldaMapProperties.loadProperties();

        // set percentage fields too high values (i.e. sum(percentage) > 100).
        zeldaMapProperties.getProperties().setProperty(ZeldaMapProperties.MAP_ELEMENT_PER_SINGLE_MUTABLE_OBSTACLE, "50");
        zeldaMapProperties.getProperties().setProperty(ZeldaMapProperties.MAP_ELEMENT_PER_SINGLE_IMMUTABLE_OBSTACLE, "60");

        assertThatThrownBy(zeldaMapProperties::checkProperties)
                .isInstanceOf(InvalidConfigurationException.class)
                .hasMessage("'/test.zelda.map.properties' is not a valid properties file: "
                        + "sum of the percentage cannot exceed 100.");
    }
}
