package utils;

import org.apache.commons.lang3.StringUtils;
import static images.ImagesLoader.IMAGE_SIZE;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Tools {

    /**
     * @return true if:
     * - the string is not null,
     * - AND the string can be converted to an int
     * false otherwise.
     */
    public static boolean isNotNullAndValidInteger(String value) {
        return StringUtils.isNumeric(value);
    }

    /**
     * @return true if:
     * - the string is null,
     * - OR the string can be converted to an int
     * false otherwise.
     */
    public static boolean isNullOrValidInteger(String value) {
        return value == null || StringUtils.isNumeric(value);
    }

    /*
      -------------------------------------------------------------------
      The following functions allow having the patron of the characters.
      The patron is defined as a square with a radius of "IMAGE" (30px).
      -------------------------------------------------------------------
     */

    /**
     * Compute the top point of the patron.
     *
     * @param yChar the ordinate of the character
     * @return the top point of the patron
     */
    public static int getCharTopOrdinate(int yChar) {
        return yChar - IMAGE_SIZE / 2;
    }

    /**
     * Compute the bottom point of the patron.
     *
     * @param yChar the ordinate of the character
     * @return the bottom point of the patron
     */
    public static int getCharBottomOrdinate(int yChar) {
        return yChar;
    }

    /**
     * Compute the left point of the patron.
     *
     * @param xChar the abscissa of the character
     * @return the left point of the patron
     */
    public static int getCharLeftAbscissa(int xChar) {
        return xChar - IMAGE_SIZE / 2;
    }

    /**
     * Compute the right point of the patron.
     *
     * @param xChar the abscissa of the character
     * @return the right point of the patron
     */
    public static int getCharRightAbscissa(int xChar) {
        return xChar + IMAGE_SIZE / 2 - 1;
    }

    /*
      --------------------------------------------------------------------
      The following functions allow getting the relative top/bottom rowIdx
      and left/right colIdx of a character.
      -------------------------------------------------------------------
     */

    /**
     * Compute the rowIdx of a character.
     *
     * @param yChar the ordinate of the character
     * @return the map rowIdx of the character
     */
    public static int getCharRowIdx(int yChar) {
        return yChar / IMAGE_SIZE;
    }

    /**
     * Compute the colIdx of a character.
     *
     * @param xChar the abscissa of the character
     * @return the colIdx of the character
     */
    public static int getCharColIdx(int xChar) {
        return xChar / IMAGE_SIZE;
    }

    /**
     * Compute the top rowIdx of a character.
     *
     * @param yChar the ordinate of the character
     * @return the top rowIdx of the character
     */
    public static int getCharTopRowIdx(int yChar) {
        return getCharTopOrdinate(yChar) < 0 ? -1 : (yChar - IMAGE_SIZE / 2) / IMAGE_SIZE;
    }

    /**
     * Compute the bottom rowIdx of a character.
     *
     * @param yChar the ordinate of the character
     * @return the bottom rowIdx of the character
     */
    public static int getCharBottomRowIdx(int yChar) {
        return getCharBottomOrdinate(yChar) / IMAGE_SIZE;
    }

    /**
     * Compute the left colIdx of a character.
     *
     * @param xChar the abscissa of the character
     * @return the left colIdx of the character
     */
    public static int getCharLeftColIdx(int xChar) {
        return getCharLeftAbscissa(xChar) < 0 ? -1 : (xChar - IMAGE_SIZE / 2) / IMAGE_SIZE;
    }

    /**
     * Compute the right colIdx of a character.
     *
     * @param xChar the abscissa of the character
     * @return the right colIdx of the character
     */
    public static int getCharRightColIdx(int xChar) {
        return getCharRightAbscissa(xChar) / IMAGE_SIZE;
    }

    /*
      -----------------------------------------------------------------------
      The following functions allow getting the abscissa (resp. ordinate)
      of the case centre (resp. case bottom) given its colIdx (resp. rowIdx).
      -----------------------------------------------------------------------
     */

    /**
     * Compute the abscissa of the case centre given its colIdx.
     *
     * @param colIdx the colIdx of the case
     * @return the abscissa of the case centre given its colIdx
     */
    public static int getCaseCentreAbscissa(int colIdx) {
        return colIdx * IMAGE_SIZE + IMAGE_SIZE / 2;
    }

    /**
     * Compute the ordinate of the case bottom given its rowIdx.
     *
     * @param rowIdx the rowIdx of the case
     * @return the ordinate of the case bottom given its rowIdx
     */
    public static int getCaseBottomOrdinate(int rowIdx) {
        return rowIdx * IMAGE_SIZE + IMAGE_SIZE - 1;
    }
}
