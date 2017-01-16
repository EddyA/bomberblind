package sprite.nomad;

import images.ImagesLoader;
import org.assertj.core.api.WithAssertions;
import org.junit.Before;
import org.junit.Test;
import sprite.SpriteType;

import java.io.IOException;

import static utils.Action.ACTION_WALKING;

public class MecaAngelTest implements WithAssertions {

    @Before
    public void fillImagesMatrix() throws IOException {
        ImagesLoader.fillImagesMatrix();
    }

    @Test
    public void constructorShouldSetMembersWithTheExpectedValues() throws Exception {
        MecaAngel mecaAngel = new MecaAngel(10, 20);

        // check members value.
        assertThat(mecaAngel.getxMap()).isEqualTo(10);
        assertThat(mecaAngel.getyMap()).isEqualTo(20);
        assertThat(mecaAngel.getSpriteType()).isEqualTo(SpriteType.WALKING_ENEMY);
        assertThat(mecaAngel.getRefreshTime()).isEqualTo(MecaAngel.REFRESH_TIME);
        assertThat(mecaAngel.getActingTime()).isEqualTo(MecaAngel.ACTING_TIME);
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
        assertThat(mecaAngel.getCurAction()).isEqualTo(ACTION_WALKING);
    }
}
