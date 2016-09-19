package sprites.settled;

import java.io.IOException;

import org.assertj.core.api.WithAssertions;
import org.junit.Before;
import org.junit.Test;

import images.ImagesLoader;
import utils.Tools;

public class FlameTest implements WithAssertions {

    @Before
    public void fillImagesMatrix() throws IOException {
        ImagesLoader.fillImagesMatrix();
    }

    @Test
    public void constructorShouldSetMembersWithTheExpectedValues() throws Exception {
        Flame flame = new Flame(5, 4);

        // check members value.
        assertThat(flame.getRowIdx()).isEqualTo(5);
        assertThat(flame.getColIdx()).isEqualTo(4);
        assertThat(flame.getXMap()).isEqualTo(Tools.getCaseCentreAbscissa(4));
        assertThat(flame.getYMap()).isEqualTo(Tools.getCaseBottomOrdinate(5));
        assertThat(flame.getImages()).isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.flameMatrixRowIdx]);
        assertThat(flame.getNbImages()).isEqualTo(ImagesLoader.NB_FLAME_FRAME);
        assertThat(flame.getRefreshTime()).isEqualTo(Flame.REFRESH_TIME);
        assertThat(flame.getDurationTime()).isEqualTo(Flame.DURATION_TIME);
    }
}