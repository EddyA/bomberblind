package spriteList.ctrl;

import java.util.LinkedList;

import map.MapPoint;
import sprite.Sprite;
import sprite.nomad.Bomber;
import sprite.nomad.Enemy;
import sprite.settled.Bomb;
import sprite.settled.Flame;
import sprite.settled.FlameEnd;

/**
 * A collection of methods to add sprites to a list according to a map status.
 */
public class AddingMethods {

    /**
     * Add a bomber to the list.
     *
     * @param list   the list into which adding the sprite
     * @param bomber the bomber to add
     */
    public static void addBomber(LinkedList<Sprite> list, Bomber bomber) {
        list.add(bomber);
    }

    /**
     * Add an enemy to the list.
     *
     * @param list  the list into which adding the sprite
     * @param enemy the enemy to add
     */
    public static void addEnemy(LinkedList<Sprite> list, Enemy enemy) {
        list.add(enemy);
    }

    /**
     * Add a bomb to a list.
     * The bomb is adding if:
     * - it is not a bombing case,
     * - AND it is not a burning case.
     *
     * @param list           the list into which adding the bomb
     * @param mapPointMatrix the map (represented by its matrix of MapPoint)
     * @param bomb           the bomb to add
     */
    public static void addBomb(LinkedList<Sprite> list, MapPoint[][] mapPointMatrix, Bomb bomb) {
        // to avoid adding several bombs when key is pressed.
        if (!mapPointMatrix[bomb.getRowIdx()][bomb.getColIdx()].isBombing() &&
                // to avoid adding a bomb on a burning case when the character is invicible.
                !mapPointMatrix[bomb.getRowIdx()][bomb.getColIdx()].isBurning()) {
            mapPointMatrix[bomb.getRowIdx()][bomb.getColIdx()].setBombing(true);
            list.add(bomb);
        }
    }

    /**
     * Add a flame to a list.
     * The flame is adding if:
     * - it is a pathway, in this case the function returns true as the flame can be propagated,
     * - OR it is a mutable or a bomb, in this case the function returns false as the flame cannot be propagated.
     *
     * @param list           the list into which adding the flame
     * @param mapPointMatrix the map (represented by its matrix of MapPoint)
     * @param flame          the flame to add
     * @return true if the flame can be propagated, false it is stopped
     */
    public static boolean addFlame(LinkedList<Sprite> list, MapPoint[][] mapPointMatrix, Flame flame) {
        if (mapPointMatrix[flame.getRowIdx()][flame.getColIdx()].isPathway()) {
            mapPointMatrix[flame.getRowIdx()][flame.getColIdx()].addFlame();
            mapPointMatrix[flame.getRowIdx()][flame.getColIdx()].setImageAsBurned();
            list.add(flame);
            return true; // the next case should be tested.
        } else if (mapPointMatrix[flame.getRowIdx()][flame.getColIdx()].isMutable() ||
                mapPointMatrix[flame.getRowIdx()][flame.getColIdx()].isBombing()) {
            mapPointMatrix[flame.getRowIdx()][flame.getColIdx()].setPathway(true);
            mapPointMatrix[flame.getRowIdx()][flame.getColIdx()].setMutable(false);
            mapPointMatrix[flame.getRowIdx()][flame.getColIdx()].addFlame();
            mapPointMatrix[flame.getRowIdx()][flame.getColIdx()].setImageAsBurned();
            list.add(flame);
            return false; // the next case should NOT be tested.
        } else {
            return false; // the next case should NOT be tested.
        }

    }

    /**
     * Add a set of flames to represent a bomb explosion.
     *
     * @param list           the list into which adding the flames
     * @param mapPointMatrix the map (represented by its matrix of MapPoint)
     * @param mapWidth       the map width
     * @param mapHeight      the map height
     * @param centralRowIdx  the map row index of the central flame
     * @param centralColIdx  the map column index of the central flame
     * @param flameSize      the flame size
     */
    public static void addFlames(LinkedList<Sprite> list, MapPoint[][] mapPointMatrix, int mapWidth, int mapHeight,
            int centralRowIdx, int centralColIdx, int flameSize) {
        Flame flame;

        // place left flames.
        for (int i = 1, j = centralColIdx - 1; i <= flameSize && j >= 0; i++, j--) { // from center to left.
            flame = new Flame(centralRowIdx, centralColIdx - i);
            if (!addFlame(list, mapPointMatrix, flame)) {
                break; // break loop.
            }
        }

        // place right flames.
        for (int i = 1, j = centralColIdx + 1; i <= flameSize && j < mapWidth; i++, j++) { // from center to right.
            flame = new Flame(centralRowIdx, centralColIdx + i);
            if (!addFlame(list, mapPointMatrix, flame)) {
                break; // break loop.
            }
        }

        // place upper flames.
        for (int i = 1, j = centralRowIdx - 1; i <= flameSize && j >= 0; i++, j--) { // from center to top.
            flame = new Flame(centralRowIdx - i, centralColIdx);
            if (!addFlame(list, mapPointMatrix, flame)) {
                break; // break loop.
            }
        }

        // place lower flames.
        for (int i = 1, j = centralRowIdx + 1; i <= flameSize && j < mapHeight; i++, j++) { // from center to bottom.
            flame = new Flame(centralRowIdx + i, centralColIdx);
            if (!addFlame(list, mapPointMatrix, flame)) {
                break; // break loop.
            }
        }
        addFlame(list, mapPointMatrix, new Flame(centralRowIdx, centralColIdx)); // central case.
    }

    /**
     * Add a flame end to a list.
     *
     * @param list   the list into which adding the flame
     * @param  flameEnd the flame end to add
     */
    public static void addFlameEnd(LinkedList<Sprite> list, FlameEnd flameEnd) {
        list.add(flameEnd);
    }
}