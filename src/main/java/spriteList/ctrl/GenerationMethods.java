package spriteList.ctrl;

import exceptions.CannotPlaceEnemyOnMapException;
import map.MapPoint;
import sprite.Sprite;
import sprite.nomad.*;
import sprite.settled.BonusBomb;
import sprite.settled.BonusFlame;
import sprite.settled.BonusRoller;
import sprite.settled.BonusType;
import spriteList.SpriteList;
import utils.Direction;

import java.util.*;

import static images.ImagesLoader.IMAGE_SIZE;
import static spriteList.ctrl.AddingMethods.addBreakingEnemy;
import static spriteList.ctrl.AddingMethods.addWalkingEnemy;

public class GenerationMethods {

    // init a Random for the whole process.
    private final static Random random;

    static {
        random = new Random();
    }

    /**
     * Place a certain number of elements of a certain type of enemy.
     *
     * @param spriteList  the list into which adding the enemy
     * @param enemyType   the type of enemy to place
     * @param nbElt       the number of elements to place
     * @param emptyPtList the list of empty points (available points to place the enemies)
     * @throws CannotPlaceEnemyOnMapException if a sprite cannot be placed on map
     */
    public static void randomlyPlaceEnemies(SpriteList spriteList,
                                            EnemyType enemyType,
                                            int nbElt,
                                            List<MapPoint> emptyPtList) throws CannotPlaceEnemyOnMapException {
        for (int i = 0; i < nbElt; i++) {
            if (emptyPtList.isEmpty()) {
                String msg = "cannot (create) and place a '" + EnemyType.getlabel(enemyType).orElse("no_name") +
                        "' on map, the list of empty point is empty.";
                throw new CannotPlaceEnemyOnMapException(msg);
            }
            int caseIdx = Math.abs(random.nextInt(emptyPtList.size()));
            int xMap = emptyPtList.get(caseIdx).getColIdx() * IMAGE_SIZE + IMAGE_SIZE / 2;
            int yMap = emptyPtList.get(caseIdx).getRowIdx() * IMAGE_SIZE + IMAGE_SIZE / 2;

            // create the enemy.
            switch (enemyType) {
                case TYPE_ENEMY_ZORA: {
                    addWalkingEnemy(spriteList, new Zora(xMap, yMap));
                    break;
                }
                case TYPE_ENEMY_GREEN_SOLDIER: {
                    addWalkingEnemy(spriteList, new GreenSoldier(xMap, yMap));
                    break;
                }
                case TYPE_ENEMY_RED_SPEAR_SOLDIER: {
                    addBreakingEnemy(spriteList, new RedSpearSoldier(xMap, yMap));
                    break;
                }
            }
            emptyPtList.remove(caseIdx); // remove the current point from the list of empty points.
        }
    }

    /**
     * Randomly place a bundle of bonus.
     *
     * @param list           the list into which adding the bonus
     * @param mapPointMatrix the map (represented by its matrix of MapPoint)
     * @param mapWidth       the map width
     * @param mapHeight      the map height
     * @param bonusBundle    the list of bonus to place
     */
    public static void randomlyPlaceBonusFromAMapPoint(LinkedList<Sprite> list,
                                                       MapPoint[][] mapPointMatrix,
                                                       int mapWidth,
                                                       int mapHeight,
                                                       Map<BonusType, Integer> bonusBundle) {

        // create a list of empty points (available points to place the bonus).
        List<MapPoint> emptyPtList = new ArrayList<>();
        for (int rowIdx = 0; rowIdx < mapHeight; rowIdx++) {
            for (int colIdx = 0; colIdx < mapWidth; colIdx++) {
                if (mapPointMatrix[rowIdx][colIdx].isPathway() && // the checked point is a pathway
                        !mapPointMatrix[rowIdx][colIdx].isBonusing()) { // AND is not bonusing.
                    emptyPtList.add(mapPointMatrix[rowIdx][colIdx]);
                }
            }
        }

        // randomly place bonus.
        for (Map.Entry<BonusType, Integer> bonusEntry : bonusBundle.entrySet()) { // for all types of bonus.
            for (int bonusIdx = 0; bonusIdx < bonusEntry.getValue(); bonusIdx++) {
                int caseIdx = Math.abs(random.nextInt(emptyPtList.size()));
                switch (bonusEntry.getKey()) {
                    case TYPE_BONUS_BOMB:
                        AddingMethods.addBonus(list, mapPointMatrix,
                                new BonusBomb(emptyPtList.get(caseIdx).getRowIdx(), emptyPtList.get(caseIdx).getColIdx()));
                        break;
                    case TYPE_BONUS_FLAME:
                        AddingMethods.addBonus(list, mapPointMatrix,
                                new BonusFlame(emptyPtList.get(caseIdx).getRowIdx(), emptyPtList.get(caseIdx).getColIdx()));
                        break;
                    case TYPE_BONUS_ROLLER:
                        AddingMethods.addBonus(list, mapPointMatrix,
                                new BonusRoller(emptyPtList.get(caseIdx).getRowIdx(), emptyPtList.get(caseIdx).getColIdx()));
                        break;
                    default: // should not happen.
                        throw new RuntimeException("the BonusType \""
                                + BonusType.getlabel(bonusEntry.getKey()).orElse("n/a")
                                + "\" is not handled by the switch.");
                }
            }
        }
    }

