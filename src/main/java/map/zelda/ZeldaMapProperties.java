package map.zelda;

import com.google.common.base.Preconditions;
import exceptions.InvalidConfigurationException;
import map.MapProperties;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static utils.Tools.isNotNullAndValidInteger;
import static utils.Tools.isNullOrValidInteger;

/**
 * This class extends {@link MapProperties}.
 * It Opens, reads and checks a zelda map properties file.
 */
public class ZeldaMapProperties extends MapProperties {

    public final static String MAP_MARGIN_VERTICAL = "map.margin.vertical";
    public final static String MAP_MARGIN_HORIZONTAL = "map.margin.horizontal";

    // patterns.
    // - immutable obstacles.
    public final static String MAP_ELEMENT_NB_GREEN_TREE = "map.element.green.tree.number";
    public final static String MAP_ELEMENT_NB_ORCHARD = "map.element.orchard.number";
    public final static String MAP_ELEMENT_NB_RED_TREE = "map.element.red.tree.number";
    public final static String MAP_ELEMENT_NB_STATUE = "map.element.statue.number";
    public final static String MAP_ELEMENT_NB_TROUGH = "map.element.trough.number";
    public final static String MAP_ELEMENT_NB_YELLOW_TREE = "map.element.yellow.tree.number";

    // - mutable obstacles.
    public final static String MAP_ELEMENT_NB_PATWHAY = "map.element.pathway.number";

    // single elements.
    public final static String MAP_ELEMENT_PER_SINGLE_MUTABLE_OBSTACLE = "map.element.single.mutable.obstacle.percentage";
    public final static String MAP_ELEMENT_PER_SINGLE_IMMUTABLE_OBSTACLE = "map.element.single.immutable.obstacle.percentage";
    public final static String MAP_ELEMENT_PER_DECORATED_SINGLE_PATHWAY = "map.element.decorated.single.pathway.percentage";
    public final static String MAP_ELEMENT_PER_DYNAMIC_SINGLE_PATHWAY = "map.element.dynamic.single.pathway.percentage";

    // bonus.
    public final static String MAP_BONUS_NB_BOMB = "map.bonus.bomb.number";
    public final static String MAP_BONUS_NB_FLAME = "map.bonus.flame.number";
    public final static String MAP_BONUS_NB_HEART = "map.bonus.heart.number";
    public final static String MAP_BONUS_NB_ROLLER = "map.bonus.roller.number";

    public ZeldaMapProperties(String mapPropertiesFile) {
        super(mapPropertiesFile);
    }

    public int getMapMarginVertical() {
        return Integer.parseInt(properties.getProperty(MAP_MARGIN_VERTICAL));
    }

    public int getMapMarginHorizontal() {
        return Integer.parseInt(properties.getProperty(MAP_MARGIN_HORIZONTAL));
    }

    public int getMapElementNbGreenWood() {
        String property_value = properties.getProperty(MAP_ELEMENT_NB_GREEN_TREE);
        if (property_value != null) {
            return Integer.parseInt(property_value);
        } else {
            return 0;
        }
    }

    public int getMapElementNbOrchard() {
        String property_value = properties.getProperty(MAP_ELEMENT_NB_ORCHARD);
        if (property_value != null) {
            return Integer.parseInt(property_value);
        } else {
            return 0;
        }
    }

    public int getMapElementNbRedTree() {
        String property_value = properties.getProperty(MAP_ELEMENT_NB_RED_TREE);
        if (property_value != null) {
            return Integer.parseInt(property_value);
        } else {
            return 0;
        }
    }

    public int getMapElementNbStatue() {
        String property_value = properties.getProperty(MAP_ELEMENT_NB_STATUE);
        if (property_value != null) {
            return Integer.parseInt(property_value);
        } else {
            return 0;
        }
    }

    public int getMapElementNbTrough() {
        String property_value = properties.getProperty(MAP_ELEMENT_NB_TROUGH);
        if (property_value != null) {
            return Integer.parseInt(property_value);
        } else {
            return 0;
        }
    }

    public int getMapElementNbYellowTree() {
        String property_value = properties.getProperty(MAP_ELEMENT_NB_YELLOW_TREE);
        if (property_value != null) {
            return Integer.parseInt(property_value);
        } else {
            return 0;
        }
    }

    public int getMapElementNbPathway() {
        String property_value = properties.getProperty(MAP_ELEMENT_NB_PATWHAY);
        if (property_value != null) {
            return Integer.parseInt(property_value);
        } else {
            return 0;
        }
    }

    public int getMapElementPerSingleImmutableObstacle() {
        String property_value = properties.getProperty(MAP_ELEMENT_PER_SINGLE_IMMUTABLE_OBSTACLE);
        if (property_value != null) {
            return Integer.parseInt(property_value);
        } else {
            return 0;
        }
    }

