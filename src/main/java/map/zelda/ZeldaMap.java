package map.zelda;

import ai.PathFinding;
import exceptions.CannotCreateMapElementException;
import exceptions.CannotFindPathFromEntranceToExitException;
import exceptions.CannotPlaceBonusOnMapException;
import map.Map;
import map.MapPattern;
import map.MapPoint;
import map.ctrl.GenerationMethods;
import utils.Tuple2;

import java.util.ArrayList;
import java.util.Set;

import static images.ImagesLoader.IMAGE_SIZE;
import static map.ctrl.PatternMethods.placeNorthEdgeOnMap;
import static map.ctrl.PatternMethods.placeSouthEdgeOnMap;
import static map.zelda.ZeldaMapPatterns.*;

public class ZeldaMap extends Map {
    private final ZeldaMapSetting zeldaMapSetting;
    private MapPoint entranceStartPoint; // entrance start point (north/west MapPoint).
    private MapPoint exitStartPoint; // castle start point (north/west MapPoint).

    public ZeldaMap(ZeldaMapSetting zeldaMapSetting, int screenWidth, int screenHeight) {
        super(zeldaMapSetting, screenWidth, screenHeight);
        this.zeldaMapSetting = zeldaMapSetting;
    }

    @Override
    public void generateMap() throws CannotCreateMapElementException, CannotFindPathFromEntranceToExitException {
        int maxNbTry = 10;

        // place north/south edges.
        placeNorthEdgeOnMap(mapPointMatrix, zeldaMapSetting.getMapWidth(), zeldaMapSetting.getMapHeight(), greenTree);
        placeSouthEdgeOnMap(mapPointMatrix, zeldaMapSetting.getMapWidth(), zeldaMapSetting.getMapHeight(), edge);

        // place entrance/exit.
        Tuple2<MapPattern, MapPattern> entranceAndExitPatterns = new Tuple2<>(castle, trunk);
        Tuple2<MapPoint, MapPoint> spEntranceAndExit =
                GenerationMethods.randomlyPlaceEntranceAndExit(mapPointMatrix,
                        zeldaMapSetting.getMapWidth(),
                        zeldaMapSetting.getMapHeight(),
                        zeldaMapSetting.getHorizontalMargin(),
                        greenTree.getHeight(),
                        edge.getHeight(),
                        zeldaMapSetting.getVerticalMargin(),
                        entranceAndExitPatterns,
                        zeldaMapSetting.getPerDecoratedSinglePathway(),
                        zeldaMapSetting.getPerDynamicSinglePathway());
        entranceStartPoint = spEntranceAndExit.getFirst();
        exitStartPoint = spEntranceAndExit.getSecond();

        try {
            // place complex elements.
            ArrayList<Tuple2<MapPattern, Integer>> complexEltPatterns = new ArrayList<Tuple2<MapPattern, Integer>>() {{
                add(new Tuple2<>(orchad, zeldaMapSetting.getNbOrchard()));
                add(new Tuple2<>(trough, zeldaMapSetting.getNbTrough()));
                add(new Tuple2<>(greenTree, zeldaMapSetting.getNbGreenTree()));
                add(new Tuple2<>(redTree, zeldaMapSetting.getNbRedTree()));
                add(new Tuple2<>(yellowTree, zeldaMapSetting.getNbYellowTree()));
                add(new Tuple2<>(pathway, zeldaMapSetting.getNbPathway()));
                add(new Tuple2<>(statue, zeldaMapSetting.getNbStatue()));
            }};
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

        // complete with single elements.
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

        // check if there does exit a path between entrance and exit.
        Tuple2<Boolean, Set<PathFinding.Point>> res =
                PathFinding.isThereAPathBetweenTwoPoints(mapPointMatrix,
                        zeldaMapSetting.getMapWidth(),
                        zeldaMapSetting.getMapHeight(),
                        new PathFinding.Point(entranceStartPoint.getColIdx(), entranceStartPoint.getRowIdx()),
                        new PathFinding.Point(exitStartPoint.getColIdx(), exitStartPoint.getRowIdx() - 1));
        if (!res.getFirst()) {
            throw new CannotFindPathFromEntranceToExitException("not able to find a path between entrance and exit: "
                    + "the proportion of immutable patterns/obstacles must be to high, please check the relative "
                    + "properties file.");
        }
    }

    @Override
    public Tuple2<Integer, Integer> computeInitialBbManPosition() {

        // compute the initial bomber position in order to be in front of the entrance door.
        int xBbManOnMap = entranceStartPoint.getColIdx() * IMAGE_SIZE +
                (castle.getWidth() * IMAGE_SIZE / 2);
        int yBbManOnMap = entranceStartPoint.getRowIdx() * IMAGE_SIZE +
                (castle.getHeight() * IMAGE_SIZE) + (IMAGE_SIZE / 2);
        return new Tuple2<>(xBbManOnMap, yBbManOnMap);
    }

    @Override
    public Tuple2<Integer, Integer> computeExitSignPosition() {

        // compute the best position to print exit sign (e.g. sparkle).
        int xBbManOnMap = exitStartPoint.getColIdx() * IMAGE_SIZE +
                (trunk.getWidth() * IMAGE_SIZE / 2);
        int yBbManOnMap = exitStartPoint.getRowIdx() * IMAGE_SIZE +
                (trunk.getHeight() * IMAGE_SIZE) / 2;
        return new Tuple2<>(xBbManOnMap, yBbManOnMap);
    }
}
