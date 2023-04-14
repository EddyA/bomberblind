package glue;

import java.io.IOException;

import org.mockito.Mockito;

import images.ImagesLoader;
import sprite.nomad.BlueBomber;
import sprite.nomad.Bomber;

public class BomberState {

    private final BlueBomber spiedBlueBomber;
    private boolean shouldBeRemoved;

    public BomberState() throws IOException {
        ImagesLoader.fillImagesMatrix();
        BlueBomber blueBomber = new BlueBomber(0, 0);
        spiedBlueBomber = Mockito.spy(blueBomber);
    }

    Bomber getBomber() {
        return spiedBlueBomber;
    }

    boolean isShouldBeRemoved() {
        return shouldBeRemoved;
    }

    void setShouldBeRemoved(boolean shouldBeRemoved) {
        this.shouldBeRemoved = shouldBeRemoved;
    }
}
