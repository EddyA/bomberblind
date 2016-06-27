package sprites;

import images.ImagesLoader;

/**
 * A timed flame sprite.
 */
public class Flame extends TimedSprite {

    /**
     * Create a timed flame sprite.
     *
     * @param rowIdx row index of the flame
     * @param colIdx column index of the flame
     */
    public Flame(int rowIdx, int colIdx) {
        super(rowIdx,
                colIdx,
                ImagesLoader.imagesMatrix[ImagesLoader.flameMatrixRowIdx],
                ImagesLoader.NB_FLAME_FRAME,
                1500,
                100);
    }
}
