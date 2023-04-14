package sprite.nomad;

import java.awt.Image;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import exceptions.SpriteActionException;
import lombok.Getter;
import lombok.Setter;
import sprite.SpriteAction;
import static sprite.SpriteAction.ACTION_DYING;
import static sprite.SpriteAction.ACTION_WAITING;
import static sprite.SpriteAction.ACTION_WALKING;
import static sprite.SpriteAction.ACTION_WINING;
import sprite.SpriteType;
import sprite.settled.Bomb;
import sprite.settled.BonusBundle;
import sprite.settled.BonusType;

/**
 * Abstract class of a bomber.
 */
public abstract class Bomber extends Nomad {

    public static final int DEFAULT_ACTING_TIME = 10;

    @Getter
    private final Image[] deathImages;
    @Getter
    private final int nbDeathFrame;
    @Getter
    private final Image[] waitImages;
    @Getter
    private final int nbWaitFrame;
    @Getter
    private final Image[] walkBackImages;
    @Getter
    private final Image[] walkFrontImages;
    @Getter
    private final Image[] walkLeftImages;
    @Getter
    private final Image[] walkRightImages;
    @Getter
    private final int nbWalkFrame;
    @Getter
    private final Image[] winImages;
    @Getter
    private final int nbWinFrame;

    @Getter
    @Setter
    private int initialXMap; // initial abscissa on map.
    @Getter
    @Setter
    private int initialYMap; // initial ordinate on map.

    @Getter
    private final List<Bomb> droppedBombs; // array of dropped bombs.

    private final BonusBundle bundleBonus; // handle bonus.

    /**
     * Create a bomber.
     *
     * @param xMap              abscissa on the map
     * @param yMap              ordinate on the map
     * @param deathImages       the array of image for the "death" action
     * @param nbDeathFrame      the number of images of the "death" array
     * @param waitImages        the array of image for the "wait" action
     * @param nbWaitFrame       the number of images of the "wait" array
     * @param walkBackImages    the array of images for the "walk back" action
     * @param walkFrontImages   the array of images for the "walk front" action
     * @param walkLeftImages    the array of images for the "walk left" action
     * @param walkRightImages   the array of images for the "walk right" action
     * @param nbWalkFrame       number of images of the "walk" arrays
     * @param winImages         the array of image for the "win" action
     * @param nbWinFrame        the number of images of the "win" array
     * @param refreshTime       the sprite refresh time (i.e. defining the sprite speed in term of image/sec)
     * @param invincibilityTime the sprite invincibility time
     */
    protected Bomber(int xMap,
                     int yMap,
                     Image[] deathImages,
                     int nbDeathFrame,
                     Image[] waitImages,
                     int nbWaitFrame,
                     Image[] walkBackImages,
                     Image[] walkFrontImages,
                     Image[] walkLeftImages,
                     Image[] walkRightImages,
                     int nbWalkFrame,
                     Image[] winImages,
                     int nbWinFrame,
                     int refreshTime,
                     int invincibilityTime) {
        super(xMap,
            yMap,
            SpriteType.TYPE_SPRITE_BOMBER,
            DEFAULT_ACTING_TIME,
            refreshTime,
            invincibilityTime);
        this.deathImages = deathImages;
        this.nbDeathFrame = nbDeathFrame;
        this.waitImages = waitImages;
        this.nbWaitFrame = nbWaitFrame;
        this.walkBackImages = walkBackImages;
        this.walkFrontImages = walkFrontImages;
        this.walkLeftImages = walkLeftImages;
        this.walkRightImages = walkRightImages;
        this.nbWalkFrame = nbWalkFrame;
        this.winImages = winImages;
        this.nbWinFrame = nbWinFrame;
        this.refreshTime = refreshTime;
        initialXMap = xMap;
        initialYMap = yMap;
        droppedBombs = new LinkedList<>();
        bundleBonus = new BonusBundle();
        init();
    }

    public void dropBomb(Bomb bomb) {
        droppedBombs.add(bomb);
    }

