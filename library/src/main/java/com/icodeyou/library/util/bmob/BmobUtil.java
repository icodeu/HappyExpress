package com.icodeyou.library.util.bmob;

import android.content.Context;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.RequestSMSCodeListener;

/**
 * Created by huan on 16/4/11.
 */
public class BmobUtil {
    private static final String TAG = "BmobUtil";

    private static final String SMS_TAKE_CODE_CONTENT = "您的取件码是%smscode%，请在快递员上门取件时提供。";

    public static void sendSMSWithTakeCode(Context context, String phoneNumber, String takeCode) {
        final String takeCodeContent = SMS_TAKE_CODE_CONTENT.replace("%smscode%", takeCode);

        Log.d(TAG, "before SendSMS phoneNumber=" + phoneNumber + " takeCode=" + takeCode);
        SimpleDateFormat format =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sendTime = format.format(new Date());
        BmobSMS.requestSMS(context, phoneNumber, takeCodeContent, sendTime, new RequestSMSCodeListener() {
            @Override
            public void done(Integer smsId,BmobException ex) {
                if(ex==null){
                    //用于查询本次短信发送详情
                    Log.i(TAG,"短信发送成功，短信id："+smsId + " 短信内容:" + takeCodeContent);
                }else{
                    Log.i(TAG,"errorCode = "+ex.getErrorCode()+",errorMsg = "+ex.getLocalizedMessage());
                }
            }
        });
    }

}
