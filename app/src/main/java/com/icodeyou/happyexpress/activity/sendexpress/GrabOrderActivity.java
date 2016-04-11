package com.icodeyou.happyexpress.activity.sendexpress;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationListener;
import com.icodeyou.happyexpress.R;
import com.icodeyou.happyexpress.activity.BaseActivity;
import com.icodeyou.happyexpress.model.ActivityModel;
import com.icodeyou.library.util.SnackBarUtil;
import com.icodeyou.library.util.StringUtils;
import com.icodeyou.library.util.ViewFinder;
import com.icodeyou.library.util.amap.AMapUtil;
import com.icodeyou.library.util.bean.ExpressInfo;
import com.icodeyou.library.util.bean.User;
import com.icodeyou.library.util.model.RequestCallback;
import com.icodeyou.library.util.model.RequestModel;

import cn.bmob.v3.BmobUser;

public class GrabOrderActivity extends BaseActivity {
    private static final String TAG = "GrabOrderActivity";

    private Button mBtnSendExpress;
    private EditText mEtSendAddress, mEtSendName, mEtSendMobile, mEtRecvAddress, mEtRecvName, mEtRecvMobile;

    // 定位用 mLocationClient
    private AMapLocationClient mLocationClient;
    private double mSendLongtitude = 0, mSendLatitude = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grab_order);

        setToolbar();

        initView();

        getLocation();
    }

    /**
     * 获取定位信息
     */
    private void getLocation() {
        mLocationClient = AMapUtil.startLocation(getApplicationContext(), new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        //定位成功回调信息，设置相关消息
                        Log.d(TAG, "aMap = " + aMapLocation.toString());
                        mEtSendAddress.setText(aMapLocation.getAddress());
                        mSendLatitude = aMapLocation.getLatitude();
                        mSendLongtitude = aMapLocation.getLongitude();
                    } else {
                        //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                        Log.e("AmapError","location Error, ErrCode:"
                                + aMapLocation.getErrorCode() + ", errInfo:"
                                + aMapLocation.getErrorInfo());
                    }
                }
            }
        });
    }

    private void initView() {
        mBtnSendExpress = ViewFinder.findViewById(this, R.id.id_btn_send_express);
        mEtSendAddress = ViewFinder.findViewById(this, R.id.id_et_send_address);
        mEtSendName = ViewFinder.findViewById(this, R.id.id_et_send_name);
        mEtSendMobile = ViewFinder.findViewById(this, R.id.id_et_send_mobile);
        mEtRecvAddress = ViewFinder.findViewById(this, R.id.id_et_recv_address);
        mEtRecvName = ViewFinder.findViewById(this, R.id.id_et_recv_name);
        mEtRecvMobile = ViewFinder.findViewById(this, R.id.id_et_recv_mobile);

        mBtnSendExpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnSendClick();
            }
        });
    }

    /**
     * 点击发布订单
     */
    private void onBtnSendClick() {
        String sendAddress = mEtSendAddress.getText().toString().trim();
        String sendName = mEtSendName.getText().toString().trim();
        String sendMobile = mEtSendMobile.getText().toString().trim();
        String recvAddress = mEtRecvAddress.getText().toString().trim();
        String recvName = mEtRecvName.getText().toString().trim();
        String recvMobile = mEtRecvMobile.getText().toString().trim();
        if (StringUtils.isAllEmpty(sendAddress, sendName, sendMobile, recvAddress, recvName, recvMobile)) {
            SnackBarUtil.showSnackBar(mBtnSendExpress, "信息不完整");
            return;
        }

        showProgressDialog("请稍等，正在发布订单");

        RequestModel.saveExpressInfo(this, sendAddress, sendName, sendMobile, recvAddress, recvName, recvMobile, BmobUser.getCurrentUser(this, User.class), 0, 0, 10.0, mSendLongtitude, mSendLatitude, new RequestCallback<ExpressInfo>() {
            @Override
            public void onSuccess(ExpressInfo expressInfo) {
                dismissProgressDialog();
                Log.d("wanghuan", "objectId = " + expressInfo.getObjectId());
                ActivityModel.goToPublishGrabOrderActivity(GrabOrderActivity.this, expressInfo);
            }
            @Override
            public void onFail(ExpressInfo expressInfo) {
            }
        });
    }

}
