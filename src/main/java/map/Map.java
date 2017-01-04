package map;

import exceptions.CannotCreateMapElementException;
import images.ImagesLoader;
import utils.Tuple2;

import java.awt.*;

public abstract class Map {

    // map information.
    protected MapPoint[][] mapPointMatrix;
    private final int mapWidth; // width of the map (expressed in MapPoint).
    private final int mapHeight; // height of the map (expressed in MapPoint).

    // screen information.
    private final int screenWidth; // widht of the screen (expressed in pixel).
    private final int screenHeight; // height of the screen (expressed in pixel).

    protected Map(MapSettings mapSettings, int screenWidth, int screenHeight) {
        this.mapWidth = mapSettings.getMapWidth();
        this.mapHeight = mapSettings.getMapHeight();
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    public MapPoint[][] getMapPointMatrix() {
        return mapPointMatrix;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    /**
     * Randomly generate a map.
     *
     * @throws CannotCreateMapElementException if the map cannot be created
     */
    public abstract void generateMap() throws CannotCreateMapElementException;

    /**
     * Compute the initial Bomber position on map.
     * This function must be called after generateMap().
     *
     * @return the initial Bomber position on map (in pixel)
     */
    public abstract Tuple2<Integer, Integer> computeInitialBbManPosition();

    /**
     * Paint visible elements of the map.
     *
     * @param g    the graphics context
     * @param xMap the map abscissa from which painting elements
     * @param yMap the map ordinate from which painting elements
     */
    public void paintBuffer(Graphics2D g, int xMap, int yMap) {

        // get the starting MapPoint concerned.
        int startColIdx = xMap / ImagesLoader.IMAGE_SIZE;
        int startRowIdx = yMap / ImagesLoader.IMAGE_SIZE;

        // paint the map.
        for (int rowIdx = startRowIdx;
             rowIdx <= startRowIdx + (screenHeight / ImagesLoader.IMAGE_SIZE) + 1 && rowIdx < mapHeight;
             rowIdx++) {
            for (int colIdx = startColIdx;
                 colIdx <= startColIdx + (screenWidth / ImagesLoader.IMAGE_SIZE) + 1 && colIdx < mapWidth;
                 colIdx++) {
                mapPointMatrix[rowIdx][colIdx].paintBuffer(g,
                        (colIdx - startColIdx) * ImagesLoader.IMAGE_SIZE - xMap % ImagesLoader.IMAGE_SIZE, // colIdx -> x
                        (rowIdx - startRowIdx) * ImagesLoader.IMAGE_SIZE - yMap % ImagesLoader.IMAGE_SIZE); // roxIdx -> y
            }
        }
    }
}
