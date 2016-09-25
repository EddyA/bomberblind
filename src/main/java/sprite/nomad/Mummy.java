package sprite.nomad;

import images.ImagesLoader;
import sprite.nomad.abstracts.Enemy;

public class Mummy extends Enemy {

    public final static int REFRESH_TIME = 300;
    public final static int MOVING_TIME = 50;

    /**
     * Create a cloaked skeleton.
     *
     * @param xMap abscissa on the map.
     * @param yMap ordinate on the map.
     */
    public Mummy(int xMap,
                 int yMap) {
        super(xMap,
                yMap,
                ImagesLoader.imagesMatrix[ImagesLoader.mummyDeathMatrixRowIdx],
                ImagesLoader.NB_MUMMY_DEATH_FRAME,
                ImagesLoader.imagesMatrix[ImagesLoader.mummyWalkBackMatrixRowIdx],
                ImagesLoader.imagesMatrix[ImagesLoader.mummyWalkFrontMatrixRowIdx],
                ImagesLoader.imagesMatrix[ImagesLoader.mummyWalkLeftMatrixRowIdx],
                ImagesLoader.imagesMatrix[ImagesLoader.mummyWalkRightMatrixRowIdx],
                ImagesLoader.NB_MUMMY_WALK_FRAME,
                REFRESH_TIME,
                MOVING_TIME);
    }
}
