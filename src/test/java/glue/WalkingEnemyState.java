package glue;

import java.io.IOException;

import org.mockito.Mockito;

import images.ImagesLoader;
import sprite.nomad.CloakedSkeleton;
import sprite.nomad.WalkingEnemy;

public class WalkingEnemyState {

    private final CloakedSkeleton spyedCloakedSkeleton;
    private boolean shouldBeRemoved;

    public WalkingEnemyState() throws IOException {
        ImagesLoader.fillImagesMatrix();
        CloakedSkeleton cloakedSkeleton = new CloakedSkeleton(0, 0);
        spyedCloakedSkeleton = Mockito.spy(cloakedSkeleton);

        // avoid the time constraint to make action.
        Mockito.when(spyedCloakedSkeleton.isTimeToAct()).thenReturn(true);
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
