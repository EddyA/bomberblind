package spriteList;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import exceptions.CannotPlaceSpriteOnMapException;
import map.MapPoint;
import map.abstracts.Map;
import sprite.abstracts.Sprite;
import sprite.nomad.SimpleEnemyType;
import sprite.nomad.abstracts.Bomber;
import sprite.nomad.abstracts.Enemy;
import sprite.settled.Bomb;
import sprite.settled.ConclusionFlame;
import sprite.settled.Flame;
import spriteList.ctrl.ActionMethods;
import spriteList.ctrl.GenerationMethodes;

public class SpriteList extends LinkedList<Sprite> {
    private SpritesSetting spritesSetting;
    private Map map;
    private int screenWidth; // width of the screen (expressed in pixel).
    private int screenHeight; // height of the screen (expressed in pixel).

    // create a temporary list to manage addings and avoid concurent accesses.
    private LinkedList<Sprite> tmpList = new LinkedList<>();

    public SpriteList(SpritesSetting spritesSetting, Map map, int screenWidth, int screenHeight) {
        this.spritesSetting = spritesSetting;
        this.map = map;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    /**
     * Randomly (create) and place sprites on the map.
     *
     * @throws CannotPlaceSpriteOnMapException if all the requested sprites cannot be placed on map
     */
    public void generateSprites() throws CannotPlaceSpriteOnMapException {

        // create a list of empty points (available points to place the enemies).
        List<MapPoint> emptyPtList = new ArrayList<>();
        for (int rowIdx = 0; rowIdx < map.getMapHeight(); rowIdx++) {
            for (int colIdx = 0; colIdx < map.getMapWidth(); colIdx++) {
                if (map.getMapPointMatrix()[rowIdx][colIdx].isPathway()) {
                    emptyPtList.add(map.getMapPointMatrix()[rowIdx][colIdx]);
                }
            }
        }

        // enemies:
        // - cloaked skeleton
        GenerationMethodes.randomlyPlaceSimpleEnemy(this, SimpleEnemyType.CLOAKED_SKELETON,
                spritesSetting.getNbCloakedSkeleton(), emptyPtList);
        // - meca angel
        GenerationMethodes.randomlyPlaceSimpleEnemy(this, SimpleEnemyType.MECA_ANGEL,
                spritesSetting.getNbMecaAngel(), emptyPtList);
        // - mummy
        GenerationMethodes.randomlyPlaceSimpleEnemy(this, SimpleEnemyType.MUMMY,
                spritesSetting.getNbMummy(), emptyPtList);
    }

    /**
     * Process and clean sprites.
     */
    public synchronized void update() {
        for (ListIterator<Sprite> iterator = this.listIterator(); iterator.hasNext(); ) {
            Sprite sprite = iterator.next();
            boolean shouldBeRemoved;

            switch (sprite.getSpriteType()) {
            case BOMBER: {
                shouldBeRemoved = ActionMethods.processBomber(this, map.getMapPointMatrix(), (Bomber) sprite);
                break;
            }
            case ENEMY: {
                shouldBeRemoved = ActionMethods.processEnemyA(this, map.getMapPointMatrix(), map.getMapWidth(),
                        map.getMapHeight(), (Enemy) sprite);
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
            default: {
                throw new RuntimeException("the following sprite " + sprite.getUid() + " has no type.");
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
     * Paint the visible sprites on screen.
     *
     * @param g the graphics context
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
                        && (sprite.getXMap() >= xMap - sprite.getCurImage().getWidth(null) / 2)
                        && (sprite.getXMap() <= xMap + sprite.getCurImage().getHeight(null) / 2 + screenWidth)) {
                    sprite.paintBuffer(g, sprite.getXMap() - xMap, sprite.getYMap() - yMap);
                }
            }
        }
    }
}
