package sprite.settled;

import images.ImagesLoader;
import org.assertj.core.api.WithAssertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import sprite.SpriteType;
import utils.Tools;

import java.io.IOException;

import static images.ImagesLoader.NB_FLAME_END_FRAME;
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
        assertThat(flameEnd.getxMap()).isEqualTo(Tools.getCaseCentreAbscissa(4));
        assertThat(flameEnd.getyMap()).isEqualTo(Tools.getCaseBottomOrdinate(5));
        assertThat(flameEnd.getSpriteType()).isEqualTo(SpriteType.TYPE_SPRITE_FLAME_END);
        assertThat(flameEnd.getRefreshTime()).isEqualTo(FlameEnd.REFRESH_TIME);
        assertThat(flameEnd.getImages()).isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.flameEndMatrixRowIdx]);
        assertThat(flameEnd.getNbImages()).isEqualTo(NB_FLAME_END_FRAME);
        assertThat(flameEnd.getNbTimes()).isEqualTo(FlameEnd.NB_TIMES);
    }

    @Test
    public void updateStatusWithANotReachedNbTimeShouldReturnFalseAndStatusShouldBeAlive() throws Exception {
        FlameEnd flameEnd = new FlameEnd(5, 4);

        // set the start time.
        flameEnd.setLoopIdx(FlameEnd.NB_TIMES - 2); // number of times not reached.
        flameEnd.setCurImageIdx(NB_FLAME_END_FRAME - 1); // sprite ended.
        assertThat(flameEnd.updateStatus()).isFalse();
        assertThat(flameEnd.getStatus()).isEqualTo(STATUS_ALIVE);
    }

    @Test
    public void updateStatusWithANotEndedSpriteShouldReturnFalseAndStatusShouldBeAlive() throws Exception {
        FlameEnd flameEnd = new FlameEnd(5, 4);

        // set the start time.
        flameEnd.setLoopIdx(FlameEnd.NB_TIMES - 1); // number of times reached.
        flameEnd.setCurImageIdx(NB_FLAME_END_FRAME - 2); // sprite not ended.
        assertThat(flameEnd.updateStatus()).isFalse();
        assertThat(flameEnd.getStatus()).isEqualTo(STATUS_ALIVE);
    }

    @Test
    public void updateStatusShouldReturnTrueAndStatusShouldBeFinished() throws Exception {
        FlameEnd flameEnd = new FlameEnd(5, 4);

        // set the start time.
        flameEnd.setLoopIdx(FlameEnd.NB_TIMES - 1); // number of times reached.
        flameEnd.setCurImageIdx(NB_FLAME_END_FRAME - 1); // sprite ended.
        assertThat(flameEnd.updateStatus()).isTrue();
        assertThat(flameEnd.getStatus()).isEqualTo(STATUS_ENDED);
    }

    @Test
    public void updateImageWithAEndedSpriteShouldDoNothing() throws Exception {
        FlameEnd flameEnd = new FlameEnd(5, 4);
        FlameEnd spyedFlameEnd = Mockito.spy(flameEnd);
        Mockito.when(spyedFlameEnd.updateStatus()).thenReturn(true);

        // set settled.
        spyedFlameEnd.setCurImageIdx(1);
        spyedFlameEnd.setLoopIdx(3);
        spyedFlameEnd.setCurImage(ImagesLoader.imagesMatrix[ImagesLoader.flameEndMatrixRowIdx][1]);

        // call & check.
        spyedFlameEnd.updateImage();
        assertThat(spyedFlameEnd.getCurImageIdx()).isEqualTo(1);
        assertThat(spyedFlameEnd.getLoopIdx()).isEqualTo(3);
        assertThat(spyedFlameEnd.getCurImage()).isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.flameEndMatrixRowIdx][1]);
    }

    @Test
    public void updateImageWhenItIsNotTimeToRefreshShouldDoNothing() throws Exception {
        FlameEnd flameEnd = new FlameEnd(5, 4);
        FlameEnd spyedFlameEnd = Mockito.spy(flameEnd);
        Mockito.when(spyedFlameEnd.isTimeToRefresh()).thenReturn(false);

        // set settled.
        spyedFlameEnd.setCurImageIdx(1);
        spyedFlameEnd.setLoopIdx(3);
        spyedFlameEnd.setCurImage(ImagesLoader.imagesMatrix[ImagesLoader.flameEndMatrixRowIdx][1]);

        // call & check.
        spyedFlameEnd.updateImage();
        assertThat(spyedFlameEnd.getCurImageIdx()).isEqualTo(1);
        assertThat(spyedFlameEnd.getLoopIdx()).isEqualTo(3);
        assertThat(spyedFlameEnd.getCurImage()).isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.flameEndMatrixRowIdx][1]);
    }

    @Test
    public void updateImageShouldIncreaseCurImageIdx() throws Exception {
        FlameEnd flameEnd = new FlameEnd(5, 4);
        FlameEnd spyedFlameEnd = Mockito.spy(flameEnd);
        Mockito.when(spyedFlameEnd.isTimeToRefresh()).thenReturn(true);
        Mockito.when(spyedFlameEnd.updateStatus()).thenReturn(false);

        // set settled.
        spyedFlameEnd.setCurImageIdx(1);
        spyedFlameEnd.setLoopIdx(3);

        // call & check.
        spyedFlameEnd.updateImage();
        assertThat(spyedFlameEnd.getCurImageIdx()).isEqualTo(2);
        assertThat(spyedFlameEnd.getLoopIdx()).isEqualTo(3); // stay the same.
        assertThat(spyedFlameEnd.getCurImage())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.flameEndMatrixRowIdx][2]);
    }

    @Test
    public void updateImageWithTheLastImageShouldSetCurImageIdxTo0AndIncrementCurLoopIdx() throws Exception {
        FlameEnd flameEnd = new FlameEnd(5, 4);
        FlameEnd spyedFlameEnd = Mockito.spy(flameEnd);
        Mockito.when(spyedFlameEnd.isTimeToRefresh()).thenReturn(true);
        Mockito.when(spyedFlameEnd.updateStatus()).thenReturn(false);

        // set settled.
        spyedFlameEnd.setCurImageIdx(NB_FLAME_END_FRAME - 1); // last image of the sprite.
        spyedFlameEnd.setLoopIdx(3);

        // call & check.
        spyedFlameEnd.updateImage();
        assertThat(spyedFlameEnd.getCurImageIdx()).isEqualTo(0);
        assertThat(spyedFlameEnd.getLoopIdx()).isEqualTo(4);
        assertThat(spyedFlameEnd.getCurImage())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.flameEndMatrixRowIdx][0]);
    }

    @Test
    public void isFinishedShouldReturnFalseWhenCurStatusIsAlive() throws Exception {
        FlameEnd flameEnd = new FlameEnd(5, 4);

        // set the status and check.
        flameEnd.setStatus(STATUS_ALIVE);
        assertThat(flameEnd.isFinished()).isFalse();
    }

    @Test
    public void isFinishedShouldReturnTrueWhenCurStatusIsFinished() throws Exception {
        FlameEnd flameEnd = new FlameEnd(5, 4);

        // set the status and check.
        flameEnd.setStatus(STATUS_ENDED);
        assertThat(flameEnd.isFinished()).isTrue();
    }
}