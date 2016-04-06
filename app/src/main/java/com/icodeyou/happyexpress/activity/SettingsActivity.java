package com.icodeyou.happyexpress.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.icodeyou.happyexpress.R;
import com.icodeyou.happyexpress.util.ConstantUtil;
import com.icodeyou.happyexpress.util.PreferencesUtils;


public class SettingsActivity extends BaseActivity {

    private Toolbar mToolbar;
    private Button mBtnAbout;
    private Button mBtnLogout;

    private Switch mSwChangeMode;
    private Switch mSwStatusBarTransparent;


    private int mItemMode;
    private boolean mIsAllowStatusBarTransparent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initViews();
        configToolbar();
        loadSettings();
        configViews();
    }

    private void configToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 从Preference中读取之前的设置值
     */
    private void loadSettings() {
        // TODO: 15/11/24 先只加一个ItemMode的
        mIsAllowStatusBarTransparent = PreferencesUtils.getBoolean(getApplicationContext(), ConstantUtil.PREFER_KEY_STATUSBAR_TRANSPARENT, true);
    }

    private void configViews() {
        mBtnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(SettingsActivity.this, AboutActivity.class));
            }
        });

        // 设置沉浸式状态栏
        mSwStatusBarTransparent.setChecked(mIsAllowStatusBarTransparent);
        mSwStatusBarTransparent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PreferencesUtils.putBoolean(getApplicationContext(), ConstantUtil.PREFER_KEY_STATUSBAR_TRANSPARENT, isChecked);
            }
        });

        // 注销
//        mBtnLogout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AVUser.logOut();
//                if (AVUser.getCurrentUser() == null) {
//                    SnackBarUtil.showSnackBar(mBtnLogout, "当前账号已退出");
//                } else {
//                    SnackBarUtil.showSnackBar(mBtnLogout, "注销失败，当前用户为: " + AVUser.getCurrentUser());
//                }
//            }
//        });
    }

    private void initViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mBtnAbout = (Button) findViewById(R.id.id_btn_about);
        mSwChangeMode = (Switch) findViewById(R.id.id_sw_switch_mode);
        mSwStatusBarTransparent = (Switch) findViewById(R.id.id_sw_statusbar_transparent);
        mBtnLogout = (Button) findViewById(R.id.id_btn_logout);
    }
}
