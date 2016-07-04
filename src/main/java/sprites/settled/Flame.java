package sprites.settled;

import images.ImagesLoader;
import sprites.settled.abstracts.TSprite;

/**
 * A flame.
 */
public class Flame extends TSprite {

    /**
     * Create a flame.
     *
     * @param rowIdx the map row index of the flame
     * @param colIdx the map column index of the flame
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
