package sprite.nomad;

import static sprite.nomad.abstracts.Enemy.Action.ACTION_WALKING;

import java.io.IOException;

import org.assertj.core.api.WithAssertions;
import org.junit.Before;
import org.junit.Test;

import images.ImagesLoader;

public class MecaAngelTest implements WithAssertions {

    @Before
    public void fillImagesMatrix() throws IOException {
        ImagesLoader.fillImagesMatrix();
    }

    @Test
    public void constructorShouldSetMembersWithTheExpectedValues() throws Exception {
        MecaAngel mecaAngel = new MecaAngel(10, 20);

        // check members value.
        assertThat(mecaAngel.getXMap()).isEqualTo(10);
        assertThat(mecaAngel.getYMap()).isEqualTo(20);
        assertThat(mecaAngel.getCurAction()).isEqualTo(ACTION_WALKING);
        assertThat(mecaAngel.getLastAction()).isEqualTo(ACTION_WALKING);
        assertThat(mecaAngel.getDeathImages()).
                isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.mecaAngelDeathMatrixRowIdx]);
        assertThat(mecaAngel.getNbDeathFrame()).isEqualTo(ImagesLoader.NB_MECA_ANGEL_DEATH_FRAME);
        assertThat(mecaAngel.getWalkBackImages()).
                isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.mecaAngelWalkBackMatrixRowIdx]);
        assertThat(mecaAngel.getWalkFrontImages()).
                isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.mecaAngelWalkFrontMatrixRowIdx]);
        assertThat(mecaAngel.getWalkLeftImages()).
                isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.mecaAngelWalkLeftMatrixRowIdx]);
        assertThat(mecaAngel.getWalkRightImages()).
                isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.mecaAngelWalkRightMatrixRowIdx]);
        assertThat(mecaAngel.getNbWalkFrame()).isEqualTo(ImagesLoader.NB_MECA_ANGEL_WALK_FRAME);
        assertThat(mecaAngel.getRefreshTime()).isEqualTo(MecaAngel.REFRESH_TIME);
        assertThat(mecaAngel.getMoveTime()).isEqualTo(MecaAngel.MOVING_TIME);
    }
}
