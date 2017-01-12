package sprite.nomad;

import images.ImagesLoader;
import org.assertj.core.api.WithAssertions;
import org.junit.Before;
import org.junit.Test;
import sprite.SpriteType;
import utils.Direction;

import java.io.IOException;

import static utils.Action.ACTION_DYING;
import static utils.Action.ACTION_WALKING;

public class WalkingEnemyTest implements WithAssertions {

    @Before
    public void fillImagesMatrix() throws IOException {
        ImagesLoader.fillImagesMatrix();
    }

    @Test
    public void constructorShouldSetMembersWithTheExpectedValues() throws Exception {
        CloakedSkeleton cloakedSkeleton = new CloakedSkeleton(15, 30);

        // check members value.
        assertThat(cloakedSkeleton.getxMap()).isEqualTo(15);
        assertThat(cloakedSkeleton.getyMap()).isEqualTo(30);
        assertThat(cloakedSkeleton.getSpriteType()).isEqualTo(SpriteType.WALKING_ENEMY);
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
        assertThat(cloakedSkeleton.getActingTime()).isEqualTo(CloakedSkeleton.ACTING_TIME);
        assertThat(cloakedSkeleton.getCurAction()).isEqualTo(ACTION_WALKING);
    }

    @Test
    public void hasActionChangedWithTheSameActionShouldReturnFalse() {
        CloakedSkeleton cloakedSkeleton = new CloakedSkeleton(15, 30);

        // set test.
        cloakedSkeleton.setCurAction(ACTION_DYING);
        cloakedSkeleton.setLastAction(ACTION_DYING);

        // call & check.
        assertThat(cloakedSkeleton.hasActionChanged()).isFalse();
    }

    @Test
    public void hasActionChangedWithTheSameDirectionShouldReturnFalse() {
        CloakedSkeleton cloakedSkeleton = new CloakedSkeleton(15, 30);

        // set test.
        cloakedSkeleton.setCurAction(ACTION_WALKING);
        cloakedSkeleton.setCurDirection(Direction.NORTH);
        cloakedSkeleton.setLastAction(ACTION_WALKING);
        cloakedSkeleton.setLastDirection(Direction.NORTH);

        // call & check.
        assertThat(cloakedSkeleton.hasActionChanged()).isFalse();
    }

    @Test
    public void hasActionChangedWithADifferentActionShouldReturnTrue() throws Exception {
        CloakedSkeleton cloakedSkeleton = new CloakedSkeleton(15, 30);

        // set test.
        cloakedSkeleton.setCurAction(ACTION_WALKING);
        cloakedSkeleton.setLastAction(ACTION_DYING);

        // call & check.
        assertThat(cloakedSkeleton.hasActionChanged()).isTrue();
    }

    @Test
    public void hasActionChangedWithADifferentDirectionShouldReturnTrue() throws Exception {
        CloakedSkeleton cloakedSkeleton = new CloakedSkeleton(15, 30);

        // set test.
        cloakedSkeleton.setCurAction(ACTION_WALKING);
        cloakedSkeleton.setCurDirection(Direction.NORTH);
        cloakedSkeleton.setLastAction(ACTION_WALKING);
        cloakedSkeleton.setLastDirection(Direction.SOUTH);

        // call & check.
        assertThat(cloakedSkeleton.hasActionChanged()).isTrue();
    }

    @Test
    public void updateSpriteShouldSetTheExpectedMember() throws Exception {
        CloakedSkeleton cloakedSkeleton = new CloakedSkeleton(15, 30);

        // dying.
        cloakedSkeleton.setCurAction(ACTION_DYING);
        cloakedSkeleton.updateSprite();
        assertThat(cloakedSkeleton.getImages())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.cloakedSkeletonDeathMatrixRowIdx]);
        assertThat(cloakedSkeleton.getNbImages()).isEqualTo(ImagesLoader.NB_CLOAKED_SKELETON_DEATH_FRAME);

        // walking back.
        cloakedSkeleton.setCurAction(ACTION_WALKING);
        cloakedSkeleton.setCurDirection(Direction.NORTH);
        cloakedSkeleton.updateSprite();
        assertThat(cloakedSkeleton.getImages())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.cloakedSkeletonWalkBackMatrixRowIdx]);
        assertThat(cloakedSkeleton.getNbImages()).isEqualTo(ImagesLoader.NB_CLOAKED_SKELETON_WALK_FRAME);

        // walking front.
        cloakedSkeleton.setCurAction(ACTION_WALKING);
        cloakedSkeleton.setCurDirection(Direction.SOUTH);
        cloakedSkeleton.updateSprite();
        assertThat(cloakedSkeleton.getImages())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.cloakedSkeletonWalkFrontMatrixRowIdx]);
        assertThat(cloakedSkeleton.getNbImages()).isEqualTo(ImagesLoader.NB_CLOAKED_SKELETON_WALK_FRAME);

        // walking left.
        cloakedSkeleton.setCurAction(ACTION_WALKING);
        cloakedSkeleton.setCurDirection(Direction.WEST);
        cloakedSkeleton.updateSprite();
        assertThat(cloakedSkeleton.getImages())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.cloakedSkeletonWalkLeftMatrixRowIdx]);
        assertThat(cloakedSkeleton.getNbImages()).isEqualTo(ImagesLoader.NB_CLOAKED_SKELETON_WALK_FRAME);

        // walking right.
        cloakedSkeleton.setCurAction(ACTION_WALKING);
        cloakedSkeleton.setCurDirection(Direction.EAST);
        cloakedSkeleton.updateSprite();
        assertThat(cloakedSkeleton.getImages())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.cloakedSkeletonWalkRightMatrixRowIdx]);
        assertThat(cloakedSkeleton.getNbImages()).isEqualTo(ImagesLoader.NB_CLOAKED_SKELETON_WALK_FRAME);
    }
}