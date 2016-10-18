package sprite.nomad;

import static sprite.nomad.abstracts.Enemy.Action.ACTION_WALKING;

import java.io.IOException;

import org.assertj.core.api.WithAssertions;
import org.junit.Before;
import org.junit.Test;

import images.ImagesLoader;

public class MummyTest implements WithAssertions {

    @Before
    public void fillImagesMatrix() throws IOException {
        ImagesLoader.fillImagesMatrix();
    }

    @Test
    public void constructorShouldSetMembersWithTheExpectedValues() throws Exception {
        Mummy mummy = new Mummy(15, 30);

        // check members value.
        assertThat(mummy.getXMap()).isEqualTo(15);
        assertThat(mummy.getYMap()).isEqualTo(30);
        assertThat(mummy.getCurAction()).isEqualTo(ACTION_WALKING);
        assertThat(mummy.getLastAction()).isEqualTo(ACTION_WALKING);
        assertThat(mummy.getDeathImages())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.mummyDeathMatrixRowIdx]);
        assertThat(mummy.getNbDeathFrame()).isEqualTo(ImagesLoader.NB_MUMMY_DEATH_FRAME);
        assertThat(mummy.getWalkBackImages())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.mummyWalkBackMatrixRowIdx]);
        assertThat(mummy.getWalkFrontImages())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.mummyWalkFrontMatrixRowIdx]);
        assertThat(mummy.getWalkLeftImages())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.mummyWalkLeftMatrixRowIdx]);
        assertThat(mummy.getWalkRightImages())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.mummyWalkRightMatrixRowIdx]);
        assertThat(mummy.getNbWalkFrame()).isEqualTo(ImagesLoader.NB_MUMMY_WALK_FRAME);
        assertThat(mummy.getRefreshTime()).isEqualTo(Mummy.REFRESH_TIME);
        assertThat(mummy.getMoveTime()).isEqualTo(Mummy.MOVING_TIME);
    }
}