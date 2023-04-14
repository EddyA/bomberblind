package spritelist.ctrl;

import java.awt.event.KeyEvent;
import java.util.List;
import ai.NomadAi;
import static images.ImagesLoader.IMAGE_SIZE;
import lombok.experimental.UtilityClass;
import map.MapPoint;
import map.ctrl.NomadMethods;
import static map.ctrl.NomadMethods.isNomadBlockedOffByMutableObstacle;
import static map.ctrl.NomadMethods.isNomadBurning;
import sprite.Sprite;
import static sprite.SpriteAction.ACTION_BREAKING;
import static sprite.SpriteAction.ACTION_DYING;
import static sprite.SpriteAction.ACTION_WAITING;
import static sprite.SpriteAction.ACTION_WALKING;
import static sprite.SpriteAction.ACTION_WINING;
import static sprite.ctrl.NomadMethods.isNomadCrossingBonus;
import static sprite.ctrl.NomadMethods.isNomadCrossingEnemy;
import sprite.nomad.Bomber;
import sprite.nomad.BreakingEnemy;
import sprite.nomad.FlyingNomad;
import sprite.nomad.WalkingEnemy;
import sprite.settled.Bomb;
import sprite.settled.Bonus;
import static sprite.settled.BonusType.TYPE_BONUS_BOMB;
import static sprite.settled.BonusType.TYPE_BONUS_FLAME;
import static sprite.settled.BonusType.TYPE_BONUS_HEART;
import sprite.settled.Flame;
import sprite.settled.FlameEnd;
import sprite.settled.LoopedSettled;
import static spritelist.ctrl.AddingMethods.addBomb;
import utils.Direction;
import static utils.Direction.DIRECTION_EAST;
import static utils.Direction.DIRECTION_NORTH;
import static utils.Direction.DIRECTION_SOUTH;
import static utils.Direction.DIRECTION_WEST;
import static utils.Tools.getCharColIdx;
import static utils.Tools.getCharRowIdx;

/**
 * Define a collection of methods to process sprites.
 * These methods can:
 * - update the sprite's action (e.g. change the enemy direction),
 * - check if the sprite should be removed from the list of sprites (e.g. the enemy is dead),
 * - add other sprites according to the sprite's action (e.g. add flames if a bomb is exploding).
 */
@UtilityClass
public class ActionMethods {

    /**
     * - Notice that the bomb must be removed from the list (if the sprite is finished),
     * -- AND add flames,
     * -- AND remove the bombing status of the relative case.
     * - OR ended the bomb (if the bomb is on a burning case),
     * - OR do nothing.
     *
     * @param tmpList        the temporary list of sprites to add new elements
     * @param mapPointMatrix mapPointMatrix the map (represented by its matrix of MapPoint)
     * @param mapWidth       the map width
     * @param mapHeight      the map height
     * @param bomb           the bomb
     * @return true if the bomb should be removed from the list, false otherwise.
     */
    public static boolean processBomb(List<Sprite> tmpList,
                                      MapPoint[][] mapPointMatrix,
                                      int mapWidth,
                                      int mapHeight,
                                      Bomb bomb) {
        boolean shouldBeRemoved = false;
        if (bomb.isFinished()) {
            AddingMethods.addFlames(tmpList, mapPointMatrix, mapWidth, mapHeight, bomb.getRowIdx(), bomb.getColIdx(),
                bomb.getFlameSize()); // create flames.
            mapPointMatrix[bomb.getRowIdx()][bomb.getColIdx()].setBombing(false);
            shouldBeRemoved = true;

        } else if (mapPointMatrix[bomb.getRowIdx()][bomb.getColIdx()].isBurning()) { // is it on a burning case?
            bomb.setStatus(LoopedSettled.Status.STATUS_ENDED);
        }
        return shouldBeRemoved;
    }

