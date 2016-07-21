package utils;

import java.time.Instant;
import java.util.function.Supplier;

/**
 * Provide a way  to control time (test purpose).
 */
public class CurrentTimeSupplier implements Supplier<Instant> {

    @Override
    public Instant get() {
        return Instant.now();
    }
}
