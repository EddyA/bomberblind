package spritelist;

import java.io.IOException;
import exceptions.InvalidConfigurationException;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;

class SpritesPropertiesTest implements WithAssertions {

    private final String TEST_SPRITES_PROPERTIES_FILE = "/test.zelda.sprites.properties";

    @Test
    void loadAndCheckPropertiesShouldLoadExpectedValues() throws IOException, InvalidConfigurationException {
        SpritesProperties spritesProperties = new SpritesProperties(TEST_SPRITES_PROPERTIES_FILE);
        spritesProperties.loadProperties();
        spritesProperties.checkProperties();

        // check values.
        assertThat(spritesProperties.getSpritesEnemyZora()).isEqualTo(1);
        assertThat(spritesProperties.getSpritesEnemyGreenSoldier()).isEqualTo(2);
        assertThat(spritesProperties.getSpritesEnemyRedSpearSoldier()).isEqualTo(3);
        assertThat(spritesProperties.getSpritesBirdsArrivalTimeInterval()).isEqualTo(4);
    }

    @Test
    void valueOfOptionnalPropertiesShouldBeZeroWhenNotSet() {
        SpritesProperties spritesProperties = new SpritesProperties(null);

        // check values of optionnal properties.
        assertThat(spritesProperties.getSpritesEnemyZora()).isEqualTo(0);
        assertThat(spritesProperties.getSpritesEnemyGreenSoldier()).isEqualTo(0);
        assertThat(spritesProperties.getSpritesEnemyRedSpearSoldier()).isEqualTo(0);
        assertThat(spritesProperties.getSpritesBirdsArrivalTimeInterval()).isEqualTo(0);
    }

    @Test
    void loadPropertiesWithNullFileShouldThrowExpectedException() {
        SpritesProperties zeldaMapProperties = new SpritesProperties(null);
        assertThatThrownBy(zeldaMapProperties::loadProperties)
                .isInstanceOf(AssertionError.class)
                .hasMessage("sprites properties file not set.");
    }

    @Test
    void loadPropertiesWithEmptyFileShouldThrowExpectedException() {
        SpritesProperties zeldaMapProperties = new SpritesProperties("");
        assertThatThrownBy(zeldaMapProperties::loadProperties)
                .isInstanceOf(AssertionError.class)
                .hasMessage("sprites properties file not set.");
    }

    @Test
    void loadPropertiesWithUnknownParameterShouldThrowExpectedException() {
        SpritesProperties zeldaMapProperties = new SpritesProperties("badFilePath");
        assertThatThrownBy(zeldaMapProperties::loadProperties)
                .isInstanceOf(InvalidConfigurationException.class)
                .hasMessage("'badFilePath' file not found.");
    }

    @Test
    void checkPropertiesWithNotIntegerPropertiesShouldThrowExpectedException()
        throws IOException, InvalidConfigurationException {
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
