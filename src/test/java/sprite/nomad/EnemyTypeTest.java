package sprite.nomad;

import static sprite.nomad.EnemyType.TYPE_ENEMY_ZORA;
import static sprite.nomad.EnemyType.TYPE_ENEMY_RED_SPEAR_SOLDIER;
import static sprite.nomad.EnemyType.getlabel;

import org.assertj.core.api.WithAssertions;
import org.junit.Test;

public class EnemyTypeTest implements WithAssertions {

    @Test
    public void getlabelShouldReturnTheAppropriateLabel() throws Exception {
        assertThat(getlabel(TYPE_ENEMY_ZORA).orElse("no_name")).isEqualTo("zora");
        assertThat(getlabel(TYPE_ENEMY_RED_SPEAR_SOLDIER).orElse("no_name")).isEqualTo("red_spear_soldier");
    }
}