    /**
     * - Re-init the bomber (if the bomber is finished - i.e. dead and the sprite ended),
     * - OR kill the bomber (if the bomber is on a burning case or is crossing an enemy),
     * - OR the bomber acts (if it is time to act),
     * - OR do nothing.
     *
     * @param list           the list of sprites
     * @param tmpList        the temporary list to add new sprites
     * @param mapPointMatrix mapPointMatrix the map (represented by its matrix of MapPoint)
     * @param mapWidth       the map width
     * @param mapHeight      the map height
     * @param bomber         the bomber
     * @param pressedKey     the pressed key
     * @return true if the bomber should be removed from the list, false otherwise.
     */
    public static boolean processBomber(List<Sprite> list,
                                        List<Sprite> tmpList,
                                        MapPoint[][] mapPointMatrix,
                                        int mapWidth,
                                        int mapHeight,
                                        Bomber bomber,
                                        int pressedKey) {
        boolean shouldBeRemoved = false;

        // handle bonus.
        Bonus bonus = isNomadCrossingBonus(list, bomber.getXMap(), bomber.getYMap());
        if (bonus != null) { // the bomber is crossing a bonus.
            bomber.setBonus(bonus.getBonusType(), bomber.getBonus(bonus.getBonusType()) + 1); // add the bonus.
            bonus.setStatus(Bonus.Status.STATUS_ENDED); // end the bonus.
        }

        // act bomber.
        if (bomber.isFinished()) {
            bomber.setBonus(TYPE_BONUS_HEART, bomber.getBonus(TYPE_BONUS_HEART) - 1); // remove a life.

            // randomly replace bonus from the bomber's end point.
            GenerationMethods.randomlyPlaceBonusFromAMapPoint(tmpList,
                    mapPointMatrix,
                    mapWidth,
                    mapHeight,
                    bomber.getCollectedBonus());

            shouldBeRemoved = bomber.init(); // re-init the bomber if finished.
        } else if (bomber.getCurSpriteAction() != ACTION_DYING) { // the bomber is not finished and not dead.

            // should the bomber die?
            if (!bomber.isInvincible() &&
                (isNomadBurning(mapPointMatrix, bomber.getXMap(), bomber.getYMap()) ||
                    isNomadCrossingEnemy(list, bomber.getXMap(), bomber.getYMap(), bomber))) {
                bomber.setCurSpriteAction(ACTION_DYING);

            } else if (bomber.isTimeToAct()) { // it is time to act.
                switch (pressedKey) {
                    case 0 -> bomber.setCurSpriteAction(ACTION_WAITING);
                    case KeyEvent.VK_UP -> {
                        bomber.setCurSpriteAction(ACTION_WALKING);
                        bomber.setCurDirection(DIRECTION_NORTH);
                        if (!NomadMethods.isNomadCrossingMapLimit(mapWidth, mapHeight,
                            bomber.getXMap(), bomber.getYMap() - 1)) {
                            if (!NomadMethods.isNomadCrossingObstacle(mapPointMatrix, bomber.getXMap(),
                                bomber.getYMap() - 1) &&
                                !NomadMethods.isNomadCrossingBomb(mapPointMatrix, bomber.getXMap(),
                                    bomber.getYMap() - 1, DIRECTION_NORTH)) {
                                bomber.setYMap(bomber.getYMap() - 1);
                            } else {
                                shiftBomberIfPossible(mapPointMatrix, bomber, DIRECTION_NORTH);
                            }
                        }
                    }
                    case KeyEvent.VK_DOWN -> {
                        bomber.setCurSpriteAction(ACTION_WALKING);
                        bomber.setCurDirection(Direction.DIRECTION_SOUTH);
                        if (!NomadMethods.isNomadCrossingMapLimit(mapWidth, mapHeight,
                            bomber.getXMap(), bomber.getYMap() + 1)) {
                            if (!NomadMethods.isNomadCrossingObstacle(mapPointMatrix, bomber.getXMap(),
                                bomber.getYMap() + 1) &&
                                !NomadMethods.isNomadCrossingBomb(mapPointMatrix, bomber.getXMap(),
                                    bomber.getYMap() + 1, DIRECTION_SOUTH)) {
                                bomber.setYMap(bomber.getYMap() + 1);
                            } else {
                                shiftBomberIfPossible(mapPointMatrix, bomber, DIRECTION_SOUTH);
                            }
                        }
                    }
                    case KeyEvent.VK_LEFT -> {
                        bomber.setCurSpriteAction(ACTION_WALKING);
                        bomber.setCurDirection(DIRECTION_WEST);
                        if (!NomadMethods.isNomadCrossingMapLimit(mapWidth, mapHeight,
                            bomber.getXMap() - 1, bomber.getYMap())) {
                            if (!NomadMethods.isNomadCrossingObstacle(mapPointMatrix, bomber.getXMap() - 1,
                                bomber.getYMap()) &&
                                !NomadMethods.isNomadCrossingBomb(mapPointMatrix, bomber.getXMap() - 1,
                                    bomber.getYMap(), DIRECTION_WEST)) {
                                bomber.setXMap(bomber.getXMap() - 1);
                            } else {
                                shiftBomberIfPossible(mapPointMatrix, bomber, DIRECTION_WEST);
                            }
                        }
                    }
                    case KeyEvent.VK_RIGHT -> {
                        bomber.setCurSpriteAction(ACTION_WALKING);
                        bomber.setCurDirection(Direction.DIRECTION_EAST);
                        if (!NomadMethods.isNomadCrossingMapLimit(mapWidth, mapHeight,
                            bomber.getXMap() + 1, bomber.getYMap())) {
                            if (!NomadMethods.isNomadCrossingObstacle(mapPointMatrix, bomber.getXMap() + 1,
                                bomber.getYMap()) &&
                                !NomadMethods.isNomadCrossingBomb(mapPointMatrix, bomber.getXMap() + 1,
                                    bomber.getYMap(), DIRECTION_EAST)) {
                                bomber.setXMap(bomber.getXMap() + 1);
                            } else {
                                shiftBomberIfPossible(mapPointMatrix, bomber, DIRECTION_EAST);
                            }
                        }
                    }
                    case KeyEvent.VK_B -> {
                        if (bomber.getNbDroppedBomb() < bomber.getBonus(TYPE_BONUS_BOMB)) {
                            Bomb bomb = new Bomb(getCharRowIdx(bomber.getYMap()),
                                getCharColIdx(bomber.getXMap()),
                                bomber.getBonus(TYPE_BONUS_FLAME));
                            if (addBomb(tmpList, mapPointMatrix, bomb)) {
                                bomber.dropBomb(bomb);
                            }
                        }
                    }
                    case KeyEvent.VK_W -> bomber.setCurSpriteAction(ACTION_WINING);
                }
            }
        }
        return shouldBeRemoved;
    }

