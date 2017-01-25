package sprite.nomad;

import static sprite.SpriteAction.ACTION_WALKING;

import java.io.IOException;

import org.assertj.core.api.WithAssertions;
import org.junit.Before;
import org.junit.Test;

import images.ImagesLoader;
import sprite.SpriteType;

public class CloakedSkeletonTest implements WithAssertions {

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
}