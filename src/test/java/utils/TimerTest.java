package utils;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Instant;

import static org.mockito.Mockito.mock;

class TimerTest implements WithAssertions{

    @Test
    void getElapsedTimeWithoutStartingShouldReturnZero() {
        Timer timer = new Timer();
        assertThat(timer.getElapsedTime()).isEqualTo(0);
    }

    @Test
    void getElapsedTimeStartingButNotStoppingShouldReturnTheExpectedValue() {
        Timer timer = new Timer();

        // mock CurrentTimeSupplier class to set currentTimeMillis to 1000ms.
        CurrentTimeSupplier currentTimeSupplier = mock(CurrentTimeSupplier.class);
        Mockito.when(currentTimeSupplier.get()).thenReturn(Instant.ofEpochMilli(1000L));
        timer.setCurrentTimeSupplier(currentTimeSupplier);

        // start the timer.
        timer.start();

        // set currentTimeMillis to 5000ms
        Mockito.when(currentTimeSupplier.get()).thenReturn(Instant.ofEpochMilli(5000L));

        // test.
        assertThat(timer.getElapsedTime()).isEqualTo(4000); // 5000 - 1000.
    }

    @Test
    void getElapsedTimeStartingAndStoppingShouldReturnTheExecptedValue() {
        Timer timer = new Timer();

        // mock CurrentTimeSupplier class to set currentTimeMillis to 1000ms.
        CurrentTimeSupplier currentTimeSupplier = mock(CurrentTimeSupplier.class);
        Mockito.when(currentTimeSupplier.get()).thenReturn(Instant.ofEpochMilli(1000L));
        timer.setCurrentTimeSupplier(currentTimeSupplier);

        // start the timer.
        timer.start();

        // set currentTimeMillis to 5000ms
        Mockito.when(currentTimeSupplier.get()).thenReturn(Instant.ofEpochMilli(5000L));

        // stop the timer.
        timer.stop();

        // set currentTimeMillis to 10000ms
        Mockito.when(currentTimeSupplier.get()).thenReturn(Instant.ofEpochMilli(10000L));

        // test.
        assertThat(timer.getElapsedTime()).isEqualTo(4000); // 5000 - 1000.
    }


}
