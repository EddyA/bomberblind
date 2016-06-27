package sprites;

import java.awt.*;

import static images.ImagesLoader.IMAGE_SIZE;

/**
 * Abstract class defining a sprite.
 */
public abstract class Sprite {

    private int rowIdx; // map row index of the sprite.
    private int colIdx; // map column index of the sprite.

    public Sprite(int rowIdx,
                  int colIdx) {
        this.rowIdx = rowIdx;
        this.colIdx = colIdx;
    }

    /**
     * @return the map row index of the sprite.
     */
    public synchronized int getRowIdx() {
        return rowIdx;
    }

    /**
     * @return the map column index of the sprite.
     */
    public synchronized int getColIdx() {
        return colIdx;
    }

    /**
     * @return true if the sprite is finished, false otherwise.
     */
    public abstract boolean isFinished();

    /**
     * Update image of the animation.
     *
     * @return the updated image.
     */
    abstract public Image updateImage();

    /**
     * Paint current image of the animation.
     *
     * @param g       the graphics context
     * @param xScreen the abscissa on screen
     * @param yScreen the ordinate on screen
     */
    public synchronized void paintBuffer(Graphics g, int xScreen, int yScreen) {
        if (!isFinished()) {
            Image updatedImage = updateImage();
            int xMap = xScreen + (IMAGE_SIZE / 2) - updatedImage.getWidth(null) / 2;
            int yMap = yScreen + IMAGE_SIZE - updatedImage.getHeight(null);
            g.drawImage(updatedImage, xMap, yMap, null);
        }
    }
}