package spriteList;

import exceptions.CannotPlaceEnemyOnMapException;
import map.Map;
import map.MapPoint;
import sprite.Sprite;
import sprite.nomad.*;
import sprite.settled.Bomb;
import sprite.settled.Bonus;
import sprite.settled.Flame;
import sprite.settled.FlameEnd;
import spriteList.ctrl.ActionMethods;
import spriteList.ctrl.GenerationMethods;
import utils.CurrentTimeSupplier;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import static images.ImagesLoader.IMAGE_SIZE;

public class SpriteList extends LinkedList<Sprite> {
    protected CurrentTimeSupplier currentTimeSupplier = new CurrentTimeSupplier();

    private final SpritesSetting spritesSetting;
    private final Map map;
    private final int screenWidth; // width of the screen (expressed in pixel).
    private final int screenHeight; // height of the screen (expressed in pixel).

    private long birdsArrivalLastTs; // the last ts a group of bird has been added to the list.

    // create a temporary list to manage addings and avoid concurent accesses.
    private final LinkedList<Sprite> tmpList = new LinkedList<>();

    public SpriteList(SpritesSetting spritesSetting, Map map, int screenWidth, int screenHeight) {
        this.spritesSetting = spritesSetting;
        this.map = map;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.birdsArrivalLastTs = currentTimeSupplier.get().toEpochMilli();
    }

    /**
     * Randomly (create) and place sprites on the map.
     */
    public void generateSprites() {
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

        try {
            // - zora.
            GenerationMethods.randomlyPlaceEnemies(this,
                    EnemyType.TYPE_ENEMY_ZORA,
                    spritesSetting.getNbZora(),
                    emptyPtList);

            // - green soldier.
            GenerationMethods.randomlyPlaceEnemies(this,
                    EnemyType.TYPE_ENEMY_GREEN_SOLDIER,
                    spritesSetting.getNbGreenSoldier(),
                    emptyPtList);

            // - red spear soldier.
            GenerationMethods.randomlyPlaceEnemies(this,
                    EnemyType.TYPE_ENEMY_RED_SPEAR_SOLDIER,
                    spritesSetting.getNbRedSpearSoldier(),
                    emptyPtList);
        } catch (CannotPlaceEnemyOnMapException e) {
            System.out.print(e.getMessage() + "\n"); // log only, not very important.
        }
    }

    /**
     * Process sprite's action and clean the latter if needed.
     */
    public synchronized void update(int pressedKey) {

        // process sprites.
        for (ListIterator<Sprite> iterator = this.listIterator(); iterator.hasNext(); ) {
            Sprite sprite = iterator.next();
            boolean shouldBeRemoved;
            switch (sprite.getSpriteType()) { // process the sprite's action.
                case TYPE_SPRITE_BOMB: {
                    shouldBeRemoved = ActionMethods.processBomb(tmpList, map.getMapPointMatrix(), map.getMapWidth(),
                            map.getMapHeight(), (Bomb) sprite);
                    break;
                }
                case TYPE_SPRITE_BOMBER: {
                    shouldBeRemoved = ActionMethods.processBomber(this, tmpList, map.getMapPointMatrix(), map.getMapWidth(),
                            map.getMapHeight(), (Bomber) sprite, pressedKey);
                    break;
                }
                case TYPE_SPRITE_BONUS: {
                    shouldBeRemoved = ActionMethods.processBonus(map.getMapPointMatrix(), (Bonus) sprite);
                    break;
                }
                case TYPE_SPRITE_BREAKING_ENEMY: {
                    shouldBeRemoved = ActionMethods.processBreakingEnemy(this, tmpList, map.getMapPointMatrix(),
                            map.getMapWidth(), map.getMapHeight(), (BreakingEnemy) sprite);
                    break;
                }
                case TYPE_SPRITE_FLAME: {
                    shouldBeRemoved = ActionMethods.processFlame(tmpList, map.getMapPointMatrix(), (Flame) sprite);
                    break;
                }
                case TYPE_SPRITE_FLAME_END: {
                    shouldBeRemoved = ActionMethods.processFlameEnd((FlameEnd) sprite);
                    break;
                }
                case TYPE_SPRITE_FLYING_NOMAD: {
                    shouldBeRemoved = ActionMethods.processFlyingNomad(map.getMapWidth(), map.getMapHeight(), (FlyingNomad) sprite);
                    break;
                }
                case TYPE_SPRITE_WALKING_ENEMY: {
                    shouldBeRemoved = ActionMethods.processWalkingEnemy(this, map.getMapPointMatrix(), map.getMapWidth(),
                            map.getMapHeight(), (WalkingEnemy) sprite);
                    break;
                }
                default: {
                    shouldBeRemoved = false;
                    /*throw new RuntimeException("the SpriteType \""
                            + SpriteType.getlabel(sprite.getSpriteType()).orElse("n/a")
                            + "\" is not handled by the switch.");*/
                }
            }
            if (shouldBeRemoved) { // should the sprite be removed from the list?
                iterator.remove();
                continue;
            }
            sprite.updateImage(); // update the sprite's images.
        }

        // add birds (every X ms).
        long currentTs = currentTimeSupplier.get().toEpochMilli();
        if (spritesSetting.getBirdsArrivalTimeInterval() != 0 && // birds arrival is set0
                birdsArrivalLastTs + (spritesSetting.getBirdsArrivalTimeInterval()) <= currentTs) {
            GenerationMethods.randomlyPlaceAGroupOfBird(this, screenWidth, screenHeight,
                    map.getMapWidth() * IMAGE_SIZE, map.getMapHeight() * IMAGE_SIZE);
            birdsArrivalLastTs = currentTs;
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
                if ((sprite.getyMap() >= yMap)
                        && (sprite.getyMap() <= yMap + sprite.getCurImage().getWidth(null) + screenHeight + IMAGE_SIZE)
                        && (sprite.getxMap() >= xMap - sprite.getCurImage().getWidth(null) / 2)
                        && (sprite.getxMap() <= xMap + sprite.getCurImage().getHeight(null) / 2 + screenWidth + IMAGE_SIZE)) {
                    sprite.paintBuffer(g, sprite.getxMap() - xMap, sprite.getyMap() - yMap);
                }
            }
        }
    }
}
