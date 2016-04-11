package com.icodeyou.happyexpress.activity.sendexpress;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.icodeyou.happyexpress.R;
import com.icodeyou.happyexpress.activity.BaseActivity;
import com.icodeyou.happyexpress.model.ActivityModel;

import c.b.BP;
import c.b.PListener;

public class SendExpressActivity extends BaseActivity {
    private static final String TAG = "SendExpressActivity";

    private Toolbar mToolbar;
    private LinearLayout mLlGrabOrder;
    private LinearLayout mLlComeDoorOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_express);

        initViews();
        setToolbar();
        configViews();
    }

    private void configViews() {
        mLlGrabOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityModel.goToGrabOrderActivity(SendExpressActivity.this);
            }
        });

        mLlComeDoorOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ActivityModel.goToComeDoorOrderActivity(SendExpressActivity.this);
            BP.pay(SendExpressActivity.this, "商品名称", "商品描述", 0.02, true, new PListener() {
                @Override
                public void orderId(String s) {
                    Log.d(TAG, "pay orderId = " + s);
                }

                @Override
                public void succeed() {
                    Log.d(TAG, "pay success");
                }

                @Override
                public void fail(int i, String s) {
                    Log.d(TAG, "pay fail i = " + i + " msg = " + s);
                }

                @Override
                public void unknow() {
                    Log.d(TAG, "pay unknow");
                }
            });
            }
        });
    }

    private void initViews() {
        mLlGrabOrder = (LinearLayout) findViewById(R.id.id_ll_grab_order);
        mLlComeDoorOrder = (LinearLayout) findViewById(R.id.id_ll_come_door_order);
    }
}
