package spriteList;

import com.google.common.base.Preconditions;
import exceptions.InvalidConfigurationException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import static utils.Tools.isNotNullAndValidInteger;

/**
 * Open, read and check a sprites map properties file.
 */

public class SpritesProperties {

    public final static String SPRITES_ENEMY_CLOAKED_SKELETON = "sprite.enemy.cloaked.skeleton";
    public final static String SPRITES_ENEMY_MECA_ANGEL = "sprite.enemy.meca.angel";
    public final static String SPRITES_ENEMY_MUMMY = "sprite.enemy.mummy";

    public final static String SPRITES_ENEMY_MINOTOR = "sprite.enemy.minotor";

    public final static String SPRITES_BIRDS_ARRIVAL_TIME_INTERVAL = "sprite.birds.arrival.time.interval";

    private final String propertiesFile;
    private final Properties properties = new Properties();

    public SpritesProperties(String propertiesFile) {
        this.propertiesFile = propertiesFile;
    }

    public int getSpritesEnemyCloakedSkeleton() {
        return Integer.parseInt(properties.getProperty(SPRITES_ENEMY_CLOAKED_SKELETON));
    }

    public int getSpritesEnemyMecaAngel() {
        return Integer.parseInt(properties.getProperty(SPRITES_ENEMY_MECA_ANGEL));
    }

    public int getSpritesEnemyMummy() {
        return Integer.parseInt(properties.getProperty(SPRITES_ENEMY_MUMMY));
    }

    public int getSpritesEnemyMinotor() {
        return Integer.parseInt(properties.getProperty(SPRITES_ENEMY_MINOTOR));
    }

    public int getSpritesBirdsArrivalTimeInterval() {
        return Integer.parseInt(properties.getProperty(SPRITES_BIRDS_ARRIVAL_TIME_INTERVAL));
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
        if (!isNotNullAndValidInteger(properties.getProperty(SPRITES_ENEMY_CLOAKED_SKELETON)) ||
                !isNotNullAndValidInteger(properties.getProperty(SPRITES_ENEMY_MECA_ANGEL)) ||
                !isNotNullAndValidInteger(properties.getProperty(SPRITES_ENEMY_MUMMY)) ||
                !isNotNullAndValidInteger(properties.getProperty(SPRITES_ENEMY_MINOTOR)) ||
                !isNotNullAndValidInteger(properties.getProperty(SPRITES_BIRDS_ARRIVAL_TIME_INTERVAL))) {
            throw new InvalidConfigurationException("'" + propertiesFile + "' is not a valid properties file: "
                    + "some field are missing or not integer convertible.");
        }
        return this;
    }
}
