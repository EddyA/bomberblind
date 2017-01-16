package glue;

import java.io.IOException;

import org.mockito.Mockito;

import images.ImagesLoader;
import sprite.nomad.BreakingEnemy;
import sprite.nomad.Minotor;

public class BreakingEnemyState {

    private final Minotor spyedMinotor;
    private boolean shouldBeRemoved;

    public BreakingEnemyState() throws IOException {
        ImagesLoader.fillImagesMatrix();
        Minotor minotor = new Minotor(0, 0);
        spyedMinotor = Mockito.spy(minotor);
    }

    public BreakingEnemy getEnemy() {
        return spyedMinotor;
    }

    boolean isShouldBeRemoved() {
        return shouldBeRemoved;
    }

    void setShouldBeRemoved(boolean shouldBeRemoved) {
        this.shouldBeRemoved = shouldBeRemoved;
    }
}
