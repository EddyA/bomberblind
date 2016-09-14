package sprites.settled.abstracts;

import java.awt.*;

/**
 * Abstract class of a looped settled sprite.
 * The sprite loops a certain number of times.
 */
public abstract class LoopedSettled extends Settled {

    private final Image[] images; // array of images of the sprite.
    private final int nbImages; // number of images of the sprite.
    private int curImageIdx; // current image index of the sprite.
    private Image curImage; // current image of the sprite.
    private final int maxNbTimes; // number of times the sprite should be painted.
    private int curNbTimes; // current number of times.

    public LoopedSettled(int rowIdx,
                         int colIdx,
                         Image[] images,
                         int nbImages,
                         int refreshTime,
                         int maxNbTimes) {
        super(rowIdx, colIdx, refreshTime);
        this.images = images;
        this.nbImages = nbImages;
        this.maxNbTimes = maxNbTimes;
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

    public int getMaxNbTimes() {
        return maxNbTimes;
    }

    public int getCurNbTimes() {
        return curNbTimes;
    }

    public void setCurImageIdx(int curImageIdx) {
        this.curImageIdx = curImageIdx;
    }

    public void setCurImage(Image curImage) {
        this.curImage = curImage;
    }

    public void setCurNbTimes(int curNbTimes) {
        this.curNbTimes = curNbTimes;
    }

    @Override
    public boolean isFinished() {
        return curNbTimes == maxNbTimes;
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
                curNbTimes++;
            }
            curImage = images[curImageIdx];
        }
    }
}