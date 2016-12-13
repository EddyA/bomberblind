package sprite.settled;

import images.ImagesLoader;
import org.assertj.core.api.WithAssertions;
import org.junit.Before;
import org.junit.Test;
import sprite.settled.FlameEnd;
import utils.Tools;

import java.io.IOException;

import static sprite.settled.LoopedSettled.Status.STATUS_ALIVE;
import static sprite.settled.LoopedSettled.Status.STATUS_ENDED;

public class LoopedSettledTest implements WithAssertions {

    @Before
    public void fillImagesMatrix() throws IOException {
        ImagesLoader.fillImagesMatrix();
    }

    @Test
    public void constructorShouldSetMembersWithTheExpectedValues() throws Exception {
        FlameEnd flameEnd = new FlameEnd(5, 4);

        // check members value.
        assertThat(flameEnd.getRowIdx()).isEqualTo(5);
        assertThat(flameEnd.getColIdx()).isEqualTo(4);
        assertThat(flameEnd.getXMap()).isEqualTo(Tools.getCaseCentreAbscissa(4));
        assertThat(flameEnd.getYMap()).isEqualTo(Tools.getCaseBottomOrdinate(5));
        assertThat(flameEnd.getImages()).isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.flameEndMatrixRowIdx]);
        assertThat(flameEnd.getNbImages()).isEqualTo(ImagesLoader.NB_FLAME_END_FRAME);
        assertThat(flameEnd.getRefreshTime()).isEqualTo(FlameEnd.REFRESH_TIME);
        assertThat(flameEnd.getNbTimes()).isEqualTo(FlameEnd.NB_TIMES);
    }

    @Test
    public void updateStatusShouldReturnFalseAndStatusShouldBeAlive() throws Exception {
        FlameEnd flameEnd = new FlameEnd(5, 4);

        // set the start time.
        flameEnd.setCurLoopIdx(FlameEnd.NB_TIMES - 1); // sprite not ended.
        assertThat(flameEnd.updateStatus()).isFalse();
        assertThat(flameEnd.getCurStatus()).isEqualTo(STATUS_ALIVE);
    }

    @Test
    public void updateStatusShouldReturnTrueAndStatusShouldBeFinished() throws Exception {
        FlameEnd flameEnd = new FlameEnd(5, 4);

        // set the start time.
        flameEnd.setCurLoopIdx(FlameEnd.NB_TIMES - 1); // sprite ended.
        flameEnd.setCurImageIdx(ImagesLoader.NB_FLAME_END_FRAME - 1); // sprite ended.
        assertThat(flameEnd.updateStatus()).isTrue();
        assertThat(flameEnd.getCurStatus()).isEqualTo(STATUS_ENDED);
    }

    @Test
    public void isFinishedShouldReturnFalseWhenCurStatusIsAlive() throws Exception {
        FlameEnd flameEnd = new FlameEnd(5, 4);

        // set the status and check.
        flameEnd.setCurStatus(STATUS_ALIVE);
        assertThat(flameEnd.isFinished()).isFalse();
    }

    @Test
    public void isFinishedShouldReturnTrueWhenCurStatusIsFinished() throws Exception {
        FlameEnd flameEnd = new FlameEnd(5, 4);

        // set the status and check.
        flameEnd.setCurStatus(STATUS_ENDED);
        assertThat(flameEnd.isFinished()).isTrue();
    }
}