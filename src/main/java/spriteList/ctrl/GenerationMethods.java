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
import static utils.Direction.*;

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
            Random R = new Random(); // randomly get a point.
            int caseIdx = Math.abs(R.nextInt(emptyPtList.size()));
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
     * Randomly add a group of birds.
     * The random conditions:
     * - a random number of birds between [3; 6],
     * - a random direction,
     * - a random deviation between [-8; 8],
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
        int deviation = random.nextInt(8) * (random.nextInt(1) == 0 ? -1 : 1);

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

    /**
     * Randomly place a bundle of bonus from a map point.
     *
     * @param list           the list into which adding the bonus
     * @param mapPointMatrix the map (represented by its matrix of MapPoint)
     * @param mapWidth       the map width
     * @param mapHeight      the map height
     * @param rowIdx         the rowIdx from which adding the bonus
     * @param colIdx         the colIdx from which adding the bonus
     * @param bonusBundle    the list of bonus to place
     */
    public static void randomlyPlaceBonusFromAMapPoint(LinkedList<Sprite> list,
                                                       MapPoint[][] mapPointMatrix,
                                                       int mapWidth,
                                                       int mapHeight,
                                                       int rowIdx,
                                                       int colIdx,
                                                       Map<BonusType, Integer> bonusBundle) {
        int nbBonusToPlace = 0;
        ArrayList<BonusType> bonusTypesToPlace = new ArrayList<>();
        for (Map.Entry<BonusType, Integer> bonusEntry : bonusBundle.entrySet()) {
            if (bonusEntry.getValue() != 0) {
                nbBonusToPlace += bonusEntry.getValue();
                bonusTypesToPlace.add(bonusEntry.getKey());
            }
        }
        if (rowIdx < 0 || rowIdx >= mapWidth || colIdx < 0 || colIdx >= mapHeight || // out of map.
                nbBonusToPlace == 0) { // no more bonus to place.
            return; // end of recursivity.
        }

        // randomly get a BonusType (among the available ones) and try to place it onto the current map point.
        BonusType rbonusType = bonusTypesToPlace.get(random.nextInt(bonusTypesToPlace.size()));
        boolean bonusAdded;
        switch (rbonusType) {
            case TYPE_BONUS_BOMB:
                bonusAdded = AddingMethods.addBonus(list, mapPointMatrix, new BonusBomb(rowIdx, colIdx));
                break;
            case TYPE_BONUS_FLAME:
                bonusAdded = AddingMethods.addBonus(list, mapPointMatrix, new BonusFlame(rowIdx, colIdx));
                break;
            case TYPE_BONUS_ROLLER:
                bonusAdded = AddingMethods.addBonus(list, mapPointMatrix, new BonusRoller(rowIdx, colIdx));
                break;
            default: // should not happen.
                throw new RuntimeException("the BonusType \""
                        + BonusType.getlabel(rbonusType).orElse("n/a")
                        + "\" is not handled by the switch.");
        }
        if (bonusAdded) {
            bonusBundle.put(rbonusType, bonusBundle.get(rbonusType) - 1);
        }

        // recursivly explore other directions.b
        Set<Direction> checkedDirections = new HashSet<>();
        do {

            Direction rDirection = Direction.getRandomDirectionWithExclusion(checkedDirections);
            GenerationMethods.randomlyPlaceBonusFromAMapPoint(list,
                    mapPointMatrix,
                    mapWidth,
                    mapHeight,
                    rDirection == DIRECTION_NORTH ? rowIdx - 1 : rDirection == DIRECTION_SOUTH ? rowIdx + 1 : rowIdx,
                    rDirection == DIRECTION_WEST ? colIdx - 1 : rDirection == DIRECTION_EAST ? colIdx + 1 : colIdx,
                    bonusBundle);
            checkedDirections.add(rDirection); // mark the random direction as checked.
        } while (checkedDirections.size() != 4);
    }

}