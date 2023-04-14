package sprite.settled;

import java.io.IOException;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import images.ImagesLoader;
import sprite.SpriteType;
import utils.Tools;

class FlameTest implements WithAssertions {

    @BeforeEach
    void fillImagesMatrix() throws IOException {
        ImagesLoader.fillImagesMatrix();
    }

    @Test
    void constructorShouldSetMembersWithTheExpectedValues() {
        Flame flame = new Flame(5, 4);

        // check members value.
        assertThat(flame.getRowIdx()).isEqualTo(5);
        assertThat(flame.getColIdx()).isEqualTo(4);
        assertThat(flame.getXMap()).isEqualTo(Tools.getCaseCentreAbscissa(4));
        assertThat(flame.getYMap()).isEqualTo(Tools.getCaseBottomOrdinate(5));
        assertThat(flame.getSpriteType()).isEqualTo(SpriteType.TYPE_SPRITE_FLAME);
        assertThat(flame.getRefreshTime()).isEqualTo(Flame.REFRESH_TIME);
        assertThat(flame.getImages()).isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.flameMatrixRowIdx]);
        assertThat(flame.getNbImages()).isEqualTo(ImagesLoader.NB_FLAME_FRAME);
        assertThat(flame.getDurationTime()).isEqualTo(Flame.DURATION_TIME);
    }
}
