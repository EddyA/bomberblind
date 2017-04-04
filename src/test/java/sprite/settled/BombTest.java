package sprite.settled;

import java.io.IOException;

import org.assertj.core.api.WithAssertions;
import org.junit.Before;
import org.junit.Test;

import images.ImagesLoader;
import sprite.SpriteType;
import utils.Tools;

public class BombTest implements WithAssertions {

    @Before
    public void fillImagesMatrix() throws IOException {
        ImagesLoader.fillImagesMatrix();
    }

    @Test
    public void constructorShouldSetMembersWithTheExpectedValues() throws Exception {
        Bomb bomb = new Bomb(5, 4, 3);

        // check members value.
        assertThat(bomb.getRowIdx()).isEqualTo(5);
        assertThat(bomb.getColIdx()).isEqualTo(4);
        assertThat(bomb.getxMap()).isEqualTo(Tools.getCaseCentreAbscissa(4));
        assertThat(bomb.getyMap()).isEqualTo(Tools.getCaseBottomOrdinate(5));
        assertThat(bomb.getSpriteType()).isEqualTo(SpriteType.TYPE_SPRITE_BOMB);
        assertThat(bomb.getRefreshTime()).isEqualTo(Bomb.REFRESH_TIME);
        assertThat(bomb.getImages()).isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.bombMatrixRowIdx]);
        assertThat(bomb.getNbImages()).isEqualTo(ImagesLoader.NB_BOMB_FRAME);
        assertThat(bomb.getDurationTime()).isEqualTo(Bomb.DURATION_TIME);
    }

    @Test
    public void accessorsShouldReturnTheExpectedValues() throws Exception {

        // set by the constructor.
        Bomb bomb = new Bomb(5, 4, 3);
        assertThat(bomb.getFlameSize()).isEqualTo(3);
    }
}