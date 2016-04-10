package com.icodeyou.library.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by huan on 16/4/10.
 */
public class ToastUtil {
    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, int resId) {
        Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
    }
}
