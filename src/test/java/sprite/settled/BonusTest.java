package sprite.settled;

import images.ImagesLoader;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import utils.Tools;

import java.io.IOException;

import static images.ImagesLoader.NB_BONUS_BOMB_FRAME;

class BonusTest implements WithAssertions {

    @BeforeEach
    void fillImagesMatrix() throws IOException {
        ImagesLoader.fillImagesMatrix();
    }

    @Test
    void constructorShouldSetMembersWithTheExpectedValues() {
        BonusBomb bonusBomb = new BonusBomb(5, 10);

        // check members value.
        assertThat(bonusBomb.getRowIdx()).isEqualTo(5);
        assertThat(bonusBomb.getColIdx()).isEqualTo(10);
        assertThat(bonusBomb.getXMap()).isEqualTo(Tools.getCaseCentreAbscissa(10));
        assertThat(bonusBomb.getYMap()).isEqualTo(Tools.getCaseBottomOrdinate(5));
        assertThat(bonusBomb.getBonusType()).isEqualTo(BonusType.TYPE_BONUS_BOMB);
        assertThat(bonusBomb.getImages()).isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.bonusBombMatrixRowIdx]);
        assertThat(bonusBomb.getNbImages()).isEqualTo(NB_BONUS_BOMB_FRAME);
        assertThat(bonusBomb.getStatus()).isEqualTo(Bonus.Status.STATUS_ALIVE);
    }

    @Test
    void updateImageWhenItIsNotTimeToRefreshShouldDoNothing() {
        BonusBomb bonusBomb = new BonusBomb(5, 4);
        BonusBomb spiedBonusBomb = Mockito.spy(bonusBomb);
        Mockito.when(spiedBonusBomb.isTimeToRefresh()).thenReturn(false);

        // set bonus.
        spiedBonusBomb.setCurImageIdx(0);
        spiedBonusBomb.setCurImage(ImagesLoader.imagesMatrix[ImagesLoader.bonusBombMatrixRowIdx][0]);

        // call & check.
        spiedBonusBomb.updateImage();
        assertThat(spiedBonusBomb.getCurImageIdx()).isEqualTo(0);
        assertThat(spiedBonusBomb.getCurImage()).isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.bonusBombMatrixRowIdx][0]);
    }

    @Test
    void updateImageShouldIncreaseCurImageIdx() {
        BonusBomb bonusBomb = new BonusBomb(5, 4);
        BonusBomb spiedBonusBomb = Mockito.spy(bonusBomb);
        Mockito.when(spiedBonusBomb.isTimeToRefresh()).thenReturn(true);

        // set bonus.
        spiedBonusBomb.setCurImageIdx(0);
        spiedBonusBomb.setCurImage(ImagesLoader.imagesMatrix[ImagesLoader.bonusBombMatrixRowIdx][0]);

        // call & check.
        spiedBonusBomb.updateImage();
        assertThat(spiedBonusBomb.getCurImageIdx()).isEqualTo(1);
        assertThat(spiedBonusBomb.getCurImage()).isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.bonusBombMatrixRowIdx][1]);
    }

    @Test
    void updateImageWithTheLastImageShouldSetCurImageIdxTo0() {
        BonusBomb bonusBomb = new BonusBomb(5, 4);
        BonusBomb spiedBonusBomb = Mockito.spy(bonusBomb);
        Mockito.when(spiedBonusBomb.isTimeToRefresh()).thenReturn(true);

        // set bonus.
        spiedBonusBomb.setCurImageIdx(NB_BONUS_BOMB_FRAME - 1);

        // call & check.
        spiedBonusBomb.updateImage();
        assertThat(spiedBonusBomb.getCurImageIdx()).isEqualTo(0);
        assertThat(spiedBonusBomb.getCurImage()).isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.bonusBombMatrixRowIdx][0]);
    }

    @Test
    void isFinishedShouldReturnFalseWhenCurStatusIsAlive() {
        BonusBomb bonusBomb = new BonusBomb(5, 4);

        // set the status and check.
        bonusBomb.setStatus(Bonus.Status.STATUS_ALIVE);
        assertThat(bonusBomb.isFinished()).isFalse();
    }

    @Test
    void isFinishedShouldReturnTrueWhenCurStatusIsFinished() {
        BonusBomb bonusBomb = new BonusBomb(5, 4);

        // set the status and check.
        bonusBomb.setStatus(Bonus.Status.STATUS_ENDED);
        assertThat(bonusBomb.isFinished()).isTrue();
    }
}
