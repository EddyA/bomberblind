package sprite.nomad;

import static utils.Action.ACTION_DYING;
import static utils.Action.ACTION_FLYING;

import java.awt.Image;
import java.util.Random;

import sprite.SpriteType;
import utils.Direction;

/**
 * Abstract class of a flying nomad.
 */
public abstract class FlyingNomad extends Nomad {

    private final Image[] flyBackImages;
    private final Image[] flyFrontImages;
    private final Image[] flyLeftImages;
    private final Image[] flyRightImages;
    private final int nbFlyFrame;

    private int deviation; // the number of iterations before shifting to the orthogonal direction.
    private int moveIdx; // number of times the sprite has moved.

    /**
     * Create a flying figure..
     *
     * @param xMap the abscissa on the map
     * @param yMap the ordinate on the map
     * @param flyBackImages the array of images for the "fly back" action
     * @param flyFrontImages the array of images for the "fly front" action
     * @param flyLeftImages the array of images for the "fly left" action
     * @param flyRightImages the array of images for the "fly right" action
     * @param nbFlyFrame the number of images of the "fly" arrays
     * @param direction the sprite direction.
     * @param deviation the sprite deviation.
     * @param refreshTime the sprite refresh time (i.e. defining the sprite speed in term of image/sec)
     * @param actingTime the sprite acting time (i.e. defining the sprite speed in term of action/sec)
     */
    public FlyingNomad(int xMap,
            int yMap,
            Image[] flyBackImages,
            Image[] flyFrontImages,
            Image[] flyLeftImages,
            Image[] flyRightImages,
            int nbFlyFrame,
            Direction direction,
            int deviation,
            int refreshTime,
            int actingTime) {
        super(xMap, yMap, SpriteType.BIRD, refreshTime, actingTime, 0);
        this.flyFrontImages = flyFrontImages;
        this.flyBackImages = flyBackImages;
        this.flyLeftImages = flyLeftImages;
        this.flyRightImages = flyRightImages;
        this.nbFlyFrame = nbFlyFrame;
        this.deviation = deviation;

        // update cur/last - action/direction to avoid re-init curImageIdx to 0.
        curAction = ACTION_FLYING;
        lastAction = ACTION_FLYING;
        curDirection = direction;
        lastDirection = direction;
        moveIdx = 0;
        curImageIdx = new Random().nextInt(nbFlyFrame); // init the sprite with a random image index.
    }

    /**
     * Compute the next position of the sprite.
     */
    public void computeMove() {
        switch (curDirection) {
        case NORTH: {
            yMap--;
            if (moveIdx % deviation == 0) {
                if (deviation < 0) {
                    xMap--;
                } else if (deviation > 0) {
                    xMap++;
                }
            }
            break;
        }
        case SOUTH: {
            yMap++;
            if (moveIdx % deviation == 0) {
                if (deviation < 0) {
                    xMap--;
                } else if (deviation > 0) {
                    xMap++;
                }
            }
            break;
        }
        case WEST: {
            xMap--;
            if (moveIdx % deviation == 0) {
                if (deviation < 0) {
                    yMap--;
                } else if (deviation > 0)
                    yMap++;
            }
            break;
        }
        case EAST: {
            xMap++;
            if (moveIdx % deviation == 0) {
                if (deviation < 0) {
                    yMap--;
                } else if (deviation > 0)
                    yMap++;
            }
            break;
        }
        }
        moveIdx++;
    }

    @Override
    public boolean hasActionChanged() {
        if (!curAction.equals(lastAction) || // either the action has changed
                (curAction.equals(ACTION_FLYING) && !curDirection.equals(lastDirection))) { // or flying to another
                                                                                            // direction.
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
        case ACTION_FLYING: {
            switch (curDirection) {
            case NORTH: {
                images = flyBackImages;
                nbImages = nbFlyFrame;
                break;
            }
            case SOUTH: {
                images = flyFrontImages;
                nbImages = nbFlyFrame;
                break;
            }
            case WEST: {
                images = flyLeftImages;
                nbImages = nbFlyFrame;
                break;
            }
            case EAST: {
                images = flyRightImages;
                nbImages = nbFlyFrame;
                break;
            }
            }
            break;
        }
        }
    }

    @Override
    public boolean isFinished() {
        return curAction.equals(ACTION_DYING);
    }
}
