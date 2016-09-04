package sprites.nomad;

import utils.CurrentTimeSupplier;

import java.awt.*;

import static sprites.nomad.Bomber.status.STATUS_DEAD;
import static sprites.nomad.Bomber.status.STATUS_WAIT;

/**
 * Abstract class of a bomber.
 * The sprite loops until isFinished() return true.
 */
public abstract class Bomber extends Nomad {
    protected CurrentTimeSupplier currentTimeSupplier = new CurrentTimeSupplier();

    /**
     * enum the different available status of a bomber.
     */
    public enum status {
        STATUS_DEAD,
        STATUS_WAIT,
        STATUS_WALK_BACK,
        STATUS_WALK_FRONT,
        STATUS_WALK_LEFT,
        STATUS_WALK_RIGHT,
        STATUS_WIN
    }

    protected Bomber.status status; // status.
    protected Bomber.status lastStatus; // last status.

    protected int initialXMap; // initial abscissa on map.
    protected int initialYMap; // initial ordinate on map.

    protected final Image[] deathImages;
    protected final int nbDeathFrame;
    protected final Image[] waitImages;
    protected final int nbWaitFrame;
    protected final Image[] walkBackImages;
    protected final Image[] walkFrontImages;
    protected final Image[] walkLeftImages;
    protected final Image[] walkRightImages;
    protected final int nbWalkFrame;
    protected final Image[] winImages;
    protected final int nbWinFrame;
    protected int curImageIdx; // current image index of the sprite.
    protected Image curImage; // current image of the sprite.
    protected int refreshTime; // refresh time of the sprite (in ms).
    protected long lastRefreshTs; // last refresh timestamp.

    protected boolean isInvincible; // is the bomber invincible?
    protected int invincibilityTime; // invincibility time (in ms).
    protected long lastInvincibilityTs; // last invincibility timestamp.

    protected boolean isFinished; // is the bomber dead and the dead sprite finished?

    public Bomber(int xMap,
                  int yMap,
                  Image[] deathImages,
                  int nbDeathFrame,
                  Image[] waitImages,
                  int nbWaitFrame,
                  Image[] walkBackImages,
                  Image[] walkFrontImages,
                  Image[] walkLeftImages,
                  Image[] walkRightImages,
                  int nbWalkFrame,
                  Image[] winImages,
                  int nbWinFrame,
                  int refreshTime,
                  int invincibleTime) {
        super(xMap, yMap);
        this.initialXMap = xMap;
        this.initialYMap = yMap;
        this.status = STATUS_WAIT;
        this.lastStatus = STATUS_WAIT;
        this.deathImages = deathImages;
        this.nbDeathFrame = nbDeathFrame;
        this.waitImages = waitImages;
        this.nbWaitFrame = nbWaitFrame;
        this.walkBackImages = walkBackImages;
        this.walkFrontImages = walkFrontImages;
        this.walkLeftImages = walkLeftImages;
        this.walkRightImages = walkRightImages;
        this.nbWalkFrame = nbWalkFrame;
        this.winImages = winImages;
        this.nbWinFrame = nbWinFrame;
        this.refreshTime = refreshTime;
        this.lastRefreshTs = 0;
        this.invincibilityTime = invincibleTime;
    }

    /**
     * This function is mainly used to re-init the bomber after he died.
     */
    public void initStatement() {
        this.status = STATUS_WAIT;
        super.setXMap(initialXMap);
        super.setYMap(initialYMap);
        this.isFinished = false;
        this.isInvincible = true;
        this.lastInvincibilityTs = currentTimeSupplier.get().toEpochMilli(); // get the current time.
    }

    public void setStatus(Bomber.status status) {
        this.status = status;
    }

    public Bomber.status getStatus() {
        return status;
    }

    public boolean isInvincible() {
        return isInvincible;
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }

    @Override
    public Image getCurImage() {
        return curImage;
    }

    @Override
    public void updateImage() {
        long curTs = currentTimeSupplier.get().toEpochMilli(); // get the current time.
        boolean shouldPrint = true;

        int nbFrames = 0;
        Image[] images = null;
        switch (status) {
            case STATUS_DEAD: {
                nbFrames = nbDeathFrame;
                images = deathImages;
                break;
            }
            case STATUS_WAIT: {
                nbFrames = nbWaitFrame;
                images = waitImages;
                break;
            }
            case STATUS_WALK_BACK: {
                nbFrames = nbWalkFrame;
                images = walkBackImages;
                break;
            }
            case STATUS_WALK_FRONT: {
                nbFrames = nbWalkFrame;
                images = walkFrontImages;
                break;
            }
            case STATUS_WALK_LEFT: {
                nbFrames = nbWalkFrame;
                images = walkLeftImages;
                break;
            }
            case STATUS_WALK_RIGHT: {
                nbFrames = nbWalkFrame;
                images = walkRightImages;
                break;
            }
            case STATUS_WIN: {
                nbFrames = nbWinFrame;
                images = winImages;
                break;
            }
        }
        if ((status != lastStatus) || // etiher the status changed
                (lastRefreshTs == 0)) { // or it is the 1st call to that function.
            lastRefreshTs = curTs;
            lastStatus = status;
            curImageIdx = 0;
        } else {
            if (curTs - lastRefreshTs > refreshTime) { // it is time to refresh.
                lastRefreshTs = curTs;
                curImageIdx++;
                if (curImageIdx == nbFrames) { // at the end of the sprite.
                    if (status == STATUS_DEAD) {
                        isFinished = true;
                    } else {
                        curImageIdx = 0; // back to the begining of the sprite.
                    }
                }
            }
        }
        if (isInvincible) {
            if (curTs - lastInvincibilityTs > invincibilityTime) { // stop invincibility?
                isInvincible = false;
            } else if (curImageIdx % 2 == 0) { // print every two images.
                shouldPrint = false;
            }
        }
        curImage = shouldPrint ? images[curImageIdx] : null;
    }
}