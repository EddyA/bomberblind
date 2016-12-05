package glue;

import java.io.IOException;

import org.mockito.Mockito;

import images.ImagesLoader;
import sprite.settled.Bomb;

public class BombState {
    private final Bomb spyedBomb;
    private boolean shouldBeRemoved;

    public BombState() throws IOException {
        ImagesLoader.fillImagesMatrix();
        Bomb bomb = new Bomb(0, 0, 0);
        spyedBomb = Mockito.spy(bomb);
    }

    public Bomb getBomb() {
        return spyedBomb;
    }

    public boolean isShouldBeRemoved() {
        return shouldBeRemoved;
    }

    public void setShouldBeRemoved(boolean shouldBeRemoved) {
        this.shouldBeRemoved = shouldBeRemoved;
    }
}
