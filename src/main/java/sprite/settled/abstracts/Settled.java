package sprite.settled.abstracts;

import sprite.SpriteType;
import sprite.abstracts.Sprite;
import utils.Tools;

import java.awt.*;

/**
 * Abstract class of a settled sprite.
 */
public abstract class Settled extends Sprite {

    private int rowIdx; // map row index of the sprite (not private for test purpose).
    private int colIdx; // map column index of the sprite (not private for test purpose).

    private final Image[] images; // array of images of the sprite.
    private final int nbImages; // number of images of the sprite.
    private int curImageIdx; // current image index of the sprite.
    private Image curImage; // current image of the sprite.

    private int curLoopIdx; // current number of times.

    /**
     * Create a settled sprite.
     *
     * @param rowIdx      the map row index of the sprite
     * @param colIdx      the map col index of the sprite
     * @param spriteType  the sprite's type
     * @param refreshTime the sprite refresh time (i.e. defining the fps)
     * @param images      the sprite's array of images
     * @param nbImages    the number of images
     */
    public Settled(int rowIdx, int colIdx, SpriteType spriteType, int refreshTime, Image[] images, int nbImages) {
        super(Tools.getCaseCentreAbscissa(colIdx), Tools.getCaseBottomOrdinate(rowIdx), spriteType, refreshTime);
        this.rowIdx = rowIdx;
        this.colIdx = colIdx;
        this.images = images;
        this.nbImages = nbImages;
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

    public Image[] getImages() {
        return images;
    }

    public int getNbImages() {
        return nbImages;
    }

    public int getCurImageIdx() {
        return curImageIdx;
    }

    public int getCurLoopIdx() {
        return curLoopIdx;
    }

    public void setCurImageIdx(int curImageIdx) {
        this.curImageIdx = curImageIdx;
    }

    public void setCurImage(Image curImage) {
        this.curImage = curImage;
    }

    public void setCurLoopIdx(int curLoopIdx) {
        this.curLoopIdx = curLoopIdx;
    }

    @Override
    public Image getCurImage() {
        return curImage;
    }

    @Override
    public void updateImage() {
        if (!updateStatus() && // if the sprite still alive.
                isTimeToRefresh()) { // it is time to refresh.
            if (++curImageIdx == nbImages) {
                curImageIdx = 0;
                curLoopIdx++;
            }
            curImage = images[curImageIdx];
        }
    }
}