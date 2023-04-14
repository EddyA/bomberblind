package glue;

import images.ImagesLoader;
import org.mockito.Mockito;
import sprite.nomad.WhiteBird;
import sprite.nomad.FlyingNomad;
import utils.Direction;

import java.io.IOException;

public class FlyingNomadState {

    private final WhiteBird spiedWhiteBird;
    private boolean shouldBeRemoved;

    public FlyingNomadState() throws IOException {
        ImagesLoader.fillImagesMatrix();
        WhiteBird whiteBird = new WhiteBird(0, 0, Direction.DIRECTION_NORTH, 0);
        spiedWhiteBird = Mockito.spy(whiteBird);

        // avoid the time constraint to make action.
        Mockito.when(spiedWhiteBird.isTimeToAct()).thenReturn(true);
    }

    public FlyingNomad getFlyingNomad() {
        return spiedWhiteBird;
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
