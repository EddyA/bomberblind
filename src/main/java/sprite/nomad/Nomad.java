package sprite.nomad;

import static utils.Action.ACTION_DYING;

import sprite.Sprite;
import sprite.SpriteType;
import utils.Action;
import utils.Direction;

/**
 * Abstract class of a nomad.
 */
public abstract class Nomad extends Sprite {

    protected Action curAction; // current action.
    protected Action lastAction; // last action.
    protected Direction curDirection; // current direction.
    protected Direction lastDirection; // last direction.

    private final int actingTime; // acting time (in ms, defining the sprite's speed in term of action/sec).
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

    public Action getCurAction() {
        return curAction;
    }

    public void setCurAction(Action curAction) {
        this.curAction = curAction;
    }

    public void setLastAction(Action lastAction) {
        this.lastAction = lastAction;
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

    public void setLastActionTs(long lastActionTs) {
        this.lastActionTs = lastActionTs;
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
     * Update the sprite's image according to the current sprite's action.
     */
    public abstract void updateSprite();

    /**
     * @return true if the current action has changed, false otherwise.
     */
    public abstract boolean hasActionChanged();

    @Override
    public void updateImage() {
        updateSprite();
        if ((hasActionChanged() && // the action has changed
                !(paintedAtLeastOneTime = false)) || // just to re-init this variable when the action has changed.
                (isTimeToRefresh() && // OR (it is time to refresh
                        (++curImageIdx == nbImages && // AND it is the end of the sprite - the image index is ++ here).
                                (paintedAtLeastOneTime = true)))) { // just to notice the sprite has been painted once.
            curImageIdx = 0;
        }
        if (isInvincible() &&
                invincibleFrameIdx++ % 240 > 120) {
            curImage = null;
        } else {
            curImage = images[curImageIdx];
        }
    }

    @Override
    public boolean isFinished() {
        return curAction.equals(ACTION_DYING) && paintedAtLeastOneTime;
    }
}