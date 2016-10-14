package sprite.abstracts;

import java.awt.Graphics2D;
import java.awt.Image;

import sprite.SpriteType;
import utils.CurrentTimeSupplier;
import utils.UIDSupplier;

/**
 * Abstract class of a sprite.
 */
public abstract class Sprite {
    protected CurrentTimeSupplier currentTimeSupplier = new CurrentTimeSupplier();
    protected UIDSupplier uidSupplier = new UIDSupplier();

    protected int xMap; // abscissa on map.
    protected int yMap; // ordinate on map.
    protected int uid; // unique id.
    protected SpriteType spriteType; // type of sprite.

    private int refreshTime; // refresh time (in ms).
    protected long lastRefreshTs; // last refresh timestamp.

    /**
     * Create a settled sprite.
     *
     * @param xMap        the sprite's abscissa
     * @param yMap        the sprite's ordinate
     * @param spriteType  the sprite's type
     * @param refreshTime the sprite refreshTime (i.e. fps)
     */
    public Sprite(int xMap, int yMap, SpriteType spriteType, int refreshTime) {
        this.xMap = xMap;
        this.yMap = yMap;
        this.spriteType = spriteType;
        this.uid = uidSupplier.get();
        this.refreshTime = refreshTime;
    }

    public int getXMap() {
        return xMap;
    }

    public void setXMap(int xMap) {
        this.xMap = xMap;
    }

    public int getYMap() {
        return yMap;
    }

    public void setYMap(int yMap) {
        this.yMap = yMap;
    }

    public SpriteType getSpriteType() {
        return spriteType;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getRefreshTime() {
        return refreshTime;
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
        if ((getCurImage() != null) // happens when the bomber is invincible.
                && !isFinished()) {
            int xImage = xScreen - getCurImage().getWidth(null) / 2;
            int yImage = yScreen - getCurImage().getHeight(null);
            g.drawImage(getCurImage(), xImage, yImage, null);
        }
    }
}
