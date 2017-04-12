package sprite.nomad;

import images.ImagesLoader;
import org.assertj.core.api.WithAssertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import sprite.SpriteAction;
import sprite.SpriteType;
import utils.CurrentTimeSupplier;
import utils.Direction;

import java.io.IOException;
import java.time.Instant;

import static images.ImagesLoader.NB_BOMBER_WAIT_FRAME;
import static org.mockito.Mockito.mock;
import static sprite.SpriteAction.*;
import static sprite.nomad.BlueBomber.INVINCIBILITY_TIME;

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
        assertThat(blueBomber.getSpriteType()).isEqualTo(SpriteType.TYPE_SPRITE_BOMBER);
        assertThat(blueBomber.getRefreshTime()).isEqualTo(BlueBomber.REFRESH_TIME);
        assertThat(blueBomber.getActingTime()).isEqualTo(BlueBomber.DEFAULT_ACTING_TIME);
        assertThat(blueBomber.getInvincibilityTime()).isEqualTo(BlueBomber.INVINCIBILITY_TIME);
    }

    @Test
    public void setCurSpriteActionWithAnAllowedActionShouldSetTheExpectedMember() throws Exception {
        BlueBomber blueBomber = new BlueBomber(5, 4);
        blueBomber.setCurSpriteAction(SpriteAction.ACTION_WALKING);
        assertThat(blueBomber.getCurSpriteAction()).isEqualTo(SpriteAction.ACTION_WALKING);
    }

    @Test
    public void setCurSpriteActionWithANotAllowedActionShouldThrowAnException() throws Exception {
        BlueBomber blueBomber = new BlueBomber(5, 4);
        assertThatThrownBy(() -> blueBomber.setCurSpriteAction(SpriteAction.ACTION_BREAKING))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("'breaking' action is not allowed here.");
    }

    @Test
    public void setLastSpriteActionWithAnAllowedActionShouldSetTheExpectedMember() throws Exception {
        BlueBomber blueBomber = new BlueBomber(5, 4);
        blueBomber.setLastSpriteAction(SpriteAction.ACTION_WALKING);
        assertThat(blueBomber.getLastSpriteAction()).isEqualTo(SpriteAction.ACTION_WALKING);
    }

    @Test
    public void setLastSpriteActionWithANotAllowedActionShouldThrowAnException() throws Exception {
        BlueBomber blueBomber = new BlueBomber(5, 4);
        assertThatThrownBy(() -> blueBomber.setLastSpriteAction(SpriteAction.ACTION_BREAKING))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("'breaking' action is not allowed here.");
    }

    @Test
    public void isTimeToActShouldReturnFalse() throws Exception {
        BlueBomber blueBomber = new BlueBomber(5, 4);

        // mock CurrentTimeSupplier class to set currentTimeMillis to 1000ms.
        CurrentTimeSupplier currentTimeSupplier = mock(CurrentTimeSupplier.class);
        Mockito.when(currentTimeSupplier.get()).thenReturn(Instant.ofEpochMilli(10000L));
        blueBomber.setCurrentTimeSupplier(currentTimeSupplier);

        // current time - last refresh time - 1 < 1000ms -> should return false.
        blueBomber.setLastActionTs(10000L - BlueBomber.DEFAULT_ACTING_TIME + 1);

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
        blueBomber.setLastRefreshTs(10000L - BlueBomber.DEFAULT_ACTING_TIME);

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
        blueBomber.setCurSpriteAction(ACTION_WAITING);

        // 1st case: should continue to be invincible.
        blueBomber.setLastInvincibilityTs(10000L - INVINCIBILITY_TIME); // limit not reached.
        assertThat(blueBomber.isInvincible()).isTrue();

        // 2nd case: should stop being invincible.
        blueBomber.setLastInvincibilityTs(10000L - INVINCIBILITY_TIME - 1); // limit reached.
        assertThat(blueBomber.isInvincible()).isFalse();
    }

    @Test
    public void setInvincibleShouldSetTheNomadinvincible() throws Exception {
        BlueBomber blueBomber = new BlueBomber(10, 20);
        blueBomber.setInvincible();
        assertThat(blueBomber.isInvincible()).isTrue();
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
    public void updateImageWithANewStatusShouldSetPaintedAtLeastOneTimeToFalseAndCurImageIdxTo0() throws Exception {
        BlueBomber blueBomber = new BlueBomber(5, 4);
        BlueBomber spyedBlueBomber = Mockito.spy(blueBomber);
        Mockito.when(spyedBlueBomber.hasActionChanged()).thenReturn(true);
        Mockito.when(spyedBlueBomber.isTimeToRefresh()).thenReturn(false);

        // set nomad.
        spyedBlueBomber.setImages(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWaitMatrixRowIdx]);
        spyedBlueBomber.setNbImages(NB_BOMBER_WAIT_FRAME);
        spyedBlueBomber.setPaintedAtLeastOneTime(true);
        spyedBlueBomber.setCurImageIdx(1); // index != 0.

        // call & check.
        spyedBlueBomber.updateImage();
        assertThat(spyedBlueBomber.getCurImageIdx()).isEqualTo(0);
        assertThat(spyedBlueBomber.isPaintedAtLeastOneTime()).isFalse();
        assertThat(spyedBlueBomber.getCurImage())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWaitMatrixRowIdx][0]);
    }

    @Test
    public void updateImageWithTheLastImageShouldSetPaintedAtLeastOneTimeToTrueAndCurImageIdxTo0() throws Exception {
        BlueBomber blueBomber = new BlueBomber(5, 4);
        BlueBomber spyedBlueBomber = Mockito.spy(blueBomber);
        Mockito.when(spyedBlueBomber.hasActionChanged()).thenReturn(false);
        Mockito.when(spyedBlueBomber.isTimeToRefresh()).thenReturn(true);

        // set nomad.
        spyedBlueBomber.setImages(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWaitMatrixRowIdx]);
        spyedBlueBomber.setNbImages(NB_BOMBER_WAIT_FRAME);
        spyedBlueBomber.setPaintedAtLeastOneTime(false);
        spyedBlueBomber.setCurImageIdx(NB_BOMBER_WAIT_FRAME - 1); // last sprite's image.

        // call & check.
        spyedBlueBomber.updateImage();
        assertThat(spyedBlueBomber.getCurImageIdx()).isEqualTo(0);
        assertThat(spyedBlueBomber.isPaintedAtLeastOneTime()).isTrue();
        assertThat(spyedBlueBomber.getCurImage())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWaitMatrixRowIdx][0]);
    }

    @Test
    public void isFinishedShouldReturnTrue() throws Exception {
        BlueBomber blueBomber = new BlueBomber(5, 4);
        BlueBomber spyedBlueBomber = Mockito.spy(blueBomber);
        Mockito.when(spyedBlueBomber.hasActionChanged()).thenReturn(false);
        Mockito.when(spyedBlueBomber.isTimeToRefresh()).thenReturn(true);

        // set test.
        spyedBlueBomber.setCurSpriteAction(ACTION_DYING);
        spyedBlueBomber.setCurImageIdx(ImagesLoader.NB_BOMBER_DEATH_FRAME - 1);
        spyedBlueBomber.updateImage();

        // call & check.
        assertThat(spyedBlueBomber.isFinished()).isTrue();
    }

    @Test
    public void isFinishedWithACurActionDifferentOfDyingShouldReturnFalse() throws Exception {
        BlueBomber blueBomber = new BlueBomber(15, 30);

        // set test.
        blueBomber.setCurSpriteAction(ACTION_WALKING);
        blueBomber.setCurDirection(Direction.DIRECTION_NORTH);
        blueBomber.updateSprite();
        blueBomber.setCurImageIdx(ImagesLoader.NB_BOMBER_DEATH_FRAME - 1);

        // call & check.
        assertThat(blueBomber.isFinished()).isFalse();
    }

    @Test
    public void isFinishedWithACurImageIdxDifferentOfNbImagesMinus1ShouldReturnFalse() throws Exception {
        BlueBomber blueBomber = new BlueBomber(15, 30);

        // set test.
        blueBomber.setCurSpriteAction(ACTION_DYING);
        blueBomber.updateSprite();
        blueBomber.setCurImageIdx(0);

        // call & check.
        assertThat(blueBomber.isFinished()).isFalse();
    }
}
