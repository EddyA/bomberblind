package utils.text;

import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;
import lombok.experimental.UtilityClass;
import static utils.Configuration.ORNAMENT_ARC_SIZE;
import static utils.Configuration.ORNAMENT_COLOR;
import static utils.Configuration.ORNAMENT_PADDING_SIZE;
import static utils.text.SkinnedLine.computeLineAbscissaToCenterItOnScreen;
import static utils.text.SkinnedText.computeTextHeight;
import static utils.text.SkinnedText.computeTextOrdinateToCenterItOnScreen;
import static utils.text.SkinnedText.computeTextWidth;

/**
 * This class allows painting a text using skinned ascii, adding a background.
 */
@UtilityClass
public class SkinnedTextWithBG {

    public static final String TEXT_NEW = "WELCOME!\n\n" +
        "Reach the exit an press (Q) to complete the stage\n\n" +
        "Press (B) to put bombs\n" +
        "Press (ENTER) to start the stage\n" +
        "Press (ESC) to quit the game\n\n" +
        "GOOD LUCK!";

    public static final String TEXT_WIN = "STAGE COMPLETE\n\n" +
        "Thank you for playing!\n\n" +
        "(Press ESC to exit)";

    public static final String TEXT_GAME_OVER = "GAME OVER\n\n" +
        "Thank you for playing!\n\n" +
        "(Press ESC to exit)";

    /**
     * Paint a centered text using skinned ascii, with an ornament.
     *
     * @param g            the graphics context
     * @param screenWidth  the screen width
     * @param screenHeight the screen hieght
     */
    public static void paintBuffer(Graphics2D g, int screenWidth, int screenHeight, String text) {
        int skinnedTextWidth = computeTextWidth(text);
        int skinnedTextHeight = computeTextHeight(text);
        int xSkinnedText = computeLineAbscissaToCenterItOnScreen(skinnedTextWidth, screenWidth);
        int ySkinnedText = computeTextOrdinateToCenterItOnScreen(skinnedTextHeight, screenHeight);

        // print ornament.
        RoundRectangle2D ornament = new RoundRectangle2D.Float(
                xSkinnedText - ORNAMENT_PADDING_SIZE,
                ySkinnedText - ORNAMENT_PADDING_SIZE,
                skinnedTextWidth + 2 * ORNAMENT_PADDING_SIZE,
                skinnedTextHeight + 2 * ORNAMENT_PADDING_SIZE,
                ORNAMENT_ARC_SIZE,
                ORNAMENT_ARC_SIZE);
        g.setPaint(ORNAMENT_COLOR);
        g.fill(ornament);

        // print text.
        SkinnedText.paintBuffer(g, screenWidth, screenHeight, text);
    }
}
