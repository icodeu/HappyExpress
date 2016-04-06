package com.icodeyou.happyexpress.activity.sendexpress;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.icodeyou.happyexpress.R;
import com.icodeyou.happyexpress.activity.BaseActivity;
import com.icodeyou.happyexpress.model.ActivityModel;

public class GrabOrderActivity extends BaseActivity {

    private Button mBtnSendExpress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grab_order);

        setToolbar();

        initView();
    }

    private void initView() {
        mBtnSendExpress = (Button) findViewById(R.id.id_btn_send_express);

        mBtnSendExpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityModel.goToPublishGrabOrderActivity(GrabOrderActivity.this);
            }
        });
    }

}
