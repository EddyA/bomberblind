package sprites.settled.abstracts;

import static images.ImagesLoader.NB_FLAME_FRAME;

import java.io.IOException;

import org.assertj.core.api.WithAssertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import images.ImagesLoader;
import sprites.settled.Flame;
import utils.Tools;

public class SettledTest implements WithAssertions {

    @Before
    public void fillImagesMatrix() throws IOException {
        ImagesLoader.fillImagesMatrix();
    }

    @Test
    public void constructorShouldSetMembersWithTheExpectedValues() throws Exception {
        Flame flame = new Flame(5, 4);

        // check members value.
        assertThat(flame.getRowIdx()).isEqualTo(5);
        assertThat(flame.getColIdx()).isEqualTo(4);
        assertThat(flame.getXMap()).isEqualTo(Tools.getCaseCentreAbscissa(4));
        assertThat(flame.getYMap()).isEqualTo(Tools.getCaseBottomOrdinate(5));
        assertThat(flame.getImages()).isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.flameMatrixRowIdx]);
        assertThat(flame.getNbImages()).isEqualTo(ImagesLoader.NB_FLAME_FRAME);
        assertThat(flame.getRefreshTime()).isEqualTo(Flame.REFRESH_TIME);
    }

    @Test
    public void updateImageShouldDoNothing() throws Exception {
        Flame flame = new Flame(5, 4);
        Flame spyedFlame = Mockito.spy(flame);
        Mockito.when(spyedFlame.isTimeToRefresh()).thenReturn(false);

        // set settled.
        spyedFlame.setCurImageIdx(1);
        spyedFlame.setCurLoopIdx(3);
        spyedFlame.setCurImage(ImagesLoader.imagesMatrix[ImagesLoader.flameMatrixRowIdx][1]);

        // call & check.
        spyedFlame.updateImage();
        assertThat(spyedFlame.getCurImageIdx()).isEqualTo(1);
        assertThat(spyedFlame.getCurLoopIdx()).isEqualTo(3);
        assertThat(spyedFlame.getCurImage()).isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.flameMatrixRowIdx][1]);
    }

    @Test
    public void updateImageShouldIncreaseCurImageIdx() throws Exception {
        Flame flame = new Flame(5, 4);
        Flame spyedFlame = Mockito.spy(flame);
        Mockito.when(spyedFlame.isTimeToRefresh()).thenReturn(true);

        // set settled.
        spyedFlame.setCurImageIdx(1);
        spyedFlame.setCurLoopIdx(3);

        // call & check.
        spyedFlame.updateImage();
        assertThat(spyedFlame.getCurImageIdx()).isEqualTo(2);
        assertThat(spyedFlame.getCurLoopIdx()).isEqualTo(3); // stay the same.
        assertThat(spyedFlame.getCurImage())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.flameMatrixRowIdx][2]);
    }

    @Test
    public void updateImageWithTheLastImageShouldSetCurImageIdxTo0AndIncrementCurLoopIdx()
            throws Exception {
        Flame flame = new Flame(5, 4);
        Flame spyedFlame = Mockito.spy(flame);
        Mockito.when(spyedFlame.isTimeToRefresh()).thenReturn(true);

        // set settled.
        spyedFlame.setCurImageIdx(NB_FLAME_FRAME - 1); // last sprite's image.
        spyedFlame.setCurLoopIdx(3);

        // call & check.
        spyedFlame.updateImage();
        assertThat(spyedFlame.getCurImageIdx()).isEqualTo(0);
        assertThat(spyedFlame.getCurLoopIdx()).isEqualTo(4);
        assertThat(spyedFlame.getCurImage())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.flameMatrixRowIdx][0]);
    }
}