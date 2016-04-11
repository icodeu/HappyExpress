package com.icodeyou.happyexpress.activity.sendexpress;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.icodeyou.happyexpress.R;
import com.icodeyou.happyexpress.activity.BaseActivity;
import com.icodeyou.happyexpress.view.TimePassageView;
import com.icodeyou.library.util.CollectionUtil;
import com.icodeyou.library.util.bean.ExpressInfo;
import com.icodeyou.library.util.bean.GrabOrder;
import com.icodeyou.library.util.bean.User;
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

    //Header - 下单、接单、支付
    // 下单
    @Bind(R.id.id_iv_step1)
    ImageView mIvStep1;
    @Bind(R.id.id_tv_step1)
    TextView mTvStep1;
    @Bind(R.id.id_iv_step1_progress)
    ImageView mIvStep1Progress;
    // 接单
    @Bind(R.id.id_iv_step2)
    ImageView mIvStep2;
    @Bind(R.id.id_tv_step2)
    TextView mTvStep2;
    @Bind(R.id.id_iv_step2_progress)
    ImageView mIvStep2Progress;
    // 支付
    @Bind(R.id.id_iv_step3)
    ImageView mIvStep3;
    @Bind(R.id.id_tv_step3)
    TextView mTvStep3;

    @Bind(R.id.id_rl_publish_order)
    RelativeLayout mRlPublishOrder;
    @Bind(R.id.id_rl_grabed)
    LinearLayout mRlGrabedSuccess;
    @Bind(R.id.id_tv_take_code)
    TextView mTvTakeCode;
    @Bind(R.id.id_btn_pay)
    Button mBtnPay;

    @Bind(R.id.post_man_map)
    MapView mMapView;

    private AMap mAMap;

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

        mMapView.onCreate(savedInstanceState);// 此方法必须重写
        setMapView();
    }

    private void setMapView() {
        if (mAMap == null) {
            mAMap = mMapView.getMap();
        }
    }

    /**
     * 根据动画按钮状态，调用函数animateCamera或moveCamera来改变可视区域
     */
    private void changeCamera(CameraUpdate update, AMap.CancelableCallback callback) {
        boolean animated = true;
        if (animated) {
            mAMap.animateCamera(update, 1000, callback);
        } else {
            mAMap.moveCamera(update);
        }
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
                            // setview 有人抢单了
                            orderHasBeenGrabed(grabOrders.get(0));
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

    /**
     * 被抢单后执行的操作
     */
    private void orderHasBeenGrabed(final GrabOrder grabOrder) {
        changeToStep2();
        mRlPublishOrder.setVisibility(View.GONE);
        mRlGrabedSuccess.setVisibility(View.VISIBLE);

        // 更新取件码和支付金额
        mTvTakeCode.setText("取件码: " + grabOrder.getTakeCode());
        mBtnPay.setText("支付: " + grabOrder.getExpressInfo().getMoney());


        // 实时在地图上更新快递员的位置
        mPollTimer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                RequestModel.queryCourierUserInfo(PublishGrabOrderActivity.this, grabOrder, new RequestCallback<User>() {
                    @Override
                    public void onSuccess(User user) {
                        // 快递员的经纬度坐标
                        LatLng postMan = new LatLng(user.getLatitude(), user.getLongtitude());
                        // 往地图上添加一个marker
                        mAMap.addMarker(new MarkerOptions().position(postMan).icon(
                                BitmapDescriptorFactory
                                        .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                        // 地图中心移动到指定坐标
                        changeCamera(
                                CameraUpdateFactory.newCameraPosition(new CameraPosition(
                                        postMan, 18, 0, 30)), null);
                        Log.d(TAG, "更新快递员坐标位置");
                    }
                    @Override
                    public void onFail(User user) {
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
        changeToStep1();

        mBtnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnPayOnClick();
            }
        });
    }

    /**
     * 支付
     */
    private void btnPayOnClick() {
        Log.d(TAG, "btnPayOnClick");

    }

    /**
     * 正在下单状态
     */
    private void changeToStep1() {
        mIvStep1.setImageResource(R.drawable.postman_take_order_step_1_done);
        mTvStep1.setTextColor(getResources().getColor(R.color.spiro_disco));
        mIvStep1Progress.setImageResource(R.drawable.postman_take_order_step_status_1);

        mIvStep2.setImageResource(R.drawable.postman_take_order_step_2_doing);
        mTvStep2.setTextColor(getResources().getColor(R.color.mountain_mist));
        mIvStep2Progress.setImageResource(R.drawable.postman_take_order_step_status_0);

        mIvStep3.setImageResource(R.drawable.postman_take_order_step_3_doing);
        mTvStep3.setTextColor(getResources().getColor(R.color.mountain_mist));
    }

    /**
     * 已接单状态
     */
    private void changeToStep2() {
        mIvStep1.setImageResource(R.drawable.postman_take_order_step_1_done);
        mTvStep1.setTextColor(getResources().getColor(R.color.spiro_disco));
        mIvStep1Progress.setImageResource(R.drawable.postman_take_order_step_status_3);

        mIvStep2.setImageResource(R.drawable.postman_take_order_step_2_done);
        mTvStep2.setTextColor(getResources().getColor(R.color.spiro_disco));
        mIvStep2Progress.setImageResource(R.drawable.postman_take_order_step_status_1);

        mIvStep3.setImageResource(R.drawable.postman_take_order_step_3_doing);
        mTvStep3.setTextColor(getResources().getColor(R.color.mountain_mist));

        mBaseToolbar.setTitle("已抢单，请支付");
    }

    /**
     * 已支付状态
     */
    private void changeToStep3() {
        mIvStep1.setImageResource(R.drawable.postman_take_order_step_1_done);
        mTvStep1.setTextColor(getResources().getColor(R.color.spiro_disco));
        mIvStep1Progress.setImageResource(R.drawable.postman_take_order_step_status_3);

        mIvStep2.setImageResource(R.drawable.postman_take_order_step_2_done);
        mTvStep2.setTextColor(getResources().getColor(R.color.spiro_disco));
        mIvStep2Progress.setImageResource(R.drawable.postman_take_order_step_status_3);

        mIvStep3.setImageResource(R.drawable.postman_take_order_step_3_done);
        mTvStep3.setTextColor(getResources().getColor(R.color.spiro_disco));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPollTimer != null) {
            mPollTimer.cancel();
        }
        mMapView.onDestroy();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }
}
