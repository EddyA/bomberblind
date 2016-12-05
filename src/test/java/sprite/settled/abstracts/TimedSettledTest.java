package sprite.settled.abstracts;

import images.ImagesLoader;
import org.assertj.core.api.WithAssertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import sprite.settled.Flame;
import utils.CurrentTimeSupplier;
import utils.Tools;

import java.io.IOException;
import java.time.Instant;

import static org.mockito.Mockito.mock;
import static sprite.settled.abstracts.TimedSettled.Status.STATUS_ALIVE;
import static sprite.settled.abstracts.TimedSettled.Status.STATUS_ENDED;

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
        assertThat(flame.getXMap()).isEqualTo(Tools.getCaseCentreAbscissa(4));
        assertThat(flame.getYMap()).isEqualTo(Tools.getCaseBottomOrdinate(5));
        assertThat(flame.getImages()).isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.flameMatrixRowIdx]);
        assertThat(flame.getNbImages()).isEqualTo(ImagesLoader.NB_FLAME_FRAME);
        assertThat(flame.getRefreshTime()).isEqualTo(Flame.REFRESH_TIME);
        assertThat(flame.getDurationTime()).isEqualTo(Flame.DURATION_TIME);
    }

    @Test
    public void updateStatusShouldReturnFalseAndStatusShouldBeAlive() throws Exception {
        Flame flame = new Flame(5, 4);

        // mock CurrentTimeSupplier class to set currentTimeMillis to 1000ms.
        CurrentTimeSupplier currentTimeSupplier = mock(CurrentTimeSupplier.class);
        Mockito.when(currentTimeSupplier.get()).thenReturn(Instant.ofEpochMilli(3000L));
        flame.setCurrentTimeSupplier(currentTimeSupplier);

        // set the start time.
        flame.setStartTs(3000L - Flame.DURATION_TIME + 1);
        assertThat(flame.updateStatus()).isFalse();
        assertThat(flame.getCurStatus()).isEqualTo(STATUS_ALIVE);
    }

    @Test
    public void updateStatusShouldReturnTrueAndStatusShouldBeFinished() throws Exception {
        Flame flame = new Flame(5, 4);

        // mock CurrentTimeSupplier class to set currentTimeMillis to 1000ms.
        CurrentTimeSupplier currentTimeSupplier = mock(CurrentTimeSupplier.class);
        Mockito.when(currentTimeSupplier.get()).thenReturn(Instant.ofEpochMilli(3000L));
        flame.setCurrentTimeSupplier(currentTimeSupplier);

        // set the start time.
        flame.setStartTs(3000L - Flame.DURATION_TIME);
        assertThat(flame.updateStatus()).isTrue();
        assertThat(flame.getCurStatus()).isEqualTo(STATUS_ENDED);
    }

    @Test
    public void isFinishedShouldReturnFalseWhenCurStatusIsAlive() throws Exception {
        Flame flame = new Flame(5, 4);

        // set the status and check.
        flame.setCurStatus(STATUS_ALIVE);
        assertThat(flame.isFinished()).isFalse();
    }

    @Test
    public void isFinishedShouldReturnTrueWhenCurStatusIsFinished() throws Exception {
        Flame flame = new Flame(5, 4);

        // set the status and check.
        flame.setCurStatus(STATUS_ENDED);
        assertThat(flame.isFinished()).isTrue();
    }
}