package sprite.nomad;

import static sprite.nomad.EnemyType.CLOAKED_SKELETON;
import static sprite.nomad.EnemyType.MECA_ANGEL;
import static sprite.nomad.EnemyType.MINOTOR;
import static sprite.nomad.EnemyType.MUMMY;
import static sprite.nomad.EnemyType.getlabel;

import org.assertj.core.api.WithAssertions;
import org.junit.Test;

public class EnemyTypeTest implements WithAssertions {

    @Test
    public void getlabelShouldReturnTheAppropriateLabel() throws Exception {
        assertThat(getlabel(CLOAKED_SKELETON).orElse("no_name")).isEqualTo("cloaked_skeleton");
        assertThat(getlabel(MECA_ANGEL).orElse("no_name")).isEqualTo("meca_angel");
        assertThat(getlabel(MINOTOR).orElse("no_name")).isEqualTo("minotor");
        assertThat(getlabel(MUMMY).orElse("no_name")).isEqualTo("mummy");
    }
}