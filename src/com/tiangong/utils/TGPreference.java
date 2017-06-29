
package com.tiangong.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class TGPreference {
    private static SharedPreferences preferences;

    public static SharedPreferences getPreferenc(Context activity) {

        preferences = activity
                .getSharedPreferences("com.tiangong.data", Context.MODE_PRIVATE);
        return preferences;
    }
}
