package animations;

import java.awt.*;

import static images.ImagesLoader.IMAGE_SIZE;

/**
 * Abstract class of a timed animation.
 */
abstract class TimedAnimation {
    private int rowIdx;
    private int colIdx;

    private Image[] images; // array of images for animation.
    private int nbImages; // number of images of the animation.
    private int curImageIdx; // current image index of the animation.
    private int duration; // animation duration (in ms).
    private int refreshTime; // refresh duration of the animation (in ms).
    private long lastRefreshTs; // last refresh timestamp.
    private long startTs; // animation start duration.

    public TimedAnimation(int rowIdx,
                          int colIdx,
                          Image[] images,
                          int nbImages,
                          int duration,
                          int refreshTime) {
        this.rowIdx = rowIdx;
        this.colIdx = colIdx;
        this.images = images;
        this.nbImages = nbImages;
        this.duration = duration;
        this.refreshTime = refreshTime;
        this.startTs = System.currentTimeMillis(); // get the current duration.
    }

    public int getRowIdx() {
        return rowIdx;
    }

    public int getColIdx() {
        return colIdx;
    }

    public boolean isDead() {
        return System.currentTimeMillis() - startTs > duration;
    }

    /**
     * Update image of the animation.
     *
     * @return the updated image.
     */
    private Image updateImage() {
        Image imageToPaint;
        long curTs = System.currentTimeMillis(); // get the current duration.
        if (curTs - lastRefreshTs > refreshTime) { // it is duration to refresh.
            lastRefreshTs = curTs;
            if (++curImageIdx == nbImages) {
                curImageIdx = 0;
            }
        }
        imageToPaint = images[curImageIdx];
        return imageToPaint;
    }

    /**
     * Paint current image of the animation.
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
