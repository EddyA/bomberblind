package utils.text;

import java.awt.*;

import static utils.text.SkinnedLine.computeLineAbscissaToCenterItOnScreen;
import static utils.text.SkinnedLine.computeLineWidth;

/**
 * This class allows painting a text using skinned ascii.
 */
public class SkinnedText {

    private final static int lineSpacing = 25;

    /**
     * Compute the height of a skinned text (in px).
     *
     * @param text the text
     * @return the height of the text (in px)

     */
    public static int computeTextHeight(String text) {
        int nbLines = text.length() - text.replace("\n", "").length() + 1;
        return nbLines * lineSpacing;
    }

    /**
     * Compute the width of a skinned text (in px).
     * i.e. return the width of the largest skinned line of a text.
     *
     * @param text the text
     * @return the width of the text (in px)
     */
    public static int computeTextWidth(String text) {
        StringBuilder curLine = new StringBuilder();
        int textWidth = 0;
        for (int charIdx = 0; charIdx < text.length(); charIdx++) {
            int asciiCode = (int) text.charAt(charIdx);
            if (asciiCode == 10) { // new line.
                int curLineWidth = computeLineWidth(curLine.toString());
                if (curLineWidth > textWidth) {
                    textWidth = curLineWidth;
                }
                curLine.setLength(0);
            } else {
                curLine.append(text.charAt(charIdx));
            }
        }
        int curLineWidth = computeLineWidth(curLine.toString()); // last line.
        if (curLineWidth > textWidth) {
            textWidth = curLineWidth;
        }
        return textWidth;
    }

    /**
     * Compute the ordinate (in px) from which painting a skinned text to vertically center it on screen.
     *
     * @param textHeight         the text height
     * @param screenHeight the screen height
     * @return the ordinate from which painting the text to vertically center it on screen.
     */
    public static int computeTextOrdinateToCenterItOnScreen(int textHeight, int screenHeight) {
        return (screenHeight - textHeight) / 2 - 30;
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
        int curY = computeTextOrdinateToCenterItOnScreen(computeTextHeight(text), screenHeight);
        for (int charIdx = 0; charIdx < text.length(); charIdx++) {
            int asciiCode = (int) text.charAt(charIdx);
            if (asciiCode == 10) { // new line.
                int curX = computeLineAbscissaToCenterItOnScreen(computeLineWidth(curLine), screenWidth);
                SkinnedLine.paintBuffer(g, curX, curY, curLine);
                curY += lineSpacing;
                curLine = "";
            } else {
                curLine += text.charAt(charIdx);
            }
        }
        int curX = computeLineAbscissaToCenterItOnScreen(computeLineWidth(curLine), screenWidth);
        SkinnedLine.paintBuffer(g, curX, curY, curLine); // last line.
    }
}