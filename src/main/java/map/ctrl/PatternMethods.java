package map.ctrl;

import exceptions.CannotCreateMapElementException;
import map.MapPoint;
import map.MapPattern;

import java.util.Random;

import static map.ctrl.SingleMethods.placeSinglePathwayOnMap;

public class PatternMethods {

    /**
     * Place a north edge on map.
     *
     * @param mapPointMatrix the map (represented by its matrix of MapPoint)
     * @param mapWidth        the map width
     * @param mapHeight       the map height
     * @param mapPattern     the pattern of the element
     * @throws CannotCreateMapElementException if the north edge has not been fully created
     */
    public static void placeNorthEdgeOnMap(MapPoint[][] mapPointMatrix, int mapWidth, int mapHeight,
                                           MapPattern mapPattern) throws CannotCreateMapElementException {
        for (int colIdx = 0; colIdx < mapWidth; colIdx += mapPattern.getWidth()) {
            if (!placePatternOnMap(mapPointMatrix, mapWidth, mapHeight, mapPattern, 0, colIdx)) {
                throw new CannotCreateMapElementException("not able to create the north edge: mapWidth(=" +
                        mapWidth + ") % patternWidth(=" + mapPattern.getWidth() + ") != 0).");
            }
        }
    }

    /**
     * Place a south edge on map.
     *
     * @param mapPointMatrix the map (represented by its matrix of MapPoint)
     * @param mapWidth        the map width
     * @param mapHeight       the map height
     * @param mapPattern     the pattern of the element
     * @throws CannotCreateMapElementException if the south edge has not been fully created
     */
    public static void placeSouthEdgeOnMap(MapPoint[][] mapPointMatrix, int mapWidth, int mapHeight,
                                           MapPattern mapPattern) throws CannotCreateMapElementException {
        for (int colIdx = 0; colIdx < mapWidth; colIdx += mapPattern.getWidth()) {
            if (!placePatternOnMap(mapPointMatrix, mapWidth, mapHeight, mapPattern, mapHeight - mapPattern.getHeight(), colIdx)) {
                throw new CannotCreateMapElementException("not able to create the south edge: mapWidth(=" +
                        mapWidth + ") % patternWidth(=" + mapPattern.getWidth() + ") != 0).");
            }
        }
    }

    /**
     * Place a castle map and secure its perimeter.
     *
     * @param mapPointMatrix      the map (represented by its matrix of MapPoint)
     * @param mapWidth             the map width
     * @param mapHeight            the map height
     * @param mapPattern          the pattern of the castle
     * @param startRowIdx          the row index of the north/west pattern corner
     * @param startColIdx          the column index of the north/west pattern corner
     * @param perDynamicPathwayElt the percentage of dynamic patwhay elements to place arround the castle
     * @throws CannotCreateMapElementException if the castle has not been placed
     */
    static void placeCastleOnMap(MapPoint[][] mapPointMatrix, int mapWidth, int mapHeight,
                                        MapPattern mapPattern, int startRowIdx, int startColIdx,
                                        int perDynamicPathwayElt)
            throws CannotCreateMapElementException {
        if (!placePatternOnMap(mapPointMatrix, mapWidth, mapHeight, mapPattern, startRowIdx, startColIdx)) {
            throw new CannotCreateMapElementException("not able to create a castle at rowIdx=" +
                    startRowIdx + ", colIdx=" + startColIdx + ".");
        }
        securePerimeter(mapPointMatrix, mapWidth, mapHeight, mapPattern, startRowIdx, startColIdx, perDynamicPathwayElt);
    }

