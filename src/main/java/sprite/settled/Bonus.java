package sprite.settled;

import sprite.Sprite;
import sprite.SpriteType;
import utils.Tools;

import java.awt.*;

import static sprite.settled.Bonus.Status.STATUS_ALIVE;
import static sprite.settled.Bonus.Status.STATUS_ENDED;

/**
 * Abstract class of a bonus.
 * The sprite loops until is ended.
 */
public abstract class Bonus extends Sprite {

    public final static int REFRESH_TIME = 50;

    private final BonusType bonusType;

    /**
     * enum the different available status of a bonus.
     */
    public enum Status {
        STATUS_ALIVE, STATUS_ENDED
    }

    private Status status = STATUS_ALIVE; // current status of the sprite.

    protected int rowIdx; // map row index of the sprite.
    protected int colIdx; // map column index of the sprite.

    /**
     * Create a bonus sprite.
     *
     * @param rowIdx     the map row index of the sprite
     * @param colIdx     the map col index of the sprite
     * @param bonusType  the bonus' type
     * @param images     the sprite's array of images
     * @param nbImages   the number of images composing the sprite
     */
    public Bonus(int rowIdx,
                 int colIdx,
                 BonusType bonusType,
                 Image[] images,
                 int nbImages) {
        super(Tools.getCaseCentreAbscissa(colIdx),
                Tools.getCaseBottomOrdinate(rowIdx),
                SpriteType.TYPE_BONUS,
                REFRESH_TIME);
        this.rowIdx = rowIdx;
        this.colIdx = colIdx;
        this.bonusType = bonusType;
        this.images = images;
        this.nbImages = nbImages;
    }

    public BonusType getBonusType() {
        return bonusType;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getRowIdx() {
        return rowIdx;
    }

    public void setRowIdx(int rowIdx) {
        this.rowIdx = rowIdx;
    }

    public int getColIdx() {
        return colIdx;
    }

    public void setColIdx(int colIdx) {
        this.colIdx = colIdx;
    }

    @Override
    public void updateImage() {
        if (isTimeToRefresh()) { // it is time to refresh the sprite image.
            if (++curImageIdx == nbImages) {
                curImageIdx = 0;
            }
            curImage = images[curImageIdx];
        }
    }

    @Override
    public boolean isFinished() {
        return status.equals(STATUS_ENDED);
    }
}
