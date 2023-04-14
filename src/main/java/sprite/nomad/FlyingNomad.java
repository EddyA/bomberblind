package sprite.nomad;

import java.awt.Image;
import java.util.Random;
import exceptions.SpriteActionException;
import lombok.Getter;
import sprite.SpriteAction;
import static sprite.SpriteAction.ACTION_DYING;
import static sprite.SpriteAction.ACTION_FLYING;
import sprite.SpriteType;
import utils.Direction;
import utils.Tuple2;

/**
 * Abstract class of a flying nomad.
 */
public abstract class FlyingNomad extends Nomad {

    @Getter
    private final Image[] flyBackImages;
    @Getter
    private final Image[] flyFrontImages;
    @Getter
    private final Image[] flyLeftImages;
    @Getter
    private final Image[] flyRightImages;
    @Getter
    private final int nbFlyFrame;

    @Getter
    private final int deviation; // the number of iterations before shifting to the orthogonal direction.
    @Getter
    private int moveIdx; // number of times the sprite has moved.

    @Getter
    private final Tuple2<Integer, Integer> lastCoordinatesOnMap; // test purpose.

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
    protected FlyingNomad(int xMap,
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
        super(xMap, yMap, SpriteType.TYPE_SPRITE_FLYING_NOMAD, refreshTime, actingTime, 0);
        this.flyBackImages = flyBackImages;
        this.flyFrontImages = flyFrontImages;
        this.flyLeftImages = flyLeftImages;
        this.flyRightImages = flyRightImages;
        this.nbFlyFrame = nbFlyFrame;
        this.deviation = deviation;

        moveIdx = 0;
        curImageIdx = new Random().nextInt(nbFlyFrame); // init the sprite with a random image index.
        lastCoordinatesOnMap = new Tuple2<>(xMap, yMap);

        // update cur/last - action/direction to avoid re-init curImageIdx to 0.
        curSpriteAction = ACTION_FLYING;
        lastSpriteAction = ACTION_FLYING;
        curDirection = direction;
        lastDirection = direction;
    }

    /**
     * Compute the next position of the sprite.
     */
    public void computeMove() {
        lastCoordinatesOnMap.setFirst(xMap);
        lastCoordinatesOnMap.setSecond(yMap);
        switch (curDirection) {
            case DIRECTION_NORTH -> {
                yMap--;
                if ((deviation != 0) && (moveIdx % deviation == 0)) {
                    if (deviation < 0) {
                        xMap--;
                    } else {
                        xMap++;
                    }
                }
            }
            case DIRECTION_SOUTH -> {
                yMap++;
                if ((deviation != 0) && (moveIdx % deviation == 0)) {
                    if (deviation < 0) {
                        xMap--;
                    } else {
                        xMap++;
                    }
                }
            }
            case DIRECTION_WEST -> {
                xMap--;
                if ((deviation != 0) && (moveIdx % deviation == 0)) {
                    if (deviation < 0) {
                        yMap--;
                    } else {
                        yMap++;
                    }
                }
            }
            case DIRECTION_EAST -> {
                xMap++;
                if ((deviation != 0) && (moveIdx % deviation == 0)) {
                    if (deviation < 0) {
                        yMap--;
                    } else {
                        yMap++;
                    }
                }
            }
        }
        moveIdx++;
    }

    @Override
    public boolean isActionAllowed(SpriteAction spriteAction) {
        return !(spriteAction != ACTION_FLYING &&
                spriteAction != ACTION_DYING);
    }

    @Override
    public boolean hasActionChanged() {
        if (!curSpriteAction.equals(lastSpriteAction) || // either the action has changed
                (curSpriteAction.equals(ACTION_FLYING) && // or (is flying
                        !curDirection.equals(lastDirection))) { // and the direction has changed).
            lastDirection = curDirection;
            lastRefreshTs = currentTimeSupplier.get().toEpochMilli(); // get the current time.
            return true;
        }
        return false;
    }

    @Override
    public void updateSprite() throws SpriteActionException {
        switch (curSpriteAction) {
            case ACTION_DYING -> {
                images = null;
                nbImages = 0;
            }
            case ACTION_FLYING -> {
                switch (curDirection) {
                    case DIRECTION_NORTH -> {
                        images = flyBackImages;
                        nbImages = nbFlyFrame;
                    }
                    case DIRECTION_SOUTH -> {
                        images = flyFrontImages;
                        nbImages = nbFlyFrame;
                    }
                    case DIRECTION_WEST -> {
                        images = flyLeftImages;
                        nbImages = nbFlyFrame;
                    }
                    case DIRECTION_EAST -> {
                        images = flyRightImages;
                        nbImages = nbFlyFrame;
                    }
                }
            }
            default -> throw new SpriteActionException(curSpriteAction, this.getClass());
        }
    }

    @Override
    public boolean isFinished() {
        return curSpriteAction.equals(ACTION_DYING);
    }
}
