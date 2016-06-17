package bomb;

import java.util.ArrayList;
import java.util.List;

import map.RMap;
import map.RMapPoint;

public class Bomb {

    /**
     * enum the different available status of the bomb.
     */
    public enum STATUS {
        STATUS_ALIVE,
        STATUS_EXPLOSING
    }

    private STATUS status; // status ().
    private RMapPoint rMapPoint; // position on the map.
    private List linkedCases; // list of cases linked to that bomb (for blast impact).
    private int bombTimer; // time before a bomb should explose (in ms).


    private int curImageIdx; // current image index of the animation.
    private int refreshTime; // refresh time of the animation (in ms).
    private long lastRefreshTs; // last refresh timestamp.

    public Bomb(RMapPoint rMapPoint, RMap rMap, int bombTimer, int flameSize) {

        this.status = STATUS.STATUS_ALIVE;
        this.rMapPoint = rMapPoint;
        this.bombTimer = bombTimer;

        this.refreshTime = 100;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }

    public void setLinkedCases(int flameSize, RMap rMap) {
        linkedCases = new ArrayList<>();
        linkedCases.add(rMapPoint); // add the current case.
        for (int i = 0; i < flameSize; i++) {
            linkedCases.add(rMap.myMap[rMapPoint.getRowIdx()][rMapPoint.getColIdx() - i]);
            linkedCases.add(rMap.myMap[rMapPoint.getRowIdx()][rMapPoint.getColIdx() + i]);
            linkedCases.add(rMap.myMap[rMapPoint.getRowIdx()][rMapPoint.getColIdx() - i]);
            linkedCases.add(rMap.myMap[rMapPoint.getRowIdx()][rMapPoint.getColIdx() + i]);
        }

    }

    /**
     * Paint the image.
     *
     * @param g the graphics context
     * @param bbManPosition the start point (abscissa and ordinate of the BbMan).
     */
    /*
     * public void paintBuffer(Graphics g, Point bbManPosition) {
     * Image updatedImage = updateImage();
     * int xMap = (int) bbManPosition.getX() - updatedImage.getWidth(null) / 2;
     * int yMap = (int) bbManPosition.getY() - updatedImage.getHeight(null);
     * g.drawImage(updatedImage, xMap, yMap, null);
     * }
     */
}
