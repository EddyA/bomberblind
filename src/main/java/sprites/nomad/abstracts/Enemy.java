package sprites.nomad.abstracts;

import java.awt.*;

import static sprites.nomad.abstracts.Enemy.status.*;

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

    protected final Image[] deathImages;
    protected final int nbDeathFrame;
    protected final Image[] walkBackImages; // array of images of the "walk back" sprite.
    protected final Image[] walkFrontImages; // array of images of the "walk front" sprite.
    protected final Image[] walkLeftImages; // array of images of the "walk left" sprite.
    protected final Image[] walkRightImages; // array of images of the "walk right" sprite.
    protected final int nbWalkFrame; // number of images of the "walk" sprite.

    protected status curStatus = STATUS_WALKING_FRONT; // current curStatus.
    protected status lastStatus = STATUS_WALKING_FRONT; // last curStatus.

    protected Image curImage; // current image of the sprite.
    protected int curImageIdx; // current image index of the sprite.

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

    public int getCurImageIdx() {
        return curImageIdx;
    }

    public void setLastStatus(status lastStatus) {
        this.lastStatus = lastStatus;
    }

    public void setCurImage(Image curImage) {
        this.curImage = curImage;
    }

    public void setCurImageIdx(int curImageIdx) {
        this.curImageIdx = curImageIdx;
    }

    public void setCurStatus(Enemy.status curStatus) {
        this.curStatus = curStatus;
    }

    public Enemy.status getCurStatus() {
        return curStatus;
    }

    @Override
    public boolean isFinished() {
        return curStatus == STATUS_DEAD;
    }

    @Override
    public Image getCurImage() {
        return curImage;
    }

    @Override
    public void updateImage() {
        long curTs = currentTimeSupplier.get().toEpochMilli(); // get the current time.

        int nbFrames;
        Image[] images;
        switch (curStatus) {
            case STATUS_DYING: {
                nbFrames = nbDeathFrame;
                images = deathImages;
                break;
            }
            case STATUS_WALKING_BACK: {
                nbFrames = nbWalkFrame;
                images = walkBackImages;
                break;
            }
            case STATUS_WALKING_FRONT: {
                nbFrames = nbWalkFrame;
                images = walkFrontImages;
                break;
            }
            case STATUS_WALKING_LEFT: {
                nbFrames = nbWalkFrame;
                images = walkLeftImages;
                break;
            }
            case STATUS_WALKING_RIGHT: {
                nbFrames = nbWalkFrame;
                images = walkRightImages;
                break;
            }
            default: {
                throw new RuntimeException("another status is not allowed here, please check the algorithm.");
            }
        }
        if ((curStatus != lastStatus) || // etiher the curStatus changed
                (lastRefreshTs == 0)) { // or it is the 1st call to that function.
            lastRefreshTs = curTs;
            lastStatus = curStatus;
            curImageIdx = 0;
        } else {
            if (curTs - lastRefreshTs >= refreshTime) { // it is time to refresh.
                lastRefreshTs = curTs;
                curImageIdx++;
                if (curImageIdx == nbFrames) { // at the end of the sprite.
                    if (curStatus == STATUS_DYING) {
                        curStatus = STATUS_DEAD;
                    } else {
                        curImageIdx = 0; // back to the begining of the sprite.
                    }
                }
            }
        }
        curImage = images[curImageIdx];
    }
}
