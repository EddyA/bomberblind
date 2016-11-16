package sprite.nomad.abstracts;

import static sprite.nomad.abstracts.Enemy.Action.ACTION_DYING;
import static sprite.nomad.abstracts.Enemy.Action.ACTION_WALKING;

import java.awt.Image;

import sprite.SpriteType;
import sprite.nomad.EnemyType;
import utils.Direction;

/**
 * Abstract class of an enemy.
 */
public abstract class Enemy extends Nomad {

    /**
     * enum the different available action of an enemy.
     */
    public enum Action {
        ACTION_DYING, ACTION_WALKING
    }

    private final EnemyType enemyType;
    private final Image[] deathImages;
    private final int nbDeathFrame;
    private final Image[] walkBackImages;
    private final Image[] walkFrontImages;
    private final Image[] walkLeftImages;
    private final Image[] walkRightImages;
    private final int nbWalkFrame;

    private Action curAction = ACTION_WALKING; // current action.
    private Action lastAction = curAction; // last action.
    private Direction curDirection = Direction.getRandomDirection(); // init the current direction with a random one.
    private Direction lastDirection = curDirection; // last direction.

    /**
     * Create an enemy.
     *
     * @param xMap abscissa on the map
     * @param yMap ordinate on the map
     * @param enemyType the enemy's type
     * @param deathImages the array of image for the "death" action
     * @param nbDeathFrame the number of images of the "death" array
     * @param walkBackImages the array of images for the "walk back" action
     * @param walkFrontImages the array of images for the "walk front" action
     * @param walkLeftImages the array of images for the "walk left" action
     * @param walkRightImages the array of images for the "walk right" action
     * @param nbWalkFrame number of images of the "walk" arrays
     * @param refreshTime the sprite refresh time (i.e. defining the image/sec)
     * @param moveTime the move time (i.e. defining the nomad move speed)
     */
    public Enemy(int xMap,
                 int yMap,
                 EnemyType enemyType,
                 Image[] deathImages,
                 int nbDeathFrame,
                 Image[] walkBackImages,
                 Image[] walkFrontImages,
                 Image[] walkLeftImages,
                 Image[] walkRightImages,
                 int nbWalkFrame,
                 int refreshTime,
                 int moveTime) {
        super(xMap, yMap, SpriteType.ENEMY, refreshTime, moveTime);
        this.enemyType = enemyType;
        this.deathImages = deathImages;
        this.nbDeathFrame = nbDeathFrame;
        this.walkBackImages = walkBackImages;
        this.walkFrontImages = walkFrontImages;
        this.walkLeftImages = walkLeftImages;
        this.walkRightImages = walkRightImages;
        this.nbWalkFrame = nbWalkFrame;
    }

    public EnemyType getEnemyType() {
        return enemyType;
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

    public Action getCurAction() {
        return curAction;
    }

    public void setCurAction(Action curAction) {
        this.curAction = curAction;
    }

    public Direction getCurDirection() {
        return curDirection;
    }

    public void setCurDirection(Direction curDirection) {
        this.curDirection = curDirection;
    }

    public Action getLastAction() {
        return lastAction;
    }

    public void setLastAction(Action lastAction) {
        this.lastAction = lastAction;
    }

    public void setLastDirection(Direction lastDirection) {
        this.lastDirection = lastDirection;
    }

    public boolean statusHasChanged() {
        return ((curAction != lastAction) ||
                (curAction == ACTION_WALKING && curDirection != lastDirection));
    }

    @Override
    public boolean updateStatus() {
        long curTs = currentTimeSupplier.get().toEpochMilli(); // get the current time.
        if ((statusHasChanged()) || // either the action has changed
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

    @Override
    public boolean isInvincible() {
        return false;
    }

    @Override
    public boolean isFinished() {
        return ((curAction == ACTION_DYING) && (curImageIdx == nbImages - 1));
    }
}
