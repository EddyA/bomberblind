package sprite.settled;

import images.ImagesLoader;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Tools;

import java.io.IOException;

class BonusRollerTest implements WithAssertions {

    @BeforeEach
    void fillImagesMatrix() throws IOException {
        ImagesLoader.fillImagesMatrix();
    }

    @Test
    void constructorShouldSetMembersWithTheExpectedValues() {
        BonusRoller bonusRoller = new BonusRoller(5, 10);

        // check members value.
        assertThat(bonusRoller.getRowIdx()).isEqualTo(5);
        assertThat(bonusRoller.getColIdx()).isEqualTo(10);
        assertThat(bonusRoller.getXMap()).isEqualTo(Tools.getCaseCentreAbscissa(10));
        assertThat(bonusRoller.getYMap()).isEqualTo(Tools.getCaseBottomOrdinate(5));
        assertThat(bonusRoller.getBonusType()).isEqualTo(BonusType.TYPE_BONUS_ROLLER);
        assertThat(bonusRoller.getImages()).isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.bonusRollerMatrixRowIdx]);
        assertThat(bonusRoller.getNbImages()).isEqualTo(ImagesLoader.NB_BONUS_ROLLER_FRAME);
        assertThat(bonusRoller.getStatus()).isEqualTo(Bonus.Status.STATUS_ALIVE);
    }
}
