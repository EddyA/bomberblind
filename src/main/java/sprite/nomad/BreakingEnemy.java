package sprite.nomad;

import sprite.SpriteType;

import java.awt.*;

import static utils.Action.ACTION_BREAKING;
import static utils.Action.ACTION_WALKING;

/**
 * Abstract class of an breaking enemy.
 */
public class BreakingEnemy extends WalkingEnemy {

    private final Image[] breakBackImages;
    private final Image[] breakFrontImages;
    private final Image[] breakLeftImages;
    private final Image[] breakRightImages;
    private final int nbBreakFrame;

    /**
     * Create an enemy.
     *
     * @param xMap             abscissa on the map
     * @param yMap             ordinate on the map
     * @param deathImages      the array of image for the "death" action
     * @param nbDeathFrame     the number of images of the "death" array
     * @param walkBackImages   the array of images for the "walk back" action
     * @param walkFrontImages  the array of images for the "walk front" action
     * @param walkLeftImages   the array of images for the "walk left" action
     * @param walkRightImages  the array of images for the "walk right" action
     * @param nbWalkFrame      number of images of the "walk" arrays
     * @param breakBackImages  the array of images for the "break back" action
     * @param breakFrontImages the array of images for the "break front" action
     * @param breakLeftImages  the array of images for the "break left" action
     * @param breakRightImages the array of images for the "break right" action
     * @param nbBreakFrame     number of images of the "break" arrays
     * @param refreshTime      the sprite refresh time (i.e. defining the sprite speed in term of image/sec)
     * @param actingTime       the sprite acting time (i.e. defining the sprite speed in term of action/sec)
     */
    public BreakingEnemy(int xMap,
                         int yMap,
                         Image[] deathImages,
                         int nbDeathFrame,
                         Image[] walkBackImages,
                         Image[] walkFrontImages,
                         Image[] walkLeftImages,
                         Image[] walkRightImages,
                         int nbWalkFrame,
                         Image[] breakBackImages,
                         Image[] breakFrontImages,
                         Image[] breakLeftImages,
                         Image[] breakRightImages,
                         int nbBreakFrame,
                         int refreshTime,
                         int actingTime) {
        super(xMap,
                yMap,
                deathImages,
                nbDeathFrame,
                walkBackImages,
                walkFrontImages,
                walkLeftImages,
                walkRightImages,
                nbWalkFrame,
                refreshTime,
                actingTime);
        this.setSpriteType(SpriteType.BREAKING_ENEMY); // override the type of sprite.
        this.breakBackImages = breakBackImages;
        this.breakFrontImages = breakFrontImages;
        this.breakLeftImages = breakLeftImages;
        this.breakRightImages = breakRightImages;
        this.nbBreakFrame = nbBreakFrame;
    }

    @Override
    public boolean hasActionChanged() {
        if (!curAction.equals(lastAction) || // either the action has changed
                (curAction.equals(ACTION_WALKING) && !curDirection.equals(lastDirection)) || // or walking to another direction.
                (curAction.equals(ACTION_BREAKING) && !curDirection.equals(lastDirection))) { // or breaking to another direction.
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
            case ACTION_BREAKING: {
                switch (curDirection) {
                    case NORTH: {
                        images = breakBackImages;
                        nbImages = nbBreakFrame;
                        break;
                    }
                    case SOUTH: {
                        images = breakFrontImages;
                        nbImages = nbBreakFrame;
                        break;
                    }
                    case WEST: {
                        images = breakLeftImages;
                        nbImages = nbBreakFrame;
                        break;
                    }
                    case EAST: {
                        images = breakRightImages;
                        nbImages = nbBreakFrame;
                        break;
                    }
                }
                break;
            }
        }
    }
}