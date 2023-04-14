package utils;

import images.ImagesLoader;
import lombok.experimental.UtilityClass;
import sprite.nomad.Bomber;
import sprite.settled.BonusType;
import utils.text.SkinnedLine;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;

import static utils.Configuration.*;

/**
 * This class allows painting the top bar.
 */
@UtilityClass
public class TopBar {

    private static final Image bombImage = ImagesLoader.imagesMatrix[ImagesLoader.bonusBombMatrixRowIdx][0];
    private static final Image flameImage = ImagesLoader.imagesMatrix[ImagesLoader.bonusFlameMatrixRowIdx][0];
    private static final Image heartImage = ImagesLoader.imagesMatrix[ImagesLoader.bonusHeartMatrixRowIdx][0];
    private static final Image rollerImage = ImagesLoader.imagesMatrix[ImagesLoader.bonusRollerMatrixRowIdx][0];
    private static final int ZONE_HEIGHT =
            Math.max(bombImage.getHeight(null),
                    Math.max(flameImage.getHeight(null),
                            Math.max(heartImage.getHeight(null),
                                    Math.max(rollerImage.getHeight(null),
                                            SkinnedLine.SKINNED_ASCII_HEIGHT)))) + 2 * ORNAMENT_PADDING_SIZE - 2;

    /**
     * Paint the number of remaining lifes (top left corner).
     *
     * @param g       the graphics context
     * @param nbLifes the number of remaining lifes
     */
    private static void paintNbRemainingLifes(Graphics2D g, int nbLifes) {

        // print ornament.
        int zoneWidth = SkinnedLine.computeLineWidth(String.valueOf(nbLifes)) +
                heartImage.getWidth(null) +
                3 * ORNAMENT_PADDING_SIZE;
        RoundRectangle2D ornament = new RoundRectangle2D.Float(
                -ORNAMENT_ARC_SIZE,
                -ORNAMENT_ARC_SIZE,
                zoneWidth + ORNAMENT_ARC_SIZE,
                ZONE_HEIGHT + ORNAMENT_ARC_SIZE,
                ORNAMENT_ARC_SIZE,
                ORNAMENT_ARC_SIZE);
        g.setPaint(ORNAMENT_COLOR);
        g.fill(ornament);

        // print heart.
        g.drawImage(heartImage, ORNAMENT_PADDING_SIZE, (ZONE_HEIGHT - heartImage.getHeight(null)) / 2, null);

        // print the number of remaining lifes.
        SkinnedLine.paintBuffer(g,
                heartImage.getWidth(null) + 2 * ORNAMENT_PADDING_SIZE,
                (ZONE_HEIGHT - SkinnedLine.SKINNED_ASCII_HEIGHT) / 2, String.valueOf(nbLifes));
    }

    /**
     * Paint the elpased time (middle top).
     *
     * @param g           the graphics context
     * @param screenWidth the screen width
     * @param elapsedTime the elapsed time
     */
    private static void paintElapsedTime(Graphics2D g, int screenWidth, long elapsedTime) {

        // convert to text.
        int elapsedMinutes = (int) (elapsedTime / 1000 / 60 % 60); // minutes.
        String time = (elapsedMinutes / 10) + String.valueOf(elapsedMinutes % 10) + ":";
        int elapsedSeconds = (int) (elapsedTime / 1000 % 60); // seconds.
        time += (elapsedSeconds / 10) + String.valueOf(elapsedSeconds % 10) + ":";
        int elapsedHundredth = (int) (elapsedTime / 10 % 100); // hundredth.
        time += (elapsedHundredth / 10) + String.valueOf(elapsedHundredth % 10);

        // print ornament.
        int zoneWidth = SkinnedLine.computeLineWidth(time) + 2 * ORNAMENT_PADDING_SIZE;
        int zoneX = (screenWidth - zoneWidth) / 2;
        RoundRectangle2D ornament = new RoundRectangle2D.Float(
                zoneX,
                -ORNAMENT_ARC_SIZE,
                zoneWidth,
                ZONE_HEIGHT + ORNAMENT_ARC_SIZE,
                ORNAMENT_ARC_SIZE,
                ORNAMENT_ARC_SIZE);
        g.setPaint(ORNAMENT_COLOR);
        g.fill(ornament);

        // print elapsed time.
        SkinnedLine.paintBuffer(g, zoneX + ORNAMENT_PADDING_SIZE, ORNAMENT_PADDING_SIZE, time);
    }

