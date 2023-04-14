package sprite.nomad;

import images.ImagesLoader;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

class NomadTest implements WithAssertions {

    @BeforeEach
    void fillImagesMatrix() throws IOException {
        ImagesLoader.fillImagesMatrix();
    }

    @Test
    void constructorShouldSetMembersWithTheExpectedValues() {
        BlueBomber blueBomber = new BlueBomber(5, 4);

        // check members value.
        assertThat(blueBomber.getXMap()).isEqualTo(5);
        assertThat(blueBomber.getYMap()).isEqualTo(4);
        assertThat(blueBomber.getSpriteType()).isEqualTo(SpriteType.TYPE_SPRITE_BOMBER);
        assertThat(blueBomber.getRefreshTime()).isEqualTo(BlueBomber.REFRESH_TIME);
        assertThat(blueBomber.getActingTime()).isEqualTo(BlueBomber.DEFAULT_ACTING_TIME);
        assertThat(blueBomber.getInvincibilityTime()).isEqualTo(BlueBomber.INVINCIBILITY_TIME);
    }

    @Test
    void setCurSpriteActionWithAnAllowedActionShouldSetTheExpectedMember() {
        BlueBomber blueBomber = new BlueBomber(5, 4);
        blueBomber.setCurSpriteAction(SpriteAction.ACTION_WALKING);
        assertThat(blueBomber.getCurSpriteAction()).isEqualTo(SpriteAction.ACTION_WALKING);
    }

    @Test
    void setCurSpriteActionWithANotAllowedActionShouldThrowAnException() {
        BlueBomber blueBomber = new BlueBomber(5, 4);
        assertThatThrownBy(() -> blueBomber.setCurSpriteAction(SpriteAction.ACTION_BREAKING))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("'breaking' action is not allowed here.");
    }

    @Test
    void setLastSpriteActionWithAnAllowedActionShouldSetTheExpectedMember() {
        BlueBomber blueBomber = new BlueBomber(5, 4);
        blueBomber.setLastSpriteAction(SpriteAction.ACTION_WALKING);
        assertThat(blueBomber.getLastSpriteAction()).isEqualTo(SpriteAction.ACTION_WALKING);
    }

    @Test
    void setLastSpriteActionWithANotAllowedActionShouldThrowAnException() {
        BlueBomber blueBomber = new BlueBomber(5, 4);
        assertThatThrownBy(() -> blueBomber.setLastSpriteAction(SpriteAction.ACTION_BREAKING))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("'breaking' action is not allowed here.");
    }

    @Test
    void isTimeToActShouldReturnFalse() {
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
    void isTimeToActShouldReturnTrue() {
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
    void isInvincibleShouldReturnTheExpectedValues() {
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
    void setInvincibleShouldSetTheNomadinvincible() {
        BlueBomber blueBomber = new BlueBomber(10, 20);
        blueBomber.setInvincible();
        assertThat(blueBomber.isInvincible()).isTrue();
    }

    @Test
    void updateImageShouldDoNothing() {
        BlueBomber blueBomber = new BlueBomber(5, 4);
        BlueBomber spiedBlueBomber = Mockito.spy(blueBomber);
        Mockito.when(spiedBlueBomber.hasActionChanged()).thenReturn(false);
        Mockito.when(spiedBlueBomber.isTimeToRefresh()).thenReturn(false);

        // set nomad.
        spiedBlueBomber.setImages(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWaitMatrixRowIdx]);
        spiedBlueBomber.setNbImages(NB_BOMBER_WAIT_FRAME);
        spiedBlueBomber.setCurImageIdx(1);

        // call & check.
        spiedBlueBomber.updateImage();
        assertThat(spiedBlueBomber.getCurImageIdx()).isEqualTo(1);
        assertThat(spiedBlueBomber.getCurImage())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWaitMatrixRowIdx][1]);
    }

    @Test
    void updateImageShouldIncreaseCurImageIdx() {
        BlueBomber blueBomber = new BlueBomber(5, 4);
        BlueBomber spiedBlueBomber = Mockito.spy(blueBomber);
        Mockito.when(spiedBlueBomber.hasActionChanged()).thenReturn(false);
        Mockito.when(spiedBlueBomber.isTimeToRefresh()).thenReturn(true);

        // set nomad.
        spiedBlueBomber.setImages(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWaitMatrixRowIdx]);
        spiedBlueBomber.setNbImages(NB_BOMBER_WAIT_FRAME);
        spiedBlueBomber.setCurImageIdx(1);

