package com.icodeyou.happyexpress.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.icodeyou.happyexpress.R;
import com.icodeyou.happyexpress.model.ActivityModel;
import com.icodeyou.library.util.AlertDialogUtil;
import com.icodeyou.library.util.adapter.CommonAdapter;
import com.icodeyou.library.util.bean.HelpTake;
import com.icodeyou.library.util.model.RequestCallback;
import com.icodeyou.library.util.model.RequestModel;

import java.util.List;

/**
 * Created by huan on 16/4/6.
 */
public class HelpTakeListAdapter extends CommonAdapter<HelpTake> {

    private static final String TAG = "HelpTakeListAdapter";
    private Context mContext;

    public HelpTakeListAdapter(Context context) {
        super(context);
    }

    public HelpTakeListAdapter(Context context, List<HelpTake> list) {
        super(context, list);
        this.mContext = context;
    }

    @Override
    public int getContentView() {
        return R.layout.item_help_take_grab;
    }

    @Override
    public void onInitView(View view, int position) {
        final HelpTake helpTake = (HelpTake) getItem(position);
        TextView tvSendAddress = get(view, R.id.id_tv_send_address);
        TextView tvDistance = get(view, R.id.id_tv_distance);
        final Button btnGrab = get(view, R.id.id_btn_grab_order);

        tvSendAddress.setText(helpTake.getRecvPackageAddress());
        tvDistance.setText("1200m");

        btnGrab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestModel.grabHelpTakeOrder(getContext(), helpTake, new RequestCallback<String>() {
                    @Override
                    public void onSuccess(String s) {
                        AlertDialogUtil.showAlertDialogOK(getContext(), "抢单成功，请帮忙尽快取哦");
                        btnGrab.setText("详情");
                        btnGrab.setEnabled(false);
                    }
                    @Override
                    public void onFail(String s) {
                        AlertDialogUtil.showAlertDialogOK(getContext(), "抢单失败 " + s);
                    }
                });
            }
        });

        // 进入代取详情页
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityModel.goToHelpTakeDetailActivity(mContext, helpTake);
            }
        });
    }
}
