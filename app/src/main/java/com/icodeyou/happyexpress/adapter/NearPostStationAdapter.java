package com.icodeyou.happyexpress.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.icodeyou.happyexpress.R;
import com.icodeyou.library.util.adapter.CommonAdapter;
import com.icodeyou.library.util.amap.AMapUtil;
import com.icodeyou.library.util.bean.PostStation;

import java.util.List;

/**
 * Created by huan on 16/4/6.
 */
public class NearPostStationAdapter extends CommonAdapter<PostStation> {
    private static final String TAG = "NearPostStationAdapter";

    public NearPostStationAdapter(Context context) {
        super(context);
    }

    public NearPostStationAdapter(Context context, List<PostStation> list) {
        super(context, list);
    }

    @Override
    public int getContentView() {
        return R.layout.item_near_post_station;
    }

    @Override
    public void onInitView(View view, int position) {
        PostStation postStation = (PostStation) getItem(position);
        TextView tvPostStationName = get(view, R.id.id_tv_post_station_name);
        TextView tvPostStationDesc = get(view, R.id.id_tv_post_station_desc);
        TextView tvPostStationDistance = get(view, R.id.id_tv_post_station_distance);
        ImageView ivPostStation = get(view, R.id.id_iv_post_station);

        tvPostStationName.setText(postStation.getName());
        tvPostStationDesc.setText(postStation.getDesc());
        Glide.with(getContext()).load(postStation.getImgUrl()).into(ivPostStation);

        tvPostStationDistance.setText(AMapUtil.calculateDistance(getContext(), postStation.getLatitude(), postStation.getLongtitude()));
    }
}
