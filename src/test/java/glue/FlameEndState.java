package glue;

import java.io.IOException;

import org.mockito.Mockito;

import images.ImagesLoader;
import sprite.settled.FlameEnd;

public class FlameEndState {

    private final FlameEnd spyedFlameEnd;
    private boolean shouldBeRemoved;

    public FlameEndState() throws IOException {
        ImagesLoader.fillImagesMatrix();
        FlameEnd flameEnd = new FlameEnd(0, 0);
        spyedFlameEnd = Mockito.spy(flameEnd);
    }

    FlameEnd getFlameEnd() {
        return spyedFlameEnd;
    }

    boolean isShouldBeRemoved() {
        return shouldBeRemoved;
    }

    void setShouldBeRemoved(boolean shouldBeRemoved) {
        this.shouldBeRemoved = shouldBeRemoved;
    }
}
