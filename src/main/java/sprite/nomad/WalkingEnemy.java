package sprite.nomad;

import sprite.SpriteType;
import utils.Direction;

import java.awt.*;

import static utils.Action.ACTION_WALKING;

/**
 * Abstract class of a walking enemy.
 */
public abstract class WalkingEnemy extends Nomad {

    protected final Image[] deathImages;
    protected final int nbDeathFrame;
    protected final Image[] walkBackImages;
    protected final Image[] walkFrontImages;
    protected final Image[] walkLeftImages;
    protected final Image[] walkRightImages;
    protected final int nbWalkFrame;

    /**
     * Create an enemy.
     *
     * @param xMap            abscissa on the map
     * @param yMap            ordinate on the map
     * @param deathImages     the array of image for the "death" action
     * @param nbDeathFrame    the number of images of the "death" array
     * @param walkBackImages  the array of images for the "walk back" action
     * @param walkFrontImages the array of images for the "walk front" action
     * @param walkLeftImages  the array of images for the "walk left" action
     * @param walkRightImages the array of images for the "walk right" action
     * @param nbWalkFrame     number of images of the "walk" arrays
     * @param refreshTime     the sprite refresh time (i.e. defining the sprite speed in term of image/sec)
     * @param actingTime      the sprite acting time (i.e. defining the sprite speed in term of action/sec)
     */
    public WalkingEnemy(int xMap,
                        int yMap,
                        Image[] deathImages,
                        int nbDeathFrame,
                        Image[] walkBackImages,
                        Image[] walkFrontImages,
                        Image[] walkLeftImages,
                        Image[] walkRightImages,
                        int nbWalkFrame,
                        int refreshTime,
                        int actingTime) {
        super(xMap, yMap, SpriteType.WALKING_ENEMY, refreshTime, actingTime, 0);
        this.deathImages = deathImages;
        this.nbDeathFrame = nbDeathFrame;
        this.walkBackImages = walkBackImages;
        this.walkFrontImages = walkFrontImages;
        this.walkLeftImages = walkLeftImages;
        this.walkRightImages = walkRightImages;
        this.nbWalkFrame = nbWalkFrame;

        curAction = ACTION_WALKING;
        curDirection = Direction.getRandomDirection(); // init the sprite with a random direction.
    }

    public Image[] getDeathImages() {
        return deathImages;
    }

    public int getNbDeathFrame() {
        return nbDeathFrame;
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

    @Override
    public boolean hasActionChanged() {
        if (!curAction.equals(lastAction) || // either the action has changed
                (curAction.equals(ACTION_WALKING) && !curDirection.equals(lastDirection))) { // or the direction has changed.
            lastAction = curAction;
            lastDirection = curDirection;
            lastRefreshTs = currentTimeSupplier.get().toEpochMilli(); // get the current time.
            return true;
        }
        return false;
    }

    @Override
    public void updateSprite() {
        switch (curAction) {
            case ACTION_DYING: {
                images = deathImages;
                nbImages = nbDeathFrame;
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
        }
    }
}