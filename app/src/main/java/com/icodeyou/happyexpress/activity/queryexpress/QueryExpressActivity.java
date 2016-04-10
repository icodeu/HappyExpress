package com.icodeyou.happyexpress.activity.queryexpress;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.icodeyou.happyexpress.R;
import com.icodeyou.happyexpress.activity.BaseActivity;
import com.icodeyou.happyexpress.adapter.LogisticsAdapter;
import com.icodeyou.happyexpress.view.LogisticsHeaderView;
import com.icodeyou.library.util.bean.LogisticsInfo;

import java.util.ArrayList;
import java.util.List;

public class QueryExpressActivity extends BaseActivity {

    private Button mBtnQueryExpress;
    private EditText mEtExpressOrder;
    private TextInputLayout mTilExpressOrder;

    private ListView mLvLogistics;
    private List<LogisticsInfo> mLogisticsInfos;
    private LogisticsAdapter mLogisticsAdapter;

    private LogisticsHeaderView mLogisticsHeaderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_express);

        setToolbar();

        initView();
        setListener();
        initData();
        setAdapter();
    }

    private void setAdapter() {
        mLogisticsAdapter = new LogisticsAdapter(this, mLogisticsInfos);
        mLvLogistics.setAdapter(mLogisticsAdapter);

    }

    private void initData() {
        mLogisticsInfos = new ArrayList<LogisticsInfo>();
        mLogisticsInfos.add(new LogisticsInfo("18:26", "2016.01.23", "已签收"));
        mLogisticsInfos.add(new LogisticsInfo("18:26", "2016.01.23", "正在派送途中，请您准备签收"));
        mLogisticsInfos.add(new LogisticsInfo("18:26", "2016.01.23", "顺丰速运正在为您派件"));
    }

    private void setListener() {
        mBtnQueryExpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String expressOrder = mEtExpressOrder.getText().toString();
                if (!isValidExpressOrder(expressOrder)) {
                    mTilExpressOrder.setError("不是有效的运单号");
                    return;
                }
                mTilExpressOrder.setErrorEnabled(false);
            }
        });
    }

    private boolean isValidExpressOrder(String expressOrder) {
        if (TextUtils.isEmpty(expressOrder))
            return false;
        if (expressOrder.length() < 10)
            return false;
        return true;
    }

    private void initView() {
        mBtnQueryExpress = (Button) findViewById(R.id.id_btn_query_express);
        mEtExpressOrder = (EditText) findViewById(R.id.id_et_express_order);
        mTilExpressOrder = (TextInputLayout) findViewById(R.id.id_til_express_order);
        mLvLogistics = (ListView) findViewById(R.id.id_lv_logistics);
        mLogisticsHeaderView = (LogisticsHeaderView) findViewById(R.id.id_logistics_header_view);
        mLogisticsHeaderView.setAttachActivity(this);
    }

}
