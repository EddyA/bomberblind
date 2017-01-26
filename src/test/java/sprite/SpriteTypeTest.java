package sprite;

import static sprite.SpriteType.TYPE_BOMB;
import static sprite.SpriteType.TYPE_BOMBER;
import static sprite.SpriteType.TYPE_BREAKING_ENEMY;
import static sprite.SpriteType.TYPE_FLAME_END;
import static sprite.SpriteType.TYPE_FLYING_NOMAD;
import static sprite.SpriteType.TYPE_WALKING_ENEMY;
import static sprite.SpriteType.TYPE_FLAME;
import static sprite.SpriteType.getlabel;

import org.assertj.core.api.WithAssertions;
import org.junit.Test;

public class SpriteTypeTest implements WithAssertions {

    @Test
    public void getlabelShouldReturnTheAppropriateLabel() throws Exception {
        assertThat(getlabel(TYPE_BOMB).orElse("no_name")).isEqualTo("bomb");
        assertThat(getlabel(TYPE_BOMBER).orElse("no_name")).isEqualTo("bomber");
        assertThat(getlabel(TYPE_BREAKING_ENEMY).orElse("no_name")).isEqualTo("breaking_enemy");
        assertThat(getlabel(TYPE_FLAME).orElse("no_name")).isEqualTo("flame");
        assertThat(getlabel(TYPE_FLAME_END).orElse("no_name")).isEqualTo("flame_end");
        assertThat(getlabel(TYPE_FLYING_NOMAD).orElse("no_name")).isEqualTo("flying_nomad");
        assertThat(getlabel(TYPE_WALKING_ENEMY).orElse("no_name")).isEqualTo("walking_enemy");
    }
}