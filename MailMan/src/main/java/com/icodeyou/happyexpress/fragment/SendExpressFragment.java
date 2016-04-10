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
import com.icodeyou.happyexpress.adapter.SendExpressListAdapter;
import com.icodeyou.library.util.CollectionUtil;
import com.icodeyou.library.util.bean.GrabOrder;
import com.icodeyou.library.util.model.RequestCallback;
import com.icodeyou.library.util.model.RequestModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SendExpressFragment extends Fragment {

    private static final String TAG = "SendExpressFragment";

    private ListView mLvSendExpress;
    private SendExpressListAdapter mSendExpressAdapter;
    private List<GrabOrder> mGrabOrders;

    private TextView mTvEmptyView;

    private Timer mPollTimer;
    private static final long POLL_PERIOD_TIME = 4000;

    public SendExpressFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_send_express, container, false);
        mLvSendExpress = (ListView) view.findViewById(R.id.id_lv_send_express_list);
        mTvEmptyView = (TextView) view.findViewById(R.id.id_tv_empty_view);
        mTvEmptyView.setVisibility(View.VISIBLE);
        ((MainActivity)getActivity()).showProgressDialog("正在获取待发货订单");

        initData();

        setAdapter();

        startPollSendExpressData();

        return view;
    }

    private void startPollSendExpressData() {
        mPollTimer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                RequestModel.querySendExpressInfo(getContext(), new RequestCallback<List<GrabOrder>>() {
                    @Override
                    public void onSuccess(List<GrabOrder> grabOrders) {
                        if (!CollectionUtil.isEmpty(grabOrders)) {
                            mGrabOrders.clear();
                            mGrabOrders.addAll(grabOrders);
                            mSendExpressAdapter.notifyDataSetChanged();
                            mTvEmptyView.setVisibility(View.GONE);
                            ((MainActivity)getActivity()).dismissProgressDialog();
                            Log.d(TAG, "获取待发货信息 = " + grabOrders.toString());
                        } else {
                            mGrabOrders.clear();
                            mTvEmptyView.setVisibility(View.VISIBLE);
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

    private void initData() {
        mGrabOrders = new ArrayList<GrabOrder>();
    }

    private void setAdapter() {
        mSendExpressAdapter = new SendExpressListAdapter(getContext(), mGrabOrders);
        mLvSendExpress.setAdapter(mSendExpressAdapter);
    }

}
