package sprite.nomad;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;

import static sprite.nomad.EnemyType.*;

class EnemyTypeTest implements WithAssertions {

    @Test
    void getLabelShouldReturnTheAppropriateLabel() {
        assertThat(getLabel(TYPE_ENEMY_ZORA).orElse("no_name")).isEqualTo("zora");
        assertThat(getLabel(TYPE_ENEMY_GREEN_SOLDIER).orElse("no_name")).isEqualTo("green_soldier");
        assertThat(getLabel(TYPE_ENEMY_RED_SPEAR_SOLDIER).orElse("no_name")).isEqualTo("red_spear_soldier");
    }
}
