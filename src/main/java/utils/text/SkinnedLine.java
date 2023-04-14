package utils.text;

import java.awt.Graphics2D;
import java.awt.Image;
import images.ImagesLoader;
import lombok.experimental.UtilityClass;

/**
 * This class allows painting a line using skinned ascii.
 */
@UtilityClass
public class SkinnedLine {

    private static final Image[] skinnedAscii = ImagesLoader.imagesMatrix[ImagesLoader.asciiMatrixRowIdx];
    public static final int SKINNED_ASCII_HEIGHT = 24; // height of a skinned ascii (in px).

    /**
     * Compute the width of a skinned line (in px).
     *
     * @param line the line
     * @return the width of the line (in px)
     */
    public static int computeLineWidth(String line) {
        int lineWidth = 0;
        for (int charIdx = 0; charIdx < line.length(); charIdx++) {
            int asciiCode = line.charAt(charIdx);
            lineWidth += (skinnedAscii[asciiCode].getWidth(null) + 1);
        }
        return lineWidth;
    }

    /**
     * Compute the abscissa (in px) from which painting a skinned line to horizontally center it on screen.
     *
     * @param lineWidth   the line width
     * @param screenWidth the screen width
     * @return the abscissa from which painting the line to horizontally center it on screen.
     */
    public static int computeLineAbscissaToCenterItOnScreen(int lineWidth, int screenWidth) {
        return (screenWidth - lineWidth) / 2;
    }

    /**
     * Paint a line using skinned ascii.
     *
     * @param g       the graphics context
     * @param xScreen the map's abscissa from which painting
     * @param yScreen the map's ordinate from which painting
     * @param line    the line to paint
     */
    public static void paintBuffer(Graphics2D g, int xScreen, int yScreen, String line) {
        int curX = xScreen;
        for (int charIdx = 0; charIdx < line.length(); charIdx++) {
            int asciiCode = line.charAt(charIdx);
            g.drawImage(skinnedAscii[asciiCode], curX, yScreen, null);
            curX += skinnedAscii[asciiCode].getWidth(null) + 1;
        }
    }
}
