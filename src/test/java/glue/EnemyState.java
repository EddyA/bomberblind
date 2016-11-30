package glue;

import static org.mockito.Matchers.anyInt;

import java.io.IOException;

import org.mockito.Mockito;

import images.ImagesLoader;
import sprite.nomad.CloakedSkeleton;
import sprite.nomad.abstracts.Enemy;

public class EnemyState {

    private final CloakedSkeleton spyedCloakedSkeleton;

    public EnemyState() throws IOException {
        ImagesLoader.fillImagesMatrix();
        CloakedSkeleton cloakedSkeleton = new CloakedSkeleton(0, 0);
        spyedCloakedSkeleton = Mockito.spy(cloakedSkeleton);
    }

    public Enemy getEnemy() {
        return spyedCloakedSkeleton;
    }
}
