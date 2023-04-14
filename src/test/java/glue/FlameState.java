package glue;

import java.io.IOException;

import org.mockito.Mockito;

import images.ImagesLoader;
import sprite.settled.Flame;

public class FlameState {

    private final Flame spiedFlame;
    private boolean shouldBeRemoved;

    public FlameState() throws IOException {
        ImagesLoader.fillImagesMatrix();
        Flame flame = new Flame(0, 0);
        spiedFlame = Mockito.spy(flame);
    }

    Flame getFlame() {
        return spiedFlame;
    }

    boolean isShouldBeRemoved() {
        return shouldBeRemoved;
    }

    void setShouldBeRemoved(boolean shouldBeRemoved) {
        this.shouldBeRemoved = shouldBeRemoved;
    }
}
