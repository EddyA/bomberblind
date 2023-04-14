package glue;

import images.ImagesLoader;
import org.mockito.Mockito;
import sprite.settled.BonusHeart;

import java.io.IOException;

public class BonusHeartState {

    private final BonusHeart spiedBonusHeart;

    public BonusHeartState() throws IOException {
        ImagesLoader.fillImagesMatrix();
        BonusHeart bonusHeart = new BonusHeart(0, 0);
        spiedBonusHeart = Mockito.spy(bonusHeart);
    }

    BonusHeart getBonusHeart() {
        return spiedBonusHeart;
    }
}
