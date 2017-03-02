package utils;

import java.awt.*;

import static utils.SkinnedLine.computeLineWidth;

public class SkinnedText {

    public final static String TEXT_GAME_OVER = "GAME OVER\nThank you for playing!\n\n(Press ESC to exit)";
    public final static String TEXT_WIN = "YOU WIN!\nThank you for playing!\n\n(Press ESC to exit)";

    private static int lineSpacing = 25;

    /**
     * Compute the abscissa from which painting a skinned line to horizontally center it.
     *
     * @param line        the line
     * @param screenWidth the screen width
     * @return the relative abscissa
     */
    public static int computeAbscissaToCenterLine(String line, int screenWidth) {
        return (screenWidth - computeLineWidth(line)) / 2;
    }

    /**
     * Compute the ordinate from which painting a skinned text to vertically center it.
     *
     * @param text         the text
     * @param screenHeight the screen height
     * @return the relative ordinate
     */
    public static int computeOrdinateToCenterText(String text, int screenHeight) {
        int nbLines = text.length() - text.replace("\n", "").length() + 1;
        return (screenHeight - nbLines * lineSpacing) / 2 - 30;
    }

    /**
     * Paint a centered text using skinned ascii.
     *
     * @param g            the graphics context
     * @param screenWidth  the screen width
     * @param screenHeight the screen height
     * @param text         the text to paint
     */
    public static void paintBuffer(Graphics2D g, int screenWidth, int screenHeight, String text) {
        String curLine = "";
        int curY = computeOrdinateToCenterText(text, screenHeight);
        for (int charIdx = 0; charIdx < text.length(); charIdx++) {
            int asciiCode = (int) text.charAt(charIdx);
            if (asciiCode == 10) { // new line.
                SkinnedLine.paintBuffer(g, computeAbscissaToCenterLine(curLine, screenWidth), curY, curLine);
                curY += lineSpacing;
                curLine = "";
            } else {
                curLine += text.charAt(charIdx);
            }
        }
        SkinnedLine.paintBuffer(g, computeAbscissaToCenterLine(curLine, screenWidth), curY, curLine); // last line.
    }
}