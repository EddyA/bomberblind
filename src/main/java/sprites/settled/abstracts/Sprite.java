package sprites.settled.abstracts;

import java.awt.*;

import static images.ImagesLoader.IMAGE_SIZE;

/**
 * Abstract class of a sprite.
 * The sprite loops until isFinished() return true.
 */
public abstract class Sprite {

    private final int rowIdx; // map row index of the sprite.
    private final int colIdx; // map column index of the sprite.

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
     * Update the sprite image.
     *
     * @return the updated image.
     */
    public abstract Image updateImage();

    /**
     * Paint current image of the sprite.
     *
     * @param g       the graphics context
     * @param xScreen the abscissa on the screen
     * @param yScreen the ordinate on the screen
     */
    public synchronized void paintBuffer(Graphics g, int xScreen, int yScreen) {
        Image updatedImage = updateImage();
        if (!isFinished()) {
            int xMap = xScreen + (IMAGE_SIZE / 2) - updatedImage.getWidth(null) / 2;
            int yMap = yScreen + IMAGE_SIZE - updatedImage.getHeight(null);
            g.drawImage(updatedImage, xMap, yMap, null);
        }
    }
}