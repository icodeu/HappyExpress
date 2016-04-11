package com.icodeyou.happyexpress.activity.sendexpress;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.icodeyou.happyexpress.R;
import com.icodeyou.happyexpress.activity.BaseActivity;
import com.icodeyou.happyexpress.model.ActivityModel;

public class SendExpressActivity extends BaseActivity {

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
        mLlComeDoorOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityModel.goToComeDoorOrderActivity(SendExpressActivity.this);
            }
        });
    }

    private void initViews() {
        mLlGrabOrder = (LinearLayout) findViewById(R.id.id_ll_grab_order);
        mLlComeDoorOrder = (LinearLayout) findViewById(R.id.id_ll_come_door_order);
    }
}
