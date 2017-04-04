package sprite.nomad;

import org.assertj.core.api.WithAssertions;
import org.junit.Test;

import static sprite.nomad.EnemyType.*;

public class EnemyTypeTest implements WithAssertions {

    @Test
    public void getlabelShouldReturnTheAppropriateLabel() throws Exception {
        assertThat(getlabel(TYPE_ENEMY_ZORA).orElse("no_name")).isEqualTo("zora");
        assertThat(getlabel(TYPE_ENEMY_GREEN_SOLDIER).orElse("no_name")).isEqualTo("green_soldier");
        assertThat(getlabel(TYPE_ENEMY_RED_SPEAR_SOLDIER).orElse("no_name")).isEqualTo("red_spear_soldier");
    }
}