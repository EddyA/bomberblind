package glue;

import images.ImagesLoader;
import org.mockito.Mockito;
import sprite.settled.BonusRoller;

import java.io.IOException;

public class BonusRollerState {

    private final BonusRoller spyedBonusRoller;

    public BonusRollerState() throws IOException {
        ImagesLoader.fillImagesMatrix();
        BonusRoller bonusRoller = new BonusRoller(0, 0);
        spyedBonusRoller = Mockito.spy(bonusRoller);
    }

    BonusRoller getBonusRoller() {
        return spyedBonusRoller;
    }
}