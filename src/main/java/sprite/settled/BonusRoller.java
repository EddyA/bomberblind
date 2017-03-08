package sprite.settled;

import images.ImagesLoader;
import sprite.SpriteType;

public class BonusRoller extends Bonus {

    /**
     * Create a bonus roller.
     *
     * @param rowIdx the map row index of the sprite
     * @param colIdx the map col index of the sprite
     */
    public BonusRoller(int rowIdx,
                       int colIdx) {
        super(rowIdx,
                colIdx,
                BonusType.TYPE_BONUS_ROLLER,
                ImagesLoader.imagesMatrix[ImagesLoader.bonusRollerMatrixRowIdx],
                ImagesLoader.NB_BONUS_ROLLER_FRAME);
    }
}
