package com.wangyuelin.easybug.utils;

import android.text.TextUtils;

public class ParseUtil {

    /**
     * String转为Long
     * @param l
     * @param defaultLong
     * @return
     */
    public static long parseToLong(String l, long defaultLong) {
        if (TextUtils.isEmpty(l)) {
            return defaultLong;
        }
        try {
            Long result = Long.valueOf(l);
            if (result == null) {
                return defaultLong;
            } else {
                return result;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return defaultLong;
        }

    }
}
