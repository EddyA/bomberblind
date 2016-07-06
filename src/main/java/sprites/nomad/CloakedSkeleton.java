package sprites.nomad;

import images.ImagesLoader;
import sprites.nomad.abstracts.Enemy;

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
                ImagesLoader.imagesMatrix[ImagesLoader.blueBbManWalkBackMatrixRowIdx],
                ImagesLoader.imagesMatrix[ImagesLoader.blueBbManWalkFrontMatrixRowIdx],
                ImagesLoader.imagesMatrix[ImagesLoader.blueBbManWalkLeftMatrixRowIdx],
                ImagesLoader.imagesMatrix[ImagesLoader.blueBbManWalkRightMatrixRowIdx],
                ImagesLoader.NB_BBMAN_WALK_FRAME,
                100); // create the BbMan.
    }
}