    /**
     * Shift the bomber of a pixel to help him finding the way (if possible).
     *
     * @param mapPointMatrix mapPointMatrix the map (represented by its matrix of MapPoint)
     * @param bomber the bomber
     * @param direction the bomber's direction
     */
    public static void shiftBomberIfPossible(MapPoint[][] mapPointMatrix, Bomber bomber, Direction direction) {
        int bbManRowIdx = bomber.getYMap() / IMAGE_SIZE;
        int bbManColIdx = bomber.getXMap() / IMAGE_SIZE;
        int bbManRowShift = bomber.getYMap() % IMAGE_SIZE;
        int bbManColShift = bomber.getXMap() % IMAGE_SIZE;

        switch (direction) {
            case DIRECTION_NORTH -> {
                if (mapPointMatrix[bbManRowIdx - 1][bbManColIdx].isPathway() && // the upper case is a pathway
                    !mapPointMatrix[bbManRowIdx - 1][bbManColIdx].isBombing()) { // && !bombing.
                    if (bbManColShift < IMAGE_SIZE / 2) { // bomber on left side of its case.
                        bomber.setXMap(bomber.getXMap() + 1);
                    } else if (bbManColShift > IMAGE_SIZE / 2) { // bomber on right side of its case.
                        bomber.setXMap(bomber.getXMap() - 1);
                    }
                }
            }
            case DIRECTION_SOUTH -> {
                if (mapPointMatrix[bbManRowIdx + 1][bbManColIdx].isPathway() && // the lower case is a pathway
                    !mapPointMatrix[bbManRowIdx + 1][bbManColIdx].isBombing()) { // && !bombing.
                    if (bbManColShift < IMAGE_SIZE / 2) { // bomber on left side of its case.
                        bomber.setXMap(bomber.getXMap() + 1);
                    } else if (bbManColShift > IMAGE_SIZE / 2) { // bomber on right side of its case.
                        bomber.setXMap(bomber.getXMap() - 1);
                    }
                }
            }
            case DIRECTION_WEST -> {
                if (mapPointMatrix[bbManRowIdx][bbManColIdx - 1].isPathway() && // the left case is a pathway
                    !mapPointMatrix[bbManRowIdx][bbManColIdx - 1].isBombing()) { // && !bombing.
                    if (bbManRowShift < IMAGE_SIZE / 2) { // bomber on upper side of its case.
                        bomber.setYMap(bomber.getYMap() + 1);
                    }
                }
                if (mapPointMatrix[bbManRowIdx - 1][bbManColIdx - 1].isPathway() && // the upper/left case is a
                    // pathway
                    !mapPointMatrix[bbManRowIdx - 1][bbManColIdx - 1].isBombing()) { // && !bombing.
                    if (bbManRowShift < IMAGE_SIZE / 2) { // bomber on upper side of its case.
                        bomber.setYMap(bomber.getYMap() - 1);
                    }
                }
            }
            case DIRECTION_EAST -> {
                if (mapPointMatrix[bbManRowIdx][bbManColIdx + 1].isPathway() && // the right case is a pathway
                    !mapPointMatrix[bbManRowIdx][bbManColIdx + 1].isBombing()) { // && !bombing.
                    if (bbManRowShift < IMAGE_SIZE / 2) { // bomber on upper side of its case.
                        bomber.setYMap(bomber.getYMap() + 1);
                    }
                }
                if (mapPointMatrix[bbManRowIdx - 1][bbManColIdx + 1].isPathway() && // the upper/right case is a
                    // pathway
                    !mapPointMatrix[bbManRowIdx - 1][bbManColIdx + 1].isBombing()) { // && !bombing.
                    if (bbManRowShift < IMAGE_SIZE / 2) { // bomber on upper side of its case.
                        bomber.setYMap(bomber.getYMap() - 1);
                    }
                }
            }
        }
    }