    /**
     * Get the number of (not finished) bombs and remove finished bombs from the list of dropped bombs.
     */
    public int getNbDroppedBomb() {
        int nbDroppedBombs = 0;
        for (ListIterator<Bomb> iterator = droppedBombs.listIterator(); iterator.hasNext(); ) {
            Bomb bomb = iterator.next();
            if (!bomb.isFinished()) {
                nbDroppedBombs++;
            } else {
                iterator.remove();
            }
        }
        return nbDroppedBombs;
    }

    /**
     * Get the number of bonus typed 'bonusType'.
     *
     * @param bonusType the bonus type
     * @return the number of bonus typed 'bonusType'
     */
    public int getBonus(BonusType bonusType) {
        return bundleBonus.getBonus(bonusType);
    }

    /**
     * Set the number of bonus typed 'bonusType'.
     *
     * @param bonusType the bonus type
     * @param nbBonus   the number of bonus typed 'bonusType'
     */
    public void setBonus(BonusType bonusType, int nbBonus) {
        bundleBonus.setBonus(bonusType, nbBonus);
        if (bonusType == BonusType.TYPE_BONUS_ROLLER) {
            if (DEFAULT_ACTING_TIME - bundleBonus.getBonus(BonusType.TYPE_BONUS_ROLLER) < 4) { // put a limit to 4ms.
                setActingTime(4);
            } else {
                setActingTime(DEFAULT_ACTING_TIME - bundleBonus.getBonus(BonusType.TYPE_BONUS_ROLLER));
            }
        }
    }

    /**
     * Get the number of collected bonus with:
     * - key: the type of bonus
     * - value: the number of collected bonus
     * <p>
     * Note: this function does not take into account heart bonus.
     */
    public Map<BonusType, Integer> getCollectedBonus() {
        return bundleBonus.getCollectedBonus();
    }

    /**
     * This function is mainly used to process the bomber after he died.
     *
     * @return true if the bomber is definitively dead, false otherwise.
     */
    public boolean init() {
        if (bundleBonus.getBonus(BonusType.TYPE_BONUS_HEART) > 0) {
            xMap = initialXMap;
            yMap = initialYMap;
            curSpriteAction = ACTION_WAITING;
            bundleBonus.resetBonus();
            setActingTime(DEFAULT_ACTING_TIME);
            setInvincible(); // activate invincibility.
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean isActionAllowed(SpriteAction spriteAction) {
        return !(spriteAction != ACTION_WAITING &&
            spriteAction != ACTION_WALKING &&
            spriteAction != ACTION_WINING &&
            spriteAction != ACTION_DYING);
    }

    @Override
    public boolean hasActionChanged() {
        if (!curSpriteAction.equals(lastSpriteAction) || // either the action has changed
            (curSpriteAction.equals(ACTION_WALKING) && // or (is walking
                !curDirection.equals(lastDirection))) { // and the direction has changed).
            lastSpriteAction = curSpriteAction;
            lastDirection = curDirection;
            lastRefreshTs = currentTimeSupplier.get().toEpochMilli();
            return true;
        }
        return false;
    }

    @Override
    public void updateSprite() {
        switch (curSpriteAction) {
            case ACTION_DYING -> {
                images = deathImages;
                nbImages = nbDeathFrame;
            }
            case ACTION_WAITING -> {
                images = waitImages;
                nbImages = nbWaitFrame;
            }
            case ACTION_WALKING -> {
                switch (curDirection) {
                    case DIRECTION_NORTH -> {
                        images = walkBackImages;
                        nbImages = nbWalkFrame;
                    }
                    case DIRECTION_SOUTH -> {
                        images = walkFrontImages;
                        nbImages = nbWalkFrame;
                    }
                    case DIRECTION_WEST -> {
                        images = walkLeftImages;
                        nbImages = nbWalkFrame;
                    }
                    case DIRECTION_EAST -> {
                        images = walkRightImages;
                        nbImages = nbWalkFrame;
                    }
                }
            }
            case ACTION_WINING -> {
                images = winImages;
                nbImages = nbWinFrame;
            }
            default -> throw new SpriteActionException(curSpriteAction, this.getClass());
        }
    }
}
