package sprites.settled;

import images.ImagesLoader;
import sprites.settled.abstracts.TimedSettled;

/**
 * A flame.
 */
public class Flame extends TimedSettled {

    public final static int REFRESH_TIME = 100;
    public final static int DURATION_TIME = 1500;

    /**
     * Create a flame.
     *
     * @param rowIdx the map row index of the sprite
     * @param colIdx the map col index of the sprite
     */
    public Flame(int rowIdx, int colIdx) {
        super(rowIdx,
                colIdx,
                REFRESH_TIME,
                ImagesLoader.imagesMatrix[ImagesLoader.flameMatrixRowIdx],
                ImagesLoader.NB_FLAME_FRAME,
                DURATION_TIME);
    }
}
