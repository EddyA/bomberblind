package spriteList.ctrl;

import java.io.IOException;

import org.assertj.core.api.WithAssertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import images.ImagesLoader;
import sprite.nomad.CloakedSkeleton;
import sprite.nomad.abstracts.Enemy;

public class ActionMethodsTest implements WithAssertions {

    @Before
    public void fillImagesMatrix() throws IOException {
        ImagesLoader.fillImagesMatrix();
    }

    @Test
    public void processEnemyShouldReturnTrueWhenTheEnemyIsFinished() throws Exception {
        Enemy enemy = new CloakedSkeleton(1, 2);
        Enemy spyedEnemy = Mockito.spy(enemy);
        Mockito.when(spyedEnemy.isFinished()).thenReturn(true);

        // test (as the relative check is done at the begining of the function, the different parameters are not
        // instancied).
        assertThat(ActionMethods.processEnemy(null, null, 0, 0, spyedEnemy)).isTrue();
    }
}
