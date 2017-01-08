package sprite;

import static sprite.SpriteType.BOMB;
import static sprite.SpriteType.BOMBER;
import static sprite.SpriteType.FLAME_END;
import static sprite.SpriteType.ENEMY_A;
import static sprite.SpriteType.FLAME;
import static sprite.SpriteType.getlabel;

import org.assertj.core.api.WithAssertions;
import org.junit.Test;

public class SpriteTypeTest implements WithAssertions {

    @Test
    public void getlabelShouldReturnTheAppropriateLabel() throws Exception {
        assertThat(getlabel(BOMBER).orElse("no_name")).isEqualTo("bomber");
        assertThat(getlabel(ENEMY_A).orElse("no_name")).isEqualTo("enemy");
        assertThat(getlabel(BOMB).orElse("no_name")).isEqualTo("bomb");
        assertThat(getlabel(FLAME).orElse("no_name")).isEqualTo("flame");
        assertThat(getlabel(FLAME_END).orElse("no_name")).isEqualTo("flame_end");
    }
}