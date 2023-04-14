package spritelist;

import lombok.Getter;

/**
 * Based on {@link SpritesProperties}, it contains all the properties requiered to create enemies.
 */
public class SpritesSetting {

    @Getter
    private final int nbZora; // the number of zora to place.
    @Getter
    private final int nbGreenSoldier; // the number of green soldier to place
    @Getter
    private final int nbRedSpearSoldier; // the number of red spear soldier to place.

    @Getter
    private final int birdsArrivalTimeInterval; // the time to wait before creating a new groups of bird (in ms).

    public SpritesSetting(SpritesProperties spritesProperties) {
        this.nbZora = spritesProperties.getSpritesEnemyZora();
        this.nbGreenSoldier = spritesProperties.getSpritesEnemyGreenSoldier();
        this.nbRedSpearSoldier = spritesProperties.getSpritesEnemyRedSpearSoldier();
        this.birdsArrivalTimeInterval = spritesProperties.getSpritesBirdsArrivalTimeInterval();
    }
}
