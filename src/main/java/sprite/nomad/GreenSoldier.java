package sprite.nomad;

import images.ImagesLoader;

public class GreenSoldier extends WalkingEnemy {

    public final static int ACTING_TIME = 25;

    public final static int DEATH_REFRESH_TIME = 100;
    public final static int WALK_REFRESH_TIME = 150;

    /**
     * Create a green soldier.
     *
     * @param xMap abscissa on the map.
     * @param yMap ordinate on the map.
     */
    public GreenSoldier(int xMap,
                        int yMap) {
        super(xMap,
                yMap,
                ImagesLoader.imagesMatrix[ImagesLoader.deathMatrixRowIdx],
                ImagesLoader.NB_DEATH_FRAME,
                DEATH_REFRESH_TIME,
                ImagesLoader.imagesMatrix[ImagesLoader.greenSoldierWalkBackMatrixRowIdx],
                ImagesLoader.imagesMatrix[ImagesLoader.greenSoldierWalkFrontMatrixRowIdx],
                ImagesLoader.imagesMatrix[ImagesLoader.greenSoldierWalkLeftMatrixRowIdx],
                ImagesLoader.imagesMatrix[ImagesLoader.greenSoldierWalkRightMatrixRowIdx],
                ImagesLoader.NB_GREEN_SOLDIER_WALK_FRAME,
                WALK_REFRESH_TIME,
                ACTING_TIME);
    }
}
