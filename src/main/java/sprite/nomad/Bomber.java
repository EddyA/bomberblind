package sprite.nomad;

import sprite.SpriteType;
import utils.Direction;

import java.awt.*;

import static sprite.nomad.Bomber.Action.*;

/**
 * Abstract class of a bomber.
 */
public abstract class Bomber extends Nomad {

    /**
     * enum the different available action of a bomber.
     */
    public enum Action {
        ACTION_DYING, ACTION_WAITING, ACTION_WALKING, ACTION_WINING
    }

    private final BomberType bomberType;
    private Image[] deathImages;
    private int nbDeathFrame;
    private Image[] waitImages;
    private int nbWaitFrame;
    private Image[] walkBackImages;
    private Image[] walkFrontImages;
    private Image[] walkLeftImages;
    private Image[] walkRightImages;
    private int nbWalkFrame;
    private Image[] winImages;
    private int nbWinFrame;

    private Action curAction = ACTION_WAITING; // current action.
    private Action lastAction = curAction; // last action.
    private Direction curDirection; // current direction.
    private Direction lastDirection; // last direction.

    private int initialXMap; // initial abscissa on map.
    private int initialYMap; // initial ordinate on map.

    private int invincibilityTime; // invincibility time (in ms).
    private long lastInvincibilityTs; // last invincibility timestamp.

    /**
     * Create a bomber.
     *
     * @param xMap            abscissa on the map
     * @param yMap            ordinate on the map
     * @param bomberType      the bomber's type
     * @param deathImages     the array of image for the "death" action
     * @param nbDeathFrame    the number of images of the "death" array
     * @param waitImages      the array of image for the "wait" action
     * @param nbWaitFrame     the number of images of the "wait" array
     * @param walkBackImages  the array of images for the "walk back" action
     * @param walkFrontImages the array of images for the "walk front" action
     * @param walkLeftImages  the array of images for the "walk left" action
     * @param walkRightImages the array of images for the "walk right" action
     * @param nbWalkFrame     number of images of the "walk" arrays
     * @param winImages       the array of image for the "win" action
     * @param nbWinFrame      the number of images of the "win" array
     * @param refreshTime     the sprite refresh time (i.e. defining the image/sec)
     * @param moveTime        the move time (i.e. defining the nomad move speed)
     * @param invincibleTime  the time the bomber should be invicible after being revived
     */
    public Bomber(int xMap,
                  int yMap,
                  BomberType bomberType,
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
        super(xMap, yMap, SpriteType.BOMBER, refreshTime, moveTime);
        this.bomberType = bomberType;
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
        this.setInvincible(false);
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

    public Action getLastAction() {
        return lastAction;
    }

    public int getInitialXMap() {
        return initialXMap;
    }

    public void setInitialXMap(int initialXMap) {
        this.initialXMap = initialXMap;
    }

    public int getInitialYMap() {
        return initialYMap;
    }

    public void setInitialYMap(int initialYMap) {
        this.initialYMap = initialYMap;
    }

    public int getInvincibilityTime() {
        return invincibilityTime;
    }

    public long getLastInvincibilityTs() {
        return lastInvincibilityTs;
    }

    public void setLastAction(Action lastAction) {
        this.lastAction = lastAction;
    }

    public void setInvincible(boolean isInvincible) {
        long curTs = currentTimeSupplier.get().toEpochMilli(); // get the current time.
        if (isInvincible) {
            this.lastInvincibilityTs = curTs;
        } else {
            this.lastInvincibilityTs = curTs - invincibilityTime - 1;
        }
    }

    public void setLastInvincibilityTs(long lastInvincibilityTs) {
        this.lastInvincibilityTs = lastInvincibilityTs;
    }

    public void setCurAction(Action curAction) {
        this.curAction = curAction;
    }

    public Action getCurAction() {
        return curAction;
    }

    public void setCurDirection(Direction curDirection) {
        this.curDirection = curDirection;
    }

    public void setLastDirection(Direction lastDirection) {
        this.lastDirection = lastDirection;
    }

    /**
     * This function is mainly used to re-init the bomber after he died.
     */
    public void init() {
        xMap = initialXMap;
        yMap = initialYMap;
        curAction = ACTION_WAITING;
        setInvincible(true);
    }

    public boolean actionHasChanged() {
        return ((curAction != lastAction) ||
                (curAction == ACTION_WALKING && curDirection != lastDirection));
    }

    @Override
    public boolean updateStatus() {
        long curTs = currentTimeSupplier.get().toEpochMilli(); // get the current time.
        if ((actionHasChanged()) || // either the action has changed
                (lastRefreshTs == 0)) { // or it is the 1st call to that function.
            lastRefreshTs = curTs;
            lastAction = curAction;
            lastDirection = curDirection;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void updateSprite() {
        switch (curAction) {
            case ACTION_DYING: {
                images = deathImages;
                nbImages = nbDeathFrame;
                break;
            }
            case ACTION_WAITING: {
                images = waitImages;
                nbImages = nbWaitFrame;
                break;
            }
            case ACTION_WALKING: {
                switch (curDirection) {
                    case NORTH: {
                        images = walkBackImages;
                        nbImages = nbWalkFrame;
                        break;
                    }
                    case SOUTH: {
                        images = walkFrontImages;
                        nbImages = nbWalkFrame;
                        break;
                    }
                    case WEST: {
                        images = walkLeftImages;
                        nbImages = nbWalkFrame;
                        break;
                    }
                    case EAST: {
                        images = walkRightImages;
                        nbImages = nbWalkFrame;
                        break;
                    }
                }
                break;
            }
            case ACTION_WINING: {
                images = winImages;
                nbImages = nbWinFrame;
                break;
            }
            default: {
                throw new RuntimeException("another action is not allowed here, please check the algorithm.");
            }
        }
    }

    @Override
    public boolean isInvincible() {
        long curTs = currentTimeSupplier.get().toEpochMilli(); // get the current time.
        return lastInvincibilityTs + invincibilityTime >= curTs;
    }

    @Override
    public boolean isFinished() {
        return ((curAction.equals(ACTION_DYING)) && (curImageIdx == nbImages - 1));
    }
}