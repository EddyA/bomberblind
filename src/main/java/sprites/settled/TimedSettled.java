package sprites.settled;

import java.awt.Image;

import utils.CurrentTimeSupplier;

/**
 * Abstract class of a timed sprites.
 * The sprite loops during a certain time.
 */
public abstract class TimedSettled extends Settled {
    protected CurrentTimeSupplier currentTimeSupplier = new CurrentTimeSupplier();

    protected final Image[] images; // array of images of the sprite.
    protected final int nbImages; // number of images of the sprite.
    protected int curImageIdx; // current image index of the sprite.
    protected Image curImage; // current image of the sprite.
    protected final int duration; // duration (in ms).
    protected final long startTs; // start timestamp.
    protected final int refreshTime; // refresh time (in ms).
    protected long lastRefreshTs; // last refresh timestamp.

    public TimedSettled(int rowIdx,
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
        this.startTs = currentTimeSupplier.get().toEpochMilli(); // get the current time.
    }

    @Override
    public boolean isFinished() {
        return currentTimeSupplier.get().toEpochMilli() - startTs >= duration;
    }

    @Override
    public Image getCurImage() {
        return curImage;
    }

    @Override
    public Image updateImage() {
        long curTs = currentTimeSupplier.get().toEpochMilli(); // get the current time.
        if (curTs - lastRefreshTs > refreshTime) { // it is time to refresh.
            lastRefreshTs = curTs;
            if (++curImageIdx == nbImages) {
                curImageIdx = 0;
            }
            curImage = images[curImageIdx];
        }
        return curImage;
    }
}