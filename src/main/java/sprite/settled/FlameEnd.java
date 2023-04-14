package sprite.settled;

import images.ImagesLoader;
import sprite.SpriteType;

/**
 * A flame end.
 */
public class FlameEnd extends LoopedSettled {

    public static final int REFRESH_TIME = 50;
    public static final int NB_TIMES = 1;

    /**
     * Create a flame end.
     *
     * @param rowIdx the map row index of the sprite
     * @param colIdx the map col index of the sprite
     */
    public FlameEnd(int rowIdx, int colIdx) {
        super(rowIdx,
            colIdx,
            SpriteType.TYPE_SPRITE_FLAME_END,
            REFRESH_TIME,
            ImagesLoader.imagesMatrix[ImagesLoader.flameEndMatrixRowIdx],
            ImagesLoader.NB_FLAME_END_FRAME,
            NB_TIMES);
    }
}
