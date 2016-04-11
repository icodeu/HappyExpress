package com.icodeyou.happyexpress.activity.queryexpress;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.icodeyou.happyexpress.R;
import com.icodeyou.happyexpress.activity.BaseActivity;
import com.icodeyou.happyexpress.adapter.LogisticsAdapter;
import com.icodeyou.happyexpress.view.LogisticsHeaderView;
import com.icodeyou.library.util.Juhe.JuheUtil;
import com.icodeyou.library.util.StringUtils;
import com.icodeyou.library.util.bean.LogisticsInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class QueryExpressActivity extends BaseActivity {

    private Button mBtnQueryExpress;
    private EditText mEtExpressOrder;
    private TextInputLayout mTilExpressOrder;

    private ListView mLvLogistics;
    private List<LogisticsInfo> mLogisticsInfos;
    private LogisticsAdapter mLogisticsAdapter;

    private LogisticsHeaderView mLogisticsHeaderView;

    @Bind(R.id.id_ll_logistics_result)
    LinearLayout mLlLogisticsResult;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String jsonStr = (String) msg.obj;
            if (StringUtils.isEmpty(jsonStr))
                return;
            try {
                JSONObject jsonObject = new JSONObject(jsonStr);
                JSONObject resultObject = jsonObject.getJSONObject("result");
                JSONArray listArr = resultObject.getJSONArray("list");
                for (int i=0;i<listArr.length();i++) {
                    JSONObject itemObject = listArr.getJSONObject(i);
                    String dateTime = itemObject.getString("datetime");
                    String remark = itemObject.getString("remark");
                    String[] dateTimeArr = dateTime.split(" ");
                    mLogisticsInfos.add(new LogisticsInfo(dateTimeArr[1], dateTimeArr[0], remark));
                }
                mLlLogisticsResult.setVisibility(View.VISIBLE);
                mLogisticsAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_express);
        ButterKnife.bind(this);

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
    }

    private void setListener() {
        mBtnQueryExpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String expressOrder = mEtExpressOrder.getText().toString().trim();
                if (!isValidExpressOrder(expressOrder)) {
                    mTilExpressOrder.setError("不是有效的运单号");
                    return;
                }
                mTilExpressOrder.setErrorEnabled(false);
                queryExpress(expressOrder);
            }
        });
    }

    private void queryExpress(final String expressOrder) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String jsonStr = JuheUtil.queryLogisticsInfo("zto", expressOrder);
                Message message = Message.obtain();
                message.obj = jsonStr;
                mHandler.sendMessage(message);
            }
        }).start();
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

        mLlLogisticsResult.setVisibility(View.GONE);
    }

}
