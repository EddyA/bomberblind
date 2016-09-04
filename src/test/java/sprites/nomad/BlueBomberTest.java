package sprites.nomad;

import images.ImagesLoader;
import org.assertj.core.api.WithAssertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import utils.CurrentTimeSupplier;

import java.io.IOException;
import java.time.Instant;

import static org.mockito.Mockito.mock;
import static sprites.nomad.BlueBomber.INVINCIBLE_TIME;
import static sprites.nomad.Bomber.status.*;

public class BlueBomberTest implements WithAssertions {

    @Before
    public void fillImagesMatrix() throws IOException {
        ImagesLoader.fillImagesMatrix();
    }

    @Test
    public void setAndGetFunctionsShouldSetAndReturnExpectedValues() throws Exception {
        // test several cases in a single function to avoid calling fillImagesMatrix() several times.

        // create a blue bomber.
        BlueBomber blueBomber = new BlueBomber(10, 20);

        // check set and get functions.
        // - status:
        blueBomber.status = STATUS_WAIT; // initial status.
        blueBomber.setStatus(STATUS_WALK_LEFT); // update the status.
        assertThat(blueBomber.status).isEqualTo(STATUS_WALK_LEFT); // check value after the update.
        assertThat(blueBomber.getStatus()).isEqualTo(blueBomber.status); // check value using getter.

        // - isInvicible:
        blueBomber.isInvincible = false;
        assertThat(blueBomber.isInvincible()).isEqualTo(false);
        blueBomber.isInvincible = true;
        assertThat(blueBomber.isInvincible()).isEqualTo(true);

        // - isInvicible:
        blueBomber.isFinished = false;
        assertThat(blueBomber.isFinished()).isEqualTo(false);
        blueBomber.isFinished = true;
        assertThat(blueBomber.isFinished()).isEqualTo(true);

        // - curImage:
        blueBomber.curImage = ImagesLoader.createImage("/images/icon.gif");
        assertThat(blueBomber.getCurImage()).isEqualTo(blueBomber.curImage); // check the status.
    }

