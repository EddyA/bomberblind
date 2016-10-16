package sprite.nomad;

import images.ImagesLoader;
import sprite.SpriteType;
import sprite.nomad.abstracts.Enemy;

/**
 * A mecanical angel.
 */
public class MecaAngel extends Enemy {

    public final static int REFRESH_TIME = 100;
    public final static int MOVING_TIME = 10;

    /**
     * Create a mecanical angel
     *
     * @param xMap abscissa on the map.
     * @param yMap ordinate on the map.
     */
    public MecaAngel(int xMap,
                     int yMap) {
        super(xMap,
                yMap,
                SpriteType.ENEMY,
                ImagesLoader.imagesMatrix[ImagesLoader.mecaAngelDeathMatrixRowIdx],
                ImagesLoader.NB_MECA_ANGEL_DEATH_FRAME,
                ImagesLoader.imagesMatrix[ImagesLoader.mecaAngelWalkBackMatrixRowIdx],
                ImagesLoader.imagesMatrix[ImagesLoader.mecaAngelWalkFrontMatrixRowIdx],
                ImagesLoader.imagesMatrix[ImagesLoader.mecaAngelWalkLeftMatrixRowIdx],
                ImagesLoader.imagesMatrix[ImagesLoader.mecaAngelWalkRightMatrixRowIdx],
                ImagesLoader.NB_MECA_ANGEL_WALK_FRAME,
                REFRESH_TIME,
                MOVING_TIME);
    }
}
