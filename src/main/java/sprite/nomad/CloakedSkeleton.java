package sprite.nomad;

import images.ImagesLoader;

/**
 * A cloaked skeleton.
 */
public class CloakedSkeleton extends Enemy {

    public final static int REFRESH_TIME = 100;
    final static int ACTING_TIME = 25;

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
                ImagesLoader.NB_CLOAKED_SKELETON_DEATH_FRAME,
                ImagesLoader.imagesMatrix[ImagesLoader.cloakedSkeletonWalkBackMatrixRowIdx],
                ImagesLoader.imagesMatrix[ImagesLoader.cloakedSkeletonWalkFrontMatrixRowIdx],
                ImagesLoader.imagesMatrix[ImagesLoader.cloakedSkeletonWalkLeftMatrixRowIdx],
                ImagesLoader.imagesMatrix[ImagesLoader.cloakedSkeletonWalkRightMatrixRowIdx],
                ImagesLoader.NB_CLOAKED_SKELETON_WALK_FRAME,
                REFRESH_TIME,
                ACTING_TIME);
    }
}
