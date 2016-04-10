package com.icodeyou.happyexpress.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.icodeyou.happyexpress.R;
import com.icodeyou.library.util.AlertDialogUtil;
import com.icodeyou.library.util.adapter.CommonAdapter;
import com.icodeyou.library.util.bean.ExpressInfo;
import com.icodeyou.library.util.bean.GrabOrder;
import com.icodeyou.library.util.model.RequestCallback;
import com.icodeyou.library.util.model.RequestModel;

import java.util.List;

/**
 * Created by huan on 16/4/6.
 */
public class GrabOrderListAdapter extends CommonAdapter<ExpressInfo> {

    private static final String TAG = "GrabOrderAdapter";

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
        final ExpressInfo expressInfo = (ExpressInfo) getItem(position);
        TextView tvSendAddress = get(view, R.id.id_tv_send_address);
        TextView tvDistance = get(view, R.id.id_tv_distance);
        Button btnGrab = get(view, R.id.id_btn_grab_order);
        tvSendAddress.setText(expressInfo.getSendAddress());
        tvDistance.setText("40m");

        btnGrab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestModel.grabOrder(getContext(), expressInfo, new RequestCallback<GrabOrder>() {
                    @Override
                    public void onSuccess(GrabOrder grabOrder) {
                        AlertDialogUtil.showAlertDialogOK(getContext(), "抢单成功，请到待上门取件查看订单");
                        Log.d(TAG, "抢单成功 grabOrder = " + grabOrder.toString());
                    }
                    @Override
                    public void onFail(GrabOrder grabOrder) {
                    }
                });
            }
        });
    }
}
