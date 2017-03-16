package map.zelda;

import com.google.common.base.Preconditions;
import exceptions.InvalidConfigurationException;
import map.MapProperties;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static utils.Tools.isValidInteger;

/**
 * This class extends {@link MapProperties}.
 * It Opens, reads and checks a zelda map properties file.
 */
public class ZeldaMapProperties extends MapProperties {

    public final static String MAP_MARGIN_VERTICAL = "map.margin.vertical";
    public final static String MAP_MARGIN_HORIZONTAL = "map.margin.horizontal";
    public final static String MAP_ELEMENT_NB_WOOD1 = "map.element.woods1.number";
    public final static String MAP_ELEMENT_NB_WOOD2 = "map.element.woods2.number";
    public final static String MAP_ELEMENT_NB_TREE1 = "map.element.tree1.number";
    public final static String MAP_ELEMENT_NB_TREE2 = "map.element.tree2.number";
    public final static String MAP_ELEMENT_NB_PUDDLE1 = "map.element.puddle1.number";
    public final static String MAP_ELEMENT_NB_PUDDLE2 = "map.element.puddle2.number";
    public final static String MAP_ELEMENT_PER_SINGLE_MUTABLE_OBSTACLE = "map.element.single.mutable.percentage";
    public final static String MAP_ELEMENT_PER_SINGLE_IMMUTABLE_OBSTACLE = "map.element.single.immutable.obstacle.percentage";
    public final static String MAP_ELEMENT_PER_SINGLE_DYNAMIC_PATHWAY = "map.element.single.dynamic.pathway.percentage";
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

    public int getMapElementPerSingleMutableObstacle() {
        return Integer.parseInt(properties.getProperty(MAP_ELEMENT_PER_SINGLE_MUTABLE_OBSTACLE));
    }

    public int getMapElementPerSingleImmutableObstacle() {
        return Integer.parseInt(properties.getProperty(MAP_ELEMENT_PER_SINGLE_IMMUTABLE_OBSTACLE));
    }

    public int getMapElementPerSingleDynamicPathway() {
        return Integer.parseInt(properties.getProperty(MAP_ELEMENT_PER_SINGLE_DYNAMIC_PATHWAY));
    }

    public int getMapBonusNbBomb() {
        return Integer.parseInt(properties.getProperty(MAP_BONUS_NB_BOMB));
    }

    public int getMapBonusNbFlame() {
        return Integer.parseInt(properties.getProperty(MAP_BONUS_NB_FLAME));
    }

    public int getMapBonusNbHeart() {
        return Integer.parseInt(properties.getProperty(MAP_BONUS_NB_HEART));
    }

    public int getMapBonusNbRoller() {
        return Integer.parseInt(properties.getProperty(MAP_BONUS_NB_ROLLER));
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
                !isValidInteger(properties.getProperty(MAP_ELEMENT_PER_SINGLE_MUTABLE_OBSTACLE)) ||
                !isValidInteger(properties.getProperty(MAP_ELEMENT_PER_SINGLE_IMMUTABLE_OBSTACLE)) ||
                !isValidInteger(properties.getProperty(MAP_ELEMENT_PER_SINGLE_DYNAMIC_PATHWAY)) ||
                !isValidInteger(properties.getProperty(MAP_BONUS_NB_BOMB)) ||
                !isValidInteger(properties.getProperty(MAP_BONUS_NB_FLAME)) ||
                !isValidInteger(properties.getProperty(MAP_BONUS_NB_HEART)) ||
                !isValidInteger(properties.getProperty(MAP_BONUS_NB_ROLLER))) {
            throw new InvalidConfigurationException("'" + propertiesFile + "' is not a valid properties file: "
                    + "some field are missing or not integer convertible.");
        }
        int perSingleMutable = Integer.parseInt(properties.getProperty(MAP_ELEMENT_PER_SINGLE_MUTABLE_OBSTACLE));
        int perSingleObstacle = Integer.parseInt(properties.getProperty(MAP_ELEMENT_PER_SINGLE_IMMUTABLE_OBSTACLE));
        int perSingleDynamicPathway = Integer.parseInt(properties.getProperty(MAP_ELEMENT_PER_SINGLE_DYNAMIC_PATHWAY));
        if (perSingleMutable + perSingleObstacle + perSingleDynamicPathway > 100) {
            throw new InvalidConfigurationException("'" + propertiesFile + "' is not a valid properties file: "
                    + "sum of the percentage cannot exceed 100.");
        }
        return this;
    }
}
