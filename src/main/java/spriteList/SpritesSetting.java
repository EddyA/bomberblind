package spriteList;

/**
 * Based on {@link SpritesProperties}, it contains all the properties requiered to create enemies.
 */
public class SpritesSetting {

    private final int nbZora; // the number of zora to place.
    private final int nbRedSpearSoldier; // the number of red spear soldier to place.

    private final int birdsArrivalTimeInterval; // the time to wait before creating a new groups of bird (in ms).

    public SpritesSetting(SpritesProperties spritesProperties) {
        this.nbZora = spritesProperties.getSpritesEnemyzora();
        this.nbRedSpearSoldier = spritesProperties.getSpritesEnemyRedSpearSoldier();
        this.birdsArrivalTimeInterval = spritesProperties.getSpritesBirdsArrivalTimeInterval();
    }

    public int getNbZora() {
        return nbZora;
    }

    public int getNbRedSpearSoldier() {
        return nbRedSpearSoldier;
    }

    public int getBirdsArrivalTimeInterval() {
        return birdsArrivalTimeInterval;
    }
}