    /**
     * - Notice that the bonus must be removed from the list (if the sprite is finished),
     * -- AND remove the bonusing status of the relative case.
     * - OR do nothing.
     *
     * @param mapPointMatrix mapPointMatrix the map (represented by its matrix of MapPoint)
     * @param bonus the bonus
     * @return true if the bonus should be removed from the list, false otherwise.
     */
    public static boolean processBonus(MapPoint[][] mapPointMatrix, Bonus bonus) {
        boolean shouldBeRemoved = false;
        if (bonus.isFinished()) {
            mapPointMatrix[bonus.getRowIdx()][bonus.getColIdx()].setBonusing(false); // the case is no more bonusing.
            shouldBeRemoved = true;
        }
        return shouldBeRemoved;
    }

    /**
     * - Notice that the enemy must be removed from the list (if the enemy is finished - i.e. dead and the sprite
     * ended),
     * - OR kill the enemy (if the enemy is on a burning case),
     * - OR compute the next direction (if it is tim to act),
     * - OR do nothing.
     *
     * @param list           the list of sprites
     * @param tmpList        the temporary list of sprites to add new elements
     * @param mapPointMatrix mapPointMatrix the map (represented by its matrix of MapPoint)
     * @param mapWidth       the map width
     * @param mapHeight      the map height
     * @param breakingEnemy  the enemy
     * @return true if the enemy should be removed from the list, false otherwise.
     */
    public static boolean processBreakingEnemy(List<Sprite> list,
                                               List<Sprite> tmpList,
                                               MapPoint[][] mapPointMatrix,
                                               int mapWidth,
                                               int mapHeight,
                                               BreakingEnemy breakingEnemy) {
        boolean shouldBeRemoved = false;
        if (breakingEnemy.isFinished()) {
            shouldBeRemoved = true;
        } else if (breakingEnemy.getCurSpriteAction() != ACTION_DYING) { // -> the enemy is not dying.

            // should the enemy die?
            if (isNomadBurning(mapPointMatrix, breakingEnemy.getXMap(), breakingEnemy.getYMap())) {
                breakingEnemy.setCurSpriteAction(ACTION_DYING);

            } else if (breakingEnemy.isTimeToAct()) { // it is time to act.
                if (breakingEnemy.getCurSpriteAction() != ACTION_BREAKING) { // -> the enemy is not breaking.

                    // is the nomad blocked off by a mutable?
                    MapPoint mapPointToBreak = isNomadBlockedOffByMutableObstacle(mapPointMatrix,
                        mapWidth,
                        mapHeight,
                        breakingEnemy.getXMap(),
                        breakingEnemy.getYMap(),
                        breakingEnemy.getCurDirection());
                    if (mapPointToBreak != null) { // if so, break the mutable.
                        breakingEnemy.setBreakingMapPoint(mapPointToBreak);
                        breakingEnemy.setCurSpriteAction(ACTION_BREAKING);

                    } else { // else, move.
                        moveEnemyIfPossible(list, mapPointMatrix, mapWidth, mapHeight, breakingEnemy);
                    }
                } else { // -> the enemy is breaking.

                    // is the breaking sprite finished?
                    if (breakingEnemy.isBreakingSpriteFinished()) {

                        // add a flame.
                        AddingMethods.addFlame(tmpList, mapPointMatrix,
                                new Flame(breakingEnemy.getBreakingMapPoint().getRowIdx(),
                                        breakingEnemy.getBreakingMapPoint().getColIdx()));

                        // move.
                        moveEnemyIfPossible(list, mapPointMatrix, mapWidth, mapHeight, breakingEnemy);
                    }
                }
            }
        }
        return shouldBeRemoved;
    }

