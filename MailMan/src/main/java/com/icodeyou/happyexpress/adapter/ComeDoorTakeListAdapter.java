package com.icodeyou.happyexpress.adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.icodeyou.happyexpress.R;
import com.icodeyou.library.util.SnackBarUtil;
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
                showTakeCodeDialog(grabOrder);
            }
        });
    }


    /**
     * 弹出输入取件码对话框
     */
    private void showTakeCodeDialog(final GrabOrder grabOrder) {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_input_take_code);
        Button btnTakeCode = (Button) dialog.findViewById(R.id.id_btn_take_code);
        final EditText etTakeCode = (EditText) dialog.findViewById(R.id.id_et_take_code);
        final TextInputLayout tilTakeCode = (TextInputLayout) dialog.findViewById(R.id.id_til_take_code);

        btnTakeCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtils.equals(grabOrder.getTakeCode(), etTakeCode.getText().toString().trim())) {
                    // success
                    RequestModel.updateGrabOrderAfterTakeExpress(getContext(), grabOrder, new RequestCallback<String>() {
                        @Override
                        public void onSuccess(String s) {
                            ToastUtil.showToast(getContext(), "取件成功，请到待发货填写物流信息");
//                            SnackBarUtil.showSnackBar(etTakeCode, "取件成功，请到待发货填写物流信息");
                            if (getmList().contains(grabOrder)) {
                                getmList().remove(grabOrder);
                                ComeDoorTakeListAdapter.this.notifyDataSetChanged();
                            }
                            if (dialog != null && dialog.isShowing())
                                dialog.dismiss();
                        }

                        @Override
                        public void onFail(String s) {
                            SnackBarUtil.showSnackBar(etTakeCode, "出错啦 " + s);
                        }
                    });
                } else {
                    // fail
                    tilTakeCode.setError("取件码错误");
                }
            }
        });

        dialog.setTitle("请输入取件码");
        dialog.show();
    }
}
