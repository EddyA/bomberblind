package sprites.nomad;

import java.awt.*;

/**
 * Abstract class of a nomad.
 */
public abstract class Nomad {

    private int xMap; // abscissa on the map.
    private int yMap; // ordinate on the map.

    public Nomad(int xMap, int yMap) {
        this.xMap = xMap;
        this.yMap = yMap;
    }

    /**
     * @return the nomad abscissa on the map.
     */
    public int getXMap() {
        return xMap;
    }

    /**
     * Set the nomad abscissa on the map.
     */
    public void setXMap(int xMap) {
        this.xMap = xMap;
    }

    /**
     * @return the nomad ordinate on the map.
     */
    public int getYMap() {
        return yMap;
    }

    /**
     * Set the nomad ordinate on the map.
     */
    public void setYMap(int yMap) {
        this.yMap = yMap;
    }

    /**
     * @return true if the nomad is dead and the sprite is finished, false otherwise.
     */
    public abstract boolean isFinished();

    /**
     * @return the current image of the sprite.
     */
    public abstract Image getCurImage();

    /**
     * Update the sprite image.
     *
     * @return the updated image.
     */
    public abstract void updateImage();

    /**
     * Paint the nomad.
     *
     * @param g       the graphics context
     * @param xScreen the abscissa on the screen
     * @param yScreen the ordinate on the screen
     */
    public void paintBuffer(Graphics2D g, int xScreen, int yScreen) {
        if ((getCurImage() != null) && !isFinished()) {
            int xImage = xScreen - getCurImage().getWidth(null) / 2;
            int yImage = yScreen - getCurImage().getHeight(null);
            g.drawImage(getCurImage(), xImage, yImage, null);
        }
    }
}
