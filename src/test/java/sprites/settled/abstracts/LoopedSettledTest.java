package sprites.settled.abstracts;

import java.io.IOException;

import org.assertj.core.api.WithAssertions;
import org.junit.Before;
import org.junit.Test;

import images.ImagesLoader;
import sprites.settled.ConclusionFlame;
import utils.Tools;

public class LoopedSettledTest implements WithAssertions {

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
        assertThat(conclusionFlame.getImages()).isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.flameEndMatrixRowIdx]);
        assertThat(conclusionFlame.getNbImages()).isEqualTo(ImagesLoader.NB_FLAME_END_FRAME);
        assertThat(conclusionFlame.getRefreshTime()).isEqualTo(ConclusionFlame.REFRESH_TIME);
        assertThat(conclusionFlame.getMaxNbLoop()).isEqualTo(ConclusionFlame.NB_TIMES);
    }

    @Test
    public void isFinishedShouldReturnFalse() throws Exception {
        ConclusionFlame conclusionFlame = new ConclusionFlame(5, 4);

        // set the start time.
        conclusionFlame.setCurLoopIdx(ConclusionFlame.NB_TIMES - 1);
        assertThat(conclusionFlame.isFinished()).isFalse();
    }

    @Test
    public void isFinishedShouldReturnTrue() throws Exception {
        ConclusionFlame conclusionFlame = new ConclusionFlame(5, 4);

        // set the start time.
        conclusionFlame.setCurLoopIdx(ConclusionFlame.NB_TIMES);
        assertThat(conclusionFlame.isFinished()).isTrue();
    }
}