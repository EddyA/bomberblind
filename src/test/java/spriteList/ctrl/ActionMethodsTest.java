package spriteList.ctrl;

import java.io.IOException;
import java.util.LinkedList;

import org.assertj.core.api.WithAssertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import images.ImagesLoader;
import map.MapPoint;
import sprite.Sprite;
import sprite.nomad.CloakedSkeleton;
import sprite.nomad.Enemy;

public class ActionMethodsTest implements WithAssertions {

    @Before
    public void fillImagesMatrix() throws IOException {
        ImagesLoader.fillImagesMatrix();
    }

    @Test
    public void processEnemyShouldReturnTrueWhenTheEnemyIsFinished() throws Exception {
        int mapWidth = 2;
        int mapHeight = 2;

        // set map.
        MapPoint[][] mapPointMatrix = new MapPoint[mapHeight][mapWidth];
        for (int rowIdx = 0; rowIdx < mapHeight; rowIdx++) {
            for (int colIdx = 0; colIdx < mapWidth; colIdx++) {
                mapPointMatrix[rowIdx][colIdx] = new MapPoint(rowIdx, colIdx);
            }
        }

        Enemy enemy = new CloakedSkeleton(15, 30);
        Enemy spyedEnemy = Mockito.spy(enemy);
        Mockito.when(spyedEnemy.isFinished()).thenReturn(true);

        // test (as the relative check is done at the begining of the function, the different parameters are not
        // instancied).
        assertThat(ActionMethods.processEnemy(null, mapPointMatrix, mapWidth, mapHeight, spyedEnemy)).isTrue();
    }
}
