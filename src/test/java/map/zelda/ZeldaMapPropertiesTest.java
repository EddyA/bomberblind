package map.zelda;

import exceptions.InvalidConfigurationException;

import org.assertj.core.api.WithAssertions;
import org.junit.Test;

public class ZeldaMapPropertiesTest implements WithAssertions {

    final private String TEST_MAP_PROPERTIES_FILE = "/test.zelda.map.properties";

    @Test
    public void loadAndCheckPropertiesShouldLoadExpectedValues() throws Exception {
        ZeldaMapProperties zeldaMapProperties = new ZeldaMapProperties(TEST_MAP_PROPERTIES_FILE);
        zeldaMapProperties.loadProperties();
        zeldaMapProperties.checkProperties();

        // check values.
        assertThat(zeldaMapProperties.getMapSizeWidth()).isEqualTo(1);
        assertThat(zeldaMapProperties.getMapSizeHeight()).isEqualTo(2);
        assertThat(zeldaMapProperties.getMapMarginVertical()).isEqualTo(3);
        assertThat(zeldaMapProperties.getMapMarginHorizontal()).isEqualTo(4);
        assertThat(zeldaMapProperties.getMapElementNbWood1()).isEqualTo(5);
        assertThat(zeldaMapProperties.getMapElementNbWood2()).isEqualTo(6);
        assertThat(zeldaMapProperties.getMapElementNbTree1()).isEqualTo(7);
        assertThat(zeldaMapProperties.getMapElementNbTree2()).isEqualTo(8);
        assertThat(zeldaMapProperties.getMapElementNbPuddle1()).isEqualTo(9);
        assertThat(zeldaMapProperties.getMapElementNbPuddle2()).isEqualTo(10);
        assertThat(zeldaMapProperties.getMapElementPerSingleMutable()).isEqualTo(11);
        assertThat(zeldaMapProperties.getMapElementPerSingleObstacle()).isEqualTo(12);
        assertThat(zeldaMapProperties.getMapElementPerSingleDynPathway()).isEqualTo(13);
    }

    @Test
    public void loadPropertiesWithNullFileShouldThrowExpectedException() throws Exception {
        ZeldaMapProperties zeldaMapProperties = new ZeldaMapProperties(null);
        assertThatThrownBy(zeldaMapProperties::loadProperties)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("map properties file not set.");
    }

    @Test
    public void loadPropertiesWithEmptyFileShouldThrowExpectedException() throws Exception {
        ZeldaMapProperties zeldaMapProperties = new ZeldaMapProperties("");
        assertThatThrownBy(zeldaMapProperties::loadProperties)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("map properties file not set.");
    }

    @Test
    public void loadPropertiesWithUnknownParameterShouldThrowExpectedException() throws Exception {
        ZeldaMapProperties zeldaMapProperties = new ZeldaMapProperties("badFilePath");
        assertThatThrownBy(zeldaMapProperties::loadProperties)
                .isInstanceOf(InvalidConfigurationException.class)
                .hasMessage("'badFilePath' file not found.");
    }

    @Test
    public void checkPropertiesWithNotIntegerPropertiesShouldThrowExpectedException() throws Exception {
        ZeldaMapProperties zeldaMapProperties = new ZeldaMapProperties(TEST_MAP_PROPERTIES_FILE);
        zeldaMapProperties.loadProperties();

        // set a field with a bad value.
        zeldaMapProperties.getProperties().setProperty(ZeldaMapProperties.MAP_ELEMENT_PER_SINGLE_DYN_PATHWAY,
                "notAnIntegerConvertibleString");

        assertThatThrownBy(zeldaMapProperties::checkProperties)
                .isInstanceOf(InvalidConfigurationException.class)
                .hasMessage("'/test.zelda.map.properties' is not a valid properties file: "
                        + "some field are missing or not integer convertible.");
    }

    @Test
    public void checkPropertiesWithMissingPropertiesShouldThrowExpectedException() throws Exception {
        ZeldaMapProperties zeldaMapProperties = new ZeldaMapProperties(TEST_MAP_PROPERTIES_FILE);
        zeldaMapProperties.loadProperties();

        // remove a mandatory field.
        zeldaMapProperties.getProperties().remove(ZeldaMapProperties.MAP_ELEMENT_PER_SINGLE_DYN_PATHWAY);

        assertThatThrownBy(zeldaMapProperties::checkProperties)
                .isInstanceOf(InvalidConfigurationException.class)
                .hasMessage("'/test.zelda.map.properties' is not a valid properties file: "
                        + "some field are missing or not integer convertible.");
    }

    @Test
    public void checkPropertiesWithTooHighPerValuePropertiesShouldThrowExpectedException() throws Exception {
        ZeldaMapProperties zeldaMapProperties = new ZeldaMapProperties(TEST_MAP_PROPERTIES_FILE);
        zeldaMapProperties.loadProperties();

        // set percentage fields too high values (i.e. sum(percentage) > 100).
        zeldaMapProperties.getProperties().setProperty(ZeldaMapProperties.MAP_ELEMENT_PER_SINGLE_MUTABLE, "50");
        zeldaMapProperties.getProperties().setProperty(ZeldaMapProperties.MAP_ELEMENT_PER_SINGLE_OBSTACLE, "40");
        zeldaMapProperties.getProperties().setProperty(ZeldaMapProperties.MAP_ELEMENT_PER_SINGLE_DYN_PATHWAY, "30");

        assertThatThrownBy(zeldaMapProperties::checkProperties)
                .isInstanceOf(InvalidConfigurationException.class)
                .hasMessage("'/test.zelda.map.properties' is not a valid properties file: "
                        + "sum of the percentage cannot exceed 100.");
    }
}