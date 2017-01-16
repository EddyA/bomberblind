package sprite.settled;

import sprite.SpriteType;

import java.awt.*;

/**
 * Abstract class of a timed sprites.
 * The sprite loops during a certain duration.
 */
public abstract class TimedSettled extends LoopedSettled {

    private final int durationTime; // duration the sprite must loop (in ms).
    private long startTs; // start timestamp.

    /**
     * Create a timed settled.
     *
     * @param rowIdx       the map row index of the sprite
     * @param colIdx       the map col index of the sprite
     * @param spriteType   the sprite's type
     * @param refreshTime  the sprite refreshTime (i.e. fps)
     * @param images       the sprite's array of images
     * @param nbImages     the number of images of the array
     * @param durationTime the duration the sprite must loop
     */
    TimedSettled(int rowIdx,
                 int colIdx,
                 SpriteType spriteType,
                 int refreshTime,
                 Image[] images,
                 int nbImages,
                 int durationTime) {
        super(rowIdx,
                colIdx,
                spriteType,
                refreshTime,
                images,
                nbImages,
                0);
        this.durationTime = durationTime;
        this.startTs = currentTimeSupplier.get().toEpochMilli(); // start the sprite.
    }

    public int getDurationTime() {
        return durationTime;
    }

    public void setStartTs(long startTs) {
        this.startTs = startTs;
    }

    @Override
    public boolean updateStatus() {
        if (currentTimeSupplier.get().toEpochMilli() - startTs >= durationTime) {
            status = Status.STATUS_ENDED;
            return true;
        }
        return false;
    }
}