package com.icodeyou.happyexpress.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.icodeyou.happyexpress.R;
import com.icodeyou.happyexpress.adapter.HelpTakeListAdapter;
import com.icodeyou.library.util.CollectionUtil;
import com.icodeyou.library.util.bean.HelpTake;
import com.icodeyou.library.util.model.RequestCallback;
import com.icodeyou.library.util.model.RequestModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * 发现页面 - 代取可抢列表
 */
public class FindFragment extends Fragment {
    private static final String TAG = "FindFragment";

    private ListView mLvHelpTake;

    private HelpTakeListAdapter mAdapter;
    private List<HelpTake> mData;

    public FindFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find, container, false);
        mLvHelpTake = (ListView) view.findViewById(R.id.id_lv_help_take);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initData();
        setAdapter();
        setViewListener();
        getData();
    }

    private void initData() {
        mData = new ArrayList<HelpTake>();
    }

    private void getData() {
        RequestModel.queryHelpTake(getContext(), new RequestCallback<List<HelpTake>>() {
            @Override
            public void onSuccess(List<HelpTake> helpTakes) {
                if (CollectionUtil.isNotEmpty(helpTakes)) {
                    mAdapter.clear();
                    mAdapter.addAll(helpTakes);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFail(List<HelpTake> helpTakes) {

            }
        });
    }

    private void setAdapter() {
        mAdapter = new HelpTakeListAdapter(getContext(), mData);
        mLvHelpTake.setAdapter(mAdapter);
    }

    private void setViewListener() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
