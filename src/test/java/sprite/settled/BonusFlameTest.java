package sprite.settled;

import images.ImagesLoader;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Tools;

import java.io.IOException;

class BonusFlameTest implements WithAssertions {

    @BeforeEach
    void fillImagesMatrix() throws IOException {
        ImagesLoader.fillImagesMatrix();
    }

    @Test
    void constructorShouldSetMembersWithTheExpectedValues() {
        BonusFlame bonusFlame = new BonusFlame(5, 10);

        // check members value.
        assertThat(bonusFlame.getRowIdx()).isEqualTo(5);
        assertThat(bonusFlame.getColIdx()).isEqualTo(10);
        assertThat(bonusFlame.getXMap()).isEqualTo(Tools.getCaseCentreAbscissa(10));
        assertThat(bonusFlame.getYMap()).isEqualTo(Tools.getCaseBottomOrdinate(5));
        assertThat(bonusFlame.getBonusType()).isEqualTo(BonusType.TYPE_BONUS_FLAME);
        assertThat(bonusFlame.getImages()).isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.bonusFlameMatrixRowIdx]);
        assertThat(bonusFlame.getNbImages()).isEqualTo(ImagesLoader.NB_BONUS_FLAME_FRAME);
        assertThat(bonusFlame.getStatus()).isEqualTo(Bonus.Status.STATUS_ALIVE);
    }
}
