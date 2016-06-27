package sprites;

import java.awt.*;

/**
 * Abstract class defining framed sprites (finished when all the frames have been displayed).
 */
abstract class FramedSprite extends Sprite {

    private Image[] images; // array of images of the animation.
    private int nbImages; // number of images of the animation.
    private int curImageIdx; // current image index of the animation.
    private int refreshTime; // refresh duration of the animation (in ms).
    private long lastRefreshTs; // last refresh timestamp.

    public FramedSprite(int rowIdx,
                        int colIdx,
                        Image[] images,
                        int nbImages,
                        int refreshTime) {
        super(rowIdx, colIdx);
        this.images = images;
        this.nbImages = nbImages;
        this.refreshTime = refreshTime;
    }

    public boolean isFinished() {
        return curImageIdx == nbImages - 1;
    }

    /**
     * Update image of the animation.
     *
     * @return the updated image.
     */
    public Image updateImage() {
        Image imageToPaint;
        long curTs = System.currentTimeMillis(); // get the current duration.
        if (curTs - lastRefreshTs > refreshTime) { // it is duration to refresh.
            lastRefreshTs = curTs;
            if (curImageIdx < nbImages - 1) {
                curImageIdx++;
            }
        }
        imageToPaint = images[curImageIdx];
        return imageToPaint;
    }
}
