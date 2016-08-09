package map;

import exceptions.CannotCreateMapElementException;
import exceptions.InvalidMapConfigurationException;
import images.ImagesLoader;
import utils.MapProperties;

import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;

import static map.Ctrl.PatternMethods.*;
import static map.Ctrl.SingleMethods.*;

public class RMap {

    private final int MAX_NB_TRY = 10;

    // the following values allow put castles at:
    // - x cases from the left/right sides of the map,
    // - a minimum of y cases from the top/bottom of the map.
    private final static int MARGIN_X = 2;
    private final static int MARGIN_Y = 2;

    protected RMapSetting rMapSetting;
    private RMapPoint[][] rMapPointMatrix;

    // screen information.
    private int screenWidth; // widht of the screen (expressed in pixel).
    private int screenHeight; // height of the screen (expressed in pixel).

    private RMapPoint spCastleT1; // start point (north/west) of the castle of team 1.
    private RMapPoint spCastleT2; // start point (north/west) of the castle of team 2.

    // Declare pattern for all the graphical elements (except for simple pathway - 1*1).
    // immutables:
    // - castles.
    private RMapPattern castleT1;
    private RMapPattern castleT2;

    // - obstacles.
    private RMapPattern edge;
    private RMapPattern tree1;
    private RMapPattern tree2;
    private RMapPattern wood1;
    private RMapPattern wood2;

    // - pathways.
    private RMapPattern puddle1;
    private RMapPattern puddle2;

    public RMap(int screenWidth, int screenHeight)
            throws IOException, InvalidMapConfigurationException, CannotCreateMapElementException {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        // load and check map properties file.
        MapProperties mapProperties = new MapProperties();
        mapProperties.loadProperties();
        mapProperties.checkProperties();

        // create a map of mapHeight*mapWidth cases.
        this.rMapSetting = new RMapSetting(mapProperties);
        this.rMapPointMatrix = new RMapPoint[rMapSetting.getMapHeight()][rMapSetting.getMapWidth()];
        for (int rowIdx = 0; rowIdx < rMapSetting.getMapHeight(); rowIdx++) {
            for (int colIdx = 0; colIdx < rMapSetting.getMapWidth(); colIdx++) {
                this.rMapPointMatrix[rowIdx][colIdx] = new RMapPoint(rowIdx, colIdx);
            }
        }
    }

    public int getMapWidth() {
        return rMapSetting.getMapWidth();
    }

