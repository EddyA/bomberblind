package utils;

import com.google.common.primitives.Ints;

public class Tools {

    /**
     * Check if an String can be converted to an Int.
     * 
     * @param value the string to check
     * @return true if the string can be converted to Int, otherwise, return false
     */
    public static boolean isValidInteger(String value) {
        boolean res = true;
        if (value == null || Ints.tryParse(value) == null) {
            res = false;
        }
        return res;
    }
}
