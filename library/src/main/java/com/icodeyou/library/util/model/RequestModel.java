package com.icodeyou.library.util.model;

import android.content.Context;
import android.util.Log;

import com.icodeyou.library.util.bean.ExpressInfo;
import com.icodeyou.library.util.bean.GrabOrder;
import com.icodeyou.library.util.bean.PostStation;
import com.icodeyou.library.util.bean.User;

import java.util.Date;
import java.util.List;
import java.util.Random;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by huan on 16/4/8.
 */
public class RequestModel {

    private static final String TAG = "RequestModel";

    /**
     * 发布订单后保存快递基本信息
     */
    public static void saveExpressInfo(Context context, String sendAddress, String sendName, String sendMobile, String recvAddress, String recvName, String recvMobile, User pubUser, int type, int status, double money, double sendLongtitude, double sendLatitude, final RequestCallback<ExpressInfo> callback) {
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
        info.setMoney(money);
        info.setSendLongtitude(sendLongtitude);
        info.setSendLatitude(sendLatitude);

        info.save(context, new SaveListener() {
            @Override
            public void onSuccess() {
                callback.onSuccess(info);
                Log.d(TAG, "保存ExpressInfo成功 " + info.toString());
            }
            @Override
            public void onFailure(int code, String msg) {
                callback.onFail(info);
                Log.d(TAG, "保存ExpressInfo失败 " + msg);
            }
        });
    }

