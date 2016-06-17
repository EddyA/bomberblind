package map;

import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;

import images.ImagesLoader;

public class RMapPoint {

    private int rowIdx;
    private int colIdx;

    private boolean isAvailable; // is available (empty case)?
    private boolean isPathway; // is a pathway?
    private boolean isMutable; // is a mutable?

    private Image image;

    private Image[] images; // array of images for animation.
    private int nbImages; // number of images of the animation.
    private int curImageIdx; // current image index of the animation.
    private int refreshTime; // refresh time of the animation (in ms).
    private long lastRefreshTs; // last refresh timestamp.

    public RMapPoint(int rowIdx, int colIdx) {
        this.rowIdx = rowIdx;
        this.colIdx = colIdx;
        this.isAvailable = true;
    }

    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }

    public void setPathway(boolean pathway) {
        this.isPathway = pathway;
    }

    public void setMutable(boolean mutable) {
        this.isMutable = mutable;
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

    /**
     * If the object is a pathway or a mutable, the rMapPoint blowsUp.
     * Else, nothing happen.
     */
    public void blowUp() {
        if (isPathway || isMutable) {
            isPathway = true;
            isMutable = false;
            image = ImagesLoader.imagesMatrix[ImagesLoader.boomMatrixRowIdx][0]; // update image.
        }
    }

    /**
     * Update the image.
     *
     * @return the image to paint.
     */
    private Image updateImage() {
        Image imageToPaint;
        if (image != null) {
            imageToPaint = image;
        } else {
            long curTs = System.currentTimeMillis(); // get the current time.
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
     * @param g the graphics context
     * @param xScreen the abscissa on screen
     * @param yScreen the ordinate on screen
     */
    public void paintBuffer(Graphics g, int xScreen, int yScreen) {
        g.drawImage(updateImage(), xScreen, yScreen, null);
    }
}

