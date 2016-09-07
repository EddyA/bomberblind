package sprites.settled;

import java.io.IOException;

import org.assertj.core.api.WithAssertions;
import org.junit.Before;
import org.junit.Test;

import images.ImagesLoader;

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
        assertThat(bomb.images).isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.bombMatrixRowIdx]);
        assertThat(bomb.nbImages).isEqualTo(ImagesLoader.NB_BOMB_FRAME);
        assertThat(bomb.refreshTime).isEqualTo(Bomb.REFRESH_TIME);
        assertThat(bomb.duration).isEqualTo(Bomb.DURATION_TIME);
    }

    @Test
    public void accessorsShouldReturnTheExpectedValues() throws Exception {
        Bomb bomb = new Bomb(5, 4, 3);

        assertThat(bomb.getFlameSize()).isEqualTo(3);
    }
}