    @Test
    public void bomberBlueShouldSetMembersWithExpectedValues() throws Exception {

        // create a blue bomber.
        BlueBomber blueBomber = new BlueBomber(10, 20);

        // check members value.
        assertThat(blueBomber.getXMap()).isEqualTo(10);
        assertThat(blueBomber.getYMap()).isEqualTo(20);
        assertThat(blueBomber.initialXMap).isEqualTo(blueBomber.getXMap());
        assertThat(blueBomber.initialYMap).isEqualTo(blueBomber.getYMap());
        assertThat(blueBomber.getStatus()).isEqualTo(STATUS_WAIT);
        assertThat(blueBomber.lastStatus).isEqualTo(STATUS_WAIT);
        assertThat(blueBomber.deathImages).
                isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberDeathMatrixRowIdx]);
        assertThat(blueBomber.nbDeathFrame).isEqualTo(ImagesLoader.NB_BOMBER_DEATH_FRAME);
        assertThat(blueBomber.waitImages).
                isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWaitMatrixRowIdx]);
        assertThat(blueBomber.nbWaitFrame).isEqualTo(ImagesLoader.NB_BOMBER_WAIT_FRAME);
        assertThat(blueBomber.walkBackImages).
                isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWalkBackMatrixRowIdx]);
        assertThat(blueBomber.walkFrontImages).
                isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWalkFrontMatrixRowIdx]);
        assertThat(blueBomber.walkLeftImages).
                isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWalkLeftMatrixRowIdx]);
        assertThat(blueBomber.walkRightImages).
                isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWalkRightMatrixRowIdx]);
        assertThat(blueBomber.nbWalkFrame).isEqualTo(ImagesLoader.NB_BOMBER_WALK_FRAME);
        assertThat(blueBomber.winImages).
                isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWinMatrixRowIdx]);
        assertThat(blueBomber.nbWinFrame).isEqualTo(ImagesLoader.NB_BOMBER_WIN_FRAME);
        assertThat(blueBomber.refreshTime).isEqualTo(BlueBomber.REFRESH_TIME);
        assertThat(blueBomber.lastRefreshTs).isEqualTo(0);
        assertThat(blueBomber.invincibilityTime).isEqualTo(INVINCIBLE_TIME);
    }

    @Test
    public void initStatementShouldSetMembersWithExpectedValues() throws Exception {

        // create a blue bomber.
        BlueBomber blueBomber = new BlueBomber(10, 20);

        // update members value (!= than expected values).
        blueBomber.status = STATUS_DEAD;
        blueBomber.setXMap(30);
        blueBomber.setYMap(40);
        blueBomber.isFinished = true;
        blueBomber.isInvincible = false;

        // mock CurrentTimeSupplier class to set currentTimeMillis to 1000ms.
        CurrentTimeSupplier currentTimeSupplier = mock(CurrentTimeSupplier.class);
        Mockito.when(currentTimeSupplier.get()).thenReturn(Instant.ofEpochMilli(1000L));
        blueBomber.currentTimeSupplier = currentTimeSupplier;

        // init bomber and check values.
        blueBomber.initStatement();
        assertThat(blueBomber.getXMap()).isEqualTo(blueBomber.initialXMap);
        assertThat(blueBomber.getYMap()).isEqualTo(blueBomber.initialYMap);
        assertThat(blueBomber.status).isEqualTo(STATUS_WAIT);
        assertThat(blueBomber.isFinished).isEqualTo(false);
        assertThat(blueBomber.isInvincible).isEqualTo(true);
        assertThat(blueBomber.lastInvincibilityTs).isEqualTo(1000L);
    }

    @Test
    public void updateImageWithNewStatusShouldSetCurImageWith1stImageOfTheRelativeArray() throws Exception {

        // create a blue bomber.
        BlueBomber blueBomber = new BlueBomber(10, 20);

        // check image array according to the status.
        // - dead:
        blueBomber.setStatus(STATUS_DEAD);
        blueBomber.updateImage();
        assertThat(blueBomber.getCurImage())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberDeathMatrixRowIdx][0]);
        // - wait:
        blueBomber.setStatus(STATUS_WAIT);
        blueBomber.updateImage();
        assertThat(blueBomber.getCurImage())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWaitMatrixRowIdx][0]);
        // - walk back:
        blueBomber.setStatus(STATUS_WALK_BACK);
        blueBomber.updateImage();
        assertThat(blueBomber.getCurImage())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWalkBackMatrixRowIdx][0]);
        // - walk front:
        blueBomber.setStatus(STATUS_WALK_FRONT);
        blueBomber.updateImage();
        assertThat(blueBomber.getCurImage())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWalkFrontMatrixRowIdx][0]);
        // - walk left:
        blueBomber.setStatus(STATUS_WALK_LEFT);
        blueBomber.updateImage();
        assertThat(blueBomber.getCurImage())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWalkLeftMatrixRowIdx][0]);
        // - walk right:
        blueBomber.setStatus(STATUS_WALK_RIGHT);
        blueBomber.updateImage();
        assertThat(blueBomber.getCurImage())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWalkRightMatrixRowIdx][0]);
        // - win:
        blueBomber.setStatus(STATUS_WIN);
        blueBomber.updateImage();
        assertThat(blueBomber.getCurImage())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWinMatrixRowIdx][0]);
    }

    @Test
    public void updateImageWithSingleStatusShouldSetCurImageWithExpectedValues() throws Exception {
        // create a blue bomber.
        BlueBomber blueBomber = new BlueBomber(10, 20);

        // mock CurrentTimeSupplier class to set currentTimeMillis to 1000ms.
        CurrentTimeSupplier currentTimeSupplier = mock(CurrentTimeSupplier.class);
        Mockito.when(currentTimeSupplier.get()).thenReturn(Instant.ofEpochMilli(1000L));
        blueBomber.currentTimeSupplier = currentTimeSupplier;

        // set status.
        blueBomber.setStatus(STATUS_WALK_BACK);

        // check value according to "lastRefreshTs":
        // - 1st update -> 1st image.
        blueBomber.updateImage();
        assertThat(blueBomber.getCurImage())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWalkBackMatrixRowIdx][0]);

        // 2nd update -> // current time - last refresh time < 100ms -> image should not change.
        blueBomber.lastRefreshTs = 1000L;
        blueBomber.updateImage();
        assertThat(blueBomber.getCurImage())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWalkBackMatrixRowIdx][0]);

        // 3rd, 4th and 5th update -> // current time - last refresh time > 100ms -> image should change.
        blueBomber.lastRefreshTs = 800L;
        blueBomber.updateImage();
        assertThat(blueBomber.getCurImage())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWalkBackMatrixRowIdx][1]);
        blueBomber.lastRefreshTs = 800L;
        blueBomber.updateImage();
        assertThat(blueBomber.getCurImage())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWalkBackMatrixRowIdx][2]);
        blueBomber.lastRefreshTs = 800L;
        blueBomber.updateImage();
        assertThat(blueBomber.getCurImage())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWalkBackMatrixRowIdx][3]);

        // 6th update -> // current time - last refresh time > 100ms -> image should change, go back to the 1st one.
        blueBomber.lastRefreshTs = 800L;
        blueBomber.updateImage();
        assertThat(blueBomber.getCurImage())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWalkBackMatrixRowIdx][0]);
    }

    @Test
    public void updateImageWithDeadStatusShouldSetIsFinishedToTrueWhenSpriteIsFinished() throws Exception {
        // create a blue bomber.
        BlueBomber blueBomber = new BlueBomber(10, 20);

        // mock CurrentTimeSupplier class to set currentTimeMillis to 1000ms.
        CurrentTimeSupplier currentTimeSupplier = mock(CurrentTimeSupplier.class);
        Mockito.when(currentTimeSupplier.get()).thenReturn(Instant.ofEpochMilli(1000L));
        blueBomber.currentTimeSupplier = currentTimeSupplier;

        // set dead status.
        blueBomber.setStatus(STATUS_DEAD);

        // for each step of the dead sprite:
        // - check 'curImage' value,
        // - check 'isFinished' value.
        for (int iterIdx = 0; iterIdx < blueBomber.nbDeathFrame; iterIdx++) {
            blueBomber.lastRefreshTs = 800L;
            blueBomber.updateImage();
            assertThat(blueBomber.getCurImage())
                    .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberDeathMatrixRowIdx][iterIdx]);
            assertThat(blueBomber.isFinished()).isFalse();
        }

        // the last update should set 'isFinished' to true.
        blueBomber.lastRefreshTs = 800L;
        blueBomber.updateImage();
        assertThat(blueBomber.isFinished()).isTrue();
    }

    @Test
    public void updateImageWhenInvincibleShouldStopInvicibleTimeReached() throws Exception {
        // create a blue bomber.
        BlueBomber blueBomber = new BlueBomber(10, 20);

        // mock CurrentTimeSupplier class to set currentTimeMillis to 1000ms.
        CurrentTimeSupplier currentTimeSupplier = mock(CurrentTimeSupplier.class);
        Mockito.when(currentTimeSupplier.get()).thenReturn(Instant.ofEpochMilli(10000L));
        blueBomber.currentTimeSupplier = currentTimeSupplier;

        // set status.
        blueBomber.setStatus(STATUS_WAIT);
        blueBomber.isInvincible = true;

        // 1st case: should continue being invincible.
        blueBomber.lastInvincibilityTs = 10000L - INVINCIBLE_TIME; // limit before stopping invincibility.
        blueBomber.updateImage();
        assertThat(blueBomber.isInvincible()).isTrue();

        // 2nd case: should stop being invincible.
        blueBomber.lastInvincibilityTs = 10000L - INVINCIBLE_TIME - 1; // limit to stop invincibility.
        blueBomber.updateImage();
        assertThat(blueBomber.isInvincible()).isFalse();
    }
}