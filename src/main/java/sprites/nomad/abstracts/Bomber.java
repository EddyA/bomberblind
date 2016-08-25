package sprites.nomad.abstracts;

import utils.CurrentTimeSupplier;

import java.awt.*;

import static sprites.nomad.abstracts.Bomber.status.STATUS_DEAD;
import static sprites.nomad.abstracts.Bomber.status.STATUS_WAIT;

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

    private Bomber.status status; // status.
    private Bomber.status lastStatus; // last status.

    private int initialXMap; // initial abscissa on map.
    private int initialYMap; // initial ordinate on map.

    private final Image[] deathImages;
    private final int nbDeathFrame;
    private final Image[] waitImages;
    private final int nbWaitFrame;
    private final Image[] walkBackImages;
    private final Image[] walkFrontImages;
    private final Image[] walkLeftImages;
    private final Image[] walkRightImages;
    private final int nbWalkFrame;
    private final Image[] winImages;
    private final int nbWinFrame;
    private int curImageIdx; // current image index of the animation.
    private int refreshTime; // refresh time of the sprite (in ms).
    private long lastRefreshTs; // last refresh timestamp.

    private boolean isInvincible; // is the bomber invincible?
    private int invincibilityTime; // invincibility time (in ms).
    private long lastInvincibilityTs; // last invincibility timestamp.

    private boolean isFinished; // is the bomber dead and the dead sprite finished?

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

    public boolean isFinished() {
        return (status == STATUS_DEAD && isFinished);
    }

    public boolean isInvincible() {
        return isInvincible;
    }

    /**
     * Update the image.
     *
     * @return the image to paint.
     */
    public Image updateImage() {
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
        if (status != lastStatus) {
            lastStatus = status;
            curImageIdx = 0;
            lastRefreshTs = curTs;
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
            if (isInvincible) {
                if (curTs - lastInvincibilityTs > invincibilityTime) { // stop invincibility?
                    isInvincible = false;
                } else if (curImageIdx % 2 == 0) { // print every two images.
                    shouldPrint = false;
                }
            }
        }
        return shouldPrint ? images[curImageIdx] : null;
    }
}