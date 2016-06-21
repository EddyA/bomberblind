package animations;

import images.ImagesLoader;

/**
 * An animated bomb.
 */
public class Bomb extends TimedAnimation {

    private int flamesize;

    /**
     * Create an animated bomb.
     *
     * @param rowIdx abscissa of the bomb
     * @param colIdx ordinate of the bomb
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