package com.icodeyou.happyexpress.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.icodeyou.happyexpress.R;
import com.icodeyou.happyexpress.util.ConstantUtil;
import com.icodeyou.happyexpress.util.PreferencesUtils;

public abstract class BaseActivity extends AppCompatActivity {

    private Toolbar mBaseToolbar;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarTransparentIfPossible();

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }

    protected void setToolbar() {
        mBaseToolbar = (Toolbar) this.findViewById(R.id.toolbar);
        setSupportActionBar(mBaseToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mBaseToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void showProgressDialog(String message) {
        mProgressDialog.setMessage(message);
        mProgressDialog.show();
    }

    public void dismissProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    /**
     * 设置状态栏一体化
     */
    private void setStatusBarTransparentIfPossible() {
        // 读取是否允许透明的偏好设置
        boolean isAllowTransparent = loadStatusBarConfig();
        if (!isAllowTransparent)
            return;
        // Kitkat 4.4 以上版本才可以
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            // 设置根布局的背景色与ActionBar一致
//            getWindow().getDecorView().setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
//            // 加入透明flag
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }
    }

    /**
     * @return Prefer 中关于状态栏是否允许透明的设置
     */
    private boolean loadStatusBarConfig() {
        return PreferencesUtils.getBoolean(getApplicationContext(), ConstantUtil.PREFER_KEY_STATUSBAR_TRANSPARENT, true);
    }
}