    /**
     * 根据ExpressInfo对象查询对应的GrabOrder
     */
    public static void getGrabOrderInfoByExpressInfo(Context context, ExpressInfo expressInfo, final RequestCallback<List<GrabOrder>> callback) {
        BmobQuery<GrabOrder> query = new BmobQuery<GrabOrder>();
        query.addWhereEqualTo("expressInfo", expressInfo);
        query.include("publishedUser");
        query.include("courierUser");
        query.include("expressInfo");
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

    /**
     * 查询可抢的快递信息 ExpressInfo
     */
    public static void queryGrabedExpressInfo(Context context, final RequestCallback<List<ExpressInfo>> callback) {
        BmobQuery<ExpressInfo> query = new BmobQuery<ExpressInfo>();
        query.addWhereEqualTo("status", ExpressInfo.STATUS_PUBLISHING);
        query.addWhereEqualTo("type", ExpressInfo.TYPE_GRAB);
        query.findObjects(context, new FindListener<ExpressInfo>() {
            @Override
            public void onSuccess(List<ExpressInfo> list) {
                callback.onSuccess(list);
            }

            @Override
            public void onError(int i, String s) {
            }
        });
    }

    /**
     * 抢单
     */
    public static void grabOrder(Context context, ExpressInfo expressInfo, final RequestCallback<GrabOrder> callback) {
        //1. Update ExpressInfo status->1
        ExpressInfo newInfo = new ExpressInfo();
        newInfo.setStatus(ExpressInfo.STATUS_GRABED);
        newInfo.update(context, expressInfo.getObjectId(), new UpdateListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "onSuccess 更新status->1");
            }
            @Override
            public void onFailure(int i, String s) {
                Log.d(TAG, "onFailure 更新status->1");
            }
        });

        //2. Insert To GrabOrder   此时还没有运单号信息
        final GrabOrder grabOrder = new GrabOrder();
        grabOrder.setExpressInfo(expressInfo);
        grabOrder.setPublishedUser(expressInfo.getPublishedUser());
        grabOrder.setCourierUser(BmobUser.getCurrentUser(context, User.class));
        grabOrder.setGrabTime(new BmobDate(new Date()));
        grabOrder.setPayed(false);
        // 生成四位随机安全码
        Random random = new Random();
        StringBuilder codeBuilder = new StringBuilder();
        for (int i=0;i<4;i++) {
            int code = random.nextInt(10);
            codeBuilder.append(code);
        }
        grabOrder.setTakeCode(codeBuilder.toString());
        grabOrder.setTaked(false);
        grabOrder.setSend(false);

        grabOrder.save(context, new SaveListener() {
            @Override
            public void onSuccess() {
                callback.onSuccess(grabOrder);
                Log.d(TAG, "onSuccess 保存ExpressInfo " + grabOrder.toString());
            }
            @Override
            public void onFailure(int code, String msg) {
                callback.onFail(grabOrder);
                Log.d(TAG, "onFail 保存ExpressInfo " + msg);
            }
        });
    }

    /**
     * 查询待上门取件的订单 GrabOrder
     */
    public static void queryComeDoorTakeExpressInfo(Context context, final RequestCallback<List<GrabOrder>> callback) {
        BmobQuery<GrabOrder> query = new BmobQuery<GrabOrder>();
        query.addWhereEqualTo("courierUser", BmobUser.getCurrentUser(context, User.class));
        query.addWhereEqualTo("isTaked", false);
        query.include("expressInfo");
        query.findObjects(context, new FindListener<GrabOrder>() {
            @Override
            public void onSuccess(List<GrabOrder> list) {
                Log.d(TAG, "onSuccess 获取上门取件信息 " + list.toString());
                callback.onSuccess(list);
            }

            @Override
            public void onError(int i, String s) {
            }
        });
    }

    /**
     * 取货完成，填写取件码 GrabOrder
     */
    public static void updateGrabOrderAfterTakeExpress(Context context, GrabOrder grabOrder, final RequestCallback<String> callback) {
        // Update isTake -> true
        GrabOrder newOrder = new GrabOrder();
        newOrder.setTaked(true);
        newOrder.update(context, grabOrder.getObjectId(), new UpdateListener() {
            @Override
            public void onSuccess() {
                callback.onSuccess("success");
                Log.d(TAG, "onSuccess 更新isTaked->true");
            }
            @Override
            public void onFailure(int i, String s) {
                callback.onSuccess(s);
                Log.d(TAG, "onFailure 更新isTaked->true");
            }
        });
    }

    /**
     * 查询待发货的订单 GrabOrder
     */
    public static void querySendExpressInfo(Context context, final RequestCallback<List<GrabOrder>> callback) {
        BmobQuery<GrabOrder> query = new BmobQuery<GrabOrder>();
        query.addWhereEqualTo("courierUser", BmobUser.getCurrentUser(context, User.class));
        query.addWhereEqualTo("isSend", false);
        query.addWhereEqualTo("isTaked", true);
        query.include("expressInfo");
        query.findObjects(context, new FindListener<GrabOrder>() {
            @Override
            public void onSuccess(List<GrabOrder> list) {
                Log.d(TAG, "onSuccess 查询待发货的订单 " + list.toString());
                callback.onSuccess(list);
            }
            @Override
            public void onError(int i, String s) {
            }
        });
    }

    /**
     * 发货完成，填写运单号 GrabOrder
     */
    public static void updateGrabOrderAfterSendExpress(Context context, GrabOrder grabOrder, String trackingNumber ,final RequestCallback<String> callback) {
        // Update trackingNumber
        GrabOrder newOrder = new GrabOrder();
        newOrder.setTrackingNumber(trackingNumber);
        newOrder.setSend(true);
        newOrder.update(context, grabOrder.getObjectId(), new UpdateListener() {
            @Override
            public void onSuccess() {
                callback.onSuccess("success");
                Log.d(TAG, "onSuccess 更新trackingNumber");
            }
            @Override
            public void onFailure(int i, String s) {
                callback.onSuccess(s);
                Log.d(TAG, "onFailure 更新trackingNumber");
            }
        });
    }

    /**
     * 查询已完成的订单 GrabOrder
     */
    public static void queryCompleteExpressInfo(Context context, final RequestCallback<List<GrabOrder>> callback) {
        BmobQuery<GrabOrder> query = new BmobQuery<GrabOrder>();
        query.addWhereEqualTo("courierUser", BmobUser.getCurrentUser(context, User.class));
        query.addWhereEqualTo("isSend", true);
        query.addWhereEqualTo("isTaked", true);
        query.include("expressInfo");
        query.findObjects(context, new FindListener<GrabOrder>() {
            @Override
            public void onSuccess(List<GrabOrder> list) {
                Log.d(TAG, "onSuccess 查询已完成的订单 " + list.toString());
                callback.onSuccess(list);
            }
            @Override
            public void onError(int i, String s) {
            }
        });
    }

    /**
     * 查询周边驿站列表 PostStation
     * todo 附近功能没有实现  现在是所有驿站
     */
    public static void getNearbyPostStation(Context context, final RequestCallback<List<PostStation>> callback) {
        BmobQuery<PostStation> query = new BmobQuery<PostStation>();
        query.findObjects(context, new FindListener<PostStation>() {
            @Override
            public void onSuccess(List<PostStation> list) {
                Log.d(TAG, "onSuccess 查询周边驿站列表 " + list.toString());
                callback.onSuccess(list);
            }
            @Override
            public void onError(int i, String s) {
            }
        });
    }

    /**
     * 根据抢单查询快递员信息 User
     */
    public static void queryCourierUserInfo(Context context, GrabOrder grabOrder, final RequestCallback<User> callback) {
        BmobQuery<User> query = new BmobQuery<User>();
        query.getObject(context, grabOrder.getCourierUser().getObjectId(), new GetListener<User>() {
            @Override
            public void onSuccess(User user) {
                Log.d(TAG, "onSuccess 根据抢单查询快递员信息 " + user.toString());
                callback.onSuccess(user);
            }

            @Override
            public void onFailure(int i, String s) {

            }
        });
    }

    /**
     * 更新用户的经纬度信息 User
     */
    public static void updateUserPositon(Context context, double longtitude, double latitude, final RequestCallback<String> callback) {
        // Update longtitude and latitude
        User newUser = new User();
        newUser.setLongtitude(longtitude);
        newUser.setLatitude(latitude);
        newUser.update(context, BmobUser.getCurrentUser(context, User.class).getObjectId(), new UpdateListener() {
            @Override
            public void onSuccess() {
                callback.onSuccess("success");
                Log.d(TAG, "onSuccess 更新用户的经纬度信息");
            }
            @Override
            public void onFailure(int i, String s) {
                callback.onSuccess(s);
                Log.d(TAG, "onFailure 更新用户的经纬度信息");
            }
        });
    }

    /**
     * 更新订单付款信息 GrabOrder
     */
    public static void updateOrderIdAfterPayed(Context context, GrabOrder grabOrder, String orderId, final RequestCallback<String> callback) {
        // Update orderId and isPay
        GrabOrder newOrder = new GrabOrder();
        newOrder.setPayed(true);
        newOrder.setOrderId(orderId);
        newOrder.update(context, grabOrder.getObjectId(), new UpdateListener() {
            @Override
            public void onSuccess() {
                callback.onSuccess("success");
                Log.d(TAG, "onSuccess 更新订单付款信息");
            }
            @Override
            public void onFailure(int i, String s) {
                callback.onSuccess(s);
                Log.d(TAG, "onFailure 更新订单付款信息");
            }
        });
    }

}
