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

public class BomberTest implements WithAssertions {

    @Before
    public void fillImagesMatrix() throws IOException {
        ImagesLoader.fillImagesMatrix();
    }

    @Test
    public void accessorsShouldReturnTheExpectedValues() throws Exception {
        BlueBomber blueBomber = new BlueBomber(10, 20);

        // - status:
        blueBomber.status = STATUS_WALK_BACK;
        assertThat(blueBomber.getStatus()).isEqualTo(STATUS_WALK_BACK);
        blueBomber.status = STATUS_WALK_LEFT;
        assertThat(blueBomber.getStatus()).isEqualTo(STATUS_WALK_LEFT);

        // - isInvicible:
        blueBomber.isInvincible = false;
        assertThat(blueBomber.isInvincible()).isEqualTo(false);
        blueBomber.isInvincible = true;
        assertThat(blueBomber.isInvincible()).isEqualTo(true);

        // - isFinished:
        blueBomber.isFinished = false;
        assertThat(blueBomber.isFinished()).isEqualTo(false);
        blueBomber.isFinished = true;
        assertThat(blueBomber.isFinished()).isEqualTo(true);

        // - curImage:
        blueBomber.curImage = null;
        assertThat(blueBomber.getCurImage()).isEqualTo(blueBomber.curImage);
        blueBomber.curImage = ImagesLoader.createImage("/images/icon.gif");
        assertThat(blueBomber.getCurImage()).isEqualTo(blueBomber.curImage);
    }

    @Test
    public void mutatorsShouldSetMembersWithTheExpectedValues() throws Exception {
        BlueBomber blueBomber = new BlueBomber(10, 20);

        // - status.
        blueBomber.status = STATUS_WAIT; // initial status.
        blueBomber.setStatus(STATUS_WALK_LEFT); // update the status.
        assertThat(blueBomber.status).isEqualTo(STATUS_WALK_LEFT); // check value after the update.
    }

    @Test
    public void initStatementShouldSetMembersWithTheExpectedValues() throws Exception {
        BlueBomber blueBomber = new BlueBomber(10, 20);

        // update members with value != than the expected one.
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
    public void updateImageWithANewStatusShouldSetCurImageWithThe1stImageOfTheRelativeArray() throws Exception {
        BlueBomber blueBomber = new BlueBomber(10, 20);

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
    public void updateImageWithWalkBackStatusShouldSetCurImageWithTheExpectedValues() throws Exception {
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

        // 3rd, 4th and 5th update -> // current time - last refresh time >= REFRESH_TIME -> image should change.
        blueBomber.lastRefreshTs = 1000L - BlueBomber.REFRESH_TIME;
        blueBomber.updateImage();
        assertThat(blueBomber.getCurImage())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWalkBackMatrixRowIdx][1]);
        blueBomber.lastRefreshTs = 1000L - BlueBomber.REFRESH_TIME;
        blueBomber.updateImage();
        assertThat(blueBomber.getCurImage())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWalkBackMatrixRowIdx][2]);
        blueBomber.lastRefreshTs = 1000L - BlueBomber.REFRESH_TIME;
        blueBomber.updateImage();
        assertThat(blueBomber.getCurImage())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWalkBackMatrixRowIdx][3]);

        // 6th update -> // current time - last refresh time >= REFRESH_TIME -> image should change, go back to the 1st one.
        blueBomber.lastRefreshTs = 1000L - BlueBomber.REFRESH_TIME;
        blueBomber.updateImage();
        assertThat(blueBomber.getCurImage())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWalkBackMatrixRowIdx][0]);
    }

    @Test
    public void updateImageWithDeadStatusShouldSetIsFinishedToTrueWhenTheSpriteIsFinished() throws Exception {
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
            blueBomber.lastRefreshTs = 1000L - BlueBomber.REFRESH_TIME;
            blueBomber.updateImage();
            assertThat(blueBomber.getCurImage())
                    .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberDeathMatrixRowIdx][iterIdx]);
            assertThat(blueBomber.isFinished()).isFalse();
        }

        // the last update should set 'isFinished' to true.
        blueBomber.lastRefreshTs = 1000L - BlueBomber.REFRESH_TIME;
        blueBomber.updateImage();
        assertThat(blueBomber.isFinished()).isTrue();
    }

    @Test
    public void updateImageWhenInvincibleTimeReachedShouldSetIsInvincibleToFalse() throws Exception {
        BlueBomber blueBomber = new BlueBomber(10, 20);

        // mock CurrentTimeSupplier class to set currentTimeMillis to 1000ms.
        CurrentTimeSupplier currentTimeSupplier = mock(CurrentTimeSupplier.class);
        Mockito.when(currentTimeSupplier.get()).thenReturn(Instant.ofEpochMilli(10000L));
        blueBomber.currentTimeSupplier = currentTimeSupplier;

        // set status.
        blueBomber.setStatus(STATUS_WAIT);
        blueBomber.isInvincible = true; // set to invincible.

        // 1st case: should continue to be invincible.
        blueBomber.lastInvincibilityTs = 10000L - INVINCIBLE_TIME + 1; // limit not reached.
        blueBomber.updateImage();
        assertThat(blueBomber.isInvincible()).isTrue();

        // 2nd case: should stop being invincible.
        blueBomber.lastInvincibilityTs = 10000L - INVINCIBLE_TIME; // limit reached.
        blueBomber.updateImage();
        assertThat(blueBomber.isInvincible()).isFalse();
    }
}