package sprite.settled;

import images.ImagesLoader;
import sprite.SpriteType;

/**
 * A bomb.
 */
public class Bomb extends TimedSettled {

    public final static int REFRESH_TIME = 100;
    public final static int DURATION_TIME = 2000;

    private int flamesize;

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
                SpriteType.TYPE_SPRITE_BOMB,
                REFRESH_TIME,
                ImagesLoader.imagesMatrix[ImagesLoader.bombMatrixRowIdx],
                ImagesLoader.NB_BOMB_FRAME,
                DURATION_TIME);
        this.flamesize = flameSize;
    }

    public void setFlamesize(int flamesize) {
        this.flamesize = flamesize;
    }

    public int getFlameSize() {
        return flamesize;
    }
}