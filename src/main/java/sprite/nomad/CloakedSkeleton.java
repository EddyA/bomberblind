package sprite.nomad;

import images.ImagesLoader;
import sprite.SpriteType;
import sprite.nomad.abstracts.EnemyA;

/**
 * A cloaked skeleton.
 */
public class CloakedSkeleton extends EnemyA {

    public final static int REFRESH_TIME = 100;
    public final static int MOVING_TIME = 25;

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
                SpriteType.ENEMY,
                ImagesLoader.imagesMatrix[ImagesLoader.cloakedSkeletonDeathMatrixRowIdx],
                ImagesLoader.NB_CLOAKED_SKELETON_DEATH_FRAME,
                ImagesLoader.imagesMatrix[ImagesLoader.cloakedSkeletonWalkBackMatrixRowIdx],
                ImagesLoader.imagesMatrix[ImagesLoader.cloakedSkeletonWalkFrontMatrixRowIdx],
                ImagesLoader.imagesMatrix[ImagesLoader.cloakedSkeletonWalkLeftMatrixRowIdx],
                ImagesLoader.imagesMatrix[ImagesLoader.cloakedSkeletonWalkRightMatrixRowIdx],
                ImagesLoader.NB_CLOAKED_SKELETON_WALK_FRAME,
                REFRESH_TIME,
                MOVING_TIME);
    }
}
