package glue;

import images.ImagesLoader;
import org.mockito.Mockito;
import sprite.nomad.BreakingEnemy;
import sprite.nomad.RedSpearSoldier;

import java.io.IOException;

public class BreakingEnemyState {

    private final RedSpearSoldier spiedRedSpearSoldier;
    private boolean shouldBeRemoved;

    public BreakingEnemyState() throws IOException {
        ImagesLoader.fillImagesMatrix();
        RedSpearSoldier redSpearSoldier = new RedSpearSoldier(0, 0);
        spiedRedSpearSoldier = Mockito.spy(redSpearSoldier);

        // avoid the time constraint to make action.
        Mockito.when(spiedRedSpearSoldier.isTimeToAct()).thenReturn(true);
    }

    public BreakingEnemy getEnemy() {
        return spiedRedSpearSoldier;
    }

    boolean isShouldBeRemoved() {
        return shouldBeRemoved;
    }

    void setShouldBeRemoved(boolean shouldBeRemoved) {
        this.shouldBeRemoved = shouldBeRemoved;
    }
}
