package sprite.nomad;

import sprite.Sprite;
import sprite.SpriteAction;
import sprite.SpriteType;
import utils.Direction;

import static sprite.SpriteAction.ACTION_DYING;

/**
 * Abstract class of a nomad.
 */
public abstract class Nomad extends Sprite {

    protected SpriteAction curSpriteAction; // current action.
    protected SpriteAction lastSpriteAction; // last action.
    protected Direction curDirection; // current direction.
    protected Direction lastDirection; // last direction.

    private int actingTime; // acting time (in ms, defining the sprite's speed in term of action/sec).
    protected long lastActionTs; // last action timestamp.

    protected boolean paintedAtLeastOneTime; // to notice the current action has been painted at least 1 time.

    private final int invincibilityTime; // invincibility time (in ms).
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
    public Nomad(int xMap,
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

    public SpriteAction getCurSpriteAction() {
        return curSpriteAction;
    }

    public void setCurSpriteAction(SpriteAction curSpriteAction) {
        if (!isActionAllowed(curSpriteAction)) {
            String msg = "'" + SpriteAction.getlabel(curSpriteAction).orElse("no_name")
                    + "' action is not allowed here.";
            throw new RuntimeException(msg);
        }
        this.curSpriteAction = curSpriteAction;
    }

    public void setLastSpriteAction(SpriteAction lastSpriteAction) {
        if (!isActionAllowed(lastSpriteAction)) {
            String msg = "'" + SpriteAction.getlabel(lastSpriteAction).orElse("no_name")
                    + "' action is not allowed here.";
            throw new RuntimeException(msg);
        }
        this.lastSpriteAction = lastSpriteAction;
    }

    public Direction getCurDirection() {
        return curDirection;
    }

    public void setCurDirection(Direction curDirection) {
        this.curDirection = curDirection;
    }

    public Direction getLastDirection() {
        return lastDirection;
    }

    public void setLastDirection(Direction lastDirection) {
        this.lastDirection = lastDirection;
    }

    public int getActingTime() {
        return actingTime;
    }

    public void setActingTime(int actingTime) {
        this.actingTime = actingTime;
    }

    public SpriteAction getLastSpriteAction() {
        return lastSpriteAction;
    }

    public void setLastActionTs(long lastActionTs) {
        this.lastActionTs = lastActionTs;
    }

    public boolean isPaintedAtLeastOneTime() {
        return paintedAtLeastOneTime;
    }

    public void setPaintedAtLeastOneTime(boolean paintedAtLeastOneTime) {
        this.paintedAtLeastOneTime = paintedAtLeastOneTime;
    }

    public int getInvincibilityTime() {
        return invincibilityTime;
    }

    public long getLastInvincibilityTs() {
        return lastInvincibilityTs;
    }

    public void setLastInvincibilityTs(long lastInvincibilityTs) {
        this.lastInvincibilityTs = lastInvincibilityTs;
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
    public abstract void updateSprite();

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