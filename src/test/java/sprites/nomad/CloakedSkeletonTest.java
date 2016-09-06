package sprites.nomad;

import images.ImagesLoader;
import org.assertj.core.api.WithAssertions;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static sprites.nomad.Enemy.status.STATUS_WALK_FRONT;

public class CloakedSkeletonTest implements WithAssertions {

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
        assertThat(cloakedSkeleton.getStatus()).isEqualTo(STATUS_WALK_FRONT);
        assertThat(cloakedSkeleton.lastStatus).isEqualTo(STATUS_WALK_FRONT);
        assertThat(cloakedSkeleton.walkBackImages).
                isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.cloakedSkeletonWalkBackMatrixRowIdx]);
        assertThat(cloakedSkeleton.walkFrontImages).
                isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.cloakedSkeletonWalkFrontMatrixRowIdx]);
        assertThat(cloakedSkeleton.walkLeftImages).
                isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.cloakedSkeletonWalkLeftMatrixRowIdx]);
        assertThat(cloakedSkeleton.walkRightImages).
                isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.cloakedSkeletonWalkRightMatrixRowIdx]);
        assertThat(cloakedSkeleton.nbWalkFrame).isEqualTo(ImagesLoader.NB_ENEMY_WALK_FRAME);
        assertThat(cloakedSkeleton.refreshTime).isEqualTo(CloakedSkeleton.REFRESH_TIME);
        assertThat(cloakedSkeleton.lastRefreshTs).isEqualTo(0);
        assertThat(cloakedSkeleton.moveTime).isEqualTo(CloakedSkeleton.MOVE_TIME);
        assertThat(cloakedSkeleton.lastMoveTs).isEqualTo(0);
    }
}