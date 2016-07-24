package map;

import exceptions.CannotCreateRMapElementException;
import images.ImagesLoader;

import java.awt.*;
import java.util.Random;

public class PatternMethods {

    /**
     * Is the pattern crossing a map limit?
     *
     * @param mapWidth    the map width
     * @param mapHeight   the map height
     * @param rMapPattern the pattern
     * @param startRowIdx the row index of the north/west pattern corner
     * @param startColIdx the column index of the north/west pattern corner
     * @return true if the pattern is crossing a map limit, false otherwise
     */
    public static boolean isPatternCrossingMapLimit(int mapWidth, int mapHeight,
                                                    RMapPattern rMapPattern, int startRowIdx, int startColIdx) {
        boolean isCrossing = false;
        if (startRowIdx < 0 || startRowIdx + rMapPattern.getHeight() > mapHeight ||
                startColIdx < 0 || startColIdx + rMapPattern.getWidth() > mapWidth) {
            isCrossing = true;
        }
        return isCrossing;
    }

    /**
     * Is the pattern crossing a not available case?
     *
     * @param rMapPointMatrix the map (represented by its matrix of RMapPoint)
     * @param rMapPattern     the pattern
     * @param startRowIdx     the row index of the north/west pattern corner
     * @param startColIdx     the column index of the north/west pattern corner
     * @return true if the pattern is crossing a not available case, false otherwise
     * @implSpec isPatternCrossingMapLimit() must be called before this function!
     */
    public static boolean isPatternCrossingNotAvailableCase(RMapPoint[][] rMapPointMatrix, RMapPattern rMapPattern,
                                                            int startRowIdx, int startColIdx) {
        boolean isCrossing = false;
        for (int rowIdx = startRowIdx; rowIdx < startRowIdx + rMapPattern.getHeight(); rowIdx++) {
            for (int colIdx = startColIdx; colIdx < startColIdx + rMapPattern.getWidth(); colIdx++) {
                if (!rMapPointMatrix[rowIdx][colIdx].isAvailable()) {
                    isCrossing = true;
                }
            }
        }
        return isCrossing;
    }

    /**
     * Try to place an element (based on its pattern) on map.
     * The pointed out case (startRowIdx, startColIdx) corresponds to the north/west corner of the pattern.
     * If the pattern is eligible, create the element and return true, otherwise return false.
     *
     * @param rMapPointMatrix the map (represented by its matrix of RMapPoint)
     * @param mapWidth        the map width
     * @param mapHeight       the map height
     * @param rMapPattern     the pattern of the element
     * @param startRowIdx     the row index of the north/west pattern corner
     * @param startColIdx     the column index of the north/west pattern corner
     * @return true if the element has been placed, false otherwise
     */
    public static boolean placePatternOnMap(RMapPoint[][] rMapPointMatrix, int mapWidth, int mapHeight,
                                            RMapPattern rMapPattern, int startRowIdx, int startColIdx) {

        // firstly check if the pattern is not crossing a map limit or a not available case.
        if (isPatternCrossingMapLimit(mapWidth, mapHeight, rMapPattern, startRowIdx, startColIdx) ||
                isPatternCrossingNotAvailableCase(rMapPointMatrix, rMapPattern, startRowIdx, startColIdx)) {
            return false;
        }

        // then, we create the element.
        for (int rowIdx = 0; rowIdx < rMapPattern.getHeight(); rowIdx++) {
            for (int colIdx = 0; colIdx < rMapPattern.getWidth(); colIdx++) {
                rMapPointMatrix[startRowIdx + rowIdx][startColIdx + colIdx]
                        .setImage(rMapPattern.getImageArray()[(colIdx * rMapPattern.getHeight()) + rowIdx]);
                rMapPointMatrix[startRowIdx + rowIdx][startColIdx + colIdx].setPathway(rMapPattern.isPathway());
                rMapPointMatrix[startRowIdx + rowIdx][startColIdx + colIdx].setMutable(rMapPattern.isMutable());
                rMapPointMatrix[startRowIdx + rowIdx][startColIdx + colIdx].setAvailable(false);
            }
        }
        return true;
    }

    /**
     * Secure the perimeter of an element (based on its pattern) putting single pathways around it.
     * The pointed out case (startRowIdx, startColIdx) corresponds to the north/west corner of the pattern.
     * If a case is not available, go to the next case.
     *
     * @param rMapPointMatrix the map (represented by its matrix of RMapPoint)
     * @param mapWidth        the map width
     * @param mapHeight       the map height
     * @param rMapPattern     the pattern of the element
     * @param startRowIdx     the row index of the north/west pattern corner
     * @param startColIdx     the column index of the north/west pattern corner
     */
    public static void securePerimeter(RMapPoint[][] rMapPointMatrix, int mapWidth, int mapHeight,
                                       RMapPattern rMapPattern, int startRowIdx, int startColIdx) {
        Random R = new Random(); // initStatement the random function.

        for (int rowIdx = Math.min(0, startRowIdx - 1);
             rowIdx <= Math.min(mapHeight - 1, startRowIdx + rMapPattern.getHeight()); rowIdx++) {
            for (int colIdx = Math.min(0, startColIdx - 1);
                 colIdx <= Math.min(mapWidth - 1, startColIdx + rMapPattern.getWidth()); colIdx++) {
                if (rMapPointMatrix[rowIdx][colIdx].isAvailable()) {
                    int imageIdx = R.nextInt(ImagesLoader.NB_SINGLE_PATHWAY); // get a random single pathway image.
                    Image image = ImagesLoader.imagesMatrix[ImagesLoader.singlePathwayMatrixRowIdx][imageIdx];

                    rMapPointMatrix[rowIdx][colIdx].setImage(image);
                    rMapPointMatrix[rowIdx][colIdx].setPathway(true);
                    rMapPointMatrix[rowIdx][colIdx].setMutable(false);
                    rMapPointMatrix[rowIdx][colIdx].setAvailable(false);
                }
            }
        }
    }

