package com.icodeyou.happyexpress.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.icodeyou.happyexpress.R;

public class LoginActivity extends BaseActivity {

    private EditText mEtEmail, mEtPassword;
    private Button mBtnLogin, mBtnSignup;

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        configToolbar();

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

    private void initViews() {
        mEtEmail = (EditText) findViewById(R.id.id_et_email);
        mEtPassword = (EditText) findViewById(R.id.id_et_password);
        mBtnLogin = (Button) findViewById(R.id.id_btn_login);
        mBtnSignup = (Button) findViewById(R.id.id_btn_signup);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
    }

}

