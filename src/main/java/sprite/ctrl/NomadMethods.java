package sprite.ctrl;

import sprite.Sprite;
import sprite.SpriteType;
import sprite.nomad.Nomad;
import sprite.settled.Bonus;

import java.util.List;

import static utils.Tools.*;

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
    public static boolean isNomadCrossingEnemy(List<Sprite> spriteList, int xChar, int yChar, Nomad nomad) {
        for (Sprite curSprite : spriteList) {
            if (curSprite != nomad && // the checked nomad is not the provided one
                    (curSprite.getSpriteType().equals(SpriteType.TYPE_WALKING_ENEMY) || // AND is a walking enemy
                            curSprite.getSpriteType().equals(SpriteType.TYPE_BREAKING_ENEMY))) { // OR is a breaking enemy.

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
                    return true;
                }
            }
        }
        return false;
    }

    public static Bonus isNomadCrossingBonus(List<Sprite> spriteList, int xChar, int yChar) {
        for (Sprite curSprite : spriteList) {
            if (curSprite.getSpriteType().equals(SpriteType.TYPE_BONUS)) { // it is a bonus.

                // the right bound of the provided nomad is between the left & the right side of the checked one.
                if (((getCharRightAbscissa(xChar) >= getCharLeftAbscissa(curSprite.getxMap()) &&
                        getCharRightAbscissa(xChar) <= getCharRightAbscissa(curSprite.getxMap()) ||

                        // the left bound of the provided nomad is between the left & the right side of the checked one.
                        getCharLeftAbscissa(xChar) >= getCharLeftAbscissa(curSprite.getxMap()) &&
                                getCharLeftAbscissa(xChar) <= getCharRightAbscissa(curSprite.getxMap()))

                        &&

                        // the top bound of the provided nomad is between the top & the bottom side of the checked one.
                        (getCharTopOrdinate(yChar) >= getCharTopOrdinate(curSprite.getyMap()) &&
                                getCharTopOrdinate(yChar) <= getCharBottomOrdinate(curSprite.getyMap()) ||

                                // the bottom bound of the provided nomad is between the top & the bottom side of the checked one.
                                getCharBottomOrdinate(yChar) >= getCharTopOrdinate(curSprite.getyMap()) &&
                                        getCharBottomOrdinate(yChar) <= getCharBottomOrdinate(curSprite.getyMap())))) {
                    return (Bonus) curSprite;
                }
            }
        }
        return null;
    }
}
