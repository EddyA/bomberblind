package map.zelda;

import static utils.Tools.isValidInteger;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import com.google.common.base.Preconditions;

import exceptions.InvalidConfigurationException;
import map.MapProperties;

/**
 * This class extends {@link MapProperties}.
 * It Opens, reads and checks a zelda map properties file.
 */
public class ZeldaMapProperties extends MapProperties {

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

    public ZeldaMapProperties(String mapPropertiesFile) {
        super(mapPropertiesFile);
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

    public MapProperties loadProperties()
            throws IllegalArgumentException, IOException, InvalidConfigurationException {
        Preconditions.checkArgument((propertiesFile != null) &&
                !propertiesFile.isEmpty(), "map properties file not set.");
        URL configurationFile = ZeldaMapProperties.class.getResource(propertiesFile);
        if (configurationFile == null) {
            throw new InvalidConfigurationException("'" + propertiesFile + "' file not found.");
        }
        InputStream inputStream = configurationFile.openStream();
        properties.load(inputStream);
        return this;
    }

    public MapProperties checkProperties() throws InvalidConfigurationException {
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
            throw new InvalidConfigurationException("'" + propertiesFile + "' is not a valid properties file: "
                    + "some field are missing or not integer convertible.");
        }
        int perSingleMutable = Integer.parseInt(properties.getProperty(MAP_ELEMENT_PER_SINGLE_MUTABLE));
        int perSingleObstacle = Integer.parseInt(properties.getProperty(MAP_ELEMENT_PER_SINGLE_OBSTACLE));
        int perSingleDynamicPathway = Integer.parseInt(properties.getProperty(MAP_ELEMENT_PER_SINGLE_DYN_PATHWAY));
        if (perSingleMutable + perSingleObstacle + perSingleDynamicPathway > 100) {
            throw new InvalidConfigurationException("'" + propertiesFile + "' is not a valid properties file: "
                    + "sum of the percentage cannot exceed 100.");
        }
        return this;
    }
}