    /**
     * Move the enemy of a pixel if possible, favoring the current direction and changing of it if needed.
     * If the enemy cannot move (i.e. blocked off), do nothing.
     *
     * @param list           the list of sprites
     * @param mapPointMatrix mapPointMatrix the map (represented by its matrix of MapPoint)
     * @param mapWidth       the map width
     * @param mapHeight      the map height
     * @param walkingEnemy   the enemy
     */
    public static void moveEnemyIfPossible(List<Sprite> list,
                                           MapPoint[][] mapPointMatrix,
                                           int mapWidth,
                                           int mapHeight,
                                           WalkingEnemy walkingEnemy) {
        // compute the next direction.
        Direction newDirection = NomadAi.computeNextDirection(
            mapPointMatrix,
            mapWidth,
            mapHeight,
            list,
            walkingEnemy);

        if (newDirection != null) { // a new direction has been found (i.e. the enemy must change or direction).
            walkingEnemy.setCurSpriteAction(ACTION_WALKING);
            walkingEnemy.setCurDirection(newDirection);
            switch (newDirection) {
                case DIRECTION_NORTH -> walkingEnemy.setYMap(walkingEnemy.getYMap() - 1);
                case DIRECTION_SOUTH -> walkingEnemy.setYMap(walkingEnemy.getYMap() + 1);
                case DIRECTION_WEST -> walkingEnemy.setXMap(walkingEnemy.getXMap() - 1);
                case DIRECTION_EAST -> walkingEnemy.setXMap(walkingEnemy.getXMap() + 1);
            }
        }
    }

    /**
     * - Notice that the flame must be removed from the list (if the sprite is finished),
     * -- AND add a flame end.
     * - OR do nothing.
     *
     * @param tmpList        the temporary list of sprites to add new elements
     * @param mapPointMatrix mapPointMatrix the map (represented by its matrix of MapPoint)
     * @param flame          the flame
     * @return true if the flame should be removed from the list, false otherwise.
     */
    public static boolean processFlame(List<Sprite> tmpList,
                                       MapPoint[][] mapPointMatrix,
                                       Flame flame) {
        boolean shouldBeRemoved = false;
        if (flame.isFinished()) {

            // create flame ends.
            AddingMethods.addFlameEnd(tmpList, new FlameEnd(flame.getRowIdx(), flame.getColIdx()));
            mapPointMatrix[flame.getRowIdx()][flame.getColIdx()].removeFlame();
            shouldBeRemoved = true;
        }
        return shouldBeRemoved;
    }

