package sprite.settled;

import images.ImagesLoader;
import sprite.SpriteType;

public class Sparkle extends LoopedSettled {

    public static final int REFRESH_TIME = 100;
    public static final int NB_TIMES = 0; // never stop.

    /**
     * Create a sparkle.
     *
     * @param xMap the abscissa of the sprite
     * @param yMap the ordinate of the sprite
     */
    public Sparkle(int xMap, int yMap) {
        // as sparkle must be (precisely) place using pixels coordinates, init it with fake rowIdx/colIdx
        // and then, set the coordinates in pixels.

        super(0,
            0,
            SpriteType.TYPE_SPRITE_SPARKLE,
            REFRESH_TIME,
            ImagesLoader.imagesMatrix[ImagesLoader.sparkleMatrixRowIdx],
            ImagesLoader.NB_SPARKLE_FRAME,
            NB_TIMES);
        this.setXMap(xMap);
        this.setYMap(yMap);
    }
}
