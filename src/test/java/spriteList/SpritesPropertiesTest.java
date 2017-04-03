package spriteList;

import exceptions.InvalidConfigurationException;
import org.assertj.core.api.WithAssertions;
import org.junit.Test;

public class SpritesPropertiesTest implements WithAssertions {

    private final String TEST_SPRITES_PROPERTIES_FILE = "/test.zelda.sprites.properties";

    @Test
    public void loadAndCheckPropertiesShouldLoadExpectedValues() throws Exception {
        SpritesProperties spritesProperties = new SpritesProperties(TEST_SPRITES_PROPERTIES_FILE);
        spritesProperties.loadProperties();
        spritesProperties.checkProperties();

        // check values.
        assertThat(spritesProperties.getSpritesEnemyzora()).isEqualTo(1);
        assertThat(spritesProperties.getSpritesEnemyGreenSoldier()).isEqualTo(2);
        assertThat(spritesProperties.getSpritesEnemyRedSpearSoldier()).isEqualTo(3);
        assertThat(spritesProperties.getSpritesBirdsArrivalTimeInterval()).isEqualTo(4);
    }

    @Test
    public void valueOfOptionnalPropertiesShouldBeZeroWhenNotSet() throws Exception {
        SpritesProperties spritesProperties = new SpritesProperties(null);

        // check values of optionnal properties.
        assertThat(spritesProperties.getSpritesEnemyzora()).isEqualTo(0);
        assertThat(spritesProperties.getSpritesEnemyGreenSoldier()).isEqualTo(0);
        assertThat(spritesProperties.getSpritesEnemyRedSpearSoldier()).isEqualTo(0);
        assertThat(spritesProperties.getSpritesBirdsArrivalTimeInterval()).isEqualTo(0);
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
        SpritesProperties zeldaMapProperties = new SpritesProperties(TEST_SPRITES_PROPERTIES_FILE);
        zeldaMapProperties.loadProperties();

        // set a field with a bad value.
        zeldaMapProperties.getProperties().setProperty(SpritesProperties.SPRITES_ENEMY_ZORA,
                "notAnIntegerConvertibleString");

        assertThatThrownBy(zeldaMapProperties::checkProperties)
                .isInstanceOf(InvalidConfigurationException.class)
                .hasMessage("'/test.zelda.sprites.properties' is not a valid properties file: "
                        + "some field are missing or not integer convertible.");
    }
}