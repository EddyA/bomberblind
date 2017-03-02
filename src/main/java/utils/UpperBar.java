package utils;

import images.ImagesLoader;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;

import static utils.Configuration.*;

public class UpperBar {
    private final static Image heartImage = ImagesLoader.imagesMatrix[ImagesLoader.bonusHeartMatrixRowIdx][0];

    /**
     * Paint the number of remaining lifes on the left corner.
     *
     * @param g               the graphics context
     * @param nbRemainingLife the number of remaining lifes
     */
    private static void paintRemainingLife(Graphics2D g, int nbRemainingLife) {

        // print ornament.
        RoundRectangle2D ornament = new RoundRectangle2D.Float(
                -ORNAMENT_ARC_SIZE,
                -ORNAMENT_ARC_SIZE,
                ORNAMENT_ARC_SIZE + SkinnedLine.computeLineWidth(String.valueOf(nbRemainingLife)) + heartImage.getWidth(null) + 3 * ORNAMENT_PADDING_SIZE,
                ORNAMENT_ARC_SIZE + SkinnedLine.getSkinnedAsciiHeight() + 2 * ORNAMENT_PADDING_SIZE,
                ORNAMENT_ARC_SIZE,
                ORNAMENT_ARC_SIZE);
        g.setPaint(ORNAMENT_COLOR);
        g.fill(ornament);

        // print heart.
        g.drawImage(heartImage, ORNAMENT_PADDING_SIZE, ORNAMENT_PADDING_SIZE - 2, null);

        // print the number of remaining lifes.
        SkinnedLine.paintBuffer(g,
                ORNAMENT_PADDING_SIZE + heartImage.getWidth(null) + ORNAMENT_PADDING_SIZE,
                ORNAMENT_PADDING_SIZE, String.valueOf(nbRemainingLife));
    }

    /**
     * Paint the elpased time on the right corner.
     *
     * @param g           the graphics context
     * @param screenWidth the screen width
     * @param elapsedTime the elasped time
     */
    private static void paintTimer(Graphics2D g, int screenWidth, long elapsedTime) {

        // convert to text.
        int elapsedMinutes = (int) (elapsedTime / 1000 / 60 % 60); // minutes.
        String time = String.valueOf(elapsedMinutes / 10) + String.valueOf(elapsedMinutes % 10) + ":";
        int elapsedSeconds = (int) (elapsedTime / 1000 % 60); // seconds.
        time += String.valueOf(elapsedSeconds / 10) + String.valueOf(elapsedSeconds % 10) + ":";
        int elapsedCentis = (int) (elapsedTime / 10 % 100); // decis.
        time += String.valueOf(elapsedCentis / 10) + String.valueOf(elapsedCentis % 10);

        // print ornament.
        RoundRectangle2D ornament = new RoundRectangle2D.Float(
                -ORNAMENT_ARC_SIZE + screenWidth - SkinnedLine.computeLineWidth(time) - 2 * ORNAMENT_PADDING_SIZE,
                -ORNAMENT_ARC_SIZE,
                ORNAMENT_ARC_SIZE + SkinnedLine.computeLineWidth(time) + 2 * ORNAMENT_PADDING_SIZE,
                ORNAMENT_ARC_SIZE + SkinnedLine.getSkinnedAsciiHeight() + 2 * ORNAMENT_PADDING_SIZE,
                ORNAMENT_ARC_SIZE,
                ORNAMENT_ARC_SIZE);
        g.setPaint(ORNAMENT_COLOR);
        g.fill(ornament);

        // print elapsed time.
        SkinnedLine.paintBuffer(g, screenWidth - SkinnedLine.computeLineWidth(time) - ORNAMENT_PADDING_SIZE, ORNAMENT_PADDING_SIZE, time);
    }

    /**
     * Paint the upper bar.
     *
     * @param g               the graphics context
     * @param screenWidth     the screen width
     * @param nbRemainingLife the number of remaining lifes
     * @param elapsedTime     the elasped time
     */
    public static void paintBuffer(Graphics2D g, int screenWidth, int nbRemainingLife, long elapsedTime) {
        paintTimer(g, screenWidth, elapsedTime);
        paintRemainingLife(g, nbRemainingLife);
    }
}