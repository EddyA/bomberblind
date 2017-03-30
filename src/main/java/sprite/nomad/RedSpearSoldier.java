package sprite.nomad;

import images.ImagesLoader;

public class RedSpearSoldier extends BreakingEnemy {

    public final static int ACTING_TIME = 30;

    public final static int WALK_REFRESH_TIME = 150;
    public final static int BREAK_REFRESH_TIME = 50;

    /**
     * Create a red spear soldier.
     *
     * @param xMap abscissa on the map.
     * @param yMap ordinate on the map.
     */
    public RedSpearSoldier(int xMap,
                           int yMap) {
        super(xMap,
                yMap,
                ImagesLoader.imagesMatrix[ImagesLoader.redSpearSoldierBreakBackMatrixRowIdx],
                ImagesLoader.imagesMatrix[ImagesLoader.redSpearSoldierBreakFrontMatrixRowIdx],
                ImagesLoader.imagesMatrix[ImagesLoader.redSpearSoldierBreakLeftMatrixRowIdx],
                ImagesLoader.imagesMatrix[ImagesLoader.redSpearSoldierBreakRightMatrixRowIdx],
                ImagesLoader.NB_RED_SPEAR_SOLDIER_BREAK_FRAME,
                BREAK_REFRESH_TIME,
                ImagesLoader.imagesMatrix[ImagesLoader.redSpearSoldierWalkBackMatrixRowIdx],
                ImagesLoader.imagesMatrix[ImagesLoader.redSpearSoldierWalkFrontMatrixRowIdx],
                ImagesLoader.imagesMatrix[ImagesLoader.redSpearSoldierWalkLeftMatrixRowIdx],
                ImagesLoader.imagesMatrix[ImagesLoader.redSpearSoldierWalkRightMatrixRowIdx],
                ImagesLoader.NB_RED_SPEAR_SOLDIER_WALK_FRAME,
                WALK_REFRESH_TIME,
                ACTING_TIME);
    }
}
