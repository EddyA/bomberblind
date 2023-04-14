package spritelist;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import exceptions.InvalidConfigurationException;
import lombok.Getter;
import static utils.Tools.isNullOrValidInteger;

/**
 * Open, read and check a sprites map properties file.
 */
public class SpritesProperties {

    public static final String SPRITES_ENEMY_ZORA = "sprite.enemy.zora";
    public static final String SPRITES_ENEMY_GREEN_SOLDIER = "sprite.enemy.green.soldier";
    public static final String SPRITES_ENEMY_RED_SPEAR_SOLDIER = "sprite.enemy.red.spear.soldier";

    public static final String SPRITES_BIRDS_ARRIVAL_TIME_INTERVAL = "sprite.birds.arrival.time.interval";

    private final String propertiesFile;

    @Getter
    private final Properties properties = new Properties();

    public SpritesProperties(String propertiesFile) {
        this.propertiesFile = propertiesFile;
    }

    public int getSpritesEnemyZora() {
        String propertyValue = properties.getProperty(SPRITES_ENEMY_ZORA);
        if (propertyValue != null) {
            return Integer.parseInt(propertyValue);
        } else {
            return 0;
        }
    }

    public int getSpritesEnemyGreenSoldier() {
        String propertyValue = properties.getProperty(SPRITES_ENEMY_GREEN_SOLDIER);
        if (propertyValue != null) {
            return Integer.parseInt(propertyValue);
        } else {
            return 0;
        }
    }

    public int getSpritesEnemyRedSpearSoldier() {
        String propertyValue = properties.getProperty(SPRITES_ENEMY_RED_SPEAR_SOLDIER);
        if (propertyValue != null) {
            return Integer.parseInt(propertyValue);
        } else {
            return 0;
        }
    }

    public int getSpritesBirdsArrivalTimeInterval() {
        String propertyValue = properties.getProperty(SPRITES_BIRDS_ARRIVAL_TIME_INTERVAL);
        if (propertyValue != null) {
            return Integer.parseInt(propertyValue);
        } else {
            return 0;
        }
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
        assert ((propertiesFile != null) && !propertiesFile.isEmpty()) : "sprites properties file not set.";
        URL configurationFile = SpritesProperties.class.getResource(propertiesFile);
        if (configurationFile == null) {
            throw new InvalidConfigurationException("'" + propertiesFile + "' file not found.");
        }
        try (InputStream inputStream = configurationFile.openStream()) {
            properties.load(inputStream);
        }
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
