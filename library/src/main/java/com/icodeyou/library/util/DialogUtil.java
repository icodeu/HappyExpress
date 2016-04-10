package com.icodeyou.library.util;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;

/**
 * Created by huan on 16/4/10.
 */
public class DialogUtil {

    public interface IDialogListener {
        void onBtnOKClick();
    }

   public static Dialog showDialog(Context context, int contentViewResId, String title, int btnResId, final IDialogListener listener ) {
       Dialog dialog = new Dialog(context);
       dialog.setContentView(contentViewResId);
       Button btnTakeCode = (Button) dialog.findViewById(btnResId);
       btnTakeCode.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               listener.onBtnOKClick();
           }
       });
       dialog.setTitle("请输入取件码");
       dialog.show();
       return dialog;
   }

}
