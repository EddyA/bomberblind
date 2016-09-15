package sprites.nomad.abstracts;

import java.awt.*;

import static sprites.nomad.abstracts.Bomber.status.STATUS_DYING;
import static sprites.nomad.abstracts.Bomber.status.STATUS_WAITING;

/**
 * Abstract class of a bomber.
 */
public abstract class Bomber extends Nomad {

    /**
     * enum the different available status of a bomber.
     */
    public enum status {
        STATUS_DEAD,
        STATUS_DYING,
        STATUS_WAITING,
        STATUS_WALKING_BACK,
        STATUS_WALKING_FRONT,
        STATUS_WALKING_LEFT,
        STATUS_WALKING_RIGHT,
        STATUS_WON
    }

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

    private Bomber.status curStatus = STATUS_WAITING; // current status.
    private Bomber.status lastStatus = STATUS_WAITING; // last curStatus.

    private int initialXMap; // initial abscissa on map.
    private int initialYMap; // initial ordinate on map.

    private boolean isInvincible; // is the bomber invincible?
    private int invincibilityTime; // invincibility time (in ms).
    private long lastInvincibilityTs; // last invincibility timestamp.

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
                  int moveTime,
                  int invincibleTime) {
        super(xMap, yMap, refreshTime, moveTime);
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
        this.invincibilityTime = invincibleTime;
        this.lastInvincibilityTs = 0;
        this.initialXMap = xMap;
        this.initialYMap = yMap;
    }

    public Image[] getDeathImages() {
        return deathImages;
    }

    public int getNbDeathFrame() {
        return nbDeathFrame;
    }

    public Image[] getWaitImages() {
        return waitImages;
    }

    public int getNbWaitFrame() {
        return nbWaitFrame;
    }

    public Image[] getWalkBackImages() {
        return walkBackImages;
    }

    public Image[] getWalkFrontImages() {
        return walkFrontImages;
    }

    public Image[] getWalkLeftImages() {
        return walkLeftImages;
    }

    public Image[] getWalkRightImages() {
        return walkRightImages;
    }

    public int getNbWalkFrame() {
        return nbWalkFrame;
    }

    public Image[] getWinImages() {
        return winImages;
    }

    public int getNbWinFrame() {
        return nbWinFrame;
    }

    public status getLastStatus() {
        return lastStatus;
    }

    public int getInitialXMap() {
        return initialXMap;
    }

    public int getInitialYMap() {
        return initialYMap;
    }

    public int getInvincibilityTime() {
        return invincibilityTime;
    }

    public long getLastInvincibilityTs() {
        return lastInvincibilityTs;
    }

    public void setLastStatus(status lastStatus) {
        this.lastStatus = lastStatus;
    }

    public void setInitialXMap(int initialXMap) {
        this.initialXMap = initialXMap;
    }

    public void setInitialYMap(int initialYMap) {
        this.initialYMap = initialYMap;
    }

    public void setInvincible(boolean invincible) {
        isInvincible = invincible;
    }

    public void setInvincibilityTime(int invincibilityTime) {
        this.invincibilityTime = invincibilityTime;
    }

    public void setLastInvincibilityTs(long lastInvincibilityTs) {
        this.lastInvincibilityTs = lastInvincibilityTs;
    }

    /**
     * This function is mainly used to re-init the bomber after he died.
     */
    public void initStatement() {
        this.curStatus = STATUS_WAITING;
        super.setXMap(initialXMap);
        super.setYMap(initialYMap);
        this.isInvincible = true;
        this.lastInvincibilityTs = currentTimeSupplier.get().toEpochMilli(); // get the current time.
    }

    public void setCurStatus(Bomber.status curStatus) {
        this.curStatus = curStatus;
    }

    public Bomber.status getCurStatus() {
        return curStatus;
    }

    public boolean isInvincible() {
        return isInvincible;
    }

    @Override
    public boolean isFinished() {
        return ((curStatus == STATUS_DYING) && (getCurImageIdx() == (getNbImages() - 1)));
    }

    @Override
    public boolean updateStatus() {
        long curTs = currentTimeSupplier.get().toEpochMilli(); // get the current time.
        if ((curStatus != lastStatus) || // etiher the status has changed
                (lastRefreshTs == 0)) { // or it is the 1st call to that function.
            lastRefreshTs = curTs;
            lastStatus = curStatus;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void updateSprite() {
        switch (curStatus) {
            case STATUS_DYING: {
                setImages(deathImages);
                setNbImages(nbDeathFrame);
                break;
            }
            case STATUS_WAITING: {
                setImages(waitImages);
                setNbImages(nbWaitFrame);
                break;
            }
            case STATUS_WALKING_BACK: {
                setImages(walkBackImages);
                setNbImages(nbWalkFrame);
                break;
            }
            case STATUS_WALKING_FRONT: {
                setImages(walkFrontImages);
                setNbImages(nbWalkFrame);
                break;
            }
            case STATUS_WALKING_LEFT: {
                setImages(walkLeftImages);
                setNbImages(nbWalkFrame);
                break;
            }
            case STATUS_WALKING_RIGHT: {
                setImages(walkRightImages);
                setNbImages(nbWalkFrame);
                break;
            }
            case STATUS_WON: {
                setImages(winImages);
                setNbImages(nbWinFrame);
                break;
            }
            default: {
                throw new RuntimeException("another status is not allowed here, please check the algorithm.");
            }
        }
    }
}