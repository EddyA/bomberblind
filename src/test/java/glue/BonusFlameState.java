package glue;

import images.ImagesLoader;
import org.mockito.Mockito;
import sprite.settled.BonusFlame;

import java.io.IOException;

public class BonusFlameState {
    private final BonusFlame spyedBonusFlame;
    private boolean shouldBeRemoved;

    public BonusFlameState() throws IOException {
        ImagesLoader.fillImagesMatrix();
        BonusFlame bonusFlame = new BonusFlame(0, 0);
        spyedBonusFlame = Mockito.spy(bonusFlame);
    }

    BonusFlame getBonusFlame() {
        return spyedBonusFlame;
    }

    boolean isShouldBeRemoved() {
        return shouldBeRemoved;
    }

    void setShouldBeRemoved(boolean shouldBeRemoved) {
        this.shouldBeRemoved = shouldBeRemoved;
    }
}
