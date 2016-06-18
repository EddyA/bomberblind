package bomb;

import images.ImagesLoader;

import java.awt.*;

import static images.ImagesLoader.IMAGE_SIZE;

public class Flame {
    private int rowIdx;
    private int colIdx;

    private Image[] images; // array of images for animation.
    private int nbImages; // number of images of the animation.
    private int curImageIdx; // current image index of the animation.
    private int refreshTime; // refresh time of the animation (in ms).
    private long lastRefreshTs; // last refresh timestamp.
    private long startTs; // flame start time.
    private int flameTime; // flame duration (in ms).

    public Flame(int rowIdx, int colIdx, int flameTime) {
        this.rowIdx = rowIdx;
        this.colIdx = colIdx;
        this.images = ImagesLoader.imagesMatrix[ImagesLoader.flameMatrixRowIdx];
        this.nbImages = ImagesLoader.NB_FLAME_FRAME;
        this.refreshTime = 100;
        this.startTs = System.currentTimeMillis(); // get the current time.
        this.flameTime = flameTime;
    }

    public int getRowIdx() {
        return rowIdx;
    }

    public int getColIdx() {
        return colIdx;
    }

    public boolean isDead() {
        return System.currentTimeMillis() - startTs > flameTime;
    }

    /**
     * Update the image.
     *
     * @return the image to paint.
     */
    private Image updateImage() {
        Image imageToPaint;
        long curTs = System.currentTimeMillis(); // get the current time.
        if (curTs - lastRefreshTs > refreshTime) { // it is time to refresh.
            lastRefreshTs = curTs;
            if (++curImageIdx == nbImages) {
                curImageIdx = 0;
            }
        }
        imageToPaint = images[curImageIdx];
        return imageToPaint;
    }

    /**
     * Paint the image.
     *
     * @param g       the graphics context
     * @param xScreen the abscissa on screen
     * @param yScreen the ordinate on screen
     */
    public void paintBuffer(Graphics g, int xScreen, int yScreen) {
        if (!isDead()) {
            Image updatedImage = updateImage();
            int xMap = xScreen + (IMAGE_SIZE / 2) - updatedImage.getWidth(null) / 2;
            int yMap = yScreen + IMAGE_SIZE - updatedImage.getHeight(null);
            g.drawImage(updatedImage, xMap, yMap, null);
        }
    }
}
