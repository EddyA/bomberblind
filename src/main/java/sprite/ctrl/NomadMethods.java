package sprite.ctrl;

import java.util.List;
import lombok.experimental.UtilityClass;
import sprite.Sprite;
import sprite.SpriteType;
import sprite.nomad.Nomad;
import sprite.settled.Bonus;
import static utils.Tools.getCharBottomOrdinate;
import static utils.Tools.getCharLeftAbscissa;
import static utils.Tools.getCharRightAbscissa;
import static utils.Tools.getCharTopOrdinate;

@UtilityClass
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
                    (curSprite.getSpriteType().equals(SpriteType.TYPE_SPRITE_WALKING_ENEMY) || // AND is a walking enemy
                            curSprite.getSpriteType().equals(SpriteType.TYPE_SPRITE_BREAKING_ENEMY))) { // OR is a breaking enemy.

                // the right bound of the provided abstracts is between the left & the right side of the checked one.
                if (((getCharRightAbscissa(xChar) >= getCharLeftAbscissa(curSprite.getXMap()) &&
                        getCharRightAbscissa(xChar) <= getCharRightAbscissa(curSprite.getXMap()) ||

                        // the left bound of the provided abstracts is between the left & the right side of the checked one.
                        getCharLeftAbscissa(xChar) >= getCharLeftAbscissa(curSprite.getXMap()) &&
                                getCharLeftAbscissa(xChar) <= getCharRightAbscissa(curSprite.getXMap()))

                        &&

                        // the top bound of the provided abstracts is between the top & the bottom side of the checked one.
                        (getCharTopOrdinate(yChar) >= getCharTopOrdinate(curSprite.getYMap()) &&
                                getCharTopOrdinate(yChar) <= getCharBottomOrdinate(curSprite.getYMap()) ||

                                // the bottom bound of the provided abstracts is between the top & the bottom side of the checked one.
                                getCharBottomOrdinate(yChar) >= getCharTopOrdinate(curSprite.getYMap()) &&
                                        getCharBottomOrdinate(yChar) <= getCharBottomOrdinate(curSprite.getYMap())))) {
                    return true;
                }
            }
        }
        return false;
    }

    public static Bonus isNomadCrossingBonus(List<Sprite> spriteList, int xChar, int yChar) {
        for (Sprite curSprite : spriteList) {
            if (curSprite.getSpriteType().equals(SpriteType.TYPE_SPRITE_BONUS)) { // it is a bonus.

                // the right bound of the provided nomad is between the left & the right side of the checked one.
                if (((getCharRightAbscissa(xChar) >= getCharLeftAbscissa(curSprite.getXMap()) &&
                        getCharRightAbscissa(xChar) <= getCharRightAbscissa(curSprite.getXMap()) ||

                        // the left bound of the provided nomad is between the left & the right side of the checked one.
                        getCharLeftAbscissa(xChar) >= getCharLeftAbscissa(curSprite.getXMap()) &&
                                getCharLeftAbscissa(xChar) <= getCharRightAbscissa(curSprite.getXMap()))

                        &&

                        // the top bound of the provided nomad is between the top & the bottom side of the checked one.
                        (getCharTopOrdinate(yChar) >= getCharTopOrdinate(curSprite.getYMap()) &&
                                getCharTopOrdinate(yChar) <= getCharBottomOrdinate(curSprite.getYMap()) ||

                                // the bottom bound of the provided nomad is between the top & the bottom side of the checked one.
                                getCharBottomOrdinate(yChar) >= getCharTopOrdinate(curSprite.getYMap()) &&
                                        getCharBottomOrdinate(yChar) <= getCharBottomOrdinate(curSprite.getYMap())))) {
                    return (Bonus) curSprite;
                }
            }
        }
        return null;
    }
}
