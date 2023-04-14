package map.zelda;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import exceptions.InvalidConfigurationException;
import map.MapProperties;
import static utils.Tools.isNotNullAndValidInteger;
import static utils.Tools.isNullOrValidInteger;

/**
 * This class extends {@link MapProperties}. It Opens, reads and checks a zelda map properties file.
 */
public class ZeldaMapProperties extends MapProperties {

    public static final String MAP_MARGIN_VERTICAL = "map.margin.vertical";
    public static final String MAP_MARGIN_HORIZONTAL = "map.margin.horizontal";

    // patterns.
    // - immutable obstacles.
    public static final String MAP_ELEMENT_NB_GREEN_TREE = "map.element.green.tree.number";
    public static final String MAP_ELEMENT_NB_ORCHARD = "map.element.orchard.number";
    public static final String MAP_ELEMENT_NB_RED_TREE = "map.element.red.tree.number";
    public static final String MAP_ELEMENT_NB_STATUE = "map.element.statue.number";
    public static final String MAP_ELEMENT_NB_TROUGH = "map.element.trough.number";
    public static final String MAP_ELEMENT_NB_YELLOW_TREE = "map.element.yellow.tree.number";

    // - mutable obstacles.
    public static final String MAP_ELEMENT_NB_PATHWAY = "map.element.pathway.number";

    // single elements.
    public static final String MAP_ELEMENT_PER_SINGLE_MUTABLE_OBSTACLE = "map.element.single.mutable.obstacle.percentage";
    public static final String MAP_ELEMENT_PER_SINGLE_IMMUTABLE_OBSTACLE = "map.element.single.immutable.obstacle.percentage";
    public static final String MAP_ELEMENT_PER_DECORATED_SINGLE_PATHWAY = "map.element.decorated.single.pathway.percentage";
    public static final String MAP_ELEMENT_PER_DYNAMIC_SINGLE_PATHWAY = "map.element.dynamic.single.pathway.percentage";

    // bonus.
    public static final String MAP_BONUS_NB_BOMB = "map.bonus.bomb.number";
    public static final String MAP_BONUS_NB_FLAME = "map.bonus.flame.number";
    public static final String MAP_BONUS_NB_HEART = "map.bonus.heart.number";
    public static final String MAP_BONUS_NB_ROLLER = "map.bonus.roller.number";

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
        String propertyValue = properties.getProperty(MAP_ELEMENT_NB_GREEN_TREE);
        if (propertyValue != null) {
            return Integer.parseInt(propertyValue);
        } else {
            return 0;
        }
    }

    public int getMapElementNbOrchard() {
        String propertyValue = properties.getProperty(MAP_ELEMENT_NB_ORCHARD);
        if (propertyValue != null) {
            return Integer.parseInt(propertyValue);
        } else {
            return 0;
        }
    }

    public int getMapElementNbRedTree() {
        String propertyValue = properties.getProperty(MAP_ELEMENT_NB_RED_TREE);
        if (propertyValue != null) {
            return Integer.parseInt(propertyValue);
        } else {
            return 0;
        }
    }

    public int getMapElementNbStatue() {
        String propertyValue = properties.getProperty(MAP_ELEMENT_NB_STATUE);
        if (propertyValue != null) {
            return Integer.parseInt(propertyValue);
        } else {
            return 0;
        }
    }

    public int getMapElementNbTrough() {
        String propertyValue = properties.getProperty(MAP_ELEMENT_NB_TROUGH);
        if (propertyValue != null) {
            return Integer.parseInt(propertyValue);
        } else {
            return 0;
        }
    }

    public int getMapElementNbYellowTree() {
        String propertyValue = properties.getProperty(MAP_ELEMENT_NB_YELLOW_TREE);
        if (propertyValue != null) {
            return Integer.parseInt(propertyValue);
        } else {
            return 0;
        }
    }

    public int getMapElementNbPathway() {
        String propertyValue = properties.getProperty(MAP_ELEMENT_NB_PATHWAY);
        if (propertyValue != null) {
            return Integer.parseInt(propertyValue);
        } else {
            return 0;
        }
    }

    public int getMapElementPerSingleImmutableObstacle() {
        String propertyValue = properties.getProperty(MAP_ELEMENT_PER_SINGLE_IMMUTABLE_OBSTACLE);
        if (propertyValue != null) {
            return Integer.parseInt(propertyValue);
        } else {
            return 0;
        }
    }

    public int getMapElementPerSingleMutableObstacle() {
        String propertyValue = properties.getProperty(MAP_ELEMENT_PER_SINGLE_MUTABLE_OBSTACLE);
        if (propertyValue != null) {
            return Integer.parseInt(propertyValue);
        } else {
            return 0;
        }
    }

    public int getMapElementPerDecoratedSinglePathway() {
        String propertyValue = properties.getProperty(MAP_ELEMENT_PER_DECORATED_SINGLE_PATHWAY);
        if (propertyValue != null) {
            return Integer.parseInt(propertyValue);
        } else {
            return 0;
        }
    }

    public int getMapElementPerDynamicSinglePathway() {
        String propertyValue = properties.getProperty(MAP_ELEMENT_PER_DYNAMIC_SINGLE_PATHWAY);
        if (propertyValue != null) {
            return Integer.parseInt(propertyValue);
        } else {
            return 0;
        }
    }

    public int getMapBonusNbBomb() {
        String propertyValue = properties.getProperty(MAP_BONUS_NB_BOMB);
        if (propertyValue != null) {
            return Integer.parseInt(propertyValue);
        } else {
            return 0;
        }
    }

    public int getMapBonusNbFlame() {
        String propertyValue = properties.getProperty(MAP_BONUS_NB_FLAME);
        if (propertyValue != null) {
            return Integer.parseInt(propertyValue);
        } else {
            return 0;
        }
    }

    public int getMapBonusNbHeart() {
        String propertyValue = properties.getProperty(MAP_BONUS_NB_HEART);
        if (propertyValue != null) {
            return Integer.parseInt(propertyValue);
        } else {
            return 0;
        }
    }

    public int getMapBonusNbRoller() {
        String propertyValue = properties.getProperty(MAP_BONUS_NB_ROLLER);
        if (propertyValue != null) {
            return Integer.parseInt(propertyValue);
        } else {
            return 0;
        }
    }

    @Override
    public MapProperties loadProperties()
            throws IllegalArgumentException, IOException, InvalidConfigurationException {
        assert ((propertiesFile != null) && !propertiesFile.isEmpty()) : "map properties file not set.";
        URL configurationFile = ZeldaMapProperties.class.getResource(propertiesFile);
        if (configurationFile == null) {
            throw new InvalidConfigurationException("'" + propertiesFile + "' file not found.");
        }
        try (InputStream inputStream = configurationFile.openStream()) {
            properties.load(inputStream);
        }
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
            !isNullOrValidInteger(properties.getProperty(MAP_ELEMENT_NB_PATHWAY)) ||
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
        if (getMapElementPerSingleMutableObstacle() + getMapElementPerSingleImmutableObstacle() > 100) {
            throw new InvalidConfigurationException("'" + propertiesFile + "' is not a valid properties file: "
                + "sum of the percentage cannot exceed 100.");
        }
        return this;
    }
}
