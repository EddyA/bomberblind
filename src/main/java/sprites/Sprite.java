package sprites;

import utils.CurrentTimeSupplier;

import java.awt.*;

/**
 * Abstract class of a sprite.
 */
public abstract class Sprite {
    protected CurrentTimeSupplier currentTimeSupplier = new CurrentTimeSupplier();

    private int xMap; // abscissa on map.
    private int yMap; // ordinate on map.

    private int refreshTime; // refresh time (in ms).
    private long lastRefreshTs; // last refresh timestamp.

    /**
     * Create a settled sprite.
     *
     * @param xMap        the sprite's abscissa
     * @param yMap        the sprite's ordinate
     * @param refreshTime the sprite refreshTime (i.e. fps)
     */
    public Sprite(int xMap, int yMap, int refreshTime) {
        this.xMap = xMap;
        this.yMap = yMap;
        this.refreshTime = refreshTime;
    }

    public Sprite() { }

    /**
     * @return the sprite's abscissa on the map.
     */
    public int getXMap() {
        return xMap;
    }

    /**
     * Set the sprite's abscissa on the map.
     */
    public void setXMap(int xMap) {
        this.xMap = xMap;
    }

    /**
     * @return the sprite's ordinate on the map.
     */
    public int getYMap() {
        return yMap;
    }

    /**
     * Set the sprite's ordinate on the map.
     */
    public void setYMap(int yMap) {
        this.yMap = yMap;
    }

    public int getRefreshTime() {
        return refreshTime;
    }

    public long getLastRefreshTs() {
        return lastRefreshTs;
    }

    public void setLastRefreshTs(long lastRefreshTs) {
        this.lastRefreshTs = lastRefreshTs;
    }

    public void setCurrentTimeSupplier(CurrentTimeSupplier currentTimeSupplier) {
        this.currentTimeSupplier = currentTimeSupplier;
    }

    /**
     * This function is used to handle sprite's speed - in term of image/sec.
     * It computes the elapsed time since the sprite's image has been updated
     * and return true if it should be updated again, false oterhwise.
     *
     * @return true if the sprite should be updated, false oterhwise.
     */
    public boolean isTimeToRefresh() {
        long curTs = currentTimeSupplier.get().toEpochMilli(); // get the current time.
        if (curTs - lastRefreshTs >= refreshTime) { // it is time to refresh.
            lastRefreshTs = curTs;
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return true if the sprite is finished, false otherwise.
     */
    public abstract boolean isFinished();

    /**
     * @return the current sprite image.
     */
    public abstract Image getCurImage();

    /**
     * Update the sprite image.
     */
    public abstract void updateImage();

    /**
     * Paint the sprite.
     *
     * @param g       the graphics context
     * @param xScreen the map's abscissa from which painting
     * @param yScreen the map's ordinate from which painting
     */
    public void paintBuffer(Graphics2D g, int xScreen, int yScreen) {
        if ((getCurImage() != null) && !isFinished()) {
            int xImage = xScreen - getCurImage().getWidth(null) / 2;
            int yImage = yScreen - getCurImage().getHeight(null);
            g.drawImage(getCurImage(), xImage, yImage, null);
        }
    }
}
