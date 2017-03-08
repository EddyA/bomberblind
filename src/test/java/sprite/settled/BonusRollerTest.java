package sprite.settled;

import images.ImagesLoader;
import org.assertj.core.api.WithAssertions;
import org.junit.Before;
import org.junit.Test;
import utils.Tools;

import java.io.IOException;

public class BonusRollerTest implements WithAssertions {

    @Before
    public void fillImagesMatrix() throws IOException {
        ImagesLoader.fillImagesMatrix();
    }

    @Test
    public void constructorShouldSetMembersWithTheExpectedValues() throws Exception {
        BonusRoller bonusRoller = new BonusRoller(5, 10);

        // check members value.
        assertThat(bonusRoller.getRowIdx()).isEqualTo(5);
        assertThat(bonusRoller.getColIdx()).isEqualTo(10);
        assertThat(bonusRoller.getxMap()).isEqualTo(Tools.getCaseCentreAbscissa(10));
        assertThat(bonusRoller.getyMap()).isEqualTo(Tools.getCaseBottomOrdinate(5));
        assertThat(bonusRoller.getBonusType()).isEqualTo(BonusType.TYPE_BONUS_ROLLER);
        assertThat(bonusRoller.getImages()).isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.bonusRollerMatrixRowIdx]);
        assertThat(bonusRoller.getNbImages()).isEqualTo(ImagesLoader.NB_BONUS_ROLLER_FRAME);
        assertThat(bonusRoller.getStatus()).isEqualTo(Bonus.Status.STATUS_ALIVE);
    }
}