    /**
     * Paint bonus (top right corner).
     *
     * @param g      the graphics context
     * @param nbBomb the number of bombs
     */
    private static void paintBonus(Graphics2D g,
                                   int screenWidth,
                                   int nbBomb,
                                   int nbFlame,
                                   int nbRoller) {

        // print ornament.
        int zoneWidth = bombImage.getHeight(null) +
                flameImage.getHeight(null) +
                rollerImage.getHeight(null) +
                SkinnedLine.computeLineWidth(String.valueOf(nbBomb)) +
                SkinnedLine.computeLineWidth(String.valueOf(nbFlame)) +
                SkinnedLine.computeLineWidth(String.valueOf(nbRoller)) +
                7 * ORNAMENT_PADDING_SIZE;
        int xZone = screenWidth - zoneWidth;
        RoundRectangle2D ornament = new RoundRectangle2D.Float(
                xZone,
                -ORNAMENT_ARC_SIZE,
                zoneWidth + ORNAMENT_ARC_SIZE,
                ZONE_HEIGHT + ORNAMENT_ARC_SIZE,
                ORNAMENT_ARC_SIZE,
                ORNAMENT_ARC_SIZE);
        g.setPaint(ORNAMENT_COLOR);
        g.fill(ornament);

        // bonus bomb.
        int imgX = xZone + ORNAMENT_PADDING_SIZE;
        g.drawImage(bombImage, imgX, (ZONE_HEIGHT - bombImage.getHeight(null)) / 2, null);
        imgX += bombImage.getWidth(null) + ORNAMENT_PADDING_SIZE;
        SkinnedLine.paintBuffer(g, imgX, (ZONE_HEIGHT - SkinnedLine.SKINNED_ASCII_HEIGHT) / 2, String.valueOf(nbBomb));
        imgX += SkinnedLine.computeLineWidth(String.valueOf(nbBomb)) + ORNAMENT_PADDING_SIZE;

        // bonus flame.
        g.drawImage(flameImage, imgX, (ZONE_HEIGHT - flameImage.getHeight(null)) / 2, null);
        imgX += flameImage.getWidth(null) + ORNAMENT_PADDING_SIZE;
        SkinnedLine.paintBuffer(g, imgX, (ZONE_HEIGHT - SkinnedLine.SKINNED_ASCII_HEIGHT) / 2, String.valueOf(nbFlame));
        imgX += SkinnedLine.computeLineWidth(String.valueOf(nbFlame)) + ORNAMENT_PADDING_SIZE;

        // bonus roller.
        g.drawImage(rollerImage, imgX, (ZONE_HEIGHT - rollerImage.getHeight(null)) / 2, null);
        imgX += rollerImage.getWidth(null) + ORNAMENT_PADDING_SIZE;
        SkinnedLine.paintBuffer(g, imgX, (ZONE_HEIGHT - SkinnedLine.SKINNED_ASCII_HEIGHT) / 2, String.valueOf(nbRoller));
    }

    /**
     * Paint the upper bar.
     *
     * @param g           the graphics context
     * @param screenWidth the screen width
     * @param bomber      the main bomber
     * @param elapsedTime the elapsed time
     */
    public static void paintBuffer(Graphics2D g, int screenWidth, Bomber bomber, long elapsedTime) {
        paintElapsedTime(g, screenWidth, elapsedTime);
        paintNbRemainingLifes(g, bomber.getBonus(BonusType.TYPE_BONUS_HEART));
        paintBonus(g,
                screenWidth,
                bomber.getBonus(BonusType.TYPE_BONUS_BOMB),
                bomber.getBonus(BonusType.TYPE_BONUS_FLAME),
                bomber.getBonus(BonusType.TYPE_BONUS_ROLLER));
    }
}
