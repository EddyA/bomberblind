package sprite.nomad;

import images.ImagesLoader;

public class Minotor extends BreakingEnemy {

    public final static int REFRESH_TIME = 300;
    public final static int ACTING_TIME = 40;

    /**
     * Create a minotor.
     *
     * @param xMap abscissa on the map.
     * @param yMap ordinate on the map.
     */
    public Minotor(int xMap,
                   int yMap) {
        super(xMap,
                yMap,
                ImagesLoader.imagesMatrix[ImagesLoader.minotorBreakBackMatrixRowIdx],
                ImagesLoader.imagesMatrix[ImagesLoader.minotorBreakFrontMatrixRowIdx],
                ImagesLoader.imagesMatrix[ImagesLoader.minotorBreakLeftMatrixRowIdx],
                ImagesLoader.imagesMatrix[ImagesLoader.minotorBreakRightMatrixRowIdx],
                ImagesLoader.NB_MINOTOR_BREAK_FRAME,
                ImagesLoader.imagesMatrix[ImagesLoader.minotorDeathMatrixRowIdx],
                ImagesLoader.NB_MINOTOR_DEATH_FRAME,
                ImagesLoader.imagesMatrix[ImagesLoader.minotorWalkBackMatrixRowIdx],
                ImagesLoader.imagesMatrix[ImagesLoader.minotorWalkFrontMatrixRowIdx],
                ImagesLoader.imagesMatrix[ImagesLoader.minotorWalkLeftMatrixRowIdx],
                ImagesLoader.imagesMatrix[ImagesLoader.minotorWalkRightMatrixRowIdx],
                ImagesLoader.NB_MINOTOR_WAIT_FRAME,
                ImagesLoader.imagesMatrix[ImagesLoader.minotorWalkBackMatrixRowIdx],
                ImagesLoader.imagesMatrix[ImagesLoader.minotorWalkFrontMatrixRowIdx],
                ImagesLoader.imagesMatrix[ImagesLoader.minotorWalkLeftMatrixRowIdx],
                ImagesLoader.imagesMatrix[ImagesLoader.minotorWalkRightMatrixRowIdx],
                ImagesLoader.NB_MINOTOR_WALK_FRAME,
                REFRESH_TIME,
                ACTING_TIME);
    }
}
