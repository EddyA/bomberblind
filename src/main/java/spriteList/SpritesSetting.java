package spriteList;

/**
 * Based on {@link SpritesProperties}, it contains all the properties requiered to create enemies.
 */
public class SpritesSetting {

    private final int nbCloakedSkeleton; // the number of cloaked skeleton to place.
    private final int nbMecaAngel; // the number of meca angel to place.
    private final int nbMummy; // the number of mummy to place.

    public SpritesSetting(SpritesProperties spritesProperties) {
        this.nbCloakedSkeleton = spritesProperties.getSpritesEnemyCloakedSkeleton();
        this.nbMecaAngel = spritesProperties.getSpritesEnemyMecaAngel();
        this.nbMummy = spritesProperties.getSpritesEnemyMummy();
    }

    public int getNbCloakedSkeleton() {
        return nbCloakedSkeleton;
    }

    public int getNbMecaAngel() {
        return nbMecaAngel;
    }

    public int getNbMummy() {
        return nbMummy;
    }
}