    /**
     * Place a north edge on map.
     *
     * @param rMapPointMatrix the map (represented by its matrix of RMapPoint)
     * @param mapWidth        the map width
     * @param mapHeight       the map height
     * @param rMapPattern     the pattern of the element
     * @throws CannotCreateRMapElementException if the north edge has not been fully created
     */
    public static void placeNorthEdgeOnMap(RMapPoint[][] rMapPointMatrix, int mapWidth, int mapHeight,
                                           RMapPattern rMapPattern) throws CannotCreateRMapElementException {
        for (int colIdx = 0; colIdx < mapWidth; colIdx += rMapPattern.getWidth()) {
            if (!placePatternOnMap(rMapPointMatrix, mapWidth, mapHeight, rMapPattern, 0, colIdx)) {
                throw new CannotCreateRMapElementException("not able to create the north edge: mapWidth(=" +
                        mapWidth + ") % patternWidth(=" + rMapPattern.getWidth() + ") != 0).");
            }
        }
    }

    /**
     * Place a south edge on map.
     *
     * @param rMapPointMatrix the map (represented by its matrix of RMapPoint)
     * @param mapWidth        the map width
     * @param mapHeight       the map height
     * @param rMapPattern     the pattern of the element
     * @throws CannotCreateRMapElementException if the south edge has not been fully created
     */
    public static void placeSouthEdgeOnMap(RMapPoint[][] rMapPointMatrix, int mapWidth, int mapHeight,
                                           RMapPattern rMapPattern) throws CannotCreateRMapElementException {
        for (int colIdx = 0; colIdx < mapWidth; colIdx += rMapPattern.getWidth()) {
            if (!placePatternOnMap(rMapPointMatrix, mapWidth, mapHeight, rMapPattern, mapHeight - rMapPattern.getHeight(), colIdx)) {
                throw new CannotCreateRMapElementException("not able to create the south edge: mapWidth(=" +
                        mapWidth + ") % patternWidth(=" + rMapPattern.getWidth() + ") != 0).");
            }
        }
    }

    /**
     * Place a castle map and secure its perimeter.
     *
     * @param rMapPointMatrix the map (represented by its matrix of RMapPoint)
     * @param mapWidth        the map width
     * @param mapHeight       the map height
     * @param rMapPattern     the pattern of the castle
     * @param startRowIdx     the row index of the north/west pattern corner
     * @param startColIdx     the column index of the north/west pattern corner
     * @throws CannotCreateRMapElementException if the castle has not been placed
     */
    public static void placeCastleOnMap(RMapPoint[][] rMapPointMatrix, int mapWidth, int mapHeight,
                                        RMapPattern rMapPattern, int startRowIdx, int startColIdx)
            throws CannotCreateRMapElementException {
        if (!placePatternOnMap(rMapPointMatrix, mapWidth, mapHeight, rMapPattern, startRowIdx, startColIdx)) {
            throw new CannotCreateRMapElementException("not able to create a castle at rowIdx=" +
                    startRowIdx + ", colIdx=" + startColIdx + ".");
        }
        securePerimeter(rMapPointMatrix, mapWidth, mapHeight, rMapPattern, startRowIdx, startColIdx);
    }

    /**
     * Try to place a single obstacle on map.
     * If the case is available, place the obstacle and return true, otherwise return false.
     *
     * @param rMapPoint the RMapPoint to place the obstacle
     * @return true if the obstacle has been placed, false otherwise
     */
    public static boolean placeSingleObstacleOnMap(RMapPoint rMapPoint) {
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
     * Try to place a single mutable on map.
     * If the case is available, place the mutable and return true, otherwise return false.
     *
     * @param rMapPoint the RMapPoint to place the mutable
     * @return true if the mutable has been placed, false otherwise
     */
    public static boolean placeSingleMutableOnMap(RMapPoint rMapPoint) {
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
     * Try to place a single pathway on map.
     * If the case is available, place the pathway and return true, otherwise return false.
     *
     * @param rMapPoint the RMapPoint to place the pathway
     * @return true if the pathway has been placed, false otherwise
     */
    public static boolean placeSinglePathwayOnMap(RMapPoint rMapPoint, int perSingleFlowerPathway) {
        if (rMapPoint.isAvailable()) {
            Random R = new Random(); // initStatement the random function.
            int randomPercent = Math.abs(R.nextInt(100)); // randomly choose a single element.

            if (randomPercent < perSingleFlowerPathway) { // animated flower
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
}
