package sprites.nomad;

import sprites.nomad.abstracts.Nomad;

import java.util.ListIterator;

import static utils.Character.*;

public class NomadCtrl {

    /**
     * Is the nomad is crossing another nomad?
     *
     * @param nomadList the list of nomads
     * @param nomad     the provided nomad
     * @param xChar     the abscissa of the provided nomad
     * @param yChar     the ordinate of the provided nomad
     * @return true if the provided nomad is crossing another one, flase otherwise
     */
    public static boolean isNomadCrossingAnotherNomad(java.util.List<Nomad> nomadList, Nomad nomad, int xChar, int yChar) {
        boolean isCrossing = false;
        for (ListIterator<Nomad> iterator = nomadList.listIterator(); iterator.hasNext(); ) {
            Nomad curNomad = iterator.next();
            if (curNomad != nomad) { // the checked nomad is not the one provided.

                // the right bound of the provided nomad is between the left & the right side of the checked one.
                if (((getCharRightBound(xChar) >= getCharLeftBound(curNomad.getXMap()) &&
                        getCharRightBound(xChar) <= getCharRightBound(curNomad.getXMap()) ||

                        // the left bound of the provided nomad is between the left & the right side of the checked one.
                        getCharLeftBound(xChar) >= getCharLeftBound(curNomad.getXMap()) &&
                                getCharLeftBound(xChar) <= getCharRightBound(curNomad.getXMap())) &&

                        // the top bound of the provided nomad is between the top & the bottom side of the checked one.
                        (getCharTopBound(yChar) >= getCharTopBound(curNomad.getYMap()) &&
                                getCharTopBound(yChar) <= getCharBottomBound(curNomad.getYMap()) ||

                                // the bottom bound of the provided nomad is between the top & the bottom side of the checked one.
                                getCharBottomBound(yChar) >= getCharTopBound(curNomad.getYMap()) &&
                                        getCharBottomBound(yChar) <= getCharBottomBound(curNomad.getYMap())))) {
                    isCrossing = true;
                }
            }
        }
        return isCrossing;
    }
}
