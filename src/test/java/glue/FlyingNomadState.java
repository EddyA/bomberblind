package glue;

import images.ImagesLoader;
import org.mockito.Mockito;
import sprite.nomad.Bird;
import sprite.nomad.FlyingNomad;
import utils.Direction;

import java.io.IOException;

public class FlyingNomadState {

    private final Bird spyedBird;
    private boolean shouldBeRemoved;

    public FlyingNomadState() throws IOException {
        ImagesLoader.fillImagesMatrix();
        Bird bird = new Bird(0, 0, Direction.DIRECTION_NORTH, 0);
        spyedBird = Mockito.spy(bird);

        // avoid the time constraint to make action.
        Mockito.when(spyedBird.isTimeToAct()).thenReturn(true);
    }

    public FlyingNomad getFlyingNomad() {
        return spyedBird;
    }

    public int getFlyingNomadWidth() {
        return 42; // based on bird sprite.
    }

    public int getFlyingNomadHeight() {
        return 44; // based on bird sprite.
    }

    boolean isShouldBeRemoved() {
        return shouldBeRemoved;
    }

    void setShouldBeRemoved(boolean shouldBeRemoved) {
        this.shouldBeRemoved = shouldBeRemoved;
    }
}
