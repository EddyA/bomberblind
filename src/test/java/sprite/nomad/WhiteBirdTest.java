package sprite.nomad;

import static sprite.SpriteAction.ACTION_FLYING;

import java.io.IOException;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import images.ImagesLoader;
import sprite.SpriteType;
import utils.Direction;

class WhiteBirdTest implements WithAssertions {

    @BeforeEach
    void fillImagesMatrix() throws IOException {
        ImagesLoader.fillImagesMatrix();
    }

    @Test
    void constructorShouldSetMembersWithTheExpectedValues() {
        WhiteBird whiteBird = new WhiteBird(15, 30, Direction.DIRECTION_EAST, 5);

        // check members value.
        assertThat(whiteBird.getXMap()).isEqualTo(15);
        assertThat(whiteBird.getYMap()).isEqualTo(30);
        assertThat(whiteBird.getSpriteType()).isEqualTo(SpriteType.TYPE_SPRITE_FLYING_NOMAD);
        assertThat(whiteBird.getFlyBackImages()).isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.birdFlyBackMatrixRowIdx]);
        assertThat(whiteBird.getFlyFrontImages()).isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.birdFlyFrontMatrixRowIdx]);
        assertThat(whiteBird.getFlyLeftImages()).isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.birdFlyLeftMatrixRowIdx]);
        assertThat(whiteBird.getFlyRightImages()).isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.birdFlyRightMatrixRowIdx]);
        assertThat(whiteBird.getNbFlyFrame()).isEqualTo(ImagesLoader.NB_BIRD_FLY_FRAME);
        assertThat(whiteBird.getRefreshTime()).isEqualTo(WhiteBird.REFRESH_TIME);
        assertThat(whiteBird.getActingTime()).isEqualTo(WhiteBird.ACTING_TIME);
        assertThat(whiteBird.getCurSpriteAction()).isEqualTo(ACTION_FLYING);
        assertThat(whiteBird.getLastSpriteAction()).isEqualTo(ACTION_FLYING);
        assertThat(whiteBird.getCurDirection()).isEqualTo(Direction.DIRECTION_EAST);
        assertThat(whiteBird.getLastDirection()).isEqualTo(Direction.DIRECTION_EAST);
        assertThat(whiteBird.getDeviation()).isEqualTo(5);
        assertThat(whiteBird.getMoveIdx()).isEqualTo(0);
        assertThat(whiteBird.getCurImageIdx()).isBetween(0, ImagesLoader.NB_BIRD_FLY_FRAME - 1);
    }
}
