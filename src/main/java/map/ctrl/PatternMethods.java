package map.ctrl;

import java.util.Random;
import exceptions.CannotCreateMapElementException;
import lombok.experimental.UtilityClass;
import map.MapPattern;
import map.MapPoint;
import static map.ctrl.SingleMethods.placeSinglePathwayOnMap;

@UtilityClass
public class PatternMethods {

    private static final Random R = new Random();

    /**
     * Place a north edge on map.
     *
     * @param mapPointMatrix the map (represented by its matrix of MapPoint)
     * @param mapWidth       the map width
     * @param mapHeight      the map height
     * @param mapPattern     the pattern of the element
     * @throws CannotCreateMapElementException if the north edge has not been fully created
     */
    public static void placeNorthEdgeOnMap(MapPoint[][] mapPointMatrix, int mapWidth, int mapHeight,
                                           MapPattern mapPattern) throws CannotCreateMapElementException {
        for (int colIdx = 0; colIdx < mapWidth; colIdx += mapPattern.width()) {
            if (!placePatternOnMap(mapPointMatrix, mapWidth, mapHeight, mapPattern, 0, colIdx)) {
                throw new CannotCreateMapElementException("not able to create the north edge: mapWidth(=" +
                    mapWidth + ") % patternWidth(=" + mapPattern.width() + ") != 0).");
            }
        }
    }

    /**
     * Place a south edge on map.
     *
     * @param mapPointMatrix the map (represented by its matrix of MapPoint)
     * @param mapWidth       the map width
     * @param mapHeight      the map height
     * @param mapPattern     the pattern of the element
     * @throws CannotCreateMapElementException if the south edge has not been fully created
     */
    public static void placeSouthEdgeOnMap(MapPoint[][] mapPointMatrix,
                                           int mapWidth,
                                           int mapHeight,
                                           MapPattern mapPattern) throws CannotCreateMapElementException {
        for (int colIdx = 0; colIdx < mapWidth; colIdx += mapPattern.width()) {
            if (!placePatternOnMap(mapPointMatrix, mapWidth, mapHeight, mapPattern, mapHeight - mapPattern.height(),
                colIdx)) {
                throw new CannotCreateMapElementException("not able to create the south edge: mapWidth(=" +
                    mapWidth + ") % patternWidth(=" + mapPattern.width() + ") != 0).");
            }
        }
    }

    /**
     * Place an element (based on its pattern) on map and secure its perimeter.
     *
     * @param mapPointMatrix            the map (represented by its matrix of MapPoint)
     * @param mapWidth                  the map width
     * @param mapHeight                 the map height
     * @param mapPattern                the pattern of the element
     * @param startRowIdx               the row index of the north/west pattern corner
     * @param startColIdx               the column index of the north/west pattern corner
     * @param isExit                    is the pattern to secure is an exit?
     * @param perDecoratedSinglePathway the percentage of decorated elements to place among single pathway
     * @param perDynamicSinglePathway   the percentage of dynamic elements to place among decorated single pathway
     * @throws CannotCreateMapElementException if the element has not been placed
     */
    public static void placePatternOnMapAndSecurePerimeter(MapPoint[][] mapPointMatrix,
                                                           int mapWidth,
                                                           int mapHeight,
                                                           MapPattern mapPattern,
                                                           int startRowIdx,
                                                           int startColIdx,
                                                           boolean isExit,
                                                           int perDecoratedSinglePathway,
                                                           int perDynamicSinglePathway)
        throws CannotCreateMapElementException {
        if (!placePatternOnMap(mapPointMatrix, mapWidth, mapHeight, mapPattern, startRowIdx, startColIdx)) {
            throw new CannotCreateMapElementException("not able to create an element at rowIdx=" +
                startRowIdx + ", colIdx=" + startColIdx + ".");
        }
        securePerimeter(mapPointMatrix,
            mapWidth,
            mapHeight,
            mapPattern,
            startRowIdx,
            startColIdx,
            isExit,
            perDecoratedSinglePathway,
            perDynamicSinglePathway);
    }

