package com.icodeyou.happyexpress.activity.helptake;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.bumptech.glide.Glide;
import com.icodeyou.happyexpress.R;
import com.icodeyou.happyexpress.activity.BaseActivity;
import com.icodeyou.library.util.ToastUtil;
import com.icodeyou.library.util.bean.HelpTake;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HelpTakeDetailActivity extends BaseActivity {

    public static final String KEY_HELP_TAKE = "KEY_HELP_TAKE";

    private HelpTake mHelpTake;

    @Bind(R.id.id_iv_post_station)
    ImageView mIvPostStation;
    @Bind(R.id.id_tv_post_station_name)
    TextView mTvPostStationName;
    @Bind(R.id.id_tv_post_station_desc)
    TextView mTvPostStationDesc;
    @Bind(R.id.id_tv_post_station_distance)
    TextView mTvPostStationDistance;
    @Bind(R.id.id_tv_address)
    TextView mTvAddress;
    @Bind(R.id.id_iv_telephone)
    ImageView mIvTelephone;
    @Bind(R.id.id_tv_open_time)
    TextView mTvOpenTime;
    @Bind(R.id.id_iv_qrcode)
    ImageView mIvQrcode;
    @Bind(R.id.post_station_map)
    MapView mMapView;

    private AMap mAMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_take_detail);
        ButterKnife.bind(this);

        setToolbar();

        getDataPassed();

        setViewData();

        setViewListener();

        mMapView.onCreate(savedInstanceState);// 此方法必须重写
        setMapView();
    }

    private void setMapView() {
        if (mAMap == null) {
            mAMap = mMapView.getMap();

            // 驿站经纬度坐标
            LatLng stationPos = new LatLng(39.953978, 116.34844);

            // 往地图上添加一个marker
            mAMap.addMarker(new MarkerOptions().position(stationPos).icon(
                    BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            // 地图中心移动到指定坐标
            changeCamera(
                    CameraUpdateFactory.newCameraPosition(new CameraPosition(
                            stationPos, 18, 0, 30)), null);
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

    private void setViewData() {
        Glide.with(this).load("https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=3775999286,734565944&fm=58").into(mIvPostStation);
        mTvPostStationName.setText("取货地址:" + mHelpTake.getRecvPackageAddress());
        mTvPostStationDesc.setText("顺丰速运");
        mTvPostStationDistance.setText("900m");
        mTvAddress.setText("交付地址:" + mHelpTake.getDeliveryAddress());
        mTvOpenTime.setText("交付时间:" + mHelpTake.getDeliveryTime());
    }

    private void setViewListener() {
        // 拨打电话
        mIvTelephone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mHelpTake.getRecvOwnerMobile()));
                if (ActivityCompat.checkSelfPermission(HelpTakeDetailActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                HelpTakeDetailActivity.this.startActivity(intent);
            }
        });

        // 显示二维码
        mIvQrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog qrDialog = new Dialog(HelpTakeDetailActivity.this);
                qrDialog.setContentView(R.layout.dialog_qrcode);
                qrDialog.setTitle("二维码");
                ImageView ivQrcode = (ImageView) qrDialog.findViewById(R.id.id_iv_qr_code);
                Glide.with(HelpTakeDetailActivity.this).load("http://a.hiphotos.baidu.com/baike/w%3D268%3Bg%3D0/sign=7bcb659c9745d688a302b5a29cf91a23/2934349b033b5bb571dc8c5133d3d539b600bc12.jpg").into(ivQrcode);
                qrDialog.show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        qrDialog.dismiss();
                        ToastUtil.showToast(HelpTakeDetailActivity.this, "认证成功");
                    }
                }, 5000);
            }
        });
    }

    private void getDataPassed() {
        Intent intent = getIntent();
        if (intent.hasExtra(KEY_HELP_TAKE)) {
            mHelpTake = (HelpTake) intent.getSerializableExtra(KEY_HELP_TAKE);
        }
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

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }
}


