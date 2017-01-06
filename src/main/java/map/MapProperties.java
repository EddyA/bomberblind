package map;

import exceptions.InvalidConfigurationException;

import java.io.IOException;
import java.util.Properties;

/**
 * Abstract class of map properties.
 * Should at least define map dimensions.
 */
public abstract class MapProperties {

    public final static String MAP_SIZE_WIDTH = "map.size.width";
    public final static String MAP_SIZE_HEIGHT = "map.size.height";

    protected final String propertiesFile;
    protected final Properties properties = new Properties();

    public MapProperties(String propertiesFile) {
        this.propertiesFile = propertiesFile;
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
     * @throws InvalidConfigurationException if the properties file cannot be found
     * @throws IOException                      if the properties file cannot be opened
     */
    public abstract MapProperties loadProperties()
            throws IllegalArgumentException, InvalidConfigurationException, IOException;

    /**
     * Check all properties of the map file properties.
     *
     * @return the current MapProperties
     * @throws InvalidConfigurationException as soon as a propertie is badly set
     */
    public abstract MapProperties checkProperties()
            throws InvalidConfigurationException;
}
