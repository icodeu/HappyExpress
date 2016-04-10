package com.icodeyou.happyexpress.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.icodeyou.happyexpress.R;
import com.icodeyou.library.util.adapter.CommonAdapter;
import com.icodeyou.library.util.bean.ExpressInfo;

import java.util.List;

/**
 * Created by huan on 16/4/6.
 */
public class GrabOrderListAdapter extends CommonAdapter<ExpressInfo> {

    public GrabOrderListAdapter(Context context) {
        super(context);
    }

    public GrabOrderListAdapter(Context context, List<ExpressInfo> list) {
        super(context, list);
    }

    @Override
    public int getContentView() {
        return R.layout.item_grab_order;
    }

    @Override
    public void onInitView(View view, int position) {
        ExpressInfo expressInfo = (ExpressInfo) getItem(position);
        TextView tvSendAddress = get(view, R.id.id_tv_send_address);
        TextView tvDistance = get(view, R.id.id_tv_distance);
        Button btnGrab = get(view, R.id.id_btn_grab_order);
        tvSendAddress.setText(expressInfo.getSendAddress());
        tvDistance.setText("40m");
        btnGrab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }
}
