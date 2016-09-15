package sprites.settled.abstracts;

import java.awt.*;

/**
 * Abstract class of a timed sprites.
 * The sprite loops during a certain time.
 */
public abstract class TimedSettled extends Settled {

    private final int time; // time the sprite must loop (in ms).
    private final long startTs; // start timestamp.

    /**
     * Create a timed settled.
     *
     * @param rowIdx      the map row index of the sprite
     * @param colIdx      the map col index of the sprite
     * @param refreshTime the sprite refreshTime (i.e. fps)
     * @param images      the sprite's array of images
     * @param nbImages    the number of images
     * @param time        the time the sprite must loop
     */
    public TimedSettled(int rowIdx,
                        int colIdx,
                        int refreshTime,
                        Image[] images,
                        int nbImages,
                        int time) {
        super(rowIdx, colIdx, refreshTime, images, nbImages);
        this.time = time;
        this.startTs = currentTimeSupplier.get().toEpochMilli(); // get the current time.
    }

    public int getTime() {
        return time;
    }

    public long getStartTs() {
        return startTs;
    }

    @Override
    public boolean isFinished() {
        return currentTimeSupplier.get().toEpochMilli() - startTs >= time;
    }
}