package sprites.settled.abstracts;

import java.awt.Image;

/**
 * Abstract class of a timed sprites.
 * The sprite loops during a certain time.
 */
public abstract class TimedSettled extends Settled {

    protected final Image[] images; // array of images of the sprite.
    protected final int nbImages; // number of images of the sprite.
    protected int curImageIdx; // current image index of the sprite.
    protected Image curImage; // current image of the sprite.

    protected final int duration; // duration (in ms).
    protected final long startTs; // start timestamp.

    public TimedSettled(int rowIdx,
                        int colIdx,
                        Image[] images,
                        int nbImages,
                        int refreshTime,
                        int duration) {
        super(rowIdx, colIdx, refreshTime);
        this.images = images;
        this.nbImages = nbImages;
        this.duration = duration;
        this.startTs = currentTimeSupplier.get().toEpochMilli(); // get the current time.
    }

    public Image[] getImages() {
        return images;
    }

    public int getNbImages() {
        return nbImages;
    }

    public int getCurImageIdx() {
        return curImageIdx;
    }

    public int getDuration() {
        return duration;
    }

    public long getStartTs() {
        return startTs;
    }

    public void setCurImageIdx(int curImageIdx) {
        this.curImageIdx = curImageIdx;
    }

    public void setCurImage(Image curImage) {
        this.curImage = curImage;
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
    public void updateImage() {
        long curTs = currentTimeSupplier.get().toEpochMilli(); // get the current time.
        if (curTs - lastRefreshTs > refreshTime) { // it is time to refresh.
            lastRefreshTs = curTs;
            if (++curImageIdx == nbImages) {
                curImageIdx = 0;
            }
            curImage = images[curImageIdx];
        }
    }
}