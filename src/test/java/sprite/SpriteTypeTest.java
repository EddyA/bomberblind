package sprite;

import org.assertj.core.api.WithAssertions;
import org.junit.Test;

import static sprite.SpriteType.*;

public class SpriteTypeTest implements WithAssertions {

    @Test
    public void getlabelShouldReturnTheAppropriateLabel() throws Exception {
        assertThat(getlabel(TYPE_SPRITE_BOMB).orElse("no_name")).isEqualTo("bomb");
        assertThat(getlabel(TYPE_SPRITE_BOMBER).orElse("no_name")).isEqualTo("bomber");
        assertThat(getlabel(TYPE_SPRITE_BONUS).orElse("no_name")).isEqualTo("bonus");
        assertThat(getlabel(TYPE_SPRITE_BREAKING_ENEMY).orElse("no_name")).isEqualTo("breaking_enemy");
        assertThat(getlabel(TYPE_SPRITE_EXPLORING_ENEMY).orElse("no_name")).isEqualTo("exploring_enemy");
        assertThat(getlabel(TYPE_SPRITE_FLAME).orElse("no_name")).isEqualTo("flame");
        assertThat(getlabel(TYPE_SPRITE_FLAME_END).orElse("no_name")).isEqualTo("flame_end");
        assertThat(getlabel(TYPE_SPRITE_FLYING_NOMAD).orElse("no_name")).isEqualTo("flying_nomad");
        assertThat(getlabel(TYPE_SPRITE_WALKING_ENEMY).orElse("no_name")).isEqualTo("walking_enemy");
    }
}