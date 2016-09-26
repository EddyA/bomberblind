package sprite.nomad.abstracts;

import images.ImagesLoader;
import org.assertj.core.api.WithAssertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import sprite.nomad.BlueBomber;
import utils.CurrentTimeSupplier;

import java.io.IOException;
import java.time.Instant;

import static images.ImagesLoader.NB_BOMBER_WAIT_FRAME;
import static org.mockito.Mockito.mock;

public class NomadTest implements WithAssertions {

    @Before
    public void fillImagesMatrix() throws IOException {
        ImagesLoader.fillImagesMatrix();
    }

    @Test
    public void constructorShouldSetMembersWithTheExpectedValues() throws Exception {
        BlueBomber blueBomber = new BlueBomber(5, 4);

        // check members value.
        assertThat(blueBomber.getXMap()).isEqualTo(5);
        assertThat(blueBomber.getYMap()).isEqualTo(4);
        assertThat(blueBomber.getRefreshTime()).isEqualTo(BlueBomber.REFRESH_TIME);
        assertThat(blueBomber.getMoveTime()).isEqualTo(BlueBomber.MOVING_TIME);
    }

    @Test
    public void isTimeToMoveShouldReturnFalse() throws Exception {
        BlueBomber blueBomber = new BlueBomber(5, 4);

        // mock CurrentTimeSupplier class to set currentTimeMillis to 1000ms.
        CurrentTimeSupplier currentTimeSupplier = mock(CurrentTimeSupplier.class);
        Mockito.when(currentTimeSupplier.get()).thenReturn(Instant.ofEpochMilli(1000L));
        blueBomber.setCurrentTimeSupplier(currentTimeSupplier);

        // current time - last refresh time - 1 < 1000ms -> should return false.
        blueBomber.setLastMoveTs(1000L - BlueBomber.MOVING_TIME + 1);

        // call & check.
        assertThat(blueBomber.isTimeToMove()).isFalse();
    }

    @Test
    public void isTimeToMoveShouldReturnTrue() throws Exception {
        BlueBomber blueBomber = new BlueBomber(5, 4);

        // mock CurrentTimeSupplier class to set currentTimeMillis to 1000ms.
        CurrentTimeSupplier currentTimeSupplier = mock(CurrentTimeSupplier.class);
        Mockito.when(currentTimeSupplier.get()).thenReturn(Instant.ofEpochMilli(1000L));
        blueBomber.setCurrentTimeSupplier(currentTimeSupplier);

        // current time - last refresh time >= 1000ms -> should return false.
        blueBomber.setLastRefreshTs(1000L - BlueBomber.MOVING_TIME);

        // call & check.
        assertThat(blueBomber.isTimeToMove()).isTrue();
    }

    @Test
    public void updateImageShouldDoNothing() throws Exception {
        BlueBomber blueBomber = new BlueBomber(5, 4);
        BlueBomber spyedBlueBomber = Mockito.spy(blueBomber);
        Mockito.when(spyedBlueBomber.updateStatus()).thenReturn(false);
        Mockito.when(spyedBlueBomber.isTimeToRefresh()).thenReturn(false);

        // set nomad.
        spyedBlueBomber.images = ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWaitMatrixRowIdx];
        spyedBlueBomber.nbImages = NB_BOMBER_WAIT_FRAME;
        spyedBlueBomber.curImageIdx = 1;

        // call & check.
        spyedBlueBomber.updateImage();
        assertThat(spyedBlueBomber.curImageIdx).isEqualTo(1);
        assertThat(spyedBlueBomber.getCurImage())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWaitMatrixRowIdx][1]);
    }

    @Test
    public void updateImageShouldIncreaseCurImageIdx() throws Exception {
        BlueBomber blueBomber = new BlueBomber(5, 4);
        BlueBomber spyedBlueBomber = Mockito.spy(blueBomber);
        Mockito.when(spyedBlueBomber.updateStatus()).thenReturn(false);
        Mockito.when(spyedBlueBomber.isTimeToRefresh()).thenReturn(true);

        // set nomad.
        spyedBlueBomber.images = ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWaitMatrixRowIdx];
        spyedBlueBomber.nbImages = NB_BOMBER_WAIT_FRAME;
        spyedBlueBomber.curImageIdx = 1;

        // call & check.
        spyedBlueBomber.updateImage();
        assertThat(spyedBlueBomber.curImageIdx).isEqualTo(2);
        assertThat(spyedBlueBomber.getCurImage())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWaitMatrixRowIdx][2]);
    }

    @Test
    public void updateImageWithANewStatusShouldSetCurImageIdxTo0() throws Exception {
        BlueBomber blueBomber = new BlueBomber(5, 4);
        BlueBomber spyedBlueBomber = Mockito.spy(blueBomber);
        Mockito.when(spyedBlueBomber.updateStatus()).thenReturn(true);
        Mockito.when(spyedBlueBomber.isTimeToRefresh()).thenReturn(false);

        // set nomad.
        spyedBlueBomber.images = ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWaitMatrixRowIdx];
        spyedBlueBomber.nbImages = NB_BOMBER_WAIT_FRAME;
        spyedBlueBomber.curImageIdx = 1; // index != 0.

        // call & check.
        spyedBlueBomber.updateImage();
        assertThat(spyedBlueBomber.curImageIdx).isEqualTo(0);
        assertThat(spyedBlueBomber.getCurImage())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWaitMatrixRowIdx][0]);
    }

    @Test
    public void updateImageWithTheLastImageShouldSetCurImageIdxTo0() throws Exception {
        BlueBomber blueBomber = new BlueBomber(5, 4);
        BlueBomber spyedBlueBomber = Mockito.spy(blueBomber);
        Mockito.when(spyedBlueBomber.updateStatus()).thenReturn(false);
        Mockito.when(spyedBlueBomber.isTimeToRefresh()).thenReturn(true);

        // set nomad.
        spyedBlueBomber.images = ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWaitMatrixRowIdx];
        spyedBlueBomber.nbImages = NB_BOMBER_WAIT_FRAME;
        spyedBlueBomber.curImageIdx = NB_BOMBER_WAIT_FRAME - 1; // last sprite's image.

        // call & check.
        spyedBlueBomber.updateImage();
        assertThat(spyedBlueBomber.curImageIdx).isEqualTo(0);
        assertThat(spyedBlueBomber.getCurImage())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWaitMatrixRowIdx][0]);
    }
}
