package sprite.nomad;

import images.ImagesLoader;
import utils.Direction;

/**
 * A bird.
 */
public class Bird extends FlyingNomad {

    public final static int REFRESH_TIME = 150;
    public final static int ACTING_TIME = 6;

    /**
     * Create a bird.
     *
     * @param xMap the abscissa on the map.
     * @param yMap the ordinate on the map.
     * @param direction the bird direction.
     * @param deviation the bird deviation.
     */
    public Bird(int xMap, int yMap, Direction direction, int deviation) {
        super(xMap,
                yMap,
                ImagesLoader.imagesMatrix[ImagesLoader.birdFlyBackMatrixRowIdx],
                ImagesLoader.imagesMatrix[ImagesLoader.birdFlyFrontMatrixRowIdx],
                ImagesLoader.imagesMatrix[ImagesLoader.birdFlyLeftMatrixRowIdx],
                ImagesLoader.imagesMatrix[ImagesLoader.birdFlyRightMatrixRowIdx],
                ImagesLoader.NB_BIRD_FLY_FRAME,
                direction,
                deviation,
                REFRESH_TIME,
                ACTING_TIME);
    }
}
