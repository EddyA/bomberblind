package sprite.nomad;

import sprite.Sprite;
import sprite.SpriteType;

import java.awt.*;

/**
 * Abstract class of a nomad.
 */
public abstract class Nomad extends Sprite {

    Image[] images; // array of image according to the current sprite's action.
    int nbImages; // number of images of the array of image.
    int curImageIdx; // sprite's current image index.
    private Image curImage; // sprite's current image.

    private final int moveTime; // move time (in ms, defining the nomad's move speed).
    private long lastMoveTs; // last move timestamp.

    private int invincibleFrameIdx; // current invincible frame index.

    /**
     * Create a nomad.
     *
     * @param xMap        abscissa on the map.
     * @param yMap        ordinate on the map.
     * @param spriteType  the sprite's type
     * @param refreshTime the sprite refresh time (i.e. defining the image/sec)
     * @param moveTime    the move time (i.e. defining the sprite's speed)
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

    void setNbImages(int nbImages) {
        this.nbImages = nbImages;
    }

    int getCurImageIdx() {
        return curImageIdx;
    }

    void setCurImageIdx(int curImageIdx) {
        this.curImageIdx = curImageIdx;
    }

    int getMoveTime() {
        return moveTime;
    }

    void setLastMoveTs(long lastMoveTs) {
        this.lastMoveTs = lastMoveTs;
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
     * Update the sprite's image according to the current sprite's action.
     */
    protected abstract void updateSprite();

    /**
     * @return true if the current action has changed, false otherwise.
     */
    protected abstract boolean hasActionChanged();

    /**
     * @return true if the sprite is invincible, false otherwise.
     */
    protected abstract boolean isInvincible();

    @Override
    public abstract boolean isFinished();

    @Override
    public Image getCurImage() {
        return curImage;
    }

    @Override
    public void updateImage() {
        updateSprite();
        if (hasActionChanged() || // the action has changed
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