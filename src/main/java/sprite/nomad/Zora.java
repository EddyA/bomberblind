package sprite.nomad;

import images.ImagesLoader;

/**
 * A zora.
 */
public class Zora extends WalkingEnemy {

    public static final int ACTING_TIME = 15;

    public static final int DEATH_REFRESH_TIME = 100;
    public static final int WALK_REFRESH_TIME = 100;

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
                ImagesLoader.imagesMatrix[ImagesLoader.deathMatrixRowIdx],
                ImagesLoader.NB_DEATH_FRAME,
                DEATH_REFRESH_TIME,
                ImagesLoader.imagesMatrix[ImagesLoader.zoraWalkBackMatrixRowIdx],
                ImagesLoader.imagesMatrix[ImagesLoader.zoraWalkFrontMatrixRowIdx],
                ImagesLoader.imagesMatrix[ImagesLoader.zoraWalkLeftMatrixRowIdx],
                ImagesLoader.imagesMatrix[ImagesLoader.zoraWalkRightMatrixRowIdx],
                ImagesLoader.NB_ZORA_WALK_FRAME,
                WALK_REFRESH_TIME,
                ACTING_TIME);
    }
}