    /**
     * - Notice that the flame end must be removed from the list (if the sprite is finished),
     * - OR do nothing.
     *
     * @param flameEnd the flame end
     * @return true if the flame end should be removed from the list, false otherwise.
     */
    public static boolean processFlameEnd(FlameEnd flameEnd) {
        return flameEnd.isFinished();
    }

    /**
     * - Notice that the flyingNomad must be removed from the list (if the sprite is finished),
     * - OR ended the flyingNomad (if the flyingNomad is outside the map),
     * - OR compute the next move.
     *
     * @param mapWidth    the map width
     * @param mapHeight   the map height
     * @param flyingNomad the flyingNomad
     * @return true if the flyingNomad should be removed from the list, false otherwise.
     */
    public static boolean processFlyingNomad(int mapWidth, int mapHeight, FlyingNomad flyingNomad) {
        boolean shouldBeRemoved = false;
        int birdWidth = flyingNomad.getCurImage() != null ? flyingNomad.getCurImage().getWidth(null) : 0;
        int birdHeight = flyingNomad.getCurImage() != null ? flyingNomad.getCurImage().getHeight(null) : 0;
        if (flyingNomad.isFinished()) {
            shouldBeRemoved = true;

        } else if (flyingNomad.isTimeToAct()) {
            switch (flyingNomad.getCurDirection()) {
                case DIRECTION_NORTH -> {
                    if (flyingNomad.getYMap() + birdHeight < 0) { // outside the north limit.
                        flyingNomad.setCurSpriteAction(ACTION_DYING);
                    } else {
                        flyingNomad.computeMove();
                    }
                }
                case DIRECTION_SOUTH -> {
                    if (flyingNomad.getYMap() - birdHeight > mapHeight * IMAGE_SIZE) { // outside the south limit.
                        flyingNomad.setCurSpriteAction(ACTION_DYING);
                    } else {
                        flyingNomad.computeMove();
                    }
                }
                case DIRECTION_WEST -> {
                    if (flyingNomad.getXMap() + birdWidth < 0) { // outside the left limit.
                        flyingNomad.setCurSpriteAction(ACTION_DYING);
                    } else {
                        flyingNomad.computeMove();
                    }
                }
                case DIRECTION_EAST -> {
                    if (flyingNomad.getXMap() - birdWidth > mapWidth * IMAGE_SIZE) { // outside the right limit.
                        flyingNomad.setCurSpriteAction(ACTION_DYING);
                    } else {
                        flyingNomad.computeMove();
                    }
                }
            }
        }
        return shouldBeRemoved;
    }

    /**
     * - Notice that the enemy must be removed from the list (if the enemy is finished - i.e. dead and the sprite
     * ended),
     * - OR kill the enemy (if the enemy is on a burning case),
     * - OR compute the next direction (if it is tim to act),
     * - OR do nothing.
     *
     * @param list           the list of sprites
     * @param mapPointMatrix mapPointMatrix the map (represented by its matrix of MapPoint)
     * @param mapWidth       the map width
     * @param mapHeight      the map height
     * @param walkingEnemy   the enemy
     * @return true if the enemy should be removed from the list, false otherwise.
     */
    public static boolean processWalkingEnemy(List<Sprite> list,
                                              MapPoint[][] mapPointMatrix,
                                              int mapWidth,
                                              int mapHeight,
                                              WalkingEnemy walkingEnemy) {
        boolean shouldBeRemoved = false;
        if (walkingEnemy.isFinished()) {
            shouldBeRemoved = true;
        } else if (walkingEnemy.getCurSpriteAction() != ACTION_DYING) { // the enemy is not finished and not dead.

            // should the enemy die?
            if (isNomadBurning(mapPointMatrix, walkingEnemy.getXMap(), walkingEnemy.getYMap())) {
                walkingEnemy.setCurSpriteAction(ACTION_DYING);

            } else if (walkingEnemy.isTimeToAct()) { // it is time to act.
                moveEnemyIfPossible(list, mapPointMatrix, mapWidth, mapHeight, walkingEnemy);
            }
        }
        return shouldBeRemoved;
    }
}
