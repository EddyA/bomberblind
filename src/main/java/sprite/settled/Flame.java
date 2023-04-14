package sprite.settled;

import images.ImagesLoader;
import sprite.SpriteType;

/**
 * A flame.
 */
public class Flame extends TimedSettled {

    public static final int REFRESH_TIME = 100;
    public static final int DURATION_TIME = 1500;

    /**
     * Create a flame.
     *
     * @param rowIdx the map row index of the sprite
     * @param colIdx the map col index of the sprite
     */
    public Flame(int rowIdx, int colIdx) {
        super(rowIdx,
            colIdx,
            SpriteType.TYPE_SPRITE_FLAME,
            REFRESH_TIME,
            ImagesLoader.imagesMatrix[ImagesLoader.flameMatrixRowIdx],
            ImagesLoader.NB_FLAME_FRAME,
            DURATION_TIME);
    }
}
