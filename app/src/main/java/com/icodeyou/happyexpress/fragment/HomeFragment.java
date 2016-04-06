package com.icodeyou.happyexpress.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.icodeyou.happyexpress.R;
import com.icodeyou.happyexpress.adapter.NearPostStationAdapter;
import com.icodeyou.happyexpress.bean.PostStation;
import com.icodeyou.happyexpress.model.ActivityModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private ImageView mIvPostStationMore;
    private LinearLayout mLlSendExpress;
    private ListView mLvNearPostStation;
    private NearPostStationAdapter mNearPostStationAdapter;
    private List<PostStation> mPostStations;


    public HomeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mLlSendExpress = (LinearLayout) view.findViewById(R.id.id_ll_send_express);
        mLvNearPostStation = (ListView) view.findViewById(R.id.id_lv_near_post_station);
        mIvPostStationMore = (ImageView) view.findViewById(R.id.id_iv_near_post_station_more);

        initData();

        setAdapter();

        setViewListener();

        return view;
    }

    private void initData() {
        mPostStations = new ArrayList<PostStation>();
        String imgUrl = "https://avatars1.githubusercontent.com/u/7385129?v=3&s=460";
        mPostStations.add(new PostStation("卫生部宿舍报刊亭" ,imgUrl, 30));
        mPostStations.add(new PostStation("卫生部宿舍报刊亭" ,imgUrl, 30));
        mPostStations.add(new PostStation("卫生部宿舍报刊亭" ,imgUrl, 30));
    }

    private void setAdapter() {
        mNearPostStationAdapter = new NearPostStationAdapter(getContext(), mPostStations);
        mLvNearPostStation.setAdapter(mNearPostStationAdapter);
    }

    private void setViewListener() {
        mLlSendExpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityModel.goToSendExpressActivity(getActivity());
            }
        });

        mIvPostStationMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLvNearPostStation.getVisibility() == View.GONE) {
                    mLvNearPostStation.setVisibility(View.VISIBLE);
                    mIvPostStationMore.setImageResource(R.drawable.drop_up);
                } else if (mLvNearPostStation.getVisibility() == View.VISIBLE) {
                    mLvNearPostStation.setVisibility(View.GONE);
                    mIvPostStationMore.setImageResource(R.drawable.drop_down);
                }
            }
        });
    }

}
