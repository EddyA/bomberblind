package sprite.settled.abstracts;

import java.awt.Image;

import sprite.SpriteType;

/**
 * Abstract class of a timed sprites.
 * The sprite loops during a certain durationTime.
 */
public abstract class TimedSettled extends Settled {

    private final int durationTime; // durationTime the sprite must loop (in ms).
    private long startTs; // start timestamp.

    /**
     * Create a timed settled.
     *
     * @param rowIdx the map row index of the sprite
     * @param colIdx the map col index of the sprite
     * @param spriteType the sprite's type
     * @param refreshTime the sprite refreshTime (i.e. fps)
     * @param images the sprite's array of images
     * @param nbImages the number of images
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

    @Override
    public boolean isFinished() {
        return currentTimeSupplier.get().toEpochMilli() - startTs >= durationTime;
    }
}