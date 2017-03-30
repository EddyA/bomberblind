package sprite.nomad;

import map.MapPoint;
import sprite.SpriteAction;
import sprite.SpriteType;

import java.awt.*;

import static sprite.SpriteAction.*;

/**
 * Abstract class of a breaking enemy.
 */
public class BreakingEnemy extends WalkingEnemy {

    private final Image[] breakBackImages;
    private final Image[] breakFrontImages;
    private final Image[] breakLeftImages;
    private final Image[] breakRightImages;
    private final int nbBreakFrame;
    private final int breakRefreshTime;

    private MapPoint breakingMapPoint = null; // the current MapPoint the enemy is breaking.

    /**
     * Create a breaking enemy.
     *
     * @param xMap             the abscissa on the map
     * @param yMap             the ordinate on the map
     * @param breakBackImages  the array of images for the "break back" action
     * @param breakFrontImages the array of images for the "break front" action
     * @param breakLeftImages  the array of images for the "break left" action
     * @param breakRightImages the array of images for the "break right" action
     * @param nbBreakFrame     the number of images of the "break" arrays
     * @param breakRefreshTime the sprite refresh time when breaking (i.e. defining the sprite speed in term of image/sec)
     * @param walkBackImages   the array of images for the "walk back" action
     * @param walkFrontImages  the array of images for the "walk front" action
     * @param walkLeftImages   the array of images for the "walk left" action
     * @param walkRightImages  the array of images for the "walk right" action
     * @param nbWalkFrame      the number of images of the "walk" arrays
     * @param walkRefreshTime  the sprite refresh time when walking (i.e. defining the sprite speed in term of image/sec)
     * @param actingTime       the sprite acting time (i.e. defining the sprite speed in term of action/sec)
     */
    public BreakingEnemy(int xMap,
                         int yMap,
                         Image[] breakBackImages,
                         Image[] breakFrontImages,
                         Image[] breakLeftImages,
                         Image[] breakRightImages,
                         int nbBreakFrame,
                         int breakRefreshTime,
                         Image[] walkBackImages,
                         Image[] walkFrontImages,
                         Image[] walkLeftImages,
                         Image[] walkRightImages,
                         int nbWalkFrame,
                         int walkRefreshTime,
                         int actingTime) {
        super(xMap,
                yMap,
                walkBackImages,
                walkFrontImages,
                walkLeftImages,
                walkRightImages,
                nbWalkFrame,
                walkRefreshTime,
                actingTime);
        this.setSpriteType(SpriteType.TYPE_BREAKING_ENEMY); // override the type of sprite.
        this.breakBackImages = breakBackImages;
        this.breakFrontImages = breakFrontImages;
        this.breakLeftImages = breakLeftImages;
        this.breakRightImages = breakRightImages;
        this.nbBreakFrame = nbBreakFrame;
        this.breakRefreshTime = breakRefreshTime;
    }

    public Image[] getBreakBackImages() {
        return breakBackImages;
    }

    public Image[] getBreakFrontImages() {
        return breakFrontImages;
    }

    public Image[] getBreakLeftImages() {
        return breakLeftImages;
    }

    public Image[] getBreakRightImages() {
        return breakRightImages;
    }

    public int getNbBreakFrame() {
        return nbBreakFrame;
    }

    public int getBreakRefreshTime() {
        return breakRefreshTime;
    }

    public MapPoint getBreakingMapPoint() {
        return breakingMapPoint;
    }

    public void setBreakingMapPoint(MapPoint breakingMapPoint) {
        this.breakingMapPoint = breakingMapPoint;
    }

    public boolean isBreakingSpriteFinished() {
        return curSpriteAction.equals(ACTION_BREAKING) && paintedAtLeastOneTime;
    }

    @Override
    public boolean isActionAllowed(SpriteAction spriteAction) {
        return !(spriteAction != ACTION_BREAKING &&
                spriteAction != ACTION_DYING &&
                spriteAction != ACTION_WALKING);
    }

    @Override
    public boolean hasActionChanged() {
        if (!curSpriteAction.equals(lastSpriteAction) || // either the action has changed
                ((curSpriteAction.equals(ACTION_BREAKING) || // or ((is breaking
                        curSpriteAction.equals(ACTION_WALKING)) && // or is walking)
                        !curDirection.equals(lastDirection))) { // and the direction has changed).
            lastSpriteAction = curSpriteAction;
            lastDirection = curDirection;
            lastRefreshTs = currentTimeSupplier.get().toEpochMilli(); // get the current time.
            return true;
        }
        return false;
    }

    @Override
    public void updateSprite() {
        switch (curSpriteAction) {
            case ACTION_BREAKING: {
                switch (curDirection) {
                    case DIRECTION_NORTH: {
                        images = breakBackImages;
                        nbImages = nbBreakFrame;
                        refreshTime = breakRefreshTime;
                        break;
                    }
                    case DIRECTION_SOUTH: {
                        images = breakFrontImages;
                        nbImages = nbBreakFrame;
                        refreshTime = breakRefreshTime;
                        break;
                    }
                    case DIRECTION_WEST: {
                        images = breakLeftImages;
                        nbImages = nbBreakFrame;
                        refreshTime = breakRefreshTime;
                        break;
                    }
                    case DIRECTION_EAST: {
                        images = breakRightImages;
                        nbImages = nbBreakFrame;
                        refreshTime = breakRefreshTime;
                        break;
                    }
                }
                break;
            }
            case ACTION_WALKING: {
                switch (curDirection) {
                    case DIRECTION_NORTH: {
                        images = walkBackImages;
                        nbImages = nbWalkFrame;
                        refreshTime = walkRefreshTime;
                        break;
                    }
                    case DIRECTION_SOUTH: {
                        images = walkFrontImages;
                        nbImages = nbWalkFrame;
                        refreshTime = walkRefreshTime;
                        break;
                    }
                    case DIRECTION_WEST: {
                        images = walkLeftImages;
                        nbImages = nbWalkFrame;
                        refreshTime = walkRefreshTime;
                        break;
                    }
                    case DIRECTION_EAST: {
                        images = walkRightImages;
                        nbImages = nbWalkFrame;
                        refreshTime = walkRefreshTime;
                        break;
                    }
                }
                break;
            }
        }
    }
}