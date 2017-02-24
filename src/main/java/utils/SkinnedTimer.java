package utils;

import java.awt.*;

public class SkinnedTimer {
    protected CurrentTimeSupplier currentTimeSupplier = new CurrentTimeSupplier();

    private long startTs;

    public void start() {
        startTs = currentTimeSupplier.get().toEpochMilli();
    }

    /**
     * Paint the elapsed time using skined ascii.
     *
     * @param g       the graphics context
     * @param xScreen the map's abscissa from which painting
     * @param yScreen the map's ordinate from which painting
     */
    public void paintBuffer(Graphics2D g, int xScreen, int yScreen) {
        long elapsedTime = currentTimeSupplier.get().toEpochMilli() - startTs;

        // format elapsed time.
        int elapsedMinutes = (int) (elapsedTime / 1000 / 60 % 60); // minutes.
        String time = String.valueOf(elapsedMinutes / 10) + String.valueOf(elapsedMinutes % 10) + ":";
        int elapsedSeconds = (int) (elapsedTime / 1000 % 60); // seconds.
        time += String.valueOf(elapsedSeconds / 10) + String.valueOf(elapsedSeconds % 10) + ":";
        int elapsedCentis = (int) (elapsedTime / 10 % 100); // decis.
        time += String.valueOf(elapsedCentis / 10) + String.valueOf(elapsedCentis % 10);

        // print elapsed time.
        SkinnedAscii.paintBuffer(g, xScreen, yScreen, time);
    }
}
