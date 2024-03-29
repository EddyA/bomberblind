package sprite.settled;

import java.io.IOException;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import images.ImagesLoader;
import sprite.SpriteType;
import utils.Tools;

import static images.ImagesLoader.NB_FLAME_END_FRAME;

class FlameEndTest implements WithAssertions {

    @BeforeEach
    void fillImagesMatrix() throws IOException {
        ImagesLoader.fillImagesMatrix();
    }

    @Test
    void constructorShouldSetMembersWithTheExpectedValues() {
        FlameEnd flameEnd = new FlameEnd(5, 4);

        // check members value.
        assertThat(flameEnd.getRowIdx()).isEqualTo(5);
        assertThat(flameEnd.getColIdx()).isEqualTo(4);
        assertThat(flameEnd.getXMap()).isEqualTo(Tools.getCaseCentreAbscissa(4));
        assertThat(flameEnd.getYMap()).isEqualTo(Tools.getCaseBottomOrdinate(5));
        assertThat(flameEnd.getSpriteType()).isEqualTo(SpriteType.TYPE_SPRITE_FLAME_END);
        assertThat(flameEnd.getRefreshTime()).isEqualTo(FlameEnd.REFRESH_TIME);
        assertThat(flameEnd.getImages()).isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.flameEndMatrixRowIdx]);
        assertThat(flameEnd.getNbImages()).isEqualTo(NB_FLAME_END_FRAME);
        assertThat(flameEnd.getNbTimes()).isEqualTo(FlameEnd.NB_TIMES);
    }
}
