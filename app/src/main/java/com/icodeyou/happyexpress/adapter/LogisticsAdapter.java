package com.icodeyou.happyexpress.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.icodeyou.happyexpress.R;
import com.icodeyou.happyexpress.bean.LogisticsInfo;

import java.util.List;

/**
 * Created by huan on 16/4/6.
 */
public class LogisticsAdapter extends CommonAdapter<LogisticsInfo> {

    public LogisticsAdapter(Context context) {
        super(context);
    }

    public LogisticsAdapter(Context context, List<LogisticsInfo> list) {
        super(context, list);
    }

    @Override
    public int getContentView() {
        return R.layout.item_logistics;
    }

    @Override
    public void onInitView(View view, int position) {
        LogisticsInfo logisticsInfo = (LogisticsInfo) getItem(position);
        TextView tvTime = get(view, R.id.id_tv_time);
        TextView tvDate = get(view, R.id.id_tv_date);
        TextView tvInfo= get(view, R.id.id_tv_info);
        View topDidiver = get(view, R.id.id_top_divider);
        ImageView topImg = get(view, R.id.id_top_img);

        tvTime.setText(logisticsInfo.getTime());
        tvDate.setText(logisticsInfo.getDate());
        tvInfo.setText(logisticsInfo.getInfo());
        // 如果是第一个 就把竖线换成icon
        if (position == 0) {
            topDidiver.setVisibility(View.GONE);
            topImg.setVisibility(View.VISIBLE);
        } else {
            topDidiver.setVisibility(View.VISIBLE);
            topImg.setVisibility(View.GONE);
        }
    }
}
