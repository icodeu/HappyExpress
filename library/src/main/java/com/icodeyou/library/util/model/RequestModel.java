package com.icodeyou.library.util.model;

import android.content.Context;
import android.util.Log;

import com.icodeyou.library.util.bean.ExpressInfo;
import com.icodeyou.library.util.bean.GrabOrder;
import com.icodeyou.library.util.bean.User;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by huan on 16/4/8.
 */
public class RequestModel {

    public static void saveExpressInfo(Context context, String sendAddress, String sendName, String sendMobile, String recvAddress, String recvName, String recvMobile, User pubUser, int type, int status, final RequestCallback<ExpressInfo> callback) {
        final ExpressInfo info = new ExpressInfo();
        info.setSendAddress(sendAddress);
        info.setSendName(sendName);
        info.setSendMobile(sendMobile);
        info.setRecvAddress(recvAddress);
        info.setRecvName(recvName);
        info.setRecvMobile(recvMobile);
        info.setPublishedUser(pubUser);
        info.setType(type);
        info.setStatus(status);

        info.save(context, new SaveListener() {
            @Override
            public void onSuccess() {
                callback.onSuccess(info);
                Log.d("ExpressInfo", "保存ExpressInfo成功 " + info.toString());
            }
            @Override
            public void onFailure(int code, String msg) {
                callback.onFail(info);
                Log.d("ExpressInfo", "保存ExpressInfo失败 " + msg);
            }
        });
    }

    public static void getGrabOrderInfoByExpressInfo(Context context, ExpressInfo expressInfo, final RequestCallback<List<GrabOrder>> callback) {
        BmobQuery<GrabOrder> query = new BmobQuery<GrabOrder>();
        query.addWhereEqualTo("expressInfo", expressInfo);
        query.findObjects(context, new FindListener<GrabOrder>() {
            @Override
            public void onSuccess(List<GrabOrder> list) {
                callback.onSuccess(list);
            }

            @Override
            public void onError(int i, String s) {
                callback.onFail(null);
            }
        });

    }

}
