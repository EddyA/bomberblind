package sprite.nomad.abstracts;

import java.awt.Image;

import sprite.abstracts.Sprite;

/**
 * Abstract class of a nomad.
 */
public abstract class Nomad extends Sprite {

    private Image[] images; // array of image according to the current sprite's status.
    private int nbImages; // number of images of the array of image.
    private int curImageIdx; // sprite's current image index.
    private Image curImage; // sprite's current image.

    private int moveTime; // move time (in ms, defining the nomad's move speed).
    private long lastMoveTs; // last move timestamp.

    /**
     * Create a nomad.
     *
     * @param xMap        abscissa on the map.
     * @param yMap        ordinate on the map.
     * @param refreshTime the sprite refresh time (i.e. defining the image/sec)
     * @param moveTime    the move time (i.e. defining the nomad move speed)
     */
    public Nomad(int xMap, int yMap, int refreshTime, int moveTime) {
        super(xMap, yMap, refreshTime);
        this.moveTime = moveTime;
    }

    public Nomad() {}

    public int getMoveTime() {
        return moveTime;
    }

    public void setLastMoveTs(long lastMoveTs) {
        this.lastMoveTs = lastMoveTs;
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

    public void setImages(Image[] images) {
        this.images = images;
    }

    public void setNbImages(int nbImages) {
        this.nbImages = nbImages;
    }

    public void setCurImageIdx(int curImageIdx) {
        this.curImageIdx = curImageIdx;
    }

    /**
     * This function is used to handle the sprite's speed - in term of move on map.
     * It computes the elapsed time since the sprite has moved and return true if it should move, false oterhwise.
     *
     * @return true if the sprite should move, false oterhwise.
     */
    public boolean isTimeToMove() {
        long curTs = currentTimeSupplier.get().toEpochMilli(); // get the current time.
        if (curTs - lastMoveTs >= moveTime) { // it is time to move.
            lastMoveTs = curTs;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Update the nomad status.
     *
     * @return true if the status changed, flase otherwise.
     */
    public abstract boolean updateStatus();

    /**
     * Update the array of image according to the current sprite's status.
     */
    public abstract void updateSprite();

    @Override
    public abstract boolean isFinished();

    @Override
    public Image getCurImage() {
        return curImage;
    }

    @Override
    public void updateImage() {
        updateSprite();
        if (updateStatus() || // the status has changed
                (isTimeToRefresh() && // OR (it is time to refresh
                        (++curImageIdx == nbImages))) { // AND it is the end of the sprite).
            curImageIdx = 0;
        }
        curImage = images[curImageIdx];
    }
}
