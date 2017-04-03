package spriteList;

import com.google.common.base.Preconditions;
import exceptions.InvalidConfigurationException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import static utils.Tools.isNullOrValidInteger;

/**
 * Open, read and check a sprites map properties file.
 */

public class SpritesProperties {

    public final static String SPRITES_ENEMY_ZORA = "sprite.enemy.zora";
    public final static String SPRITES_ENEMY_GREEN_SOLDIER = "sprite.enemy.green.soldier";
    public final static String SPRITES_ENEMY_RED_SPEAR_SOLDIER = "sprite.enemy.red.spear.soldier";

    public final static String SPRITES_BIRDS_ARRIVAL_TIME_INTERVAL = "sprite.birds.arrival.time.interval";

    private final String propertiesFile;
    private final Properties properties = new Properties();

    public SpritesProperties(String propertiesFile) {
        this.propertiesFile = propertiesFile;
    }

    public int getSpritesEnemyzora() {
        String property_value = properties.getProperty(SPRITES_ENEMY_ZORA);
        if (property_value != null) {
            return Integer.parseInt(property_value);
        } else {
            return 0;
        }
    }

    public int getSpritesEnemyGreenSoldier() {
        String property_value = properties.getProperty(SPRITES_ENEMY_GREEN_SOLDIER);
        if (property_value != null) {
            return Integer.parseInt(property_value);
        } else {
            return 0;
        }
    }

    public int getSpritesEnemyRedSpearSoldier() {
        String property_value = properties.getProperty(SPRITES_ENEMY_RED_SPEAR_SOLDIER);
        if (property_value != null) {
            return Integer.parseInt(property_value);
        } else {
            return 0;
        }
    }

    public int getSpritesBirdsArrivalTimeInterval() {
        String property_value = properties.getProperty(SPRITES_BIRDS_ARRIVAL_TIME_INTERVAL);
        if (property_value != null) {
            return Integer.parseInt(property_value);
        } else {
            return 0;
        }
    }

    public Properties getProperties() {
        return properties;
    }

    /**
     * Load all properties from the sprites file properties.
     *
     * @return the current MapProperties
     * @throws IllegalArgumentException if the properties file is null or empty
     * @throws InvalidConfigurationException if the properties file cannot be found
     * @throws IOException if the properties file cannot be opened
     */
    public SpritesProperties loadProperties()
            throws IllegalArgumentException, IOException, InvalidConfigurationException {
        Preconditions.checkArgument((propertiesFile != null) &&
                !propertiesFile.isEmpty(), "sprites properties file not set.");
        URL configurationFile = SpritesProperties.class.getResource(propertiesFile);
        if (configurationFile == null) {
            throw new InvalidConfigurationException("'" + propertiesFile + "' file not found.");
        }
        InputStream inputStream = configurationFile.openStream();
        properties.load(inputStream);
        return this;
    }

    /**
     * Check all properties of the map file properties.
     *
     * @return the current MapProperties
     * @throws InvalidConfigurationException as soon as a propertie is badly set
     */
    public SpritesProperties checkProperties() throws InvalidConfigurationException {
        if (!isNullOrValidInteger(properties.getProperty(SPRITES_ENEMY_ZORA)) ||
                !isNullOrValidInteger(properties.getProperty(SPRITES_ENEMY_GREEN_SOLDIER)) ||
                !isNullOrValidInteger(properties.getProperty(SPRITES_ENEMY_RED_SPEAR_SOLDIER)) ||
                !isNullOrValidInteger(properties.getProperty(SPRITES_BIRDS_ARRIVAL_TIME_INTERVAL))) {
            throw new InvalidConfigurationException("'" + propertiesFile + "' is not a valid properties file: "
                    + "some field are missing or not integer convertible.");
        }
        return this;
    }
}
