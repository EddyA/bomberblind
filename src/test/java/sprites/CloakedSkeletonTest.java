package sprites;

import images.ImagesLoader;
import org.assertj.core.api.WithAssertions;
import org.junit.Before;
import org.junit.Test;
import sprites.nomad.CloakedSkeleton;

import java.io.IOException;

import static sprites.nomad.abstracts.Enemy.status.STATUS_WALKING_FRONT;

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
        assertThat(cloakedSkeleton.getCurStatus()).isEqualTo(STATUS_WALKING_FRONT);
        assertThat(cloakedSkeleton.getLastStatus()).isEqualTo(STATUS_WALKING_FRONT);
        assertThat(cloakedSkeleton.getWalkBackImages()).
                isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.cloakedSkeletonWalkBackMatrixRowIdx]);
        assertThat(cloakedSkeleton.getWalkFrontImages()).
                isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.cloakedSkeletonWalkFrontMatrixRowIdx]);
        assertThat(cloakedSkeleton.getWalkLeftImages()).
                isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.cloakedSkeletonWalkLeftMatrixRowIdx]);
        assertThat(cloakedSkeleton.getWalkRightImages()).
                isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.cloakedSkeletonWalkRightMatrixRowIdx]);
        assertThat(cloakedSkeleton.getNbWalkFrame()).isEqualTo(ImagesLoader.NB_ENEMY_WALK_FRAME);
        assertThat(cloakedSkeleton.refreshTime).isEqualTo(CloakedSkeleton.REFRESH_TIME);
        assertThat(cloakedSkeleton.lastRefreshTs).isEqualTo(0);
        assertThat(cloakedSkeleton.getMoveTime()).isEqualTo(CloakedSkeleton.MOVE_TIME);
        assertThat(cloakedSkeleton.getLastMoveTs()).isEqualTo(0);
    }
}