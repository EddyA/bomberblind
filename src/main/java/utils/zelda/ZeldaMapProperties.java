package utils.zelda;

import com.google.common.base.Preconditions;
import exceptions.InvalidMapConfigurationException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import static utils.Tools.isValidInteger;

/**
 * Open and read the map properties file.
 */
public class ZeldaMapProperties {

    final private String MAP_PROPERTIES_FILE = "/map.properties";

    // properties field name.
    final protected static String MAP_SIZE_WIDTH = "map.size.width";
    final private static String MAP_SIZE_HEIGHT = "map.size.height";
    final private static String MAP_MARGIN_VERTICAL = "map.margin.vertical";
    final private static String MAP_MARGIN_HORIZONTAL = "map.margin.horizontal";
    final private static String MAP_ELEMENT_NB_WOOD1 = "map.element.woods1.number";
    final private static String MAP_ELEMENT_NB_WOOD2 = "map.element.woods2.number";
    final private static String MAP_ELEMENT_NB_TREE1 = "map.element.tree1.number";
    final private static String MAP_ELEMENT_NB_TREE2 = "map.element.tree2.number";
    final private static String MAP_ELEMENT_NB_PUDDLE1 = "map.element.puddle1.number";
    final private static String MAP_ELEMENT_NB_PUDDLE2 = "map.element.puddle2.number";
    final protected static String MAP_ELEMENT_PER_SINGLE_MUTABLE = "map.element.single.mutable.percentage";
    final protected static String MAP_ELEMENT_PER_SINGLE_OBSTACLE = "map.element.single.obstacle.percentage";
    final protected static String MAP_ELEMENT_PER_SINGLE_DYN_PATHWAY = "map.element.single.dynamic.pathway.percentage";

    protected String mapPropertiesFile;
    protected Properties properties = new Properties();

    /**
     * Load and check the default map properties file.
     *
     * @throws IOException
     * @throws InvalidMapConfigurationException
     */
    public ZeldaMapProperties() throws IOException, InvalidMapConfigurationException {
        this.mapPropertiesFile = MAP_PROPERTIES_FILE;
    }

    /**
     * Load and check a particular map properties file.
     * Note: this constructor has been declare for test purpose.
     *
     * @throws IOException                      if the properties file cannot be opened
     * @throws InvalidMapConfigurationException if properties file has not been set or cannot be found
     */
    public ZeldaMapProperties(String mapPropertiesFile) {
        this.mapPropertiesFile = mapPropertiesFile;
    }

    public int getMapSizeWidth() {
        return Integer.parseInt(properties.getProperty(MAP_SIZE_WIDTH));
    }

    public int getMapSizeHeight() {
        return Integer.parseInt(properties.getProperty(MAP_SIZE_HEIGHT));
    }

    public int getMapMarginVertical() {
        return Integer.parseInt(properties.getProperty(MAP_MARGIN_VERTICAL));
    }

    public int getMapMarginHorizontal() {
        return Integer.parseInt(properties.getProperty(MAP_MARGIN_HORIZONTAL));
    }

    public int getMapElementNbWood1() {
        return Integer.parseInt(properties.getProperty(MAP_ELEMENT_NB_WOOD1));
    }

    public int getMapElementNbWood2() {
        return Integer.parseInt(properties.getProperty(MAP_ELEMENT_NB_WOOD2));
    }

    public int getMapElementNbTree1() {
        return Integer.parseInt(properties.getProperty(MAP_ELEMENT_NB_TREE1));
    }

    public int getMapElementNbTree2() {
        return Integer.parseInt(properties.getProperty(MAP_ELEMENT_NB_TREE2));
    }

    public int getMapElementNbPuddle1() {
        return Integer.parseInt(properties.getProperty(MAP_ELEMENT_NB_PUDDLE1));
    }

    public int getMapElementNbPuddle2() {
        return Integer.parseInt(properties.getProperty(MAP_ELEMENT_NB_PUDDLE2));
    }

    public int getMapElementPerSingleMutable() {
        return Integer.parseInt(properties.getProperty(MAP_ELEMENT_PER_SINGLE_MUTABLE));
    }

    public int getMapElementPerSingleObstacle() {
        return Integer.parseInt(properties.getProperty(MAP_ELEMENT_PER_SINGLE_OBSTACLE));
    }

    public int getMapElementPerSingleDynPathway() {
        return Integer.parseInt(properties.getProperty(MAP_ELEMENT_PER_SINGLE_DYN_PATHWAY));
    }

    /**
     * Load all properties from the map file properties.
     *
     * @throws IOException                      if the properties file cannot be opened
     * @throws InvalidMapConfigurationException if properties file has not been set or cannot be found
     */
    public void loadProperties()
            throws IllegalArgumentException, IOException, InvalidMapConfigurationException {
        Preconditions.checkArgument((mapPropertiesFile != null) &&
                !mapPropertiesFile.isEmpty(), "map properties file not set.");
        URL configurationFile = ZeldaMapProperties.class.getResource(mapPropertiesFile);
        if (configurationFile == null) {
            throw new InvalidMapConfigurationException("'" + mapPropertiesFile + "' file not found.");
        }
        InputStream inputStream = configurationFile.openStream();
        properties.load(inputStream);
    }

    /**
     * Check all properties of the map file properties.
     *
     * @throws InvalidMapConfigurationException if at least one propertie is badly set.
     */
    public void checkProperties() throws InvalidMapConfigurationException {
        if (!isValidInteger(properties.getProperty(MAP_SIZE_WIDTH)) ||
                !isValidInteger(properties.getProperty(MAP_SIZE_HEIGHT)) ||
                !isValidInteger(properties.getProperty(MAP_MARGIN_VERTICAL)) ||
                !isValidInteger(properties.getProperty(MAP_MARGIN_HORIZONTAL)) ||
                !isValidInteger(properties.getProperty(MAP_ELEMENT_NB_WOOD1)) ||
                !isValidInteger(properties.getProperty(MAP_ELEMENT_NB_WOOD2)) ||
                !isValidInteger(properties.getProperty(MAP_ELEMENT_NB_TREE1)) ||
                !isValidInteger(properties.getProperty(MAP_ELEMENT_NB_TREE2)) ||
                !isValidInteger(properties.getProperty(MAP_ELEMENT_NB_PUDDLE1)) ||
                !isValidInteger(properties.getProperty(MAP_ELEMENT_NB_PUDDLE2)) ||
                !isValidInteger(properties.getProperty(MAP_ELEMENT_PER_SINGLE_MUTABLE)) ||
                !isValidInteger(properties.getProperty(MAP_ELEMENT_PER_SINGLE_OBSTACLE)) ||
                !isValidInteger(properties.getProperty(MAP_ELEMENT_PER_SINGLE_DYN_PATHWAY))) {
            throw new InvalidMapConfigurationException("'" + mapPropertiesFile + "' is not a valid properties file: "
                    + "some field are missing or not integer convertible.");
        }
        int perSingleMutable = Integer.parseInt(properties.getProperty(MAP_ELEMENT_PER_SINGLE_MUTABLE));
        int perSingleObstacle = Integer.parseInt(properties.getProperty(MAP_ELEMENT_PER_SINGLE_OBSTACLE));
        int perSingleDynamicPathway = Integer.parseInt(properties.getProperty(MAP_ELEMENT_PER_SINGLE_DYN_PATHWAY));
        if (perSingleMutable + perSingleObstacle + perSingleDynamicPathway > 100) {
            throw new InvalidMapConfigurationException("'" + mapPropertiesFile + "' is not a valid properties file: "
                    + "sum of the percentage cannot exceed 100.");
        }
    }
}
