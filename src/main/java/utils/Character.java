package utils;

import static images.ImagesLoader.IMAGE_SIZE;

/**
 * Some character utils.
 */
public class Character {

    /**
     * Return the character top ordinate given its map ordinate.
     */
    public static int getCharTopBound(int yChar) {
        return yChar - IMAGE_SIZE / 2;
    }

    /**
     * Return the character bottom ordinate given its map ordinate.
     */
    public static int getCharBottomBound(int yChar) {
        return yChar;
    }

    /**
     * Return the character left abscissa given its map abscissa.
     */
    public static int getCharLeftBound(int xChar) {
        return xChar - IMAGE_SIZE / 2;
    }

    /**
     * Return the character right abscissa given its map abscissa.
     */
    public static int getCharRightBound(int xChar) {
        return xChar + IMAGE_SIZE / 2 - 1;
    }
}
