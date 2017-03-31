package sprite.nomad;

import sprite.SpriteAction;
import sprite.SpriteType;

import java.awt.*;

import static sprite.SpriteAction.*;

/**
 * Abstract class of an exploring enemy.
 */
public class ExploringEnemy extends WalkingEnemy {

    private final Image[] exploreBackImages;
    private final Image[] exploreFrontImages;
    private final Image[] exploreLeftImages;
    private final Image[] exploreRightImages;
    private final int nbExploreFrame;
    private final int exploreRefreshTime;

    /**
     * Create a breaking enemy.
     *
     * @param xMap               the abscissa on the map
     * @param yMap               the ordinate on the map
     * @param deathImages        the array of images for the "death" status (i.e. dying action)
     * @param nbDeathFrame       the number of images of the "death" arrays
     * @param deathRefreshTime   the sprite refresh time when dying (i.e. defining the sprite speed in term of image/sec)
     * @param exploreBackImages  the array of images for the "explore back" action
     * @param exploreFrontImages the array of images for the "explore front" action
     * @param exploreLeftImages  the array of images for the "explore left" action
     * @param exploreRightImages the array of images for the "explore right" action
     * @param exploreRefreshTime the sprite refresh time when exploring (i.e. defining the sprite speed in term of image/sec)
     * @param nbExploreFrame     the number of images of the "explore" arrays
     * @param walkBackImages     the array of images for the "walk back" action
     * @param walkFrontImages    the array of images for the "walk front" action
     * @param walkLeftImages     the array of images for the "walk left" action
     * @param walkRightImages    the array of images for the "walk right" action
     * @param nbWalkFrame        the number of images of the "walk" arrays
     * @param walkRefreshTime    the sprite refresh time when walking (i.e. defining the sprite speed in term of image/sec)
     * @param actingTime         the sprite acting time (i.e. defining the sprite speed in term of action/sec)
     */
    public ExploringEnemy(int xMap,
                          int yMap,
                          Image[] deathImages,
                          int nbDeathFrame,
                          int deathRefreshTime,
                          Image[] exploreBackImages,
                          Image[] exploreFrontImages,
                          Image[] exploreLeftImages,
                          Image[] exploreRightImages,
                          int nbExploreFrame,
                          int exploreRefreshTime,
                          Image[] walkBackImages,
                          Image[] walkFrontImages,
                          Image[] walkLeftImages,
                          Image[] walkRightImages,
                          int nbWalkFrame,
                          int walkRefreshTime,
                          int actingTime) {
        super(xMap,
                yMap,
                deathImages,
                nbDeathFrame,
                deathRefreshTime,
                walkBackImages,
                walkFrontImages,
                walkLeftImages,
                walkRightImages,
                nbWalkFrame,
                walkRefreshTime,
                actingTime);
        this.setSpriteType(SpriteType.TYPE_EXPLORING_ENEMY); // override the type of sprite.
        this.exploreBackImages = exploreBackImages;
        this.exploreFrontImages = exploreFrontImages;
        this.exploreLeftImages = exploreLeftImages;
        this.exploreRightImages = exploreRightImages;
        this.nbExploreFrame = nbExploreFrame;
        this.exploreRefreshTime = exploreRefreshTime;
    }

    public Image[] getExploreBackImages() {
        return exploreBackImages;
    }

    public Image[] getExploreFrontImages() {
        return exploreFrontImages;
    }

    public Image[] getExploreLeftImages() {
        return exploreLeftImages;
    }

    public Image[] getExploreRightImages() {
        return exploreRightImages;
    }

    public int getNbExploreFrame() {
        return nbExploreFrame;
    }

    public int getExploreRefreshTime() {
        return exploreRefreshTime;
    }

    @Override
    public boolean isActionAllowed(SpriteAction spriteAction) {
        return !(spriteAction != ACTION_DYING &&
                spriteAction != ACTION_EXPLORING &&
                spriteAction != ACTION_WALKING);
    }

    @Override
    public boolean hasActionChanged() {
        if (!curSpriteAction.equals(lastSpriteAction) || // either the action has changed
                ((curSpriteAction.equals(ACTION_EXPLORING) || // or ((is exploring
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
            case ACTION_DYING: {
                images = deathImages;
                nbImages = nbDeathFrame;
                refreshTime = deathRefreshTime;
                break;
            }
            case ACTION_EXPLORING: {
                switch (curDirection) {
                    case DIRECTION_NORTH: {
                        images = exploreBackImages;
                        nbImages = nbExploreFrame;
                        refreshTime = exploreRefreshTime;
                        break;
                    }
                    case DIRECTION_SOUTH: {
                        images = exploreFrontImages;
                        nbImages = nbExploreFrame;
                        refreshTime = exploreRefreshTime;
                        break;
                    }
                    case DIRECTION_WEST: {
                        images = exploreLeftImages;
                        nbImages = nbExploreFrame;
                        refreshTime = exploreRefreshTime;
                        break;
                    }
                    case DIRECTION_EAST: {
                        images = exploreRightImages;
                        nbImages = nbExploreFrame;
                        refreshTime = exploreRefreshTime;
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
