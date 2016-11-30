package spriteList.ctrl;

import java.util.LinkedList;

import map.MapPoint;
import sprite.abstracts.Sprite;
import sprite.nomad.abstracts.Bomber;
import sprite.nomad.abstracts.Enemy;
import sprite.settled.Bomb;
import sprite.settled.ConclusionFlame;
import sprite.settled.Flame;

/**
 * A collection of methods to add sprites to a list according to a map status.
 */
public class AddingMethods {

    /**
     * Add a bomber to the list.
     *
     * @param list the list into which adding the sprite
     * @param bomber the bomber to add
     */
    public static void addBomber(LinkedList<Sprite> list, Bomber bomber) {
        list.add(bomber);
    }

    /**
     * Add an enemy to the list.
     *
     * @param list the list into which adding the sprite
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
     * @param list the list into which adding the bomb
     * @param mapPointMatrix the map (represented by its matrix of MapPoint)
     * @param bomb the bomb to add
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
     * @param list the list into which adding the flame
     * @param mapPointMatrix the map (represented by its matrix of MapPoint)
     * @param rowIdx the map row index of the flame
     * @param colIdx the map column index of the flame
     * @return true if the flame can be propagated, false it is stopped
     */
    public static boolean addFlame(LinkedList<Sprite> list, MapPoint[][] mapPointMatrix, int rowIdx, int colIdx) {
        if (mapPointMatrix[rowIdx][colIdx].isPathway()) {
            mapPointMatrix[rowIdx][colIdx].addFlame();
            mapPointMatrix[rowIdx][colIdx].setImageAsBurned();
            list.add(new Flame(rowIdx, colIdx));
            return true; // the next case should be tested.
        } else if (mapPointMatrix[rowIdx][colIdx].isMutable() ||
                mapPointMatrix[rowIdx][colIdx].isBombing()) {
            mapPointMatrix[rowIdx][colIdx].setPathway(true);
            mapPointMatrix[rowIdx][colIdx].setMutable(false);
            mapPointMatrix[rowIdx][colIdx].addFlame();
            mapPointMatrix[rowIdx][colIdx].setImageAsBurned();
            list.add(new Flame(rowIdx, colIdx));
            return false; // the next case should NOT be tested.
        } else {
            return false; // the next case should NOT be tested.
        }

    }

    /**
     * Add a set of flames to represent a bomb explosion.
     *
     * @param list the list into which adding the flames
     * @param mapPointMatrix the map (represented by its matrix of MapPoint)
     * @param mapWidth the map width
     * @param mapHeight the map height
     * @param centralRowIdx the map row index of the central flame
     * @param centralColIdx the map column index of the central flame
     * @param flameSize the flame size
     */
    public static void addFlames(LinkedList<Sprite> list, MapPoint[][] mapPointMatrix, int mapWidth, int mapHeight,
            int centralRowIdx, int centralColIdx, int flameSize) {

        // place left flames.
        for (int i = 1, j = centralColIdx - 1; i <= flameSize && j >= 0; i++, j--) { // from center to left.
            if (!addFlame(list, mapPointMatrix, centralRowIdx, centralColIdx - i)) {
                break; // break loop.
            }
        }

        // place right flames.
        for (int i = 1, j = centralColIdx + 1; i <= flameSize && j < mapWidth; i++, j++) { // from center to right.
            if (!addFlame(list, mapPointMatrix, centralRowIdx, centralColIdx + i)) {
                break; // break loop.
            }
        }

        // place upper flames.
        for (int i = 1, j = centralRowIdx - 1; i <= flameSize && j >= 0; i++, j--) { // from center to top.
            if (!addFlame(list, mapPointMatrix, centralRowIdx - i, centralColIdx)) {
                break; // break loop.
            }
        }

        // place lower flames.
        for (int i = 1, j = centralRowIdx + 1; i <= flameSize && j < mapHeight; i++, j++) { // from center to bottom.
            if (!addFlame(list, mapPointMatrix, centralRowIdx + i, centralColIdx)) {
                break; // break loop.
            }
        }
        addFlame(list, mapPointMatrix, centralRowIdx, centralColIdx); // central case.
    }

    /**
     * Add a conclusion flame to a list.
     *
     * @param list the list into which adding the flame
     * @param mapPointMatrix the map (represented by its matrix of MapPoint)
     * @param rowIdx the map row index of the flame
     * @param colIdx the map column index of the flame
     */
    public static void addConclusionFlame(LinkedList<Sprite> list, MapPoint[][] mapPointMatrix, int rowIdx,
            int colIdx) {
            list.add(new ConclusionFlame(rowIdx, colIdx));
    }
}