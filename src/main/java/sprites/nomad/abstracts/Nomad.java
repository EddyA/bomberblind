package sprites.nomad.abstracts;

import java.awt.*;

/**
 * Abstract class of a character.
 */
public abstract class Nomad {

    private int xMap; // abscissa on the map.
    private int yMap; // ordinate on the map.

    public Nomad(int xMap, int yMap) {
        this.xMap = xMap;
        this.yMap = yMap;
    }

    /**
     * @return the character abscissa on the map.
     */
    public int getXMap() {
        return xMap;
    }

    /**
     * Set the character abscissa on the map.
     */
    public void setXMap(int xMap) {
        this.xMap = xMap;
    }

    /**
     * @return the character ordinate on the map.
     */
    public int getYMap() {
        return yMap;
    }

    /**
     * Set the character ordinate on the map.
     */
    public void setYMap(int yMap) {
        this.yMap = yMap;
    }

    /**
     * @return true if the character is dead and the sprite is finished, false otherwise.
     */
    public abstract boolean isFinished();

    /**
     * Update the sprite image.
     *
     * @return the updated image.
     */
    public abstract Image updateImage();

    /**
     * Paint the character.
     *
     * @param g       the graphics context
     * @param xScreen the abscissa on the screen
     * @param yScreen the ordinate on the screen
     */
    public void paintBuffer(Graphics2D g, int xScreen, int yScreen) {
        Image updatedImage = updateImage();
        if ((updatedImage != null) && !isFinished()) {
            int xImage = xScreen - updatedImage.getWidth(null) / 2;
            int yImage = yScreen - updatedImage.getHeight(null);
            g.drawImage(updatedImage, xImage, yImage, null);
        }
    }
}
