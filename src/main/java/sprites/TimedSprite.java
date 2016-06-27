package sprites;

import java.awt.*;

/**
 * Abstract class defining timed sprites (loop until the timer does reach a certain duration).
 */
abstract class TimedSprite extends Sprite {

    private Image[] images; // array of images of the animation.
    private int nbImages; // number of images of the animation.
    private int curImageIdx; // current image index of the animation.
    private int duration; // animation duration (in ms).
    private int refreshTime; // refresh duration (in ms).
    private long lastRefreshTs; // last refresh timestamp.
    private long startTs; // animation start timestamp.

    public TimedSprite(int rowIdx,
                       int colIdx,
                       Image[] images,
                       int nbImages,
                       int duration,
                       int refreshTime) {
        super(rowIdx, colIdx);
        this.images = images;
        this.nbImages = nbImages;
        this.duration = duration;
        this.refreshTime = refreshTime;
        this.startTs = System.currentTimeMillis(); // get the current duration.
    }

    public boolean isFinished() {
        return System.currentTimeMillis() - startTs > duration;
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
            if (++curImageIdx == nbImages) {
                curImageIdx = 0;
            }
        }
        imageToPaint = images[curImageIdx];
        return imageToPaint;
    }
}
