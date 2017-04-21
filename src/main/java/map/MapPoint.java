package map;

import images.ImagesLoader;
import sprite.settled.BonusType;
import utils.CurrentTimeSupplier;

import java.awt.*;
import java.util.Random;


/**
 * A point of the map.
 */
public class MapPoint {
    protected CurrentTimeSupplier currentTimeSupplier = new CurrentTimeSupplier();

    private final int rowIdx;
    private final int colIdx;

    private final MapPointStatus mapPointStatus = new MapPointStatus();

    private Image image;

    private Image[] images; // array of images for animation.
    private int nbImages; // number of images of the animation.
    private int curImageIdx; // current image index of the animation.
    private int refreshTime; // refresh time of the animation (in ms).
    private long lastRefreshTs; // last refresh timestamp.

    private boolean isBombing; // is bombed (bomb on case)?
    private int nbFlames; // number of flames on that case (can be multiple because of crossing explosions).

    private BonusType attachedBonus; // attached bonus.
    private boolean isBonusing; // the bonus has been revealed.

    public MapPoint(int rowIdx, int colIdx) {
        this.rowIdx = rowIdx;
        this.colIdx = colIdx;
        this.mapPointStatus.setAvailable(true);
        this.attachedBonus = null;
    }

    public int getRowIdx() {
        return rowIdx;
    }

    public int getColIdx() {
        return colIdx;
    }

    public boolean isAvailable() {
        return mapPointStatus.isAvailable();
    }

    public void setAvailable(boolean available) {
        mapPointStatus.setAvailable(available);
    }

    public boolean isPathway() {
        return mapPointStatus.isPathway();
    }

    public void setPathway(boolean pathway) {
        mapPointStatus.setPathway(pathway);
    }

    public boolean isMutable() {
        return mapPointStatus.isMutable();
    }

    public void setMutable(boolean mutable) {
        mapPointStatus.setMutable(mutable);
    }

    public boolean isEntrance() {
        return mapPointStatus.isEntrance();
    }

    public void setEntrance(boolean entrance) {
        mapPointStatus.setEntrance(entrance);
    }

    public boolean isExit() {
        return mapPointStatus.isExit();
    }

    public void setExit(boolean exit) {
        mapPointStatus.setExit(exit);
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Image[] getImages() {
        return images;
    }

    public void setCurImageIdx(int curImageIdx) {
        this.curImageIdx = curImageIdx;
    }

    public int getRefreshTime() {
        return refreshTime;
    }

    public void setRefreshTime(int refreshTime) {
        this.refreshTime = refreshTime;
    }

    public void setLastRefreshTs(long lastRefreshTs) {
        this.lastRefreshTs = lastRefreshTs;
    }

    public boolean isBombing() {
        return isBombing;
    }

    public void setBombing(boolean bombing) {
        isBombing = bombing;
    }

    public void setNbFlames(int nbFlames) {
        this.nbFlames = nbFlames;
    }

    public void addFlame() {
        this.nbFlames++;
    }

    public void removeFlame() {
        this.nbFlames--;
    }

    public boolean isBurning() {
        return nbFlames > 0;
    }

    public BonusType getAttachedBonus() {
        return attachedBonus;
    }

    public void setAttachedBonus(BonusType attachedBonus) {
        this.attachedBonus = attachedBonus;
    }

    public boolean isBonusing() {
        return isBonusing;
    }

    public void setBonusing(boolean bonusing) {
        isBonusing = bonusing;
    }

    public void setImages(Image[] images, int nbImages) {
        this.images = images;
        this.nbImages = nbImages;
        this.curImageIdx = new Random().nextInt(nbImages);
    }

    /**
     * Set the image to burned.
     */
    public void setImageAsBurned() {
        image = ImagesLoader.imagesMatrix[ImagesLoader.singleBoomMatrixRowIdx][0]; // update image.
    }

    /**
     * Rest the point.
     */
    public void reset() {
        mapPointStatus.init();
        image = null;
        images = null;
        nbImages = 0;
        curImageIdx = 0;
        refreshTime = 0;
        lastRefreshTs = 0;
        isBombing = false;
        nbFlames = 0;
        attachedBonus = null;
        isBonusing = false;
    }

    /**
     * Update the image.
     *
     * @return the image to paint.
     */
    Image updateImage() {
        Image imageToPaint;
        if (image != null) {
            imageToPaint = image;
        } else {
            long curTs = currentTimeSupplier.get().toEpochMilli(); // get the current time.
            if (curTs - lastRefreshTs > refreshTime) { // if it is time to refresh.
                lastRefreshTs = curTs;
                if (++curImageIdx == nbImages) curImageIdx = 0; // update the image to display.
            }
            imageToPaint = images[curImageIdx];
        }
        return imageToPaint;
    }

    /**
     * Paint the image.
     *
     * @param g       the graphics context
     * @param xScreen the abscissa on screen
     * @param yScreen the ordinate on screen
     */
    void paintBuffer(Graphics2D g, int xScreen, int yScreen) {
        g.drawImage(updateImage(), xScreen, yScreen, null);
    }
}