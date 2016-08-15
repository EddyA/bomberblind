package map.zelda;

import exceptions.CannotCreateMapElementException;
import exceptions.InvalidMapConfigurationException;
import images.ImagesLoader;
import map.MapPoint;
import map.MapPattern;
import map.ctrl.GenerationMethods;
import utils.Tuple2;
import utils.zelda.ZeldaMapProperties;

import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static map.ctrl.PatternMethods.placeNorthEdgeOnMap;
import static map.ctrl.PatternMethods.placeSouthEdgeOnMap;

public class ZeldaMap {

    private final int MAX_NB_TRY = 10;

    protected ZeldaMapSetting zeldaMapSetting;
    private MapPoint[][] mapPointMatrix;

    // screen information.
    protected int screenWidth; // widht of the screen (expressed in pixel).
    protected int screenHeight; // height of the screen (expressed in pixel).

    // castles information.
    private MapPoint spCastleT1; // start point (north/west) of the castle of team 1.
    private MapPoint spCastleT2; // start point (north/west) of the castle of team 2.

    // Declare pattern for all the graphical elements (except for simple pathway - 1*1).
    // immutables:
    // - castles.
    protected MapPattern castleT1;
    protected MapPattern castleT2;

    // - obstacles.
    protected MapPattern edge;
    protected MapPattern tree1;
    protected MapPattern tree2;
    protected MapPattern wood1;
    protected MapPattern wood2;

    // - pathways.
    protected MapPattern puddle1;
    protected MapPattern puddle2;

    public ZeldaMap(int screenWidth, int screenHeight)
            throws IOException, InvalidMapConfigurationException, CannotCreateMapElementException {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        // load and check map properties file.
        ZeldaMapProperties zeldaMapProperties = new ZeldaMapProperties();
        zeldaMapProperties.loadProperties();
        zeldaMapProperties.checkProperties();

        // create a map of mapHeight*mapWidth cases.
        this.zeldaMapSetting = new ZeldaMapSetting(zeldaMapProperties);
        this.mapPointMatrix = new MapPoint[zeldaMapSetting.getMapHeight()][zeldaMapSetting.getMapWidth()];
        for (int rowIdx = 0; rowIdx < zeldaMapSetting.getMapHeight(); rowIdx++) {
            for (int colIdx = 0; colIdx < zeldaMapSetting.getMapWidth(); colIdx++) {
                this.mapPointMatrix[rowIdx][colIdx] = new MapPoint(rowIdx, colIdx);
            }
        }
    }

    public int getMapWidth() {
        return zeldaMapSetting.getMapWidth();
    }

    public int getMapHeight() {
        return zeldaMapSetting.getMapHeight();
    }

    public MapPoint[][] getMapPointMatrix() {
        return mapPointMatrix;
    }

    public MapPoint getSpCastleT1() {
        return spCastleT1;
    }

    public MapPoint getSpCastleT2() {
        return spCastleT2;
    }

    public MapPattern getCastleT1() {
        return castleT1;
    }

    public MapPattern getCastleT2() {
        return castleT2;
    }

    /**
     * Create pattern for all the graphical elements (except for simple pathway - 1*1).
     * these patterns will be used to dynamically generate the map.
     */
    public void createPatterns() {

        // immutables:
        // - castles.
        castleT1 = new MapPattern(ImagesLoader.imagesMatrix[ImagesLoader.castleT1MatrixRowIdx],
                ImagesLoader.CASTLE_WIDTH, ImagesLoader.CASTLE_HEIGHT, false, false, "castleT1");
        castleT2 = new MapPattern(ImagesLoader.imagesMatrix[ImagesLoader.castleT2MatrixRowIdx],
                ImagesLoader.CASTLE_WIDTH, ImagesLoader.CASTLE_HEIGHT, false, false, "castleT2");

        // - obstacles.
        edge = new MapPattern(ImagesLoader.imagesMatrix[ImagesLoader.edgeMatrixRowIdx],
                ImagesLoader.EDGE_WIDTH, ImagesLoader.EDGE_HEIGHT, false, false, "edge");
        tree1 = new MapPattern(ImagesLoader.imagesMatrix[ImagesLoader.tree1MatrixRowIdx],
                ImagesLoader.TREE_WIDTH, ImagesLoader.TREE_HEIGHT, false, false, "tree1");
        tree2 = new MapPattern(ImagesLoader.imagesMatrix[ImagesLoader.tree2MatrixRowIdx],
                ImagesLoader.TREE_WIDTH, ImagesLoader.TREE_HEIGHT, false, false, "tree2");
        wood1 = new MapPattern(ImagesLoader.imagesMatrix[ImagesLoader.wood1MatrixRowIdx],
                ImagesLoader.WOOD_WIDTH, ImagesLoader.WOOD_HEIGHT, false, false, "wood1");
        wood2 = new MapPattern(ImagesLoader.imagesMatrix[ImagesLoader.wood2MatrixRowIdx],
                ImagesLoader.WOOD_WIDTH, ImagesLoader.WOOD_HEIGHT, false, false, "wood2");

        // - pathways.
        puddle1 = new MapPattern(ImagesLoader.imagesMatrix[ImagesLoader.puddle1MatrixRowIdx],
                ImagesLoader.PUDDLE_WIDTH, ImagesLoader.PUDDLE_HEIGHT, true, false, "puddle1");
        puddle2 = new MapPattern(ImagesLoader.imagesMatrix[ImagesLoader.puddle2MatrixRowIdx],
                ImagesLoader.PUDDLE_WIDTH, ImagesLoader.PUDDLE_HEIGHT, true, false, "puddle2");
    }


