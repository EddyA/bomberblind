package sprite.settled;

import java.io.IOException;

import org.assertj.core.api.WithAssertions;
import org.junit.Before;
import org.junit.Test;

import images.ImagesLoader;
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
        assertThat(bomb.getXMap()).isEqualTo(Tools.getCaseCentreAbscissa(4));
        assertThat(bomb.getYMap()).isEqualTo(Tools.getCaseBottomOrdinate(5));
        assertThat(bomb.getImages()).isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.bombMatrixRowIdx]);
        assertThat(bomb.getNbImages()).isEqualTo(ImagesLoader.NB_BOMB_FRAME);
        assertThat(bomb.getRefreshTime()).isEqualTo(Bomb.REFRESH_TIME);
        assertThat(bomb.getDurationTime()).isEqualTo(Bomb.DURATION_TIME);
    }

    @Test
    public void accessorsShouldReturnTheExpectedValues() throws Exception {
        Bomb bomb = new Bomb(5, 4, 3);

        assertThat(bomb.getFlameSize()).isEqualTo(3);
    }
}