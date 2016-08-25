package sprites.settled;

import images.ImagesLoader;
import sprites.settled.abstracts.TimedSettled;

/**
 * A bomb.
 */
public class Bomb extends TimedSettled {

    private int flamesize;

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
                2000,
                100);
        this.flamesize = flameSize;
    }

    public int getFlameSize() {
        return flamesize;
    }
}