package sprites.nomad.abstracts;

import java.awt.*;

import static images.ImagesLoader.IMAGE_SIZE;
import static sprites.nomad.abstracts.BbMan.STATUS.STATUS_DEAD;
import static sprites.nomad.abstracts.BbMan.STATUS.STATUS_WAIT;

public class BbMan extends Character {

    /**
     * enum the different available status of a bbman.
     */
    public enum STATUS {
        STATUS_DEAD,
        STATUS_WAIT,
        STATUS_WALK_BACK,
        STATUS_WALK_FRONT,
        STATUS_WALK_LEFT,
        STATUS_WALK_RIGHT,
        STATUS_WIN
    }

    private STATUS status; // status.
    private STATUS lastStatus; // last status.

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
    private int refreshTime; // refresh time of the animation (in ms).
    private long lastRefreshTs; // last refresh timestamp.

    private boolean isFinished; // is the BbMan dead and the sprite finished?

    public BbMan(int xMap,
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
                 int refreshTime) {
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
    }

    /**
     * This function is mainly used to re-init the BbMan after he died.
     */
    public void initStatement() {
        this.status = STATUS_WAIT;
        super.setXMap(initialXMap);
        super.setYMap(initialYMap);
        this.isFinished = false;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }

    public STATUS getStatus() {
        return status;
    }

    public boolean isFinished() {
        return (status == STATUS_DEAD && isFinished);
    }

    /**
     * Return the rowIdx of the BbMan top point given an map ordinate.
     */
    public static int getTopRowIdxIfOrdIs(int yBbMan) {
        return (yBbMan - (IMAGE_SIZE / 2)) / IMAGE_SIZE;
    }

    /**
     * Return the rowIdx of the BbMan lowest point given a map ordinate.
     */
    public static int getLowestRowIdxIfOrdIs(int yBbMan) {
        return yBbMan / IMAGE_SIZE;
    }

    /**
     * Return the colIdx of the BbMan most left point given a map abscissa.
     */
    public static int getMostLeftColIdxIfAbsIs(int xBbMan) {
        return (xBbMan - IMAGE_SIZE / 2) / IMAGE_SIZE;
    }

    /**
     * Return the colIdx of the BbMap most right point given a map abscissa.
     */
    public static int getMostRightColIdxIfAbsIs(int xBbMan) {
        return (xBbMan + IMAGE_SIZE / 2 - 1) / IMAGE_SIZE;
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
                if (++curImageIdx == nbFrames) { // at the end of the sprite.
                    if (status == STATUS_DEAD) {
                        isFinished = true;
                    } else {
                        curImageIdx = 0; // back to the begining of the sprite.
                    }
                }
            }
        }
        return images[curImageIdx];
    }
}

