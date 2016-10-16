package sprite.nomad.abstracts;

import static sprite.nomad.abstracts.Enemy.status.STATUS_DYING;
import static sprite.nomad.abstracts.Enemy.status.STATUS_WALKING_FRONT;

import java.awt.Image;
import java.util.Optional;

import sprite.SpriteType;
import utils.Direction;

/**
 * Abstract class of an enemy.
 */
public abstract class Enemy extends Nomad {

    /**
     * enum the different available status of an enemy.
     */
    public enum status {
        STATUS_DYING,
        STATUS_WALKING_BACK,
        STATUS_WALKING_FRONT,
        STATUS_WALKING_LEFT,
        STATUS_WALKING_RIGHT
    }

    private final Image[] deathImages;
    private final int nbDeathFrame;
    private final Image[] walkBackImages;
    private final Image[] walkFrontImages;
    private final Image[] walkLeftImages;
    private final Image[] walkRightImages;
    private final int nbWalkFrame;

    private status curStatus = STATUS_WALKING_FRONT; // current status.
    private status lastStatus = STATUS_WALKING_FRONT; // last status.

    /**
     * Create an enemy.
     *
     * @param xMap abscissa on the map
     * @param yMap ordinate on the map
     * @param spriteType the sprite's type
     * @param deathImages the array of image for the "death" status
     * @param nbDeathFrame the number of images of the "death" array
     * @param walkBackImages the array of images for the "walk back" status
     * @param walkFrontImages the array of images for the "walk front" status
     * @param walkLeftImages the array of images for the "walk left" status
     * @param walkRightImages the array of images for the "walk right" status
     * @param nbWalkFrame number of images of the "walk" arrays
     * @param refreshTime the sprite refresh time (i.e. defining the image/sec)
     * @param moveTime the move time (i.e. defining the nomad move speed)
     */
    public Enemy(int xMap,
                 int yMap,
                 SpriteType spriteType,
                 Image[] deathImages,
                 int nbDeathFrame,
                 Image[] walkBackImages,
                 Image[] walkFrontImages,
                 Image[] walkLeftImages,
                 Image[] walkRightImages,
                 int nbWalkFrame,
                 int refreshTime,
                 int moveTime) {
        super(xMap, yMap, spriteType, refreshTime, moveTime);
        this.deathImages = deathImages;
        this.nbDeathFrame = nbDeathFrame;
        this.walkBackImages = walkBackImages;
        this.walkFrontImages = walkFrontImages;
        this.walkLeftImages = walkLeftImages;
        this.walkRightImages = walkRightImages;
        this.nbWalkFrame = nbWalkFrame;
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

    public status getLastStatus() {
        return lastStatus;
    }

    public void setLastStatus(status lastStatus) {
        this.lastStatus = lastStatus;
    }

    public Enemy.status getCurStatus() {
        return curStatus;
    }

    public void setCurStatus(Enemy.status curStatus) {
        this.curStatus = curStatus;
    }

    /**
     * @return the current direction according to the current status.
     */
    public Optional<Direction> getCurDirection() {
        switch (curStatus) {
        case STATUS_WALKING_BACK:
            return Optional.of(Direction.NORTH);
        case STATUS_WALKING_FRONT:
            return Optional.of(Direction.SOUTH);
        case STATUS_WALKING_LEFT:
            return Optional.of(Direction.WEST);
        case STATUS_WALKING_RIGHT:
            return Optional.of(Direction.EAST);
        default:
            return Optional.empty();
        }
    }

    @Override
    public boolean updateStatus() {
        long curTs = currentTimeSupplier.get().toEpochMilli(); // get the current time.
        if ((curStatus != lastStatus) || // either the status has changed
                (lastRefreshTs == 0)) { // or it is the 1st call to that function.
            lastRefreshTs = curTs;
            lastStatus = curStatus;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void updateSprite() {
        switch (curStatus) {
            case STATUS_DYING: {
                images = deathImages;
                nbImages = nbDeathFrame;
                break;
            }
            case STATUS_WALKING_BACK: {
                images = walkBackImages;
                nbImages = nbWalkFrame;
                break;
            }
            case STATUS_WALKING_FRONT: {
                images = walkFrontImages;
                nbImages = nbWalkFrame;
                break;
            }
            case STATUS_WALKING_LEFT: {
                images = walkLeftImages;
                nbImages = nbWalkFrame;
                break;
            }
            case STATUS_WALKING_RIGHT: {
                images = walkRightImages;
                nbImages = nbWalkFrame;
                break;
            }
        }
    }

    @Override
    public boolean isInvincible() {
        return false;
    }

    @Override
    public boolean isFinished() {
        return ((curStatus == STATUS_DYING) && (curImageIdx == nbImages - 1));
    }
}
