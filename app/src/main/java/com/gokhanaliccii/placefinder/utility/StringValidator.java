package com.gokhanaliccii.placefinder.utility;

import java.util.regex.Pattern;

/**
 * Created by gokhan on 04/02/17.
 */

public class StringValidator {

    public static boolean isValidPlaceType(String placeType) {

        if (placeType == null)
            return false;

        if (placeType.length() < 3)
            return false;

        String mCombimned = placeType.replaceAll(" ", "");

        //area has to be only alphabetic character
        if (!Pattern.matches("[a-zA-Z]+", mCombimned))
            return false;

        return true;
    }


}
