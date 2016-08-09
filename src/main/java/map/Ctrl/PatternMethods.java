package map.Ctrl;

import exceptions.CannotCreateMapElementException;
import map.RMapPattern;
import map.RMapPoint;

import java.util.Random;

import static map.Ctrl.SingleMethods.placeSinglePathwayOnMap;

public class PatternMethods {

    /**
     * Place a north edge on map.
     *
     * @param rMapPointMatrix the map (represented by its matrix of RMapPoint)
     * @param mapWidth        the map width
     * @param mapHeight       the map height
     * @param rMapPattern     the pattern of the element
     * @throws CannotCreateMapElementException if the north edge has not been fully created
     */
    public static void placeNorthEdgeOnMap(RMapPoint[][] rMapPointMatrix, int mapWidth, int mapHeight,
                                           RMapPattern rMapPattern) throws CannotCreateMapElementException {
        for (int colIdx = 0; colIdx < mapWidth; colIdx += rMapPattern.getWidth()) {
            if (!placePatternOnMap(rMapPointMatrix, mapWidth, mapHeight, rMapPattern, 0, colIdx)) {
                throw new CannotCreateMapElementException("not able to create the north edge: mapWidth(=" +
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
     * @throws CannotCreateMapElementException if the south edge has not been fully created
     */
    public static void placeSouthEdgeOnMap(RMapPoint[][] rMapPointMatrix, int mapWidth, int mapHeight,
                                           RMapPattern rMapPattern) throws CannotCreateMapElementException {
        for (int colIdx = 0; colIdx < mapWidth; colIdx += rMapPattern.getWidth()) {
            if (!placePatternOnMap(rMapPointMatrix, mapWidth, mapHeight, rMapPattern, mapHeight - rMapPattern.getHeight(), colIdx)) {
                throw new CannotCreateMapElementException("not able to create the south edge: mapWidth(=" +
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
     * @throws CannotCreateMapElementException if the castle has not been placed
     */
    public static void placeCastleOnMap(RMapPoint[][] rMapPointMatrix, int mapWidth, int mapHeight,
                                        RMapPattern rMapPattern, int startRowIdx, int startColIdx,
                                        int perDynamicElt)
            throws CannotCreateMapElementException {
        if (!placePatternOnMap(rMapPointMatrix, mapWidth, mapHeight, rMapPattern, startRowIdx, startColIdx)) {
            throw new CannotCreateMapElementException("not able to create a castle at rowIdx=" +
                    startRowIdx + ", colIdx=" + startColIdx + ".");
        }
        securePerimeter(rMapPointMatrix, mapWidth, mapHeight, rMapPattern, startRowIdx, startColIdx, perDynamicElt);
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
     * Is the pattern crossing a map limit?
     *
     * @param mapWidth    the map width
     * @param mapHeight   the map height
     * @param rMapPattern the pattern
     * @param startRowIdx the row index of the north/west pattern corner
     * @param startColIdx the column index of the north/west pattern corner
     * @return true if the pattern is crossing a map limit, false otherwise
     */
    public static boolean isPatternCrossingMapLimit(int mapWidth, int mapHeight, RMapPattern rMapPattern,
                                                    int startRowIdx, int startColIdx) {
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
                                       RMapPattern rMapPattern, int startRowIdx, int startColIdx, int perDynamicElt) {
        for (int rowIdx = Math.max(0, startRowIdx - 1); rowIdx <= Math.min(mapHeight - 1,
                startRowIdx + rMapPattern.getHeight()); rowIdx++) {
            for (int colIdx = Math.max(0, startColIdx - 1); colIdx <= Math.min(mapWidth - 1,
                    startColIdx + rMapPattern.getWidth()); colIdx++) {
                if (rMapPointMatrix[rowIdx][colIdx].isAvailable()) {
                    placeSinglePathwayOnMap(rMapPointMatrix[rowIdx][colIdx], perDynamicElt);
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
    public static int generateRandomRowIdx(int mapHeight, int northEdgeHeight, int southEdgeHeight, int patternHeight,
                                           int marginRange) throws IllegalArgumentException {
        int bound = mapHeight - 2 * marginRange - // north/south requiered margins.
                patternHeight - // pattern height as we place its north/west point.
                (northEdgeHeight + southEdgeHeight); // south egde height + north edge height.
        if (bound == 0) {
            return northEdgeHeight + marginRange;
        }
        Random R = new Random(); // init the random function.
        return R.nextInt(bound) +
                northEdgeHeight + marginRange; // re-add north edge height + margin to get the right range.
    }

    /**
     * Generate a random colIdx based on a pattern width and a margin range.
     * This function is used to place elements at a certain margin from east and west sides.
     *
     * @param mapWidth       the map width
     * @param eastEdgeHeight the east edge height
     * @param westEdgeHeight the west edge height
     * @param patternWidth   the pattern height
     * @param marginRange    the margin range
     * @return the random rowIdx
     * @throws IllegalArgumentException if a random colIdx cannot be computed (not applicable)
     */
    public static int generateRandomColIdx(int mapWidth, int eastEdgeHeight, int westEdgeHeight, int patternWidth,
                                           int marginRange) throws IllegalArgumentException {
        int bound = mapWidth - 2 * marginRange - // east/west requiered margins.
                patternWidth - // pattern width as we place its noth/west point.
                (eastEdgeHeight + westEdgeHeight); // east egde height + west edge height.
        if (bound == 0) {
            return eastEdgeHeight + marginRange;
        }
        Random R = new Random(); // init the random function.
        return R.nextInt(bound) +
                eastEdgeHeight + marginRange; // re-add east edge height + margin to get the right range.
    }
}