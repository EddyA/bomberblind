package utils;

import java.time.Instant;
import java.util.function.Supplier;

public class CurrentTimeSupplier implements Supplier<Instant> {

    @Override
    public Instant get() {
        return Instant.now();
    }
}