    /**
     * Try to place an element (based on its pattern) on map.
     * The pointed out case (startRowIdx, startColIdx) corresponds to the north/west corner of the pattern.
     * If the pattern is eligible, create the element and return true, otherwise return false.
     *
     * @param mapPointMatrix the map (represented by its matrix of MapPoint)
     * @param mapWidth       the map width
     * @param mapHeight      the map height
     * @param mapPattern     the pattern of the element
     * @param startRowIdx    the row index of the north/west pattern corner
     * @param startColIdx    the column index of the north/west pattern corner
     * @return true if the element has been placed, false otherwise
     */
    public static boolean placePatternOnMap(MapPoint[][] mapPointMatrix,
                                            int mapWidth,
                                            int mapHeight,
                                            MapPattern mapPattern,
                                            int startRowIdx,
                                            int startColIdx) {

        // firstly check if the pattern is not crossing a map limit or a not available case.
        if (isPatternCrossingMapLimit(mapWidth, mapHeight, mapPattern, startRowIdx, startColIdx) ||
            isPatternCrossingNotAvailableCase(mapPointMatrix, mapPattern, startRowIdx, startColIdx)) {
            return false;
        }

        // then, we create the element.
        for (int rowIdx = 0; rowIdx < mapPattern.height(); rowIdx++) {
            for (int colIdx = 0; colIdx < mapPattern.width(); colIdx++) {
                mapPointMatrix[startRowIdx + rowIdx][startColIdx + colIdx]
                    .setImage(mapPattern.imageArray()[(rowIdx * mapPattern.width()) + colIdx]);
                mapPointMatrix[startRowIdx + rowIdx][startColIdx + colIdx].setPathway(mapPattern.isPathway());
                mapPointMatrix[startRowIdx + rowIdx][startColIdx + colIdx].setMutable(mapPattern.isMutable());
                mapPointMatrix[startRowIdx + rowIdx][startColIdx + colIdx].setEntrance(mapPattern.isEntrance());
                mapPointMatrix[startRowIdx + rowIdx][startColIdx + colIdx].setExit(mapPattern.isExit());
                mapPointMatrix[startRowIdx + rowIdx][startColIdx + colIdx].setAvailable(false);
            }
        }
        return true;
    }

    /**
     * Is the pattern crossing a map limit?
     *
     * @param mapWidth    the map width
     * @param mapHeight   the map height
     * @param mapPattern  the pattern
     * @param startRowIdx the row index of the north/west pattern corner
     * @param startColIdx the column index of the north/west pattern corner
     * @return true if the pattern is crossing a map limit, false otherwise
     */
    public static boolean isPatternCrossingMapLimit(int mapWidth,
                                                    int mapHeight,
                                                    MapPattern mapPattern,
                                                    int startRowIdx,
                                                    int startColIdx) {
        return startRowIdx < 0 || startRowIdx + mapPattern.height() > mapHeight ||
            startColIdx < 0 || startColIdx + mapPattern.width() > mapWidth;
    }

    /**
     * Is the pattern crossing a not available case?
     *
     * @param mapPointMatrix the map (represented by its matrix of MapPoint)
     * @param mapPattern     the pattern
     * @param startRowIdx    the row index of the north/west pattern corner
     * @param startColIdx    the column index of the north/west pattern corner
     * @return true if the pattern is crossing a not available case, false otherwise
     * @implSpec isPatternCrossingMapLimit() must be called before this function!
     */
    public static boolean isPatternCrossingNotAvailableCase(MapPoint[][] mapPointMatrix,
                                                            MapPattern mapPattern,
                                                            int startRowIdx,
                                                            int startColIdx) {
        boolean isCrossing = false;
        for (int rowIdx = startRowIdx; rowIdx < startRowIdx + mapPattern.height(); rowIdx++) {
            for (int colIdx = startColIdx; colIdx < startColIdx + mapPattern.width(); colIdx++) {
                if (!mapPointMatrix[rowIdx][colIdx].isAvailable()) {
                    isCrossing = true;
                }
            }
        }
        return isCrossing;
    }

