package com.icodeyou.happyexpress.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.icodeyou.happyexpress.R;
import com.icodeyou.library.util.SnackBarUtil;

public class SignupActivity extends BaseActivity {

    private static final int DELAY_TIME = 1000;

    private EditText mEtEmail, mEtPassword;
    private Button mBtnLogin, mBtnSignup;

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initViews();

        configToolbar();

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

    private void configViews() {
        onLogin();
        onSignup();
    }

    /**
     * 注册
     */
    private void onSignup() {
        mBtnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEtEmail.getText().toString().trim();
                String password = mEtPassword.getText().toString().trim();
                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                    if (!isValidEmail(email)) {
                        SnackBarUtil.showSnackBar(mBtnLogin, "邮箱地址不合法");
                        return;
                    }
                    if (!isValidPassword(password)) {
                        SnackBarUtil.showSnackBar(mBtnLogin, "密码不合法");
                        return;
                    }
                    // processLogin
                    performSignup(email, password);
                } else {
                    SnackBarUtil.showSnackBar(mBtnLogin, "用户名或密码不能为空");
                }
            }
        });
    }

    /**
     * 执行注册的具体逻辑
     *
     * @param email
     * @param password
     */
    private void performSignup(String email, String password) {
        /*
        AVUser user = new AVUser();
        user.setUsername(email);
        user.setPassword(password);
        user.setEmail(email);

        user.signUpInBackground(new SignUpCallback() {
            public void done(AVException e) {
                if (e == null) {
                    // 注册成功
                    SnackBarUtil.showSnackBar(mBtnSignup, "注册成功");
                    // perform延时跳转
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    }, DELAY_TIME);
                } else {
                    // 注册失败
                    try {
                        JSONObject jsonObject = new JSONObject(e.getMessage());
                        String errorMessage = jsonObject.getString("error");
                        SnackBarUtil.showSnackBar(mBtnSignup, "注册失败: " + errorMessage);
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        */
    }


    /**
     * 登录
     */
    private void onLogin() {
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                finish();
            }
        });
    }

    /**
     * @param email
     * @return 邮箱地址是否合法 暂时先简单写
     */
    private boolean isValidEmail(String email) {
        // TODO: 15/11/23 完善这个方法
        return email.contains("@");
    }

    /**
     * @param password
     * @return 密码地址是否合法 暂时先简单写
     */
    private boolean isValidPassword(String password) {
        // TODO: 15/11/23 完善这个方法
        return true;
    }

    private void initViews() {
        mEtEmail = (EditText) findViewById(R.id.id_et_email);
        mEtPassword = (EditText) findViewById(R.id.id_et_password);
        mBtnLogin = (Button) findViewById(R.id.id_btn_login);
        mBtnSignup = (Button) findViewById(R.id.id_btn_signup);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
    }

}
