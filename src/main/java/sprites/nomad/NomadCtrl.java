package sprites.nomad;

import sprites.nomad.Nomad;

import static utils.Tools.*;

public class NomadCtrl {

    /**
     * Is the nomad is crossing an enemy?
     *
     * @param nomadList the list of nomads
     * @param nomad     the provided nomad (requiered to avoid comparing with itself)
     * @param xChar     the abscissa of the provided nomad
     * @param yChar     the ordinate of the provided nomad
     * @return true if the provided nomad is crossing another one, flase otherwise
     */
    public static boolean isNomadCrossingEnemy(java.util.List<Nomad> nomadList, Nomad nomad, int xChar, int yChar) {
        boolean isCrossing = false;
        for (Nomad curNomad : nomadList) {
            if (curNomad.getClass().getSuperclass().getSimpleName().equals("Enemy") && // it is an enemy
                    curNomad != nomad) { // AND the checked nomad is not the one provided.

                // the right bound of the provided nomad is between the left & the right side of the checked one.
                if (((getCharRightAbscissa(xChar) >= getCharLeftAbscissa(curNomad.getXMap()) &&
                        getCharRightAbscissa(xChar) <= getCharRightAbscissa(curNomad.getXMap()) ||

                        // the left bound of the provided nomad is between the left & the right side of the checked one.
                        getCharLeftAbscissa(xChar) >= getCharLeftAbscissa(curNomad.getXMap()) &&
                                getCharLeftAbscissa(xChar) <= getCharRightAbscissa(curNomad.getXMap())) &&

                        // the top bound of the provided nomad is between the top & the bottom side of the checked one.
                        (getCharTopOrdinate(yChar) >= getCharTopOrdinate(curNomad.getYMap()) &&
                                getCharTopOrdinate(yChar) <= getCharBottomOrdinate(curNomad.getYMap()) ||

                                // the bottom bound of the provided nomad is between the top & the bottom side of the checked one.
                                getCharBottomOrdinate(yChar) >= getCharTopOrdinate(curNomad.getYMap()) &&
                                        getCharBottomOrdinate(yChar) <= getCharBottomOrdinate(curNomad.getYMap())))) {
                    isCrossing = true;
                }
            }
        }
        return isCrossing;
    }
}