    public int getMapHeight() {
        return rMapSetting.getMapHeight();
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public RMapPoint[][] getRMapPointMatrix() {
        return rMapPointMatrix;
    }

    public RMapPoint getSpCastleT1() {
        return spCastleT1;
    }

    public RMapPoint getSpCastleT2() {
        return spCastleT2;
    }

    public RMapPattern getCastleT1() {
        return castleT1;
    }

    public RMapPattern getCastleT2() {
        return castleT2;
    }

    /**
     * Create pattern for all the graphical elements (except for simple pathway - 1*1).
     * these patterns will be used to dynamically generate the map.
     */
    public void createPatterns() {

        // immutables:
        // - castles.
        castleT1 = new RMapPattern(ImagesLoader.imagesMatrix[ImagesLoader.castleT1MatrixRowIdx],
                ImagesLoader.CASTLE_WIDTH, ImagesLoader.CASTLE_HEIGHT, false, false, "castleT1");
        castleT2 = new RMapPattern(ImagesLoader.imagesMatrix[ImagesLoader.castleT2MatrixRowIdx],
                ImagesLoader.CASTLE_WIDTH, ImagesLoader.CASTLE_HEIGHT, false, false, "castleT2");

        // - obstacles.
        edge = new RMapPattern(ImagesLoader.imagesMatrix[ImagesLoader.edgeMatrixRowIdx],
                ImagesLoader.EDGE_WIDTH, ImagesLoader.EDGE_HEIGHT, false, false, "edge");
        tree1 = new RMapPattern(ImagesLoader.imagesMatrix[ImagesLoader.tree1MatrixRowIdx],
                ImagesLoader.TREE_WIDTH, ImagesLoader.TREE_HEIGHT, false, false, "tree1");
        tree2 = new RMapPattern(ImagesLoader.imagesMatrix[ImagesLoader.tree2MatrixRowIdx],
                ImagesLoader.TREE_WIDTH, ImagesLoader.TREE_HEIGHT, false, false, "tree2");
        wood1 = new RMapPattern(ImagesLoader.imagesMatrix[ImagesLoader.wood1MatrixRowIdx],
                ImagesLoader.WOOD_WIDTH, ImagesLoader.WOOD_HEIGHT, false, false, "wood1");
        wood2 = new RMapPattern(ImagesLoader.imagesMatrix[ImagesLoader.wood2MatrixRowIdx],
                ImagesLoader.WOOD_WIDTH, ImagesLoader.WOOD_HEIGHT, false, false, "wood2");

        // - pathways.
        puddle1 = new RMapPattern(ImagesLoader.imagesMatrix[ImagesLoader.puddle1MatrixRowIdx],
                ImagesLoader.PUDDLE_WIDTH, ImagesLoader.PUDDLE_HEIGHT, true, false, "puddle1");
        puddle2 = new RMapPattern(ImagesLoader.imagesMatrix[ImagesLoader.puddle2MatrixRowIdx],
                ImagesLoader.PUDDLE_WIDTH, ImagesLoader.PUDDLE_HEIGHT, true, false, "puddle2");
    }

    /**
     * Randomly generate a map.
     *
     * @throws CannotCreateMapElementException if the map could not be created
     */
    public void generateMap() throws CannotCreateMapElementException {
        placeNorthEdgeOnMap(rMapPointMatrix, rMapSetting.getMapWidth(), rMapSetting.getMapHeight(), tree1);
        placeSouthEdgeOnMap(rMapPointMatrix, rMapSetting.getMapWidth(), rMapSetting.getMapHeight(), edge);

        // 1st castle.
        int xSpCastleT1 = MARGIN_X;
        int ySpCastleT1 = generateRandomRowIdx(rMapSetting.getMapHeight(), ImagesLoader.TREE_HEIGHT,
                ImagesLoader.EDGE_HEIGHT, ImagesLoader.CASTLE_HEIGHT, MARGIN_Y);
        placeCastleOnMap(rMapPointMatrix, rMapSetting.getMapWidth(), rMapSetting.getMapHeight(),
                castleT1, ySpCastleT1, xSpCastleT1, rMapSetting.getPerSingleFlowerPathway());
        spCastleT1 = rMapPointMatrix[ySpCastleT1][xSpCastleT1];

        // 2nd castle.
        int xSpCastleT2 = rMapSetting.getMapWidth() - MARGIN_X - ImagesLoader.CASTLE_WIDTH;
        int ySpCastleT2 = generateRandomRowIdx(rMapSetting.getMapHeight(), ImagesLoader.TREE_HEIGHT,
                ImagesLoader.EDGE_HEIGHT, ImagesLoader.CASTLE_HEIGHT, MARGIN_Y);
        placeCastleOnMap(rMapPointMatrix, rMapSetting.getMapWidth(), rMapSetting.getMapHeight(),
                castleT2, ySpCastleT2, xSpCastleT2, rMapSetting.getPerSingleFlowerPathway());
        spCastleT2 = rMapPointMatrix[ySpCastleT2][xSpCastleT2];

        // complex elements.
        Map<RMapPattern, Integer> eltConfMap = new HashMap<>();
        eltConfMap.put(wood1, rMapSetting.getNbWood1());
        eltConfMap.put(wood2, rMapSetting.getNbWood2());
        eltConfMap.put(tree1, rMapSetting.getNbTree1());
        eltConfMap.put(tree2, rMapSetting.getNbTree2());
        eltConfMap.put(puddle1, rMapSetting.getNbPuddle1());
        eltConfMap.put(puddle2, rMapSetting.getNbPuddle2());

        for (Map.Entry<RMapPattern, Integer> eltConf : eltConfMap.entrySet()) {
            for (int eltIdx = 0; eltIdx < eltConf.getValue(); eltIdx++) {
                int nbTry = 0;
                while (true) {
                    int xSpElt = generateRandomColIdx(rMapSetting.getMapWidth(), 0, 0, eltConf.getKey().getWidth(), 0);
                    int ySpElt = generateRandomRowIdx(rMapSetting.getMapHeight(), ImagesLoader.TREE_HEIGHT,
                            ImagesLoader.EDGE_HEIGHT, eltConf.getKey().getHeight(), 0);
                    if (!placePatternOnMap(rMapPointMatrix, rMapSetting.getMapWidth(), rMapSetting.getMapHeight(),
                            eltConf.getKey(), ySpElt, xSpElt)) {
                        if (nbTry < MAX_NB_TRY) {
                            nbTry++;
                        } else {
                            break;
                        }
                    } else {
                        break;
                    }
                }
            }
        }

        // create list of empty cases.
        List<RMapPoint> emptyPtList = new ArrayList<>();
        for (int rowIdx = 0; rowIdx < rMapSetting.getMapHeight(); rowIdx++) {
            for (int colIdx = 0; colIdx < rMapSetting.getMapWidth(); colIdx++) {
                if (rMapPointMatrix[rowIdx][colIdx].isAvailable()) {
                    emptyPtList.add(rMapPointMatrix[rowIdx][colIdx]);
                }
            }
        }
        int nbEmptyPt = emptyPtList.size();

        // single elements.
        int perSingleMutable = rMapSetting.getPerSingleMutable();
        int perSingleObstacle = rMapSetting.getPerSingleObstacle();

        Random R = new Random();
        for (int i = 0; i < nbEmptyPt; i++) {
            int randomPercent = Math.abs(R.nextInt(100)); // randomly choose a single element.
            int ptIdx = Math.abs(R.nextInt(emptyPtList.size())); // randomly choose an empty case.
            RMapPoint rMapPoint = emptyPtList.get(ptIdx);

            if (randomPercent < perSingleObstacle) {
                if (!placeSingleObstacleOnMap(rMapPoint)) {
                    throw new CannotCreateMapElementException("not able to create a single obstacle.");
                }

            } else if (randomPercent < perSingleObstacle + perSingleMutable) {
                if (!placeSingleMutableOnMap(rMapPoint)) {
                    throw new CannotCreateMapElementException("not able to create a single mutable.");
                }
            } else {
                if (!placeSinglePathwayOnMap(rMapPoint, rMapSetting.getPerSingleFlowerPathway())) {
                    throw new CannotCreateMapElementException("not able to create a single pathway.");
                }
            }
            emptyPtList.remove(ptIdx);
        }
    }

    /**
     * Paint visible elements of the map.
     *
     * @param g    the graphics context
     * @param xMap the map abscissa from which painting elements
     * @param yMap the map ordinate from which painting elements
     */
    public void paintBuffer(Graphics g, int xMap, int yMap) {

        // get the starting RMapPoint concerned.
        int startColIdx = xMap / ImagesLoader.IMAGE_SIZE;
        int startRowIdx = yMap / ImagesLoader.IMAGE_SIZE;

        // paint the map.
        for (int rowIdx = startRowIdx;
             rowIdx <= startRowIdx + (screenHeight / ImagesLoader.IMAGE_SIZE) && rowIdx < rMapSetting.getMapHeight();
             rowIdx++) {
            for (int colIdx = startColIdx;
                 colIdx <= startColIdx + (screenWidth / ImagesLoader.IMAGE_SIZE) && colIdx < rMapSetting.getMapWidth();
                 colIdx++) {
                rMapPointMatrix[rowIdx][colIdx].paintBuffer(g,
                        (colIdx - startColIdx) * ImagesLoader.IMAGE_SIZE - xMap % ImagesLoader.IMAGE_SIZE, // colIdx -> x
                        (rowIdx - startRowIdx) * ImagesLoader.IMAGE_SIZE - yMap % ImagesLoader.IMAGE_SIZE); // roxIdx -> y
            }
        }
    }
}
