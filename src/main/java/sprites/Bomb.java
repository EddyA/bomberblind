package sprites;

import images.ImagesLoader;

/**
 * A timed bomb animation.
 */
public class Bomb extends TimedSprite {

    private int flamesize;

    /**
     * Create a timed bomb animation.
     *
     * @param rowIdx    row index of the bomb
     * @param colIdx    column index of the bomb
     * @param flameSize size of flames
     */
    public Bomb(int rowIdx, int colIdx, int flameSize) {
        super(rowIdx,
                colIdx,
                ImagesLoader.imagesMatrix[ImagesLoader.bombMatrixRowIdx],
                ImagesLoader.NB_BOMB_FRAME,
                2000,
                100);
        this.flamesize = flameSize;
    }

    public int getFlameSize() {
        return flamesize;
    }
}