    public int getMapElementPerSingleMutableObstacle() {
        String property_value = properties.getProperty(MAP_ELEMENT_PER_SINGLE_MUTABLE_OBSTACLE);
        if (property_value != null) {
            return Integer.parseInt(property_value);
        } else {
            return 0;
        }
    }

    public int getMapElementPerDecoratedSinglePathway() {
        String property_value = properties.getProperty(MAP_ELEMENT_PER_DECORATED_SINGLE_PATHWAY);
        if (property_value != null) {
            return Integer.parseInt(property_value);
        } else {
            return 0;
        }
    }

    public int getMapElementPerDynamicSinglePathway() {
        String property_value = properties.getProperty(MAP_ELEMENT_PER_DYNAMIC_SINGLE_PATHWAY);
        if (property_value != null) {
            return Integer.parseInt(property_value);
        } else {
            return 0;
        }
    }

    public int getMapBonusNbBomb() {
        String property_value = properties.getProperty(MAP_BONUS_NB_BOMB);
        if (property_value != null) {
            return Integer.parseInt(property_value);
        } else {
            return 0;
        }
    }

    public int getMapBonusNbFlame() {
        String property_value = properties.getProperty(MAP_BONUS_NB_FLAME);
        if (property_value != null) {
            return Integer.parseInt(property_value);
        } else {
            return 0;
        }
    }

    public int getMapBonusNbHeart() {
        String property_value = properties.getProperty(MAP_BONUS_NB_HEART);
        if (property_value != null) {
            return Integer.parseInt(property_value);
        } else {
            return 0;
        }
    }

    public int getMapBonusNbRoller() {
        String property_value = properties.getProperty(MAP_BONUS_NB_ROLLER);
        if (property_value != null) {
            return Integer.parseInt(property_value);
        } else {
            return 0;
        }
    }

    @Override
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

    @Override
    public MapProperties checkProperties() throws InvalidConfigurationException {
        if (!isNotNullAndValidInteger(properties.getProperty(MAP_SIZE_WIDTH)) ||
                !isNotNullAndValidInteger(properties.getProperty(MAP_SIZE_HEIGHT)) ||
                !isNotNullAndValidInteger(properties.getProperty(MAP_MARGIN_VERTICAL)) ||
                !isNotNullAndValidInteger(properties.getProperty(MAP_MARGIN_HORIZONTAL)) ||
                !isNullOrValidInteger(properties.getProperty(MAP_ELEMENT_NB_GREEN_TREE)) ||
                !isNullOrValidInteger(properties.getProperty(MAP_ELEMENT_NB_ORCHARD)) ||
                !isNullOrValidInteger(properties.getProperty(MAP_ELEMENT_NB_RED_TREE)) ||
                !isNullOrValidInteger(properties.getProperty(MAP_ELEMENT_NB_STATUE)) ||
                !isNullOrValidInteger(properties.getProperty(MAP_ELEMENT_NB_TROUGH)) ||
                !isNullOrValidInteger(properties.getProperty(MAP_ELEMENT_NB_YELLOW_TREE)) ||
                !isNullOrValidInteger(properties.getProperty(MAP_ELEMENT_NB_PATWHAY)) ||
                !isNullOrValidInteger(properties.getProperty(MAP_ELEMENT_PER_SINGLE_MUTABLE_OBSTACLE)) ||
                !isNullOrValidInteger(properties.getProperty(MAP_ELEMENT_PER_SINGLE_IMMUTABLE_OBSTACLE)) ||
                !isNullOrValidInteger(properties.getProperty(MAP_ELEMENT_PER_DECORATED_SINGLE_PATHWAY)) ||
                !isNullOrValidInteger(properties.getProperty(MAP_ELEMENT_PER_DYNAMIC_SINGLE_PATHWAY)) ||
                !isNullOrValidInteger(properties.getProperty(MAP_BONUS_NB_BOMB)) ||
                !isNullOrValidInteger(properties.getProperty(MAP_BONUS_NB_FLAME)) ||
                !isNullOrValidInteger(properties.getProperty(MAP_BONUS_NB_HEART)) ||
                !isNullOrValidInteger(properties.getProperty(MAP_BONUS_NB_ROLLER))) {
            throw new InvalidConfigurationException("'" + propertiesFile + "' is not a valid properties file: "
                    + "some field are missing or not integer convertible.");
        }
        int perSingleMutableObstacle = Integer.parseInt(properties.getProperty(MAP_ELEMENT_PER_SINGLE_MUTABLE_OBSTACLE));
        int perSingleImmutableObstacle = Integer.parseInt(properties.getProperty(MAP_ELEMENT_PER_SINGLE_IMMUTABLE_OBSTACLE));
        if (perSingleMutableObstacle + perSingleImmutableObstacle > 100) {
            throw new InvalidConfigurationException("'" + propertiesFile + "' is not a valid properties file: "
                    + "sum of the percentage cannot exceed 100.");
        }
        return this;
    }
}
