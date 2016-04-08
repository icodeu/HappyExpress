package com.icodeyou.happyexpress.activity.sendexpress;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.icodeyou.happyexpress.R;
import com.icodeyou.happyexpress.activity.BaseActivity;
import com.icodeyou.happyexpress.bean.User;
import com.icodeyou.happyexpress.model.ActivityModel;
import com.icodeyou.happyexpress.model.RequestCallback;
import com.icodeyou.happyexpress.model.RequestModel;
import com.icodeyou.happyexpress.util.SnackBarUtil;
import com.icodeyou.happyexpress.util.StringUtils;
import com.icodeyou.happyexpress.util.ViewFinder;

import cn.bmob.v3.BmobUser;

public class GrabOrderActivity extends BaseActivity {

    private Button mBtnSendExpress;
    private EditText mEtSendAddress, mEtSendName, mEtSendMobile, mEtRecvAddress, mEtRecvName, mEtRecvMobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grab_order);

        setToolbar();

        initView();
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

        RequestModel.saveExpressInfo(this, sendAddress, sendName, sendMobile, recvAddress, recvName, recvMobile, BmobUser.getCurrentUser(this, User.class), new RequestCallback<String>() {
            @Override
            public void onSuccess(String objectId) {
                dismissProgressDialog();
                Log.d("wanghuan", "objectId = " + objectId);
                ActivityModel.goToPublishGrabOrderActivity(GrabOrderActivity.this, objectId);
            }
            @Override
            public void onFail(String msg) {

            }
        });
        // 直接发请求保存吧 懒得传递了
        /*
        Bundle bundle = new Bundle();
        bundle.putString(PublishGrabOrderActivity.SEND_ADDRESS, sendAddress);
        bundle.putString(PublishGrabOrderActivity.SEND_MOBILE, sendMobile);
        bundle.putString(PublishGrabOrderActivity.SEND_NAME, sendName);
        bundle.putString(PublishGrabOrderActivity.RECV_ADDRESS, recvAddress);
        bundle.putString(PublishGrabOrderActivity.RECV_MOBILE, recvMobile);
        bundle.putString(PublishGrabOrderActivity.RECV_NAME, recvName);
        */
    }

}
