package sprites.settled;

import java.awt.*;

import static images.ImagesLoader.IMAGE_SIZE;

/**
 * Abstract class of an animated item.
 * The animation loops until isFinished() return true.
 */
public abstract class Settled {

    private final int rowIdx; // map row index of the item.
    private final int colIdx; // map column index of the item.

    public Settled(int rowIdx,
                   int colIdx) {
        this.rowIdx = rowIdx;
        this.colIdx = colIdx;
    }

    /**
     * @return the map row index of the item.
     */
    public synchronized int getRowIdx() {
        return rowIdx;
    }

    /**
     * @return the map column index of the item.
     */
    public synchronized int getColIdx() {
        return colIdx;
    }

    /**
     * @return true if the animation is finished, false otherwise.
     */
    public abstract boolean isFinished();

    /**
     * @return the current image of the sprite.
     */
    public abstract Image getCurImage();

    /**
     * Update the animation image.
     *
     * @return the updated image.
     */
    public abstract void updateImage();

    /**
     * Paint the current animation image.
     *
     * @param g       the graphics context
     * @param xScreen the abscissa on the screen
     * @param yScreen the ordinate on the screen
     */
    public synchronized void paintBuffer(Graphics g, int xScreen, int yScreen) {
        if ((getCurImage() != null) && !isFinished()) {
            int xMap = xScreen + (IMAGE_SIZE / 2) - getCurImage().getWidth(null) / 2;
            int yMap = yScreen + IMAGE_SIZE - getCurImage().getHeight(null);
            g.drawImage(getCurImage(), xMap, yMap, null);
        }
    }
}