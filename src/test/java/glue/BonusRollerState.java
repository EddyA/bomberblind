package glue;

import images.ImagesLoader;
import org.mockito.Mockito;
import sprite.settled.BonusRoller;

import java.io.IOException;

public class BonusRollerState {

    private final BonusRoller spiedBonusRoller;

    public BonusRollerState() throws IOException {
        ImagesLoader.fillImagesMatrix();
        BonusRoller bonusRoller = new BonusRoller(0, 0);
        spiedBonusRoller = Mockito.spy(bonusRoller);
    }

    BonusRoller getBonusRoller() {
        return spiedBonusRoller;
    }
}
