import ai.EnemyAi;
import exceptions.CannotMoveNomadException;
import map.abstracts.Map;
import sprites.nomad.CloakedSkeleton;
import sprites.nomad.abstracts.Bomber;
import sprites.nomad.abstracts.Enemy;
import sprites.nomad.abstracts.Nomad;

import java.awt.*;
import java.util.LinkedList;
import java.util.ListIterator;

import static map.ctrl.NomadMethods.isNomadBurning;
import static sprites.NomadCtrl.isNomadCrossingEnemy;

/**
 * List of abstracts items.
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
     * Add the main bomber to the list.
     *
     * @param bomber the bomber to add
     */
    public void addMainBomber(Bomber bomber) {
        this.add(bomber);
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
     * Update curStatus of nomads.
     */
    public synchronized void updateStatusAndClean() {
        for (ListIterator<Nomad> iterator = this.listIterator(); iterator.hasNext(); ) {
            Nomad nomad = iterator.next();

            if (nomad.getClass().getSuperclass().getSimpleName().equals("Bomber")) { // it is a bomber.
                Bomber bomber = (Bomber) nomad; // cast to bomber.

                // is it finished?
                if (bomber.isFinished()) {
                    bomber.initStatement(); // bombers are never removed from the list, they are just re-initialized.
                } else if (bomber.getCurStatus() != Bomber.status.STATUS_DYING) { // not finished and not dead.

                    // should the bomber die?
                    if (!bomber.isInvincible() &&
                            (isNomadBurning(map.getMapPointMatrix(), bomber.getXMap(), bomber.getYMap()) ||
                                    isNomadCrossingEnemy(this, bomber, bomber.getXMap(), bomber.getYMap()))) {
                        bomber.setCurStatus(Bomber.status.STATUS_DYING);
                    }
                }
            } else if (nomad.getClass().getSuperclass().getSimpleName().equals("Enemy")) {  // it is an enemy.
                Enemy enemy = (Enemy) nomad; // cast to enemy.

                // is it finished?
                if (enemy.isFinished()) {
                    iterator.remove(); // remove it from the list.
                } else if (enemy.getCurStatus() != Enemy.status.STATUS_DYING) { // not finished and not dead.

                    // should the enemy die?
                    if (isNomadBurning(map.getMapPointMatrix(), enemy.getXMap(), enemy.getYMap())) {
                        enemy.setCurStatus(Enemy.status.STATUS_DYING);

                    } else if (enemy.shouldMove()) { // not dead -> should the enemy move?
                        try {
                            Enemy.status newStatus = EnemyAi.computeNextMove(map.getMapPointMatrix(), map.getMapWidth(),
                                    map.getMapHeight(), this, enemy); // try to compute the nex move.

                            enemy.setCurStatus(newStatus); // if the previous function has not thrown an exception.
                            switch (enemy.getCurStatus()) {
                                case STATUS_WALKING_BACK: {
                                    nomad.setYMap(nomad.getYMap() - 1);
                                    break;
                                }
                                case STATUS_WALKING_FRONT: {
                                    nomad.setYMap(nomad.getYMap() + 1);
                                    break;
                                }
                                case STATUS_WALKING_LEFT: {
                                    nomad.setXMap(nomad.getXMap() - 1);
                                    break;
                                }
                                case STATUS_WALKING_RIGHT: {
                                    nomad.setXMap(nomad.getXMap() + 1);
                                    break;
                                }
                            }
                        } catch (CannotMoveNomadException e) {
                            // nothing to do, just wait for the next iteration.
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
            nomad.updateImage();
            if (nomad.getCurImage() != null) {
                if ((nomad.getYMap() >= yMap && nomad.getYMap() <=
                        yMap + nomad.getCurImage().getWidth(null) + screenHeight) &&
                        (nomad.getXMap() >= xMap && nomad.getXMap() <=
                                xMap + nomad.getCurImage().getHeight(null) / 2 + screenWidth)) {
                    nomad.paintBuffer(g, nomad.getXMap() - xMap, nomad.getYMap() - yMap);
                }
            }
        }
    }
}
