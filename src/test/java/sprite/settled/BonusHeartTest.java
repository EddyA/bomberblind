package sprite.settled;

import images.ImagesLoader;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Tools;

import java.io.IOException;


class BonusHeartTest implements WithAssertions {

    @BeforeEach
    void fillImagesMatrix() throws IOException {
        ImagesLoader.fillImagesMatrix();
    }

    @Test
    void constructorShouldSetMembersWithTheExpectedValues() {
        BonusHeart bonusHeart = new BonusHeart(5, 10);

        // check members value.
        assertThat(bonusHeart.getRowIdx()).isEqualTo(5);
        assertThat(bonusHeart.getColIdx()).isEqualTo(10);
        assertThat(bonusHeart.getXMap()).isEqualTo(Tools.getCaseCentreAbscissa(10));
        assertThat(bonusHeart.getYMap()).isEqualTo(Tools.getCaseBottomOrdinate(5));
        assertThat(bonusHeart.getBonusType()).isEqualTo(BonusType.TYPE_BONUS_HEART);
        assertThat(bonusHeart.getImages()).isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.bonusHeartMatrixRowIdx]);
        assertThat(bonusHeart.getNbImages()).isEqualTo(ImagesLoader.NB_BONUS_HEART_FRAME);
        assertThat(bonusHeart.getStatus()).isEqualTo(Bonus.Status.STATUS_ALIVE);
    }
}
