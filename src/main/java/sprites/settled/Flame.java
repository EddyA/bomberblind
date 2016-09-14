package sprites.settled;

import images.ImagesLoader;
import sprites.settled.abstracts.TimedSettled;

/**
 * A flame.
 */
public class Flame extends TimedSettled {

    protected final static int REFRESH_TIME = 100;
    protected final static int DURATION_TIME = 1500;

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
                REFRESH_TIME,
                DURATION_TIME);
    }
}
