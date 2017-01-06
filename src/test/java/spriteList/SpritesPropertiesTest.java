package spriteList;

import org.assertj.core.api.WithAssertions;
import org.junit.Test;

import exceptions.InvalidConfigurationException;

public class SpritesPropertiesTest implements WithAssertions {

    private final String TEST_MAP_PROPERTIES_FILE = "/test.zelda.sprites.properties";

    @Test
    public void loadAndCheckPropertiesShouldLoadExpectedValues() throws Exception {
        SpritesProperties spritesProperties = new SpritesProperties(TEST_MAP_PROPERTIES_FILE);
        spritesProperties.loadProperties();
        spritesProperties.checkProperties();

        // check values.
        assertThat(spritesProperties.getSpritesEnemyCloakedSkeleton()).isEqualTo(1);
        assertThat(spritesProperties.getSpritesEnemyMecaAngel()).isEqualTo(2);
        assertThat(spritesProperties.getSpritesEnemyMummy()).isEqualTo(3);
    }

    @Test
    public void loadPropertiesWithNullFileShouldThrowExpectedException() throws Exception {
        SpritesProperties zeldaMapProperties = new SpritesProperties(null);
        assertThatThrownBy(zeldaMapProperties::loadProperties)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("sprites properties file not set.");
    }

    @Test
    public void loadPropertiesWithEmptyFileShouldThrowExpectedException() throws Exception {
        SpritesProperties zeldaMapProperties = new SpritesProperties("");
        assertThatThrownBy(zeldaMapProperties::loadProperties)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("sprites properties file not set.");
    }

    @Test
    public void loadPropertiesWithUnknownParameterShouldThrowExpectedException() throws Exception {
        SpritesProperties zeldaMapProperties = new SpritesProperties("badFilePath");
        assertThatThrownBy(zeldaMapProperties::loadProperties)
                .isInstanceOf(InvalidConfigurationException.class)
                .hasMessage("'badFilePath' file not found.");
    }

    @Test
    public void checkPropertiesWithNotIntegerPropertiesShouldThrowExpectedException() throws Exception {
        SpritesProperties zeldaMapProperties = new SpritesProperties(TEST_MAP_PROPERTIES_FILE);
        zeldaMapProperties.loadProperties();

        // set a field with a bad value.
        zeldaMapProperties.getProperties().setProperty(SpritesProperties.SPRITES_ENEMY_CLOAKED_SKELETON,
                "notAnIntegerConvertibleString");

        assertThatThrownBy(zeldaMapProperties::checkProperties)
                .isInstanceOf(InvalidConfigurationException.class)
                .hasMessage("'/test.zelda.sprites.properties' is not a valid properties file: "
                        + "some field are missing or not integer convertible.");
    }

    @Test
    public void checkPropertiesWithMissingPropertiesShouldThrowExpectedException() throws Exception {
        SpritesProperties zeldaMapProperties = new SpritesProperties(TEST_MAP_PROPERTIES_FILE);
        zeldaMapProperties.loadProperties();

        // remove a mandatory field.
        zeldaMapProperties.getProperties().remove(SpritesProperties.SPRITES_ENEMY_CLOAKED_SKELETON);

        assertThatThrownBy(zeldaMapProperties::checkProperties)
                .isInstanceOf(InvalidConfigurationException.class)
                .hasMessage("'/test.zelda.sprites.properties' is not a valid properties file: "
                        + "some field are missing or not integer convertible.");
    }
}