package utils;

import javax.swing.*;
import java.awt.*;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Optional;
import lombok.experimental.UtilityClass;

/**
 * This class computeFullscreenResolution the screen resolution according to the screen format.
 */
@UtilityClass
public class ScreenMode {

    // the different supported screen format
    private static final  double SCREEN_FORMAT_4_3;
    private static final double SCREEN_FORMAT_16_9;
    private static final double SCREEN_FORMAT_16_10;

    private static final DecimalFormat decimalFormat;

    static {
        decimalFormat = new DecimalFormat("#.##");
        decimalFormat.setRoundingMode(RoundingMode.CEILING);
        SCREEN_FORMAT_4_3 = Double.parseDouble(decimalFormat.format(4d / 3d).replace(",", "."));
        SCREEN_FORMAT_16_9 = Double.parseDouble(decimalFormat.format(16d / 9d).replace(",", "."));
        SCREEN_FORMAT_16_10 = Double.parseDouble(decimalFormat.format(16d / 10d).replace(",", "."));
    }

    /**
     * Compute the fullscreen resolution according to the screen format.
     *
     * @param screenFormat the screen format
     * @return a tuple holding the screen resolution (width, height) if the screen format is supported,
     * an empty optional otherwise.
     */
    public static Optional<Tuple2<Integer, Integer>> computeFullscreenResolution(double screenFormat) {
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

    /**
     * Set the fullscreen mode.
     *
     * @param graphicsDevice the relative GraphicsDevice (i.e. the screen to work with)
     * @param jFrame         the JFrame to fullscreen
     * @param screenWidth    the requested screen width
     * @param screenHeight   the requested screen height
     * @return true if the fullscreen mode has been well set, false otherwise.
     */
    public static boolean setFullscreenMode(GraphicsDevice graphicsDevice,
                                            JFrame jFrame,
                                            int screenWidth,
                                            int screenHeight) {
        if (graphicsDevice.isFullScreenSupported()) { // is the fullscreen supported by the hardware?
            jFrame.setUndecorated(true);
            jFrame.setAlwaysOnTop(true);
            graphicsDevice.setFullScreenWindow(jFrame);
            try {
                graphicsDevice.setDisplayMode(
                        new DisplayMode(screenWidth, screenHeight, 16, 60));
            } catch (IllegalArgumentException e) {
                return false; // if the screen resolution is not supported by hardware.
            }
        } else {
            return false; // if the full screen mode is not supported by hardware.
        }
        return true;
    }

    /**
     * Set the window mode.
     *
     * @param jFrame       the JFrame to window
     * @param windowWidth  the requested window width
     * @param windowHeight the requested window height
     */
    public static void setWindowMode(JFrame jFrame,
                                     int windowWidth,
                                     int windowHeight) {
        jFrame.dispose(); // reset the JFrame.
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setSize(windowWidth, windowHeight);
        jFrame.setLocationRelativeTo(null); // align to center.
        jFrame.setUndecorated(false);
        jFrame.setAlwaysOnTop(false);
        jFrame.setResizable(false);
    }
}

