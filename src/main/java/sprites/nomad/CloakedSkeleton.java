package sprites.nomad;

import images.ImagesLoader;
import sprites.nomad.abstracts.Enemy;

/**
 * A cloaked skeleton.
 */
public class CloakedSkeleton extends Enemy {

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
                ImagesLoader.imagesMatrix[ImagesLoader.cloakedSkeletonWalkBackMatrixRowIdx],
                ImagesLoader.imagesMatrix[ImagesLoader.cloakedSkeletonWalkFrontMatrixRowIdx],
                ImagesLoader.imagesMatrix[ImagesLoader.cloakedSkeletonWalkLeftMatrixRowIdx],
                ImagesLoader.imagesMatrix[ImagesLoader.cloakedSkeletonWalkRightMatrixRowIdx],
                ImagesLoader.NB_ENEMY_WALK_FRAME,
                100); // create the BbMan.
    }
}
