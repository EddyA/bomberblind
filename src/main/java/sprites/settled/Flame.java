package sprites.settled;

import images.ImagesLoader;

/**
 * A flame.
 */
public class Flame extends TimedSettled {

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
