package timer;

import images.ImagesLoader;
import utils.CurrentTimeSupplier;

import java.awt.*;

public class GraphTimer {
    protected CurrentTimeSupplier currentTimeSupplier = new CurrentTimeSupplier();

    private final Image[] digits;
    private long startTs;

    public GraphTimer() {
        digits = ImagesLoader.imagesMatrix[ImagesLoader.digitsMatrixRowIdx];
    }

    public void start() {
        startTs = currentTimeSupplier.get().toEpochMilli();
    }

    /**
     * Paint the sprite.
     *
     * @param g       the graphics context
     * @param xScreen the map's abscissa from which painting
     * @param yScreen the map's ordinate from which painting
     */
    public void paintBuffer(Graphics2D g, int xScreen, int yScreen) {
        long elapsedTime = currentTimeSupplier.get().toEpochMilli() - startTs;

        // print minutes.
        int elapsedMinutes = (int) (elapsedTime / 1000 / 60 % 60);
        g.drawImage(digits[elapsedMinutes / 10], xScreen, yScreen, null);
        g.drawImage(digits[elapsedMinutes % 10], xScreen + 20, yScreen, null);

        // print seconds.
        int elapsedSeconds = (int) (elapsedTime / 1000 % 60);
        g.drawImage(digits[elapsedSeconds / 10], xScreen + 60, yScreen, null);
        g.drawImage(digits[elapsedSeconds % 10], xScreen + 80, yScreen, null);

        // print decis.
        int elapsedCentis = (int) (elapsedTime / 10 % 100);
        g.drawImage(digits[elapsedCentis / 10], xScreen + 120, yScreen, null);
        g.drawImage(digits[elapsedCentis % 10], xScreen + 140, yScreen, null);
    }
}
