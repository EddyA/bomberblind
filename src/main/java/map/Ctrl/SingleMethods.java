package map.Ctrl;

import java.awt.Image;
import java.util.Random;

import images.ImagesLoader;
import map.RMapPoint;
import utils.Triple;


public class SingleMethods {

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
            if (randomPercent < perSingleFlowerPathway) { // animated elements.
                Triple dynamicElt = ImagesLoader.getRandomSingleDynamicPathway();
                rMapPoint.setImages((Image[]) dynamicElt.getFirst(), (Integer) dynamicElt.getSecond());
                rMapPoint.setRefreshTime((Integer) dynamicElt.getThird());
            } else { // static elements.
                rMapPoint.setImage(ImagesLoader.getRandomSingleStaticPathway());
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
     * Try to place a single mutable on map.
     * If the case is available, place the mutable and return true, otherwise return false.
     *
     * @param rMapPoint the RMapPoint to place the mutable
     * @return true if the mutable has been placed, false otherwise
     */
    public static boolean placeSingleMutableOnMap(RMapPoint rMapPoint) {
        if (rMapPoint.isAvailable()) {
            rMapPoint.setImage(ImagesLoader.getRandomSingleMutable());
            rMapPoint.setMutable(true);
            rMapPoint.setPathway(false);
            rMapPoint.setAvailable(false);
            return true;
        } else {
            return false;
        }
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
            rMapPoint.setImage(ImagesLoader.getRandomSingleObstacle());
            rMapPoint.setMutable(false);
            rMapPoint.setPathway(false);
            rMapPoint.setAvailable(false);
            return true;
        } else {
            return false;
        }
    }
}
