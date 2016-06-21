package animations;

import images.ImagesLoader;

/**
 * An animated flame.
 */
public class Flame extends TimedAnimation {

    /**
     * Create an animated flame.
     *
     * @param rowIdx abscissa of the flame
     * @param colIdx ordinate of the bomb
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
