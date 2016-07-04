package sprites.nomad.abstracts;

import java.awt.*;

public abstract class Enemy extends Character {

    /**
     * enum the different status of an enemy.
     */
    public enum STATUS {
        STATUS_WALK_BACK,
        STATUS_WALK_FRONT,
        STATUS_WALK_LEFT,
        STATUS_WALK_RIGHT,
        NO_STATUS
    }

    private Enemy.STATUS status; // status.
    private Enemy.STATUS lastStatus; // last status.

    private final Image[] walkBackImages; // array of images of the "walk back" sprite.
    private final Image[] walkFrontImages; // array of images of the "walk front" sprite.
    private final Image[] walkLeftImages; // array of images of the "walk left" sprite.
    private final Image[] walkRightImages; // array of images of the "walk right" sprite.
    private final int nbWalkFrame; // number of images of the "walk" sprite.
    private int curImageIdx; // current image index of the sprite.
    private int refreshTime; // refresh time (in ms).
    private long lastRefreshTs; // last refresh timestamp.

    public Enemy(int xMap,
                 int yMap,
                 Image[] walkBackImages,
                 Image[] walkFrontImages,
                 Image[] walkLeftImages,
                 Image[] walkRightImages,
                 int nbWalkFrame,
                 int refreshTime) {
        super(xMap, yMap);
        this.status = STATUS.NO_STATUS;
        this.lastStatus = STATUS.NO_STATUS;
        this.walkBackImages = walkBackImages;
        this.walkFrontImages = walkFrontImages;
        this.walkLeftImages = walkLeftImages;
        this.walkRightImages = walkRightImages;
        this.nbWalkFrame = nbWalkFrame;
        this.refreshTime = refreshTime;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }

    public STATUS getStatus() {
        return status;
    }

    /**
     * Update the image.
     *
     * @return the image to paint.
     */
    public Image updateImage() {
        long curTs = System.currentTimeMillis(); // get the current time.

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
