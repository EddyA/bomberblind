package sprite;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;

import static sprite.SpriteType.*;

class SpriteTypeTest implements WithAssertions {

    @Test
    void getLabelShouldReturnTheAppropriateLabel() {
        assertThat(getLabel(TYPE_SPRITE_BOMB).orElse("no_name")).isEqualTo("bomb");
        assertThat(getLabel(TYPE_SPRITE_BOMBER).orElse("no_name")).isEqualTo("bomber");
        assertThat(getLabel(TYPE_SPRITE_BONUS).orElse("no_name")).isEqualTo("bonus");
        assertThat(getLabel(TYPE_SPRITE_BREAKING_ENEMY).orElse("no_name")).isEqualTo("breaking_enemy");
        assertThat(getLabel(TYPE_SPRITE_EXPLORING_ENEMY).orElse("no_name")).isEqualTo("exploring_enemy");
        assertThat(getLabel(TYPE_SPRITE_FLAME).orElse("no_name")).isEqualTo("flame");
        assertThat(getLabel(TYPE_SPRITE_FLAME_END).orElse("no_name")).isEqualTo("flame_end");
        assertThat(getLabel(TYPE_SPRITE_FLYING_NOMAD).orElse("no_name")).isEqualTo("flying_nomad");
        assertThat(getLabel(TYPE_SPRITE_WALKING_ENEMY).orElse("no_name")).isEqualTo("walking_enemy");
    }
}
