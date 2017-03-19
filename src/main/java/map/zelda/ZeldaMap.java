package map.zelda;

import exceptions.CannotCreateMapElementException;
import exceptions.CannotPlaceBonusOnMapException;
import map.Map;
import map.MapPattern;
import map.MapPoint;
import map.ctrl.GenerationMethods;
import utils.Tuple2;

import java.util.HashMap;

import static images.ImagesLoader.IMAGE_SIZE;
import static map.ctrl.PatternMethods.placeNorthEdgeOnMap;
import static map.ctrl.PatternMethods.placeSouthEdgeOnMap;
import static map.zelda.ZeldaMapPatterns.*;

public class ZeldaMap extends Map {
    private final ZeldaMapSetting zeldaMapSetting;
    private MapPoint castleStartPoint; // castle start point (north/west MapPoint).

    public ZeldaMap(ZeldaMapSetting zeldaMapSetting, int screenWidth, int screenHeight) {
        super(zeldaMapSetting, screenWidth, screenHeight);
        this.zeldaMapSetting = zeldaMapSetting;
        this.mapPointMatrix = new MapPoint[zeldaMapSetting.getMapHeight()][zeldaMapSetting.getMapWidth()];
        for (int rowIdx = 0; rowIdx < zeldaMapSetting.getMapHeight(); rowIdx++) {
            for (int colIdx = 0; colIdx < zeldaMapSetting.getMapWidth(); colIdx++) {
                this.mapPointMatrix[rowIdx][colIdx] = new MapPoint(rowIdx, colIdx);
            }
        }
    }

    @Override
    public void generateMap() throws CannotCreateMapElementException {
        int maxNbTry = 10;

        // place north and south edges.
        placeNorthEdgeOnMap(mapPointMatrix, zeldaMapSetting.getMapWidth(), zeldaMapSetting.getMapHeight(), greenTree);
        placeSouthEdgeOnMap(mapPointMatrix, zeldaMapSetting.getMapWidth(), zeldaMapSetting.getMapHeight(), edge);

        // place castles.
        Tuple2<MapPattern, MapPattern> castlePatterns = new Tuple2<>(castle, trunk);
        Tuple2<MapPoint, MapPoint> spCastles =
                GenerationMethods.randomlyPlaceCastles(mapPointMatrix,
                        zeldaMapSetting.getMapWidth(),
                        zeldaMapSetting.getMapHeight(),
                        zeldaMapSetting.getHorizontalMargin(),
                        greenTree.getHeight(),
                        edge.getHeight(),
                        zeldaMapSetting.getVerticalMargin(),
                        castlePatterns,
                        zeldaMapSetting.getPerDecoratedSinglePathway(),
                        zeldaMapSetting.getPerDynamicSinglePathway());
        castleStartPoint = spCastles.getFirst();

        // place complex elements.
        try {
            java.util.Map<MapPattern, Integer> complexEltPatterns = new HashMap<>();
            complexEltPatterns.put(orchad, zeldaMapSetting.getNbOrchard());
            complexEltPatterns.put(trough, zeldaMapSetting.getNbTrough());
            complexEltPatterns.put(greenTree, zeldaMapSetting.getNbGreenTree());
            complexEltPatterns.put(redTree, zeldaMapSetting.getNbRedTree());
            complexEltPatterns.put(yellowTree, zeldaMapSetting.getNbYellowTree());
            complexEltPatterns.put(pathway, zeldaMapSetting.getNbPathway());
            complexEltPatterns.put(statue, zeldaMapSetting.getNbStatue());
            GenerationMethods.randomlyPlaceComplexElements(mapPointMatrix,
                    zeldaMapSetting.getMapWidth(),
                    zeldaMapSetting.getMapHeight(),
                    greenTree.getHeight(),
                    edge.getHeight(),
                    complexEltPatterns,
                    maxNbTry);
        } catch (CannotCreateMapElementException e) {
            System.out.print(e.getMessage() + "\n"); // log only, not very important.
        }

        // place single elements.
        GenerationMethods.randomlyPlaceSingleElements(mapPointMatrix,
                zeldaMapSetting.getMapWidth(),
                zeldaMapSetting.getMapHeight(),
                zeldaMapSetting.getPerSingleImmutableObstacle(),
                zeldaMapSetting.getPerSingleMutableObstacle(),
                zeldaMapSetting.getPerDecoratedSinglePathway(),
                zeldaMapSetting.getPerDynamicSinglePathway());

        // place bonus.
        try {
            GenerationMethods.randomlyPlaceBonus(mapPointMatrix,
                    zeldaMapSetting.getMapWidth(),
                    zeldaMapSetting.getMapHeight(),
                    zeldaMapSetting.getNbBonusBomb(),
                    zeldaMapSetting.getNbBonusFlame(),
                    zeldaMapSetting.getNbBonusHeart(),
                    zeldaMapSetting.getNbBonusRoller());
        } catch (CannotPlaceBonusOnMapException e) {
            System.out.print(e.getMessage() + "\n"); // log only, not very important.
        }
    }

    @Override
    public Tuple2<Integer, Integer> computeInitialBbManPosition() {

        // compute the initial bomber position in order to be in front of the entrance door.
        int xBbManOnMap = castleStartPoint.getColIdx() * IMAGE_SIZE +
                (castle.getWidth() * IMAGE_SIZE / 2);
        int yBbManOnMap = castleStartPoint.getRowIdx() * IMAGE_SIZE +
                (castle.getHeight() * IMAGE_SIZE) + (IMAGE_SIZE / 2);
        return new Tuple2<>(xBbManOnMap, yBbManOnMap);
    }
}
