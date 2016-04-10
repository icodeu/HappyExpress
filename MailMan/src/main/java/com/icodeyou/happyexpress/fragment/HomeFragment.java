package com.icodeyou.happyexpress.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.icodeyou.happyexpress.R;
import com.icodeyou.happyexpress.adapter.GrabOrderListAdapter;
import com.icodeyou.library.util.bean.ExpressInfo;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {


    private ListView mLvGrabOrder;
    private GrabOrderListAdapter mGrabOrderListAdapter;
    private List<ExpressInfo> mExpressInfos;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mLvGrabOrder = (ListView) view.findViewById(R.id.id_lv_grab_order_list);

        initData();

        setAdapter();

        setViewListener();

        return view;
    }

    private void initData() {
        mExpressInfos = new ArrayList<ExpressInfo>();
        for(int i=0;i<10;i++) {
            ExpressInfo info = new ExpressInfo();
            info.setSendAddress("sendAddress");
            mExpressInfos.add(info);
        }
    }

    private void setAdapter() {
        mGrabOrderListAdapter = new GrabOrderListAdapter(getContext(), mExpressInfos);
        mLvGrabOrder.setAdapter(mGrabOrderListAdapter);
    }

    private void setViewListener() {
    }

}
