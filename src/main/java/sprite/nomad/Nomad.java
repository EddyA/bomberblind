package sprite.nomad;

import sprite.Sprite;
import sprite.SpriteType;

import java.awt.*;

/**
 * Abstract class of a nomad.
 */
public abstract class Nomad extends Sprite {

    protected Image[] images; // array of image according to the current sprite's action.
    protected int nbImages; // number of images of the array of image.
    protected int curImageIdx; // sprite's current image index.
    private Image curImage; // sprite's current image.

    private final int actingTime; // acting time (in ms, defining the sprite's speed in term of action/sec).
    private long lastActionTs; // last action timestamp.

    private int invincibleFrameIdx; // current invincible frame index.

    /**
     * Create a nomad.
     *
     * @param xMap        abscissa on the map.
     * @param yMap        ordinate on the map.
     * @param spriteType  the sprite's type
     * @param refreshTime the sprite refresh time (i.e. defining the sprite's speed in term of image/sec)
     * @param actingTime  the sprite acting time (i.e. defining the sprite's speed in term of action/sec)
     */
    public Nomad(int xMap, int yMap, SpriteType spriteType, int refreshTime, int actingTime) {
        super(xMap, yMap, spriteType, refreshTime);
        this.actingTime = actingTime;
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

    public int getActingTime() {
        return actingTime;
    }

    public void setLastActionTs(long lastActionTs) {
        this.lastActionTs = lastActionTs;
    }

    /**
     * This function is used to handle sprite's speed - in term of action/sec.
     * It computes the elapsed time since the last sprite's action
     * and return true if it should act again, false oterhwise.
     *
     * @return true if the sprite should act, false oterhwise.
     */
    public boolean isTimeToAct() {
        long curTs = currentTimeSupplier.get().toEpochMilli(); // get the current time.
        if (curTs - lastActionTs >= actingTime) { // it is time to act.
            lastActionTs = curTs;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Update the sprite's image according to the current sprite's action.
     */
    public abstract void updateSprite();

    /**
     * @return true if the current action has changed, false otherwise.
     */
    public abstract boolean hasActionChanged();

    /**
     * @return true if the sprite is invincible, false otherwise.
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
        if (hasActionChanged() || // the action has changed
                (isTimeToRefresh() && // OR (it is time to refresh
                        (++curImageIdx == nbImages))) { // AND it is the end of the sprite).
            curImageIdx = 0;
        }
        if (isInvincible() &&
                invincibleFrameIdx++ % 320 > 160) {
            curImage = null;
        } else {
            curImage = images[curImageIdx];
        }
    }
}