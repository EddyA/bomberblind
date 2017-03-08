package sprite.settled;

import images.ImagesLoader;
import sprite.SpriteType;

public class BonusHeart extends Bonus {

    /**
     * Create a bonus heart.
     *
     * @param rowIdx the map row index of the sprite
     * @param colIdx the map col index of the sprite
     */
    public BonusHeart(int rowIdx,
                      int colIdx) {
        super(rowIdx,
                colIdx,
                BonusType.TYPE_BONUS_HEART,
                ImagesLoader.imagesMatrix[ImagesLoader.bonusHeartMatrixRowIdx],
                ImagesLoader.NB_BONUS_HEART_FRAME);
    }
}
