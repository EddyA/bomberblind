package sprites.settled;

import images.ImagesLoader;

/**
 * A bomb.
 */
public class Bomb extends TimedSettled {

    private int flamesize;
    protected final static int REFRESH_TIME = 100;
    protected final static int DURATION_TIME = 2000;

    /**
     * Create a bomb.
     *
     * @param rowIdx    the map row index of the bomb
     * @param colIdx    the map column index of the bomb
     * @param flameSize the flame size of the bomb
     */
    public Bomb(int rowIdx, int colIdx, int flameSize) {
        super(rowIdx,
                colIdx,
                ImagesLoader.imagesMatrix[ImagesLoader.bombMatrixRowIdx],
                ImagesLoader.NB_BOMB_FRAME,
                REFRESH_TIME,
                DURATION_TIME);
        this.flamesize = flameSize;
    }

    public int getFlameSize() {
        return flamesize;
    }
}