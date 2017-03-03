package sprite.settled;

import sprite.Sprite;
import sprite.SpriteType;

import java.awt.*;

import static sprite.settled.Bonus.Status.STATUS_ALIVE;
import static sprite.settled.Bonus.Status.STATUS_ENDED;

public abstract class Bonus extends Sprite {

    public final static int REFRESH_TIME = 100;

    /**
     * enum the different available status of a bonus.
     */
    public enum Status {
        STATUS_ALIVE, STATUS_ENDED
    }

    private Status status = STATUS_ALIVE; // current status of the sprite.

    /**
     * Create a bonus sprite.
     *
     * @param rowIdx     the map row index of the sprite
     * @param colIdx     the map col index of the sprite
     * @param spriteType the sprite's type
     * @param images     the sprite's array of images
     * @param nbImages   the number of images composing the sprite
     */
    public Bonus(int rowIdx,
                 int colIdx,
                 SpriteType spriteType,
                 Image[] images,
                 int nbImages) {
        super(rowIdx,
                colIdx,
                spriteType,
                REFRESH_TIME);
        this.images = images;
        this.nbImages = nbImages;
    }

    @Override
    public void updateImage() {
        if (isTimeToRefresh()) { // it is time to refresh the sprite image.
            if (++curImageIdx == nbImages) {
                curImageIdx = 0;
            }
            curImage = images[curImageIdx];
        }
    }

    @Override
    public boolean isFinished() {
        return status.equals(STATUS_ENDED);
    }
}
