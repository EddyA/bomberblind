package utils.text;

import images.ImagesLoader;

import java.awt.*;

/**
 * This class allows painting a line using skinned ascii.
 */
public class SkinnedLine {

    private final static Image[] skinnedAscii = ImagesLoader.imagesMatrix[ImagesLoader.asciiMatrixRowIdx];
    private final static int skinnedAsciiHeight = 24; // height of a skinned ascii (in px).

    /**
     * @return the height of the skinned ascii.
     */
    public static int getSkinnedAsciiHeight() {
        return skinnedAsciiHeight;
    }

    /**
     * Compute the width of a skinned line (in px).
     *
     * @param line the line
     * @return the width of the line (in px)
     */
    public static int computeLineWidth(String line) {
        int lineWidth = 0;
        for (int charIdx = 0; charIdx < line.length(); charIdx++) {
            int asciiCode = (int) line.charAt(charIdx);
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
            int asciiCode = (int) line.charAt(charIdx);
            g.drawImage(skinnedAscii[asciiCode], curX, yScreen, null);
            curX += skinnedAscii[asciiCode].getWidth(null) + 1;
        }
    }
}
