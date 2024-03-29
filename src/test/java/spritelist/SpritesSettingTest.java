package spritelist;

import java.io.IOException;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;
import exceptions.InvalidConfigurationException;

class SpritesSettingTest implements WithAssertions {

    @SuppressWarnings("FieldCanBeLocal")
    private final String TEST_SPRITES_PROPERTIES_FILE = "/test.zelda.sprites.properties";

    @Test
    void rMapSettingShouldLoadExpectedValues() throws IOException, InvalidConfigurationException {
        SpritesProperties spritesProperties = new SpritesProperties(TEST_SPRITES_PROPERTIES_FILE);
        spritesProperties.loadProperties();
        spritesProperties.checkProperties();

        SpritesSetting spritesSetting = new SpritesSetting(spritesProperties);
        assertThat(spritesSetting.getNbZora()).isEqualTo(1);
        assertThat(spritesSetting.getNbGreenSoldier()).isEqualTo(2);
        assertThat(spritesSetting.getNbRedSpearSoldier()).isEqualTo(3);
        assertThat(spritesSetting.getBirdsArrivalTimeInterval()).isEqualTo(4);
    }
}
