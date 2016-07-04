package sprites.settled;

import images.ImagesLoader;
import sprites.settled.abstracts.LSprite;

/**
 * A conclusion flame.
 */
public class FlameEnd extends LSprite {

    /**
     * Create a conclusion flame.
     *
     * @param rowIdx the map row index of the conclusion flame
     * @param colIdx the map column index of the conclusion flame
     */
    public FlameEnd(int rowIdx, int colIdx) {
        super(rowIdx,
                colIdx,
                ImagesLoader.imagesMatrix[ImagesLoader.flameEndMatrixRowIdx],
                ImagesLoader.NB_FLAME_END_FRAME,
                50,
                1);
    }
}
