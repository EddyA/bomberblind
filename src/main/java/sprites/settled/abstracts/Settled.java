package sprites.settled.abstracts;

import sprites.Sprite;
import utils.Tools;

import java.awt.*;

/**
 * Abstract class of a settled sprite.
 */
public abstract class Settled extends Sprite {

    private final int rowIdx; // map row index of the sprite.
    private final int colIdx; // map column index of the sprite.

    public Settled(int rowIdx, int colIdx, int refreshTime) {
        super(Tools.getCaseCentreAbscissa(colIdx), Tools.getCaseBottomOrdinate(rowIdx), refreshTime);
        this.rowIdx = rowIdx;
        this.colIdx = colIdx;
    }

    /**
     * @return the map row index of the sprite.
     */
    public synchronized int getRowIdx() {
        return rowIdx;
    }

    /**
     * @return the map column index of the sprite.
     */
    public synchronized int getColIdx() {
        return colIdx;
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