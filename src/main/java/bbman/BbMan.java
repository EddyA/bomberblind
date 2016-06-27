package bbman;

import images.BbManSprites;

import java.awt.*;

import static bbman.BbMan.STATUS.STATUS_WAIT;

public class BbMan {

    /**
     * enum the different available status of a bbman.
     */
    public enum STATUS {
        STATUS_DEATH,
        STATUS_WAIT,
        STATUS_WALK_BACK,
        STATUS_WALK_FRONT,
        STATUS_WALK_LEFT,
        STATUS_WALK_RIGHT,
        STATUS_WIN
    }

    private STATUS status; // status (walking, waiting, etc.).
    private STATUS lastStatus; // last status.
    private Point initPointOnMap; // initial position on the map.
    private Point pointOnMap; // position on the map.

    private BbManSprites bBManImages; // set of images.
    private int curImageIdx; // current image index of the animation.
    private int refreshTime; // refresh time of the animation (in ms).
    private long lastRefreshTs; // last refresh timestamp.
    private boolean mustLoop; // is the current animation must loop?
    private boolean endOfAnimation; // is at the end of the animation (e.g death, win)?

    public BbMan(int x, int y, BbManSprites bBManImages) {
        this.initPointOnMap = new Point(x, y);
        this.pointOnMap = new Point(x, y);
        this.bBManImages = bBManImages;
        this.refreshTime = 100;
        this.initStatement();
    }

    /**
     * This function is mainly used to re-init the BbMan after he died.
     */
    public void initStatement() {
        this.status = STATUS_WAIT;
        this.pointOnMap.setLocation(this.initPointOnMap);
        this.endOfAnimation = false;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }

    public void setPointOnMap(int x, int y) {
        this.pointOnMap.setLocation(x, y);
    }

    public STATUS getStatus() {
        return status;
    }

    public Point getPointOnMap() {
        return pointOnMap;
    }

    public boolean getEndOfAnimation() {
        return endOfAnimation;
    }

    /**
     * Update the image.
     *
     * @return the image to paint.
     */
    private Image updateImage() {
        long curTs = System.currentTimeMillis(); // get the current time.

        int nbFrames = 0;
        Image[] images = null;
        switch (status) {
            case STATUS_WALK_BACK: {
                nbFrames = bBManImages.nbWalkFrame;
                images = bBManImages.walkBackImages;
                mustLoop = true;
                break;
            }
            case STATUS_WALK_FRONT: {
                nbFrames = bBManImages.nbWalkFrame;
                images = bBManImages.walkFrontImages;
                mustLoop = true;
                break;
            }
            case STATUS_WALK_LEFT: {
                nbFrames = bBManImages.nbWalkFrame;
                images = bBManImages.walkLeftImages;
                mustLoop = true;
                break;
            }
            case STATUS_WALK_RIGHT: {
                nbFrames = bBManImages.nbWalkFrame;
                images = bBManImages.walkRightImages;
                mustLoop = true;
                break;
            }
            case STATUS_WAIT: {
                nbFrames = bBManImages.nbWaitFrame;
                images = bBManImages.waitImages;
                mustLoop = true;
                break;
            }
            case STATUS_WIN: {
                nbFrames = bBManImages.nbWinFrame;
                images = bBManImages.winImages;
                mustLoop = false;
                break;
            }
            case STATUS_DEATH: {
                nbFrames = bBManImages.nbDeathFrame;
                images = bBManImages.deathImages;
                mustLoop = false;
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
                if (++curImageIdx == nbFrames) { // at the end of the animation.
                    if (mustLoop) {
                        curImageIdx = 0; // back to the begining of the animation.
                    } else {
                        curImageIdx--; // keep the last image.
                        endOfAnimation = true;
                    }
                }
            }
        }
        return images[curImageIdx];
    }

    /**
     * Paint the image.
     *
     * @param g             the graphics context
     * @param bbManPosition the start point (abscissa and ordinate of the BbMan).
     */
    public void paintBuffer(Graphics g, Point bbManPosition) {
        Image updatedImage = updateImage();
        int xMap = (int) bbManPosition.getX() - updatedImage.getWidth(null) / 2;
        int yMap = (int) bbManPosition.getY() - updatedImage.getHeight(null);
        g.drawImage(updatedImage, xMap, yMap, null);
    }
}

