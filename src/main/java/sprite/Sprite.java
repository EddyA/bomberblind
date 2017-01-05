package sprite;

import java.awt.Graphics2D;
import java.awt.Image;

import utils.CurrentTimeSupplier;

/**
 * Abstract class of a sprite.
 */
public abstract class Sprite {
    protected CurrentTimeSupplier currentTimeSupplier = new CurrentTimeSupplier();

    protected int xMap; // abscissa on map.
    protected int yMap; // ordinate on map.
    private final SpriteType spriteType; // type of sprite.

    private final int refreshTime; // refresh time (in ms).
    protected long lastRefreshTs; // last refresh timestamp.

    /**
     * Create a settled sprite.
     *
     * @param xMap        the sprite's abscissa
     * @param yMap        the sprite's ordinate
     * @param spriteType  the sprite's type
     * @param refreshTime the sprite refreshTime (i.e. fps)
     */
    protected Sprite(int xMap, int yMap, SpriteType spriteType, int refreshTime) {
        this.xMap = xMap;
        this.yMap = yMap;
        this.spriteType = spriteType;
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
        if (currentTimeSupplier.get().toEpochMilli() - lastRefreshTs >= refreshTime) {
            lastRefreshTs = currentTimeSupplier.get().toEpochMilli(); // get the current time.
            return true;
        }
        return false;
    }

    /**
     * @return true if the sprite is ended, false otherwise.
     */
    protected abstract boolean isFinished();

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
