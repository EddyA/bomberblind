package map.zelda;

import exceptions.CannotCreateMapElementException;
import exceptions.InvalidMapConfigurationException;
import map.MapPattern;
import map.MapPoint;
import map.ctrl.GenerationMethods;
import utils.Tuple2;

import java.io.IOException;
import java.util.HashMap;

import static images.ImagesLoader.IMAGE_SIZE;
import static map.ctrl.PatternMethods.placeNorthEdgeOnMap;
import static map.ctrl.PatternMethods.placeSouthEdgeOnMap;
import static map.zelda.ZeldaMapPatterns.*;

public class ZeldaMap extends map.abstracts.Map {
    protected ZeldaMapSetting zeldaMapSetting;
    protected MapPoint castleStartPoint; // castle start point (north/west MapPoint).

    public ZeldaMap(ZeldaMapSetting zeldaMapSetting, int screenWidth, int screenHeight)
            throws IOException, InvalidMapConfigurationException, CannotCreateMapElementException {
        super(zeldaMapSetting, screenWidth, screenHeight);
        this.zeldaMapSetting = zeldaMapSetting;
        this.mapPointMatrix = new MapPoint[zeldaMapSetting.getMapHeight()][zeldaMapSetting.getMapWidth()];
        for (int rowIdx = 0; rowIdx < zeldaMapSetting.getMapHeight(); rowIdx++) {
            for (int colIdx = 0; colIdx < zeldaMapSetting.getMapWidth(); colIdx++) {
                this.mapPointMatrix[rowIdx][colIdx] = new MapPoint(rowIdx, colIdx);
            }
        }
    }

    public void generateMap() throws CannotCreateMapElementException {
        int maxNbTry = 10;

        // place north and south edges.
        placeNorthEdgeOnMap(mapPointMatrix, zeldaMapSetting.getMapWidth(), zeldaMapSetting.getMapHeight(), tree1);
        placeSouthEdgeOnMap(mapPointMatrix, zeldaMapSetting.getMapWidth(), zeldaMapSetting.getMapHeight(), edge);

        // place castles.
        Tuple2<MapPattern, MapPattern> castlePatterns = new Tuple2<>(castle1, castle2);
        Tuple2<MapPoint, MapPoint> spCastles = GenerationMethods.randomlyPlaceCastles(mapPointMatrix,
                zeldaMapSetting.getMapWidth(), zeldaMapSetting.getMapHeight(), zeldaMapSetting.getHorizontalMargin(),
                tree1.getHeight(), edge.getHeight(), zeldaMapSetting.getVerticalMargin(), castlePatterns,
                zeldaMapSetting.getPerSingleDynPathway());
        castleStartPoint = spCastles.getFirst();

        // place complex elements.
        java.util.Map<MapPattern, Integer> complexEltPatterns = new HashMap<>();
        complexEltPatterns.put(wood1, zeldaMapSetting.getNbWood1());
        complexEltPatterns.put(wood2, zeldaMapSetting.getNbWood2());
        complexEltPatterns.put(tree1, zeldaMapSetting.getNbTree1());
        complexEltPatterns.put(tree2, zeldaMapSetting.getNbTree2());
        complexEltPatterns.put(puddle1, zeldaMapSetting.getNbPuddle1());
        complexEltPatterns.put(puddle2, zeldaMapSetting.getNbPuddle2());
        GenerationMethods.randomlyPlaceComplexElements(mapPointMatrix, zeldaMapSetting.getMapWidth(), zeldaMapSetting.getMapHeight(),
                tree1.getHeight(), edge.getHeight(), complexEltPatterns, maxNbTry);

        // place single elements.
        GenerationMethods.randomlyPlaceSingleElements(mapPointMatrix, zeldaMapSetting.getMapWidth(), zeldaMapSetting.getMapHeight(),
                zeldaMapSetting.getPerSingleMutable(), zeldaMapSetting.getPerSingleObstacle(), zeldaMapSetting.getPerSingleDynPathway());
    }

    public Tuple2<Integer, Integer> computeInitialBbManPosition() {

        // compute the initial BbMan position in order to be in front of the 1st castle door.
        int xBbManOnMap = castleStartPoint.getColIdx() * IMAGE_SIZE +
                (castle1.getWidth() * IMAGE_SIZE / 2);
        int yBbManOnMap = castleStartPoint.getRowIdx() * IMAGE_SIZE +
                (castle1.getHeight() * IMAGE_SIZE) + (IMAGE_SIZE / 2);
        return new Tuple2<>(xBbManOnMap, yBbManOnMap);
    }
}
