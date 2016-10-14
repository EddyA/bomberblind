package spriteList;

import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.ListIterator;

import map.abstracts.Map;
import sprite.abstracts.Sprite;
import sprite.nomad.abstracts.Bomber;
import sprite.nomad.abstracts.EnemyA;
import sprite.settled.Bomb;
import sprite.settled.ConclusionFlame;
import sprite.settled.Flame;

public class SpriteList extends LinkedList<Sprite> {

    // create a temporary list to manage addings and avoid concurent accesses.
    private LinkedList<Sprite> tmpList = new LinkedList<>();

    private Map map;
    private int screenWidth; // widht of the screen (expressed in pixel).
    private int screenHeight; // height of the screen (expressed in pixel).

    public SpriteList(Map map, int screenWidth, int screenHeight) {
        this.map = map;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    /**
     * Update curStatus of nomads.
     */
    public synchronized void updateStatusAndClean() {
        for (ListIterator<Sprite> iterator = this.listIterator(); iterator.hasNext(); ) {
            Sprite sprite = iterator.next();
            boolean shouldBeRemoved = false;

            switch (sprite.getSpriteType()) {
            case BOMBER: {
                shouldBeRemoved = ActionMethods.processBomber(this, map.getMapPointMatrix(), (Bomber) sprite);
                break;
            }
            case ENEMY: {
                shouldBeRemoved = ActionMethods.processEnemyA(this, map.getMapPointMatrix(), map.getMapWidth(),
                        map.getMapHeight(), (EnemyA) sprite);
                break;
            }
            case BOMB: {
                shouldBeRemoved = ActionMethods.processBomb(tmpList, map.getMapPointMatrix(), map.getMapWidth(),
                        map.getMapHeight(), (Bomb) sprite);
                break;
            }
            case FLAME: {
                shouldBeRemoved = ActionMethods.processFlame(tmpList, map.getMapPointMatrix(), (Flame) sprite);
                break;
            }
            case CONCLUSION_FLAME: {
                shouldBeRemoved = ActionMethods.processConclusionFlame((ConclusionFlame) sprite);
                break;
            }
            }
            if (shouldBeRemoved) {
                iterator.remove(); // remove it from the list.
            }
        }
        if (!tmpList.isEmpty()) {
            this.addAll(tmpList); // add sprites from the temporary list to the main one.
            tmpList.clear(); // clear the temporary list.
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
        for (Sprite sprite : this) {
            sprite.updateImage();
            if ((sprite.getCurImage() != null) && // happens when the bomber is invincible.
                    !sprite.isFinished()) {
                if ((sprite.getYMap() >= yMap)
                        && (sprite.getYMap() <= yMap + sprite.getCurImage().getWidth(null) + screenHeight)
                        && (sprite.getXMap() >= xMap)
                        && (sprite.getXMap() <= xMap + sprite.getCurImage().getHeight(null) / 2 + screenWidth)) {
                    sprite.paintBuffer(g, sprite.getXMap() - xMap, sprite.getYMap() - yMap);
                }
            }
        }
    }
}
