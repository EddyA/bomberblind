package sprite.settled;

import java.io.IOException;

import org.assertj.core.api.WithAssertions;
import org.junit.Before;
import org.junit.Test;

import images.ImagesLoader;
import sprite.SpriteType;
import utils.Tools;

public class ConclusionFlameTest implements WithAssertions {

    @Before
    public void fillImagesMatrix() throws IOException {
        ImagesLoader.fillImagesMatrix();
    }

    @Test
    public void constructorShouldSetMembersWithTheExpectedValues() throws Exception {
        ConclusionFlame conclusionFlame = new ConclusionFlame(5, 4);

        // check members value.
        assertThat(conclusionFlame.getRowIdx()).isEqualTo(5);
        assertThat(conclusionFlame.getColIdx()).isEqualTo(4);
        assertThat(conclusionFlame.getXMap()).isEqualTo(Tools.getCaseCentreAbscissa(4));
        assertThat(conclusionFlame.getYMap()).isEqualTo(Tools.getCaseBottomOrdinate(5));
        assertThat(conclusionFlame.getSpriteType()).isEqualTo(SpriteType.CONCLUSION_FLAME);
        assertThat(conclusionFlame.getRefreshTime()).isEqualTo(ConclusionFlame.REFRESH_TIME);
        assertThat(conclusionFlame.getImages()).isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.flameEndMatrixRowIdx]);
        assertThat(conclusionFlame.getNbImages()).isEqualTo(ImagesLoader.NB_FLAME_END_FRAME);
        assertThat(conclusionFlame.getNbTimes()).isEqualTo(ConclusionFlame.NB_TIMES);
    }
}