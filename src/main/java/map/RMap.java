package map;

import exceptions.CannotCreateRMapElementException;
import images.ImagesLoader;

import java.awt.*;
import java.util.*;
import java.util.List;

public class RMap {

    private final static int MARGIN_X = 2;
    private final static int MARGIN_Y = 2;
    private final int MAX_NB_TRY = 10;
    protected RMapSetting rMapSetting;
    private RMapPoint[][] rMapPointMatrix;
    // screen information.
    private int screenWidth; // widht of the screen (expressed in pixel).
    private int screenHeight; // height of the screen (expressed in pixel).

    // the following values allow put castles & ressources at:
    // - x cases from the left/right sides of the map,
    // - a minimum of y cases from the top/bottom of the map.
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
            throws CannotCreateRMapElementException {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        // Create a map of mapHeight*mapWidth cases.
        rMapSetting = new RMapSetting();
        rMapPointMatrix = new RMapPoint[rMapSetting.getMapHeight()][rMapSetting.getMapWidth()];
        for (int rawIdx = 0; rawIdx < rMapSetting.getMapHeight(); rawIdx++) {
            for (int colIdx = 0; colIdx < rMapSetting.getMapWidth(); colIdx++) {
                rMapPointMatrix[rawIdx][colIdx] = new RMapPoint(rawIdx, colIdx);
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

    public RMapPoint getRMapPoint(int rowIdx, int colIdx) {
        return rMapPointMatrix[rowIdx][colIdx];
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
     * @throws CannotCreateRMapElementException if the map could not be created
     */
    public void generateMap() throws CannotCreateRMapElementException {

        // north edge.
        for (int col = 0; col < rMapSetting.getMapWidth(); col += tree1.getWidth()) {
            if (!placePatternOnMap(rMapPointMatrix[0][col], tree1)) {
                throw new CannotCreateRMapElementException("not able to create the north edge (mapWidth % tree1.getWidth() != 0).");
            }
        }

        // south edge.
        for (int col = 0; col < rMapSetting.getMapWidth(); col += edge.getWidth()) {
            if (!placePatternOnMap(rMapPointMatrix[rMapSetting.getMapHeight() - edge.getHeight()][col], edge)) {
                throw new CannotCreateRMapElementException("not able to create the south edge (mapWidth % edge.getWidth() != 0).");
            }
        }

        // castles of team 1.
        int xSpCastleT1 = MARGIN_X;
        int ySpCastleT1 = generateRandomRowIdx(ImagesLoader.CASTLE_HEIGHT, MARGIN_Y);
        if (!placePatternOnMap(rMapPointMatrix[ySpCastleT1][xSpCastleT1], castleT1)) {
            throw new CannotCreateRMapElementException("not able to create the castle of team 1.");
        }
        securePerimeter(rMapPointMatrix[ySpCastleT1][xSpCastleT1], castleT1);
        spCastleT1 = rMapPointMatrix[ySpCastleT1][xSpCastleT1];

        // castles of team 2.
        int xSpCastleT2 = rMapSetting.getMapWidth() - MARGIN_X - ImagesLoader.CASTLE_WIDTH;
        int ySpCastleT2 = generateRandomRowIdx(ImagesLoader.CASTLE_HEIGHT, MARGIN_Y);
        if (!placePatternOnMap(rMapPointMatrix[ySpCastleT2][xSpCastleT2], castleT2)) {
            throw new CannotCreateRMapElementException("not able to create the castle of team 2.");
        }
        securePerimeter(rMapPointMatrix[ySpCastleT2][xSpCastleT2], castleT2);
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
                    int xSpElt = generateRandomColIdx(eltConf.getKey().getWidth(), 0);
                    int ySpElt = generateRandomRowIdx(eltConf.getKey().getHeight(), 0);
                    if (!placePatternOnMap(rMapPointMatrix[ySpElt][xSpElt], eltConf.getKey())) {
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
                    throw new CannotCreateRMapElementException("not able to create a single obstacle.");
                }

            } else if (randomPercent < perSingleObstacle + perSingleMutable) {
                if (!placeSingleMutableOnMap(rMapPoint)) {
                    throw new CannotCreateRMapElementException("not able to create a single mutable.");
                }
            } else {
                if (!placeSinglePathwayOnMap(rMapPoint)) {
                    throw new CannotCreateRMapElementException("not able to create a single pathway.");
                }
            }
            emptyPtList.remove(ptIdx);
        }
    }

    /**
     * Generate a random rowIdx based on a pattern height and a margin range.
     * This function is used to place elements at a certain margin from north and south sides.
     *
     * @param patternHeight the pattern height
     * @param marginRange   the margin range
     * @return the random rowIdx
     */
    private int generateRandomRowIdx(int patternHeight, int marginRange) {
        Random R = new Random(); // initStatement the random function.
        return R.nextInt(rMapSetting.getMapHeight() - 2 * marginRange - // north/south requiered margins.
                patternHeight - // pattern height as we place the north/west point.
                (ImagesLoader.EDGE_HEIGHT + ImagesLoader.TREE_HEIGHT)) + // egde + tree.
                ImagesLoader.TREE_HEIGHT + marginRange; // re-add tree height + margin to get the right range.
    }

    /**
     * Generate a random colIdx based on a pattern width and a margin range.
     * This function is used to place elements at a certain margin from east and west sides.
     *
     * @param patternWidth the pattern height
     * @param marginRange  the margin range
     * @return the random rowIdx
     */
    private int generateRandomColIdx(int patternWidth, int marginRange) {
        Random R = new Random(); // initStatement the random function.
        return R.nextInt(rMapSetting.getMapWidth() - 2 * marginRange - // east/west requiered margins.
                patternWidth) + // pattern width as we place the noth/west point.
                marginRange; // re-add the margin to get the right range.
    }

    /**
     * Try to place an element (based on its pattern) on the map.
     * The pointed out rMapPoint corresponds to the north/west corner of the pattern.
     * If the pattern is eligible, create the element and return true, else, return false.
     *
     * @param rMapPoint   the point of the map
     * @param rMapPattern the pattern of the element to place
     * @return If the pattern is eligible, create the element on the map and return true, else, return false.
     */
    private boolean placePatternOnMap(RMapPoint rMapPoint, RMapPattern rMapPattern) {
        int startRowIdx = rMapPoint.getRowIdx();
        int startColIdx = rMapPoint.getColIdx();

        // firstly, check if the pattern is not outsized.
        if (startRowIdx + rMapPattern.getHeight() > rMapSetting.getMapHeight() ||
                startColIdx + rMapPattern.getWidth() > rMapSetting.getMapWidth()) {
            return false;
        }

        // then, check if the pattern do not cross a placed element.
        for (int rowIdx = startRowIdx; rowIdx < startRowIdx + rMapPattern.getHeight(); rowIdx++) {
            for (int colIdx = startColIdx; colIdx < startColIdx + rMapPattern.getWidth(); colIdx++) {
                if (!this.rMapPointMatrix[rowIdx][colIdx].isAvailable()) {
                    return false;
                }
            }
        }

        // finally, we create the element.
        for (int rowIdx = 0; rowIdx < rMapPattern.getHeight(); rowIdx++) {
            for (int colIdx = 0; colIdx < rMapPattern.getWidth(); colIdx++) {
                this.rMapPointMatrix[startRowIdx + rowIdx][startColIdx + colIdx]
                        .setImage(rMapPattern.getImageArray()[(colIdx * rMapPattern.getHeight()) + rowIdx]);
                this.rMapPointMatrix[startRowIdx + rowIdx][startColIdx + colIdx].setPathway(rMapPattern.isPathway());
                this.rMapPointMatrix[startRowIdx + rowIdx][startColIdx + colIdx].setMutable(rMapPattern.isMutable());
                this.rMapPointMatrix[startRowIdx + rowIdx][startColIdx + colIdx].setAvailable(false);
            }
        }
        return true;
    }

    /**
     * Try to place a single obstacle on the map.
     * If the RMapPoint is available, create the element and return true, else, return false.
     *
     * @param rMapPoint the point of the map
     * @return If the RMapPoint is available, create the element and return true, else, return false.
     */
    private boolean placeSingleObstacleOnMap(RMapPoint rMapPoint) {
        if (rMapPoint.isAvailable()) {
            Random R = new Random(); // initStatement the random function.

            // randomly choose an image.
            int imageIdx = R.nextInt(ImagesLoader.NB_SINGLE_OBSTABLE);
            Image image = ImagesLoader.imagesMatrix[ImagesLoader.singleObstacleMatrixRowIdx][imageIdx];

            // set rMapPoint.
            rMapPoint.setImage(image);
            rMapPoint.setMutable(false);
            rMapPoint.setPathway(false);
            rMapPoint.setAvailable(false);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Try to place a single mutable on the map.
     * If the RMapPoint is available, create the element and return true, else, return false.
     *
     * @param rMapPoint the point of the map
     * @return If the RMapPoint is available, create the element and return true, else, return false.
     */
    private boolean placeSingleMutableOnMap(RMapPoint rMapPoint) {
        if (rMapPoint.isAvailable()) {
            Random R = new Random(); // initStatement the random function.

            // randomly choose an image.
            int imageIdx = R.nextInt(ImagesLoader.NB_SINGLE_MUTABLE);
            Image image = ImagesLoader.imagesMatrix[ImagesLoader.singleMutableMatrixRowIdx][imageIdx];

            // set rMapPoint.
            rMapPoint.setImage(image);
            rMapPoint.setMutable(true);
            rMapPoint.setPathway(false);
            rMapPoint.setAvailable(false);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Try to place a single pathway on the map.
     * If the RMapPoint is available, create the element and return true, else, return false.
     *
     * @param rMapPoint the point of the map
     * @return If the RMapPoint is available, create the element and return true, else, return false.
     */
    private boolean placeSinglePathwayOnMap(RMapPoint rMapPoint) {
        if (rMapPoint.isAvailable()) {
            Random R = new Random(); // initStatement the random function.
            int randomPercent = Math.abs(R.nextInt(100)); // randomly choose a single element.

            if (randomPercent < rMapSetting.getPerSingleFlowerPathway()) { // animated flower
                rMapPoint.setImages(ImagesLoader.imagesMatrix[ImagesLoader.flowerMatrixRowIdx],
                        ImagesLoader.NB_FLOWER_FRAME);
                rMapPoint.setRefreshTime(100);
            } else {

                // randomly choose an image.
                int imageIdx = R.nextInt(ImagesLoader.NB_SINGLE_PATHWAY);
                Image image = ImagesLoader.imagesMatrix[ImagesLoader.singlePathwayMatrixRowIdx][imageIdx];
                rMapPoint.setImage(image);
            }
            rMapPoint.setMutable(false);
            rMapPoint.setPathway(true);
            rMapPoint.setAvailable(false);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Secure the perimeter of an element putting single pathways around it.
     * If some cases are used, they are ignored.
     *
     * @param rMapPoint   the start point of the element to secure
     * @param rMapPattern the pattern of the element to secure
     */
    private void securePerimeter(RMapPoint rMapPoint, RMapPattern rMapPattern) {
        int startRowIdx = rMapPoint.getRowIdx();
        int startColIdx = rMapPoint.getColIdx();
        Random R = new Random(); // initStatement the random function.

        for (int rowIdx = startRowIdx - 1; rowIdx <= startRowIdx + rMapPattern.getHeight(); rowIdx++) {
            for (int colIdx = startColIdx - 1; colIdx <= startColIdx + rMapPattern.getWidth(); colIdx++) {
                if (this.rMapPointMatrix[rowIdx][colIdx].isAvailable()) {
                    int imageIdx = R.nextInt(ImagesLoader.NB_SINGLE_PATHWAY); // get a random single pathway image.
                    Image image = ImagesLoader.imagesMatrix[ImagesLoader.singlePathwayMatrixRowIdx][imageIdx];

                    this.rMapPointMatrix[rowIdx][colIdx].setImage(image);
                    this.rMapPointMatrix[rowIdx][colIdx].setPathway(true);
                    this.rMapPointMatrix[rowIdx][colIdx].setMutable(false);
                    this.rMapPointMatrix[rowIdx][colIdx].setAvailable(false);
                }
            }
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
