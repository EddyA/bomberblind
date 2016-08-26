import static images.ImagesLoader.IMAGE_SIZE;

import java.awt.Graphics;
import java.util.LinkedList;

import map.abstracts.Map;
import sprites.nomad.CloakedSkeleton;
import sprites.nomad.abstracts.Nomad;

/**
 * List of nomad items.
 */
public class NomadList extends LinkedList<Nomad> {

    private Map map;
    private int screenWidth; // widht of the screen (expressed in pixel).
    private int screenHeight; // height of the screen (expressed in pixel).

    public NomadList(Map map, int screenWidth, int screenHeight) {
        this.map = map;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    /**
     * Add a cloaked skeleton to the list.
     *
     * @param xMap the abscissa of the cloaked skeleton
     * @param yMap the ordinate of the cloaked skeleton
     */
    public void addCloakedSkeleton(int xMap, int yMap) {
        this.add(new CloakedSkeleton(xMap, yMap));
    }

    /**
     * Paint the visible nomads on screen.
     *
     * @param g    the graphics context
     * @param xMap the map abscissa from which painting nomads
     * @param yMap the map ordinate from which painting nomads
     */
    public synchronized void paintBuffer(Graphics g, int xMap, int yMap) {

        // paint sprites.
        for (Nomad nomad : this) {
            if ((nomad.getYMap() >= yMap && nomad.getYMap() < yMap + screenHeight + 1) &&
                    (nomad.getXMap() >= xMap && nomad.getXMap() < xMap + screenWidth + 1)) {
                nomad.paintBuffer(g, nomad.getXMap() - xMap, nomad.getYMap() - yMap);
            }
        }
    }
}
