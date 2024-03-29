package map;

import java.awt.Graphics2D;
import exceptions.CannotCreateMapElementException;
import exceptions.CannotFindPathFromEntranceToExitException;
import images.ImagesLoader;
import lombok.Getter;
import utils.Tuple2;

@Getter
public abstract class Map {

    // map information.
    protected final MapPoint[][] mapPointMatrix;
    private final int mapWidth; // width of the map (expressed in MapPoint).
    private final int mapHeight; // height of the map (expressed in MapPoint).

    // screen information.
    private final int screenWidth; // width of the screen (expressed in pixel).
    private final int screenHeight; // height of the screen (expressed in pixel).

    protected Map(MapSettings mapSettings, int screenWidth, int screenHeight) {
        this.mapWidth = mapSettings.getMapWidth();
        this.mapHeight = mapSettings.getMapHeight();
        this.mapPointMatrix = new MapPoint[mapHeight][mapWidth];
        for (int rowIdx = 0; rowIdx < mapHeight; rowIdx++) {
            for (int colIdx = 0; colIdx < mapWidth; colIdx++) {
                this.mapPointMatrix[rowIdx][colIdx] = new MapPoint(rowIdx, colIdx);
            }
        }
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    /**
     * Randomly generate a map.
     *
     * @throws CannotCreateMapElementException           if mandatory map element (e.g. entrance) cannot be placed
     * @throws CannotFindPathFromEntranceToExitException if there is no path between entrance and exit
     */
    public abstract void generateMap()
            throws CannotCreateMapElementException, CannotFindPathFromEntranceToExitException;

    /**
     * Reset all MapPoint status.
     */
    public void resetMap() {
        for (int rowIdx = 0; rowIdx < mapHeight; rowIdx++) {
            for (int colIdx = 0; colIdx < mapWidth; colIdx++) {
                this.mapPointMatrix[rowIdx][colIdx].reset();
            }
        }
    }

    /**
     * Compute the initial Bomber position on map.
     * This function must be called after generateMap().
     *
     * @return the initial Bomber position on map (in pixel)
     */
    public abstract Tuple2<Integer, Integer> computeInitialBbManPosition();

    /**
     * Compute the exit sign (e.g. sparkle) position on map.
     * This function must be called after generateMap().
     *
     * @return the exit sign position on map (in pixel)
     */
    public abstract Tuple2<Integer, Integer> computeExitSignPosition();

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
