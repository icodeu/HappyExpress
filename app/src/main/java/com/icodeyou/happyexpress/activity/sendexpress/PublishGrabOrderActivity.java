package com.icodeyou.happyexpress.activity.sendexpress;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.icodeyou.happyexpress.R;
import com.icodeyou.happyexpress.activity.BaseActivity;
import com.icodeyou.happyexpress.view.TimePassageView;

public class PublishGrabOrderActivity extends BaseActivity {

    private TimePassageView mTimePassageView;
    private Button mBtnCancelOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_grab_order);

        setToolbar();
        initView();
        mTimePassageView.setAttachActivity(this);
        mTimePassageView.start();
    }

    private void initView() {
        mTimePassageView = (TimePassageView) findViewById(R.id.id_timePassageView);
        mBtnCancelOrder = (Button) findViewById(R.id.id_btn_cancel_grab_order);
        mBtnCancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PublishGrabOrderActivity.this)
                        .setTitle("提示")
                        .setMessage("确定取消订单吗")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                PublishGrabOrderActivity.this.finish();
                            }
                        })
                        .setNegativeButton("否", null);
                builder.show();
            }
        });
    }

}
