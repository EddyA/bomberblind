package sprite;

import exceptions.SpriteActionException;
import lombok.Getter;
import lombok.Setter;
import utils.CurrentTimeSupplier;

import java.awt.*;

/**
 * Abstract class of a sprite.
 */
public abstract class Sprite {

    @Setter
    protected CurrentTimeSupplier currentTimeSupplier = new CurrentTimeSupplier();

    @Getter
    @Setter
    protected int xMap; // abscissa on map.
    @Getter
    @Setter
    protected int yMap; // ordinate on map.
    @Getter
    @Setter
    private SpriteType spriteType; // type of sprite.

    @Getter
    @Setter
    protected Image[] images; // array of images of the sprite.
    @Getter
    @Setter
    protected int nbImages; // number of images of the sprite.

    @Getter
    @Setter
    protected int curImageIdx; // current image index of the sprite.
    @Getter
    @Setter
    protected Image curImage; // current image of the sprite.

    @Getter
    protected int refreshTime; // refresh time (in ms).
    @Setter
    protected long lastRefreshTs; // last refresh timestamp.

    /**
     * Create a settled sprite.
     *
     * @param xMap        the sprite's abscissa
     * @param yMap        the sprite's ordinate
     * @param spriteType  the sprite's type
     * @param refreshTime the sprite refreshTime (i.e. fps)
     */
    protected Sprite(int xMap,
                  int yMap,
                  SpriteType spriteType,
                  int refreshTime) {
        this.xMap = xMap;
        this.yMap = yMap;
        this.spriteType = spriteType;
        this.refreshTime = refreshTime;
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
    public abstract void updateImage() throws SpriteActionException;

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
