package spriteList;

import exceptions.InvalidPropertiesException;

/**
 * Based on {@link SpritesProperties}, it contains all the properties requiered to create enemies.
 */
public class SpritesSetting {

    private int nbCloakedSkeleton; // the number of cloaked skeleton to place.
    private int nbMecaAngel; // the number of meca angel to place.
    private int nbMummy; // the number of mummy to place.

    public SpritesSetting(SpritesProperties spritesProperties) throws InvalidPropertiesException {
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
