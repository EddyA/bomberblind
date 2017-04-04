package sprite.nomad;

import images.ImagesLoader;
import org.assertj.core.api.WithAssertions;
import org.junit.Before;
import org.junit.Test;
import sprite.SpriteType;

import java.io.IOException;

import static sprite.SpriteAction.ACTION_WAITING;

public class BlueBomberTest implements WithAssertions {

    @Before
    public void fillImagesMatrix() throws IOException {
        ImagesLoader.fillImagesMatrix();
    }

    @Test
    public void constructorShouldSetMembersWithTheExpectedValues() throws Exception {
        BlueBomber blueBomber = new BlueBomber(15, 30);

        // check members value.
        assertThat(blueBomber.getxMap()).isEqualTo(15);
        assertThat(blueBomber.getyMap()).isEqualTo(30);
        assertThat(blueBomber.getSpriteType()).isEqualTo(SpriteType.TYPE_SPRITE_BOMBER);
        assertThat(blueBomber.getDeathImages()).isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberDeathMatrixRowIdx]);
        assertThat(blueBomber.getNbDeathFrame()).isEqualTo(ImagesLoader.NB_BOMBER_DEATH_FRAME);
        assertThat(blueBomber.getWaitImages()).isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWaitMatrixRowIdx]);
        assertThat(blueBomber.getNbWaitFrame()).isEqualTo(ImagesLoader.NB_BOMBER_WAIT_FRAME);
        assertThat(blueBomber.getWalkBackImages()).isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWalkBackMatrixRowIdx]);
        assertThat(blueBomber.getWalkFrontImages()).isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWalkFrontMatrixRowIdx]);
        assertThat(blueBomber.getWalkLeftImages()).isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWalkLeftMatrixRowIdx]);
        assertThat(blueBomber.getWalkRightImages()).isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWalkRightMatrixRowIdx]);
        assertThat(blueBomber.getNbWalkFrame()).isEqualTo(ImagesLoader.NB_BOMBER_WALK_FRAME);
        assertThat(blueBomber.getWinImages()).isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWinMatrixRowIdx]);
        assertThat(blueBomber.getNbWinFrame()).isEqualTo(ImagesLoader.NB_BOMBER_WIN_FRAME);
        assertThat(blueBomber.getRefreshTime()).isEqualTo(BlueBomber.REFRESH_TIME);
        assertThat(blueBomber.getActingTime()).isEqualTo(BlueBomber.DEFAULT_ACTING_TIME);
        assertThat(blueBomber.getInvincibilityTime()).isEqualTo(BlueBomber.INVINCIBILITY_TIME);
        assertThat(blueBomber.getInitialXMap()).isEqualTo(blueBomber.getxMap());
        assertThat(blueBomber.getInitialYMap()).isEqualTo(blueBomber.getyMap());
        assertThat(blueBomber.getCurSpriteAction()).isEqualTo(ACTION_WAITING);
        assertThat(blueBomber.isInvincible()).isTrue();
    }
}