package sprite.nomad;

import static sprite.SpriteAction.ACTION_FLYING;

import java.io.IOException;

import org.assertj.core.api.WithAssertions;
import org.junit.Before;
import org.junit.Test;

import images.ImagesLoader;
import sprite.SpriteType;
import utils.Direction;

public class BirdTest implements WithAssertions {

    @Before
    public void fillImagesMatrix() throws IOException {
        ImagesLoader.fillImagesMatrix();
    }

    @Test
    public void constructorShouldSetMembersWithTheExpectedValues() throws Exception {
        Bird bird = new Bird(15, 30, Direction.DIRECTION_EAST, 5);

        // check members value.
        assertThat(bird.getxMap()).isEqualTo(15);
        assertThat(bird.getyMap()).isEqualTo(30);
        assertThat(bird.getSpriteType()).isEqualTo(SpriteType.TYPE_FLYING_NOMAD);
        assertThat(bird.getFlyFrontImages()).isNull();
        assertThat(bird.getFlyBackImages()).isNull();
        assertThat(bird.getFlyLeftImages())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.birdFlyLeftMatrixRowIdx]);
        assertThat(bird.getFlyRightImages())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.birdFlyRightMatrixRowIdx]);
        assertThat(bird.getNbFlyFrame()).isEqualTo(ImagesLoader.NB_BIRD_FLY_FRAME);
        assertThat(bird.getRefreshTime()).isEqualTo(Bird.REFRESH_TIME);
        assertThat(bird.getActingTime()).isEqualTo(Bird.ACTING_TIME);
        assertThat(bird.getCurSpriteAction()).isEqualTo(ACTION_FLYING);
        assertThat(bird.getLastSpriteAction()).isEqualTo(ACTION_FLYING);
        assertThat(bird.getCurDirection()).isEqualTo(Direction.DIRECTION_EAST);
        assertThat(bird.getLastDirection()).isEqualTo(Direction.DIRECTION_EAST);
        assertThat(bird.getDeviation()).isEqualTo(5);
        assertThat(bird.getMoveIdx()).isEqualTo(0);
        assertThat(bird.getCurImageIdx()).isBetween(0, ImagesLoader.NB_BIRD_FLY_FRAME - 1);
    }
}