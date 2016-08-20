package map.abstracts;

import exceptions.InvalidMapConfigurationException;

import java.io.IOException;
import java.util.Properties;

/**
 * Abstract class of map properties.
 * Should at least define map dimensions.
 */
public abstract class MapProperties {

    final protected static String MAP_SIZE_WIDTH = "map.size.width";
    final protected static String MAP_SIZE_HEIGHT = "map.size.height";

    final protected String mapPropertiesFile;
    protected Properties properties = new Properties();

    public MapProperties(String mapPropertiesFile) {
        this.mapPropertiesFile = mapPropertiesFile;
    }

    public int getMapSizeWidth() { return Integer.parseInt(properties.getProperty(MAP_SIZE_WIDTH)); }

    public int getMapSizeHeight() { return Integer.parseInt(properties.getProperty(MAP_SIZE_HEIGHT)); }

    public Properties getProperties() {
        return properties;
    }

    /**
     * Load all properties from the map file properties.
     *
     * @return the current MapProperties
     * @throws IllegalArgumentException         if the properties file is null or empty
     * @throws InvalidMapConfigurationException if the properties file cannot be found
     * @throws IOException                      if the properties file cannot be opened
     */
    public abstract MapProperties loadProperties()
            throws IllegalArgumentException, InvalidMapConfigurationException, IOException;

    /**
     * Check all properties of the map file properties.
     *
     * @return the current MapProperties
     * @throws InvalidMapConfigurationException as soon as a propertie is badly set
     */
    public abstract MapProperties checkProperties()
            throws InvalidMapConfigurationException;
}
