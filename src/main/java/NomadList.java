import ai.EnemyAi;
import map.abstracts.Map;
import sprites.nomad.CloakedSkeleton;
import sprites.nomad.abstracts.Enemy;
import sprites.nomad.abstracts.Nomad;

import java.awt.*;
import java.util.LinkedList;

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
     * Update status of nomads.
     */
    public void updateStatus() {
        for (Nomad nomad : this) {
            if (nomad.getClass().getSuperclass().getSimpleName().equals("Enemy")) {
                Enemy enemy = (Enemy) nomad; // cast the nomad to enemy.
                if (enemy.shouldMove()) { // is it time to move?
                    enemy.setStatus(EnemyAi.updateStatus(map.getMapPointMatrix(), map.getMapWidth(),
                            map.getMapHeight(), enemy.getXMap(), enemy.getYMap(), enemy.getStatus()));
                    switch (enemy.getStatus()) {
                        case STATUS_WALK_BACK: {
                            nomad.setYMap(nomad.getYMap() - 1);
                            break;
                        }
                        case STATUS_WALK_FRONT: {
                            nomad.setYMap(nomad.getYMap() + 1);
                            break;
                        }
                        case STATUS_WALK_LEFT: {
                            nomad.setXMap(nomad.getXMap() - 1);
                            break;
                        }
                        case STATUS_WALK_RIGHT: {
                            nomad.setXMap(nomad.getXMap() + 1);
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * Paint the visible nomads on screen.
     *
     * @param g    the graphics context
     * @param xMap the map abscissa from which painting nomads
     * @param yMap the map ordinate from which painting nomads
     */
    public synchronized void paintBuffer(Graphics2D g, int xMap, int yMap) {

        // paint sprites.
        for (Nomad nomad : this) {
            if ((nomad.getYMap() >= yMap && nomad.getYMap() < yMap + screenHeight + 1) &&
                    (nomad.getXMap() >= xMap && nomad.getXMap() < xMap + screenWidth + 1)) {
                nomad.paintBuffer(g, nomad.getXMap() - xMap, nomad.getYMap() - yMap);
            }
        }
    }
}
