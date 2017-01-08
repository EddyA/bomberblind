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
    private SpriteType spriteType; // type of sprite.

    protected Image[] images; // array of images of the sprite.
    protected int nbImages; // number of images of the sprite.

    protected int curImageIdx; // current image index of the sprite.
    protected Image curImage; // current image of the sprite.

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
    public Sprite(int xMap,
                  int yMap,
                  SpriteType spriteType,
                  int refreshTime) {
        this.xMap = xMap;
        this.yMap = yMap;
        this.spriteType = spriteType;
        this.refreshTime = refreshTime;
    }

    public void setCurrentTimeSupplier(CurrentTimeSupplier currentTimeSupplier) {
        this.currentTimeSupplier = currentTimeSupplier;
    }

    public int getxMap() {
        return xMap;
    }

    public void setxMap(int xMap) {
        this.xMap = xMap;
    }

    public int getyMap() {
        return yMap;
    }

    public void setyMap(int yMap) {
        this.yMap = yMap;
    }

    public SpriteType getSpriteType() {
        return spriteType;
    }

    public void setSpriteType(SpriteType spriteType) {
        this.spriteType = spriteType;
    }

    public Image[] getImages() {
        return images;
    }

    public void setImages(Image[] images) {
        this.images = images;
    }

    public int getNbImages() {
        return nbImages;
    }

    public void setNbImages(int nbImages) {
        this.nbImages = nbImages;
    }

    public int getCurImageIdx() {
        return curImageIdx;
    }

    public void setCurImageIdx(int curImageIdx) {
        this.curImageIdx = curImageIdx;
    }

    public Image getCurImage() {
        return curImage;
    }

    public void setCurImage(Image curImage) {
        this.curImage = curImage;
    }

    public int getRefreshTime() {
        return refreshTime;
    }

    public void setLastRefreshTs(long lastRefreshTs) {
        this.lastRefreshTs = lastRefreshTs;
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
     * Update the sprite image.
     */
    public abstract void updateImage();

    /**
     * @return true if the sprite is finished, false otherwise.
     */
    public abstract boolean isFinished();

    /**
     * Paint the sprite.
     *
     * @param g       the graphics context
     * @param xScreen the map's abscissa from which painting
     * @param yScreen the map's ordinate from which painting
     */
    public void paintBuffer(Graphics2D g, int xScreen, int yScreen) {
        if (curImage != null) { // happens when the bomber is invincible.
            int xImage = xScreen - curImage.getWidth(null) / 2;
            int yImage = yScreen - curImage.getHeight(null);
            g.drawImage(curImage, xImage, yImage, null);
        }
    }
}