    /**
     * Secure the perimeter of an element (based on its pattern) putting single pathways around it.
     * The pointed out case (startRowIdx, startColIdx) corresponds to the north/west corner of the pattern.
     * If a case is not available, go to the next case.
     *
     * @param mapPointMatrix            the map (represented by its matrix of MapPoint)
     * @param mapWidth                  the map width
     * @param mapHeight                 the map height
     * @param mapPattern                the pattern of the element
     * @param startRowIdx               the row index of the north/west pattern corner
     * @param startColIdx               the column index of the north/west pattern corner
     * @param isExit                    is the pattern to secure is an exit?
     * @param perDecoratedSinglePathway the percentage of decorated elements to place among single pathway
     * @param perDynamicSinglePathway   the percentage of dynamic elements to place among decorated single pathway
     */
    public static void securePerimeter(MapPoint[][] mapPointMatrix,
                                       int mapWidth,
                                       int mapHeight,
                                       MapPattern mapPattern,
                                       int startRowIdx,
                                       int startColIdx,
                                       boolean isExit,
                                       int perDecoratedSinglePathway,
                                       int perDynamicSinglePathway) {
        for (int rowIdx = Math.max(0, startRowIdx - 1); rowIdx <= Math.min(mapHeight - 1,
            startRowIdx + mapPattern.height()); rowIdx++) {
            for (int colIdx = Math.max(0, startColIdx - 1); colIdx <= Math.min(mapWidth - 1,
                startColIdx + mapPattern.width()); colIdx++) {
                if (mapPointMatrix[rowIdx][colIdx].isAvailable()) {
                    if (placeSinglePathwayOnMap(mapPointMatrix[rowIdx][colIdx],
                        perDecoratedSinglePathway,
                        perDynamicSinglePathway) && isExit) {
                        mapPointMatrix[rowIdx][colIdx].setExit(true);
                    }
                }
            }
        }
    }

    /**
     * Generate a random rowIdx based on a pattern height and a margin range.
     * This function is used to place elements at a certain margin from north and south sides.
     *
     * @param mapHeight       the map height
     * @param northEdgeHeight the north edge height
     * @param southEdgeHeight the south edge height
     * @param patternHeight   the pattern height
     * @param marginRange     the margin range
     * @return the random rowIdx
     * @throws IllegalArgumentException if a random rowIdx cannot be computed (not applicable)
     */
    public static int generateRandomRowIdx(int mapHeight,
                                           int northEdgeHeight,
                                           int southEdgeHeight,
                                           int patternHeight,
                                           int marginRange) throws IllegalArgumentException {
        int bound = mapHeight - 2 * marginRange - // north/south requiered margins.
            patternHeight - // pattern height as we place its north/west point.
            (northEdgeHeight + southEdgeHeight); // south egde height + north edge height.
        if (bound == 0) {
            return northEdgeHeight + marginRange;
        }
        return Math.abs(R.nextInt(bound)) +
            northEdgeHeight + marginRange; // re-add north edge height + margin to get the right range.
    }

    /**
     * Generate a random colIdx based on a pattern width and a margin range.
     * This function is used to place elements at a certain margin from east and west sides.
     *
     * @param mapWidth      the map width
     * @param eastEdgeWidth the east edge width
     * @param westEdgeWidth the west edge width
     * @param patternWidth  the pattern width
     * @param marginRange   the margin range
     * @return the random colIdx
     * @throws IllegalArgumentException if a random colIdx cannot be computed (not applicable)
     */
    public static int generateRandomColIdx(int mapWidth,
                                           int eastEdgeWidth,
                                           int westEdgeWidth,
                                           int patternWidth,
                                           int marginRange) throws IllegalArgumentException {
        int bound = mapWidth - 2 * marginRange - // east/west requiered margins.
            patternWidth - // pattern width as we place its noth/west point.
            (eastEdgeWidth + westEdgeWidth); // east egde width + west edge width.
        if (bound == 0) {
            return eastEdgeWidth + marginRange;
        }
        return Math.abs(R.nextInt(bound)) +
            eastEdgeWidth + marginRange; // re-add east edge width + margin to get the right range.
    }
}
