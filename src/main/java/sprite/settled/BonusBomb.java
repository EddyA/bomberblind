package sprite.settled;

import images.ImagesLoader;

/**
 * A bonus bomb.
 */
public class BonusBomb extends Bonus {

    /**
     * Create a bonus bomb.
     *
     * @param rowIdx the map row index of the sprite
     * @param colIdx the map col index of the sprite
     */
    public BonusBomb(int rowIdx,
                     int colIdx) {
        super(rowIdx,
                colIdx,
                BonusType.TYPE_BONUS_BOMB,
                ImagesLoader.imagesMatrix[ImagesLoader.bonusBombMatrixRowIdx],
                ImagesLoader.NB_BONUS_BOMB_FRAME);
    }
}
