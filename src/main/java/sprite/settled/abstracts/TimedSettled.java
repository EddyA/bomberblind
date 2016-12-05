package sprite.settled.abstracts;

import sprite.SpriteType;

import java.awt.*;

import static sprite.settled.abstracts.TimedSettled.Status.STATUS_ALIVE;

/**
 * Abstract class of a timed sprites.
 * The sprite loops during a certain durationTime.
 */
public abstract class TimedSettled extends Settled {

    /**
     * enum the different available action of a timed settled.
     */
    public enum Status {
        STATUS_ALIVE, STATUS_ENDED
    }

    private final int durationTime; // durationTime the sprite must loop (in ms).
    private long startTs; // start timestamp.

    private Status curStatus = STATUS_ALIVE; // current status of the sprite.

    /**
     * Create a timed settled.
     *
     * @param rowIdx       the map row index of the sprite
     * @param colIdx       the map col index of the sprite
     * @param spriteType   the sprite's type
     * @param refreshTime  the sprite refreshTime (i.e. fps)
     * @param images       the sprite's array of images
     * @param nbImages     the number of images
     * @param durationTime the durationTime the sprite must loop
     */
    public TimedSettled(int rowIdx, int colIdx, SpriteType spriteType, int refreshTime, Image[] images, int nbImages,
                        int durationTime) {
        super(rowIdx, colIdx, spriteType, refreshTime, images, nbImages);
        this.durationTime = durationTime;
        this.startTs = currentTimeSupplier.get().toEpochMilli(); // get the current durationTime.
    }

    public int getDurationTime() {
        return durationTime;
    }

    public void setStartTs(long startTs) {
        this.startTs = startTs;
    }

    public Status getCurStatus() {
        return curStatus;
    }

    public void setCurStatus(Status curStatus) {
        this.curStatus = curStatus;
    }

    @Override
    public boolean updateStatus() {
        if (currentTimeSupplier.get().toEpochMilli() - startTs >= durationTime) {
            curStatus = Status.STATUS_ENDED;
            return true;
        }
        return false;
    }

    @Override
    public boolean isFinished() {
        return curStatus.equals(Status.STATUS_ENDED);
    }
}