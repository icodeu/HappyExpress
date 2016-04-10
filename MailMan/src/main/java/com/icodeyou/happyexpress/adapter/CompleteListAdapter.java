package com.icodeyou.happyexpress.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.icodeyou.happyexpress.R;
import com.icodeyou.library.util.adapter.CommonAdapter;
import com.icodeyou.library.util.bean.GrabOrder;

import java.util.List;

/**
 * Created by huan on 16/4/6.
 */
public class CompleteListAdapter extends CommonAdapter<GrabOrder> {

    private static final String TAG = "CompleteListAdapter";

    public CompleteListAdapter(Context context) {
        super(context);
    }

    public CompleteListAdapter(Context context, List<GrabOrder> list) {
        super(context, list);
    }

    @Override
    public int getContentView() {
        return R.layout.item_complete_express;
    }

    @Override
    public void onInitView(View view, int position) {
        final GrabOrder grabOrder = (GrabOrder) getItem(position);
        TextView tvSendAddress = get(view, R.id.id_tv_send_address);
        TextView tvDistance = get(view, R.id.id_tv_distance);
        TextView tvGrabTime = get(view, R.id.id_tv_grab_time);

        tvSendAddress.setText(grabOrder.getExpressInfo().getSendAddress());
        tvDistance.setText("40m");
        tvGrabTime.setText(grabOrder.getGrabTime().getDate());
    }
}
