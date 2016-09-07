package sprites.settled;

import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.time.Instant;

import org.assertj.core.api.WithAssertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import images.ImagesLoader;
import utils.CurrentTimeSupplier;

public class TimedSettledTest implements WithAssertions {

    @Before
    public void fillImagesMatrix() throws IOException {
        ImagesLoader.fillImagesMatrix();
    }

    @Test
    public void isFinishedShouldReturnFalseWhileTimeIsNotReached() throws Exception {
        Bomb bomb = new Bomb(5, 4, 3);

        // mock CurrentTimeSupplier class to set currentTimeMillis to DURATION_TIME - 1 (1ms before stopping).
        CurrentTimeSupplier currentTimeSupplier = mock(CurrentTimeSupplier.class);
        Mockito.when(currentTimeSupplier.get()).thenReturn(Instant.ofEpochMilli(bomb.startTs + Bomb.DURATION_TIME - 1));
        bomb.currentTimeSupplier = currentTimeSupplier;

        assertThat(bomb.isFinished()).isFalse();
    }

    @Test
    public void isFinishedShouldReturnTrueWhenTimeIsReached() throws Exception {
        Bomb bomb = new Bomb(5, 4, 3);

        // mock CurrentTimeSupplier class to set currentTimeMillis to DURATION_TIME (time has been reached.).
        CurrentTimeSupplier currentTimeSupplier = mock(CurrentTimeSupplier.class);
        Mockito.when(currentTimeSupplier.get()).thenReturn(Instant.ofEpochMilli(bomb.startTs + Bomb.DURATION_TIME));
        bomb.currentTimeSupplier = currentTimeSupplier;

        assertThat(bomb.isFinished()).isTrue();
    }
}