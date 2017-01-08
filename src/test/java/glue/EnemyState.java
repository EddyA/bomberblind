package glue;

import java.io.IOException;

import org.mockito.Mockito;

import images.ImagesLoader;
import sprite.nomad.CloakedSkeleton;
import sprite.nomad.WalkingEnemy;

public class EnemyState {

    private final CloakedSkeleton spyedCloakedSkeleton;
    private boolean shouldBeRemoved;

    public EnemyState() throws IOException {
        ImagesLoader.fillImagesMatrix();
        CloakedSkeleton cloakedSkeleton = new CloakedSkeleton(0, 0);
        spyedCloakedSkeleton = Mockito.spy(cloakedSkeleton);
    }

    public WalkingEnemy getEnemy() {
        return spyedCloakedSkeleton;
    }

    boolean isShouldBeRemoved() {
        return shouldBeRemoved;
    }

    void setShouldBeRemoved(boolean shouldBeRemoved) {
        this.shouldBeRemoved = shouldBeRemoved;
    }
}
