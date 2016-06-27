package sprites;

import images.ImagesLoader;

public class FlameEnd extends FramedSprite {

    /**
     * Create an animated flame.
     *
     * @param rowIdx abscissa of the flame
     * @param colIdx ordinate of the bomb
     */
    public FlameEnd(int rowIdx, int colIdx) {
        super(rowIdx,
                colIdx,
                ImagesLoader.imagesMatrix[ImagesLoader.flameEndMatrixRowIdx],
                ImagesLoader.NB_FLAME_END_FRAME,
                50);
    }
}
