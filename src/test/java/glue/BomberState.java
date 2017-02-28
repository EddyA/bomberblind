package glue;

import java.io.IOException;

import org.mockito.Mockito;

import images.ImagesLoader;
import sprite.nomad.BlueBomber;
import sprite.nomad.Bomber;

public class BomberState {

    private final BlueBomber spyedBlueBomber;
    private boolean shouldBeRemoved;

    public BomberState() throws IOException {
        ImagesLoader.fillImagesMatrix();
        BlueBomber blueBomber = new BlueBomber(0, 0);
        spyedBlueBomber = Mockito.spy(blueBomber);
    }

    Bomber getBomber() {
        return spyedBlueBomber;
    }

    boolean isShouldBeRemoved() {
        return shouldBeRemoved;
    }

    void setShouldBeRemoved(boolean shouldBeRemoved) {
        this.shouldBeRemoved = shouldBeRemoved;
    }
}
