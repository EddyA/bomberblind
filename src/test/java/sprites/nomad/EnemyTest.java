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
import static sprites.nomad.Enemy.status.*;

public class EnemyTest implements WithAssertions {

    @Before
    public void fillImagesMatrix() throws IOException {
        ImagesLoader.fillImagesMatrix();
    }

    @Test
    public void accessorsShouldReturnTheExpectedValues() throws Exception {
        CloakedSkeleton cloakedSkeleton = new CloakedSkeleton(15, 30);

        // - status:
        cloakedSkeleton.status = STATUS_WALK_BACK;
        assertThat(cloakedSkeleton.getStatus()).isEqualTo(STATUS_WALK_BACK);
        cloakedSkeleton.status = STATUS_WALK_LEFT;
        assertThat(cloakedSkeleton.getStatus()).isEqualTo(STATUS_WALK_LEFT);

        // - isFinished:
        cloakedSkeleton.isFinished = false;
        assertThat(cloakedSkeleton.isFinished()).isEqualTo(false);
        cloakedSkeleton.isFinished = true;
        assertThat(cloakedSkeleton.isFinished()).isEqualTo(true);

        // - curImage:
        cloakedSkeleton.curImage = null;
        assertThat(cloakedSkeleton.getCurImage()).isEqualTo(cloakedSkeleton.curImage);
        cloakedSkeleton.curImage = ImagesLoader.createImage("/images/icon.gif");
        assertThat(cloakedSkeleton.getCurImage()).isEqualTo(cloakedSkeleton.curImage);
    }

    @Test
    public void mutatorsShouldSetMembersWithTheExpectedValues() throws Exception {
        CloakedSkeleton cloakedSkeleton = new CloakedSkeleton(15, 30);

        // - status.
        cloakedSkeleton.status = STATUS_WALK_FRONT; // initial status.
        cloakedSkeleton.setStatus(STATUS_WALK_LEFT); // update the status.
        assertThat(cloakedSkeleton.status).isEqualTo(STATUS_WALK_LEFT); // check value after the update.
    }

    @Test
    public void shouldMoveShouldReturnFalseWhileTimeIsNotReached() throws Exception {
        CloakedSkeleton cloakedSkeleton = new CloakedSkeleton(15, 30);

        // mock CurrentTimeSupplier class to set currentTimeMillis to 1000ms.
        CurrentTimeSupplier currentTimeSupplier = mock(CurrentTimeSupplier.class);
        Mockito.when(currentTimeSupplier.get()).thenReturn(Instant.ofEpochMilli(100L));
        cloakedSkeleton.currentTimeSupplier = currentTimeSupplier;

        // 1 ms before moving.
        cloakedSkeleton.lastMoveTs = 100 - CloakedSkeleton.MOVE_TIME + 1;
        assertThat(cloakedSkeleton.shouldMove()).isFalse();
    }

    @Test
    public void shouldMoveShouldReturnTrueWhenTimeIsReached() throws Exception {
        CloakedSkeleton cloakedSkeleton = new CloakedSkeleton(15, 30);

        // mock CurrentTimeSupplier class to set currentTimeMillis to 1000ms.
        CurrentTimeSupplier currentTimeSupplier = mock(CurrentTimeSupplier.class);
        Mockito.when(currentTimeSupplier.get()).thenReturn(Instant.ofEpochMilli(100L));
        cloakedSkeleton.currentTimeSupplier = currentTimeSupplier;

        // time has been reached.
        cloakedSkeleton.lastMoveTs = 100 - CloakedSkeleton.MOVE_TIME;
        assertThat(cloakedSkeleton.shouldMove()).isTrue();
    }

