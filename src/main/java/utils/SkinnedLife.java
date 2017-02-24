package utils;

import images.ImagesLoader;

import java.awt.*;

public class SkinnedLife {

    private final static Image heartImage;

    static {
        heartImage = ImagesLoader.imagesMatrix[ImagesLoader.heartMatrixRowIdx][0];
    }

    /**
     * Paint the number of remaining lifes.
     *
     * @param g               the graphics context
     * @param xScreen         the map's abscissa from which painting
     * @param yScreen         the map's ordinate from which painting
     * @param nbRemainingLife the number of remaining lifes
     */
    public static void paintBuffer(Graphics2D g, int xScreen, int yScreen, int nbRemainingLife) {
        g.drawImage(heartImage, xScreen, yScreen, null);
        SkinnedAscii.paintBuffer(g, xScreen + heartImage.getWidth(null) + 10, yScreen + 1, String.valueOf(nbRemainingLife));
    }
}
