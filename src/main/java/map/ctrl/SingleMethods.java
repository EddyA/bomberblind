package map.ctrl;

import images.ImagesLoader;
import map.MapPoint;
import utils.Tuple3;

import java.awt.*;
import java.util.Random;


public class SingleMethods {

    /**
     * Try to place a single pathway on map.
     * If the case is available, place the pathway and return true, otherwise return false.
     *
     * @param mapPoint     the MapPoint to place the pathway
     * @param perDynamicElt the percentage of dynamic elements to place
     * @return true if the pathway has been placed, false otherwise
     */
    public static boolean placeSinglePathwayOnMap(MapPoint mapPoint, int perDynamicElt) {
        if (mapPoint.isAvailable()) {
            int randomPercent = Math.abs(new Random().nextInt(100)); // randomly choose a single element.
            if (randomPercent < perDynamicElt) { // animated elements.
                Tuple3 dynamicElt = ImagesLoader.getRandomSingleDynamicPathway();
                mapPoint.setImages((Image[]) dynamicElt.getFirst(), (Integer) dynamicElt.getSecond());
                mapPoint.setRefreshTime((Integer) dynamicElt.getThird());
            } else { // static elements.
                mapPoint.setImage(ImagesLoader.getRandomSingleStaticPathway());
            }
            mapPoint.setMutable(false);
            mapPoint.setPathway(true);
            mapPoint.setAvailable(false);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Try to place a single mutable on map.
     * If the case is available, place the mutable and return true, otherwise return false.
     *
     * @param mapPoint the MapPoint to place the mutable
     * @return true if the mutable has been placed, false otherwise
     */
    public static boolean placeSingleMutableOnMap(MapPoint mapPoint) {
        if (mapPoint.isAvailable()) {
            mapPoint.setImage(ImagesLoader.getRandomSingleMutable());
            mapPoint.setMutable(true);
            mapPoint.setPathway(false);
            mapPoint.setAvailable(false);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Try to place a single obstacle on map.
     * If the case is available, place the obstacle and return true, otherwise return false.
     *
     * @param mapPoint the MapPoint to place the obstacle
     * @return true if the obstacle has been placed, false otherwise
     */
    public static boolean placeSingleObstacleOnMap(MapPoint mapPoint) {
        if (mapPoint.isAvailable()) {
            mapPoint.setImage(ImagesLoader.getRandomSingleObstacle());
            mapPoint.setMutable(false);
            mapPoint.setPathway(false);
            mapPoint.setAvailable(false);
            return true;
        } else {
            return false;
        }
    }
}
