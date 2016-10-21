package spriteList;

import org.assertj.core.api.WithAssertions;
import org.junit.Test;

public class SpritesSettingTest implements WithAssertions {

    final private String TEST_MAP_PROPERTIES_FILE = "/test.zelda.sprites.properties";

    @Test
    public void rMapSettingShouldLoadExpectedValues() throws Exception {
        SpritesProperties spritesProperties = new SpritesProperties(TEST_MAP_PROPERTIES_FILE);
        spritesProperties.loadProperties();
        spritesProperties.checkProperties();

        SpritesSetting spritesSetting = new SpritesSetting(spritesProperties);
        assertThat(spritesSetting.getNbCloakedSkeleton()).isEqualTo(1);
        assertThat(spritesSetting.getNbMecaAngel()).isEqualTo(2);
        assertThat(spritesSetting.getNbMummy()).isEqualTo(3);
    }
}