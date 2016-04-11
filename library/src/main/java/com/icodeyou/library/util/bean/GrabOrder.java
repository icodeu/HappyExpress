package com.icodeyou.library.util.bean;

import android.content.Context;
import android.util.Log;

import java.io.Serializable;
import java.util.Date;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by huan on 16/4/10.
 */
public class GrabOrder extends BmobObject implements Serializable{

    private ExpressInfo expressInfo; // 关联的ExpressInfo的ObjectId
    private User publishedUser; // 发布的用户
    private User courierUser; // 抢单的快递员
    private BmobDate grabTime; // 抢单时间
    private String takeCode;// 取件码
    private boolean isPayed; // 用户是否付款
    private String orderId; // 付款单号
    private Boolean isTaked; // 是否上门取件完成
    private Boolean isSend; // 是否发货完成
    private String trackingNumber; //运单号

    public static void createThisTable(Context context) {
        GrabOrder grabOrder = new GrabOrder();

        ExpressInfo info = new ExpressInfo();
        info.setSendAddress("北京交通大学");
        info.setSendName("欢欢");
        info.setSendMobile("18214562314");
        info.setRecvAddress("上海交通大学");
        info.setRecvName("乐乐");
        info.setRecvMobile("18545124789");
        grabOrder.setExpressInfo(info);

        User pubUser = BmobUser.getCurrentUser(context, User.class);
        grabOrder.setPublishedUser(pubUser);
        grabOrder.setCourierUser(pubUser);

        grabOrder.setGrabTime(new BmobDate(new Date()));
        grabOrder.setTakeCode(grabOrder.getObjectId());
        grabOrder.setPayed(false);
        grabOrder.setTaked(false);
        grabOrder.setSend(false);
        grabOrder.setTrackingNumber("1000234");

        grabOrder.save(context, new SaveListener() {
            @Override
            public void onSuccess() {
                Log.d("GrabOrder", "创建Table - GrabOrder 成功");
            }

            @Override
            public void onFailure(int code, String msg) {
                Log.d("GrabOrder", "创建Table - GrabOrder 失败");
            }
        });
    }

    public ExpressInfo getExpressInfo() {
        return expressInfo;
    }

    public void setExpressInfo(ExpressInfo expressInfo) {
        this.expressInfo = expressInfo;
    }

    public User getPublishedUser() {
        return publishedUser;
    }

    public void setPublishedUser(User publishedUser) {
        this.publishedUser = publishedUser;
    }

    public User getCourierUser() {
        return courierUser;
    }

    public void setCourierUser(User courierUser) {
        this.courierUser = courierUser;
    }

    public BmobDate getGrabTime() {
        return grabTime;
    }

    public void setGrabTime(BmobDate grabTime) {
        this.grabTime = grabTime;
    }

    public String getTakeCode() {
        return takeCode;
    }

    public void setTakeCode(String takeCode) {
        this.takeCode = takeCode;
    }

    public boolean isPayed() {
        return isPayed;
    }

    public void setPayed(boolean payed) {
        isPayed = payed;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public Boolean getTaked() {
        return isTaked;
    }

    public void setTaked(Boolean taked) {
        isTaked = taked;
    }

    public Boolean getSend() {
        return isSend;
    }

    public void setSend(Boolean send) {
        isSend = send;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "GrabOrder{" +
                "expressInfo=" + expressInfo +
                ", publishedUser=" + publishedUser +
                ", courierUser=" + courierUser +
                ", grabTime=" + grabTime +
                ", takeCode='" + takeCode + '\'' +
                ", isPayed=" + isPayed +
                ", orderId='" + orderId + '\'' +
                ", isTaked=" + isTaked +
                ", isSend=" + isSend +
                ", trackingNumber='" + trackingNumber + '\'' +
                '}';
    }
}
