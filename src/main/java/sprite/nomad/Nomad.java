package sprite.nomad;

import exceptions.SpriteActionException;
import lombok.Getter;
import lombok.Setter;
import sprite.Sprite;
import sprite.SpriteAction;
import static sprite.SpriteAction.ACTION_DYING;
import sprite.SpriteType;
import utils.Direction;

/**
 * Abstract class of a nomad.
 */
public abstract class Nomad extends Sprite {

    @Getter
    protected SpriteAction curSpriteAction; // current action.
    @Getter
    protected SpriteAction lastSpriteAction; // last action.
    @Getter
    @Setter
    protected Direction curDirection; // current direction.
    @Getter
    @Setter
    protected Direction lastDirection; // last direction.

    @Getter
    @Setter
    private int actingTime; // acting time (in ms, defining the sprite's speed in term of action/sec).
    @Setter
    protected long lastActionTs; // last action timestamp.

    @Getter
    @Setter
    protected boolean paintedAtLeastOneTime; // to notice the current action has been painted at least 1 time.

    @Getter
    private final int invincibilityTime; // invincibility time (in ms).
    @Getter
    @Setter
    protected long lastInvincibilityTs; // last invincibility timestamp.
    private int invincibleFrameIdx; // current invincible frame index.

    /**
     * Create a nomad.
     *
     * @param xMap        abscissa on the map.
     * @param yMap        ordinate on the map.
     * @param spriteType  the sprite's type
     * @param refreshTime the sprite refresh time (i.e. defining the sprite's speed in term of image/sec)
     * @param actingTime  the sprite acting time (i.e. defining the sprite's speed in term of action/sec)
     */
    protected Nomad(int xMap,
        int yMap,
        SpriteType spriteType,
        int refreshTime,
        int actingTime,
        int invincibilityTime) {
        super(xMap,
            yMap,
            spriteType,
            refreshTime);
        this.actingTime = actingTime;
        this.invincibilityTime = invincibilityTime;
    }

    public void setCurSpriteAction(SpriteAction curSpriteAction) {
        if (!isActionAllowed(curSpriteAction)) {
            throw new SpriteActionException(curSpriteAction);
        }
        this.curSpriteAction = curSpriteAction;
    }

    public void setLastSpriteAction(SpriteAction lastSpriteAction) {
        if (!isActionAllowed(lastSpriteAction)) {
            throw new SpriteActionException(lastSpriteAction);
        }
        this.lastSpriteAction = lastSpriteAction;
    }

    /**
     * This function is used to handle sprite's speed - in term of action/sec.
     * It computes the elapsed time since the last sprite's action
     * and return true if it should act again, false oterhwise.
     *
     * @return true if the sprite should act, false oterhwise.
     */
    public boolean isTimeToAct() {
        long curTs = currentTimeSupplier.get().toEpochMilli(); // get the current time.
        if (curTs - lastActionTs >= actingTime) { // it is time to act.
            lastActionTs = curTs;
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return true if the sprite is invincible, false otherwise.
     */
    public boolean isInvincible() {
        return lastInvincibilityTs + invincibilityTime >= currentTimeSupplier.get().toEpochMilli();
    }

    /**
     * Set the noamd as invincible.
     */
    public void setInvincible() {
        lastInvincibilityTs = currentTimeSupplier.get().toEpochMilli();
    }

    /**
     * Is an action allowed in the class.
     *
     * @param spriteAction the relative action
     * @return true if the action is allowed, false otherwise.
     */
    public abstract boolean isActionAllowed(SpriteAction spriteAction);

    /**
     * @return true if the current action has changed, false otherwise.
     */
    public abstract boolean hasActionChanged();

    /**
     * Update the sprite's image according to the current sprite's action.
     */
    public abstract void updateSprite() throws SpriteActionException;

    @Override
    public void updateImage() {
        updateSprite();
        if ((hasActionChanged() && // the action has changed
            !(paintedAtLeastOneTime = false)) || // (just to re-init this variable when the action has changed).
            (isTimeToRefresh() && // OR (it is time to refresh
                (++curImageIdx == nbImages && // AND it is the end of the sprite - the image index is ++ here).
                    (paintedAtLeastOneTime = true)))) { // (just to notice the sprite has been painted once).
            curImageIdx = 0;
        }
        if (curSpriteAction == ACTION_DYING && // the current action is dying
            images == null && nbImages == 0) { // AND there is no related sprite.
            curImage = null;
        } else if (isInvincible() && invincibleFrameIdx++ % 240 > 120) { // (quick and dirty) handle invincibility.
            curImage = null;
        } else {
            curImage = images[curImageIdx];
        }
    }

    @Override
    public boolean isFinished() {
        return curSpriteAction.equals(ACTION_DYING) && paintedAtLeastOneTime;
    }
}
