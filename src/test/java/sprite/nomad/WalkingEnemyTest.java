package sprite.nomad;

import images.ImagesLoader;
import org.assertj.core.api.WithAssertions;
import org.junit.Before;
import org.junit.Test;
import sprite.SpriteType;
import utils.Direction;

import java.io.IOException;

import static sprite.SpriteAction.ACTION_BREAKING;
import static sprite.SpriteAction.ACTION_DYING;
import static sprite.SpriteAction.ACTION_FLYING;
import static sprite.SpriteAction.ACTION_WAITING;
import static sprite.SpriteAction.ACTION_WALKING;
import static sprite.SpriteAction.ACTION_WINING;

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
        assertThat(cloakedSkeleton.getSpriteType()).isEqualTo(SpriteType.TYPE_WALKING_ENEMY);
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
        assertThat(cloakedSkeleton.getCurSpriteAction()).isEqualTo(ACTION_WALKING);
    }

    @Test
    public void isActionAllowedShouldReturnTrue() throws Exception {
        CloakedSkeleton cloakedSkeleton = new CloakedSkeleton(15, 30);
        assertThat(cloakedSkeleton.isActionAllowed(ACTION_DYING)).isTrue();
        assertThat(cloakedSkeleton.isActionAllowed(ACTION_WALKING)).isTrue();
    }

    @Test
    public void isActionAllowedShouldReturnFalse() throws Exception {
        CloakedSkeleton cloakedSkeleton = new CloakedSkeleton(15, 30);
        assertThat(cloakedSkeleton.isActionAllowed(ACTION_BREAKING)).isFalse();
        assertThat(cloakedSkeleton.isActionAllowed(ACTION_FLYING)).isFalse();
        assertThat(cloakedSkeleton.isActionAllowed(ACTION_WAITING)).isFalse();
        assertThat(cloakedSkeleton.isActionAllowed(ACTION_WINING)).isFalse();
    }

    @Test
    public void hasActionChangedWithTheSameActionShouldReturnFalse() {
        CloakedSkeleton cloakedSkeleton = new CloakedSkeleton(15, 30);

        // set test.
        cloakedSkeleton.setCurSpriteAction(ACTION_DYING);
        cloakedSkeleton.setLastSpriteAction(ACTION_DYING);

        // call & check.
        assertThat(cloakedSkeleton.hasActionChanged()).isFalse();
    }

    @Test
    public void hasActionChangedAndWalkingToTheSameDirectionShouldReturnFalse() {
        CloakedSkeleton cloakedSkeleton = new CloakedSkeleton(15, 30);

        // set test.
        cloakedSkeleton.setCurSpriteAction(ACTION_WALKING);
        cloakedSkeleton.setCurDirection(Direction.NORTH);
        cloakedSkeleton.setLastSpriteAction(ACTION_WALKING);
        cloakedSkeleton.setLastDirection(Direction.NORTH);

        // call & check.
        assertThat(cloakedSkeleton.hasActionChanged()).isFalse();
    }

    @Test
    public void hasActionChangedWithADifferentActionShouldReturnTrue() throws Exception {
        CloakedSkeleton cloakedSkeleton = new CloakedSkeleton(15, 30);

        // set test.
        cloakedSkeleton.setCurSpriteAction(ACTION_WALKING);
        cloakedSkeleton.setLastSpriteAction(ACTION_DYING);

        // call & check.
        assertThat(cloakedSkeleton.hasActionChanged()).isTrue();
    }

    @Test
    public void hasActionChangedWalkingToADifferentDirectionShouldReturnTrue() throws Exception {
        CloakedSkeleton cloakedSkeleton = new CloakedSkeleton(15, 30);

        // set test.
        cloakedSkeleton.setCurSpriteAction(ACTION_WALKING);
        cloakedSkeleton.setCurDirection(Direction.NORTH);
        cloakedSkeleton.setLastSpriteAction(ACTION_WALKING);
        cloakedSkeleton.setLastDirection(Direction.SOUTH);

        // call & check.
        assertThat(cloakedSkeleton.hasActionChanged()).isTrue();
    }

    @Test
    public void updateSpriteShouldSetTheExpectedMember() throws Exception {
        CloakedSkeleton cloakedSkeleton = new CloakedSkeleton(15, 30);

        // dying.
        cloakedSkeleton.setCurSpriteAction(ACTION_DYING);
        cloakedSkeleton.updateSprite();
        assertThat(cloakedSkeleton.getImages()).isEqualTo(cloakedSkeleton.getDeathImages());
        assertThat(cloakedSkeleton.getNbImages()).isEqualTo(cloakedSkeleton.getNbDeathFrame());

        // walking back.
        cloakedSkeleton.setCurSpriteAction(ACTION_WALKING);
        cloakedSkeleton.setCurDirection(Direction.NORTH);
        cloakedSkeleton.updateSprite();
        assertThat(cloakedSkeleton.getImages()).isEqualTo(cloakedSkeleton.getWalkBackImages());
        assertThat(cloakedSkeleton.getNbImages()).isEqualTo(cloakedSkeleton.getNbWalkFrame());

        // walking front.
        cloakedSkeleton.setCurSpriteAction(ACTION_WALKING);
        cloakedSkeleton.setCurDirection(Direction.SOUTH);
        cloakedSkeleton.updateSprite();
        assertThat(cloakedSkeleton.getImages()).isEqualTo(cloakedSkeleton.getWalkFrontImages());
        assertThat(cloakedSkeleton.getNbImages()).isEqualTo(cloakedSkeleton.getNbWalkFrame());

        // walking left.
        cloakedSkeleton.setCurSpriteAction(ACTION_WALKING);
        cloakedSkeleton.setCurDirection(Direction.WEST);
        cloakedSkeleton.updateSprite();
        assertThat(cloakedSkeleton.getImages()).isEqualTo(cloakedSkeleton.getWalkLeftImages());
        assertThat(cloakedSkeleton.getNbImages()).isEqualTo(cloakedSkeleton.getNbWalkFrame());

        // walking right.
        cloakedSkeleton.setCurSpriteAction(ACTION_WALKING);
        cloakedSkeleton.setCurDirection(Direction.EAST);
        cloakedSkeleton.updateSprite();
        assertThat(cloakedSkeleton.getImages()).isEqualTo(cloakedSkeleton.getWalkRightImages());
        assertThat(cloakedSkeleton.getNbImages()).isEqualTo(cloakedSkeleton.getNbWalkFrame());
    }
}