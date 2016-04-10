package com.icodeyou.library.util;

import android.app.AlertDialog;
import android.content.Context;

/**
 * Created by huan on 16/4/10.
 */
public class AlertDialogUtil {

    public static void showAlertDialogOK(Context context, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle("提示")
                .setMessage(message)
                .setPositiveButton("好", null);
        builder.show();
    }

}
