package sprite.nomad;

import static sprite.nomad.EnemyType.TYPE_ENEMY_CLOAKED_SKELETON;
import static sprite.nomad.EnemyType.TYPE_ENEMY_MECA_ANGEL;
import static sprite.nomad.EnemyType.TYPE_ENEMY_MINOTOR;
import static sprite.nomad.EnemyType.TYPE_ENEMY_MUMMY;
import static sprite.nomad.EnemyType.getlabel;

import org.assertj.core.api.WithAssertions;
import org.junit.Test;

public class EnemyTypeTest implements WithAssertions {

    @Test
    public void getlabelShouldReturnTheAppropriateLabel() throws Exception {
        assertThat(getlabel(TYPE_ENEMY_CLOAKED_SKELETON).orElse("no_name")).isEqualTo("cloaked_skeleton");
        assertThat(getlabel(TYPE_ENEMY_MECA_ANGEL).orElse("no_name")).isEqualTo("meca_angel");
        assertThat(getlabel(TYPE_ENEMY_MINOTOR).orElse("no_name")).isEqualTo("minotor");
        assertThat(getlabel(TYPE_ENEMY_MUMMY).orElse("no_name")).isEqualTo("mummy");
    }
}