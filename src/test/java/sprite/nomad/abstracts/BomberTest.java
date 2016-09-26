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

import static org.mockito.Mockito.mock;
import static sprite.nomad.BlueBomber.INVINCIBLE_TIME;
import static sprite.nomad.abstracts.Bomber.status.*;

public class BomberTest implements WithAssertions {

    @Before
    public void fillImagesMatrix() throws IOException {
        ImagesLoader.fillImagesMatrix();
    }

    @Test
    public void constructorShouldSetMembersWithTheExpectedValues() throws Exception {
        BlueBomber blueBomber = new BlueBomber(15, 30);

        // check members value.
        assertThat(blueBomber.getXMap()).isEqualTo(15);
        assertThat(blueBomber.getYMap()).isEqualTo(30);
        assertThat(blueBomber.getInitialXMap()).isEqualTo(blueBomber.getXMap());
        assertThat(blueBomber.getInitialYMap()).isEqualTo(blueBomber.getYMap());
        assertThat(blueBomber.getCurStatus()).isEqualTo(STATUS_WAITING);
        assertThat(blueBomber.getLastStatus()).isEqualTo(STATUS_WAITING);
        assertThat(blueBomber.getDeathImages()).
                isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberDeathMatrixRowIdx]);
        assertThat(blueBomber.getNbDeathFrame()).isEqualTo(ImagesLoader.NB_BOMBER_DEATH_FRAME);
        assertThat(blueBomber.getWaitImages()).
                isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWaitMatrixRowIdx]);
        assertThat(blueBomber.getNbWaitFrame()).isEqualTo(ImagesLoader.NB_BOMBER_WAIT_FRAME);
        assertThat(blueBomber.getWalkBackImages()).
                isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWalkBackMatrixRowIdx]);
        assertThat(blueBomber.getWalkFrontImages()).
                isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWalkFrontMatrixRowIdx]);
        assertThat(blueBomber.getWalkLeftImages()).
                isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWalkLeftMatrixRowIdx]);
        assertThat(blueBomber.getWalkRightImages()).
                isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWalkRightMatrixRowIdx]);
        assertThat(blueBomber.getNbWalkFrame()).isEqualTo(ImagesLoader.NB_BOMBER_WALK_FRAME);
        assertThat(blueBomber.getWinImages()).
                isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWinMatrixRowIdx]);
        assertThat(blueBomber.getNbWinFrame()).isEqualTo(ImagesLoader.NB_BOMBER_WIN_FRAME);
        assertThat(blueBomber.getRefreshTime()).isEqualTo(BlueBomber.REFRESH_TIME);
        assertThat(blueBomber.getMoveTime()).isEqualTo(BlueBomber.MOVING_TIME);
        assertThat(blueBomber.getInvincibilityTime()).isEqualTo(BlueBomber.INVINCIBLE_TIME);
    }

    @Test
    public void updateStatusShouldReturnFalse() throws Exception {
        BlueBomber blueBomber = new BlueBomber(15, 30);

        // set test.
        blueBomber.setCurStatus(STATUS_WALKING_LEFT);
        blueBomber.setLastStatus(STATUS_WALKING_LEFT); // last status == current status.
        blueBomber.setLastRefreshTs(1); // NOT the first call.

        // call & check.
        assertThat(blueBomber.updateStatus()).isFalse();
    }

    @Test
    public void updateStatusTheFirstTimeShouldReturnTrue() throws Exception {
        BlueBomber blueBomber = new BlueBomber(15, 30);

        // set test.
        blueBomber.setCurStatus(STATUS_WALKING_LEFT);
        blueBomber.setLastStatus(STATUS_WALKING_LEFT); // last status == current status.
        blueBomber.setLastRefreshTs(0); // first call.

        // call & check.
        assertThat(blueBomber.updateStatus()).isTrue();
    }

    @Test
    public void updateStatusWithADifferentStatusShouldReturnTrue() throws Exception {
        BlueBomber blueBomber = new BlueBomber(15, 30);

        // set test.
        blueBomber.setCurStatus(STATUS_WALKING_LEFT);
        blueBomber.setLastStatus(STATUS_WALKING_RIGHT); // last status != current status.
        blueBomber.setLastRefreshTs(1); // NOT the first call.

        // call & check.
        assertThat(blueBomber.updateStatus()).isTrue();
    }

    @Test
    public void updateSpriteShouldSetTheExpectedMember() throws Exception {
        BlueBomber blueBomber = new BlueBomber(15, 30);

        // dying.
        blueBomber.setCurStatus(Bomber.status.STATUS_DYING);
        blueBomber.updateSprite();
        assertThat(blueBomber.images)
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberDeathMatrixRowIdx]);
        assertThat(blueBomber.nbImages).isEqualTo(ImagesLoader.NB_BOMBER_DEATH_FRAME);

        // wait.
        blueBomber.setCurStatus(Bomber.status.STATUS_WAITING);
        blueBomber.updateSprite();
        assertThat(blueBomber.images)
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWaitMatrixRowIdx]);
        assertThat(blueBomber.nbImages).isEqualTo(ImagesLoader.NB_BOMBER_WAIT_FRAME);

        // walking back.
        blueBomber.setCurStatus(Bomber.status.STATUS_WALKING_BACK);
        blueBomber.updateSprite();
        assertThat(blueBomber.images)
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWalkBackMatrixRowIdx]);
        assertThat(blueBomber.nbImages).isEqualTo(ImagesLoader.NB_BOMBER_WALK_FRAME);

        // walking front.
        blueBomber.setCurStatus(Bomber.status.STATUS_WALKING_FRONT);
        blueBomber.updateSprite();
        assertThat(blueBomber.images)
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWalkFrontMatrixRowIdx]);
        assertThat(blueBomber.nbImages).isEqualTo(ImagesLoader.NB_BOMBER_WALK_FRAME);

        // walking left.
        blueBomber.setCurStatus(Bomber.status.STATUS_WALKING_LEFT);
        blueBomber.updateSprite();
        assertThat(blueBomber.images)
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWalkLeftMatrixRowIdx]);
        assertThat(blueBomber.nbImages).isEqualTo(ImagesLoader.NB_BOMBER_WALK_FRAME);

        // walking right.
        blueBomber.setCurStatus(Bomber.status.STATUS_WALKING_RIGHT);
        blueBomber.updateSprite();
        assertThat(blueBomber.images)
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWalkRightMatrixRowIdx]);
        assertThat(blueBomber.nbImages).isEqualTo(ImagesLoader.NB_BOMBER_WALK_FRAME);

        // win.
        blueBomber.setCurStatus(Bomber.status.STATUS_WON);
        blueBomber.updateSprite();
        assertThat(blueBomber.images)
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWinMatrixRowIdx]);
        assertThat(blueBomber.nbImages).isEqualTo(ImagesLoader.NB_BOMBER_WIN_FRAME);
    }

    @Test
    public void isFinishedShouldReturnTrue() throws Exception {
        BlueBomber blueBomber = new BlueBomber(15, 30);

        // set test.
        blueBomber.setCurStatus(STATUS_DYING);
        blueBomber.updateSprite();
        blueBomber.curImageIdx = ImagesLoader.NB_BOMBER_DEATH_FRAME - 1;

        // call & check.
        assertThat(blueBomber.isFinished()).isTrue();
    }

    @Test
    public void isFinishedWithAStatusDifferentOfDyingShouldReturnFalse() throws Exception {
        BlueBomber blueBomber = new BlueBomber(15, 30);

        // set test.
        blueBomber.setCurStatus(STATUS_WALKING_RIGHT);
        blueBomber.updateSprite();
        blueBomber.curImageIdx = ImagesLoader.NB_BOMBER_DEATH_FRAME - 1;

        // call & check.
        assertThat(blueBomber.isFinished()).isFalse();
    }

    @Test
    public void isFinishedWithACurImageIdxDifferentOfNbImagesMinus1ShouldReturnFalse() throws Exception {
        BlueBomber blueBomber = new BlueBomber(15, 30);

        // set test.
        blueBomber.setCurStatus(STATUS_DYING);
        blueBomber.updateSprite();
        blueBomber.curImageIdx = 0;

        // call & check.
        assertThat(blueBomber.isFinished()).isFalse();
    }

    @Test
    public void initShouldSetMembersWithTheExpectedValues() throws Exception {
        BlueBomber blueBomber = new BlueBomber(10, 20);

        // mock CurrentTimeSupplier class to set currentTimeMillis to 1000ms.
        CurrentTimeSupplier currentTimeSupplier = mock(CurrentTimeSupplier.class);
        Mockito.when(currentTimeSupplier.get()).thenReturn(Instant.ofEpochMilli(1000L));
        blueBomber.setCurrentTimeSupplier(currentTimeSupplier);

        // set test (update members with value != than the expected ones).
        blueBomber.setCurStatus(STATUS_DYING);
        blueBomber.setXMap(30);
        blueBomber.setYMap(40);
        blueBomber.setInvincible(false);

        // init bomber and check values.
        blueBomber.init();
        assertThat(blueBomber.getXMap()).isEqualTo(blueBomber.getInitialXMap());
        assertThat(blueBomber.getYMap()).isEqualTo(blueBomber.getInitialYMap());
        assertThat(blueBomber.getCurStatus()).isEqualTo(STATUS_WAITING);
        assertThat(blueBomber.isInvincible()).isEqualTo(true);
        assertThat(blueBomber.getLastInvincibilityTs()).isEqualTo(1000L);
    }

    @Test
    public void updateImageWhenInvincibleTimeReachedShouldSetIsInvincibleToFalse() throws Exception {
        BlueBomber blueBomber = new BlueBomber(10, 20);

        // mock CurrentTimeSupplier class to set currentTimeMillis to 1000ms.
        CurrentTimeSupplier currentTimeSupplier = mock(CurrentTimeSupplier.class);
        Mockito.when(currentTimeSupplier.get()).thenReturn(Instant.ofEpochMilli(10000L));
        blueBomber.setCurrentTimeSupplier(currentTimeSupplier);

        // set test.
        blueBomber.setCurStatus(STATUS_WAITING);
        blueBomber.setInvincible(true); // set to invincible.

        // 1st case: should continue to be invincible.
        blueBomber.setLastInvincibilityTs(10000L - INVINCIBLE_TIME); // limit not reached.
        blueBomber.updateImage();
        assertThat(blueBomber.isInvincible()).isTrue();

        // 2nd case: should stop being invincible.
        blueBomber.setLastInvincibilityTs(10000L - INVINCIBLE_TIME - 1); // limit reached.
        blueBomber.updateImage();
        assertThat(blueBomber.isInvincible()).isFalse();
    }
}