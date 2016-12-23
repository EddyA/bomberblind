package utils;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Optional;

/**
 * This class compute the screen resolution according to the screen format.
 */
public class FullscreenResolution {

    // the different supported screen format
    private final static double SCREEN_FORMAT_4_3;
    private final static double SCREEN_FORMAT_16_9;
    private final static double SCREEN_FORMAT_16_10;

    private final static DecimalFormat decimalFormat;

    static {
        decimalFormat = new DecimalFormat("#.##");
        decimalFormat.setRoundingMode(RoundingMode.CEILING);
        SCREEN_FORMAT_4_3 = Double.parseDouble(decimalFormat.format(4d / 3d).replace(",", "."));
        SCREEN_FORMAT_16_9 = Double.parseDouble(decimalFormat.format(16d / 9d).replace(",", "."));
        SCREEN_FORMAT_16_10 = Double.parseDouble(decimalFormat.format(16d / 10d).replace(",", "."));
    }

    /**
     * Dynamically compute the width/height screen resolution according to a screen format.
     *
     * @param screenFormat the screen format
     * @return a tuple holding the screen resolution width/height> if the screen format is supported,
     * an empty otopnnal otherwise.
     */
    public static Optional<Tuple2<Integer, Integer>> compute(double screenFormat) {
        double truncatedScreenFormat = Double.parseDouble(decimalFormat.format(screenFormat).replace(",", "."));

        if (truncatedScreenFormat == SCREEN_FORMAT_4_3) {
            return Optional.of(new Tuple2<>(1024, 768));
        } else if (truncatedScreenFormat == SCREEN_FORMAT_16_9) {
            return Optional.of(new Tuple2<>(1280, 720));
        } else if (truncatedScreenFormat == SCREEN_FORMAT_16_10) {
            return Optional.of(new Tuple2<>(1280, 800));
        } else {
            return Optional.empty();
        }
    }
}

