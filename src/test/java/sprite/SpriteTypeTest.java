package sprite;

import org.assertj.core.api.WithAssertions;
import org.junit.Test;

import static sprite.SpriteType.*;

public class SpriteTypeTest implements WithAssertions {

    @Test
    public void getlabelShouldReturnTheAppropriateLabel() throws Exception {
        assertThat(getlabel(TYPE_BOMB).orElse("no_name")).isEqualTo("bomb");
        assertThat(getlabel(TYPE_BOMBER).orElse("no_name")).isEqualTo("bomber");
        assertThat(getlabel(TYPE_BONUS_BOMB).orElse("no_name")).isEqualTo("bonus_bomb");
        assertThat(getlabel(TYPE_BREAKING_ENEMY).orElse("no_name")).isEqualTo("breaking_enemy");
        assertThat(getlabel(TYPE_FLAME).orElse("no_name")).isEqualTo("flame");
        assertThat(getlabel(TYPE_FLAME_END).orElse("no_name")).isEqualTo("flame_end");
        assertThat(getlabel(TYPE_FLYING_NOMAD).orElse("no_name")).isEqualTo("flying_nomad");
        assertThat(getlabel(TYPE_WALKING_ENEMY).orElse("no_name")).isEqualTo("walking_enemy");
    }
}