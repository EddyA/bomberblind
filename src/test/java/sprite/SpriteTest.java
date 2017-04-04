package sprite;

import images.ImagesLoader;
import org.assertj.core.api.WithAssertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import sprite.nomad.BlueBomber;
import utils.CurrentTimeSupplier;

import java.io.IOException;
import java.time.Instant;

import static org.mockito.Mockito.mock;

public class SpriteTest implements WithAssertions {

    @Before
    public void fillImagesMatrix() throws IOException {
        ImagesLoader.fillImagesMatrix();
    }

    @Test
    public void constructorShouldSetMembersWithTheExpectedValues() throws Exception {
        BlueBomber blueBomber = new BlueBomber(5, 4);

        // check members value.
        assertThat(blueBomber.getxMap()).isEqualTo(5);
        assertThat(blueBomber.getyMap()).isEqualTo(4);
        assertThat(blueBomber.getSpriteType()).isEqualTo(SpriteType.TYPE_SPRITE_BOMBER);
        assertThat(blueBomber.getRefreshTime()).isEqualTo(BlueBomber.REFRESH_TIME);
    }

    @Test
    public void isTimeToRefreshShouldReturnFalse() throws Exception {
        BlueBomber blueBomber = new BlueBomber(5, 4);

        // mock CurrentTimeSupplier class to set currentTimeMillis to 1000ms.
        CurrentTimeSupplier currentTimeSupplier = mock(CurrentTimeSupplier.class);
        Mockito.when(currentTimeSupplier.get()).thenReturn(Instant.ofEpochMilli(1000L));
        blueBomber.setCurrentTimeSupplier(currentTimeSupplier);

        // current time - last refresh time - 1 < 1000ms -> should return false.
        blueBomber.setLastRefreshTs(1000L - BlueBomber.REFRESH_TIME + 1);

        // check value.
        assertThat(blueBomber.isTimeToRefresh()).isFalse();
    }

    @Test
    public void isTimeToRefreshShouldReturnTrue() throws Exception {
        BlueBomber blueBomber = new BlueBomber(5, 4);

        // mock CurrentTimeSupplier class to set currentTimeMillis to 1000ms.
        CurrentTimeSupplier currentTimeSupplier = mock(CurrentTimeSupplier.class);
        Mockito.when(currentTimeSupplier.get()).thenReturn(Instant.ofEpochMilli(1000L));
        blueBomber.setCurrentTimeSupplier(currentTimeSupplier);

        // current time - last refresh time >= 1000ms -> should return false.
        blueBomber.setLastRefreshTs(1000L - BlueBomber.REFRESH_TIME);

        // check value.
        assertThat(blueBomber.isTimeToRefresh()).isTrue();
    }
}