package com.icodeyou.happyexpress.model;

import android.content.Context;
import android.content.Intent;

import com.icodeyou.happyexpress.activity.sendexpress.ComeDoorOrderActivity;
import com.icodeyou.happyexpress.activity.sendexpress.GrabOrderActivity;
import com.icodeyou.happyexpress.activity.sendexpress.PublishGrabOrderActivity;
import com.icodeyou.happyexpress.activity.sendexpress.SendExpressActivity;

/**
 * Created by huan on 16/4/5.
 */
public class ActivityModel {

    public static void goToSendExpressActivity(Context context) {
        Intent intent = new Intent(context, SendExpressActivity.class);
        context.startActivity(intent);
    }

    /**
     * 驿站寄件
     */
    public static void goToComeDoorOrderActivity(Context context) {
        Intent intent = new Intent(context, ComeDoorOrderActivity.class);
        context.startActivity(intent);
    }

    /**
     * 抢单寄件
     */
    public static void goToGrabOrderActivity(Context context) {
        Intent intent = new Intent(context, GrabOrderActivity.class);
        context.startActivity(intent);
    }

    /**
     * 发布抢单寄件页面
     */
    public static void goToPublishGrabOrderActivity(Context context) {
        Intent intent = new Intent(context, PublishGrabOrderActivity.class);
        context.startActivity(intent);
    }

}
