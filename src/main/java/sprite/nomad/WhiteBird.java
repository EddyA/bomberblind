package sprite.nomad;

import images.ImagesLoader;
import utils.Direction;

/**
 * A white bird.
 */
public class WhiteBird extends FlyingNomad {

    public final static int REFRESH_TIME = 150;
    public final static int ACTING_TIME = 6;

    /**
     * Create a white bird.
     *
     * @param xMap the abscissa on the map.
     * @param yMap the ordinate on the map.
     * @param direction the white bird direction.
     * @param deviation the white bird deviation.
     */
    public WhiteBird(int xMap, int yMap, Direction direction, int deviation) {
        super(xMap,
                yMap,
                null,
                null,
                ImagesLoader.imagesMatrix[ImagesLoader.birdFlyLeftMatrixRowIdx],
                ImagesLoader.imagesMatrix[ImagesLoader.birdFlyRightMatrixRowIdx],
                ImagesLoader.NB_BIRD_FLY_FRAME,
                direction,
                deviation,
                REFRESH_TIME,
                ACTING_TIME);
    }
}
