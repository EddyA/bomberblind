package sprites.nomad.abstracts;

import java.awt.*;

import static sprites.nomad.abstracts.Enemy.status.STATUS_DYING;
import static sprites.nomad.abstracts.Enemy.status.STATUS_WALKING_FRONT;

/**
 * Abstract class of an enemy.
 */
public abstract class Enemy extends Nomad {

    /**
     * enum the different available status of an enemy.
     */
    public enum status {
        STATUS_DEAD,
        STATUS_DYING,
        STATUS_WALKING_BACK,
        STATUS_WALKING_FRONT,
        STATUS_WALKING_LEFT,
        STATUS_WALKING_RIGHT
    }

    private final Image[] deathImages;
    private final int nbDeathFrame;
    private final Image[] walkBackImages; // array of images of the "walk back" sprite.
    private final Image[] walkFrontImages; // array of images of the "walk front" sprite.
    private final Image[] walkLeftImages; // array of images of the "walk left" sprite.
    private final Image[] walkRightImages; // array of images of the "walk right" sprite.
    private final int nbWalkFrame; // number of images of the "walk" sprite.

    private status curStatus = STATUS_WALKING_FRONT; // current curStatus.
    private status lastStatus = STATUS_WALKING_FRONT; // last curStatus.

    public Enemy(int xMap,
                 int yMap,
                 Image[] deathImages,
                 int nbDeathFrame,
                 Image[] walkBackImages,
                 Image[] walkFrontImages,
                 Image[] walkLeftImages,
                 Image[] walkRightImages,
                 int nbWalkFrame,
                 int refreshTime,
                 int moveTime) {
        super(xMap, yMap, refreshTime, moveTime);
        this.deathImages = deathImages;
        this.nbDeathFrame = nbDeathFrame;
        this.walkBackImages = walkBackImages;
        this.walkFrontImages = walkFrontImages;
        this.walkLeftImages = walkLeftImages;
        this.walkRightImages = walkRightImages;
        this.nbWalkFrame = nbWalkFrame;
    }

    public Image[] getDeathImages() {

        return deathImages;
    }

    public int getNbDeathFrame() {
        return nbDeathFrame;
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

    public status getLastStatus() {
        return lastStatus;
    }

    public void setLastStatus(status lastStatus) {
        this.lastStatus = lastStatus;
    }

    public void setCurStatus(Enemy.status curStatus) {
        this.curStatus = curStatus;
    }

    public Enemy.status getCurStatus() {
        return curStatus;
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
            default: {
                throw new RuntimeException("another status is not allowed here, please check the algorithm.");
            }
        }
    }
}
