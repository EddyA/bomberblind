package utils;

import exceptions.InvalidMapConfigurationException;
import org.assertj.core.api.WithAssertions;
import org.junit.Test;
import org.mockito.Mockito;

public class MapPropertiesTest implements WithAssertions {

    final private String TEST_MAP_PROPERTIES_FILE = "/test.map.properties";

    @Test
    public void loadAndCheckPropertiesShouldLoadExpectedValues() throws Exception {
        MapProperties mapProperties = new MapProperties(TEST_MAP_PROPERTIES_FILE);
        mapProperties.loadProperties();
        mapProperties.checkProperties();

        // check values.
        assertThat(mapProperties.getMapSizeWidth()).isEqualTo(1);
        assertThat(mapProperties.getMapSizeHeight()).isEqualTo(2);
        assertThat(mapProperties.getMapElementNbWood1()).isEqualTo(3);
        assertThat(mapProperties.getMapElementNbWood2()).isEqualTo(4);
        assertThat(mapProperties.getMapElementNbTree1()).isEqualTo(5);
        assertThat(mapProperties.getMapElementNbTree2()).isEqualTo(6);
        assertThat(mapProperties.getMapElementNbPuddle1()).isEqualTo(7);
        assertThat(mapProperties.getMapElementNbPuddle2()).isEqualTo(8);
        assertThat(mapProperties.getMapElementPerSingleMutable()).isEqualTo(9);
        assertThat(mapProperties.getMapElementPerSingleObstacle()).isEqualTo(10);
        assertThat(mapProperties.getMapElementPerSingleDynPathway()).isEqualTo(11);
    }

    @Test
    public void loadPropertiesWithNullFileShouldThrowTheAppropriateException() throws Exception {
        MapProperties mapProperties = new MapProperties(null);
        assertThatThrownBy(mapProperties::loadProperties)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("map properties file not set.");
    }

    @Test
    public void loadPropertiesWithEmptyFileShouldThrowTheAppropriateException() throws Exception {
        MapProperties mapProperties = new MapProperties("");
        assertThatThrownBy(mapProperties::loadProperties)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("map properties file not set.");
    }

    @Test
    public void loadPropertiesWithUnknownParameterShouldThrowTheAppropriateException() throws Exception {
        MapProperties mapProperties = new MapProperties("badFilePath");
        assertThatThrownBy(mapProperties::loadProperties)
                .isInstanceOf(InvalidMapConfigurationException.class)
                .hasMessage("'badFilePath' file not found.");
    }

    @Test
    public void checkPropertiesWithNotIntegerPropertiesShouldThrowTheAppropriateException() throws Exception {
        MapProperties mapProperties = new MapProperties(TEST_MAP_PROPERTIES_FILE);
        mapProperties.loadProperties();

        // set a field with a bad value.
        mapProperties.properties.setProperty(MapProperties.MAP_SIZE_WIDTH, "notAnIntegerConvertibleString");

        assertThatThrownBy(mapProperties::checkProperties)
                .isInstanceOf(InvalidMapConfigurationException.class)
                .hasMessage("'/test.map.properties' is not a valid properties file: "
                        + "some field are missing or not integer convertible.");
    }

    @Test
    public void checkPropertiesWithMissingPropertiesShouldThrowTheAppropriateException() throws Exception {
        MapProperties mapProperties = new MapProperties(TEST_MAP_PROPERTIES_FILE);
        mapProperties.loadProperties();

        // remove a mandatory field.
        mapProperties.properties.remove(MapProperties.MAP_SIZE_WIDTH);

        assertThatThrownBy(mapProperties::checkProperties)
                .isInstanceOf(InvalidMapConfigurationException.class)
                .hasMessage("'/test.map.properties' is not a valid properties file: "
                        + "some field are missing or not integer convertible.");
    }

    @Test
    public void checkPropertiesWithTooHighPerValuePropertiesShouldThrowTheAppropriateException() throws Exception {
        MapProperties mapProperties = new MapProperties(TEST_MAP_PROPERTIES_FILE);
        mapProperties.loadProperties();

        // set percentage fields too high values (i.e. sum(percentage) > 100).
        mapProperties.properties.setProperty(MapProperties.MAP_ELEMENT_PER_SINGLE_MUTABLE, "50");
        mapProperties.properties.setProperty(MapProperties.MAP_ELEMENT_PER_SINGLE_OBSTACLE, "40");
        mapProperties.properties.setProperty(MapProperties.MAP_ELEMENT_PER_SINGLE_DYN_PATHWAY, "30");

        assertThatThrownBy(mapProperties::checkProperties)
                .isInstanceOf(InvalidMapConfigurationException.class)
                .hasMessage("'/test.map.properties' is not a valid properties file: "
                        + "sum of the percentage cannot exceed 100.");
    }
}