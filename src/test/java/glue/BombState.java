package glue;

import static org.mockito.Matchers.anyInt;

import java.io.IOException;

import org.mockito.Mockito;

import images.ImagesLoader;
import sprite.nomad.abstracts.Enemy;
import sprite.settled.Bomb;

public class BombState {
    private final Bomb spyedBomb;

    public BombState() throws IOException {
        ImagesLoader.fillImagesMatrix();
        Bomb bomb = new Bomb(0, 0, 0);
        spyedBomb = Mockito.spy(bomb);
    }

    public Bomb getBomb() {
        return spyedBomb;
    }
}
