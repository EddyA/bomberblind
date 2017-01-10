package sprite.nomad;

import sprite.SpriteType;

import java.awt.*;

import static utils.Action.*;

/**
 * Abstract class of a breaking enemy.
 */
public class BreakingEnemy extends WalkingEnemy {

    private final Image[] breakBackImages;
    private final Image[] breakFrontImages;
    private final Image[] breakLeftImages;
    private final Image[] breakRightImages;
    private final int nbBreakFrame;
    private final Image[] waitBackImages;
    private final Image[] waitFrontImages;
    private final Image[] waitLeftImages;
    private final Image[] waitRightImages;
    private final int nbWaitFrame;

    /**
     * Create an enemy.
     *
     * @param xMap             abscissa on the map
     * @param yMap             ordinate on the map
     * @param breakBackImages  the array of images for the "break back" action
     * @param breakFrontImages the array of images for the "break front" action
     * @param breakLeftImages  the array of images for the "break left" action
     * @param breakRightImages the array of images for the "break right" action
     * @param nbBreakFrame     number of images of the "break" arrays
     * @param deathImages      the array of image for the "death" action
     * @param nbDeathFrame     the number of images of the "death" array
     * @param waitBackImages   the array of images for the "wait back" action
     * @param waitFrontImages  the array of images for the "wait front" action
     * @param waitLeftImages   the array of images for the "wait left" action
     * @param waitRightImages  the array of images for the "wait right" action
     * @param nbwaitFrame      number of images of the "wait" arrays
     * @param walkBackImages   the array of images for the "walk back" action
     * @param walkFrontImages  the array of images for the "walk front" action
     * @param walkLeftImages   the array of images for the "walk left" action
     * @param walkRightImages  the array of images for the "walk right" action
     * @param nbWalkFrame      number of images of the "walk" arrays
     * @param refreshTime      the sprite refresh time (i.e. defining the sprite speed in term of image/sec)
     * @param actingTime       the sprite acting time (i.e. defining the sprite speed in term of action/sec)
     */
    public BreakingEnemy(int xMap,
                         int yMap,
                         Image[] breakBackImages,
                         Image[] breakFrontImages,
                         Image[] breakLeftImages,
                         Image[] breakRightImages,
                         int nbBreakFrame,
                         Image[] deathImages,
                         int nbDeathFrame,
                         Image[] waitBackImages,
                         Image[] waitFrontImages,
                         Image[] waitLeftImages,
                         Image[] waitRightImages,
                         int nbwaitFrame,
                         Image[] walkBackImages,
                         Image[] walkFrontImages,
                         Image[] walkLeftImages,
                         Image[] walkRightImages,
                         int nbWalkFrame,
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
        this.waitBackImages = waitBackImages;
        this.waitFrontImages = waitFrontImages;
        this.waitLeftImages = waitLeftImages;
        this.waitRightImages = waitRightImages;
        this.nbWaitFrame = nbwaitFrame;
    }

    @Override
    public boolean hasActionChanged() {
        if (!curAction.equals(lastAction) || // either the action has changed
                (curAction.equals(ACTION_BREAKING) && !curDirection.equals(lastDirection)) || // or walking to another direction.
                (curAction.equals(ACTION_WAITING) && !curDirection.equals(lastDirection)) || // or breaking  with another direction.
                (curAction.equals(ACTION_WALKING) && !curDirection.equals(lastDirection))) { // or breaking with another direction.
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
            case ACTION_DYING: {
                images = deathImages;
                nbImages = nbDeathFrame;
                break;
            }
            case ACTION_WAITING: {
                switch (curDirection) {
                    case NORTH: {
                        images = waitBackImages;
                        nbImages = nbWaitFrame;
                        break;
                    }
                    case SOUTH: {
                        images = waitFrontImages;
                        nbImages = nbWaitFrame;
                        break;
                    }
                    case WEST: {
                        images = waitLeftImages;
                        nbImages = nbWaitFrame;
                        break;
                    }
                    case EAST: {
                        images = waitRightImages;
                        nbImages = nbWaitFrame;
                        break;
                    }
                }
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