    /**
     * Try to place an element (based on its pattern) on map.
     * The pointed out case (startRowIdx, startColIdx) corresponds to the north/west corner of the pattern.
     * If the pattern is eligible, create the element and return true, otherwise return false.
     *
     * @param mapPointMatrix the map (represented by its matrix of MapPoint)
     * @param mapWidth        the map width
     * @param mapHeight       the map height
     * @param mapPattern     the pattern of the element
     * @param startRowIdx     the row index of the north/west pattern corner
     * @param startColIdx     the column index of the north/west pattern corner
     * @return true if the element has been placed, false otherwise
     */
    static boolean placePatternOnMap(MapPoint[][] mapPointMatrix, int mapWidth, int mapHeight,
                                            MapPattern mapPattern, int startRowIdx, int startColIdx) {

        // firstly check if the pattern is not crossing a map limit or a not available case.
        if (isPatternCrossingMapLimit(mapWidth, mapHeight, mapPattern, startRowIdx, startColIdx) ||
                isPatternCrossingNotAvailableCase(mapPointMatrix, mapPattern, startRowIdx, startColIdx)) {
            return false;
        }

        // then, we create the element.
        for (int rowIdx = 0; rowIdx < mapPattern.getHeight(); rowIdx++) {
            for (int colIdx = 0; colIdx < mapPattern.getWidth(); colIdx++) {
                mapPointMatrix[startRowIdx + rowIdx][startColIdx + colIdx]
                        .setImage(mapPattern.getImageArray()[(colIdx * mapPattern.getHeight()) + rowIdx]);
                mapPointMatrix[startRowIdx + rowIdx][startColIdx + colIdx].setPathway(mapPattern.isPathway());
                mapPointMatrix[startRowIdx + rowIdx][startColIdx + colIdx].setMutable(mapPattern.isMutable());
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
     * @param mapPattern the pattern
     * @param startRowIdx the row index of the north/west pattern corner
     * @param startColIdx the column index of the north/west pattern corner
     * @return true if the pattern is crossing a map limit, false otherwise
     */
    static boolean isPatternCrossingMapLimit(int mapWidth, int mapHeight, MapPattern mapPattern,
                                                    int startRowIdx, int startColIdx) {
        boolean isCrossing = false;
        if (startRowIdx < 0 || startRowIdx + mapPattern.getHeight() > mapHeight ||
                startColIdx < 0 || startColIdx + mapPattern.getWidth() > mapWidth) {
            isCrossing = true;
        }
        return isCrossing;
    }

    /**
     * Is the pattern crossing a not available case?
     *
     * @param mapPointMatrix the map (represented by its matrix of MapPoint)
     * @param mapPattern     the pattern
     * @param startRowIdx     the row index of the north/west pattern corner
     * @param startColIdx     the column index of the north/west pattern corner
     * @return true if the pattern is crossing a not available case, false otherwise
     * @implSpec isPatternCrossingMapLimit() must be called before this function!
     */
    static boolean isPatternCrossingNotAvailableCase(MapPoint[][] mapPointMatrix, MapPattern mapPattern,
                                                            int startRowIdx, int startColIdx) {
        boolean isCrossing = false;
        for (int rowIdx = startRowIdx; rowIdx < startRowIdx + mapPattern.getHeight(); rowIdx++) {
            for (int colIdx = startColIdx; colIdx < startColIdx + mapPattern.getWidth(); colIdx++) {
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
     * @param mapPointMatrix      the map (represented by its matrix of MapPoint)
     * @param mapWidth             the map width
     * @param mapHeight            the map height
     * @param mapPattern          the pattern of the element
     * @param startRowIdx          the row index of the north/west pattern corner
     * @param startColIdx          the column index of the north/west pattern corner
     * @param perDynamicPathwayElt the percentage of dynamic pathway elements to place arround the castle
     */
    static void securePerimeter(MapPoint[][] mapPointMatrix, int mapWidth, int mapHeight,
                                       MapPattern mapPattern, int startRowIdx, int startColIdx,
                                       int perDynamicPathwayElt) {
        for (int rowIdx = Math.max(0, startRowIdx - 1); rowIdx <= Math.min(mapHeight - 1,
                startRowIdx + mapPattern.getHeight()); rowIdx++) {
            for (int colIdx = Math.max(0, startColIdx - 1); colIdx <= Math.min(mapWidth - 1,
                    startColIdx + mapPattern.getWidth()); colIdx++) {
                if (mapPointMatrix[rowIdx][colIdx].isAvailable()) {
                    placeSinglePathwayOnMap(mapPointMatrix[rowIdx][colIdx], perDynamicPathwayElt);
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
    static int generateRandomRowIdx(int mapHeight, int northEdgeHeight, int southEdgeHeight, int patternHeight,
                                           int marginRange) throws IllegalArgumentException {
        int bound = mapHeight - 2 * marginRange - // north/south requiered margins.
                patternHeight - // pattern height as we place its north/west point.
                (northEdgeHeight + southEdgeHeight); // south egde height + north edge height.
        if (bound == 0) {
            return northEdgeHeight + marginRange;
        }
        return Math.abs(new Random().nextInt(bound)) +
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
    static int generateRandomColIdx(int mapWidth, int eastEdgeWidth, int westEdgeWidth, int patternWidth,
                                           int marginRange) throws IllegalArgumentException {
        int bound = mapWidth - 2 * marginRange - // east/west requiered margins.
                patternWidth - // pattern width as we place its noth/west point.
                (eastEdgeWidth + westEdgeWidth); // east egde width + west edge width.
        if (bound == 0) {
            return eastEdgeWidth + marginRange;
        }
        return Math.abs(new Random().nextInt(bound)) +
                eastEdgeWidth + marginRange; // re-add east edge width + margin to get the right range.
    }
}