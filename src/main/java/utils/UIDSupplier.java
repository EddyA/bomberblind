package utils;

import java.util.function.Supplier;

/**
 * Provide a way to control unique identifiers.
 */
public class UIDSupplier implements Supplier<Integer> {

    private static int uid = 0;

    @Override
    public Integer get() {
        return uid++;
    }
}
