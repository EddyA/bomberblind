package sprite;

import static sprite.SpriteType.BOMB;
import static sprite.SpriteType.BOMBER;
import static sprite.SpriteType.CONCLUSION_FLAME;
import static sprite.SpriteType.ENEMY;
import static sprite.SpriteType.FLAME;
import static sprite.SpriteType.getlabel;

import org.assertj.core.api.WithAssertions;
import org.junit.Test;

public class SpriteTypeTest implements WithAssertions {

    @Test
    public void getlabelShouldReturnTheAppropriateLabel() throws Exception {
        assertThat(getlabel(BOMBER).orElse("no_name")).isEqualTo("bomber");
        assertThat(getlabel(ENEMY).orElse("no_name")).isEqualTo("enemy");
        assertThat(getlabel(BOMB).orElse("no_name")).isEqualTo("bomb");
        assertThat(getlabel(FLAME).orElse("no_name")).isEqualTo("flame");
        assertThat(getlabel(CONCLUSION_FLAME).orElse("no_name")).isEqualTo("conclusion_flame");
    }
}