package sprite.settled;

import images.ImagesLoader;
import org.assertj.core.api.WithAssertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import utils.Tools;

import java.io.IOException;

public class BonusTest implements WithAssertions {

    @Before
    public void fillImagesMatrix() throws IOException {
        ImagesLoader.fillImagesMatrix();
    }

    @Test
    public void constructorShouldSetMembersWithTheExpectedValues() throws Exception {
        BonusBomb bonusBomb = new BonusBomb(5, 10);

        // check members value.
        assertThat(bonusBomb.getRowIdx()).isEqualTo(5);
        assertThat(bonusBomb.getColIdx()).isEqualTo(10);
        assertThat(bonusBomb.getxMap()).isEqualTo(Tools.getCaseCentreAbscissa(10));
        assertThat(bonusBomb.getyMap()).isEqualTo(Tools.getCaseBottomOrdinate(5));
        assertThat(bonusBomb.getBonusType()).isEqualTo(BonusType.TYPE_BONUS_BOMB);
        assertThat(bonusBomb.getImages()).isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.bonusBombMatrixRowIdx]);
        assertThat(bonusBomb.getNbImages()).isEqualTo(ImagesLoader.NB_BONUS_BOMB_FRAME);
        assertThat(bonusBomb.getStatus()).isEqualTo(Bonus.Status.STATUS_ALIVE);
    }

    @Test
    public void updateImageWhenItIsNotTimeToRefreshShouldDoNothing() throws Exception {

        // ToDo: Update that test when having a real sprite.
        BonusBomb bonusBomb = new BonusBomb(5, 4);
        BonusBomb spyedBonusBomb = Mockito.spy(bonusBomb);
        Mockito.when(spyedBonusBomb.isTimeToRefresh()).thenReturn(false);

        // set bonus.
        spyedBonusBomb.setCurImageIdx(0);
        spyedBonusBomb.setCurImage(ImagesLoader.imagesMatrix[ImagesLoader.bonusBombMatrixRowIdx][0]);

        // call & check.
        spyedBonusBomb.updateImage();
        assertThat(spyedBonusBomb.getCurImageIdx()).isEqualTo(0);
        assertThat(spyedBonusBomb.getCurImage()).isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.bonusBombMatrixRowIdx][0]);
    }

    @Test
    public void updateImageShouldIncreaseCurImageIdx() throws Exception {

        // ToDo: Update that test when having a real sprite.
        BonusBomb bonusBomb = new BonusBomb(5, 4);
        BonusBomb spyedBonusBomb = Mockito.spy(bonusBomb);
        Mockito.when(spyedBonusBomb.isTimeToRefresh()).thenReturn(true);

        // set bonus.
        spyedBonusBomb.setCurImageIdx(0);
        spyedBonusBomb.setCurImage(ImagesLoader.imagesMatrix[ImagesLoader.bonusBombMatrixRowIdx][0]);

        // call & check.
        spyedBonusBomb.updateImage();
        assertThat(spyedBonusBomb.getCurImageIdx()).isEqualTo(0);
        assertThat(spyedBonusBomb.getCurImage()).isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.bonusBombMatrixRowIdx][0]);
    }

    @Test
    public void updateImageWithTheLastImageShouldSetCurImageIdxTo0() throws Exception {

        // ToDo: Update that test when having a real sprite.
        BonusBomb bonusBomb = new BonusBomb(5, 4);
        BonusBomb spyedBonusBomb = Mockito.spy(bonusBomb);
        Mockito.when(spyedBonusBomb.isTimeToRefresh()).thenReturn(true);

        // set bonus.
        spyedBonusBomb.setCurImageIdx(0);
        spyedBonusBomb.setCurImage(ImagesLoader.imagesMatrix[ImagesLoader.bonusBombMatrixRowIdx][0]);

        // call & check.
        spyedBonusBomb.updateImage();
        assertThat(spyedBonusBomb.getCurImageIdx()).isEqualTo(0);
        assertThat(spyedBonusBomb.getCurImage()).isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.bonusBombMatrixRowIdx][0]);
    }

    @Test
    public void isFinishedShouldReturnFalseWhenCurStatusIsAlive() throws Exception {
        BonusBomb bonusBomb = new BonusBomb(5, 4);

        // set the status and check.
        bonusBomb.setStatus(Bonus.Status.STATUS_ALIVE);
        assertThat(bonusBomb.isFinished()).isFalse();
    }

    @Test
    public void isFinishedShouldReturnTrueWhenCurStatusIsFinished() throws Exception {
        BonusBomb bonusBomb = new BonusBomb(5, 4);

        // set the status and check.
        bonusBomb.setStatus(Bonus.Status.STATUS_ENDED);
        assertThat(bonusBomb.isFinished()).isTrue();
    }
}