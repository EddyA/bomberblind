package spriteList;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import exceptions.CannotPlaceEnemyOnMapException;
import images.ImagesLoader;
import map.Map;
import map.MapPoint;
import sprite.Sprite;
import sprite.SpriteType;
import sprite.nomad.Bomber;
import sprite.nomad.Enemy;
import sprite.nomad.EnemyType;
import sprite.settled.Bomb;
import sprite.settled.Flame;
import sprite.settled.FlameEnd;
import spriteList.ctrl.ActionMethods;
import spriteList.ctrl.GenerationMethodes;

public class SpriteList extends LinkedList<Sprite> {
    private final SpritesSetting spritesSetting;
    private final Map map;
    private final int screenWidth; // width of the screen (expressed in pixel).
    private final int screenHeight; // height of the screen (expressed in pixel).

    // create a temporary list to manage addings and avoid concurent accesses.
    private final LinkedList<Sprite> tmpList = new LinkedList<>();

    public SpriteList(SpritesSetting spritesSetting, Map map, int screenWidth, int screenHeight) {
        this.spritesSetting = spritesSetting;
        this.map = map;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    /**
     * Randomly (create) and place sprites on the map.
     *
     * @throws CannotPlaceEnemyOnMapException if all the requested sprites cannot be placed on map
     */
    public void generateSprites() throws CannotPlaceEnemyOnMapException {
        if (spritesSetting == null) {
            throw new RuntimeException("generateSprites() cannot be called without providing a spritesSetting.");
        }

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
        GenerationMethodes.randomlyPlaceEnemy(this, EnemyType.CLOAKED_SKELETON,
                spritesSetting.getNbCloakedSkeleton(), emptyPtList);
        // - meca angel
        GenerationMethodes.randomlyPlaceEnemy(this, EnemyType.MECA_ANGEL,
                spritesSetting.getNbMecaAngel(), emptyPtList);
        // - mummy
        GenerationMethodes.randomlyPlaceEnemy(this, EnemyType.MUMMY,
                spritesSetting.getNbMummy(), emptyPtList);
    }

    /**
     * Process sprite's action and clean the latter if needed.
     */
    public synchronized void update(int pressedKey) {
        for (ListIterator<Sprite> iterator = this.listIterator(); iterator.hasNext(); ) {
            Sprite sprite = iterator.next();
            boolean shouldBeRemoved;
            switch (sprite.getSpriteType()) { // process the sprite's action.
            case BOMBER: {
                shouldBeRemoved = ActionMethods.processBomber(this, tmpList, map.getMapPointMatrix(), map.getMapWidth(),
                        map.getMapHeight(), (Bomber) sprite, pressedKey);
                break;
            }
            case ENEMY: {
                shouldBeRemoved = ActionMethods.processEnemy(this, map.getMapPointMatrix(), map.getMapWidth(),
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
            case FLAME_END: {
                shouldBeRemoved = ActionMethods.processFlameEnd((FlameEnd) sprite);
                break;
            }
            default: {
                throw new RuntimeException("the SpriteType \"" +
                        SpriteType.getlabel(sprite.getSpriteType()).orElse("n/a") +
                        "\" is not handled by the switch.");
            }
            }
            if (shouldBeRemoved) { // should the sprite be removed from the list?
                iterator.remove();
                continue;
            }
            sprite.updateImage(); // update the sprite's images.
        }
        if (!tmpList.isEmpty()) {
            this.addAll(tmpList); // add sprites from the temporary list to the main one.
            tmpList.clear(); // clear the temporary list.
        }
    }

    /**
     * Paint the visible sprites on screen.
     *
     * @param g    the graphics context
     * @param xMap the map abscissa from which painting nomads
     * @param yMap the map ordinate from which painting nomads
     */
    public synchronized void paintBuffer(Graphics2D g, int xMap, int yMap) {

        // paint sprites.
        for (Sprite sprite : this) {
            if ((sprite.getCurImage() != null)) { // happens when the bomber is invincible.
                if ((sprite.getYMap() >= yMap)
                        && (sprite.getYMap() <= yMap + sprite.getCurImage().getWidth(null) + screenHeight + ImagesLoader.IMAGE_SIZE)
                        && (sprite.getXMap() >= xMap - sprite.getCurImage().getWidth(null) / 2)
                        && (sprite.getXMap() <= xMap + sprite.getCurImage().getHeight(null) / 2 + screenWidth + ImagesLoader.IMAGE_SIZE)) {
                    sprite.paintBuffer(g, sprite.getXMap() - xMap, sprite.getYMap() - yMap);
                }
            }
        }
    }
}
