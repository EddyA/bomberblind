package sprites.nomad.abstracts;

import sprites.Sprite;

import java.awt.*;

/**
 * Abstract class of a abstracts sprite.
 */
public abstract class Nomad extends Sprite {

    private Image[] images; // array of images of the sprite.
    private int nbImages; // number of images of the sprite.
    private int curImageIdx; // current image index of the sprite.
    private Image curImage; // current image of the sprite.

    private int moveTime; // move time (in ms).
    private long lastMoveTs; // last move timestamp.

    public Nomad(int xMap, int yMap, int refreshTime, int moveTime) {
        super(xMap, yMap, refreshTime);
        this.moveTime = moveTime;
    }

    public int getMoveTime() {
        return moveTime;
    }

    public void setMoveTime(int moveTime) {
        this.moveTime = moveTime;
    }

    public long getLastMoveTs() {
        return lastMoveTs;
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

    public void setCurImage(Image curImage) {
        this.curImage = curImage;
    }

    /**
     * This function is used to handle the sprite's speed - in term of move on map.
     * It computes the elapsed time since the sprite has moved and return true if it should move, false oterhwise.
     *
     * @return true if the sprite should move, false oterhwise.
     */
    public boolean shouldMove() {
        long curTs = currentTimeSupplier.get().toEpochMilli(); // get the current time.
        if (curTs - lastMoveTs >= moveTime) { // it is time to move.
            lastMoveTs = curTs;
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return true if the sprite is finished, false otherwise.
     */
    public abstract boolean isFinished();

    /**
     * @return the current image of the sprite.
     */
    public Image getCurImage() {
        return curImage;
    }

    public abstract void updateSprite();

    public abstract boolean updateStatus();

    /**
     * Update the sprite image.
     */
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
