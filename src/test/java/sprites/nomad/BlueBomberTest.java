package sprites.nomad;

import images.ImagesLoader;
import org.assertj.core.api.WithAssertions;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static sprites.nomad.Bomber.status.STATUS_WAIT;

public class BlueBomberTest implements WithAssertions {

    @Before
    public void fillImagesMatrix() throws IOException {
        ImagesLoader.fillImagesMatrix();
    }

    @Test
    public void constructorShouldSetMembersWithTheExpectedValues() throws Exception {
        BlueBomber blueBomber = new BlueBomber(10, 20);

        // check members value.
        assertThat(blueBomber.getXMap()).isEqualTo(10);
        assertThat(blueBomber.getYMap()).isEqualTo(20);
        assertThat(blueBomber.initialXMap).isEqualTo(blueBomber.getXMap());
        assertThat(blueBomber.initialYMap).isEqualTo(blueBomber.getYMap());
        assertThat(blueBomber.getStatus()).isEqualTo(STATUS_WAIT);
        assertThat(blueBomber.lastStatus).isEqualTo(STATUS_WAIT);
        assertThat(blueBomber.deathImages).
                isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberDeathMatrixRowIdx]);
        assertThat(blueBomber.nbDeathFrame).isEqualTo(ImagesLoader.NB_BOMBER_DEATH_FRAME);
        assertThat(blueBomber.waitImages).
                isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWaitMatrixRowIdx]);
        assertThat(blueBomber.nbWaitFrame).isEqualTo(ImagesLoader.NB_BOMBER_WAIT_FRAME);
        assertThat(blueBomber.walkBackImages).
                isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWalkBackMatrixRowIdx]);
        assertThat(blueBomber.walkFrontImages).
                isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWalkFrontMatrixRowIdx]);
        assertThat(blueBomber.walkLeftImages).
                isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWalkLeftMatrixRowIdx]);
        assertThat(blueBomber.walkRightImages).
                isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWalkRightMatrixRowIdx]);
        assertThat(blueBomber.nbWalkFrame).isEqualTo(ImagesLoader.NB_BOMBER_WALK_FRAME);
        assertThat(blueBomber.winImages).
                isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWinMatrixRowIdx]);
        assertThat(blueBomber.nbWinFrame).isEqualTo(ImagesLoader.NB_BOMBER_WIN_FRAME);
        assertThat(blueBomber.refreshTime).isEqualTo(BlueBomber.REFRESH_TIME);
        assertThat(blueBomber.lastRefreshTs).isEqualTo(0);
        assertThat(blueBomber.invincibilityTime).isEqualTo(BlueBomber.INVINCIBLE_TIME);
        assertThat(blueBomber.lastInvincibilityTs).isEqualTo(0);
    }
}