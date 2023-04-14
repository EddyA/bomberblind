package glue;

import java.io.IOException;

import org.mockito.Mockito;

import images.ImagesLoader;
import sprite.settled.Bomb;

public class BombState {
    private final Bomb spiedBomb;
    private boolean shouldBeRemoved;

    public BombState() throws IOException {
        ImagesLoader.fillImagesMatrix();
        Bomb bomb = new Bomb(0, 0, 0);
        spiedBomb = Mockito.spy(bomb);
    }

    Bomb getBomb() {
        return spiedBomb;
    }

    boolean isShouldBeRemoved() {
        return shouldBeRemoved;
    }

    void setShouldBeRemoved(boolean shouldBeRemoved) {
        this.shouldBeRemoved = shouldBeRemoved;
    }
}
