package sprites.nomad;

import images.ImagesLoader;
import sprites.nomad.abstracts.BbMan;

/**
 * A blue BbMan.
 */
public class BbManBlue extends BbMan {

    /**
     * Create a blue BbMan.
     *
     * @param xMap abscissa on the map.
     * @param yMap ordinate on the map.
     */
    public BbManBlue(int xMap,
                     int yMap) {
        super(xMap,
                yMap,
                ImagesLoader.imagesMatrix[ImagesLoader.blueBbManDeathMatrixRowIdx],
                ImagesLoader.NB_BBMAN_DEATH_FRAME,
                ImagesLoader.imagesMatrix[ImagesLoader.blueBbManWaitMatrixRowIdx],
                ImagesLoader.NB_BBMAN_WAIT_FRAME,
                ImagesLoader.imagesMatrix[ImagesLoader.blueBbManWalkBackMatrixRowIdx],
                ImagesLoader.imagesMatrix[ImagesLoader.blueBbManWalkFrontMatrixRowIdx],
                ImagesLoader.imagesMatrix[ImagesLoader.blueBbManWalkLeftMatrixRowIdx],
                ImagesLoader.imagesMatrix[ImagesLoader.blueBbManWalkRightMatrixRowIdx],
                ImagesLoader.NB_BBMAN_WALK_FRAME,
                ImagesLoader.imagesMatrix[ImagesLoader.blueBbManWinMatrixRowIdx],
                ImagesLoader.NB_BBMAN_WIN_FRAME,
                100,
                2500); // create the BbMan.
    }
}
