package bomb;

import java.awt.Graphics;
import java.awt.Image;

import images.ImagesLoader;

public class Flame {
    private int rowIdx;
    private int colIdx;

    private Image[] images; // array of images for animation.
    private int nbImages; // number of images of the animation.
    private int curImageIdx; // current image index of the animation.
    private int refreshTime; // refresh time of the animation (in ms).
    private long lastRefreshTs; // last refresh timestamp.
    private int nbAnimationFrame; // number of frames the animation must remain.
    private int curNbAnimationFrame; // current number of frames of the animation.

    public Flame(int rowIdx, int colIdx) {
        this.rowIdx = rowIdx;
        this.colIdx = colIdx;
        this.images = ImagesLoader.imagesMatrix[ImagesLoader.flameMatrixRowIdx];
        this.nbImages = ImagesLoader.NB_FLAME_FRAME;
        this.refreshTime = 75;
        this.nbAnimationFrame = 25;
        this.curNbAnimationFrame = 0;
    }

    public int getRowIdx() {
        return rowIdx;
    }

    public int getColIdx() {
        return colIdx;
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
            curNbAnimationFrame++;
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
        if (curNbAnimationFrame < nbAnimationFrame) {
            Image updatedImage = updateImage();
            int xMap = xScreen - updatedImage.getWidth(null) / 2;
            int yMap = yScreen - updatedImage.getHeight(null);
            g.drawImage(updatedImage, xMap, yMap, null);
        }
    }
}
