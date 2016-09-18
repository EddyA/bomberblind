package sprites.nomad;

import images.ImagesLoader;
import sprites.nomad.abstracts.Enemy;

/**
 * A cloaked skeleton.
 */
public class CloakedSkeleton extends Enemy {

    public final static int REFRESH_TIME = 100;
    public final static int MOVE_TIME = 10;

    /**
     * Create a cloaked skeleton.
     *
     * @param xMap abscissa on the map.
     * @param yMap ordinate on the map.
     */
    public CloakedSkeleton(int xMap,
                           int yMap) {
        super(xMap,
                yMap,
                ImagesLoader.imagesMatrix[ImagesLoader.cloakedSkeletonDeathMatrixRowIdx],
                ImagesLoader.NB_ENEMY_DEATH_FRAME,
                ImagesLoader.imagesMatrix[ImagesLoader.cloakedSkeletonWalkBackMatrixRowIdx],
                ImagesLoader.imagesMatrix[ImagesLoader.cloakedSkeletonWalkFrontMatrixRowIdx],
                ImagesLoader.imagesMatrix[ImagesLoader.cloakedSkeletonWalkLeftMatrixRowIdx],
                ImagesLoader.imagesMatrix[ImagesLoader.cloakedSkeletonWalkRightMatrixRowIdx],
                ImagesLoader.NB_ENEMY_WALK_FRAME,
                REFRESH_TIME,
                MOVE_TIME);
    }
}
