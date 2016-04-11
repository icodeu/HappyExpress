package com.icodeyou.happyexpress.activity.poststation;

import android.content.Intent;
import android.os.Bundle;
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
import com.icodeyou.library.util.bean.PostStation;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PostStationDetailActivity extends BaseActivity {

    public static final String KEY_POST_STATION = "KEY_POST_STATION";

    private PostStation mPostStation;

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
    @Bind(R.id.post_station_map)
    MapView mMapView;

    private AMap mAMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_station_detail);
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
            LatLng stationPos = new LatLng(mPostStation.getLatitude(), mPostStation.getLongtitude());

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
        Glide.with(this).load(mPostStation.getImgUrl()).into(mIvPostStation);
        mTvPostStationName.setText(mPostStation.getName());
        mTvPostStationDesc.setText(mPostStation.getDesc());
        mTvPostStationDistance.setText("30m");
        mTvAddress.setText(mPostStation.getAddress());
        mTvOpenTime.setText(mPostStation.getOpenTime());
    }

    private void setViewListener() {
        mIvTelephone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void getDataPassed() {
        Intent intent = getIntent();
        if (intent.hasExtra(KEY_POST_STATION)) {
            mPostStation = (PostStation) intent.getSerializableExtra(KEY_POST_STATION);
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
