package sprite.nomad;

import sprite.SpriteAction;
import sprite.SpriteType;

import java.awt.*;

import static sprite.SpriteAction.*;

/**
 * Abstract class of a bomber.
 */
public abstract class Bomber extends Nomad {

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

    private int initialXMap; // initial abscissa on map.
    private int initialYMap; // initial ordinate on map.

    private int nbLife;

    /**
     * Create a bomber.
     *
     * @param xMap              abscissa on the map
     * @param yMap              ordinate on the map
     * @param deathImages       the array of image for the "death" action
     * @param nbDeathFrame      the number of images of the "death" array
     * @param waitImages        the array of image for the "wait" action
     * @param nbWaitFrame       the number of images of the "wait" array
     * @param walkBackImages    the array of images for the "walk back" action
     * @param walkFrontImages   the array of images for the "walk front" action
     * @param walkLeftImages    the array of images for the "walk left" action
     * @param walkRightImages   the array of images for the "walk right" action
     * @param nbWalkFrame       number of images of the "walk" arrays
     * @param winImages         the array of image for the "win" action
     * @param nbWinFrame        the number of images of the "win" array
     * @param refreshTime       the sprite refresh time (i.e. defining the sprite speed in term of image/sec)
     * @param actingTime        the sprite acting time (i.e. defining the sprite speed in term of action/sec)
     * @param invincibilityTime the sprite invincibility time
     * @param nbLife            the number of life
     */
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
                  int actingTime,
                  int invincibilityTime,
                  int nbLife) {
        super(xMap,
                yMap,
                SpriteType.TYPE_BOMBER,
                refreshTime,
                actingTime,
                invincibilityTime);
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
        this.initialXMap = xMap;
        this.initialYMap = yMap;
        this.nbLife = nbLife;
        init();
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

    public int getNbLife() {
        return nbLife;
    }

    public void setNbLife(int nbLife) {
        this.nbLife = nbLife;
    }

    /**
     * This function is mainly used to process the bomber after he died.
     *
     * @return true if the bomber is definitively dead, false otherwise.
     */
    public boolean init() {
        if (nbLife > 0) {
            xMap = initialXMap;
            yMap = initialYMap;
            curSpriteAction = ACTION_WAITING;
            lastInvincibilityTs = currentTimeSupplier.get().toEpochMilli(); // activate invincibility.
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean isActionAllowed(SpriteAction spriteAction) {
        return !(spriteAction != ACTION_WAITING &&
                spriteAction != ACTION_WALKING &&
                spriteAction != ACTION_WINING &&
                spriteAction != ACTION_DYING);
    }

    @Override
    public boolean hasActionChanged() {
        if (!curSpriteAction.equals(lastSpriteAction) || // either the action has changed
                (curSpriteAction.equals(ACTION_WALKING) && // or (is walking
                        !curDirection.equals(lastDirection))) { // and the direction has changed).
            lastSpriteAction = curSpriteAction;
            lastDirection = curDirection;
            lastRefreshTs = currentTimeSupplier.get().toEpochMilli();
            return true;
        }
        return false;
    }

    @Override
    public void updateSprite() {
        switch (curSpriteAction) {
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
                    case DIRECTION_NORTH: {
                        images = walkBackImages;
                        nbImages = nbWalkFrame;
                        break;
                    }
                    case DIRECTION_SOUTH: {
                        images = walkFrontImages;
                        nbImages = nbWalkFrame;
                        break;
                    }
                    case DIRECTION_WEST: {
                        images = walkLeftImages;
                        nbImages = nbWalkFrame;
                        break;
                    }
                    case DIRECTION_EAST: {
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
        }
    }
}