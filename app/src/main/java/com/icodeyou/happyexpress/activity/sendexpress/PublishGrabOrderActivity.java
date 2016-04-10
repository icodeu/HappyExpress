package com.icodeyou.happyexpress.activity.sendexpress;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.icodeyou.happyexpress.R;
import com.icodeyou.happyexpress.activity.BaseActivity;
import com.icodeyou.happyexpress.view.TimePassageView;
import com.icodeyou.library.util.CollectionUtil;
import com.icodeyou.library.util.bean.ExpressInfo;
import com.icodeyou.library.util.bean.GrabOrder;
import com.icodeyou.library.util.model.RequestCallback;
import com.icodeyou.library.util.model.RequestModel;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PublishGrabOrderActivity extends BaseActivity {

    private static final String TAG = "PublishActivity";
    public static final String EXTRA_EXPRESS_INFO = "extra_express_info";

    private TimePassageView mTimePassageView;
    private Button mBtnCancelOrder;

    @Bind(R.id.id_rl_publish_order)
    RelativeLayout mRlPublishOrder;
    @Bind(R.id.id_rl_grabed)
    RelativeLayout mRlGrabedSuccess;
    @Bind(R.id.id_tv_success)
    TextView mTvSuccess;

    private ExpressInfo mExpressInfo;

    private static final long POLL_PERIOD_TIME = 2000;
    private Timer mPollTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_grab_order);
        ButterKnife.bind(this);

        setToolbar();
        initView();
        mTimePassageView.setAttachActivity(this);
        mTimePassageView.start();

        getDataPassed();

        startPollGrabOrderState();
    }

    private void startPollGrabOrderState() {
        mPollTimer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                RequestModel.getGrabOrderInfoByExpressInfo(PublishGrabOrderActivity.this, mExpressInfo, new RequestCallback<List<GrabOrder>>() {
                    @Override
                    public void onSuccess(List<GrabOrder> grabOrders) {
                        Log.d(TAG, "pool OnSuccess");
                        if (!CollectionUtil.isEmpty(grabOrders)) {
                            Log.d(TAG, "有人抢单啦 " + grabOrders.toString());
                            mPollTimer.cancel();
                            // setview
                            mRlPublishOrder.setVisibility(View.GONE);
                            mRlGrabedSuccess.setVisibility(View.VISIBLE);
                            mTvSuccess.setText(grabOrders.get(0).getTakeCode() + "  " + grabOrders.get(0).toString());
                        }
                    }
                    @Override
                    public void onFail(List<GrabOrder> grabOrders) {
                    }
                });
            }
        };
        mPollTimer.schedule(timerTask, 0, POLL_PERIOD_TIME);
    }

    private void getDataPassed() {
        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_EXPRESS_INFO)) {
            mExpressInfo = (ExpressInfo) intent.getSerializableExtra(EXTRA_EXPRESS_INFO);
            Log.d(TAG, "getDataPassed expressInfo = " + mExpressInfo);
        }
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

        mRlPublishOrder.setVisibility(View.VISIBLE);
        mRlGrabedSuccess.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPollTimer != null) {
            mPollTimer.cancel();
        }
    }
}
