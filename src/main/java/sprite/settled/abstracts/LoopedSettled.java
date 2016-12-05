package sprite.settled.abstracts;

import java.awt.Image;

import sprite.SpriteType;

/**
 * Abstract class of a looped settled sprite.
 * The sprite loops a certain number of times.
 */
public abstract class LoopedSettled extends Settled {

    private final int nbTimes; // number of times the sprite should be painted.

    /**
     * Create a looped settled.
     *
     * @param rowIdx the map row index of the sprite
     * @param colIdx the map col index of the sprite
     * @param spriteType the sprite's type
     * @param refreshTime the sprite refreshTime (i.e. fps)
     * @param images the sprite's array of images
     * @param nbImages the number of images
     * @param nbTimes the max number of times the sprite must loop
     */
    public LoopedSettled(int rowIdx, int colIdx, SpriteType spriteType, int refreshTime, Image[] images, int nbImages,
            int nbTimes) {
        super(rowIdx, colIdx, spriteType, refreshTime, images, nbImages);
        this.nbTimes = nbTimes;
    }

    public int getNbTimes() {
        return nbTimes;
    }

    @Override
    public boolean updateStatus() {
        if (getCurLoopIdx() == nbTimes) {
            setCurStatus(Status.STATUS_FINISHED);
            return true;
        }
        return false;
    }
}