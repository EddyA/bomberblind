package sprites;

import images.ImagesLoader;
import org.assertj.core.api.WithAssertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import sprites.nomad.CloakedSkeleton;
import utils.CurrentTimeSupplier;

import java.io.IOException;
import java.time.Instant;

import static org.mockito.Mockito.mock;
import static sprites.nomad.abstracts.Enemy.status.*;

public class EnemyTest implements WithAssertions {

    @Before
    public void fillImagesMatrix() throws IOException {
        ImagesLoader.fillImagesMatrix();
    }

    @Test
    public void shouldMoveShouldReturnFalseWhileTimeIsNotReached() throws Exception {
        CloakedSkeleton cloakedSkeleton = new CloakedSkeleton(15, 30);

        // mock CurrentTimeSupplier class to set currentTimeMillis to 1000ms.
        CurrentTimeSupplier currentTimeSupplier = mock(CurrentTimeSupplier.class);
        Mockito.when(currentTimeSupplier.get()).thenReturn(Instant.ofEpochMilli(100L));
        cloakedSkeleton.currentTimeSupplier = currentTimeSupplier;

        // 1 ms before moving.
        cloakedSkeleton.setLastMoveTs(100 - CloakedSkeleton.MOVE_TIME + 1);
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
        cloakedSkeleton.setLastMoveTs(100 - CloakedSkeleton.MOVE_TIME);
        assertThat(cloakedSkeleton.shouldMove()).isTrue();
    }

    @Test
    public void updateImageWithANewWalkStatusShouldSetCurImageWithThe1stImageOfTheRelativeArray() throws Exception {
        CloakedSkeleton cloakedSkeleton = new CloakedSkeleton(15, 30);

        // - walk back:
        cloakedSkeleton.setCurStatus(STATUS_WALKING_BACK);
        cloakedSkeleton.updateImage();
        assertThat(cloakedSkeleton.getCurImage())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.cloakedSkeletonWalkBackMatrixRowIdx][0]);
        // - walk front:
        cloakedSkeleton.setCurStatus(STATUS_WALKING_FRONT);
        cloakedSkeleton.updateImage();
        assertThat(cloakedSkeleton.getCurImage())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.cloakedSkeletonWalkFrontMatrixRowIdx][0]);
        // - walk left:
        cloakedSkeleton.setCurStatus(STATUS_WALKING_LEFT);
        cloakedSkeleton.updateImage();
        assertThat(cloakedSkeleton.getCurImage())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.cloakedSkeletonWalkLeftMatrixRowIdx][0]);
        // - walk right:
        cloakedSkeleton.setCurStatus(STATUS_WALKING_RIGHT);
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

        // set curStatus.
        cloakedSkeleton.setCurStatus(STATUS_WALKING_FRONT);

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

        // update image with a curStatus != dead.
        cloakedSkeleton.setCurStatus(STATUS_WALKING_FRONT);
        cloakedSkeleton.updateImage();
        assertThat(cloakedSkeleton.isFinished()).isFalse();

        // update image with a curStatus == dead.
        cloakedSkeleton.setCurStatus(STATUS_DEAD);
        assertThat(cloakedSkeleton.isFinished()).isTrue();
    }
}