package sprite.nomad.abstracts;

import static sprite.nomad.abstracts.Enemy.status.STATUS_DYING;
import static sprite.nomad.abstracts.Enemy.status.STATUS_WALKING_FRONT;
import static sprite.nomad.abstracts.Enemy.status.STATUS_WALKING_LEFT;
import static sprite.nomad.abstracts.Enemy.status.STATUS_WALKING_RIGHT;

import java.io.IOException;

import org.assertj.core.api.WithAssertions;
import org.junit.Before;
import org.junit.Test;

import images.ImagesLoader;
import sprite.nomad.CloakedSkeleton;

public class EnemyTest implements WithAssertions {

    @Before
    public void fillImagesMatrix() throws IOException {
        ImagesLoader.fillImagesMatrix();
    }

    @Test
    public void constructorShouldSetMembersWithTheExpectedValues() throws Exception {
        CloakedSkeleton cloakedSkeleton = new CloakedSkeleton(15, 30);

        // check members value.
        assertThat(cloakedSkeleton.getXMap()).isEqualTo(15);
        assertThat(cloakedSkeleton.getYMap()).isEqualTo(30);
        assertThat(cloakedSkeleton.getCurStatus()).isEqualTo(STATUS_WALKING_FRONT);
        assertThat(cloakedSkeleton.getLastStatus()).isEqualTo(STATUS_WALKING_FRONT);
        assertThat(cloakedSkeleton.getDeathImages())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.cloakedSkeletonDeathMatrixRowIdx]);
        assertThat(cloakedSkeleton.getNbDeathFrame()).isEqualTo(ImagesLoader.NB_CLOAKED_SKELETON_DEATH_FRAME);
        assertThat(cloakedSkeleton.getWalkBackImages())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.cloakedSkeletonWalkBackMatrixRowIdx]);
        assertThat(cloakedSkeleton.getWalkFrontImages())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.cloakedSkeletonWalkFrontMatrixRowIdx]);
        assertThat(cloakedSkeleton.getWalkLeftImages())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.cloakedSkeletonWalkLeftMatrixRowIdx]);
        assertThat(cloakedSkeleton.getWalkRightImages())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.cloakedSkeletonWalkRightMatrixRowIdx]);
        assertThat(cloakedSkeleton.getNbWalkFrame()).isEqualTo(ImagesLoader.NB_CLOAKED_SKELETON_WALK_FRAME);
        assertThat(cloakedSkeleton.getRefreshTime()).isEqualTo(CloakedSkeleton.REFRESH_TIME);
        assertThat(cloakedSkeleton.getMoveTime()).isEqualTo(CloakedSkeleton.MOVING_TIME);
    }

    @Test
    public void updateStatusShouldReturnFalse() throws Exception {
        CloakedSkeleton cloakedSkeleton = new CloakedSkeleton(15, 30);

        // set test.
        cloakedSkeleton.setCurStatus(STATUS_WALKING_LEFT);
        cloakedSkeleton.setLastStatus(STATUS_WALKING_LEFT); // last status == current status.
        cloakedSkeleton.setLastRefreshTs(1); // NOT the first call.

        // call & check.
        assertThat(cloakedSkeleton.updateStatus()).isFalse();
    }

    @Test
    public void updateStatusTheFirstTimeShouldReturnTrue() throws Exception {
        CloakedSkeleton cloakedSkeleton = new CloakedSkeleton(15, 30);

        // set test.
        cloakedSkeleton.setCurStatus(STATUS_WALKING_LEFT);
        cloakedSkeleton.setLastStatus(STATUS_WALKING_LEFT); // last status == current status.
        cloakedSkeleton.setLastRefreshTs(0); // first call.

        // call & check.
        assertThat(cloakedSkeleton.updateStatus()).isTrue();
    }

    @Test
    public void updateStatusWithADifferentStatusShouldReturnTrue() throws Exception {
        CloakedSkeleton cloakedSkeleton = new CloakedSkeleton(15, 30);

        // set test.
        cloakedSkeleton.setCurStatus(STATUS_WALKING_LEFT);
        cloakedSkeleton.setLastStatus(STATUS_WALKING_RIGHT); // last status != current status.
        cloakedSkeleton.setLastRefreshTs(1); // NOT the first call.

        // call & check.
        assertThat(cloakedSkeleton.updateStatus()).isTrue();
    }

    @Test
    public void updateSpriteShouldSetTheExpectedMember() throws Exception {
        CloakedSkeleton cloakedSkeleton = new CloakedSkeleton(15, 30);

        // dying.
        cloakedSkeleton.setCurStatus(Enemy.status.STATUS_DYING);
        cloakedSkeleton.updateSprite();
        assertThat(cloakedSkeleton.images)
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.cloakedSkeletonDeathMatrixRowIdx]);
        assertThat(cloakedSkeleton.nbImages).isEqualTo(ImagesLoader.NB_CLOAKED_SKELETON_DEATH_FRAME);

        // walking back.
        cloakedSkeleton.setCurStatus(Enemy.status.STATUS_WALKING_BACK);
        cloakedSkeleton.updateSprite();
        assertThat(cloakedSkeleton.images)
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.cloakedSkeletonWalkBackMatrixRowIdx]);
        assertThat(cloakedSkeleton.nbImages).isEqualTo(ImagesLoader.NB_CLOAKED_SKELETON_WALK_FRAME);

        // walking front.
        cloakedSkeleton.setCurStatus(Enemy.status.STATUS_WALKING_FRONT);
        cloakedSkeleton.updateSprite();
        assertThat(cloakedSkeleton.images)
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.cloakedSkeletonWalkFrontMatrixRowIdx]);
        assertThat(cloakedSkeleton.nbImages).isEqualTo(ImagesLoader.NB_CLOAKED_SKELETON_WALK_FRAME);

        // walking left.
        cloakedSkeleton.setCurStatus(Enemy.status.STATUS_WALKING_LEFT);
        cloakedSkeleton.updateSprite();
        assertThat(cloakedSkeleton.images)
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.cloakedSkeletonWalkLeftMatrixRowIdx]);
        assertThat(cloakedSkeleton.nbImages).isEqualTo(ImagesLoader.NB_CLOAKED_SKELETON_WALK_FRAME);

        // walking right.
        cloakedSkeleton.setCurStatus(Enemy.status.STATUS_WALKING_RIGHT);
        cloakedSkeleton.updateSprite();
        assertThat(cloakedSkeleton.images)
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.cloakedSkeletonWalkRightMatrixRowIdx]);
        assertThat(cloakedSkeleton.nbImages).isEqualTo(ImagesLoader.NB_CLOAKED_SKELETON_WALK_FRAME);
    }

    @Test
    public void isInvincibleShouldReturnFalse() throws Exception {
        CloakedSkeleton cloakedSkeleton = new CloakedSkeleton(15, 30);

        // call & check.
        assertThat(cloakedSkeleton.isInvincible()).isFalse();
    }

    @Test
    public void isFinishedShouldReturnTrue() throws Exception {
        CloakedSkeleton cloakedSkeleton = new CloakedSkeleton(15, 30);

        // set test.
        cloakedSkeleton.setCurStatus(STATUS_DYING);
        cloakedSkeleton.updateSprite();
        cloakedSkeleton.curImageIdx = ImagesLoader.NB_CLOAKED_SKELETON_DEATH_FRAME - 1;

        // call & check.
        assertThat(cloakedSkeleton.isFinished()).isTrue();
    }

    @Test
    public void isFinishedWithAStatusDifferentOfDyingShouldReturnFalse() throws Exception {
        CloakedSkeleton cloakedSkeleton = new CloakedSkeleton(15, 30);

        // set test.
        cloakedSkeleton.setCurStatus(STATUS_WALKING_RIGHT);
        cloakedSkeleton.updateSprite();
        cloakedSkeleton.curImageIdx = ImagesLoader.NB_CLOAKED_SKELETON_DEATH_FRAME - 1;

        // call & check.
        assertThat(cloakedSkeleton.isFinished()).isFalse();
    }

    @Test
    public void isFinishedWithACurImageIdxDifferentOfNbImagesMinus1ShouldReturnFalse() throws Exception {
        CloakedSkeleton cloakedSkeleton = new CloakedSkeleton(15, 30);

        // set test.
        cloakedSkeleton.setCurStatus(STATUS_DYING);
        cloakedSkeleton.updateSprite();
        cloakedSkeleton.curImageIdx = 0;

        // call & check.
        assertThat(cloakedSkeleton.isFinished()).isFalse();
    }
}