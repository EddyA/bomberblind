package sprite.settled.abstracts;

import images.ImagesLoader;
import org.assertj.core.api.WithAssertions;
import org.junit.Before;
import org.junit.Test;
import sprite.settled.ConclusionFlame;
import utils.Tools;

import java.io.IOException;

import static sprite.settled.abstracts.LoopedSettled.Status.STATUS_ALIVE;
import static sprite.settled.abstracts.LoopedSettled.Status.STATUS_ENDED;

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
        assertThat(conclusionFlame.getNbTimes()).isEqualTo(ConclusionFlame.NB_TIMES);
    }

    @Test
    public void updateStatusShouldReturnFalseAndStatusShouldBeAlive() throws Exception {
        ConclusionFlame conclusionFlame = new ConclusionFlame(5, 4);

        // set the start time.
        conclusionFlame.setCurLoopIdx(ConclusionFlame.NB_TIMES - 1); // sprite not ended.
        assertThat(conclusionFlame.updateStatus()).isFalse();
        assertThat(conclusionFlame.getCurStatus()).isEqualTo(STATUS_ALIVE);
    }

    @Test
    public void updateStatusShouldReturnTrueAndStatusShouldBeFinished() throws Exception {
        ConclusionFlame conclusionFlame = new ConclusionFlame(5, 4);

        // set the start time.
        conclusionFlame.setCurLoopIdx(ConclusionFlame.NB_TIMES); // sprite ended.
        assertThat(conclusionFlame.updateStatus()).isTrue();
        assertThat(conclusionFlame.getCurStatus()).isEqualTo(STATUS_ENDED);
    }

    @Test
    public void isFinishedShouldReturnFalseWhenCurStatusIsAlive() throws Exception {
        ConclusionFlame conclusionFlame = new ConclusionFlame(5, 4);

        // set the status and check.
        conclusionFlame.setCurStatus(STATUS_ALIVE);
        assertThat(conclusionFlame.isFinished()).isFalse();
    }

    @Test
    public void isFinishedShouldReturnTrueWhenCurStatusIsFinished() throws Exception {
        ConclusionFlame conclusionFlame = new ConclusionFlame(5, 4);

        // set the status and check.
        conclusionFlame.setCurStatus(STATUS_ENDED);
        assertThat(conclusionFlame.isFinished()).isTrue();
    }
}