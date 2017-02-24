package utils;

import images.ImagesLoader;

import java.awt.*;

public class SkinnedAscii {

    private final static Image[] skinnedAscii;
    private final static int lineSpacing;

    static {
        skinnedAscii = ImagesLoader.imagesMatrix[ImagesLoader.asciiMatrixRowIdx];
        lineSpacing = 25;
    }

    /**
     * Paint a string using skined ascii.
     *
     * @param g       the graphics context
     * @param xScreen the map's abscissa from which painting
     * @param yScreen the map's ordinate from which painting
     * @param s       the string to paint
     */
    public static void paintBuffer(Graphics2D g, int xScreen, int yScreen, String s) {
        int curX = xScreen;
        int curY = yScreen;
        for (int charIdx = 0; charIdx < s.length(); charIdx++) {
            int asciiCode = (int) s.charAt(charIdx);
            if (asciiCode == 10) { // new line.
                curX = xScreen;
                curY += lineSpacing;
            } else {
                g.drawImage(skinnedAscii[asciiCode], curX, curY, null);
                curX += skinnedAscii[asciiCode].getWidth(null) + 1;
            }
        }
    }
}
