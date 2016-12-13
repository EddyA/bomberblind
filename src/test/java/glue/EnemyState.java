package glue;

import java.io.IOException;

import org.mockito.Mockito;

import images.ImagesLoader;
import sprite.nomad.CloakedSkeleton;
import sprite.nomad.Enemy;

public class EnemyState {

    private final CloakedSkeleton spyedCloakedSkeleton;
    private boolean shouldBeRemoved;

    public EnemyState() throws IOException {
        ImagesLoader.fillImagesMatrix();
        CloakedSkeleton cloakedSkeleton = new CloakedSkeleton(0, 0);
        spyedCloakedSkeleton = Mockito.spy(cloakedSkeleton);
    }

    public Enemy getEnemy() {
        return spyedCloakedSkeleton;
    }

    public boolean isShouldBeRemoved() {
        return shouldBeRemoved;
    }

    public void setShouldBeRemoved(boolean shouldBeRemoved) {
        this.shouldBeRemoved = shouldBeRemoved;
    }
}
