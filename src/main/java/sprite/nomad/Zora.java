package sprite.nomad;

import images.ImagesLoader;

/**
 * A zora.
 */
public class Zora extends WalkingEnemy {

    public final static int WALK_REFRESH_TIME = 100;
    public final static int ACTING_TIME = 15;

    /**
     * Create a zora.
     *
     * @param xMap abscissa on the map.
     * @param yMap ordinate on the map.
     */
    public Zora(int xMap,
                int yMap) {
        super(xMap,
                yMap,
                ImagesLoader.imagesMatrix[ImagesLoader.zoraWalkBackMatrixRowIdx],
                ImagesLoader.imagesMatrix[ImagesLoader.zoraWalkFrontMatrixRowIdx],
                ImagesLoader.imagesMatrix[ImagesLoader.zoraWalkLeftMatrixRowIdx],
                ImagesLoader.imagesMatrix[ImagesLoader.zoraWalkRightMatrixRowIdx],
                ImagesLoader.NB_ZORA_WALK_FRAME,
                WALK_REFRESH_TIME,
                ACTING_TIME);
    }
}
