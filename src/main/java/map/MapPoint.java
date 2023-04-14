package map;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Random;
import images.ImagesLoader;
import lombok.Getter;
import lombok.Setter;
import sprite.settled.BonusType;
import utils.CurrentTimeSupplier;

/**
 * A point of the map.
 */
public class MapPoint {

    private static final Random R = new Random();
    protected CurrentTimeSupplier currentTimeSupplier = new CurrentTimeSupplier();

    @Getter
    private final int rowIdx;
    @Getter
    private final int colIdx;

    private final MapPointStatus mapPointStatus = new MapPointStatus();

    @Getter
    @Setter
    private Image image;

    @Getter
    private Image[] images; // array of images for animation.
    private int nbImages; // number of images of the animation.
    @Setter
    private int curImageIdx; // current image index of the animation.
    @Getter
    @Setter
    private int refreshTime; // refresh time of the animation (in ms).
    @Setter
    private long lastRefreshTs; // last refresh timestamp.

    @Getter
    @Setter
    private boolean isBombing; // is bombed (bomb on case)?
    @Getter
    @Setter
    private int nbFlames; // number of flames on that case (can be multiple because of crossing explosions).

    @Getter
    @Setter
    private BonusType attachedBonus; // attached bonus.
    @Getter
    @Setter
    private boolean isBonusing; // the bonus has been revealed.

    public MapPoint(int rowIdx, int colIdx) {
        this.rowIdx = rowIdx;
        this.colIdx = colIdx;
        this.mapPointStatus.setAvailable(true);
        this.attachedBonus = null;
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

    public void addFlame() {
        this.nbFlames++;
    }

    public void removeFlame() {
        this.nbFlames--;
    }

    public boolean isBurning() {
        return nbFlames > 0;
    }

    public void setImages(Image[] images, int nbImages) {
        this.images = images;
        this.nbImages = nbImages;
        this.curImageIdx = R.nextInt(nbImages);
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
