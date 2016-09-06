package sprites.nomad;

import utils.CurrentTimeSupplier;

import java.awt.*;

import static sprites.nomad.Enemy.status.STATUS_DEAD;
import static sprites.nomad.Enemy.status.STATUS_WALK_FRONT;

/**
 * Abstract class of an enemy.
 */
public abstract class Enemy extends Nomad {
    protected CurrentTimeSupplier currentTimeSupplier = new CurrentTimeSupplier();

    /**
     * enum the different status of an enemy.
     */
    public enum status {
        STATUS_DEAD,
        STATUS_WALK_BACK,
        STATUS_WALK_FRONT,
        STATUS_WALK_LEFT,
        STATUS_WALK_RIGHT,
    }

    protected Enemy.status status; // status.
    protected Enemy.status lastStatus; // last status.

    protected final Image[] walkBackImages; // array of images of the "walk back" sprite.
    protected final Image[] walkFrontImages; // array of images of the "walk front" sprite.
    protected final Image[] walkLeftImages; // array of images of the "walk left" sprite.
    protected final Image[] walkRightImages; // array of images of the "walk right" sprite.
    protected final int nbWalkFrame; // number of images of the "walk" sprite.
    protected int curImageIdx; // current image index of the sprite.
    protected Image curImage; // current image of the sprite.
    protected int refreshTime; // refresh time (in ms).
    protected long lastRefreshTs; // last refresh timestamp.

    protected int moveTime; // move time (in ms).
    protected long lastMoveTs; // last move timestamp.

    protected boolean isFinished; // is the enemy dead and the sprite finished?

    public Enemy(int xMap,
                 int yMap,
                 Image[] walkBackImages,
                 Image[] walkFrontImages,
                 Image[] walkLeftImages,
                 Image[] walkRightImages,
                 int nbWalkFrame,
                 int refreshTime,
                 int moveTime) {
        super(xMap, yMap);
        this.status = STATUS_WALK_FRONT;
        this.lastStatus = STATUS_WALK_FRONT;
        this.walkBackImages = walkBackImages;
        this.walkFrontImages = walkFrontImages;
        this.walkLeftImages = walkLeftImages;
        this.walkRightImages = walkRightImages;
        this.nbWalkFrame = nbWalkFrame;
        this.refreshTime = refreshTime;
        this.lastRefreshTs = 0;
        this.moveTime = moveTime;
        this.lastMoveTs = 0;
    }

    public void setStatus(Enemy.status status) {
        this.status = status;
    }

    public Enemy.status getStatus() {
        return status;
    }

    /**
     * This function is mainly used to handle speed of character.
     * It computes the time spent since its last move and return true if it should move, false oterhwise.
     *
     * @return true if the enemy should move, false oterhwise.
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

        if (status == STATUS_DEAD) { // get images according to the current direction.
            isFinished = true;
        } else {
            int nbFrames;
            Image[] images;
            switch (status) {
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
                default: {
                    throw new RuntimeException("another status is not allowed here, please check the algorithm.");
                }
            }
            if ((status != lastStatus) || // etiher the status changed
                    (lastRefreshTs == 0)) { // or it is the 1st call to that function.
                lastRefreshTs = curTs;
                lastStatus = status;
                curImageIdx = 0;
            } else {
                if (curTs - lastRefreshTs >= refreshTime) { // it is time to refresh.
                    lastRefreshTs = curTs;
                    if (++curImageIdx == nbFrames) { // at the end of the sprite.
                        curImageIdx = 0; // back to the begining of the sprite.
                    }
                }
            }
            curImage = images[curImageIdx];
        }
    }
}
