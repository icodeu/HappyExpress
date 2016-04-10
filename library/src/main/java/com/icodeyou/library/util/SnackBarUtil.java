package com.icodeyou.library.util;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by huan on 15/11/23.
 */
public class SnackBarUtil {

    public static void showSnackBar(View view, String showText) {
        final Snackbar snackbar = Snackbar.make(view, showText, Snackbar.LENGTH_SHORT);
        snackbar.setAction("知道了", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }

}
