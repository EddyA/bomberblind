package sprites.settled;

import utils.CurrentTimeSupplier;

import java.awt.*;

/**
 * Abstract class of a looped animation.
 * The animation loops a certain number of times.
 */
public abstract class LoopedSettled extends Settled {
    protected CurrentTimeSupplier currentTimeSupplier = new CurrentTimeSupplier();

    private final Image[] images; // array of images of the sprite.
    private final int nbImages; // number of images of the sprite.
    private int curImageIdx; // current image index of the sprite.
    private final int refreshTime; // refresh time (in ms).
    private long lastRefreshTs; // last refresh timestamp.
    private final int maxNbTimes; // number of times the sprite should be painted.
    private int curNbTimes; // current number of times.

    public LoopedSettled(int rowIdx,
                         int colIdx,
                         Image[] images,
                         int nbImages,
                         int refreshTime,
                         int maxNbTimes) {
        super(rowIdx, colIdx);
        this.images = images;
        this.nbImages = nbImages;
        this.refreshTime = refreshTime;
        this.maxNbTimes = maxNbTimes;
    }

    /**
     * @return true if the sprite is finished, false otherwise.
     */
    public boolean isFinished() {
        return curNbTimes == maxNbTimes;
    }

    /**
     * Update the sprite image.
     *
     * @return the updated image.
     */
    public Image updateImage() {
        Image imageToPaint;
        long curTs = currentTimeSupplier.get().toEpochMilli(); // get the current time.
        if (curTs - lastRefreshTs > refreshTime) { // it is time to refresh.
            lastRefreshTs = curTs;
            if (++curImageIdx == nbImages) {
                curImageIdx = 0;
                curNbTimes++;
            }
        }
        imageToPaint = images[curImageIdx];
        return imageToPaint;
    }
}