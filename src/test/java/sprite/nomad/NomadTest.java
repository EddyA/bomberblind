package sprite.nomad;

import static images.ImagesLoader.NB_BOMBER_WAIT_FRAME;
import static org.mockito.Mockito.mock;
import static sprite.nomad.BlueBomber.INVINCIBILITY_TIME;
import static utils.Action.ACTION_DYING;
import static utils.Action.ACTION_WAITING;
import static utils.Action.ACTION_WALKING;

import java.io.IOException;
import java.time.Instant;

import org.assertj.core.api.WithAssertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import images.ImagesLoader;
import sprite.SpriteType;
import utils.CurrentTimeSupplier;
import utils.Direction;

public class NomadTest implements WithAssertions {

    @Before
    public void fillImagesMatrix() throws IOException {
        ImagesLoader.fillImagesMatrix();
    }

    @Test
    public void constructorShouldSetMembersWithTheExpectedValues() throws Exception {
        BlueBomber blueBomber = new BlueBomber(5, 4);

        // check members value.
        assertThat(blueBomber.getxMap()).isEqualTo(5);
        assertThat(blueBomber.getyMap()).isEqualTo(4);
        assertThat(blueBomber.getSpriteType()).isEqualTo(SpriteType.BOMBER);
        assertThat(blueBomber.getRefreshTime()).isEqualTo(BlueBomber.REFRESH_TIME);
        assertThat(blueBomber.getActingTime()).isEqualTo(BlueBomber.ACTING_TIME);
        assertThat(blueBomber.getInvincibilityTime()).isEqualTo(BlueBomber.INVINCIBILITY_TIME);
    }

    @Test
    public void isTimeToActShouldReturnFalse() throws Exception {
        BlueBomber blueBomber = new BlueBomber(5, 4);

        // mock CurrentTimeSupplier class to set currentTimeMillis to 1000ms.
        CurrentTimeSupplier currentTimeSupplier = mock(CurrentTimeSupplier.class);
        Mockito.when(currentTimeSupplier.get()).thenReturn(Instant.ofEpochMilli(10000L));
        blueBomber.setCurrentTimeSupplier(currentTimeSupplier);

        // current time - last refresh time - 1 < 1000ms -> should return false.
        blueBomber.setLastActionTs(10000L - BlueBomber.ACTING_TIME + 1);

        // call & check.
        assertThat(blueBomber.isTimeToAct()).isFalse();
    }

    @Test
    public void isTimeToActShouldReturnTrue() throws Exception {
        BlueBomber blueBomber = new BlueBomber(5, 4);

        // mock CurrentTimeSupplier class to set currentTimeMillis to 1000ms.
        CurrentTimeSupplier currentTimeSupplier = mock(CurrentTimeSupplier.class);
        Mockito.when(currentTimeSupplier.get()).thenReturn(Instant.ofEpochMilli(10000L));
        blueBomber.setCurrentTimeSupplier(currentTimeSupplier);

        // current time - last refresh time >= 1000ms -> should return false.
        blueBomber.setLastRefreshTs(10000L - BlueBomber.ACTING_TIME);

        // call & check.
        assertThat(blueBomber.isTimeToAct()).isTrue();
    }

    @Test
    public void isInvincibleShouldReturnTheExpectedValues() throws Exception {
        BlueBomber blueBomber = new BlueBomber(10, 20);

        // mock CurrentTimeSupplier class to set currentTimeMillis to 1000ms.
        CurrentTimeSupplier currentTimeSupplier = mock(CurrentTimeSupplier.class);
        Mockito.when(currentTimeSupplier.get()).thenReturn(Instant.ofEpochMilli(10000L));
        blueBomber.setCurrentTimeSupplier(currentTimeSupplier);

        // set test.
        blueBomber.setCurAction(ACTION_WAITING);

        // 1st case: should continue to be invincible.
        blueBomber.setLastInvincibilityTs(10000L - INVINCIBILITY_TIME); // limit not reached.
        assertThat(blueBomber.isInvincible()).isTrue();

        // 2nd case: should stop being invincible.
        blueBomber.setLastInvincibilityTs(10000L - INVINCIBILITY_TIME - 1); // limit reached.
        assertThat(blueBomber.isInvincible()).isFalse();
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

    @Test
    public void isFinishedShouldReturnTrue() throws Exception {
        BlueBomber blueBomber = new BlueBomber(15, 30);

        // set test.
        blueBomber.setCurAction(ACTION_DYING);
        blueBomber.updateSprite();
        blueBomber.setCurImageIdx(ImagesLoader.NB_BOMBER_DEATH_FRAME - 1);

        // call & check.
        assertThat(blueBomber.isFinished()).isTrue();
    }

    @Test
    public void isFinishedWithACurActionDifferentOfDyingShouldReturnFalse() throws Exception {
        BlueBomber blueBomber = new BlueBomber(15, 30);

        // set test.
        blueBomber.setCurAction(ACTION_WALKING);
        blueBomber.setCurDirection(Direction.NORTH);
        blueBomber.updateSprite();
        blueBomber.setCurImageIdx(ImagesLoader.NB_BOMBER_DEATH_FRAME - 1);

        // call & check.
        assertThat(blueBomber.isFinished()).isFalse();
    }

    @Test
    public void isFinishedWithACurImageIdxDifferentOfNbImagesMinus1ShouldReturnFalse() throws Exception {
        BlueBomber blueBomber = new BlueBomber(15, 30);

        // set test.
        blueBomber.setCurAction(ACTION_DYING);
        blueBomber.updateSprite();
        blueBomber.setCurImageIdx(0);

        // call & check.
        assertThat(blueBomber.isFinished()).isFalse();
    }
}
