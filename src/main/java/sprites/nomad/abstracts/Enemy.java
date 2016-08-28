package sprites.nomad.abstracts;

import utils.CurrentTimeSupplier;
import utils.Tuple2;

import java.awt.*;

import static sprites.nomad.abstracts.Enemy.status.*;

/**
 * Abstract class of an enemy.
 */
public abstract class Enemy extends Nomad {
    protected CurrentTimeSupplier currentTimeSupplier = new CurrentTimeSupplier();

    /**
     * enum the different status of an enemy.
     */
    public enum status {
        STATUS_WALK_BACK,
        STATUS_WALK_FRONT,
        STATUS_WALK_LEFT,
        STATUS_WALK_RIGHT,
        STATUS_DEAD
    }

    private Enemy.status status; // status.
    private Enemy.status lastStatus; // last status.

    private final Image[] walkBackImages; // array of images of the "walk back" sprite.
    private final Image[] walkFrontImages; // array of images of the "walk front" sprite.
    private final Image[] walkLeftImages; // array of images of the "walk left" sprite.
    private final Image[] walkRightImages; // array of images of the "walk right" sprite.
    private final int nbWalkFrame; // number of images of the "walk" sprite.
    private int curImageIdx; // current image index of the sprite.
    private Image curImage; // current image of the sprite.
    private int refreshTime; // refresh time (in ms).
    private long lastRefreshTs; // last refresh timestamp.

    private int moveTime; // move time (in ms).
    private long lastMoveTs; // last move timestamp.

    private int nbDeadImages;
    private int curDeadImageIdx;
    private boolean isFinished; // is the Bomber dead and the sprite finished?

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
        this.moveTime = moveTime;
        this.nbDeadImages = 16;
    }

    public void setStatus(Enemy.status status) {
        this.status = status;
    }

    public Enemy.status getStatus() {
        return status;
    }

    public boolean isFinished() {
        return (status == STATUS_DEAD && isFinished);
    }

    /**
     * This function allows handling enemy speed.
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

    /**
     * Return a Tuple2 containing an array of images and the relative number of images according to a status.
     *
     * @param status the status
     * @return the relative Tuple2
     */
    private Tuple2<Image[], Integer> getImagesAccordingToCurrentDirection(Enemy.status status) {
        int nbFrames = 0;
        Image[] images = null;
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
        }
        return new Tuple2<>(images, nbFrames);
    }

    /**
     * @param status
     * @return
     */
    private Enemy.status getNextDirection(Enemy.status status) {
        Enemy.status nextStatus = null;
        switch (status) {
            case STATUS_WALK_BACK: {
                nextStatus = STATUS_WALK_LEFT;
                break;
            }
            case STATUS_WALK_LEFT: {
                nextStatus = STATUS_WALK_FRONT;
                break;
            }
            case STATUS_WALK_FRONT: {
                nextStatus = STATUS_WALK_RIGHT;
                break;
            }
            case STATUS_WALK_RIGHT: {
                nextStatus = STATUS_WALK_BACK;
                break;
            }
        }
        return nextStatus;
    }

    /**
     * Update the image.
     *
     * @return the image to paint.
     */
    public Image updateImage() {
        long curTs = currentTimeSupplier.get().toEpochMilli(); // get the current time.

        Tuple2<Image[], Integer> imageInformation;
        if (status != STATUS_DEAD) {
            imageInformation = getImagesAccordingToCurrentDirection(status); // get images according to the current direction.
        } else {
            imageInformation = getImagesAccordingToCurrentDirection(lastStatus); // get images according to the last direction.
        }
        Image[] images = imageInformation.getFirst();
        int nbFrames = imageInformation.getSecond();

        if ((status != lastStatus) && (status != STATUS_DEAD)) {
            lastRefreshTs = curTs;
            lastStatus = status;
            curImageIdx = 0;
        } else {
            if (curTs - lastRefreshTs > refreshTime) { // it is time to refresh.
                lastRefreshTs = curTs;
                if (status == STATUS_DEAD) {
                    if (++curDeadImageIdx == nbDeadImages) {
                        isFinished = true;
                    }
                    lastStatus = getNextDirection(lastStatus); // update the last status to
                } else if (++curImageIdx == nbFrames) { // at the end of the sprite.
                    curImageIdx = 0; // back to the begining of the sprite.
                }
            }
        }
        return images[curImageIdx];
    }
}
