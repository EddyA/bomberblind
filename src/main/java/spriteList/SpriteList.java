package spriteList;

import exceptions.CannotPlaceEnemyOnMapException;
import map.Map;
import map.MapPoint;
import sprite.Sprite;
import sprite.SpriteType;
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

    private boolean enemiesAreDead; // to know when all enemies are dead.

    // create a temporary list to manage addings and avoid concurent accesses.
    private final LinkedList<Sprite> tmpList = new LinkedList<>();

    public SpriteList(SpritesSetting spritesSetting, Map map, int screenWidth, int screenHeight) {
        this.spritesSetting = spritesSetting;
        this.map = map;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.birdsArrivalLastTs = currentTimeSupplier.get().toEpochMilli();
        this.enemiesAreDead = false; // initially flase :)
    }

    public boolean isEnemiesAreDead() {
        return enemiesAreDead;
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

        // walking enemies:
        // - cloaked skeleton
        GenerationMethods.randomlyPlaceEnemies(this,
                EnemyType.TYPE_ENEMY_CLOAKED_SKELETON,
                spritesSetting.getNbCloakedSkeleton(),
                emptyPtList);
        // - meca angel
        GenerationMethods.randomlyPlaceEnemies(this,
                EnemyType.TYPE_ENEMY_MECA_ANGEL,
                spritesSetting.getNbMecaAngel(),
                emptyPtList);
        // - mummy
        GenerationMethods.randomlyPlaceEnemies(this,
                EnemyType.TYPE_ENEMY_MUMMY,
                spritesSetting.getNbMummy(),
                emptyPtList);

        // breaking enemies:
        // - minotor
        GenerationMethods.randomlyPlaceEnemies(this,
                EnemyType.TYPE_ENEMY_MINOTOR,
                spritesSetting.getNbMinotor(),
                emptyPtList);
    }

    /**
     * Process sprite's action and clean the latter if needed.
     */
    public synchronized void update(int pressedKey) {
        boolean isThereEnemy = false; // firstly considering there is no enemy.

        // process sprites.
        for (ListIterator<Sprite> iterator = this.listIterator(); iterator.hasNext(); ) {
            Sprite sprite = iterator.next();
            boolean shouldBeRemoved;
            switch (sprite.getSpriteType()) { // process the sprite's action.
                case TYPE_BOMB: {
                    shouldBeRemoved = ActionMethods.processBomb(tmpList, map.getMapPointMatrix(), map.getMapWidth(),
                            map.getMapHeight(), (Bomb) sprite);
                    break;
                }
                case TYPE_BOMBER: {
                    shouldBeRemoved = ActionMethods.processBomber(this, tmpList, map.getMapPointMatrix(), map.getMapWidth(),
                            map.getMapHeight(), (Bomber) sprite, pressedKey);
                    break;
                }
                case TYPE_BONUS: {
                    shouldBeRemoved = ActionMethods.processBonus((Bonus) sprite);
                    break;
                }
                case TYPE_BREAKING_ENEMY: {
                    shouldBeRemoved = ActionMethods.processBreakingEnemy(this, tmpList, map.getMapPointMatrix(),
                            map.getMapWidth(), map.getMapHeight(), (BreakingEnemy) sprite);
                    isThereEnemy = true;
                    break;
                }
                case TYPE_FLAME: {
                    shouldBeRemoved = ActionMethods.processFlame(tmpList, map.getMapPointMatrix(), (Flame) sprite);
                    break;
                }
                case TYPE_FLAME_END: {
                    shouldBeRemoved = ActionMethods.processFlameEnd((FlameEnd) sprite);
                    break;
                }
                case TYPE_FLYING_NOMAD: {
                    shouldBeRemoved = ActionMethods.processFlyingNomad(map.getMapWidth(), map.getMapHeight(), (FlyingNomad) sprite);
                    break;
                }
                case TYPE_WALKING_ENEMY: {
                    shouldBeRemoved = ActionMethods.processWalkingEnemy(this, map.getMapPointMatrix(), map.getMapWidth(),
                            map.getMapHeight(), (WalkingEnemy) sprite);
                    isThereEnemy = true;
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
        if (!isThereEnemy) {
            enemiesAreDead = true; // notice the end of the stage.
        }

        // add birds (every X ms).
        long currentTs = currentTimeSupplier.get().toEpochMilli();
        if (birdsArrivalLastTs + (spritesSetting.getBirdsArrivalTimeInterval()) <= currentTs) {
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
