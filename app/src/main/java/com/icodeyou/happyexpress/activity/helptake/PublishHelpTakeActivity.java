package com.icodeyou.happyexpress.activity.helptake;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.icodeyou.happyexpress.R;
import com.icodeyou.happyexpress.activity.BaseActivity;
import com.icodeyou.library.util.StringUtils;
import com.icodeyou.library.util.ToastUtil;
import com.icodeyou.library.util.model.RequestCallback;
import com.icodeyou.library.util.model.RequestModel;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PublishHelpTakeActivity extends BaseActivity {

    @Bind(R.id.id_et_express_company)
    EditText mEtExpressCompany;
    @Bind(R.id.id_et_recv_package_address)
    EditText mEtRecvPackageAddress;
    @Bind(R.id.id_et_recv_owner_mobile)
    EditText mEtRecvOwnerMobile;
    @Bind(R.id.id_et_recv_owner_name)
    EditText mEtRecvOwnerName;
    @Bind(R.id.id_et_mailman_mobile)
    EditText mEtMailmanMobile;
    @Bind(R.id.id_et_delivery_time)
    EditText mEtDeliveryTime;
    @Bind(R.id.id_et_delivery_address)
    EditText mEtDeliveryAddress;
    @Bind(R.id.id_btn_send_help_take_order)
    Button mBtnSendHelpTakeOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_help_take);
        ButterKnife.bind(this);

        setToolbar();
    }

    @OnClick(R.id.id_btn_send_help_take_order)
    public void onClick() {
        // 收件人信息
        String expressCompany = mEtExpressCompany.getText().toString().trim();
        String recvAddress = mEtRecvPackageAddress.getText().toString().trim();
        String recvMobile = mEtRecvOwnerMobile.getText().toString().trim();
        String recvName = mEtRecvOwnerName.getText().toString().trim();
        String mailManMobile = mEtMailmanMobile.getText().toString().trim();

        // 交付信息
        String deliveryTime = mEtDeliveryTime.getText().toString().trim();
        String deliveryAddress = mEtDeliveryAddress.getText().toString().trim();

        if (StringUtils.isAnyNullOrEmpty(expressCompany, recvAddress, recvMobile, recvName, mailManMobile, deliveryTime, deliveryAddress)) {
            ToastUtil.showToast(this, "请填写全部内容");
            return;
        }

        // 发送网络请求保存发布代取信息
        RequestModel.insertHelpTake(this, expressCompany, recvAddress, recvMobile, recvName, mailManMobile, deliveryTime, deliveryAddress, new RequestCallback<String>() {
            @Override
            public void onSuccess(String s) {
                ToastUtil.showToast(PublishHelpTakeActivity.this, "已发布代取订单请求");
                finish();
            }

            @Override
            public void onFail(String s) {
                ToastUtil.showToast(PublishHelpTakeActivity.this, "发布代取订单请求失败: " + s);
            }
        });
    }
}


