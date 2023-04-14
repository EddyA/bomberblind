package sprite.nomad;

import images.ImagesLoader;

/**
 * A blue bomber.
 */
public class BlueBomber extends Bomber {

    public static final int REFRESH_TIME = 100;
    public static final int INVINCIBILITY_TIME = 3500;

    /**
     * Create a blue bomber.
     *
     * @param xMap abscissa on the map.
     * @param yMap ordinate on the map.
     */
    public BlueBomber(int xMap,
                      int yMap) {
        super(xMap,
                yMap,
                ImagesLoader.imagesMatrix[ImagesLoader.blueBomberDeathMatrixRowIdx],
                ImagesLoader.NB_BOMBER_DEATH_FRAME,
                ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWaitMatrixRowIdx],
                ImagesLoader.NB_BOMBER_WAIT_FRAME,
                ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWalkBackMatrixRowIdx],
                ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWalkFrontMatrixRowIdx],
                ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWalkLeftMatrixRowIdx],
                ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWalkRightMatrixRowIdx],
                ImagesLoader.NB_BOMBER_WALK_FRAME,
                ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWinMatrixRowIdx],
                ImagesLoader.NB_BOMBER_WIN_FRAME,
                REFRESH_TIME,
                INVINCIBILITY_TIME);
    }
}
