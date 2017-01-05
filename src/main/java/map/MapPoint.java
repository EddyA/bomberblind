package map;

import images.ImagesLoader;
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

    private boolean isAvailable; // is available (empty case)?
    private boolean isPathway; // is a pathway?
    private boolean isMutable; // is a mutable?

    private Image image;

    private Image[] images; // array of images for animation.
    private int nbImages; // number of images of the animation.
    int curImageIdx; // current image index of the animation.
    int refreshTime; // refresh time of the animation (in ms).
    long lastRefreshTs; // last refresh timestamp.

    private boolean isBombing; // is bombed (bomb on case)?
    private int nbFlames; // number of flames on that case (can be multiple because of crossing explosions).

    public MapPoint(int rowIdx, int colIdx) {
        this.rowIdx = rowIdx;
        this.colIdx = colIdx;
        this.isAvailable = true;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public void setPathway(boolean isPathway) {
        this.isPathway = isPathway;
    }

    public void setMutable(boolean isMutable) {
        this.isMutable = isMutable;
    }

    public void setBombing(boolean isBombing) {
        this.isBombing = isBombing;
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

    public void setImage(Image image) {
        this.image = image;
    }

    public void setImages(Image[] images, int nbImages) {
        this.images = images;
        this.nbImages = nbImages;
        this.curImageIdx = new Random().nextInt(nbImages);
    }

    public void setRefreshTime(int refreshTime) {
        this.refreshTime = refreshTime;
    }

    public int getRowIdx() {
        return rowIdx;
    }

    public int getColIdx() {
        return colIdx;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public boolean isPathway() {
        return isPathway;
    }

    public boolean isMutable() {
        return isMutable;
    }

    public boolean isBombing() {
        return isBombing;
    }

    public boolean isBurning() {
        return nbFlames > 0;
    }

    public Image getImage() {
        return image;
    }

    public Image[] getImages() {
        return images;
    }

    /**
     * Set the image to burned.
     */
    public void setImageAsBurned() {
        image = ImagesLoader.imagesMatrix[ImagesLoader.singleBoomMatrixRowIdx][0]; // update image.
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
    void paintBuffer(Graphics g, int xScreen, int yScreen) {
        g.drawImage(updateImage(), xScreen, yScreen, null);
    }
}