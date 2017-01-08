package sprite.ctrl;

import static utils.Tools.getCharBottomOrdinate;
import static utils.Tools.getCharLeftAbscissa;
import static utils.Tools.getCharRightAbscissa;
import static utils.Tools.getCharTopOrdinate;

import sprite.SpriteType;
import sprite.Sprite;
import sprite.nomad.Nomad;

public class NomadMethods {

    /**
     * Is a nomad crossing an enemy?
     *
     * @param spriteList the list of sprites (including enemies)
     * @param xChar the nomad's abscissa
     * @param yChar the nomad's ordinate
     * @param nomad the relative nomad identifier (requiered to avoid comparing with itself)
     * @return true if the nomad is crossing an enemy, flase otherwise
     */
    public static boolean isNomadCrossingEnemy(java.util.List<Sprite> spriteList, int xChar, int yChar, Nomad nomad) {
        boolean isCrossing = false;
        for (Sprite curSprite : spriteList) {
            if (curSprite.getSpriteType().equals(SpriteType.WALKING_ENEMY) && // it is an enemy
                    curSprite != nomad) { // AND the checked abstracts is not the one provided.

                // the right bound of the provided abstracts is between the left & the right side of the checked one.
                if (((getCharRightAbscissa(xChar) >= getCharLeftAbscissa(curSprite.getxMap()) &&
                        getCharRightAbscissa(xChar) <= getCharRightAbscissa(curSprite.getxMap()) ||

                        // the left bound of the provided abstracts is between the left & the right side of the checked one.
                        getCharLeftAbscissa(xChar) >= getCharLeftAbscissa(curSprite.getxMap()) &&
                                getCharLeftAbscissa(xChar) <= getCharRightAbscissa(curSprite.getxMap()))

                        &&

                        // the top bound of the provided abstracts is between the top & the bottom side of the checked one.
                        (getCharTopOrdinate(yChar) >= getCharTopOrdinate(curSprite.getyMap()) &&
                                getCharTopOrdinate(yChar) <= getCharBottomOrdinate(curSprite.getyMap()) ||

                                // the bottom bound of the provided abstracts is between the top & the bottom side of the checked one.
                                getCharBottomOrdinate(yChar) >= getCharTopOrdinate(curSprite.getyMap()) &&
                                        getCharBottomOrdinate(yChar) <= getCharBottomOrdinate(curSprite.getyMap())))) {
                    isCrossing = true;
                }
            }
        }
        return isCrossing;
    }
}
