package sprite.nomad;

import sprite.SpriteType;
import sprite.Sprite;

import java.awt.*;

/**
 * Abstract class of a nomad.
 */
public abstract class Nomad extends Sprite {

    protected Image[] images; // array of image according to the current sprite's action.
    protected int nbImages; // number of images of the array of image.
    protected int curImageIdx; // sprite's current image index.
    private Image curImage; // sprite's current image.

    private int moveTime; // move time (in ms, defining the nomad's move speed).
    private long lastMoveTs; // last move timestamp.

    private int invincibleFrameIdx; // current invincible frame index.

    /**
     * Create a nomad.
     *
     * @param xMap        abscissa on the map.
     * @param yMap        ordinate on the map.
     * @param spriteType  the sprite's type
     * @param refreshTime the sprite refresh time (i.e. defining the image/sec)
     * @param moveTime    the move time (i.e. defining the nomad move speed)
     */
    Nomad(int xMap, int yMap, SpriteType spriteType, int refreshTime, int moveTime) {
        super(xMap, yMap, spriteType, refreshTime);
        this.moveTime = moveTime;
    }

    public Image[] getImages() {
        return images;
    }

    public void setImages(Image[] images) {
        this.images = images;
    }

    public int getNbImages() {
        return nbImages;
    }

    public void setNbImages(int nbImages) {
        this.nbImages = nbImages;
    }

    public int getCurImageIdx() {
        return curImageIdx;
    }

    public void setCurImageIdx(int curImageIdx) {
        this.curImageIdx = curImageIdx;
    }

    public void setCurImage(Image curImage) {
        this.curImage = curImage;
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

    public int getInvincibleFrameIdx() {
        return invincibleFrameIdx;
    }

    public void setInvincibleFrameIdx(int invincibleFrameIdx) {
        this.invincibleFrameIdx = invincibleFrameIdx;
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
     * Update the array of image according to the current sprite's action.
     */
    public abstract void updateSprite();

    /**
     * @return true if the sprite is invicible, false otherwise.
     */
    public abstract boolean isInvincible();

    @Override
    public abstract boolean isFinished();

    @Override
    public Image getCurImage() {
        return curImage;
    }

    @Override
    public void updateImage() {
        updateSprite();
        if (updateStatus() || // the action has changed
                (isTimeToRefresh() && // OR (it is time to refresh
                        (++curImageIdx == nbImages))) { // AND it is the end of the sprite).
            curImageIdx = 0;
        }
        if (isInvincible() &&
                invincibleFrameIdx++ % 60 > 30) {
            curImage = null;
        } else {
            curImage = images[curImageIdx];
        }
    }
}
