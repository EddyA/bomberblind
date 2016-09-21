package sprite.settled;

import images.ImagesLoader;
import sprite.settled.abstracts.LoopedSettled;

/**
 * A conclusion flame.
 */
public class ConclusionFlame extends LoopedSettled {

    public final static int REFRESH_TIME = 50;
    public final static int NB_TIMES = 1;

    /**
     * Create a conclusion flame.
     *
     * @param rowIdx the map row index of the sprite
     * @param colIdx the map col index of the sprite
     */
    public ConclusionFlame(int rowIdx, int colIdx) {
        super(rowIdx,
                colIdx,
                REFRESH_TIME,
                ImagesLoader.imagesMatrix[ImagesLoader.flameEndMatrixRowIdx],
                ImagesLoader.NB_FLAME_END_FRAME,
                NB_TIMES);
    }
}
