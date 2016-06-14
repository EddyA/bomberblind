package images;

import java.awt.*;

/**
 * Attach a collection of images to a BbMan to manage sprites.
 */
public class BbManSprites {

    public final Image[] deathImages;
    public final int nbDeathFrame;
    public final Image[] waitImages;
    public final int nbWaitFrame;
    public final Image[] walkBackImages;
    public final Image[] walkFrontImages;
    public final Image[] walkLeftImages;
    public final Image[] walkRightImages;
    public final int nbWalkFrame;
    public final Image[] winImages;
    public final int nbWinFrame;

    public BbManSprites(Image[] deathImages,
                        int nbDeathFrame,
                        Image[] waitImages,
                        int nbWaitFrame,
                        Image[] walkBackImages,
                        Image[] walkFrontImages,
                        Image[] walkLeftImages,
                        Image[] walkRightImages,
                        int nbWalkFrame,
                        Image[] winImages,
                        int nbWinFrame) {
        this.deathImages = deathImages;
        this.nbDeathFrame = nbDeathFrame;
        this.waitImages = waitImages;
        this.nbWaitFrame = nbWaitFrame;
        this.walkBackImages = walkBackImages;
        this.walkFrontImages = walkFrontImages;
        this.walkLeftImages = walkLeftImages;
        this.walkRightImages = walkRightImages;
        this.nbWalkFrame = nbWalkFrame;
        this.winImages = winImages;
        this.nbWinFrame = nbWinFrame;
    }
}
