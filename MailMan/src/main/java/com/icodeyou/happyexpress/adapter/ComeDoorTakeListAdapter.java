package com.icodeyou.happyexpress.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.icodeyou.happyexpress.R;
import com.icodeyou.library.util.adapter.CommonAdapter;
import com.icodeyou.library.util.bean.GrabOrder;

import java.util.List;

/**
 * Created by huan on 16/4/6.
 */
public class ComeDoorTakeListAdapter extends CommonAdapter<GrabOrder> {

    private static final String TAG = "GrabOrderAdapter";

    public ComeDoorTakeListAdapter(Context context) {
        super(context);
    }

    public ComeDoorTakeListAdapter(Context context, List<GrabOrder> list) {
        super(context, list);
    }

    @Override
    public int getContentView() {
        return R.layout.item_come_door_take;
    }

    @Override
    public void onInitView(View view, int position) {
        final GrabOrder grabOrder = (GrabOrder) getItem(position);
        TextView tvSendAddress = get(view, R.id.id_tv_send_address);
        TextView tvDistance = get(view, R.id.id_tv_distance);
        TextView tvGrabTime = get(view, R.id.id_tv_grab_time);
        Button btnTake = get(view, R.id.id_btn_take);

        tvSendAddress.setText(grabOrder.getExpressInfo().getSendAddress());
        tvDistance.setText("40m");
        tvGrabTime.setText(grabOrder.getGrabTime().getDate());

        btnTake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                 输入取件码takecode
//                RequestModel.grabOrder(getContext(), expressInfo, new RequestCallback<GrabOrder>() {
//                    @Override
//                    public void onSuccess(GrabOrder grabOrder) {
//
//                        Log.d(TAG, "抢单成功 grabOrder = " + grabOrder.toString());
//                    }
//                    @Override
//                    public void onFail(GrabOrder grabOrder) {
//                    }
//                });
            }
        });
    }
}
