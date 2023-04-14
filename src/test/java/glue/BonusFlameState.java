package glue;

import images.ImagesLoader;
import org.mockito.Mockito;
import sprite.settled.BonusFlame;

import java.io.IOException;

public class BonusFlameState {
    private final BonusFlame spiedBonusFlame;
    private boolean shouldBeRemoved;

    public BonusFlameState() throws IOException {
        ImagesLoader.fillImagesMatrix();
        BonusFlame bonusFlame = new BonusFlame(0, 0);
        spiedBonusFlame = Mockito.spy(bonusFlame);
    }

    BonusFlame getBonusFlame() {
        return spiedBonusFlame;
    }

    boolean isShouldBeRemoved() {
        return shouldBeRemoved;
    }

    void setShouldBeRemoved(boolean shouldBeRemoved) {
        this.shouldBeRemoved = shouldBeRemoved;
    }
}
