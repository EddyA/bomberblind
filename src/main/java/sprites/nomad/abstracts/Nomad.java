package sprites.nomad.abstracts;

import sprites.Sprite;

import java.awt.*;

/**
 * Abstract class of a abstracts sprite.
 */
public abstract class Nomad extends Sprite {

    protected int moveTime; // move time (in ms).
    protected long lastMoveTs; // last move timestamp.

    public Nomad(int xMap, int yMap, int refreshTime, int moveTime) {
        super(xMap, yMap, refreshTime);
        this.moveTime = moveTime;
    }

    public int getMoveTime() {
        return moveTime;
    }

    public long getLastMoveTs() {
        return lastMoveTs;
    }

    public void setMoveTime(int moveTime) {
        this.moveTime = moveTime;
    }

    public void setLastMoveTs(long lastMoveTs) {
        this.lastMoveTs = lastMoveTs;
    }

    /**
     * This function is used to handle the sprite's speed - in term of move on map.
     * It computes the elapsed time since the sprite has moved and return true if it should move, false oterhwise.
     *
     * @return true if the sprite should move, false oterhwise.
     */
    public boolean shouldMove() {
        long curTs = currentTimeSupplier.get().toEpochMilli(); // get the current time.
        if (curTs - lastMoveTs >= moveTime) { // it is time to move.
            lastMoveTs = curTs;
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return true if the sprite is finished, false otherwise.
     */
    public abstract boolean isFinished();

    /**
     * @return the current image of the sprite.
     */
    public abstract Image getCurImage();

    /**
     * Update the sprite image.
     */
    public abstract void updateImage();
}
