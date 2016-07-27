package sprites.nomad.abstracts;

import utils.CurrentTimeSupplier;

import java.awt.*;

import static sprites.nomad.abstracts.Enemy.status.NO_STATUS;
import static sprites.nomad.abstracts.Enemy.status.STATUS_DEAD;

/**
 * Abstract class of an enemy.
 */
public abstract class Enemy extends Character {
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
        NO_STATUS
    }

    private Enemy.status status; // status.
    private Enemy.status lastStatus; // last status.

    private final Image[] walkBackImages; // array of images of the "walk back" sprite.
    private final Image[] walkFrontImages; // array of images of the "walk front" sprite.
    private final Image[] walkLeftImages; // array of images of the "walk left" sprite.
    private final Image[] walkRightImages; // array of images of the "walk right" sprite.
    private final int nbWalkFrame; // number of images of the "walk" sprite.
    private int curImageIdx; // current image index of the sprite.
    private int refreshTime; // refresh time (in ms).
    private long lastRefreshTs; // last refresh timestamp.

    private boolean isFinished; // is the BbMan dead and the sprite finished?

    public Enemy(int xMap,
                 int yMap,
                 Image[] walkBackImages,
                 Image[] walkFrontImages,
                 Image[] walkLeftImages,
                 Image[] walkRightImages,
                 int nbWalkFrame,
                 int refreshTime) {
        super(xMap, yMap);
        this.status = NO_STATUS;
        this.lastStatus = NO_STATUS;
        this.walkBackImages = walkBackImages;
        this.walkFrontImages = walkFrontImages;
        this.walkLeftImages = walkLeftImages;
        this.walkRightImages = walkRightImages;
        this.nbWalkFrame = nbWalkFrame;
        this.refreshTime = refreshTime;
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
     * Update the image.
     *
     * @return the image to paint.
     */
    public Image updateImage() {
        long curTs = currentTimeSupplier.get().toEpochMilli(); // get the current time.

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
        if (status != lastStatus) {
            lastStatus = status;
            curImageIdx = 0;
            lastRefreshTs = curTs;
        } else {
            if (curTs - lastRefreshTs > refreshTime) { // it is time to refresh.
                lastRefreshTs = curTs;
                if (++curImageIdx == nbFrames) { // at the end of the sprite.
                    curImageIdx = 0; // back to the begining of the sprite.
                }
            }
        }
        return images[curImageIdx];
    }
}