    /**
     * Randomly generate a map.
     *
     * @throws CannotCreateMapElementException if the map could not be created
     */
    public void generateMap() throws CannotCreateMapElementException {

        // place north and south edges.
        placeNorthEdgeOnMap(mapPointMatrix, zeldaMapSetting.getMapWidth(), zeldaMapSetting.getMapHeight(), tree1);
        placeSouthEdgeOnMap(mapPointMatrix, zeldaMapSetting.getMapWidth(), zeldaMapSetting.getMapHeight(), edge);

        // place castles.
        Tuple2<MapPattern, MapPattern> castlePatterns = new Tuple2<>(castleT1, castleT2);
        Tuple2<MapPoint, MapPoint> spCastles = GenerationMethods.randomlyPlaceCastles(mapPointMatrix,
                zeldaMapSetting.getMapWidth(), zeldaMapSetting.getMapHeight(), zeldaMapSetting.getHorizontalMargin(),
                tree1.getHeight(), edge.getHeight(), zeldaMapSetting.getVerticalMargin(), castlePatterns,
                zeldaMapSetting.getPerSingleDynPathway());
        spCastleT1 = spCastles.getFirst();
        spCastleT2 = spCastles.getSecond();

        // place complex elements.
        Map<MapPattern, Integer> complexEltPatterns = new HashMap<>();
        complexEltPatterns.put(wood1, zeldaMapSetting.getNbWood1());
        complexEltPatterns.put(wood2, zeldaMapSetting.getNbWood2());
        complexEltPatterns.put(tree1, zeldaMapSetting.getNbTree1());
        complexEltPatterns.put(tree2, zeldaMapSetting.getNbTree2());
        complexEltPatterns.put(puddle1, zeldaMapSetting.getNbPuddle1());
        complexEltPatterns.put(puddle2, zeldaMapSetting.getNbPuddle2());
        GenerationMethods.randomlyPlaceComplexElements(mapPointMatrix, zeldaMapSetting.getMapWidth(), zeldaMapSetting.getMapHeight(),
                tree1.getHeight(), edge.getHeight(), complexEltPatterns, MAX_NB_TRY);

        // place single elements.
        GenerationMethods.randomlyPlaceSingleElements(mapPointMatrix, zeldaMapSetting.getMapWidth(), zeldaMapSetting.getMapHeight(),
                zeldaMapSetting.getPerSingleMutable(), zeldaMapSetting.getPerSingleObstacle(), zeldaMapSetting.getPerSingleDynPathway());
    }

    /**
     * Paint visible elements of the map.
     *
     * @param g    the graphics context
     * @param xMap the map abscissa from which painting elements
     * @param yMap the map ordinate from which painting elements
     */
    public void paintBuffer(Graphics g, int xMap, int yMap) {

        // get the starting MapPoint concerned.
        int startColIdx = xMap / ImagesLoader.IMAGE_SIZE;
        int startRowIdx = yMap / ImagesLoader.IMAGE_SIZE;

        // paint the map.
        for (int rowIdx = startRowIdx;
             rowIdx <= startRowIdx + (screenHeight / ImagesLoader.IMAGE_SIZE) && rowIdx < zeldaMapSetting.getMapHeight();
             rowIdx++) {
            for (int colIdx = startColIdx;
                 colIdx <= startColIdx + (screenWidth / ImagesLoader.IMAGE_SIZE) && colIdx < zeldaMapSetting.getMapWidth();
                 colIdx++) {
                mapPointMatrix[rowIdx][colIdx].paintBuffer(g,
                        (colIdx - startColIdx) * ImagesLoader.IMAGE_SIZE - xMap % ImagesLoader.IMAGE_SIZE, // colIdx -> x
                        (rowIdx - startRowIdx) * ImagesLoader.IMAGE_SIZE - yMap % ImagesLoader.IMAGE_SIZE); // roxIdx -> y
            }
        }
    }
}
