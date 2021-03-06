package com.icodeyou.happyexpress.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.icodeyou.happyexpress.R;
import com.icodeyou.happyexpress.activity.MainActivity;
import com.icodeyou.happyexpress.adapter.GrabOrderListAdapter;
import com.icodeyou.library.util.CollectionUtil;
import com.icodeyou.library.util.bean.ExpressInfo;
import com.icodeyou.library.util.model.RequestCallback;
import com.icodeyou.library.util.model.RequestModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";

    private ListView mLvGrabOrder;
    private GrabOrderListAdapter mGrabOrderListAdapter;
    private List<ExpressInfo> mExpressInfos;

    private TextView mTvEmptyView;

    private Timer mPollTimer;
    private static final long POLL_PERIOD_TIME = 2000;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mLvGrabOrder = (ListView) view.findViewById(R.id.id_lv_grab_order_list);
        mTvEmptyView = (TextView) view.findViewById(R.id.id_tv_empty_view);
        mTvEmptyView.setVisibility(View.VISIBLE);
        ((MainActivity)getActivity()).showProgressDialog("正在获取可抢订单信息");

        initData();

        setAdapter();

        setViewListener();

        startPollGrabedData();

        return view;
    }

    private void startPollGrabedData() {
        mPollTimer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                RequestModel.queryGrabedExpressInfo(getContext(), new RequestCallback<List<ExpressInfo>>() {
                    @Override
                    public void onSuccess(List<ExpressInfo> expressInfos) {
                        if (!CollectionUtil.isEmpty(expressInfos)) {
                            mExpressInfos.clear();
                            mExpressInfos.addAll(expressInfos);
                            mGrabOrderListAdapter.notifyDataSetChanged();
                            mTvEmptyView.setVisibility(View.GONE);
                            ((MainActivity)getActivity()).dismissProgressDialog();
                            Log.d(TAG, "获取可抢订单信息 = " + expressInfos.toString());
                        } else {
                            mExpressInfos.clear();
                            mTvEmptyView.setVisibility(View.VISIBLE);
                        }
                    }
                    @Override
                    public void onFail(List<ExpressInfo> expressInfos) {
                    }
                });
            }
        };
        mPollTimer.schedule(timerTask, 0, POLL_PERIOD_TIME);
    }

    private void initData() {
        mExpressInfos = new ArrayList<ExpressInfo>();
    }

    private void setAdapter() {
        mGrabOrderListAdapter = new GrabOrderListAdapter(getContext(), mExpressInfos);
        mLvGrabOrder.setAdapter(mGrabOrderListAdapter);
    }

    private void setViewListener() {
    }

}
