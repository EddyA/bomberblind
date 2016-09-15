package sprites.settled;

import images.ImagesLoader;
import sprites.settled.abstracts.TimedSettled;

/**
 * A bomb.
 */
public class Bomb extends TimedSettled {

    private int flamesize;
    public final static int REFRESH_TIME = 100;
    public final static int DURATION_TIME = 2000;

    /**
     * Create a bomb.
     *
     * @param rowIdx    the map row index of the sprite
     * @param colIdx    the map col index of the sprite
     * @param flameSize the flame size of the bomb
     */
    public Bomb(int rowIdx, int colIdx, int flameSize) {
        super(rowIdx,
                colIdx,
                REFRESH_TIME,
                ImagesLoader.imagesMatrix[ImagesLoader.bombMatrixRowIdx],
                ImagesLoader.NB_BOMB_FRAME,
                DURATION_TIME);
        this.flamesize = flameSize;
    }

    public int getFlameSize() {
        return flamesize;
    }
}