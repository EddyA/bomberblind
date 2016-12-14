package spriteList;

import static utils.Tools.isValidInteger;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import com.google.common.base.Preconditions;

import exceptions.InvalidConfigurationException;

/**
 * Open, read and check a sprites map properties file.
 */

public class SpritesProperties {

    final static String SPRITES_ENEMY_CLOAKED_SKELETON = "sprite.enemy.cloaked.skeleton";
    final private static String SPRITES_ENEMY_MECA_ANGEL = "sprite.enemy.meca.angel";
    final private static String SPRITES_ENEMY_MUMMY = "sprite.enemy.mummy";

    private final String propertiesFile;
    protected Properties properties = new Properties();

    public SpritesProperties(String propertiesFile) {
        this.propertiesFile = propertiesFile;
    }

    int getSpritesEnemyCloakedSkeleton() {
        return Integer.parseInt(properties.getProperty(SPRITES_ENEMY_CLOAKED_SKELETON));
    }

    int getSpritesEnemyMecaAngel() {
        return Integer.parseInt(properties.getProperty(SPRITES_ENEMY_MECA_ANGEL));
    }

    int getSpritesEnemyMummy() {
        return Integer.parseInt(properties.getProperty(SPRITES_ENEMY_MUMMY));
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
        if (!isValidInteger(properties.getProperty(SPRITES_ENEMY_CLOAKED_SKELETON)) ||
                !isValidInteger(properties.getProperty(SPRITES_ENEMY_MECA_ANGEL)) ||
                !isValidInteger(properties.getProperty(SPRITES_ENEMY_MUMMY))) {
            throw new InvalidConfigurationException("'" + propertiesFile + "' is not a valid properties file: "
                    + "some field are missing or not integer convertible.");
        }
        return this;
    }
}
