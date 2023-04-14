package glue;

import java.io.IOException;

import org.mockito.Mockito;

import images.ImagesLoader;
import sprite.nomad.Zora;
import sprite.nomad.WalkingEnemy;

public class WalkingEnemyState {

    private final Zora spiedzora;
    private boolean shouldBeRemoved;

    public WalkingEnemyState() throws IOException {
        ImagesLoader.fillImagesMatrix();
        Zora zora = new Zora(0, 0);
        spiedzora = Mockito.spy(zora);

        // avoid the time constraint to make action.
        Mockito.when(spiedzora.isTimeToAct()).thenReturn(true);
    }

    public WalkingEnemy getEnemy() {
        return spiedzora;
    }

    boolean isShouldBeRemoved() {
        return shouldBeRemoved;
    }

    void setShouldBeRemoved(boolean shouldBeRemoved) {
        this.shouldBeRemoved = shouldBeRemoved;
    }
}
