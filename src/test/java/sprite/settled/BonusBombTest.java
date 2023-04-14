package sprite.settled;

import images.ImagesLoader;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Tools;

import java.io.IOException;

class BonusBombTest implements WithAssertions {

    @BeforeEach
    void fillImagesMatrix() throws IOException {
        ImagesLoader.fillImagesMatrix();
    }

    @Test
    void constructorShouldSetMembersWithTheExpectedValues() {
        BonusBomb bonusBomb = new BonusBomb(5, 10);

        // check members value.
        assertThat(bonusBomb.getRowIdx()).isEqualTo(5);
        assertThat(bonusBomb.getColIdx()).isEqualTo(10);
        assertThat(bonusBomb.getXMap()).isEqualTo(Tools.getCaseCentreAbscissa(10));
        assertThat(bonusBomb.getYMap()).isEqualTo(Tools.getCaseBottomOrdinate(5));
        assertThat(bonusBomb.getBonusType()).isEqualTo(BonusType.TYPE_BONUS_BOMB);
        assertThat(bonusBomb.getImages()).isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.bonusBombMatrixRowIdx]);
        assertThat(bonusBomb.getNbImages()).isEqualTo(ImagesLoader.NB_BONUS_BOMB_FRAME);
        assertThat(bonusBomb.getStatus()).isEqualTo(Bonus.Status.STATUS_ALIVE);
    }
}
