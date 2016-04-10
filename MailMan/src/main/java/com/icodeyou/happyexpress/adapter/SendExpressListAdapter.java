package com.icodeyou.happyexpress.adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.icodeyou.happyexpress.R;
import com.icodeyou.library.util.StringUtils;
import com.icodeyou.library.util.ToastUtil;
import com.icodeyou.library.util.adapter.CommonAdapter;
import com.icodeyou.library.util.bean.GrabOrder;
import com.icodeyou.library.util.model.RequestCallback;
import com.icodeyou.library.util.model.RequestModel;

import java.util.List;

/**
 * Created by huan on 16/4/6.
 */
public class SendExpressListAdapter extends CommonAdapter<GrabOrder> {

    private static final String TAG = "SendExpressAdapter";

    public SendExpressListAdapter(Context context) {
        super(context);
    }

    public SendExpressListAdapter(Context context, List<GrabOrder> list) {
        super(context, list);
    }

    @Override
    public int getContentView() {
        return R.layout.item_send_express;
    }

    @Override
    public void onInitView(View view, int position) {
        final GrabOrder grabOrder = (GrabOrder) getItem(position);
        TextView tvSendAddress = get(view, R.id.id_tv_send_address);
        TextView tvDistance = get(view, R.id.id_tv_distance);
        TextView tvGrabTime = get(view, R.id.id_tv_grab_time);
        Button btnTrackNumber = get(view, R.id.id_btn_track_number);

        tvSendAddress.setText(grabOrder.getExpressInfo().getSendAddress());
        tvDistance.setText("40m");
        tvGrabTime.setText(grabOrder.getGrabTime().getDate());

        btnTrackNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTrackNumberDialog(grabOrder);
            }
        });
    }

    /**
     * 弹出输入运单号对话框
     */
    private void showTrackNumberDialog(final GrabOrder grabOrder) {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_input_track_number);
        Button btnTrackNumber = (Button) dialog.findViewById(R.id.id_btn_track_number);
        final EditText etTrackNumber = (EditText) dialog.findViewById(R.id.id_et_track_number);
        final TextInputLayout tilTrackNumber = (TextInputLayout) dialog.findViewById(R.id.id_til_track_number);

        btnTrackNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtils.isLengthNotSmallerThanX(etTrackNumber.getText().toString().trim(), 12)) {
                    // valid trackNumber

                    RequestModel.updateGrabOrderAfterSendExpress(getContext(), grabOrder, etTrackNumber.getText().toString().trim(), new RequestCallback<String>() {
                        @Override
                        public void onSuccess(String s) {
                            ToastUtil.showToast(getContext(), "发货成功，请到已完成查看具体信息");
                            if (getmList().contains(grabOrder)) {
                                getmList().remove(grabOrder);
                                SendExpressListAdapter.this.notifyDataSetChanged();
                            }
                            if (dialog != null && dialog.isShowing())
                                dialog.dismiss();
                        }
                        @Override
                        public void onFail(String s) {
                        }
                    });
                } else {
                    // fail
                    tilTrackNumber.setError("运单号错误");
                }
            }
        });

        dialog.setTitle("请输入运单号");
        dialog.show();
    }
}