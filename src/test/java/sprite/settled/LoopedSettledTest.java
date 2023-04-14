package sprite.settled;

import images.ImagesLoader;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import sprite.SpriteType;
import utils.Tools;

import java.io.IOException;

import static images.ImagesLoader.NB_FLAME_END_FRAME;
import static sprite.settled.LoopedSettled.Status.STATUS_ALIVE;
import static sprite.settled.LoopedSettled.Status.STATUS_ENDED;

class LoopedSettledTest implements WithAssertions {

    @BeforeEach
    void fillImagesMatrix() throws IOException {
        ImagesLoader.fillImagesMatrix();
    }

    @Test
    void constructorShouldSetMembersWithTheExpectedValues() {
        FlameEnd flameEnd = new FlameEnd(5, 4);

        // check members value.
        assertThat(flameEnd.getRowIdx()).isEqualTo(5);
        assertThat(flameEnd.getColIdx()).isEqualTo(4);
        assertThat(flameEnd.getXMap()).isEqualTo(Tools.getCaseCentreAbscissa(4));
        assertThat(flameEnd.getYMap()).isEqualTo(Tools.getCaseBottomOrdinate(5));
        assertThat(flameEnd.getSpriteType()).isEqualTo(SpriteType.TYPE_SPRITE_FLAME_END);
        assertThat(flameEnd.getRefreshTime()).isEqualTo(FlameEnd.REFRESH_TIME);
        assertThat(flameEnd.getImages()).isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.flameEndMatrixRowIdx]);
        assertThat(flameEnd.getNbImages()).isEqualTo(NB_FLAME_END_FRAME);
        assertThat(flameEnd.getNbTimes()).isEqualTo(FlameEnd.NB_TIMES);
    }

    @Test
    void updateStatusWithANotReachedNbTimeShouldReturnFalseAndStatusShouldBeAlive() {
        FlameEnd flameEnd = new FlameEnd(5, 4);

        // set the start time.
        flameEnd.setLoopIdx(FlameEnd.NB_TIMES - 2); // number of times not reached.
        flameEnd.setCurImageIdx(NB_FLAME_END_FRAME - 1); // sprite ended.
        assertThat(flameEnd.updateStatus()).isFalse();
        assertThat(flameEnd.getStatus()).isEqualTo(STATUS_ALIVE);
    }

    @Test
    void updateStatusWithANotEndedSpriteShouldReturnFalseAndStatusShouldBeAlive() {
        FlameEnd flameEnd = new FlameEnd(5, 4);

        // set the start time.
        flameEnd.setLoopIdx(FlameEnd.NB_TIMES - 1); // number of times reached.
        flameEnd.setCurImageIdx(NB_FLAME_END_FRAME - 2); // sprite not ended.
        assertThat(flameEnd.updateStatus()).isFalse();
        assertThat(flameEnd.getStatus()).isEqualTo(STATUS_ALIVE);
    }

    @Test
    void updateStatusShouldReturnTrueAndStatusShouldBeFinished() {
        FlameEnd flameEnd = new FlameEnd(5, 4);

        // set the start time.
        flameEnd.setLoopIdx(FlameEnd.NB_TIMES - 1); // number of times reached.
        flameEnd.setCurImageIdx(NB_FLAME_END_FRAME - 1); // sprite ended.
        assertThat(flameEnd.updateStatus()).isTrue();
        assertThat(flameEnd.getStatus()).isEqualTo(STATUS_ENDED);
    }

    @Test
    void updateImageWithAEndedSpriteShouldDoNothing() {
        FlameEnd flameEnd = new FlameEnd(5, 4);
        FlameEnd spiedFlameEnd = Mockito.spy(flameEnd);
        Mockito.when(spiedFlameEnd.updateStatus()).thenReturn(true);

        // set settled.
        spiedFlameEnd.setCurImageIdx(1);
        spiedFlameEnd.setLoopIdx(3);
        spiedFlameEnd.setCurImage(ImagesLoader.imagesMatrix[ImagesLoader.flameEndMatrixRowIdx][1]);

        // call & check.
        spiedFlameEnd.updateImage();
        assertThat(spiedFlameEnd.getCurImageIdx()).isEqualTo(1);
        assertThat(spiedFlameEnd.getLoopIdx()).isEqualTo(3);
        assertThat(spiedFlameEnd.getCurImage()).isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.flameEndMatrixRowIdx][1]);
    }

    @Test
    void updateImageWhenItIsNotTimeToRefreshShouldDoNothing() {
        FlameEnd flameEnd = new FlameEnd(5, 4);
        FlameEnd spiedFlameEnd = Mockito.spy(flameEnd);
        Mockito.when(spiedFlameEnd.isTimeToRefresh()).thenReturn(false);

        // set settled.
        spiedFlameEnd.setCurImageIdx(1);
        spiedFlameEnd.setLoopIdx(3);
        spiedFlameEnd.setCurImage(ImagesLoader.imagesMatrix[ImagesLoader.flameEndMatrixRowIdx][1]);

        // call & check.
        spiedFlameEnd.updateImage();
        assertThat(spiedFlameEnd.getCurImageIdx()).isEqualTo(1);
        assertThat(spiedFlameEnd.getLoopIdx()).isEqualTo(3);
        assertThat(spiedFlameEnd.getCurImage()).isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.flameEndMatrixRowIdx][1]);
    }

    @Test
    void updateImageShouldIncreaseCurImageIdx() {
        FlameEnd flameEnd = new FlameEnd(5, 4);
        FlameEnd spiedFlameEnd = Mockito.spy(flameEnd);
        Mockito.when(spiedFlameEnd.isTimeToRefresh()).thenReturn(true);
        Mockito.when(spiedFlameEnd.updateStatus()).thenReturn(false);

        // set settled.
        spiedFlameEnd.setCurImageIdx(1);
        spiedFlameEnd.setLoopIdx(3);

        // call & check.
        spiedFlameEnd.updateImage();
        assertThat(spiedFlameEnd.getCurImageIdx()).isEqualTo(2);
        assertThat(spiedFlameEnd.getLoopIdx()).isEqualTo(3); // stay the same.
        assertThat(spiedFlameEnd.getCurImage())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.flameEndMatrixRowIdx][2]);
    }

    @Test
    void updateImageWithTheLastImageShouldSetCurImageIdxTo0AndIncrementCurLoopIdx() {
        FlameEnd flameEnd = new FlameEnd(5, 4);
        FlameEnd spiedFlameEnd = Mockito.spy(flameEnd);
        Mockito.when(spiedFlameEnd.isTimeToRefresh()).thenReturn(true);
        Mockito.when(spiedFlameEnd.updateStatus()).thenReturn(false);

        // set settled.
        spiedFlameEnd.setCurImageIdx(NB_FLAME_END_FRAME - 1); // last image of the sprite.
        spiedFlameEnd.setLoopIdx(3);

        // call & check.
        spiedFlameEnd.updateImage();
        assertThat(spiedFlameEnd.getCurImageIdx()).isEqualTo(0);
        assertThat(spiedFlameEnd.getLoopIdx()).isEqualTo(4);
        assertThat(spiedFlameEnd.getCurImage())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.flameEndMatrixRowIdx][0]);
    }

    @Test
    void isFinishedShouldReturnFalseWhenCurStatusIsAlive() {
        FlameEnd flameEnd = new FlameEnd(5, 4);

        // set the status and check.
        flameEnd.setStatus(STATUS_ALIVE);
        assertThat(flameEnd.isFinished()).isFalse();
    }

    @Test
    void isFinishedShouldReturnTrueWhenCurStatusIsFinished() {
        FlameEnd flameEnd = new FlameEnd(5, 4);

        // set the status and check.
        flameEnd.setStatus(STATUS_ENDED);
        assertThat(flameEnd.isFinished()).isTrue();
    }
}
