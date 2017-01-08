package sprite.settled;

import images.ImagesLoader;
import org.assertj.core.api.WithAssertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import sprite.SpriteType;
import utils.CurrentTimeSupplier;
import utils.Tools;

import java.io.IOException;
import java.time.Instant;

import static org.mockito.Mockito.mock;
import static sprite.settled.LoopedSettled.Status.STATUS_ALIVE;
import static sprite.settled.LoopedSettled.Status.STATUS_ENDED;

public class TimedSettledTest implements WithAssertions {

    @Before
    public void fillImagesMatrix() throws IOException {
        ImagesLoader.fillImagesMatrix();
    }

    @Test
    public void constructorShouldSetMembersWithTheExpectedValues() throws Exception {
        Flame flame = new Flame(5, 4);

        // check members value.
        assertThat(flame.getRowIdx()).isEqualTo(5);
        assertThat(flame.getColIdx()).isEqualTo(4);
        assertThat(flame.getxMap()).isEqualTo(Tools.getCaseCentreAbscissa(4));
        assertThat(flame.getyMap()).isEqualTo(Tools.getCaseBottomOrdinate(5));
        assertThat(flame.getSpriteType()).isEqualTo(SpriteType.FLAME);
        assertThat(flame.getRefreshTime()).isEqualTo(Flame.REFRESH_TIME);
        assertThat(flame.getImages()).isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.flameMatrixRowIdx]);
        assertThat(flame.getNbImages()).isEqualTo(ImagesLoader.NB_FLAME_FRAME);
        assertThat(flame.getDurationTime()).isEqualTo(Flame.DURATION_TIME);
    }

    @Test
    public void updateStatusWithANotReachedDurationTimeShouldReturnFalseAndStatusShouldBeAlive() throws Exception {
        Flame flame = new Flame(5, 4);

        // mock CurrentTimeSupplier class to set currentTimeMillis to 1000ms.
        CurrentTimeSupplier currentTimeSupplier = mock(CurrentTimeSupplier.class);
        Mockito.when(currentTimeSupplier.get()).thenReturn(Instant.ofEpochMilli(10000L));
        flame.setCurrentTimeSupplier(currentTimeSupplier);

        // set the start time.
        flame.setStartTs(10000L - Flame.DURATION_TIME + 1);
        assertThat(flame.updateStatus()).isFalse();
        assertThat(flame.getStatus()).isEqualTo(STATUS_ALIVE);
    }

    @Test
    public void updateStatusShouldReturnTrueAndStatusShouldBeFinished() throws Exception {
        Flame flame = new Flame(5, 4);

        // mock CurrentTimeSupplier class to set currentTimeMillis to 1000ms.
        CurrentTimeSupplier currentTimeSupplier = mock(CurrentTimeSupplier.class);
        Mockito.when(currentTimeSupplier.get()).thenReturn(Instant.ofEpochMilli(10000L));
        flame.setCurrentTimeSupplier(currentTimeSupplier);

        // set the start time.
        flame.setStartTs(10000L - Flame.DURATION_TIME);
        assertThat(flame.updateStatus()).isTrue();
        assertThat(flame.getStatus()).isEqualTo(STATUS_ENDED);
    }
}