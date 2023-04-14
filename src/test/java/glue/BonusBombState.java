package glue;

import images.ImagesLoader;
import org.mockito.Mockito;
import sprite.settled.BonusBomb;

import java.io.IOException;

public class BonusBombState {
    private final BonusBomb spiedBonusBomb;

    public BonusBombState() throws IOException {
        ImagesLoader.fillImagesMatrix();
        BonusBomb bonusBomb = new BonusBomb(0, 0);
        spiedBonusBomb = Mockito.spy(bonusBomb);
    }

    BonusBomb getBonusBomb() {
        return spiedBonusBomb;
    }
}
