package sprite.settled;

import images.ImagesLoader;
import org.assertj.core.api.WithAssertions;
import org.junit.Before;
import org.junit.Test;
import utils.Tools;

import java.io.IOException;

public class BonusBombTest implements WithAssertions {

    @Before
    public void fillImagesMatrix() throws IOException {
        ImagesLoader.fillImagesMatrix();
    }

    @Test
    public void constructorShouldSetMembersWithTheExpectedValues() throws Exception {
        BonusBomb bonusBomb = new BonusBomb(5, 10);

        // check members value.
        assertThat(bonusBomb.getRowIdx()).isEqualTo(5);
        assertThat(bonusBomb.getColIdx()).isEqualTo(10);
        assertThat(bonusBomb.getxMap()).isEqualTo(Tools.getCaseCentreAbscissa(10));
        assertThat(bonusBomb.getyMap()).isEqualTo(Tools.getCaseBottomOrdinate(5));
        assertThat(bonusBomb.getBonusType()).isEqualTo(BonusType.TYPE_BONUS_BOMB);
        assertThat(bonusBomb.getImages()).isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.bonusBombMatrixRowIdx]);
        assertThat(bonusBomb.getNbImages()).isEqualTo(ImagesLoader.NB_BONUS_BOMB_FRAME);
        assertThat(bonusBomb.getStatus()).isEqualTo(Bonus.Status.STATUS_ALIVE);
    }
}