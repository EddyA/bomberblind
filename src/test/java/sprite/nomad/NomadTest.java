package sprite.nomad;

import static images.ImagesLoader.NB_BOMBER_WAIT_FRAME;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.time.Instant;

import org.assertj.core.api.WithAssertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import images.ImagesLoader;
import utils.CurrentTimeSupplier;

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
        assertThat(blueBomber.getActingTime()).isEqualTo(BlueBomber.ACTING_TIME);
    }

    @Test
    public void isTimeToMoveShouldReturnFalse() throws Exception {
        BlueBomber blueBomber = new BlueBomber(5, 4);

        // mock CurrentTimeSupplier class to set currentTimeMillis to 1000ms.
        CurrentTimeSupplier currentTimeSupplier = mock(CurrentTimeSupplier.class);
        Mockito.when(currentTimeSupplier.get()).thenReturn(Instant.ofEpochMilli(1000L));
        blueBomber.setCurrentTimeSupplier(currentTimeSupplier);

        // current time - last refresh time - 1 < 1000ms -> should return false.
        blueBomber.setLastActionTs(1000L - BlueBomber.ACTING_TIME + 1);

        // call & check.
        assertThat(blueBomber.isTimeToAct()).isFalse();
    }

    @Test
    public void isTimeToMoveShouldReturnTrue() throws Exception {
        BlueBomber blueBomber = new BlueBomber(5, 4);

        // mock CurrentTimeSupplier class to set currentTimeMillis to 1000ms.
        CurrentTimeSupplier currentTimeSupplier = mock(CurrentTimeSupplier.class);
        Mockito.when(currentTimeSupplier.get()).thenReturn(Instant.ofEpochMilli(1000L));
        blueBomber.setCurrentTimeSupplier(currentTimeSupplier);

        // current time - last refresh time >= 1000ms -> should return false.
        blueBomber.setLastRefreshTs(1000L - BlueBomber.ACTING_TIME);

        // call & check.
        assertThat(blueBomber.isTimeToAct()).isTrue();
    }

    @Test
    public void updateImageShouldDoNothing() throws Exception {
        BlueBomber blueBomber = new BlueBomber(5, 4);
        BlueBomber spyedBlueBomber = Mockito.spy(blueBomber);
        Mockito.when(spyedBlueBomber.hasActionChanged()).thenReturn(false);
        Mockito.when(spyedBlueBomber.isTimeToRefresh()).thenReturn(false);

        // set nomad.
        spyedBlueBomber.setImages(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWaitMatrixRowIdx]);
        spyedBlueBomber.setNbImages(NB_BOMBER_WAIT_FRAME);
        spyedBlueBomber.setCurImageIdx(1);

        // call & check.
        spyedBlueBomber.updateImage();
        assertThat(spyedBlueBomber.getCurImageIdx()).isEqualTo(1);
        assertThat(spyedBlueBomber.getCurImage())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWaitMatrixRowIdx][1]);
    }

    @Test
    public void updateImageShouldIncreaseCurImageIdx() throws Exception {
        BlueBomber blueBomber = new BlueBomber(5, 4);
        BlueBomber spyedBlueBomber = Mockito.spy(blueBomber);
        Mockito.when(spyedBlueBomber.hasActionChanged()).thenReturn(false);
        Mockito.when(spyedBlueBomber.isTimeToRefresh()).thenReturn(true);

        // set nomad.
        spyedBlueBomber.setImages(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWaitMatrixRowIdx]);
        spyedBlueBomber.setNbImages(NB_BOMBER_WAIT_FRAME);
        spyedBlueBomber.setCurImageIdx(1);

        // call & check.
        spyedBlueBomber.updateImage();
        assertThat(spyedBlueBomber.getCurImageIdx()).isEqualTo(2);
        assertThat(spyedBlueBomber.getCurImage())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWaitMatrixRowIdx][2]);
    }

    @Test
    public void updateImageWithANewStatusShouldSetCurImageIdxTo0() throws Exception {
        BlueBomber blueBomber = new BlueBomber(5, 4);
        BlueBomber spyedBlueBomber = Mockito.spy(blueBomber);
        Mockito.when(spyedBlueBomber.hasActionChanged()).thenReturn(true);
        Mockito.when(spyedBlueBomber.isTimeToRefresh()).thenReturn(false);

        // set nomad.
        spyedBlueBomber.setImages(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWaitMatrixRowIdx]);
        spyedBlueBomber.setNbImages(NB_BOMBER_WAIT_FRAME);
        spyedBlueBomber.setCurImageIdx(1); // index != 0.

        // call & check.
        spyedBlueBomber.updateImage();
        assertThat(spyedBlueBomber.getCurImageIdx()).isEqualTo(0);
        assertThat(spyedBlueBomber.getCurImage())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWaitMatrixRowIdx][0]);
    }

    @Test
    public void updateImageWithTheLastImageShouldSetCurImageIdxTo0() throws Exception {
        BlueBomber blueBomber = new BlueBomber(5, 4);
        BlueBomber spyedBlueBomber = Mockito.spy(blueBomber);
        Mockito.when(spyedBlueBomber.hasActionChanged()).thenReturn(false);
        Mockito.when(spyedBlueBomber.isTimeToRefresh()).thenReturn(true);

        // set nomad.
        spyedBlueBomber.setImages(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWaitMatrixRowIdx]);
        spyedBlueBomber.setNbImages(NB_BOMBER_WAIT_FRAME);
        spyedBlueBomber.setCurImageIdx(NB_BOMBER_WAIT_FRAME - 1); // last sprite's image.

        // call & check.
        spyedBlueBomber.updateImage();
        assertThat(spyedBlueBomber.getCurImageIdx()).isEqualTo(0);
        assertThat(spyedBlueBomber.getCurImage())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWaitMatrixRowIdx][0]);
    }
}
