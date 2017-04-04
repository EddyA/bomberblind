package sprite.nomad;

import sprite.SpriteAction;
import sprite.SpriteType;
import utils.Direction;

import java.awt.*;

import static sprite.SpriteAction.ACTION_DYING;
import static sprite.SpriteAction.ACTION_WALKING;

/**
 * Abstract class of a walking enemy.
 */
public abstract class WalkingEnemy extends Nomad {

    protected final Image[] deathImages;
    protected final int nbDeathFrame;
    protected final int deathRefreshTime;
    protected final Image[] walkBackImages;
    protected final Image[] walkFrontImages;
    protected final Image[] walkLeftImages;
    protected final Image[] walkRightImages;
    protected final int nbWalkFrame;
    protected final int walkRefreshTime;

    /**
     * Create a walking enemy.
     *
     * @param xMap the abscissa on the map
     * @param yMap the ordinate on the map
     * @param deathImages the array of images for the "death" status (i.e. dying action)
     * @param nbDeathFrame the number of images of the "death" arrays
     * @param deathRefreshTime the sprite refresh time when dying (i.e. defining the sprite speed in term of image/sec)
     * @param walkBackImages the array of images for the "walk back" action
     * @param walkFrontImages the array of images for the "walk front" action
     * @param walkLeftImages the array of images for the "walk left" action
     * @param walkRightImages the array of images for the "walk right" action
     * @param nbWalkFrame the number of images of the "walk" arrays
     * @param walkRefreshTime the sprite refresh time when walking (i.e. defining the sprite speed in term of image/sec)
     * @param actingTime the sprite acting time (i.e. defining the sprite speed in term of action/sec)
     */
    public WalkingEnemy(int xMap,
                        int yMap,
                        Image[] deathImages,
                        int nbDeathFrame,
                        int deathRefreshTime,
                        Image[] walkBackImages,
                        Image[] walkFrontImages,
                        Image[] walkLeftImages,
                        Image[] walkRightImages,
                        int nbWalkFrame,
                        int walkRefreshTime,
                        int actingTime) {
        super(xMap, yMap, SpriteType.TYPE_SPRITE_WALKING_ENEMY, walkRefreshTime, actingTime, 0);
        this.deathImages = deathImages;
        this.nbDeathFrame = nbDeathFrame;
        this.deathRefreshTime = deathRefreshTime;
        this.walkBackImages = walkBackImages;
        this.walkFrontImages = walkFrontImages;
        this.walkLeftImages = walkLeftImages;
        this.walkRightImages = walkRightImages;
        this.nbWalkFrame = nbWalkFrame;
        this.walkRefreshTime = walkRefreshTime;

        curSpriteAction = ACTION_WALKING;
        curDirection = Direction.getRandomDirection(); // init the sprite with a random direction.
    }

    public Image[] getDeathImages() {
        return deathImages;
    }

    public int getNbDeathFrame() {
        return nbDeathFrame;
    }

    public int getDeathRefreshTime() {
        return deathRefreshTime;
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

    public int getWalkRefreshTime() {
        return walkRefreshTime;
    }

    @Override
    public boolean isActionAllowed(SpriteAction spriteAction) {
        return !(spriteAction != ACTION_WALKING &&
                spriteAction != ACTION_DYING);
    }

    @Override
    public boolean hasActionChanged() {
        if (!curSpriteAction.equals(lastSpriteAction) || // either the action has changed
                (curSpriteAction.equals(ACTION_WALKING) && // or (is walking
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
            case ACTION_DYING: {
                images = deathImages;
                nbImages = nbDeathFrame;
                refreshTime = deathRefreshTime;
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