        // call & check.
        spiedBlueBomber.updateImage();
        assertThat(spiedBlueBomber.getCurImageIdx()).isEqualTo(2);
        assertThat(spiedBlueBomber.getCurImage())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWaitMatrixRowIdx][2]);
    }

    @Test
    void updateImageWithANewStatusShouldSetPaintedAtLeastOneTimeToFalseAndCurImageIdxTo0() {
        BlueBomber blueBomber = new BlueBomber(5, 4);
        BlueBomber spiedBlueBomber = Mockito.spy(blueBomber);
        Mockito.when(spiedBlueBomber.hasActionChanged()).thenReturn(true);
        Mockito.when(spiedBlueBomber.isTimeToRefresh()).thenReturn(false);

        // set nomad.
        spiedBlueBomber.setImages(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWaitMatrixRowIdx]);
        spiedBlueBomber.setNbImages(NB_BOMBER_WAIT_FRAME);
        spiedBlueBomber.setPaintedAtLeastOneTime(true);
        spiedBlueBomber.setCurImageIdx(1); // index != 0.

        // call & check.
        spiedBlueBomber.updateImage();
        assertThat(spiedBlueBomber.getCurImageIdx()).isEqualTo(0);
        assertThat(spiedBlueBomber.isPaintedAtLeastOneTime()).isFalse();
        assertThat(spiedBlueBomber.getCurImage())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWaitMatrixRowIdx][0]);
    }

    @Test
    void updateImageWithTheLastImageShouldSetPaintedAtLeastOneTimeToTrueAndCurImageIdxTo0() {
        BlueBomber blueBomber = new BlueBomber(5, 4);
        BlueBomber spiedBlueBomber = Mockito.spy(blueBomber);
        Mockito.when(spiedBlueBomber.hasActionChanged()).thenReturn(false);
        Mockito.when(spiedBlueBomber.isTimeToRefresh()).thenReturn(true);

        // set nomad.
        spiedBlueBomber.setImages(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWaitMatrixRowIdx]);
        spiedBlueBomber.setNbImages(NB_BOMBER_WAIT_FRAME);
        spiedBlueBomber.setPaintedAtLeastOneTime(false);
        spiedBlueBomber.setCurImageIdx(NB_BOMBER_WAIT_FRAME - 1); // last sprite's image.

        // call & check.
        spiedBlueBomber.updateImage();
        assertThat(spiedBlueBomber.getCurImageIdx()).isEqualTo(0);
        assertThat(spiedBlueBomber.isPaintedAtLeastOneTime()).isTrue();
        assertThat(spiedBlueBomber.getCurImage())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWaitMatrixRowIdx][0]);
    }

    @Test
    void isFinishedShouldReturnTrue() {
        BlueBomber blueBomber = new BlueBomber(5, 4);
        BlueBomber spiedBlueBomber = Mockito.spy(blueBomber);
        Mockito.when(spiedBlueBomber.hasActionChanged()).thenReturn(false);
        Mockito.when(spiedBlueBomber.isTimeToRefresh()).thenReturn(true);

        // set test.
        spiedBlueBomber.setCurSpriteAction(ACTION_DYING);
        spiedBlueBomber.setCurImageIdx(ImagesLoader.NB_BOMBER_DEATH_FRAME - 1);
        spiedBlueBomber.updateImage();

        // call & check.
        assertThat(spiedBlueBomber.isFinished()).isTrue();
    }

    @Test
    void isFinishedWithACurActionDifferentOfDyingShouldReturnFalse() {
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
    void isFinishedWithACurImageIdxDifferentOfNbImagesMinus1ShouldReturnFalse() {
        BlueBomber blueBomber = new BlueBomber(15, 30);

        // set test.
        blueBomber.setCurSpriteAction(ACTION_DYING);
        blueBomber.updateSprite();
        blueBomber.setCurImageIdx(0);

        // call & check.
        assertThat(blueBomber.isFinished()).isFalse();
    }
}
