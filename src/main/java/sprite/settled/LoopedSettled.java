package sprite.settled;

import java.awt.Image;
import lombok.Getter;
import lombok.Setter;
import sprite.Sprite;
import sprite.SpriteType;
import static sprite.settled.LoopedSettled.Status.STATUS_ALIVE;
import static sprite.settled.LoopedSettled.Status.STATUS_ENDED;
import utils.Tools;

/**
 * Abstract class of a looped sprites.
 * The sprite loops a certain number of times.
 */
public abstract class LoopedSettled extends Sprite {

    /**
     * enum the different available status of a looped settled.
     */
    public enum Status {
        STATUS_ALIVE, STATUS_ENDED
    }

    @Getter
    @Setter
    protected Status status = STATUS_ALIVE; // current status of the sprite.

    @Getter
    @Setter
    protected int rowIdx; // map row index of the sprite.
    @Getter
    @Setter
    protected int colIdx; // map column index of the sprite.

    @Getter
    private final int nbTimes; // number of times the sprite should be painted.
    @Getter
    @Setter
    protected int loopIdx; // number of times the sprite has looped.

    /**
     * Create a settled sprite.
     *
     * @param rowIdx      the map row index of the sprite
     * @param colIdx      the map col index of the sprite
     * @param spriteType  the sprite's type
     * @param refreshTime the sprite's refresh time (i.e. defining the fps)
     * @param images      the sprite's array of images
     * @param nbImages    the number of images composing the sprite
     * @param nbTimes     the max number of times the sprite must loop
     */
    protected LoopedSettled(int rowIdx,
                            int colIdx,
                            SpriteType spriteType,
                            int refreshTime,
                            Image[] images,
                            int nbImages,
                            int nbTimes) {
        super(Tools.getCaseCentreAbscissa(colIdx), Tools.getCaseBottomOrdinate(rowIdx), spriteType, refreshTime);
        this.rowIdx = rowIdx;
        this.colIdx = colIdx;
        this.images = images;
        this.nbImages = nbImages;
        this.nbTimes = nbTimes;
        this.loopIdx = 0;
    }

    /**
     * Update the sprite status.
     *
     * @return true if the status has changed, false otherwise.
     */
    public boolean updateStatus() {
        if ((loopIdx == nbTimes - 1) && (curImageIdx == nbImages - 1)) {
            status = STATUS_ENDED;
            return true;
        }
        return false;
    }

    @Override
    public void updateImage() {
        if (!updateStatus() && // if the sprite still alive.
                isTimeToRefresh()) { // it is time to refresh the sprite image.
            if (++curImageIdx == nbImages) {
                curImageIdx = 0;
                loopIdx++;
            }
            curImage = images[curImageIdx];
        }
    }

    @Override
    public boolean isFinished() {
        return status.equals(STATUS_ENDED);
    }
}
