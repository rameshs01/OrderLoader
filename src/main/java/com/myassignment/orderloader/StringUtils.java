package com.myassignment.orderloader;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by rshanmugam on 4/7/18.
 */
class StringUtils {

    public static  boolean isNotEmpty(String str) {

        if(str == null || str.trim().length() == 0) {
            return false;
        }

        return true;
    }

    public static  boolean isEmpty(String str) {
        return !isNotEmpty(str);
    }

    public static String join(List<String> errorMsgs, String seperator) {

        if(errorMsgs ==null || errorMsgs.size() == 0) {
            return null;
        }

        return errorMsgs
                .stream().collect(Collectors.joining(seperator));
    }
}
