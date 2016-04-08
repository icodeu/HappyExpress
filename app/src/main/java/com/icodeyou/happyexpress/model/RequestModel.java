package com.icodeyou.happyexpress.model;

import android.content.Context;
import android.util.Log;

import com.icodeyou.happyexpress.bean.ExpressInfo;
import com.icodeyou.happyexpress.bean.User;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by huan on 16/4/8.
 */
public class RequestModel {

    public static void saveExpressInfo(Context context, String sendAddress, String sendName, String sendMobile, String recvAddress, String recvName, String recvMobile, User pubUser, final RequestCallback<String> callback) {
        final ExpressInfo info = new ExpressInfo();
        info.setSendAddress(sendAddress);
        info.setSendName(sendName);
        info.setSendMobile(sendMobile);
        info.setRecvAddress(recvAddress);
        info.setRecvName(recvName);
        info.setRecvMobile(recvMobile);
        info.setPublishedUser(pubUser);

        info.save(context, new SaveListener() {
            @Override
            public void onSuccess() {
                callback.onSuccess(info.getObjectId());
                Log.d("ExpressInfo", "保存ExpressInfo成功 " + info.toString());
            }
            @Override
            public void onFailure(int code, String msg) {
                callback.onFail(msg);
                Log.d("ExpressInfo", "保存ExpressInfo失败 " + msg);
            }
        });
    }

    public static void getExpressInfoByObjectId(Context context, String objectId, final RequestCallback<ExpressInfo> callback) {
        BmobQuery<ExpressInfo> query = new BmobQuery<ExpressInfo>();
        query.getObject(context, objectId, new GetListener<ExpressInfo>() {
            @Override
            public void onSuccess(ExpressInfo object) {
                callback.onSuccess(object);
            }

            @Override
            public void onFailure(int code, String arg0) {
            }

        });
    }

}
