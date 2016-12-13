package sprite.nomad;

import static sprite.nomad.Enemy.Action.ACTION_DYING;
import static sprite.nomad.Enemy.Action.ACTION_WALKING;

import java.io.IOException;

import org.assertj.core.api.WithAssertions;
import org.junit.Before;
import org.junit.Test;

import images.ImagesLoader;
import sprite.nomad.CloakedSkeleton;
import sprite.nomad.Enemy;
import utils.Direction;

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
        assertThat(cloakedSkeleton.getCurAction()).isEqualTo(ACTION_WALKING);
        assertThat(cloakedSkeleton.getLastAction()).isEqualTo(ACTION_WALKING);
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
    public void statusHasChangedShouldReturnFalse() {
        CloakedSkeleton cloakedSkeleton = new CloakedSkeleton(15, 30);

        // set test.
        cloakedSkeleton.setCurAction(ACTION_WALKING);
        cloakedSkeleton.setCurDirection(Direction.NORTH);
        cloakedSkeleton.setLastAction(ACTION_WALKING);
        cloakedSkeleton.setLastDirection(Direction.NORTH);

        // call & check.
        assertThat(cloakedSkeleton.statusHasChanged()).isFalse();
    }

    @Test
    public void statusHasChangedShouldReturnTrue() {
        CloakedSkeleton cloakedSkeleton = new CloakedSkeleton(15, 30);

        // set test.
        cloakedSkeleton.setCurAction(ACTION_WALKING);
        cloakedSkeleton.setCurDirection(Direction.NORTH);
        cloakedSkeleton.setLastAction(ACTION_WALKING);
        cloakedSkeleton.setLastDirection(Direction.SOUTH);

        // call & check.
        assertThat(cloakedSkeleton.statusHasChanged()).isTrue();
    }

    @Test
    public void updateStatusShouldReturnFalse() throws Exception {
        CloakedSkeleton cloakedSkeleton = new CloakedSkeleton(15, 30);

        // set test.
        cloakedSkeleton.setCurAction(ACTION_WALKING);
        cloakedSkeleton.setCurDirection(Direction.NORTH);
        cloakedSkeleton.setLastAction(ACTION_WALKING);
        cloakedSkeleton.setLastDirection(Direction.NORTH);
        cloakedSkeleton.setLastRefreshTs(1); // NOT the first call.

        // call & check.
        assertThat(cloakedSkeleton.updateStatus()).isFalse();
    }

    @Test
    public void updateStatusTheFirstTimeShouldReturnTrue() throws Exception {
        CloakedSkeleton cloakedSkeleton = new CloakedSkeleton(15, 30);

        // set test.
        cloakedSkeleton.setLastRefreshTs(0); // first call.

        // call & check.
        assertThat(cloakedSkeleton.updateStatus()).isTrue();
    }

    @Test
    public void updateStatusWithADifferentStatusShouldReturnTrue() throws Exception {
        CloakedSkeleton cloakedSkeleton = new CloakedSkeleton(15, 30);

        // set test.
        cloakedSkeleton.setCurAction(ACTION_WALKING);
        cloakedSkeleton.setLastAction(ACTION_DYING);
        cloakedSkeleton.setLastRefreshTs(1); // NOT the first call.

        // call & check.
        assertThat(cloakedSkeleton.updateStatus()).isTrue();
    }

    @Test
    public void updateStatusWithADifferentDirectionShouldReturnTrue() throws Exception {
        CloakedSkeleton cloakedSkeleton = new CloakedSkeleton(15, 30);

        // set test.
        cloakedSkeleton.setCurAction(ACTION_WALKING);
        cloakedSkeleton.setCurDirection(Direction.NORTH);
        cloakedSkeleton.setLastAction(ACTION_WALKING);
        cloakedSkeleton.setLastDirection(Direction.SOUTH);
        cloakedSkeleton.setLastRefreshTs(1); // NOT the first call.

        // call & check.
        assertThat(cloakedSkeleton.updateStatus()).isTrue();
    }

    @Test
    public void updateSpriteShouldSetTheExpectedMember() throws Exception {
        CloakedSkeleton cloakedSkeleton = new CloakedSkeleton(15, 30);

        // dying.
        cloakedSkeleton.setCurAction(Enemy.Action.ACTION_DYING);
        cloakedSkeleton.updateSprite();
        assertThat(cloakedSkeleton.getImages())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.cloakedSkeletonDeathMatrixRowIdx]);
        assertThat(cloakedSkeleton.getNbImages()).isEqualTo(ImagesLoader.NB_CLOAKED_SKELETON_DEATH_FRAME);

        // walking back.
        cloakedSkeleton.setCurAction(Enemy.Action.ACTION_WALKING);
        cloakedSkeleton.setCurDirection(Direction.NORTH);
        cloakedSkeleton.updateSprite();
        assertThat(cloakedSkeleton.getImages())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.cloakedSkeletonWalkBackMatrixRowIdx]);
        assertThat(cloakedSkeleton.getNbImages()).isEqualTo(ImagesLoader.NB_CLOAKED_SKELETON_WALK_FRAME);

        // walking front.
        cloakedSkeleton.setCurAction(Enemy.Action.ACTION_WALKING);
        cloakedSkeleton.setCurDirection(Direction.SOUTH);
        cloakedSkeleton.updateSprite();
        assertThat(cloakedSkeleton.getImages())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.cloakedSkeletonWalkFrontMatrixRowIdx]);
        assertThat(cloakedSkeleton.getNbImages()).isEqualTo(ImagesLoader.NB_CLOAKED_SKELETON_WALK_FRAME);

        // walking left.
        cloakedSkeleton.setCurAction(Enemy.Action.ACTION_WALKING);
        cloakedSkeleton.setCurDirection(Direction.WEST);
        cloakedSkeleton.updateSprite();
        assertThat(cloakedSkeleton.getImages())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.cloakedSkeletonWalkLeftMatrixRowIdx]);
        assertThat(cloakedSkeleton.getNbImages()).isEqualTo(ImagesLoader.NB_CLOAKED_SKELETON_WALK_FRAME);

        // walking right.
        cloakedSkeleton.setCurAction(Enemy.Action.ACTION_WALKING);
        cloakedSkeleton.setCurDirection(Direction.EAST);
        cloakedSkeleton.updateSprite();
        assertThat(cloakedSkeleton.getImages())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.cloakedSkeletonWalkRightMatrixRowIdx]);
        assertThat(cloakedSkeleton.getNbImages()).isEqualTo(ImagesLoader.NB_CLOAKED_SKELETON_WALK_FRAME);
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
        cloakedSkeleton.setCurAction(ACTION_DYING);
        cloakedSkeleton.updateSprite();
        cloakedSkeleton.setCurImageIdx(ImagesLoader.NB_CLOAKED_SKELETON_DEATH_FRAME - 1);

        // call & check.
        assertThat(cloakedSkeleton.isFinished()).isTrue();
    }

    @Test
    public void isFinishedWithAStatusDifferentOfDyingShouldReturnFalse() throws Exception {
        CloakedSkeleton cloakedSkeleton = new CloakedSkeleton(15, 30);

        // set test.
        cloakedSkeleton.setCurAction(ACTION_WALKING);
        cloakedSkeleton.setCurDirection(Direction.NORTH);
        cloakedSkeleton.updateSprite();
        cloakedSkeleton.setCurImageIdx(ImagesLoader.NB_CLOAKED_SKELETON_DEATH_FRAME - 1);

        // call & check.
        assertThat(cloakedSkeleton.isFinished()).isFalse();
    }

    @Test
    public void isFinishedWithACurImageIdxDifferentOfNbImagesMinus1ShouldReturnFalse() throws Exception {
        CloakedSkeleton cloakedSkeleton = new CloakedSkeleton(15, 30);

        // set test.
        cloakedSkeleton.setCurAction(ACTION_DYING);
        cloakedSkeleton.updateSprite();
        cloakedSkeleton.setCurImageIdx(0);

        // call & check.
        assertThat(cloakedSkeleton.isFinished()).isFalse();
    }
}