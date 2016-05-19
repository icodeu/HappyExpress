package com.icodeyou.happyexpress.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.icodeyou.happyexpress.R;
import com.icodeyou.happyexpress.adapter.NearPostStationAdapter;
import com.icodeyou.happyexpress.model.ActivityModel;
import com.icodeyou.library.util.bean.PostStation;
import com.icodeyou.library.util.model.RequestCallback;
import com.icodeyou.library.util.model.RequestModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";

    private ImageView mIvPostStationMore;
    // 寄快递
    private LinearLayout mLlSendExpress;
    // 发布代取
    private LinearLayout mLlHelpTake;
    // 查快递
    private LinearLayout mLlQueryExpress;

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
        mLlHelpTake = (LinearLayout) view.findViewById(R.id.id_ll_help_take);
        mLlQueryExpress = (LinearLayout) view.findViewById(R.id.id_ll_query_express);
        mLvNearPostStation = (ListView) view.findViewById(R.id.id_lv_near_post_station);
        mIvPostStationMore = (ImageView) view.findViewById(R.id.id_iv_near_post_station_more);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initData();
        setAdapter();
        setViewListener();

        // 获取周边驿站列表
        RequestModel.getNearbyPostStation(getContext(), new RequestCallback<List<PostStation>>() {
            @Override
            public void onSuccess(List<PostStation> postStations) {
                mPostStations.addAll(postStations);
                mNearPostStationAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFail(List<PostStation> postStations) {
            }
        });
    }

    private void initData() {
        mPostStations = new ArrayList<PostStation>();
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

        mLlHelpTake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityModel.goToPublishHelpTakeActivity(getActivity());
            }
        });

        mLlQueryExpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityModel.goToQueryExpressActivity(getActivity());
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

        mLvNearPostStation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //goto 驿站详情
                ActivityModel.goToPostStationDetailActivity(getContext(), mPostStations.get(position));
            }
        });
    }

}
