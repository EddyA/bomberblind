package utils;

import images.ImagesLoader;
import sprite.nomad.Bomber;
import sprite.settled.BonusType;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;

import static utils.Configuration.*;

public class TopBar {
    private final static Image bombImage = ImagesLoader.imagesMatrix[ImagesLoader.bonusBombMatrixRowIdx][0];
    private final static Image flameImage = ImagesLoader.imagesMatrix[ImagesLoader.bonusFlameMatrixRowIdx][0];
    private final static Image heartImage = ImagesLoader.imagesMatrix[ImagesLoader.bonusHeartMatrixRowIdx][0];
    private final static Image rollerImage = ImagesLoader.imagesMatrix[ImagesLoader.bonusRollerMatrixRowIdx][0];
    private final static int zoneHeight =
            Math.max(bombImage.getHeight(null),
                    Math.max(flameImage.getHeight(null),
                            Math.max(heartImage.getHeight(null),
                                    Math.max(rollerImage.getHeight(null),
                                            SkinnedLine.getSkinnedAsciiHeight())))) + 2 * ORNAMENT_PADDING_SIZE - 2;

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
                zoneHeight + ORNAMENT_ARC_SIZE,
                ORNAMENT_ARC_SIZE,
                ORNAMENT_ARC_SIZE);
        g.setPaint(ORNAMENT_COLOR);
        g.fill(ornament);

        // print heart.
        g.drawImage(heartImage, ORNAMENT_PADDING_SIZE, (zoneHeight - heartImage.getHeight(null)) / 2, null);

        // print the number of remaining lifes.
        SkinnedLine.paintBuffer(g,
                heartImage.getWidth(null) + 2 * ORNAMENT_PADDING_SIZE,
                (zoneHeight - SkinnedLine.getSkinnedAsciiHeight()) / 2, String.valueOf(nbLifes));
    }

    /**
     * Paint the elpased time (middle top).
     *
     * @param g           the graphics context
     * @param screenWidth the screen width
     * @param elapsedTime the elasped time
     */
    private static void paintElapsedTime(Graphics2D g, int screenWidth, long elapsedTime) {

        // convert to text.
        int elapsedMinutes = (int) (elapsedTime / 1000 / 60 % 60); // minutes.
        String time = String.valueOf(elapsedMinutes / 10) + String.valueOf(elapsedMinutes % 10) + ":";
        int elapsedSeconds = (int) (elapsedTime / 1000 % 60); // seconds.
        time += String.valueOf(elapsedSeconds / 10) + String.valueOf(elapsedSeconds % 10) + ":";
        int elapsedCentis = (int) (elapsedTime / 10 % 100); // decis.
        time += String.valueOf(elapsedCentis / 10) + String.valueOf(elapsedCentis % 10);

        // print ornament.
        int zoneWidth = SkinnedLine.computeLineWidth(time) + 2 * ORNAMENT_PADDING_SIZE;
        int ZoneX = (screenWidth - zoneWidth) / 2;
        RoundRectangle2D ornament = new RoundRectangle2D.Float(
                ZoneX,
                -ORNAMENT_ARC_SIZE,
                zoneWidth,
                zoneHeight + ORNAMENT_ARC_SIZE,
                ORNAMENT_ARC_SIZE,
                ORNAMENT_ARC_SIZE);
        g.setPaint(ORNAMENT_COLOR);
        g.fill(ornament);

        // print elapsed time.
        SkinnedLine.paintBuffer(g, ZoneX + ORNAMENT_PADDING_SIZE, ORNAMENT_PADDING_SIZE, time);
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
                zoneHeight + ORNAMENT_ARC_SIZE,
                ORNAMENT_ARC_SIZE,
                ORNAMENT_ARC_SIZE);
        g.setPaint(ORNAMENT_COLOR);
        g.fill(ornament);

        // bonus bomb.
        int imgX = xZone + ORNAMENT_PADDING_SIZE;
        g.drawImage(bombImage, imgX, (zoneHeight - bombImage.getHeight(null)) / 2, null);
        imgX += bombImage.getWidth(null) + ORNAMENT_PADDING_SIZE;
        SkinnedLine.paintBuffer(g, imgX, (zoneHeight - SkinnedLine.getSkinnedAsciiHeight()) / 2, String.valueOf(nbBomb));
        imgX += SkinnedLine.computeLineWidth(String.valueOf(nbBomb)) + ORNAMENT_PADDING_SIZE;

        // bonus flame.
        g.drawImage(flameImage, imgX, (zoneHeight - flameImage.getHeight(null)) / 2, null);
        imgX += flameImage.getWidth(null) + ORNAMENT_PADDING_SIZE;
        SkinnedLine.paintBuffer(g, imgX, (zoneHeight - SkinnedLine.getSkinnedAsciiHeight()) / 2, String.valueOf(nbFlame));
        imgX += SkinnedLine.computeLineWidth(String.valueOf(nbFlame)) + ORNAMENT_PADDING_SIZE;

        // bonus roller.
        g.drawImage(rollerImage, imgX, (zoneHeight - rollerImage.getHeight(null)) / 2, null);
        imgX += rollerImage.getWidth(null) + ORNAMENT_PADDING_SIZE;
        SkinnedLine.paintBuffer(g, imgX, (zoneHeight - SkinnedLine.getSkinnedAsciiHeight()) / 2, String.valueOf(nbRoller));
    }

    /**
     * Paint the upper bar.
     *
     * @param g           the graphics context
     * @param screenWidth the screen width
     * @param bomber      the main bomber
     * @param elapsedTime the elasped time
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