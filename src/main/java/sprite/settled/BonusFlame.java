package sprite.settled;

import images.ImagesLoader;

/**
 * A bonus flame.
 */
public class BonusFlame extends Bonus {

    /**
     * Create a bonus flame.
     *
     * @param rowIdx the map row index of the sprite
     * @param colIdx the map col index of the sprite
     */
    public BonusFlame(int rowIdx,
                      int colIdx) {
        super(rowIdx,
                colIdx,
                BonusType.TYPE_BONUS_FLAME,
                ImagesLoader.imagesMatrix[ImagesLoader.bonusFlameMatrixRowIdx],
                ImagesLoader.NB_BONUS_FLAME_FRAME);
    }
}