    @Test
    public void updateImageWithANewWalkStatusShouldSetCurImageWithThe1stImageOfTheRelativeArray() throws Exception {
        CloakedSkeleton cloakedSkeleton = new CloakedSkeleton(15, 30);

        // - walk back:
        cloakedSkeleton.setStatus(STATUS_WALK_BACK);
        cloakedSkeleton.updateImage();
        assertThat(cloakedSkeleton.getCurImage())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.cloakedSkeletonWalkBackMatrixRowIdx][0]);
        // - walk front:
        cloakedSkeleton.setStatus(STATUS_WALK_FRONT);
        cloakedSkeleton.updateImage();
        assertThat(cloakedSkeleton.getCurImage())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.cloakedSkeletonWalkFrontMatrixRowIdx][0]);
        // - walk left:
        cloakedSkeleton.setStatus(STATUS_WALK_LEFT);
        cloakedSkeleton.updateImage();
        assertThat(cloakedSkeleton.getCurImage())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.cloakedSkeletonWalkLeftMatrixRowIdx][0]);
        // - walk right:
        cloakedSkeleton.setStatus(STATUS_WALK_RIGHT);
        cloakedSkeleton.updateImage();
        assertThat(cloakedSkeleton.getCurImage())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.cloakedSkeletonWalkRightMatrixRowIdx][0]);
    }

    @Test
    public void updateImageWithAFixedStatusShouldCorrectlyUpdateCurImageTimeAfterTime() throws Exception {
        CloakedSkeleton cloakedSkeleton = new CloakedSkeleton(15, 30);

        // mock CurrentTimeSupplier class to set currentTimeMillis to 1000ms.
        CurrentTimeSupplier currentTimeSupplier = mock(CurrentTimeSupplier.class);
        Mockito.when(currentTimeSupplier.get()).thenReturn(Instant.ofEpochMilli(1000L));
        cloakedSkeleton.currentTimeSupplier = currentTimeSupplier;

        // set status.
        cloakedSkeleton.setStatus(STATUS_WALK_FRONT);

        // check value according to "lastRefreshTs":
        // - 1st update -> 1st image.
        cloakedSkeleton.updateImage();
        assertThat(cloakedSkeleton.getCurImage())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.cloakedSkeletonWalkFrontMatrixRowIdx][0]);

        // 2nd update -> // current time - last refresh time < REFRESH_TIME -> image should not change.
        cloakedSkeleton.lastRefreshTs = 1000L;
        cloakedSkeleton.updateImage();
        assertThat(cloakedSkeleton.getCurImage())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.cloakedSkeletonWalkFrontMatrixRowIdx][0]);

        // 3rd, 4th and 5th update -> // current time - last refresh time >= REFRESH_TIME -> image should change.
        cloakedSkeleton.lastRefreshTs = 1000L - CloakedSkeleton.REFRESH_TIME;
        cloakedSkeleton.updateImage();
        assertThat(cloakedSkeleton.getCurImage())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.cloakedSkeletonWalkFrontMatrixRowIdx][1]);
        cloakedSkeleton.lastRefreshTs = 1000L - CloakedSkeleton.REFRESH_TIME;
        cloakedSkeleton.updateImage();
        assertThat(cloakedSkeleton.getCurImage())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.cloakedSkeletonWalkFrontMatrixRowIdx][2]);
        cloakedSkeleton.lastRefreshTs = 1000L - CloakedSkeleton.REFRESH_TIME;
        cloakedSkeleton.updateImage();
        assertThat(cloakedSkeleton.getCurImage())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.cloakedSkeletonWalkFrontMatrixRowIdx][3]);

        // 6th update -> // current time - last refresh time > REFRESH_TIME -> image should change, go back to the 1st one.
        cloakedSkeleton.lastRefreshTs = 1000L - CloakedSkeleton.REFRESH_TIME;
        cloakedSkeleton.updateImage();
        assertThat(cloakedSkeleton.getCurImage())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.cloakedSkeletonWalkFrontMatrixRowIdx][0]);
    }

    @Test
    public void updateImageWithDeadStatusShouldSetIsFinishedToTrue() throws Exception {
        CloakedSkeleton cloakedSkeleton = new CloakedSkeleton(15, 30);

        // update image with a status != dead.
        cloakedSkeleton.setStatus(STATUS_WALK_FRONT);
        cloakedSkeleton.updateImage();
        assertThat(cloakedSkeleton.isFinished()).isFalse();

        // update image with a status == dead.
        cloakedSkeleton.setStatus(STATUS_DEAD);
        cloakedSkeleton.updateImage();
        assertThat(cloakedSkeleton.isFinished()).isTrue();
    }
}