    /**
     * Randomly add a group of birds.
     * The random conditions:
     * - a random number of birds between [3; 6],
     * - a random direction,
     * - a random deviation between [-16; -8] and [8; 16],
     * - a random ordinate.
     *
     * @param spriteList   the list into which adding the birds
     * @param screenWidth  the screen width (in px)
     * @param screenHeight the screen height (in px)
     * @param mapWidth     the map width (in px)
     * @param mapHeight    the map height (in px)
     */
    public static void randomlyPlaceAGroupOfBird(SpriteList spriteList,
                                                 int screenWidth,
                                                 int screenHeight,
                                                 int mapWidth,
                                                 int mapHeight) {
        int nbElts = 3 + random.nextInt(3);
        Direction direction = Direction.getRandomDirection();
        int deviation = (8 + random.nextInt(8)) * (random.nextInt(1) == 0 ? -1 : 1);

        switch (direction) {
            case DIRECTION_NORTH: {
                int fstXChar = screenWidth + random.nextInt(mapWidth - screenWidth); // a random abscissa.
                int fstYChar = mapHeight + (nbElts * IMAGE_SIZE);
                placeAGroupOfBird(spriteList, nbElts, fstXChar, fstYChar, direction, deviation);
                break;
            }
            case DIRECTION_SOUTH: {
                int fstXChar = screenWidth + random.nextInt(mapWidth - screenWidth); // a random abscissa.
                int fstYChar = -(nbElts * IMAGE_SIZE);
                placeAGroupOfBird(spriteList, nbElts, fstXChar, fstYChar, direction, deviation);
                break;
            }
            case DIRECTION_WEST: {
                int fstXChar = mapWidth + (nbElts * IMAGE_SIZE);
                int fstYChar = screenHeight + random.nextInt(mapHeight - screenHeight); // a random ordinate.
                placeAGroupOfBird(spriteList, nbElts, fstXChar, fstYChar, direction, deviation);
                break;
            }
            case DIRECTION_EAST: {
                int fstXChar = -(nbElts * IMAGE_SIZE);
                int fstYChar = screenHeight + random.nextInt(mapHeight - screenHeight); // a random ordinate.
                placeAGroupOfBird(spriteList, nbElts, fstXChar, fstYChar, direction, deviation);
                break;
            }
        }
    }

    /**
     * Add a group of bird to a SpriteList.
     *
     * @param spriteList the list into which adding the birds
     * @param nbBirds    the number of birds
     * @param fstXChar   the abscissa of the first bird
     * @param fstYChar   the ordinate of the first bird
     * @param direction  the birds direction
     * @param deviation  the birds deviation
     */
    public static void placeAGroupOfBird(SpriteList spriteList,
                                         int nbBirds,
                                         int fstXChar,
                                         int fstYChar,
                                         Direction direction,
                                         int deviation) {
        for (int eltIdx = 0; eltIdx < nbBirds; eltIdx++) {
            switch (direction) {
                case DIRECTION_NORTH: {
                    int xChar = fstXChar - ((eltIdx + 1) / 2 * IMAGE_SIZE * (eltIdx % 2 == 0 ? 1 : -1));
                    int yChar = fstYChar + ((eltIdx + 1) / 2) * 50;
                    AddingMethods.addFlyingNomad(spriteList, new WhiteBird(xChar, yChar, direction, deviation));
                    break;
                }
                case DIRECTION_SOUTH: {
                    int xChar = fstXChar + ((eltIdx + 1) / 2 * IMAGE_SIZE * (eltIdx % 2 == 0 ? 1 : -1));
                    int yChar = fstYChar - ((eltIdx + 1) / 2) * 50;
                    AddingMethods.addFlyingNomad(spriteList, new WhiteBird(xChar, yChar, direction, deviation));
                    break;
                }
                case DIRECTION_WEST: {
                    int xChar = fstXChar + ((eltIdx + 1) / 2) * 50;
                    int yChar = fstYChar - ((eltIdx + 1) / 2 * IMAGE_SIZE * (eltIdx % 2 == 0 ? 1 : -1));
                    AddingMethods.addFlyingNomad(spriteList, new WhiteBird(xChar, yChar, direction, deviation));
                    break;
                }
                case DIRECTION_EAST: {
                    int xChar = fstXChar - ((eltIdx + 1) / 2) * 50;
                    int yChar = fstYChar + ((eltIdx + 1) / 2 * IMAGE_SIZE * (eltIdx % 2 == 0 ? 1 : -1));
                    AddingMethods.addFlyingNomad(spriteList, new WhiteBird(xChar, yChar, direction, deviation));
                    break;
                }
            }
        }
    }
}